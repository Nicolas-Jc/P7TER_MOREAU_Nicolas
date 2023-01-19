# Poseidon

## Database

### Before Running App

Before running the application, you must create the database with the following commands:

CREATE DATABASE demo;

USE demo;

Run sql script to create tables (doc/data.sql). The Sql script will not only create the tables but also initialize 2
users to enter the very first time in the
application :

- User1 : User Name = "Administrator" / Password = "Administrator_1" / Role = "ADMIN"
- User2 : User Name = "Userposeidon" / Password = Userposeidon_1 / Role = "USER"

Only the "ADMIN" role user has access to the "User Management" view

## ENVIRONMENT VARIABLES

In your IDE, add 5 environment variables (see file "application.properties"):

- DB_URL : Your JDBC connection string
- DB_USER : Your Database User Name
- DB_PASSWORD : Your Database Password
- CLIENT_ID : Your oAuth2 client_id (Github, facebook, ....)
- CLIENT_SECRET : Your oAuth2 secret (Github, facebook, ....)

## Endpoints

- **Application:**  http://localhost:9091/ - Login with "User1" OR "User2" (See Above)

## Security

- Passwords in Users view are encrypted with BCryptPasswordEncoder (Spring Security).


