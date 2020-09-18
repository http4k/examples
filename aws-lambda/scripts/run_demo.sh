DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. $DIR/common.sh

echo "Step 1 - Build"
$DIR/lambda_build.sh

echo "Step 2 - Deploy"
$DIR/ensure_role_exists.sh
$DIR/ensure_function_does_not_exist.sh
$DIR/lambda_upload.sh

echo "Step 3 - Invoke the new lambda directly"
$DIR/lambda_invoke.sh

echo "Step 4 - Create API"
$DIR/api_create.sh
