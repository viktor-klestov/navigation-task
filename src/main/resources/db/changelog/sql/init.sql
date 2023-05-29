----liquibase formatted sql
--changeset klestov_v:1

create table base_station
(
    id                         uuid    not null primary key,
    name                       text,
    x                          numeric not null,
    y                          numeric not null,
    detection_radius_in_meters numeric not null
);

create table mobile_station
(
    id                  uuid    not null primary key,
    last_known_x        numeric not null,
    last_known_y        numeric not null,
    last_known_accuracy numeric not null
);

--changeset klestov_v:1

insert into base_station
values ('f1d2c8d9-11b9-4796-8d62-3c16119e4dac', 'test station #1', 500, 500, 30),
       ('ffd93d1a-a022-447d-94fa-ccc7d2c6f0bb', 'test station #2', 1500, 1500, 50);