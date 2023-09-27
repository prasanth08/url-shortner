# url-shortner
quick url shortner


# Getting Started

## Dependencies

This project depends on
* spring-boot-starter-web (Spring boot framework)
* commons-validator:1.6 (for URL validation)
* guava:32 (for hash creation)

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

**The application will be accessible on http://localhost:8080**

# Future Enhancements / Known Issues
* Direction said not to use an URL shortner api , assuming its api available on the web like TinyUrl ot Bitly
* Hashing is a murmur3 via guava library ? Need to implement a custom one
