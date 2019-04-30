# MoneyTransfer

Just download the project and run "mvn exec:java"

### We have Support for following services

| METHOD | EndPoint | Description | Example |
| -----------| ------ | ------ | ------ |
| GET | /users/all | Get all users | http://localhost:8080/users/all |
| GET | /users/{userName} | Get user by user name | http://localhost:8080/users/vipul |
| GET | /accounts/all | Get all accounts | http://localhost:8080/accounts/all |
| GET | /accounts/{accountNumber} | Get account by account number | http://localhost:8080/accounts/1 |
| GET | /accounts/{accountNumber}/balance | Get account balance by account number | http://localhost:8080/accounts/1/balance |
| DELETE | /accounts/delete/{accountNumber} | Delete account by account number | http://localhost:8080/accounts/delete/3 |
| PUT | /accounts/add | Add new Account | {"accountName":"savings","userName":"paul","accountBalance":10.00} |
| POST | /transaction/{fromAccount}/{toAccount}/{amount} |  Transaction between 2 user accounts with specified amount | http://localhost:8080/transaction/1/2/100 |
