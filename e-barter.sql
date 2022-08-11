create database ebarter;

use ebarter;

create table user (
	id bigint not null primary key auto_increment,
    email varchar(100) not null,
    password varchar(64) not null,
    role varchar(50) not null default 'REGULAR',
    verified boolean
);

select * from user;