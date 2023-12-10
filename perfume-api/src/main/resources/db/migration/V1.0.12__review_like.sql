create table review_like
(
    id         bigint auto_increment not null,
    user_id    bigint                not null,
    review_id  bigint                not null,
    created_at datetime              not null,
    updated_at datetime              not null,
    deleted_at datetime              null,
    constraint pk_review_like primary key (id)
);

create index idx_review_like_1 on review_like (review_id);

create index idx_review_like_2 on review_like (user_id);
