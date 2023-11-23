# README

**This project is a code challenge.**

## Guide
Execute `./mvnw spring-boot:run` from the `elk` folder.

This API is configured to run on port 8083. To call it, use the endpoint: `http://localhost:8083/rest/api`.

### Endpoints

- **POST:** `http://localhost:8083/rest/api`
    - Creates an index with the specified name provided in the request body.

- **POST:** `http://localhost:8083/rest/api/{indexname}/document`
    - Creates a document related to the specified index (provided as a parameter) with the following body structure: `{ id: number, technology: string, selfRating: string }`.

- **GET:** `http://localhost:8083/rest/api/{indexname}/document/{documentid}`
    - Retrieves a document by its id.

## Extra
Though not necessary, two additional endpoints have been added for informational purposes:

- **GET:** `http://localhost:8083/rest/api`
    - Displays a list of created indexes.

- **DELETE:** `http://localhost:8083/rest/api`
    - Deletes all created indexes.

## Demo

The `Demo.mkv` file is a demonstration of the REST API working.