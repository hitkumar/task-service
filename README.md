## Task Service

Tradeshift is a platform which allows individuals to be successful in solving business-related tasks. More often than not, these tasks are solved in collaboration between multiple people.

Towards that vision, you will be building a new Task Service. The service should provide a REST API that allows callers to create, retrieve, update and delete tasks.

## Instructions

The goal is to get a basic RESTful Task Service running. You should implement the solution in Java, using JDBC and a relational database for the underlying persistence layer. Additional technology choices are up to you.

Approach this project as you would if you were to deploy to production at the end - focus on quality before throwing in many new features. Identify the core part of the service, and get that functional. Then prioritize further use cases as you see appropriate.

As you make assumptions during planning and implementation, just make a note of each and be ready to discuss them. There are many unknowns in this exercise - just like real projects.

We work iteratively, and we encourage team feedback as a driver of code quality and correctness. You are not expected to build something perfect, but we do expect a running, working service. You are expected to be thoughtful in your decisions, and make progress towards something that could be shipped to users with confidence.

## Use cases

At the core, a taskCreateInput is simply a common todo item. Since tasks on Tradeshift are used to drive actions on the platform, our tasks are associated to other objects on the platform - e.g. a taskCreateInput to approve a received invoice, a taskCreateInput to validate a W-9 received by a supplier, a taskCreateInput to add additional information to my company profile, etc. Keep this model in mind as you design your Task Service.

Some core use cases for a Task Service are:

* As a Tradeshift API consumer, I want to create a taskCreateInput.
* As a Tradeshift API consumer, I want to assign a taskCreateInput to a user.
* As a Tradeshift API consumer, I want to complete a taskCreateInput.
* As a Tradeshift API consumer, I want to fetch all tasks assigned to me.

## Get started

You should use this project as the base for your Task Service.

```sh
# Clone the project
$ git clone git@github.com:Tradeshift/interview-taskCreateInput-service.git

# Get into the project directory
$ cd interview-taskCreateInput-service

# Assuming you have PostgreSQL setup locally
# Get a postgres command prompt to create a database user
$ psql

# Create a database user and set the password
postgres=# CREATE USER taskuser WITH PASSWORD 'taskpassword';

# Create the database tasks with the owner taskuser
postgres=# CREATE DATABASE tasks OWNER taskuser;

# Quit postgres
postgres=# \q

# Run tests
$ mvn test

# Start the service
$ mvn jetty:run

# Confirm a response from the service
$ curl http://localhost:8080/hello
Shift happens!

# Save a message to confirm writing to the database works
$ curl -X POST http://localhost:8080/hello/post

# Get the message back
$ curl http://localhost:8080/hello/get
It works!
```

## On-site interview

The work done on this project will guide the technical interview. We will ask you to show your work and explain your decisions. Think of this exercise as normal work you would have as part of our team, and the interview as the conversation that would occur during a sprint demo.

This first conversation will take about 30 minutes. Then you will be given about an hour to implement something new. This may be an additional feature, adding more unit tests to cover unknown cases, a redesign because an assumption was corrected, a refactoring for performance, etc. It will depend on how the conversation goes. Come with a few ideas of how you could make the existing code better.

Then we will have a second conversation, similar to the first. We will discuss the newly implemented code, and wrap up the assignment.
