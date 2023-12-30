create table magazine
(
    id                 bigint auto_increment not null,
    title              VARCHAR(255),
    sub_title          VARCHAR(255),
    content            VARCHAR(255),
    cover_thumbnail_id bigint,
    thumbnail_id       bigint,
    user_id            bigint not null,
    brand_id           bigint not null,
    created_at         datetime not null,
    updated_at         datetime not null,
    deleted_at         datetime null,
    constraint pk_notification primary key (id)
)