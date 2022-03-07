create schema if not exists cep_campaign;
create schema if not exists cep_points;

create table cep_campaign.product (id int4 not null, name varchar(255) not null, price numeric(19, 2) not null, primary key (id));
INSERT INTO cep_campaign.product VALUES (1, 'Product1', 12.20);
INSERT INTO cep_campaign.product VALUES (2, 'Product2', 401.99);
INSERT INTO cep_campaign.product VALUES (3, 'Product3', 54.99);
INSERT INTO cep_campaign.product VALUES (4, 'Product4', 176.00);
INSERT INTO cep_campaign.product VALUES (5, 'Product5', 17.00);
INSERT INTO cep_campaign.product VALUES (6, 'Product6', 19.22);
INSERT INTO cep_campaign.product VALUES (7, 'Product7', 30.99);
INSERT INTO cep_campaign.product VALUES (8, 'Product8', 29.99);
INSERT INTO cep_campaign.product VALUES (9, 'Product9', 8.99);
INSERT INTO cep_campaign.product VALUES (10, 'Product10', 5.09);

create table cep_points.customer (id int4 not null, email varchar(255) not null, points_balance numeric default 0 not null, primary key (id));
INSERT INTO cep_points.customer VALUES (1, 'user1@email.com', 0.00);
INSERT INTO cep_points.customer VALUES (2, 'user2@email.com', 0.00);
INSERT INTO cep_points.customer VALUES (3, 'user3@email.com', 0.00);
INSERT INTO cep_points.customer VALUES (4, 'user4@email.com', 0.00);
INSERT INTO cep_points.customer VALUES (5, 'user5@email.com', 0.00);
INSERT INTO cep_points.customer VALUES (6, 'user6@email.com', 0.00);
INSERT INTO cep_points.customer VALUES (7, 'user7@email.com', 0.00);
INSERT INTO cep_points.customer VALUES (8, 'user8@email.com', 0.00);
INSERT INTO cep_points.customer VALUES (9, 'user9@email.com', 0.00);
INSERT INTO cep_points.customer VALUES (10, 'user10@email.com', 0.00);

