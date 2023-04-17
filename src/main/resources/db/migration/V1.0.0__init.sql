CREATE TABLE IF NOT EXISTS organization (
    id BINARY(16) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(255) NOT NULL,
    catalog_name varchar(255) NOT NULL,
    CONSTRAINT organization_PK PRIMARY KEY (id),
    CONSTRAINT catalog_name_uk UNIQUE KEY (catalog_name)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `user` (
	id BINARY(16) NOT NULL,
	created_at TIMESTAMP NOT NULL,
	name VARCHAR(255) NOT NULL,
	password varchar(255) NOT NULL,
    organization_id BINARY(16) NOT NULL,
	CONSTRAINT user_PK PRIMARY KEY (id),
    CONSTRAINT user_name_uk UNIQUE KEY (name),
    CONSTRAINT user_FK FOREIGN KEY (organization_id) REFERENCES organization(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE board (
    id BINARY(16) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    title varchar(255) NOT NULL,
    content varchar(255) NOT NULL,
    user_id BINARY(16) NOT NULL,
    CONSTRAINT board_PK PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE INDEX board_user_id_IDX USING BTREE ON board (user_id);
