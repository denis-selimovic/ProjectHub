# ProjectHub

Issue tracker and project management tool for developers. 
Some of the main features include:
- Creating an account and managing it (resetting and changing the password)
- Creating a project
- Adding collaborators to your project
- Creating tasks within a project and assigning them statuses, types and priorities as well as collaborators that should work on them
- Creating issues within a project and assigning them types and priorities
- Commenting on tasks 
- Recieving notifications and emails about different events in the system

## Backend (Microservices)
The project consists of following five main microservices:
- [User service](/user-service)
- [Project service](/project-service)
- [Task service](/task-service)
- [Notification service](/notification-service)
- [Email service](/email-service)

Aongside those, there are 4 utility microservices: 
- [Configuration service](/configuration-service) - for centralizing the configuration files for other services
- [Eureka server](/eureka-server) - load balancer
- [System events service](/system-events-service) - for in-app logging of all activities
- [Gateway service](/gateway-service) - a single-point entry for the backend

## Web Application
This project consists of a web application that showcases the functionalities developed on the backed. The web application is developed using Angular framework and you can find the source code [here](/frontend). 

## Startup
You can run the application utilizing the docker setup. In order to do so, follow the next steps:
- Make sure you have **docker** installed
- Navigate to the root of the project
- run `` docker-compose up `` (be aware that the first time you run this, it may take around 15 minutes to build the project)
- navigate to **localhost:4200** in your browser to start using the web application

## Technological stack
- Spring Boot
- PostgreSQL
- Angular
- Rabbit MQ

# Contributors
- [Ajša Hajradinović](https://github.com/ahajradino1)
- [Denis Selimović](https://github.com/dselimovic1)
- [Lamija Vrnjak](https://github.com/lvrnjak1)
- [Amila Žigo](https://github.com/azigo12)
