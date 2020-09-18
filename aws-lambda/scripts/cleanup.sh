DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh


API=$(aws apigatewayv2 get-apis |  jq -r ' .Items | .[] | select(.Name == "http4k-demo") | .ApiId')
if [ ! -z "${API}" ] ; then
    echo "Deleting API..."
    aws apigatewayv2 delete-api --api-id $API
fi

FUNCTION=$(aws lambda list-functions | jq -r '.Functions | .[] | select(.FunctionName == "http4k-function") | .FunctionArn')
if [ ! -z "${FUNCTION}" ] ; then
    echo "Deleting lambda..."
    aws lambda delete-function --function-name http4k-function
fi

ROLE=$(aws iam list-roles | jq  '.Roles | .[] | select(.RoleName =="lambda-ex")')
if [ ! -z "${ROLE}" ] ; then
    echo "Deleting role..."
    aws iam detach-role-policy --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole --role-name lambda-ex
    aws iam delete-role --role-name lambda-ex
fi
