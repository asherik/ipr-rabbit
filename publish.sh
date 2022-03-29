#!/bin/bash

# build docker image
docker build --no-cache \
    --file ./Dockerfile \
    --tag webento/rabbitmq:1.0.0 \
    --tag webento/rabbitmq \
    .

docker push webento/rabbitmq:1.0.0
