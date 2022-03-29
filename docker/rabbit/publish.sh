#!/bin/bash

# build docker image
docker build --no-cache \
    --file ./Dockerfile \
    --tag webento/rabbitmq:latest \
    --tag webento/rabbitmq \
    .

docker push webento/rabbitmq:latest
