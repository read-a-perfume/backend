create table perfume_image
(
    created_at datetime not null,
    updated_at datetime not null,
    deleted_at datetime null,
    perfume_id bigint   not null,
    image_id   bigint   not null,
    constraint pk_perfume_image primary key (perfume_id, image_id)
);

create index idx_perfume_image_1 on perfume_image (perfume_id);

create index idx_perfume_image_2 on perfume_image (image_id);
