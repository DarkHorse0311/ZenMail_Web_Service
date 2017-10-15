## ZenMail web service
Application for receiving and sending emails built with Java and Angular 4+  

### Technology Stack
Component         | Technology
---               | ---
Frontend          | [Angular 4+](https://github.com/angular/angular) and [Covalent](https://github.com/Teradata/covalent)
Backend (REST)    | [SpringBoot](https://projects.spring.io/spring-boot) (Java)
Security          | Token Based (Spring Security and [JWT](https://github.com/auth0/java-jwt) )
In Memory DB      | H2 
Persistence       | JPA (Using Spring Data)
Client Build Tools| [angular-cli](https://github.com/angular/angular-cli), Webpack, npm
Server Build Tools| Maven (Java)

## Folder Structure
```bash
PROJECT_FOLDER
│  README.md
│  pom.xml           
└──[src]      
│  └──[main]      
│     └──[java]      
│     └──[resources]
│        │  application.properties #contains springboot cofigurations
│        │  schema.sql  # Contains DB Script to create tables that executes during the App Startup          
│        │  data.sql    # Contains DB Script to Insert data that executes during the App Startup (after schema.sql)
│        └──[public]    # keep all html,css etc, resources that needs to be exposed to user without security
│        │
│        └──[webui]
│           │  package.json     
│           │  angular-cli.json   #ng build configurations)
│           └──[node_modules]
│           └──[src]              #frontend source files
│           └──[dist]             #frontend build files, auto-created after running angular build: ng -build
|
└──[target]              #Java build files, auto-created after running java build: mvn install
   └──[classes]
      └──[public]
```

## Prerequisites
Ensure you have this installed before proceeding further
- Java 8
- Maven 3.3.9+
- Node 6.0 or above,  
- npm 4 or above,   
- Angular-cli 

## About
This is an RESTfull email service built with Java and Angular. 

### Install Frontend
```bash
# Navigate to PROJECT_FOLDER/webui (should cntain package.json )
npm i
# build the project (this will put the files under dist folder)
ng build
# or for live development
ng serve
```

### Run Backend (SpringBoot Java)
```bash
# Maven Build : Navigate to the root folder where pom.xml is present 
mvn spring-boot:run
```

### Install Database
```bash
    TODO: !!!
```

### Check web application
```bash
# Start the server (8448)
# port and other configurations for API servere is in [./src/main/resources/application.properties](/src/main/resources/application.properties) file
# Go to the http://localhost:8448/ (or http://localhost:4200/ for live development)
```
