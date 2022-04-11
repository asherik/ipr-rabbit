#!/bin/bash

export PROJECT_ABSOLUTE_PATH=$(pwd)
#поднять все сервисы, для экстренного пересоздания добавить флаг --force-recreate
docker-compose up -d