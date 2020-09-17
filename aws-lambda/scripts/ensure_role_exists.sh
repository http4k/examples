DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh

ROLES=$(aws iam list-roles | jq  '.Roles | .[] | select(.RoleName =="lambda-ex")')

if [ -z "${ROLES}" ] ; then
  echo "Creating lambda execution role..."
  aws iam create-role --role-name lambda-ex --assume-role-policy-document file://$DIR/trust-policy.json 1>/dev/null
  aws iam attach-role-policy --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole --role-name lambda-ex
  sleep 10
else
  echo "Role already exists. Skipping..."
fi
