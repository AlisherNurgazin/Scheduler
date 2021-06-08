### API documentation
#### Table for mapping apis
- [Register](#register)
- [Login](#login)
- [Add schedule](#add-schedule)
- [Edit schedule](#edit-schedule)
- [Get by id](#get-by-id)
- [Mark as done or not](#mark-as-done-or-not)
- [Delete schedule](#delete-schedule)
- [Get by date](#get-by-date)
- [Get for today](#get-for-today)
- [Get all schedules](#get-all-schedules)


### Register

POST http://localhost:8080/api/v1/public/register

#### HTTP Request body

```
{
  "fullName":"String",
  "login":"String",
  "password":"String"
}
```



### Login

POST http://localhost:8080/api/v1/public/login


#### HTTP Request body

```
{
  "login":"String",
  "password":"String"
}
```


### Add schedule

POST http://localhost:8080/api/v1/schedule/add

#### HTTP Request body

```
{
  "name":"String",
  "description":"String",
  "certainTime":"LocalDateTime (ISO)"
}
```


### Edit schedule

PUT http://localhost:8080/api/v1/schedule/edit

#### HTTP Request body

```
{
  "id":"Long",
  "name":"String",
  "description":"String",
  "certainTime":"LocalDateTime (ISO)"
}
```


### Get by id

POST http://localhost:8080/api/v1/schedule/by/id

#### HTTP Request body

```
{
  "id":"Long"
}
```


### Mark as done or not

PUT http://localhost:8080/api/v1/schedule/mark

#### HTTP Request body

```
{
  "id":"Long"
}
```


### Delete schedule

DELETE http://localhost:8080/api/v1/schedule/delete/{id}

id => Long


### Get by date

POST http://localhost:8080/api/v1/schedule/get/by/date

#### HTTP Request body

```
{
  "from":"LocalDateTime (ISO)",
  "to":"LocalDateTime (ISO)"
}
```


### Get for today

GET http://localhost:8080/api/v1/schedule/for/today



### Get all schedules

GET http://localhost:8080/api/v1/schedule/all
