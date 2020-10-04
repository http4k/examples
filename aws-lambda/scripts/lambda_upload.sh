DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh

ROLE=$(aws iam list-roles | jq -r '.Roles | .[] | select(.RoleName =="lambda-ex") | .Arn')
FILE="${DIR}/../build/distributions/http4k-aws-lambda-example-1.0-SNAPSHOT.zip"

if [ ! -f $FILE ]; then
    echo "Lambda zip file not found: $FILE\n\n Run ./gradlew buildZip to create it."
    exit -1
fi

aws lambda create-function --function-name http4k-function \
--zip-file fileb://$FILE \
--handler org.http4k.example.MyHttp4kFunction \
--runtime java11 \
--role $ROLE 1>/dev/null

aws lambda add-permission \
--function-name http4k-function \
--action lambda:InvokeFunction \
--statement-id apigateway \
--principal apigateway.amazonaws.com 1>/dev/null
