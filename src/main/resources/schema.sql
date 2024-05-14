-- 各種テーブル削除
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS tasks;

-- categories テーブルを作成するクエリ
CREATE TABLE categories (
id INTEGER PRIMARY KEY,
name VARCHAR(20)
);

-- users テーブルを作成するクエリ
CREATE TABLE users (
id INTEGER PRIMARY KEY,
email VARCHAR(255),
name VARCHAR(20),
password VARCHAR(50)
);

-- tasks テーブルを作成するクエリ
CREATE TABLE tasks (
id INTEGER PRIMARY KEY,
category_id INTEGER,
user_id INTEGER,
title VARCHAR(255),
closing_date DATE,
progress INTEGER,
memo TEXT
);
