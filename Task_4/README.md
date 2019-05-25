# Task 4: A container in Java handling high concurrency issues

## Principle

There are four important parameters in this container:

1. Thread number. Thread number is the number of worker threads you wish to use.
2. Threshold. In our model, if request number is less than threshold, the scheduling policy is like a normal queue, but if request number increases and is over the threshold, the scheduling policy will change to a stack, i.e., the last one who came will be served first.
3. Upper bound. This means the upper bound of request number to be handled. If more requests came, they will be directly rejected, which is, to fail gracefully.
4. Timeout. If a request stays in the queue (or probably stack) longer than this parameter, this request will be directly dropped. This policy is executed by another long living thread.



## Build

Run the following command to build the project into jar file and docker image.
```shell
mvn clean package dockerfile:build
```

## Run

You can run by building docker image yourself, or just pull from <https://docker.com>

```
docker pull shenjiahuan/se418_task4
docker run -p 8080:8080 shenjiahuan/se418_task4 --thread.nThreads=<nThreads> --thread.threshold=<threshold> --thread.upperBound=<upperBound> --thread.timeout=<timeout>
```

## Validation

*Note: This might not be a reasonable choice in production environment, but is helpful to illustrate the correctness of our design.*

1. The following test is based on parameters with: nThreads=2, threshold=4, upperBound=100, timeout=30.

   We can simulate requests using ApacheBench:

   ```shell
   ab -n 40 -c 40 http://localhost:8080/wordladders\?from\=hello\&to\=world
   ```

   Our application will display log information to the console, and it shows like below:

   ```
   2019-05-25 08:47:41 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:40 UTC 2019
   2019-05-25 08:47:44 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:44 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:45 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:45 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:49 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:49 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:50 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:50 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:48 UTC 2019 (*)
   2019-05-25 08:47:52 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:52 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:53 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:54 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:55 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:55 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:56 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:56 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:58 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:58 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:59 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:47:59 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:01 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:01 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:02 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:02 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:04 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:04 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:05 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:05 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:07 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:07 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:08 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:09 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:10 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:10 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:11 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:12 DEBUG ThreadPool:95 - [Drop] Create time: Sat May 25 08:47:41 UTC 2019 (**)
   2019-05-25 08:48:12 DEBUG ThreadPool:95 - [Drop] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:12 DEBUG ThreadPool:95 - [Drop] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:12 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   2019-05-25 08:48:12 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 08:47:41 UTC 2019
   
   ```

   There are two points which requires your attention:

   1. I used postman to send a request manually during the execution, and it is the request marked as ```(*)```. As this is a later request compared to the previous 40 simutaneous ones, it is handled before the previous ones, in that tasks to be handled is more than threshold so the policy will be LIFO.
   1. At the bottom of the requests there are 3 requests marked as ```Drop```, as they have been living in the queue for a while longer than timeout.

2. The following test is based on parameters with: nThreads=2, threshold=4, upperBound=8, timeout=100.

   We can simulate requests using ApacheBench:

   ```shell
   ab -n 20 -c 20 http://localhost:8080/wordladders\?from\=hello\&to\=world
   ```
   Log information shows like below:

   ```
   2019-05-25 09:01:34 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:01:33 UTC 2019
   2019-05-25 09:01:34 DEBUG ThreadPool:37 - [Reject] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:34 DEBUG ThreadPool:37 - [Reject] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:34 DEBUG ThreadPool:37 - [Reject] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:34 DEBUG ThreadPool:37 - [Reject] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:34 DEBUG ThreadPool:37 - [Reject] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:34 DEBUG ThreadPool:37 - [Reject] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:34 DEBUG ThreadPool:37 - [Reject] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:34 DEBUG ThreadPool:37 - [Reject] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:35 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:35 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:37 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:37 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:39 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:39 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:41 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:41 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:43 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:43 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:01:34 UTC 2019
   2019-05-25 09:01:44 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:01:34 UTC 2019
   ```

   As expected, some requests are dropped by the limit of upperBound.

3. The following test is based on parameters with: nThreads=10, threshold=100, upperBound=200, timeout=100.

   We can simulate requests using ApacheBench:

   ```shell
   ab -n 40 -c 40 http://localhost:8080/wordladders\?from\=hello\&to\=world
   ```

   Log information shows like below:

   ```
   2019-05-25 09:09:02 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:01 UTC 2019
   2019-05-25 09:09:10 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:10 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:10 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:10 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:10 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:10 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:10 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:10 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:10 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:11 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:15 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:15 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:15 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:15 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:16 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:16 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:16 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:16 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   2019-05-25 09:09:16 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:08 UTC 2019 (*)
   2019-05-25 09:09:16 DEBUG ThreadPool:69 - [Finish] Create time: Sat May 25 09:09:02 UTC 2019
   
   ```

   I used postman to send a request manually during the execution, and as the queue size does not exceed threshold limit, it works like FIFO, so the manually sent request is handled later than previous ones.