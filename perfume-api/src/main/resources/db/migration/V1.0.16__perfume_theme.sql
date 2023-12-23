create table perfume_theme(
    id int primary key auto_increment,
    title varchar(100) not null,
    content varchar(1000) not null,
    thumbnail_id bigint not null,
    perfume_ids varchar(255) not null,
    created_at   datetime not null,
    updated_at   datetime not null,
    deleted_at   datetime null
);
