#!/bin/bash

# build docker image
docker build --no-cache \
    --file ./Dockerfile \
    --tag pedrozc90/rabbitmq:1.0.0 \
    --tag pedrozc90/rabbitmq \
    .

# push to docker hub
docker push pedrozc90/rabbitmq

docker push pedrozc90/rabbitmq:1.0.0
