This project is at URL:
    https://jstobigdata.com/spring/spring-webflux-rest-api-with-mongodb-and-spring-data/

It uses docker and docker compose, which is part of docker desktop in Windows 10.

create docker-compose.yml in resources directory, like following:

    # Mongo docker image
    version: '3.7'
    services:
      mongo:
        image: 'mongo:4.4.0-rc8-bionic'
        restart: on-failure
        ports:
        - 27017:27017
        volumes:
        - ./.mongo-volume:/data/db
        environment:
          MONGO_INITDB_ROOT_USERNAME: root
          MONGO_INITDB_ROOT_PASSWORD: root@123                       # I put my password into it.
      mongo-express:
        image: mongo-express
        restart: always
        ports:
          - 8081:8081
        environment:
          ME_CONFIG_MONGODB_ADMINUSERNAME: root
          ME_CONFIG_MONGODB_ADMINPASSWORD: root@123

then in terminal, cd to the resource directory, run following command:
docker-compose up


configure the application.yml with MongoDB details

