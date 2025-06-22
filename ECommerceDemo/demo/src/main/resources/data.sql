INSERT INTO products (name, price, description) VALUES ('Laptop', 999.99, 'High-performance laptop');
INSERT INTO products (name, price, description) VALUES ('Smartphone', 699.99, 'Latest smartphone');
INSERT INTO products (name, price, description) VALUES ('Headphones', 149.99, 'Noise-cancelling wireless');
INSERT INTO products (name, price, description) VALUES ('Tablet', 299.99, '10-inch portable tablet');

-- Also, ensure other INSERTs for customers and cart_items use correct plural table names:
INSERT INTO customers (name, email) VALUES ('Alice Smith', 'alice@example.com');
INSERT INTO customers (name, email) VALUES ('Bob Johnson', 'bob@example.com');

-- For cart_items, ensure foreign keys reference the correct tables and existing IDs
-- Assuming product IDs 1, 2, 3, 4 and customer IDs 1, 2 from above inserts
INSERT INTO cart_items (product_id, customer_id, quantity) VALUES (1, 1, 1);
INSERT INTO cart_items (product_id, customer_id, quantity) VALUES (2, 1, 2);
INSERT INTO cart_items (product_id, customer_id, quantity) VALUES (3, 2, 1);