# webHello
[![Build Status](https://travis-ci.org/DarkEagleGH/Hello.svg?branch=master)](https://travis-ci.org/DarkEagleGH/Hello)

REST with Spring Boot
PostgreSQL 9.4
Vagrant 1.9.0 (minimal/trusty64)+ VirtualBox 5.1.10
Maven

// Запуск
1. В корневом каталоге проекта выполнить
    - vagrant up
    - java -jar webhello-0.0.1-SNAPSHOT.jar
2. В браузере открыть http://127.0.0.1:8080/hello/contacts?nameFilter=[regexp], например:
    - http://127.0.0.1:8080/hello/contacts?nameFilter=^A.*$
    - http://127.0.0.1:8080/hello/contacts?nameFilter=^.*[aei].*$

// Первый запуск и vagrant занимает 3-6 минут. Объем виртуально машины - 0.5 - 3 Гб.
   Дополнительные настройки /vagrant-setup/
   Тестовая база PostgreSQL содержит 6 791 139 записей.
   