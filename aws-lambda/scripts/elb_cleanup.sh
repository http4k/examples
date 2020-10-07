DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh

AWS_VPC_ID=$(aws ec2 describe-vpcs \
 --query 'Vpcs[?Tags[?Key == `Name` && Value == `http4k-function-vpc`]]. VpcId' \
 --output text)

AWS_LB_ARN=$(aws elbv2 describe-load-balancers \
--query 'LoadBalancers[?LoadBalancerName == `http4k-load-balancer`].LoadBalancerArn' \
--output text)

if [ ! -z "${AWS_LB_ARN}" ]; then
    echo "Deleting load balancer $AWS_LB_ARN"
    aws elbv2 delete-load-balancer \
    --load-balancer-arn ${AWS_LB_ARN}
fi

AWS_TARGET_GROUP_ARN=$(aws elbv2 describe-target-groups \
--query 'TargetGroups[?TargetGroupName == `http4k-targets`].TargetGroupArn' \
--output text)

if [ ! -z "${AWS_TARGET_GROUP_ARN}" ]; then
    echo "Deleting target group ${AWS_TARGET_GROUP_ARN}"
     aws elbv2 delete-target-group \
    --target-group-arn $AWS_TARGET_GROUP_ARN
fi

AWS_VPC_SUBNET_ID_ONE=$(aws ec2 describe-subnets \
--filters "Name=tag:Name,Values=http4k-function-public-subnet-one" \
--query 'Subnets[*].SubnetId' \
--output text)

if [ ! -z "${AWS_VPC_SUBNET_ID_ONE}" ] ; then
    echo "Deleting subnet ${AWS_VPC_SUBNET_ID_ONE}..."
    aws ec2 delete-subnet \
    --subnet-id $AWS_VPC_SUBNET_ID_ONE
fi

AWS_VPC_SUBNET_ID_TWO=$(aws ec2 describe-subnets \
--filters "Name=tag:Name,Values=http4k-function-public-subnet-two" \
--query 'Subnets[*].SubnetId' \
--output text)

if [ ! -z "${AWS_VPC_SUBNET_ID_TWO}" ] ; then
    echo "Deleting subnet ${AWS_VPC_SUBNET_ID_TWO}..."
    aws ec2 delete-subnet \
    --subnet-id $AWS_VPC_SUBNET_ID_TWO
fi

AWS_CUSTOM_SECURITY_GROUP_ID=$(aws ec2 describe-security-groups \
--query 'SecurityGroups[?GroupName == `http4k-function-security-group`].GroupId' \
--output text)

if [ ! -z "${AWS_CUSTOM_SECURITY_GROUP_ID}" ] ; then
    echo "Deleting security group ${AWS_CUSTOM_SECURITY_GROUP_ID}..."
    aws ec2 delete-security-group \
    --group-id $AWS_CUSTOM_SECURITY_GROUP_ID
fi

AWS_CUSTOM_ROUTE_TABLE_ID=$(aws ec2 describe-route-tables \
--filters "Name=tag:Name,Values=http4k-routing-table" \
--query 'RouteTables[*].RouteTableId' \
--output text)

if [ ! -z "${AWS_CUSTOM_ROUTE_TABLE_ID}" ] ; then
    echo "Deleting routing table ${AWS_CUSTOM_ROUTE_TABLE_ID}..."
    aws ec2 delete-route-table \
    --route-table-id $AWS_CUSTOM_ROUTE_TABLE_ID
fi

AWS_INTERNET_GATEWAY_ID=$(aws ec2  describe-internet-gateways \
--filters "Name=tag:Name,Values=http4k-function-internet-gateway" \
--query 'InternetGateways[*].InternetGatewayId' \
--output text)

if [ ! -z "${AWS_VPC_ID}" ] && [ ! -z "${AWS_INTERNET_GATEWAY_ID}" ] ; then
    echo "Detaching internet gateway ${AWS_INTERNET_GATEWAY_ID} from ${AWS_VPC_ID}..."
    aws ec2 detach-internet-gateway \
    --internet-gateway-id $AWS_INTERNET_GATEWAY_ID \
    --vpc-id $AWS_VPC_ID

    echo "Deleting internet gateway ${AWS_INTERNET_GATEWAY_ID}..."
    aws ec2 delete-internet-gateway \
    --internet-gateway-id $AWS_INTERNET_GATEWAY_ID

    echo "Deleting VPC ${AWS_VPC_ID}..."
    aws ec2  delete-vpc \
    --vpc-id $AWS_VPC_ID
fi

echo "Removing lambda permission"
aws lambda  remove-permission \
--function-name http4k-function \
--statement-id elb1

echo "Done."
