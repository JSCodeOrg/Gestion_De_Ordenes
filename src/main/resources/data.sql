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

INSERT INTO estados (nombre_estado) VALUES
('POR_REVISAR'),
('EN_PROCESO'),
('APROBADO'),
('RECHAZADO'),
('PENDIENTE');

INSERT INTO orden_devolucion (orden_id, motivo_devolucion, estado_id, total_reembolso) VALUES
(1, 'Producto defectuoso', 1, 250.00),
(2, 'Cambio de opinión', 2, 300.50),
(3, 'No era lo que esperaba', 3, 500.75),
(4, 'Producto dañado', 4, 150.25),
(5, 'Producto no funciona', 5, 200.00);

INSERT INTO productos_devueltos (devolucion_id, producto_id, cantidad)
VALUES 
(1, 101, 1), 
(2, 103, 1),  
(3, 105, 1),  
(4, 107, 1),  
(5, 109, 1);  


INSERT INTO imagenes_devolucion (orden_devolucion_id, url_imagen)
VALUES 
(1, 'https://cdn.misitio.com/devoluciones/foto1.jpg'),
(2, 'https://cdn.misitio.com/devoluciones/foto2.jpg'),
(3, 'https://cdn.misitio.com/devoluciones/foto3.jpg'),
(4, 'https://cdn.misitio.com/devoluciones/foto4.jpg'),
(5, 'https://cdn.misitio.com/devoluciones/foto5.jpg');
*/