#!/bin/bash

# build docker image for consumer
docker build --no-cache \
    --file docker/consumer/Dockerfile \
    --tag webento/consumer:latest \
    --tag webento/consumer \
    docker/consumer

docker push webento/consumer:latest
