DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh

API=$(aws apigatewayv2 get-apis |  jq -r ' .Items | .[] | select(.Name == "http4k-demo") | .ApiId')
FUNCTION=$(aws lambda list-functions | jq -r '.Functions | .[] | select(.FunctionName == "http4k-function") | .FunctionArn')

if [ -z "${FUNCTION}" ] ; then
    echo "Function does not exist."
    exit -1
fi

if [ -z "${API}" ] ; then
    API_ID="$(aws apigatewayv2 create-api --name 'http4k-demo'  --protocol-type HTTP | jq -r '.ApiId')"

    aws apigatewayv2 create-stage \
    --api-id "${API_ID}" \
    --stage-name \$default \
    --auto-deploy 1>/dev/null

    INTEGRATION_ID="$(aws apigatewayv2 create-integration \
        --api-id "${API_ID}"  \
        --integration-type AWS_PROXY \
        --integration-uri ${FUNCTION} \
        --timeout-in-millis 30000 \
        --payload-format-version 1.0 | jq -r .IntegrationId)" 1>/dev/null

    aws apigatewayv2 create-route \
        --api-id "${API_ID}" \
        --route-key '$default' \
        --target "integrations/${INTEGRATION_ID}" 1>/dev/null

    ENDPOINT="$( aws apigatewayv2 get-apis |  jq -r ' .Items | .[] | select(.Name == "http4k-demo") | .ApiEndpoint')"

    echo "Access the API in: $ENDPOINT"
else
    echo "API already exists"
fi


