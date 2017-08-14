insert into user (enabled, version, password, username, email, id) values (true, 1, '$2a$10$Kn//dK2R.um1QDpi16ELKe1l99QcXTFfU3KnmpohDf5T2JNjeQzfy', 'admin', 'admin@admin.com', '2f190aa0-622a-4754-aa50-6e33ab946dcd');
insert into user (enabled, version, password, username, email, id) values (true, 1, '$2a$10$P6zp/TX0XOFkIWfi6p5XWuF7RRQ.t9O6hdL/ph6ewS2qjCgvNwfg.', 'alex', 'alex@alex.com', '19ab1bd1-cade-4c22-961c-e19d2008ff73');
insert into user (enabled, version, password, username, email, id) values (true, 1, '$2a$10$Ko7hBDLhy1NLu6ZxK1y5DO6JHLsmUC/lRhBBkPDiKk6b6cJqxVYPW', 'nikos', 'nikos@nikos.com', 'bca9a290-36ba-49ec-88e1-eb1b08d8ce49');

insert into user_roles (user_id, roles) values ('2f190aa0-622a-4754-aa50-6e33ab946dcd', 'ROLE_ADMIN');
insert into user_roles (user_id, roles) values ('2f190aa0-622a-4754-aa50-6e33ab946dcd', 'ROLE_USER');
insert into user_roles (user_id, roles) values ('19ab1bd1-cade-4c22-961c-e19d2008ff73', 'ROLE_USER');
insert into user_roles (user_id, roles) values ('bca9a290-36ba-49ec-88e1-eb1b08d8ce49', 'ROLE_USER');