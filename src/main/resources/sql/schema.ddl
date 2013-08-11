
CREATE TABLE DISHES
(
    dishId INT PRIMARY KEY AUTO_INCREMENT,
    restaurantId INT,
    userId INT,
    name VARCHAR(300),
    description VARCHAR(1000),
    shortDescription VARCHAR(1000),
    imageUrl VARCHAR(500),
    price FLOAT,
    dishType VARCHAR(100),
    vegetarian BOOL,
    alcoholic BOOL,
    disabled BOOL,
	activeDays INT,
	happyHourEnabled BOOL,
	happyHourDays INT,
	happyHourStartHour INT,
	happyHourStartMin INT,
	happyHourEndHour INT,
	happyHourEndMin INT,
	happyHourPrice FLOAT
);

CREATE TABLE SECTIONS
(
    sectionId INT PRIMARY KEY AUTO_INCREMENT,
    userId INT,
    name VARCHAR(30),
    description VARCHAR(1000),
    header VARCHAR(1000),
    footer VARCHAR(1000),
    price FLOAT
);

CREATE TABLE SECTION_DISH (
    sectionId INT NOT NULL,
    dishId INT NOT NULL,
    dishPosition INT NOT NULL,
    PRIMARY KEY (sectionId, dishId),
    INDEX FK_DISH (dishId),
    CONSTRAINT FK_SECTION FOREIGN KEY (sectionId) REFERENCES SECTIONS (sectionId),
    CONSTRAINT FK_DISH FOREIGN KEY (dishId) REFERENCES DISHES (dishId)
);


CREATE TABLE MENUS
(
    menuId INT PRIMARY KEY AUTO_INCREMENT,
    restaurantId INT,
    userId INT,
    name VARCHAR(30),
    description VARCHAR(1000),
    modifiedTime TIMESTAMP,
    status VARCHAR(10),
    imageUrl VARCHAR(500)
);

CREATE TABLE MENU_SECTION (
    menuId INT NOT NULL,
    sectionId INT NOT NULL,
    sectionPosition INT NOT NULL,
    PRIMARY KEY (menuId, sectionId),
    INDEX FK_MENUSECTION (sectionId),
    CONSTRAINT FK_MENU FOREIGN KEY (menuId) REFERENCES MENUS (menuId),
    CONSTRAINT FK_MENUSECTION FOREIGN KEY (sectionId) REFERENCES SECTIONS (sectionId)
);

CREATE TABLE USERS
(
    userId INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    passwordHash VARCHAR(100),
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    businessName VARCHAR(100),
    BUSINESSPORTRAITIMAGEURL VARCHAR(500),
	BUSINESSLANDSCAPEIMAGEURL VARCHAR(500),
    additionalChargesName1 VARCHAR(100),
	additionalChargesName2 VARCHAR(100),
	additionalChargesName3 VARCHAR(100),
	additionalChargesType1 VARCHAR(10),
	additionalChargesType2 VARCHAR(10),
	additionalChargesType3 VARCHAR(10),
	additionalChargesValue1 INT,
	additionalChargesValue2 INT,
	additionalChargesValue3 INT,
    UNIQUE idx_user_username (username)
);



CREATE TABLE RESTAURANTS
(
    restaurantId INT PRIMARY KEY AUTO_INCREMENT,
    userId INT,
    name VARCHAR(300),
    address1 VARCHAR(300),
    address2 VARCHAR(300),
    phone VARCHAR(15),
    city VARCHAR(30),
    state VARCHAR(30),
    country VARCHAR(30)
);

CREATE TABLE USER_RESTAURANT (
    userId INT NOT NULL,
    restaurantId INT NOT NULL,
    PRIMARY KEY (userId, restaurantId),
    INDEX FK_RESTAURANT (restaurantId),
    CONSTRAINT FK_USER FOREIGN KEY (userId) REFERENCES USERS (userId),
    CONSTRAINT FK_RESTAURANT FOREIGN KEY (restaurantId) REFERENCES RESTAURANTS (restaurantId)
);