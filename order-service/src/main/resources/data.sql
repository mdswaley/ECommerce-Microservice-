INSERT INTO orders (total, orders_status) VALUES
(499.99, 'CONFIRM'),
(699.00, 'PENDING'),
(150.75, 'CANCELLED'),
(320.00, 'CONFIRM'),
(89.50, 'PENDING'),
(560.25, 'CONFIRM'),
(120.00, 'CANCELLED'),
(215.40, 'PENDING');

INSERT INTO order_items (order_id, product_id, quantity) VALUES
(1, 101, 2),
(1, 102, 1),
(2, 103, 3),
(2, 104, 1),
(3, 105, 5),
(3, 106, 2),
(4, 107, 4),
(5, 108, 1);
