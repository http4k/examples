DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh

FUNCTION=$(aws lambda list-functions | jq -r '.Functions | .[] | select(.FunctionName == "http4k-function") | .FunctionArn')

if [ ! -z "${FUNCTION}" ] ; then
  echo "Deleting existing function..."
  aws lambda delete-function --function-name http4k-function >/dev/null
fi
