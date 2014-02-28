ALTER TABLE ORDERDISHES ADD COLUMN dishType VARCHAR(100) DEFAULT 'OTHERS';
ALTER TABLE CHECKS ADD COLUMN deliveryTime TIMESTAMP;

CREATE TABLE DELIVERYAREAS
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    userId INT,
    name VARCHAR(200)
);

ALTER TABLE CHECKS ADD COLUMN deliveryArea VARCHAR(300) DEFAULT 'Others';
ALTER TABLE CHECKS ADD COLUMN deliveryAddress VARCHAR(300) DEFAULT '';

ALTER TABLE USERS ADD COLUMN deliveryCharges FLOAT DEFAULT 0;
ALTER TABLE USERS ADD COLUMN minInCircleDeliveyThreshold FLOAT DEFAULT 0;
ALTER TABLE USERS ADD COLUMN minOutCircleDeliveyThreshold FLOAT DEFAULT 0;


ALTER TABLE USERS ADD COLUMN invoicePrefix VARCHAR(10) DEFAULT '';
ALTER TABLE USERS ADD COLUMN invoiceStartCounter INT DEFAULT 0;
ALTER TABLE CHECKS ADD COLUMN invoiceId VARCHAR(30) DEFAULT '';
CREATE INDEX idx_invoiceId ON CHECKS (invoiceId) USING BTREE;

ALTER TABLE CHECKS ADD COLUMN discountPercent FLOAT DEFAULT 0;
ALTER TABLE CHECKS ADD COLUMN discountAmount FLOAT DEFAULT 0;