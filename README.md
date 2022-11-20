# TodoApp - backend

### Description

A task management REST API application with JWT token based authentication and authorization (refresh + access token). Authorized user has access to his resources. Unauthorized user doesn't have access to most endpoints, besides the ones used to sign up/sign in.

### API Endpoints

**/api/lists**
- <code>GET</code>: Get all lists
- <code>POST</code>: Create a new list
- <code>PUT</code>: Update a list

**/api/lists/:id**
- <code>GET</code>: Get a list
- <code>PATCH</code>: Update a list name
- <code>DELETE</code>: Remove a list

**/api/lists/:id/items**
- <code>GET</code>: Get all tasks by list id
- <code>POST</code>: Create a new task

**/api/items**
- <code>PUT</code>: Update a task

**/api/items/:id**
- <code>DELETE</code>: Remove a task

**/api/items/today**
- <code>GET</code>: Get all tasks with today's date

**/api/items/completed**
- <code>GET</code>: Get all completed tasks

**/api/items/:id/complete**
- <code>PATCH</code>: Complete a task

**/api/items/:id/restore**
- <code>PATCH</code>: Restore a completed task

**/api/users**
- <code>GET</code>: Get all users

**/api/users/save**
- <code>POST</code>: Create a user

**/api/users/login?username=&?password=**
- <code>POST</code>: Sign in and generate tokens

**/api/users/password**
- <code>PATCH</code>: Update a password

**/api/role/save**
- <code>POST</code>: Create a role

**/api/role/addToUser**
- <code>POST</code>: Add a role to a user

**/api/token/refresh**
- <code>POST</code>: Refresh an access token with a refresh token

### Technologies

- Java 11
- Spring Boot 2.75
- Spring Boot Starter Web
- Spring Security
- Spring Data JPA
- PostgreSQL database
- PostgreSQL driver for Spring Boot
- Auth0 JSON Web Token (JWT)
- Hibernate
- ModelMapper
- Javax Validation
- Hibernate Validator
- Lombok
- Maven