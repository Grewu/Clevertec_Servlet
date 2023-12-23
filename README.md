## Task Servlet
## Endpoints:
### Check
- /untitled/pdf
    - params:
        - json
    - status: 200
    - response: pdf
### Products:
- #### GET
    - /untitled/controller
        - status: 200
        - params: uuid
            - response: application/json
              
    - /untitled/controller?uuid=all
        - status 200
        - params: uuid=all
    
    - /untitled/controller?uuid=all&page=1&pageSize=5
        - status 200
    
- #### POST
    - untitled/controller
        - request:
          <pre>{
             "uuid": "06c05096-2f5a-424f-b472-446b32281318",
             "name": "Product120",
             "description": "Description119",
             "price": 12.50,
             "created": "2023-12-20 13:47:00.000"
          }</pre>

- #### PUT
    - /untitled/controller
        - request:
          <pre>{
             "uuid": "06c05096-2f5a-424f-b472-446b32281318",
             "name": "Product119",
             "description": "Description119",
             "price": 12.50,
             "created": "2023-12-20 13:47:00.000"
          }</pre>
- #### DELETE
    - /untitled/controller?{id}
        - status: 204