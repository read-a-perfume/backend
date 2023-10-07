create table perfume_note
(
    id         bigint auto_increment not null,
    perfume_id bigint       not null,
    note_id    bigint       not null,
    note_level VARCHAR(255) not null,
    created_at datetime     not null,
    updated_at datetime     not null,
    deleted_at datetime null,
    constraint pk_perfume_note primary key (id)
);

create index idx_perfume_note_1 on perfume_note (perfume_id);

create index idx_perfume_note_2 on perfume_note (note_id);

alter table perfume
    add concentration VARCHAR(255) not null;
alter table perfume
    add perfume_shop_url VARCHAR(255) null;
alter table perfume
    add category_id bigint not null;

alter table perfume drop column strength;

alter table perfume drop column duration;
