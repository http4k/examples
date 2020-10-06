DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh

FUNCTION=$(aws lambda list-functions | jq -r '.Functions | .[] | select(.FunctionName == "http4k-function") | .FunctionArn')

if [  -z "${FUNCTION}" ] ; then
  echo "Function does not exist."
  exit -1
fi

AWS_VPC_ID=$(aws ec2 create-vpc \
--cidr-block 10.0.0.0/16 \
--query 'Vpc.{VpcId:VpcId}' \
--output text)

aws ec2 modify-vpc-attribute \
--vpc-id $AWS_VPC_ID \
--enable-dns-hostnames "{\"Value\":true}"

aws ec2 create-tags \
--resources $AWS_VPC_ID \
--tags "Key=Name,Value=http4k-function-vpc"

AWS_SUBNET_PUBLIC_ONE_ID=$(aws ec2 create-subnet \
--vpc-id $AWS_VPC_ID --cidr-block 10.0.1.0/24 \
--availability-zone  eu-west-2a --query 'Subnet.{SubnetId:SubnetId}' \
--output text)

AWS_SUBNET_PUBLIC_TWO_ID=$(aws ec2 create-subnet \
--vpc-id $AWS_VPC_ID --cidr-block 10.0.2.0/24 \
--availability-zone  eu-west-2b --query 'Subnet.{SubnetId:SubnetId}' \
--output text)

aws ec2 modify-subnet-attribute \
  --subnet-id $AWS_SUBNET_PUBLIC_ONE_ID \
  --map-public-ip-on-launch

aws ec2 modify-subnet-attribute \
  --subnet-id $AWS_SUBNET_PUBLIC_TWO_ID \
  --map-public-ip-on-launch

aws ec2 create-tags \
--resources $AWS_SUBNET_PUBLIC_ONE_ID \
--tags "Key=Name,Value=http4k-function-public-subnet-one"

aws ec2 create-tags \
--resources $AWS_SUBNET_PUBLIC_TWO_ID \
--tags "Key=Name,Value=http4k-function-public-subnet-two"

AWS_INTERNET_GATEWAY_ID=$(aws ec2 create-internet-gateway \
--query 'InternetGateway.{InternetGatewayId:InternetGatewayId}' \
--output text)

aws ec2 attach-internet-gateway \
--vpc-id $AWS_VPC_ID \
--internet-gateway-id $AWS_INTERNET_GATEWAY_ID

aws ec2 create-tags \
--resources $AWS_INTERNET_GATEWAY_ID \
--tags "Key=Name,Value=http4k-function-internet-gateway"

AWS_CUSTOM_ROUTE_TABLE_ID=$(aws ec2 create-route-table \
  --vpc-id $AWS_VPC_ID \
  --query 'RouteTable.{RouteTableId:RouteTableId}' \
  --output text )

aws ec2 create-tags \
--resources $AWS_CUSTOM_ROUTE_TABLE_ID \
--tags "Key=Name,Value=http4k-routing-table"

aws ec2 create-route \
--route-table-id $AWS_CUSTOM_ROUTE_TABLE_ID \
--destination-cidr-block 0.0.0.0/0 \
--gateway-id $AWS_INTERNET_GATEWAY_ID

AWS_ROUTE_TABLE_ASSOID_ONE=$(aws ec2 associate-route-table  \
--subnet-id $AWS_SUBNET_PUBLIC_ONE_ID \
--route-table-id $AWS_CUSTOM_ROUTE_TABLE_ID \
--query 'AssociationId' \
--output text)

AWS_ROUTE_TABLE_ASSOID_TWO=$(aws ec2 associate-route-table  \
--subnet-id $AWS_SUBNET_PUBLIC_TWO_ID \
--route-table-id $AWS_CUSTOM_ROUTE_TABLE_ID \
--query 'AssociationId' \
--output text)

aws ec2 create-security-group \
--vpc-id $AWS_VPC_ID \
--group-name http4k-function-security-group \
--description 'My VPC non default security group'

AWS_DEFAULT_SECURITY_GROUP_ID=$(aws ec2 describe-security-groups \
--filters "Name=vpc-id,Values=$AWS_VPC_ID" \
--query 'SecurityGroups[?GroupName == `default`].GroupId' \
--output text) &&
AWS_CUSTOM_SECURITY_GROUP_ID=$(aws ec2 describe-security-groups \
--filters "Name=vpc-id,Values=$AWS_VPC_ID" \
--query 'SecurityGroups[?GroupName == `http4k-function-security-group`].GroupId' \
--output text)

aws ec2 authorize-security-group-ingress \
--group-id $AWS_CUSTOM_SECURITY_GROUP_ID \
--ip-permissions '[{"IpProtocol": "tcp", "FromPort": 22, "ToPort": 22, "IpRanges": [{"CidrIp": "0.0.0.0/0", "Description": "Allow SSH"}]}]' &&
aws ec2 authorize-security-group-ingress \
--group-id $AWS_CUSTOM_SECURITY_GROUP_ID \
--ip-permissions '[{"IpProtocol": "tcp", "FromPort": 80, "ToPort": 80, "IpRanges": [{"CidrIp": "0.0.0.0/0", "Description": "Allow HTTP"}]}]'

AWS_LB_ARN=$(aws elbv2 create-load-balancer \
--name http4k-load-balancer  \
--subnets $AWS_SUBNET_PUBLIC_ONE_ID $AWS_SUBNET_PUBLIC_TWO_ID --security-groups $AWS_CUSTOM_SECURITY_GROUP_ID \
--query 'LoadBalancers[?LoadBalancerName == `http4k-load-balancer`].LoadBalancerArn' \
--output text)

AWS_TARGET_GROUP_ARN=$(aws elbv2 create-target-group \
--name http4k-targets \
--target-type lambda \
--query 'TargetGroups[?TargetGroupName == `http4k-targets`].TargetGroupArn' \
--output text)

aws lambda add-permission \
--function-name $FUNCTION \
--statement-id elb1 \
--principal elasticloadbalancing.amazonaws.com \
--action lambda:InvokeFunction \
--source-arn $AWS_TARGET_GROUP_ARN

aws elbv2 register-targets \
--target-group-arn $AWS_TARGET_GROUP_ARN \
--targets Id=$FUNCTION

aws elbv2 create-listener \
--load-balancer-arn $AWS_LB_ARN \
--protocol HTTP --port 80  \
--default-actions Type=forward,TargetGroupArn=$AWS_TARGET_GROUP_ARN

