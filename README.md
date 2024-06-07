# What is device-store?
DeviceStore is a comprehensive application designed to streamline 
the management of an online shop and enhance the shopping 
experience for customers purchasing devices.

Admin Endpoints
1. ```/api/v1/admin/device/add```
This POST require a @RequestBody with device information to be created
in the database. 
2. ```/api/v1/admin/update/{externalId}```
The @PathVariable describes the externalId of the device and
@RequestBody with information to be updated.
3. ```/api/v1/admin/change-price/{externalId}```
The prices of the device can be updated using this PATCH method.
The @PathVariable describes the externalId of the device and PriceRequest
request body contains the prices.


Client Endpoints
1. ```/api/v1/device/details/{externalId}```
Returns details for a specific device based on externalId.
This GET method has a @PathVariable and returns details such as
name of the device, details, finalPrice, referencePrice, thumbnail
category and stockNumber.

2. ```/api/v1/device/all```
This GET method returns all devices ordered descending by creation date.

3. ```/api/v1/device/buy```
This PUT method has @RequestBody containing DeviceBuyRequest
with all the information completed by the client for their purchase.
It returns the information completed in order to be extra checked and other 
addition information about the purchased device like price, details and name.

4. ```/api/v1/device/pay/{orderId}```
This is a PUT payment method which mock the payment process.
The path variable in this endpoint describe the orderId for which the payment is done.
Before paying the amount, the reservation time is checked. If the reservation is over, the
client must restart the process of buying with the new update price.
Returns the status of the payment in order to be shown in application.

Future release
1. Role-based access control (RBAC) for admin/user logic using the Spring AOP framework
