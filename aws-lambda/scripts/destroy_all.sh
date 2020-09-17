DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh

echo "Deleting role..."
aws iam delete-role --role-name lambda-ex

echo "Deleting lambda..."
aws lambda delete-function --function-name http4k-function
