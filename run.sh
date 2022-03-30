#!/bin/bash

export PROJECT_ABSOLUTE_PATH=/Users/asher/IdeaProjects/ipr-rabbit

#удалить все остановленные старые контейнеры
docker container prune
#поднять все сервисы для экстренного пересоздания задать --force-recreate
docker-compose up -d