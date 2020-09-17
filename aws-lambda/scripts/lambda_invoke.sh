DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh

aws lambda invoke --function-name http4k-function \
--payload file://$DIR/payload.json \
--cli-binary-format raw-in-base64-out \
/tmp/http4k-function-response.json 1> /dev/null


echo "$(cat /tmp/http4k-function-response.json)"


