#!/bin/bash

./gradle clean test distZip

docker build . -t helloworld

docker run -p 8080:8080 helloworld
