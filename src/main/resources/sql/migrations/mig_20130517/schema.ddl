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
	destiantionType VARCHAR(50),
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