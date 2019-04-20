# Resource usage analysis of previous REST service

## Tools
1. ApacheBench  

	> ApacheBench (ab) is a single-threaded command line computer program for measuring the performance of HTTP web servers.
	
2. Postman  
	Postman is a tool to send HTTP requests.
3. Prometheus  
	Prometheus is used to monitor CPU and memory consumption.
	
## Steps
1. Use Postman to make post request to <http://localhost:8080/login> with username and password, and retrieve the returned JSESSIONID.
2. Send concurrent HTTP requests to <http://localhost:8080/wordladders?from=hello&to=world> using ApacheBnech. The number of cuncurrent requests will be 1, 2, 4, 8, 16.
3. Monitor resource usage in Prometheus. Use ```process_cpu_usage``` to monitor CPU usage and ```jvm_memory_used_bytes``` to monitor memory usage.

## Result
### No user
### 1 concurrent request
### 2 concurrent request
### 4 concurrent request
### 8 concurrent request
### 16 concurrent request