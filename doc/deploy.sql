create
drop
database if exists springboot;
create
database springboot default character set utf8mb4 default collate utf8mb4_general_ci;

create table if not exists springboot.shedlock
(
    `name`     varchar(64)  not null,
    lock_until timestamp(3) not null,
    locked_at  timestamp(3) not null default current_timestamp(3),
    locked_by  varchar(255) not null,
    primary key (`name`)
);
