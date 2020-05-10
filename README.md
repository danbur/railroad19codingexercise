## API Demo Challenge

#### Project Setup
1. Clone this project.
2. Setup the project in your IDE.
3. From command line run mvn clean install -U -DskipTests
5. Make sure you can run the DemoTest

#### Technologies
1. Java
2. Rest Assured
3. TestNG
4. Any other technologies you see fit.
5. Please do not use a BDD framework.

#### Expectations
We will be evaluating
1. Quality of test cases
2. Variety  of testing types (examples: boundary, happy path, negative, etc)
3. Code structure and organization
4. Naming conventions
5. Code readability
6. Code modularity


#### Exercise
1. Review the spec in the root directory, PizzaAPIReferenceDoc.  API endpoints for this exercise can be found here
   https://my-json-server.typicode.com/sa2225/demo/ 
   This is a demo endpoint and not fully implemented.  Please write your code and test cases against the spec.  Any test cases that are failing because of the end point implementation please add a comment in the code noting the failure and why.
2. In the Read me file, write up all of the test cases you think are necessary to test the endpoints defined in the provided spec.
3. Code up a few examples of 
  - order get call including response validation
  - order post call
4. When complete please check your code into a public git repo

#### Test Cases

##### Functional Tests:

###### Toppings

*GET /toppings*

**Happy path:**

* Get all toppings with one topping in the list
* Get all toppings with zero toppings in the list
* Get all toppings with more than one topping in the list
* Validate response format

*POST /toppings*

**Happy path:**

* Create a new topping:
    * Verify the response code is 204
    * Validate response format 
    * Send a GET request, and verify the topping now appears in the response
* Verify you can create a topping with a null name.
* Verify you can create a topping whose name is the empty string.

(If the API does not allow the  last two cases, I would write negative tests instead – the API specification
does not specify the behavior.)

**Negative tests:**

* Attempt to create a topping with no name – verify that this results in an error
* Verify that attempting to create a duplicate topping results in a 405 error response
* Send garbage data and verify that this results in an error. Examples:
    * A JSON Array instead of a single object
    * A single string/number instead of an object

(I would not include test cases like this last one, if I knew more about the implementation of the service – for example that it was deserializing the response into POJOs with a widely used and well-tested library such as Jackson.  Then this might not be necessary.)

*DELETE /toppings/{id}*

**Happy path:**

* Delete a previously created topping
    * Verify the response code is 204
    * Attempt to GET the topping, and verify that this results in a 404
* Attempt to delete the same topping twice.

(I would need to verify the behavior on multiple deletes of the same topping -  the API specification does not state
what happens.)

**Negative tests:**

* Verify that attempt to delete a topping using an ID that never existed results in a 400/404

###### Pizzas

*GET /pizzas*

Verify GET works correctly:
* Verify status code is 200
* Verify response format

###### Orders

*GET /orders*

* Verify GET works correctly
    * Verify status code is 200
    * Verify the response format
* Verify that an order previously created via the POST endpoint appears in the list.

If it was possible to start with a clean environment, I would try to GET with zero, one, and more than one orders.  There is no DELETE endpoint, so it is not possible for me to clean up after the tests or clear state.

*GET /orders/{id}*

**Happy path:**

* Verify you can retrieve an order that exists by ID
    * Verify that status code is 200
    * Verify the response format

**Negative tests:**

* Verify you can retrieve an order that does not exist, and verify this results in 404

*POST /orders*

**Happy path:**

* Create an order with zero pizzas and verify that the status code is 201 (need to verify this allowed)
* Create an order a single pizza that has no toppings and verify that the status code is 201
* Create an order a single pizza that has one topping and verify that the status code is 201
* Create an order a single pizza that has multiple toppings and verify that the status code is 201
* Create an order with multiple pizzas and verify that the status code is 201 (include all the various types – zero toppings, one topping, and multiple toppings)

For all of these test cases, also retrieve the order from the GET/orders/{id} and make the response matches the order sent in the POST request

**Negative tests:**

* Attempt to create an order with toppings for a pizza that has no toppings, and verify this results in 406
* Attempt to create an order with no toppings for a pizza that has one topping, and verify this results in a 406
* Attempt to create an order with two toppings for a pizza that only has one topping, and verify that this results in a 406
* Attempt to create an order with only one topping for a pizza that has two toppings, and verify that his results in a 406 
* Attempt to create an order with a pizza name that does not exist and verify this results in a 407
* Attempt to create an order with no pizza name and verify this results in a 408
* Attempt to create an order with a topping name that does not exist, and verify this results in a 400/404
* Attempt to create an order with no toppings list and verify that this results in an error
* Send garbage data and verify that this results in an error. Examples:
    * A JSON Array instead of a single object
    * A single string/number instead of an object

(Again, I would not include test cases like this last one, if I knew the service was deserializing the request data to
an object with some widely used library.)

##### Non-Functional Tests:

###### Soak tests:

* Verify that you can create, retrieve, and delete an topping 100 times in a row (or some higher number)
* Verify you can create 100 orders in a row (or some higher number) and all of them appear in the list, when you call the GET orders endpoint.

I would also do performance and load testing, but that is beyond the scope of this project (I would use different tools for that.)

