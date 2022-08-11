create database ebarter;

use ebarter;

create table user (
	id bigint not null primary key auto_increment,
    email varchar(100) not null,
    password varchar(64) not null,
    role varchar(50) not null default 'REGULAR',
    verified boolean
);

create table item (
	id bigint not null primary key auto_increment,
    category varchar(100) not null,
    title varchar(300) not null,
    owner_id bigint not null,
    details json,
    constraint ownerId_foreign_key foreign key(owner_id) references user(id)
);

drop table item;
select * from item;