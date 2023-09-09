# WIBMO_MICROSERVICES_REPO

Repository containing Course Registration System with various implementations from plain java and jdbc to spring, jpa and Rest.

Steps to run:

* Start a redis server on default port
* Start kafka and zoopkeeper servers
* configure kafka, zookeeper, redis targets in respective microservices.
* Run Eureka server followed by individual microservices (Student, Admin, Professor) and API Gateway.
