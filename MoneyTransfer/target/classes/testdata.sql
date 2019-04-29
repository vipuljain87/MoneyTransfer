
DROP TABLE IF EXISTS User;
COMMIT;

CREATE TABLE User (
UserID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
UserName VARCHAR(20) NOT NULL,
Created TIMESTAMP(9),
LastUpdated TIMESTAMP(9)
);
COMMIT;

CREATE UNIQUE INDEX uidx_user on User(UserName);
COMMIT;

INSERT INTO User (UserName,Created,LastUpdated) VALUES ('vipul',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());
INSERT INTO User (UserName,Created,LastUpdated) VALUES ('james',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());
INSERT INTO User (UserName,Created,LastUpdated) VALUES ('jack',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());
COMMIT;

DROP TABLE IF EXISTS Account;

CREATE TABLE Account (
AccountNumber INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
AccountName VARCHAR(20) NOT NULL,
UserName VARCHAR(20),
AccountBalance DECIMAL(20,2),
IsSoftDeleted BOOLEAN,
Created TIMESTAMP(9),
LastUpdated TIMESTAMP(9)
);
COMMIT;

CREATE UNIQUE INDEX uidx_account on Account(AccountName,UserName,AccountBalance,IsSoftDeleted);
COMMIT;

INSERT INTO Account (AccountName,UserName,AccountBalance,IsSoftDeleted,Created,LastUpdated) VALUES ('Savings','vipul',300.00,0,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());
INSERT INTO Account (AccountName,UserName,AccountBalance,IsSoftDeleted,Created,LastUpdated) VALUES ('Checking','james',200.00,0,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());
INSERT INTO Account (AccountName,UserName,AccountBalance,IsSoftDeleted,Created,LastUpdated) VALUES ('Overdraft','jack',600.00,0,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());
COMMIT;

DROP TABLE IF EXISTS Transaction;
COMMIT;

CREATE TABLE Transaction (
TransactionID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
FromAccountNumber INT NOT NULL,
ToAccountNumber INT NOT NULL,
Amount DECIMAL(20,2),
Status ENUM('SUCCESS','FAILED') NOT NULL,
Created TIMESTAMP(9),
LastUpdated TIMESTAMP(9)
);
COMMIT;

CREATE UNIQUE INDEX uidx_txn on Transaction(TransactionID,FromAccountNumber,ToAccountNumber,Amount,Status);
COMMIT;
