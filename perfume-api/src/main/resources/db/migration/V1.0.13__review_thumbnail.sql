create table review_thumbnail
(
    created_at   datetime not null,
    updated_at   datetime not null,
    deleted_at   datetime null,
    review_id    bigint   not null,
    thumbnail_id bigint   not null,
    constraint pk_review_thumbnail primary key (review_id, thumbnail_id)
);

create index idx_review_thumbnail_1 on review_thumbnail (review_id);

create index idx_review_thumbnail_2 on review_thumbnail (thumbnail_id);
