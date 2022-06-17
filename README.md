# Kadmos case study
A micro-services based platform to manage two savings accounts.

# How to run
* Make sure `docker`, `docker-compose` and `java18` are installed
* Run `sh run.sh`
* The api gateway endpoint is protected with basic auth
  * login=user, password=pass

# Assumptions and ideas
* The solution is essentially 2 spring-boot microservices
  * `api-gateway` - `spring-cloud-gateway` based API gateway
  * `savings-service` - each instance represent a separate savings account
  * `docker-compose` is used to run the services and postgres instances
* Balance could be any decimal number including balance <= 0, thus no validations are added.
* `savings_account` table keep all records for audit reasons
  * Current balance is the last record by `created_at`
* `savings-account` instances are running on port 8081 and 8082 as required, 
but they are accessible only from the api-gateway or docker internal network
for security reasons
* Timeouts are tested in the `ApiGatewayTest.shouldReturn504IfTimeoutIsReached()` test
* Gettings logs:
  * `docker logs api-gateway` - current logs
  * `docker exec -it api-gateway bin/bash` and then `cat logs/app.log` - saved in the file

# API

Get balance:
```
curl --location --request GET 'localhost:8080/savings/a/balance' \
--header 'Authorization: Basic dXNlcjpwYXNz' \
--header 'Content-Type: application/json' 
```
Change balance:
```
curl --location --request POST 'localhost:8080/savings/a/balance' \
--header 'Authorization: Basic dXNlcjpwYXNz' \
--header 'Content-Type: application/json' \
--data-raw '{
    "balance": 1002
}'
```
### How to scale the api gateway?
* I would run the current platform in Kubernetes, so it can be easily scaled up horizontally
* It is also possible to scale it up without K8S but we will need to add custom load balancer and service discovery 
  tools

### How to monitor uptime?
* Spring actuator for health checks
* Datadog/Newrelic (or any other APM tool) for monitoring and custom metrics/alerts
