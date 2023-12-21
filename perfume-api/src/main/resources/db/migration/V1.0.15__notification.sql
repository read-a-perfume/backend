create table notification
(
    id         bigint auto_increment not null,
    content VARCHAR(255),
    redirect_url VARCHAR(255),
    receive_user_id bigint not null,
    notification_type VARCHAR(50) not null,
    is_read BOOLEAN not null,
    created_at datetime not null,
    updated_at datetime not null,
    deleted_at datetime null,
    constraint pk_notification primary key (id)
)