# Products-microservice

A brief description of what this project does and who it's for

This project provides well defined API's to perform CRUD operations on product inventory database.

For POST, DELETE and PUT opearations. Basic auth has to be included in request.

username : admin
password : admin12345

## API Reference

#### Get all products present in DB.

```http
  GET api/api/products/getAllProducts
```


#### Get a product by sku.

```http
  GET api/products/getBysku/${sku}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `sku`      | `string` | **Required**. sku of product to fetch |

#### Create a product.

```http
  POST api/products/create
```
Input :

**Required** : title, price, sku. 

```
{
  "sku": "string",
  "title": "string",
  "price": 0,
  "trending": true,
  "inStock": 0,
  "picName": "string",
  "picType": "string"
}
```

Output : Returns created product data.

If user doesn't provide values for required feilds in input JSON then following response will be shown.

```
{
    "timestamp": "2023-11-10T19:02:43.1257471",
    "message": "Validation failed for one or more Product properties.",
    "path": "uri=/api/products/create",
    "errors": {
        "price": "Product price cannot be empty.",
        "sku": "sku cannot be empty.",
        "title": "Product title cannot be empty."
    },
    "errorCode": "VALIDATION_FAILED"
}
```
#### Create many products in single API call.

```http
  POST api/products/createmany
```

Input: List of products. Each product is a JSON used in `POST api/products/create` API call.

**Required** : title, price, sku.

```
[{},{}]
```

Output : Returns List of created products.

If user doesn't provide values for required feilds in input JSON then following response will be shown.

Ex: 

Input :

In List[0], title is empty.
In List[1], sku is empty.
In List[2], price is empty.

Output :

```
{
    "timestamp": "2023-11-10T19:15:57.8630656",
    "message": "Validation failed for one or more Product properties.",
    "path": "uri=/api/products/createmany",
    "errors": {
        "product[1].sku": "sku cannot be empty.",
        "product[0].title": "Product title cannot be empty.",
        "product[2].price": "Product price cannot be empty."
    },
    "errorCode": "VALIDATION_FAILED"
}
```
#### Update a product.

```http
PUT api/products/update
```

Input :

**Required** : title, price, sku. 

```
{
  "sku": "string",
  "title": "string",
  "price": 0,
  "trending": true,
  "inStock": 0,
  "picName": "string",
  "picType": "string"
}
```

Output : Returns updated product.

Finds a product by sku. If user provides wrong sku in request. Then following response will be shown.

```
{
    "timestamp": "2023-11-10T19:26:48.9250331",
    "message": "Product not found with sku : P00",
    "path": "uri=/api/products/update",
    "errorCode": "PRODUCT_NOT_FOUND"
}
```

#### Update many products in single API call.

```http
  PUT api/products/updateProducts
```

Input: List of products. Each product is a JSON used in `PUT api/products/update` API call.

**Required** : title, price, sku.

```
[{},{}]
```

Output : Returns List of created products.


#### Delete All products.

```http
DELETE api/products/deleteAll
```

Input : none.

Output : none.

#### Delete a product by sku.

```http
  DELETE api/products/delete/${sku}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `sku`      | `string` | **Required**. sku of product to delete |

If product not found with sku, then following response will be shown.

```
{
    "timestamp": "2023-11-10T19:26:48.9250331",
    "message": "Product not found with sku : P00",
    "path": "uri=/api/products/update",
    "errorCode": "PRODUCT_NOT_FOUND"
}
```

#### Store Product image for a product identified by product-sku.

```http
  POST api/products/uploadPic/${sku}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `sku`      | `string` | **Required**. sku of product to update. |

Input : form-data. Key : image

Output : returns the product for which image was added.

#### Get the product image for a product identified by product-sku.


```http
  GET api/products/getImage/${sku}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `sku`      | `string` | **Required**. sku of product to fetch. |

Output : Image.

## Authors

- Jagadish M
