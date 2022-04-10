#!/bin/bash

# build docker image for producer
docker build --no-cache \
    --file docker/producer/Dockerfile \
    --tag webento/producer:latest \
    --tag webento/producer \
    docker/producer

docker push webento/producer:latest
