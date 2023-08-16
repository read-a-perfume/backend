CREATE TABLE authentication_key
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime     NOT NULL,
    updated_at  datetime     NOT NULL,
    deleted_at  datetime NULL,
    code        VARCHAR(255) NOT NULL,
    sign_key    VARCHAR(255) NOT NULL,
    verified_at datetime NULL,
    CONSTRAINT pk_authentication_key PRIMARY KEY (id)
);

CREATE TABLE brand
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    created_at   datetime NOT NULL,
    updated_at   datetime NOT NULL,
    deleted_at   datetime NULL,
    name         VARCHAR(255) NULL,
    story        VARCHAR(255) NULL,
    thumbnail_id BIGINT NULL,
    CONSTRAINT pk_brand PRIMARY KEY (id)
);

CREATE TABLE business
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    created_at          datetime     NOT NULL,
    updated_at          datetime     NOT NULL,
    deleted_at          datetime NULL,
    company_name        VARCHAR(255) NOT NULL,
    registration_number VARCHAR(255) NOT NULL,
    company_logo_id     BIGINT NULL,
    CONSTRAINT pk_business PRIMARY KEY (id)
);

ALTER TABLE business
    ADD CONSTRAINT uni_business_1 UNIQUE (registration_number);

CREATE INDEX idx_business_1 ON business (company_logo_id);

CREATE TABLE file
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime NOT NULL,
    updated_at datetime NOT NULL,
    deleted_at datetime NULL,
    url        VARCHAR(255) NULL,
    CONSTRAINT pk_file PRIMARY KEY (id)
);

CREATE TABLE category
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_at    datetime     NOT NULL,
    updated_at    datetime     NOT NULL,
    deleted_at    datetime NULL,
    name          VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    thumbnail_id  BIGINT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

ALTER TABLE category
    ADD CONSTRAINT uni_category_name UNIQUE (name);

CREATE TABLE category_user
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  datetime NOT NULL,
    updated_at  datetime NOT NULL,
    deleted_at  datetime NULL,
    category_id BIGINT   NOT NULL,
    user_id     BIGINT   NOT NULL,
    CONSTRAINT pk_category_user PRIMARY KEY (id)
);

ALTER TABLE category_user
    ADD CONSTRAINT uni_user_id_note_id UNIQUE (user_id, category_id);

CREATE TABLE note
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_at    datetime     NOT NULL,
    updated_at    datetime     NOT NULL,
    deleted_at    datetime NULL,
    name          VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    thumbnail_id  BIGINT NULL,
    CONSTRAINT pk_note PRIMARY KEY (id)
);

ALTER TABLE note
    ADD CONSTRAINT uni_note_name UNIQUE (name);

CREATE TABLE perfume
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    created_at   datetime     NOT NULL,
    updated_at   datetime     NOT NULL,
    deleted_at   datetime NULL,
    name         VARCHAR(255) NOT NULL,
    story        VARCHAR(255) NOT NULL,
    strength     VARCHAR(255) NULL,
    duration     VARCHAR(255) NULL,
    price        BIGINT NULL,
    capacity     BIGINT NULL,
    brand_id     BIGINT       NOT NULL,
    thumbnail_id BIGINT NULL,
    CONSTRAINT pk_perfume PRIMARY KEY (id)
);

CREATE TABLE member
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    created_at        datetime     NOT NULL,
    updated_at        datetime     NOT NULL,
    deleted_at        datetime NULL,
    username          VARCHAR(255) NOT NULL,
    email             VARCHAR(255) NOT NULL,
    password          VARCHAR(255) NULL,
    name              VARCHAR(255) NULL,
    `role`            VARCHAR(255) NOT NULL,
    marketing_consent BIT(1)       NOT NULL,
    promotion_consent BIT(1)       NOT NULL,
    business_id       BIGINT NULL,
    thumbnail_id      BIGINT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE member
    ADD CONSTRAINT uni_email UNIQUE (email);

ALTER TABLE member
    ADD CONSTRAINT uni_username UNIQUE (username);

CREATE INDEX idx_business_id ON member (business_id);

CREATE INDEX idx_thumbnail_id ON member (thumbnail_id);

CREATE TABLE social_account
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime     NOT NULL,
    updated_at      datetime     NOT NULL,
    deleted_at      datetime NULL,
    identifier      VARCHAR(255) NOT NULL,
    email           VARCHAR(255) NOT NULL,
    social_provider VARCHAR(255) NOT NULL,
    user_id         BIGINT NULL,
    CONSTRAINT pk_social_account PRIMARY KEY (id)
);

ALTER TABLE social_account
    ADD CONSTRAINT uni_social_account_1 UNIQUE (identifier);
