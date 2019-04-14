# WordLadder using Spring Boot

## Test
```
./mvnw test
```
## Run
```$
./mvnw spring-boot:run
```

## Usage
It will listen on <http://localhost:8080/> by default.  

Wordladder application requires login before you can use. You will receive HTTP status 401 when unauthorized. At this stage, username and password are both 'SE418'.  
You can use tools like 'postman' to send HTTP requests, and in this way, you need to *POST* to that url, with ```{"username":"SE418", "password": "SE418"}``` in request body.  

After that, if you wish to get a wordladder from 'hello' to 'world', you can visit <http://localhost:8080/wordladders?from=hello&to=world>.  
If nothing goes wrong, you will receive response like this:
```
{
    "result": [
        "hello",
        "hells",
        "heals",
        "weals",
        "weald",
        "woald",
        "world"
    ],
    "status": 0
}
```
`status` has the following meanings:  

`status` | meaning
---- | ---
0 | Nothing goes wrong
-1 |  No ladder exists
-2 | Dictionary not found
-3 | Request format incorrect

## Spring Actuator
You can visit <http://localhost:8080/actuator/health> to monitor the status of the Spring Application. Notice that this also requires login.
