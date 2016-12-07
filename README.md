# webHello
[![Build Status](https://travis-ci.org/DarkEagleGH/Hello.svg?branch=master)](https://travis-ci.org/DarkEagleGH/Hello)

REST with Spring Boot
PostgreSQL 9.4
Vagrant 1.9.0 (minimal/trusty64)+ VirtualBox 5.1.10
Maven

// Запуск
- в корневом каталоге проекта выполнить
    vagrant up
    java -jar webhello-0.0.1-SNAPSHOT.jar
- в браузере открыть http://127.0.0.1:8080/hello/contacts?nameFilter=

