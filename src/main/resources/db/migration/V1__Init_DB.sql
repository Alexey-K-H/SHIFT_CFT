create table users
(
    id         bigserial    not null,
    email      varchar(255) not null,
    name       varchar(255) not null,
    password   varchar(255) not null,
    patronymic varchar(255) not null,
    surname    varchar(255) not null,
    primary key (id)
);
create table balance
(
    user_id bigserial           not null,
    funds   numeric(10, 2) not null,
    primary key (user_id)
);
create table loans
(
    id              bigserial      not null,
    date_of_receive timestamp      not null,
    debt            numeric(10, 2) not null,
    maturity        int8           not null,
    user_id         int8           not null,
    primary key (id)
);
create table passport
(
    user_id bigserial not null,
    number  varchar(255),
    series  varchar(255),
    primary key (user_id)
);
create table promo_code
(
    id    bigserial      not null,
    sum   numeric(10, 2) not null,
    value varchar(255)   not null,
    primary key (id)
);

alter table promo_code
    add constraint UK_value unique (value);
alter table balance
    add constraint FK_user_balance foreign key (user_id) references users;
alter table loans
    add constraint FK_user_loan foreign key (user_id) references users;
alter table passport
    add constraint FK_user_passport foreign key (user_id) references users;