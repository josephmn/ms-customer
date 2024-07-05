# Swagger Project - OpenAPI 3.0

Esta es una version de un API creada con mongo DB, usando programacion reactiva y programación funcional para una mantenimiento de clientes.

## Instalación

1. Abrir con su ID preferido e instalar las dependencias.
2. El aplicativo esta conectado a un cluster de mongodb Atlas en la nube.

## Uso

### Customer (the customer API Methods)
- **GET - customer all (without parameters):**
    ````
    *** METHOD: GET ***
    URL: http://localhost:8080/api/v1/customer
    ````
- **GET - find by id customer:** change number **6685db4a7ac9347f4ba6b975** for the customer id
    ````
    *** METHOD: GET ***
    URL: http://localhost:8080/api/v1/customer/6685db4a7ac9347f4ba6b975
    ````
- **POST - save customer with body in JSON:**
    ````
    *** METHOD: POST ***
    URL: http://localhost:8080/api/v1/customer
    ````
    ````
    {
        "name": "Joseph",
        "lastName": "Magallanes",
        "documentType": "DNI",
        "documentNumber": "77885471",
        "clientType": "STAFF"
    }
    ````
- **PUT - update customer with body in JSON and variable path (6685db4a7ac9347f4ba6b975):**
    ````
    *** METHOD: PUT ***
    URL: http://localhost:8080/api/v1/customer/6685db4a7ac9347f4ba6b975
    ````
    ````
    {
        "name": "Joseph",
        "lastName": "Magallanes",
        "reason": "",
        "documentType": "DNI",
        "documentNumber": "77885471",
        "clientType": "STAFF"
    }
    ````
- **DELETE - delete customer with variable path (6685db4a7ac9347f4ba6b975):**
    ````
    *** METHOD: DELETE ***
    URL: http://localhost:8080/api/v1/customer/667c42cbd3fc7a6ce9e1b893
    ````