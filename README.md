# TinyURL

## Features
A url shortening application with the following fearures.
* Generate a shortened url based on the original one
* Specify a unique hash in place of a randomly generated one
* Set expiration date of the url redirect
* User authentication to save your urls

## Design Decisions
### Pre-generated hashes
Instead of generating hashes (which are the unique alphanumeric string that comes after the domain) based on the URL, the hashes are pre-generated to ensure that every URL has a unique hash to allow user customization.

### Microservices Architecture
The backend app is splitted into 5 different microservies each has its own job. This ensures scalability because each service can be scaled accroding to its traffic by using kubernetes. This modular design also has very good separation of logic and allows individual development of each service in a team settings. Also, each service can be built using a different framework.

## Backend
### Architecture
The backend is developed with a distributed microservice architecture, consisting of microservices developed with the Java Spring framework and delpoyed onto a Google Kubernetes Engine cluster.
![url-backend](https://user-images.githubusercontent.com/37493948/152458994-f2c87aa3-215b-449f-8436-9c37a0bfd0f6.jpg)

### Service Responsibilities
*  **Hash Service** manages a collection of pregenerated hashes to be consumed by the **Shortening Service**, also maintains a collection of used hashes.

* **Shortening Service** is the main application that is responsible for tinyurl generation, redirection to original URL, and listing out the urls a user has generated. It takes in a URL and generates a shortened URL with a hash retrieved from **Hash Service**, also maintains a collection of documents that maps a hash to its original URL and other fields such as expiration date and username. 

* **Scheduler Service** is running on a cronjob. It is responsible for generating new hashes when the count of available hashes in the database is low. It is also responsible for recycling hashes when the user specified expration date has come.

* **Authentication Service** is responsible user registration and authentication, manages a MySQL database that stores username and passwords

* **API Gateway** is an just an api gateway that routes traffic according to the path

### Tech Stack
* **Spring Boot** to develop each invidual service
* **Spring Cloud Gateway** to develop the api gateway
* **Spring Cloud OpenFeign** for cross-service communication
* **Spring Cloud Kubernetes** to integrate with kubernetes for service discovery
* **Spring Cloud Netflix Eureka** for service discovery in development environment
* **Spring Data JPA** for ORM with MySQL database
* **Spring Data MongoDB** for ODM with MongoDB database
* **Spring Security** for user authentication

## Frontend
The frontend is a Next JS application with Redux Toolkit for state management

## Deployment
Since this app consists of multiple standalone services, kubernetes is a good option for deployment for its features such as service registration, discovery, loadbalancing as well as separate scaling.
![url-k8s](https://user-images.githubusercontent.com/37493948/152459151-0ee10670-0297-4c6e-968c-35a95c84d107.jpg)
