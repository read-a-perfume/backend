alter table review rename column feeling to full_review;

alter table review modify duration varchar(50) not null;
alter table review modify day_type varchar(50) not null;
alter table review modify strength varchar(50) not null;
alter table review modify season varchar(50) not null;
