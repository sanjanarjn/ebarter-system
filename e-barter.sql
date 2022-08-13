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

create table item_category (
	id bigint not null primary key auto_increment,
    name varchar(300) NOT NULL,
	created_time datetime not null,
    modified_time datetime not null
);


create table item (
	id bigint not null primary key auto_increment,
    category_id bigint not null,
    title varchar(300) not null,
    owner_id bigint not null,
    details json,
    created_time datetime not null,
    modified_time datetime not null,
    constraint ownerId_foreign_key foreign key(owner_id) references user(id)
);

alter table item add column availability_status varchar(50) NOT NULL;
alter table item add constraint
category_id_foreign_key foreign key(category_id) references item_category(id);

create table item_category (
	id bigint not null primary key auto_increment,
    name varchar(300) NOT NULL
);

create table verification_otp (
	id bigint not null primary key auto_increment,
    user_id bigint not null,
    expiry_date date not null,
    token varchar(100),
    constraint user_id_foreign_key foreign key(user_id) references user(id)
);

create table exchange (
	id bigint not null primary key auto_increment,
    initiated_user_id bigint not null,
    created_time datetime not null,
    modified_time datetime not null
);

alter table exchange 
add constraint initiated_user_id_foreign_key 
foreign key(initiated_user_id) references user(id);

create table exchange_transaction (
	id bigint not null primary key auto_increment,
    borrower bigint not null,
    lender bigint not null,
    item_id bigint not null,
    exchange_id bigint not null,
    status varchar(50) not null,
    created_time datetime not null,
    modified_time datetime not null,
    constraint exchange_id_foreign_key foreign key(exchange_id) references exchange(id),
    constraint borrower_foreign_key foreign key(borrower) references user(id),
    constraint lender_foreign_key foreign key(lender) references user(id)
);

alter table exchange_transaction 
add constraint item_id_foreign_key 
foreign key(item_id) references item(id);

use ebarter;

