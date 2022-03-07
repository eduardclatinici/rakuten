README

To run cep-campaign and cep-points in docker containers you need to follow these steps:

1) make sure Docker Desktop is running
2) open terminal in current folder (rakuten)
3) run: docker-compose up
4) applications are available:
	cep-campaign: localhost:8080/campaign
	cep-points: localhost:8080/pointstransaction
	(inside cep-points there is also localhost:8080/pointsbalance/{email} to get a user points balance)
	
	step 3 will raise 4 containers: one for postgresql, one for nginx(for reverse proxy), and 2 for cep-points and cep-campaign

Notes:

	I noticed there are some differences between BackEndCodingTest.pdf and postman collections in terms of enpoint paths
		and I chose to follow postman collections
	I also noticed that every post had the id of the entities that were going to be saved in the database and i chose to 
		ignore it totally because it should be the database responsability to manage ids when creating new entities
	In my opinion pointsTransactionDTO (the wrapper for orderDTO) did not have a clear purpose so I ignored it totally, 
		therefore both "delete a points transaction by order id" and "delete a points transaction by id" require
		the orderId (instead of pointstransactionid for "delete a points transaction by id")
	In any case, i tried to make the endpoints flexibles so that they will receive the body exactly as they were specified
		in the original postman collections but they ignore some fields (for example, when creating a pointstransaction 
		with some orderItems composed from valid products, i only work with those product ids and ignore their name and
		price <- this fields are populated on response but don't really need them on the request)
	When updating an point transaction make sure that if you add a new orderItem(with a valid product) the id of that 
		orderItem is not populated.
	When the database is started in the corresponding docker container it should also pre-populate cep_campaign.product
		and cep_points.customer tables with 10 entries each for easy testing.
	In case there are some differences between the original postman collections and what I tested with i will leave here
		my postman collections as well.
	
Feedback:

1) I don't really see the need of creating 2 microservices when these homework could be done more easily with a single 
	microservice composed of 2 modules (cep-campaign and cep-points) and an app-starter
2) A microservice should have it's own database (The requirements document states that cep-campaign and cep-product
	share a database. To simulate 2 databases without having run 2 postgres instances I created a dedicated schema for 
	each microservice. cep-campaign has access only to cep-campaign schema and cep-points only to cep-points schema).
	Because I did not want these microservices to share a database i created and extra endpoint in cep-campaign used 
	by cep-points to get information about the products (see database schemas model in this folder)
3) Some endpoint paths are not restful (e.g. get for localhost:8080/pointstransaction/1 should have pointstransaction"S"
	to make sure that if /1 is not present then we will get ALL pointstransation"S", or localhost:8080/campaign/1
	with campaign"S")
