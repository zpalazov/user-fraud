# user-fraud
Tech challenge implementation, provides a singe endpoint for fraud user check.

Fraud numbers are the ones which contains "666" or "000"

Fraud emails are the ones which contains "joker" or "penguin"

Start with

gradle bootRun


Rest API

POST /users/detectFraud HTTP/1.1
Host: localhost:8080

Content-Type: application/json

{
	"phone":"456636",
	"email":"zahari@gmail.com"
}

Example Curl requests

curl -X POST \
  http://localhost:8080/users/detectFraud \
  -H 'Content-Type: application/json' \
  -d '{
	"phone":"456636",
	"email":"zahari@gmail.com"
}'
