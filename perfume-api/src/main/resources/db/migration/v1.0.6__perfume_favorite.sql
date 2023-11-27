create table perfume_favorite
(
    user_id bigint not null,
    perfume_id bigint notnull,
    created_at datetime not null,
    updated_at datetime not null,
    deleted_at datetime null,
    constraint pk_perfume_favorite primary key (user_id, perfume_id)
);