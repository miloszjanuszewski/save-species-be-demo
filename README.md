# Save Species demo app

This is a project which provides a backend functionality 
for recording new donations from the users (payments). It also allows administrator
for modifying the allowed species list. User can retrieve data associated with 
their donations.

This part of application is backend service written using Spring Boot.
It provides couple of endpoints, whose documentation can be found in this OpenAPI 
file: [open-api-docs](./open-api-docs.json)

Detailed API documentation can be found under
`http://localhost:8080/save-species/swagger-ui/index.html` 
when running app locally

Application is using local DynamoDB as a storage.

Application can be run locally using `docker compose up`
1. Compile Java app into `.jar`
2. Run script `scripts/build.sh` for building Docker image of the app
3. Go to `docker` file and run `docker compose up -d` to bring up the app and DynamoDB
4. Initialize the DynamoDB tables by running `scripts/create-tables.sh`
5. You're all set, try the API e.g. using Postman.


### Authentication
There are 3 users defined:
```
(username, password, ROLES)
user1, user1Password, USER
user2, user2Password, USER
admin, adminPassword, USER ADMIN
```
and all endpoints which are not API docs require authentication. 

#### Authorization
There is authorization based on roles - 
ADMIN is able to perform all operations on the `/species` route while
USER can only read this data.

##### Local development (without docker)
Make sure to use local properties file. To do it set env variable `SPRING_PROFILES_ACTIVE` to `local`
