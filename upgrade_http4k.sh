#!/bin/bash
set -e

NEW_VERSION=$1

function upgrade() {
    TARGET=$1
    VERSION=$2
    cat "$TARGET" | grep -v "http4k_version" > out.txt
    echo "http4k_version=$VERSION" >> out.txt
    mv out.txt "$TARGET"
}

export -f upgrade

find . -name gradle.properties -exec bash -c "upgrade {} $NEW_VERSION" bash {} \;
