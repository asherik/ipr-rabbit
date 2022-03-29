#!/bin/bash

# build docker image
docker build --no-cache \
    --file docker/rabbit/Dockerfile \
    --tag webento/rabbitmq:latest \
    --tag webento/rabbitmq \
    docker/rabbit




docker push webento/rabbitmq:latest
