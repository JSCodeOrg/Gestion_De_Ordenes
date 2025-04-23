
/*
INSERT INTO ordenes (order_code, total_amount, shipping_address, user_id, created_at, status)
VALUES 
('ORD-20250422-001', 500.00, 'Calle Falsa 123', 1, NOW(), 'COMPLETADA'),
('ORD-20250422-002', 150.00, 'Av. Libertad 456', 2, NOW(), 'PENDIENTE'),
('ORD-20250422-003', 250.00, 'Calle Real 789', 3, NOW(), 'COMPLETADA'),
('ORD-20250422-004', 450.00, 'Calle Mayor 101', 4, NOW(), 'PENDIENTE'),
('ORD-20250422-005', 350.00, 'Calle Secundaria 202', 5, NOW(), 'COMPLETADA');

INSERT INTO productos_orden (orden_id, producto_id, nombre_producto, precio_producto, cantidad, total)
VALUES 
(1, 101, 'Teclado Mecánico', 100.00, 2, 200.00),
(1, 102, 'Mouse Inalámbrico', 50.00, 2, 100.00),
(2, 103, 'Auriculares Bluetooth', 70.00, 1, 70.00),
(2, 104, 'Pantalla LED 24"', 100.00, 1, 100.00),
(3, 105, 'Laptop Gaming', 150.00, 1, 150.00),
(3, 106, 'Silla Ergonómica', 100.00, 1, 100.00),
(4, 107, 'Monitor Curvo', 200.00, 2, 400.00),
(4, 108, 'Teclado mecánico RGB', 75.00, 1, 75.00),
(5, 109, 'Smartphone Android', 300.00, 1, 300.00),
(5, 110, 'Funda para teléfono', 50.00, 1, 50.00);

INSERT INTO orden_devolucion (orden_id, fecha_devolucion, motivo_devolucion, estado_devolucion, total_reembolso)
VALUES
(1, NOW(), 'Producto defectuoso', 'POR_REVISAR', 100.00),
(2, NOW(), 'No lo quiero', 'EN_PROCESO', 150.00),
(3, NOW(), 'Producto dañado', 'POR_REVISAR', 250.00),
(4, NOW(), 'Cancelado por cliente', 'APROBADO', 450.00),
(5, NOW(), 'Producto erróneo', 'PENDIENTE', 350.00);


INSERT INTO productos_devueltos (devolucion_id, producto_id, cantidad)
VALUES 
(1, 101, 1),  -- 1 teclado devuelto
(2, 103, 1),  -- 1 auricular devuelto
(3, 105, 1),  -- 1 laptop devuelta
(4, 107, 1),  -- 1 monitor devuelto
(5, 109, 1);  -- 1 smartphone devuelto


INSERT INTO imagenes_devolucion (orden_devolucion_id, url_imagen)
VALUES 
(1, 'https://cdn.misitio.com/devoluciones/foto1.jpg'),
(2, 'https://cdn.misitio.com/devoluciones/foto2.jpg'),
(3, 'https://cdn.misitio.com/devoluciones/foto3.jpg'),
(4, 'https://cdn.misitio.com/devoluciones/foto4.jpg'),
(5, 'https://cdn.misitio.com/devoluciones/foto5.jpg');
*/