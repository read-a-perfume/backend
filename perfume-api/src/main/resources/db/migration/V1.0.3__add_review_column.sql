alter table review add short_review VARCHAR(255) null after situation;

alter table review add day_type VARCHAR(50) null after situation;

alter table review drop situation;

alter table review modify season VARCHAR(50) null;

alter table review modify strength VARCHAR(50) null;
