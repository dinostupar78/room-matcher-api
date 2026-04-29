DROP TABLE IF EXISTS favorite_listing CASCADE;
DROP TABLE IF EXISTS refresh_token CASCADE;
DROP TABLE IF EXISTS listing_image CASCADE;
DROP TABLE IF EXISTS app_user_authority CASCADE;
DROP TABLE IF EXISTS listing CASCADE;
DROP TABLE IF EXISTS app_user CASCADE;
DROP TABLE IF EXISTS authority CASCADE;

CREATE TABLE authority (
    id BIGSERIAL PRIMARY KEY,
    role VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE app_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(254) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(150) NOT NULL,
    gender VARCHAR(50) NOT NULL,
    bio VARCHAR(1000),
    profile_image_url VARCHAR(500),
    date_of_birth DATE NOT NULL,
    registration_date TIMESTAMP NOT NULL
);

CREATE TABLE app_user_authority (
     app_user_id BIGINT NOT NULL,
     authority_id BIGINT NOT NULL,
     PRIMARY KEY (app_user_id, authority_id),
     FOREIGN KEY (app_user_id) REFERENCES app_user(id) ON DELETE CASCADE,
     FOREIGN KEY (authority_id) REFERENCES authority(id) ON DELETE CASCADE
);

CREATE TABLE listing (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    app_user_id BIGINT NOT NULL,
    address VARCHAR(200) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    size INT NOT NULL,
    description VARCHAR(1000) NOT NULL,
    available_from DATE NOT NULL,
    is_active BOOLEAN NOT NULL,
    FOREIGN KEY (app_user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

CREATE TABLE listing_image (
    id BIGSERIAL PRIMARY KEY,
    image_url VARCHAR(500) NOT NULL,
    listing_id BIGINT NOT NULL,
    FOREIGN KEY (listing_id) REFERENCES listing(id) ON DELETE CASCADE
);

CREATE TABLE refresh_token (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(500) NOT NULL UNIQUE,
    expiry_date TIMESTAMP NOT NULL,
    app_user_id BIGINT NOT NULL,
    FOREIGN KEY (app_user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

CREATE TABLE favorite_listing (
    id BIGSERIAL PRIMARY KEY,
    app_user_id BIGINT NOT NULL,
    listing_id BIGINT NOT NULL,
    FOREIGN KEY (app_user_id) REFERENCES app_user(id) ON DELETE CASCADE,
    FOREIGN KEY (listing_id) REFERENCES listing(id) ON DELETE CASCADE,
    UNIQUE (app_user_id, listing_id)
);
