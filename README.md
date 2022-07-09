## Delivery API

#### Models
For Address table I decided create multi fields primary key (country, city, postcode, street and housenumber).<br>
For Timeslot table I added field "version" for managing concurrent requests. Also, I added field "supportedPostcodes" - list of postcodes which supported by timeslot.
And "deliveriesCount" was added for managing amount of deliveries.

#### Endpoints
In project structure you can find postman collection for testing this API.
1) You can send request<br>POST localhost:8080/api/initializeTimeslots<br>for initializing timeslots from courierAPI.json file.<br>
I used https://holidayapi.com/ for excluding timeslots which fall on holidays.
2) For  booking delivery I decided do not create table for users and use this structure for request body:<br>
{
"user":{
"username":"",
"address": {
}
},
"timeslotId": ""
}<br>
3) You can get structured address from request: localhost:8080/api/resolve-address<br>
I used https://holidayapi.com/docs for structure one-line address from request
4) Other endpoints work like in test description.



