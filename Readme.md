# Проект в рамках индивидуального плана развития сотрудника.

Проект описывает архитектуру взаимодействия продюсера и консьюмера.

## Запуск проекта

Запуск проект черз run.sh
```sh
sudo bash ./run.sh
```
После запуска будет доступно:
http://localhost:8090 для отправки запросов к главному сервису заданий (продюсеру)

Чтобы добавить задание отправить в body json следующего формата
```
POST localhost:8090/api/v1/create-task

{"text": "any text"}
```

Вход в rabbit mq
```
http://localhost:15672 
логин ipr-user
пароль ipr-pass
```

Также чтобы следить за статусами заданий можно подключиться к базе
```
Доступ:
хост: localhost
порт: 5433
база: postgres
пользователь: ipr-user
пароль: ipr-pass
```

При корректном запуске в базе будет схема taskservice


## Описание проекта

Сервис-продюсер берет рандомное задание со статусом "ожидает обработки" из базы данных (в базе поля id задачния, text - текст для обработки, status - статус задания (ожидает обработки, отправлено на обработку, ошибка, успешно)) и ложит его в очередь Rabbit Mq.

Также сервис-консюмер постоянно слушает DLQ очередь и если там появляются сообщения, вытаскивает из них id и помечает в базе задания как ошибочные (меняет статус).

Смена статуса происходит по rest запросу к главному сервису-продюсеру, так как доступ к базе имеет только главный сервис.

Сервис-консюмер получает id задания для обработки из сообщения, затем производится имитация обработки, результатом будет либо успешная обработка или нет. Также реализован механизм Dead Letter Queue (DLQ) для неуспешно обработанных сообщений, неуспешные сообщения отправляются в очередь DLQ.

Предумотрен эдпоинт для создания задания

Все сервисы запакованы в docker-контейнеры и запускаются через docker-compose .

Содержимое проекта:
1) rabbit mq брокер - 1 контейнер
2) postgre sql - 1 контейнер
3) сервис-продюсер - 1 контейнер
4) сервис-консюмер - 2 контейнера

### Доступные эндпоинты со стороны главного сервиса заданий (продюсера)

Создание задания:
```sh
POST /api/v1/create-task
Входные параметры:
json в body
Пример: {"text": "any text"}
```

Получение данных задания:
```sh
GET /api/v1/get-task-data
Входные параметры:
taskId - тип UUID
```

Изменение статуса задания:
```sh
PUT /api/v1/update-task-status
Входные параметры:
taskId - тип UUID
taskStatus - String
```

## Дополнительная информация.

### Сборка и выгрузка образов на docker-hub

Все образы уже на docker-hub, инструкции ниже по выгрузке нужны только если менялся код

Собрать и выгрузить в репозиторий образ консюмера.
```sh
sudo bash ./publish_consumer.sh
```

Собрать и выгрузить в репозиторий образ продюсера.
```sh
sudo bash ./publish_producer.sh
```
Собрать и выгрузить в репозиторий образ rabbit-mq (чаще всего это не нужно, но если есть кастомные правки, то надо выгрузить).
```sh
sudo bash ./publish_rabbit.sh
```

## Лицензия

MIT

ключ шифрования openam

P3dm9dGjkWnXNTKXWEGHBDLjY9QPqOzw

*  Port
   50389
*  Admin Port
   4444
*  JMX Port
   1689

дефолтный пароль 210236ss18ss

Агент политики по умолчанию [UrlAccessAgent] 210236ss

http://openam.mydomain.com:8082/openam/XUI/#realms
логин и пароль
amAdmin
210236ss18ss

