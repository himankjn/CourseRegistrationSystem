# WIBMO_MICROSERVICES_REPO

Repository containing Course Registration System with various implementations from plain java and jdbc to spring, jpa and Rest.

Steps to run:

* Start a redis server on default port 6379
* Start kafka and zoopkeeper servers
    .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
    .\bin\windows\kafka-server-start.bat .\config\server.properties
* Create required kafka topics:
      1. approvalTopic
      2. paymentTopic
      3. registrationTopic
* configure kafka, zookeeper, redis targets in respective microservices.
* Run Eureka server followed by individual microservices (Student, Admin, Professor) and API Gateway.
