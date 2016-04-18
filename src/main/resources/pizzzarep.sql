CREATE TYPE PizzaType AS ENUM ('meat', 'sea', 'vegetarian');
create table Pizzas(
 id SERIAL PRIMARY KEY NOT NULL,
 name TEXT NOT NULL,
 price REAL NOT NULL,
 pizzaType PizzaType 
)