# rabbitmq-mqtt-docker
RabbitMQ Dockerfile with default configuration to run MQTT Broker

# Build and Run 
```
$ docker build -t webento/ipr-rabbit:latest .
$ docker push webento/ipr-rabbit:latest
$ docker run -it \
    -p 15672:15672 -p 5672:5672 -p 1883:1883 \
    -v $PWD/docker/var/lib/rabbitmq:/var/lib/rabbitmq \
    webento/ipr-rabbit:latest
```
Admin interface will be available at localhost:15672 (admin:admin)
