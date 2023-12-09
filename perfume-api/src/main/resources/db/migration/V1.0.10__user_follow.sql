create table user_follow
(
    id           bigint auto_increment not null,
    follower_id  bigint not null,
    following_id bigint not null,
    created_at   datetime not null,
    updated_at   datetime not null,
    deleted_at   datetime null,
    constraint pk_user_follow primary key (id)
);