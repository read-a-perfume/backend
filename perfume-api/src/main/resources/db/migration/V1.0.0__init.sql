create table if not exists member
(
    id                bigint auto_increment
        primary key,
    username          varchar(255)              not null,
    password          varchar(255)              null,
    email             varchar(255)              not null,
    name              varchar(255)              null,
    role              enum ('BUSINESS', 'USER') not null,
    business_id       bigint                    null,
    thumbnail_id      bigint                    null,
    marketing_consent bit                       not null,
    promotion_consent bit                       not null,
    created_at        datetime(6)               not null,
    updated_at        datetime(6)               not null,
    deleted_at        datetime(6)               null,
    constraint uni_email
        unique (email),
    constraint uni_username
        unique (username)
);

create index idx_business_id
    on member (business_id);

create index idx_thumbnail_id
    on member (thumbnail_id);

create table if not exists business
(
    id                  bigint auto_increment
        primary key,
    company_logo_id     bigint       null,
    company_name        varchar(255) not null,
    registration_number varchar(255) not null,
    created_at          datetime(6)  not null,
    updated_at          datetime(6)  not null,
    deleted_at          datetime(6)  null,
    constraint uni_business_1
        unique (registration_number)
);

create index idx_business_1
    on business (company_logo_id);

create table if not exists file
(
    id         bigint auto_increment
        primary key,
    url        varchar(255) null,
    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    deleted_at datetime(6)  null
);

create table if not exists authentication_key
(
    id          bigint auto_increment
        primary key,
    code        varchar(255) not null,
    sign_key    varchar(255) not null,
    verified_at datetime(6)  null,
    created_at  datetime(6)  not null,
    updated_at  datetime(6)  not null,
    deleted_at  datetime(6)  null
);
