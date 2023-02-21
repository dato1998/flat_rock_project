Ecommerce task solution

this spring app contains 5 microservices
1. eureka :8761 - name registry server for registration and discovering other microservices
2. api-gateway : 8765 - for now just proxy and in future maybe single point for authentification logging and etc
3. sso :8400 - authorization server which generates jwt token, session(redis), manages authorization, Claims login, logout, etc. 
4. notificationService : 8500 - service for sending notifications via mail and sms after getting message from queue implemented by sendgrid
5. productService 8600 - main service with logic for clients, products, orders, which are managed by roles and authentification, also it includes publisher and subscriber which is implemented by redis, purchase logic with minimal transactions, in two words i unified other services in this one for more simplisity 

for db
1. ecommerce : 9306 i have single mySQL db which is used by SSO and productService
2. redis_db : 6379 which is used for messages and caching sessions

for each microservice except eureka and gateway-api we have swagger of their ports on link host/port/swagger-ui/index.html

for each microservice we have Dockerfile and is build by running maven install -> docker build -t name:tag . commands from root directory of each microservice. for launching project you can run command docker compose up from the project root directory
database sourse are in productService -> resource -> db_backups
