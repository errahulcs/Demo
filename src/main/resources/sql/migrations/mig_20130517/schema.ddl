CREATE TABLE CUSTOMERS
(
	customerId INT PRIMARY KEY AUTO_INCREMENT,
	restaurantId INT,
	userId INT,
	firstName VARCHAR(100),
	lastName VARCHAR(100), 
	address VARCHAR(300),
	city VARCHAR(50),
	phone VARCHAR(20),
	email VARCHAR(100),
	numberOfOrders BIGINT,
	createdTime TIMESTAMP
);

CREATE TABLE ORDERS
(
	orderId INT PRIMARY KEY AUTO_INCREMENT,
	restaurantId INT,
	userId INT,
	sourceType VARCHAR(50),
	sourceId INT,
	destinationType VARCHAR(50),
	destinationId INT,
	createdTime TIMESTAMP,
	bill FLOAT,
	paid FLOAT,
	status VARCHAR(50)
);

CREATE TABLE ORDERDISHES
(
	orderDishId INT PRIMARY KEY AUTO_INCREMENT,
	dishId INT,
	quantity INT,
	price FLOAT
);

CREATE TABLE ORDER_ORDERDISH (
	orderId INT NOT NULL,
	orderDishId INT NOT NULL,
	PRIMARY KEY (orderId, orderDishId),
	INDEX FK_ORDERORDERDISH (orderDishId),
	CONSTRAINT FK_ORDER FOREIGN KEY (orderId) REFERENCES ORDERS (orderId),
	CONSTRAINT FK_ORDERORDERDISH FOREIGN KEY (orderDishId) REFERENCES ORDERDISHES (orderDishId)
);

CREATE TABLE SEATINGTABLES
(
	seatingTableId INT PRIMARY KEY AUTO_INCREMENT,
	restaurantId INT,
	userId INT,
	name VARCHAR(50),
	seats INT,
	status VARCHAR(50),
	startTime TIMESTAMP
);

ALTER TABLE USERS ADD COLUMN BUSINESSPORTRAITIMAGEURL VARCHAR(500);
ALTER TABLE USERS ADD COLUMN BUSINESSLANDSCAPEIMAGEURL VARCHAR(500);
ALTER TABLE DISHES ADD COLUMN disabled BOOL;

ALTER TABLE DISHES ADD COLUMN activeDays INT;
ALTER TABLE DISHES ADD COLUMN happyHourEnabled BOOL;
ALTER TABLE DISHES ADD COLUMN happyHourDays INT;
ALTER TABLE DISHES ADD COLUMN happyHourStartHour INT;
ALTER TABLE DISHES ADD COLUMN happyHourStartMin INT;	
ALTER TABLE DISHES ADD COLUMN happyHourEndHour INT;
ALTER TABLE DISHES ADD COLUMN happyHourEndMin INT;
ALTER TABLE DISHES ADD COLUMN happyHourPrice FLOAT;

UPDATE DISHES SET disabled=false;
UPDATE DISHES SET happyHourEnabled=false;
UPDATE DISHES SET activeDays=255;
UPDATE DISHES SET happyHourDays=0;
UPDATE DISHES SET happyHourStartHour=0;
UPDATE DISHES SET happyHourStartMin=0;
UPDATE DISHES SET happyHourEndHour=0;
UPDATE DISHES SET happyHourEndMin=0;
UPDATE DISHES SET vegetarian=false where vegetarian IS NULL;
UPDATE DISHES SET alcoholic=false where alcoholic IS NULL;

ALTER TABLE ORDERS CHANGE destiantionType destinationType VARCHAR(50);

ALTER TABLE USERS ADD COLUMN additionalChargesName1 VARCHAR(100);
ALTER TABLE USERS ADD COLUMN additionalChargesName2 VARCHAR(100);
ALTER TABLE USERS ADD COLUMN additionalChargesName3 VARCHAR(100);
ALTER TABLE USERS ADD COLUMN additionalChargesType1 VARCHAR(10);
ALTER TABLE USERS ADD COLUMN additionalChargesType2 VARCHAR(10);
ALTER TABLE USERS ADD COLUMN additionalChargesType3 VARCHAR(10);
ALTER TABLE USERS ADD COLUMN additionalChargesValue1 FLOAT DEFAULT 0;
ALTER TABLE USERS ADD COLUMN additionalChargesValue2 FLOAT DEFAULT 0;
ALTER TABLE USERS ADD COLUMN additionalChargesValue3 FLOAT DEFAULT 0;

ALTER TABLE SEATINGTABLES ADD COLUMN description VARCHAR(300);


CREATE TABLE CHECKS
(
	checkId INT PRIMARY KEY AUTO_INCREMENT,
	restaurantId INT,
	userId INT,
	tableId INT,
	customerId INT,
	payment INT,
	status INT,
	createdTime TIMESTAMP
);

CREATE TABLE CHECK_ORDER (
	checkId INT NOT NULL,
	orderId INT NOT NULL,
	PRIMARY KEY (checkId, orderId),
	INDEX FK_CHECKORDER (orderId),
	CONSTRAINT FK_CHECK FOREIGN KEY (checkId) REFERENCES CHECKS (checkId),
	CONSTRAINT FK_CHECKORDER FOREIGN KEY (orderId) REFERENCES ORDERS (orderId)
);

