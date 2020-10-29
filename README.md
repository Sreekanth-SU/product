# product
This project provides an Rest API to fetch list of products from an in memory Database . The products can be filtered using different query paramaters.

# Prerequisites

Please Make Sure Docker is installed and Java 11 is available on your system.

# Steps to build and run the project.

1. Open the project as maven project with java 11. Go to project root folder from the IDE terminal and run the below command as the mvn wrapper is attached.

 # mvnw clean install
 
2.Once the above step is successfully run. You will be able to find a jar at the location \target\product-0.0.1-SNAPSHOT.jar . If you want to run 
the project locally without docker. Please use the below command from the root folder. 

# mvnw spring-boot:run

3.In case of docker. Go to the root folder of project where the docker file is present in this project and run the below command which will build an image for running the container.

# docker build -t product .

4. Once the image has been built. You should be able to see the image using below command.

# docker images

5.Run the application using with below command which will create a running container and the details can be seen as shown below. I have used port 8085 to expose the api, You can use whichever port you want.

 # docker run -p 8085:8080 product
 
 6. You should be able to see the data at http://localhost:8085/product. Available query params are 
 
Query Parameter			Description <br/>
type					The product type. (String. Can be 'phone' or 'subscription') <br/>
min_price				The minimum price in SEK. (Number) <br/>
max_price				The maximum price in SEK. (Number) <br/>
city					The city in which a store is located. (String) <br/>
property				The name of the property. (String. Can be 'color' or 'gb_limit') <br/>
property:color			The color of the phone. (String) <br/>
property:gb_limit_min 	The minimum GB limit of the subscription. (Number) <br/>
property:gb_limit_max 	The maximum GB limit of the subscription. (Number) <br/>
 
 
