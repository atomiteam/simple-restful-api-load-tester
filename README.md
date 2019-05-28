# simple-restful-api-load-tester


Simple RESTful API Load Tester is a  lightweight load testing tool for RESTful APIs. 

It operates on top a YAML configuration file which needs to reside in the current folder of the jar execution.

A sample file YAML content and description of its elements is given below.

```yml
# This says how many agents should be waked up
agents: 2
# Ramp up duration between agents
wait: 1000
# Any global variable to be used in request headers, body and url can be defined here
variables:
    host: https://myapihost.com/
# Finally tests definitions    
tests:
   # The first item submits credentials to get a token
   # The result is saved into executions keyed with the value of 'id'
   - url: ${globalContext.configuration.variables.host}/oauth/token
     iterations: 1
     wait: 1000
     id: auth
     method: POST
     headers:
       content-type: application/json 
     body: '{"client_id": "admin", "client_secret": "secret", "grant_type": "client_credentials"}'
   # The second request fetches users by making use of the access token received earlier
   - url: ${globalContext.configuration.variables.host}/v3/users
     iterations: 5
     wait: 30000
     method: GET
     headers:
       content-type: application/json 
       Authorization: Bearer ${agentContext.executions.auth.access_token}     
```


## Steps to execute a test run

* Build the package by Maven
* Copy Test.yml and jar file to a folder
* Play with Test.yml as you need
* Execute the following command

~~~~
      java -jar simple-restful-api-load-tester-1.0-jar-with-dependencies.jar
~~~~

## Statistics


Basic statistics for executions are logged into ./logs/stats.log


