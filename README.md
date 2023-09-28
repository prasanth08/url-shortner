# url-shortner
quick url shortner


# Getting Started

## Dependencies

This project depends on
* spring-boot-starter-web (Spring boot framework)
* commons-validator:1.6 (for URL validation)
* guava:32 (for hash creation)

## Project Open
* Recommended opening project in Intellij ide
* All requests to hit are found under /requests directory
* Just open each request in intellij and hit Send button after starting the application


## Project Build

To build this project, run


```shell script
git clone https://github.com/prasanth08/url-shortner.git
cd url-shortener
mvn clean install
```
To start redis
```shell script
redis-server &
redis-cli flushall
```

Requests

Shorten a url
```shell script
curl --location --request POST 'localhost:8080/app/shorten' \
--header 'Content-Type: application/json' \
--data-raw '{
    "originalUrl": "https://github.com"
}'
```
To test redirection
copy the response from above on to a browser
or
```shell script
curl --location --request GET 'localhost:8080/app/ee30c62f'
```
Get analytics
```shell script
curl --location --request GET 'localhost:8080/internal/analysis'
```


**The application will be accessible on http://localhost:8080**

# Future Enhancements / Known Issues
* Direction said not to use an URL shortner api , assuming its api available on the web like TinyUrl ot Bitly
* Hashing is a murmur3 via guava library ? Need to implement a custom one
* Couldnt get the app to be configured in docker...Some config issue
