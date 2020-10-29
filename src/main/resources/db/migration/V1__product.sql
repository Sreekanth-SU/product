CREATE TABLE TEMP(
 TYPE VARCHAR(255) NOT NULL,
 PROPERTIES VARCHAR(255) NOT NULL,
 PRICE DECIMAL NOT NULL,
 STORE_ADDRESS VARCHAR(255) NOT NULL
) AS SELECT * FROM CSVREAD('data.csv');

CREATE TABLE PRODUCT(
    ID VARCHAR2(1000) NOT NULL,
                     TYPE VARCHAR(255) NOT NULL,
                     PROPERTIES VARCHAR(255) NOT NULL,
                     PRICE DECIMAL NOT NULL,
                     STORE_ADDRESS VARCHAR(255) NOT NULL
);

INSERT INTO PRODUCT (SELECT RANDOM_UUID() AS ID,TYPE,PROPERTIES,PRICE,STORE_ADDRESS FROM TEMP);