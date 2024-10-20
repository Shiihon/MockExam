Questions answered with README.md tag: 
------------------------------------------------------------------------------------------------------------------------
2.1

GET /api/plants : 
NullPointerException – If plantDAO.getAll() returns null.

GET /api/plants/{id} :
NumberFormatException – If the ID in the path isn’t a valid Long, this one will slap you in the face when parsing(not handled).
EntityNotFoundException - if the plant with the specified id isn't there (is handled in this project).

GET /api/plants/type/{type} : 
Same as the one before.

POST /api/plants :
IllegalArgumentException – If ctx.bodyAsClass(PlantDTO.class) gets invalid input or if the data is somehow incompatible.
------------------------------------------------------------------------------------------------------------------------
3.4

The Stream API in Java is inspired by the functional programming paradigm. 
It encourages stateless operations, and the use of higher-order functions (like `map`, `filter`, `reduce`),
to process data in a declarative and more concise way.
All in all Functional programming is all about treating your code like a chain of data transformations, 
which the stream API does.

------------------------------------------------------------------------------------------------------------------------



