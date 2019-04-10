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
It will listen on <http://localhost:9000/> by default.  

Wordladder application requires login before you can use. You will be redirected to <http://localhost:9000/login> to log in. At this stage, username and password are both 'SE418'.  
You are also welcomed to use tools like 'postman' to send HTTP requests, and in this way, you need to *POST* to that url, with ```{"username":"SE418", "password": "SE418"}``` in request body.  

After that, if you wish to get a wordladder from 'hello' to 'world', you can visit <http://localhost:8080/wordladders?from=hello&to=world>.  
Still, you are welcomed to use 'postman' to send GET requests.  
And if nothing goes wrong, you will receive response like this:
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
You can visit <http://localhost:9001/actuator/health> to monitor the status of the Spring Application.
