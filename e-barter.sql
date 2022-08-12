create database ebarter;

use ebarter;

create table user (
	id bigint not null primary key auto_increment,
    email varchar(100) not null,
    password varchar(64) not null,
    role varchar(50) not null default 'REGULAR',
    verified boolean,
    created_time datetime not null,
    modified_time datetime not null
);

create table item (
	id bigint not null primary key auto_increment,
    category varchar(100) not null,
    title varchar(300) not null,
    owner_id bigint not null,
    details json,
    created_time datetime not null,
    modified_time datetime not null,
    constraint ownerId_foreign_key foreign key(owner_id) references user(id)
);

create table verification_otp (
	id bigint not null primary key auto_increment,
    user_id bigint not null,
    expiry_date date not null,
    token varchar(100),
    constraint user_id_foreign_key foreign key(user_id) references user(id)
);

create table exchange_request (
	id bigint not null primary key auto_increment,
    initiated_user_id bigint not null,
    created_time datetime not null,
    modified_time datetime not null
);

create table exchange_transaction (
	id bigint not null primary key auto_increment,
    borrower bigint not null,
    lender bigint not null,
    item_id bigint not null,
    request_id bigint not null,
    status varchar(50) not null,
    created_time datetime not null,
    modified_time datetime not null,
    constraint request_id_foreign_key foreign key(request_id) references exchange_request(id),
    constraint borrower_foreign_key foreign key(borrower) references user(id),
    constraint lender_foreign_key foreign key(lender) references user(id)
);
drop table exchange_transaction;
use ebarter;

