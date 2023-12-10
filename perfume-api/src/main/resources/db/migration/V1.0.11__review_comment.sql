create table review_comment
(
    id         bigint auto_increment not null,
    review_id  bigint   not null,
    user_id    bigint   not null,
    content    varchar(512) null,
    created_at datetime not null,
    updated_at datetime not null,
    deleted_at datetime null,
    constraint pk_review_comment primary key (id)
);

create index idx_review_comment_1 on review_comment (review_id);

create index idx_review_comment_2 on review_comment (user_id);
