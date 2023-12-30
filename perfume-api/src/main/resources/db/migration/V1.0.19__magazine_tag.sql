create table magazine_tag
(
    id         bigint AUTO_INCREMENT not null,
    name       varchar(255) not null,
    created_at datetime     not null,
    updated_at datetime     not null,
    deleted_at datetime null,
    constraint pk_magazine_tag primary key (id)
);

create table brand_magazine_tag
(
    magazine_id bigint   not null,
    tag_id      bigint   not null,
    created_at  datetime not null,
    updated_at  datetime not null,
    deleted_at  datetime null,
    constraint pk_brand_magazine_tag primary key (magazine_id, tag_id)
);
