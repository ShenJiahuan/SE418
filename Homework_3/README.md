#WordLadder using Spring Boot

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
If you wish to get a wordladder from 'hello' to 'world', you can visit <http://localhost:8080/wordladder?from=hello&to=world>. 
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
`status` with value 0 means there is nothing wrong, while if there does not exist a ladder, server will respond with value 1 and if there 
exists internal errors such as 'Dictionary not found', server will respond with `status` 2.
