alter table member drop column name;

alter table member
    add bio varchar(255) null;
alter table member
    add birthday datetime null;
alter table member
    add sex varchar(50) null;
