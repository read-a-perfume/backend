create table review
(
    id         bigint AUTO_INCREMENT not null,
    feeling    VARCHAR(255) null,
    situation  VARCHAR(255) null,
    strength   INT null,
    duration   bigint null,
    season     INT null,
    perfume_id bigint   not null,
    user_id    bigint   not null,
    created_at datetime not null,
    updated_at datetime not null,
    deleted_at datetime null,
    constraint pk_review primary key (id)
);

alter table review
    add index idx_review_1 (perfume_id);
alter table review
    add index idx_review_2 (user_id);

create table review_tag
(
    review_id  bigint   not null,
    tag_id     bigint   not null,
    created_at datetime not null,
    updated_at datetime not null,
    deleted_at datetime null,
    constraint pk_review_tag primary key (review_id, tag_id)
);

alter table review_tag
    add index idx_review_tag_1 (review_id);
alter table review_tag
    add index idx_review_tag_2 (tag_id);

create table tag
(
    id         bigint AUTO_INCREMENT not null,
    name       VARCHAR(255) null,
    created_at datetime not null,
    updated_at datetime not null,
    deleted_at datetime null,
    constraint pk_tag primary key (id)
);

alter table tag
    add constraint uni_tag_name unique (name);
