DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh

AWS_VPC_ID=$(aws ec2 describe-vpcs \
 --query 'Vpcs[?Tags[?Key == `Name` && Value == `http4k-function-vpc`]]. VpcId' \
 --output text)

AWS_LB_ARN=$(aws elbv2 describe-load-balancers \
--names http4k-load-balancer \
--query 'LoadBalancers[*].LoadBalancerArn' \
--output text)

aws elbv2 delete-load-balancer \
--load-balancer-arn ${AWS_LB_ARN}

AWS_TARGET_GROUP_ARN=$(aws elbv2 describe-target-groups \
--query 'TargetGroups[?TargetGroupName == `http4k-targets`].TargetGroupArn' \
--output text)

aws elbv2 delete-target-group \
--target-group-arn $AWS_TARGET_GROUP_ARN

AWS_VPC_SUBNET_ID_ONE=$(aws ec2 describe-subnets \
--filters "Name=tag:Name,Values=http4k-function-public-subnet-one" \
--query 'Subnets[*].SubnetId' \
--output text)

aws ec2 delete-subnet \
--subnet-id $AWS_VPC_SUBNET_ID_ONE

AWS_VPC_SUBNET_ID_TWO=$(aws ec2 describe-subnets \
--filters "Name=tag:Name,Values=http4k-function-public-subnet-two" \
--query 'Subnets[*].SubnetId' \
--output text)

aws ec2 delete-subnet \
--subnet-id $AWS_VPC_SUBNET_ID_TWO

AWS_CUSTOM_SECURITY_GROUP_ID=$(aws ec2 describe-security-groups \
--query 'SecurityGroups[?GroupName == `http4k-function-security-group`].GroupId' \
--output text)

aws ec2 delete-security-group \
--group-id $AWS_CUSTOM_SECURITY_GROUP_ID

AWS_CUSTOM_ROUTE_TABLE_ID=$(aws ec2 describe-route-tables \
--filters "Name=tag:Name,Values=http4k-routing-table" \
--query 'RouteTables[*].RouteTableId' \
--output text)

aws ec2 delete-route-table \
--route-table-id $AWS_CUSTOM_ROUTE_TABLE_ID

AWS_INTERNET_GATEWAY_ID=$(aws ec2  describe-internet-gateways \
--filters "Name=tag:Name,Values=http4k-function-internet-gateway" \
--query 'InternetGateways[*].InternetGatewayId' \
--output text)

aws ec2 detach-internet-gateway \
--internet-gateway-id $AWS_INTERNET_GATEWAY_ID \
--vpc-id $AWS_VPC_ID

aws ec2 delete-internet-gateway \
--internet-gateway-id $AWS_INTERNET_GATEWAY_ID

aws ec2  delete-vpc \
--vpc-id $AWS_VPC_ID

aws lambda  remove-permission \
--function-name http4k-function \
--statement-id elb1
