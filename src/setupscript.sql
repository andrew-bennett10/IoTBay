DROP TABLE IF EXISTS CUSTOMERS;
CREATE TABLE CUSTOMERS (
                        CustomerId      INTEGER PRIMARY KEY AUTOINCREMENT ,
                        Email       VARCHAR(20),
                        Password    VARCHAR(20),
                        FName       VARCHAR(20),
                        LName       VARCHAR(20),
                        Age         INTEGER(4),
                        Address     VARCHAR(20),
                        Registered  BOOLEAN
);

ALTER TABLE CUSTOMERS ADD COLUMN PhoneNumber VARCHAR(20);
ALTER TABLE CUSTOMERS ADD COLUMN IsActive BOOLEAN DEFAULT TRUE;

INSERT INTO CUSTOMERS (Email, Password, FName, LName, Age, Address, Registered) VALUES ('john@lol.com', '123', 'John', 'Smith', '42', '1 Generic Ln', 'True');
INSERT INTO CUSTOMERS (Email, Address, Registered) VALUES ('bob@bruh.org', 'Anon Street', 'False');
INSERT INTO CUSTOMERS (Email, Password, FName, LName, Age, Address, Registered, PhoneNumber, IsActive) VALUES ('2454434529@qq.com','123','Jack','Chen',22,'AU',true,'1234567',true);


DROP TABLE IF EXISTS STAFF;
CREATE TABLE STAFF (
                       StaffId      INTEGER PRIMARY KEY AUTOINCREMENT ,
                       Email       VARCHAR(20),
                       Password    VARCHAR(20),
                       FName       VARCHAR(20),
                       LName       VARCHAR(20),
                       Role       VARCHAR(20)

);

ALTER TABLE STAFF ADD COLUMN PhoneNumber VARCHAR(20);
ALTER TABLE STAFF ADD COLUMN IsActive BOOLEAN DEFAULT TRUE;

INSERT INTO STAFF (Email, Password, FName, LName, Role) VALUES ('Andrew@cool.com', '321', 'Andrew','Bennett','Technical Intern');
INSERT INTO STAFF (Email, Password, FName, LName, Role) VALUES ('Matthis@cool.com', '3210', 'Matthis','Fontaine','Fungi');
INSERT INTO STAFF (Email, Password, FName, LName, Role, PhoneNumber, IsActive) VALUES ('Jianan.Huang-3@student.uts.edu.au','123','Jianan','Huang', 'Admin','0430590617', true);
INSERT INTO STAFF (Email, Password, FName, LName, Role, PhoneNumber, IsActive) VALUES ('leoluk2211@gmail.com','password','Leo','Luk', 'Admin','0490766705', true);


DROP TABLE IF EXISTS PRODUCTS;
CREATE TABLE PRODUCTS (
                        ProductId      INTEGER PRIMARY KEY AUTOINCREMENT ,
                        Name           VARCHAR(20),
                        Description    VARCHAR(120),
                        Stock          INTEGER(4),
                        Price          FLOAT(6),
                        Supplier       VARCHAR(40)
);
INSERT INTO PRODUCTS (Name, Description, Stock, Price, Supplier) VALUES ('Sensor', 'A device which detects or measures a physical property and records, indicates, or otherwise responds to it.', '400', '12.50', 'Andrew Tech LTD.');
INSERT INTO PRODUCTS (Name, Description, Stock, Price, Supplier) VALUES ('Actuator', 'A device that causes a machine or other device to operate.', '10', '9.45', 'Matthis Corp PTY LTD.');
INSERT INTO PRODUCTS (Name, Description, Stock, Price, Supplier) VALUES ('Gateway', 'A gateway in technology refers to a network node that connects different networks or allows communication between different applications or systems.', '9827', '4.00', 'Europa Coalition.');




DROP TABLE IF EXISTS CUSTOMER_ACCESS_LOGS;
CREATE TABLE CUSTOMER_ACCESS_LOGS (
                          CustomerAccLogId      INTEGER PRIMARY KEY AUTOINCREMENT,
                          CustomerId            VARCHAR(20),
                          LoginDate             TEXT,
                          LogoutDate            TEXT,
                          CONSTRAINT CustomerFK
                              FOREIGN KEY (CustomerId)
                                  REFERENCES CUSTOMERS(CustomerId)
);
-- INSERT INTO CUSTOMER_ACCESS_LOGS (CustomerId, LoginDate, LogoutDate) VALUES ('1', '1111-01-01');


DROP TABLE IF EXISTS STAFF_ACCESS_LOGS;
CREATE TABLE STAFF_ACCESS_LOGS (
                             StaffAccLogId   INTEGER PRIMARY KEY AUTOINCREMENT,
                             StaffId         VARCHAR(20),
                             LoginDate       TEXT,
                             LogoutDate      TEXT,
                             CONSTRAINT StaffFK
                                 FOREIGN KEY (StaffId)
                                     REFERENCES STAFF(StaffId)
);
-- INSERT INTO STAFF_ACCESS_LOGS (StaffId, LogDate) VALUES ('1', '2/2/2222');




-- May need to update below - this is not my problem!
DROP TABLE IF EXISTS CUSTOMER_ORDER;
CREATE TABLE CUSTOMER_ORDER (
                             OrderId   INTEGER PRIMARY KEY AUTOINCREMENT ,
                             CustomerId      INTEGER,
                             ProductId     INTEGER,
                             TotalPrice      FLOAT(8),
                             OrderDate       DATE,
                             CONSTRAINT CustomerFK
                                FOREIGN KEY (CustomerId)
                                    REFERENCES CUSTOMERS(CustomerId),
                             CONSTRAINT ProductFK
                                FOREIGN KEY (ProductId)
                                    REFERENCES PRODUCTS(ProductId)
);
-- INSERT INTO CUSTOMER_ORDER (CustomerId, ProductId) VALUES (1, 1);