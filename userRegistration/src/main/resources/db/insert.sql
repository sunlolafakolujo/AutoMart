INSERT INTO address(house_number, street_name, city, land_mark, state_province, country)
VALUES ('2', 'Akeju Street, Alagomeji', 'Ebute Metta', 'Alagomeji', 'Lagos', 'Nigeria'),
       ('2A', 'Adegboruwa Akeju Street, Yaba', 'Surulere', 'Eric Moore', 'Lagos', 'Nigeria');

INSERT INTO app_user(user_type, user_id, first_name, last_name, username, password,email,phone,is_enabled,address_id)
VALUES ('SELLER', '$2a$04$/eZxYklMPH1EOyoRZ83PjOrgMVsq0B6gciWW/3rKR5Rz5DYJK8icK', 'Adekunle', 'Gold', 'adekunlegold',
       '$2a$04$P06/izqoy3pghw63/KnU9urv1PIv1GS681CpU2O5krSSoxjG9cD7C','adekunlegold@gmail.com','08023452673', TRUE, 1),
       ('BUYER', '$2a$04$yEmWlMPvY5I4rSKepD6jOeCvCivtJWWry4FtKjCyURcR5Y.WcRp8a', 'Suru', 'Eric', 'ericmoore',
       '$2a$04$P06/izqoy3pghw63/KnU9urv1PIv1GS681CpU2O5krSSoxjG9cD7C','seric@gmail.com','08123456326', FALSE, 2);

INSERT INTO user_role(role_name)
VALUES ('BUYER'), ('SELLER'), ('ADMIN'), ('SUPER ADMIN');