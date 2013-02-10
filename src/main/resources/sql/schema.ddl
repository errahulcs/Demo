
CREATE TABLE DISHES
(
    dishId              INT PRIMARY KEY AUTO_INCREMENT,
    restaurantId    VARCHAR(30),
    name    VARCHAR(30),
    description   VARCHAR(1000),
    shortDescription         VARCHAR(30),
    imageUrl     VARCHAR(500),
    price	FLOAT,
    categoryId INT
);

CREATE TABLE CATEGORIES
(
    categoryId              INT PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(30),
    description   VARCHAR(1000)
);

CREATE TABLE MENUS
(
    menuId              INT PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(30),
    description   VARCHAR(1000),
    restaurantId    VARCHAR(30)
);

CREATE TABLE MENU_DISH (
    menuId INT NOT NULL,
    dishId INT NOT NULL,
    PRIMARY KEY (menuId, dishId),
    INDEX FK_DISH (dishId),
    CONSTRAINT FK_MENU FOREIGN KEY (menuId) REFERENCES MENUS (menuId),
    CONSTRAINT FK_DISH FOREIGN KEY (dishId) REFERENCES DISHES (dishId)
);

CREATE TABLE USERS
(
    userId INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    passwordHash VARCHAR(100),
    UNIQUE idx_user_username (username)
);