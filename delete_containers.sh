#!/bin/bash

export PROJECT_ABSOLUTE_PATH=$(pwd)
docker-compose stop
docker-compose rm -f