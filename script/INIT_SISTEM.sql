INSERT INTO company (enabled,last_user,name_company,nit, last_time) VALUES
	 (1 ,'admin','REST-CALLE7','85928592', NOW());

INSERT INTO profile (company_id,description,enabled,last_time,last_user,name) VALUES
	 ((select c.company_id from company c where c.nit = '85928592'),'Cajero',1, NOW(),'admin','ROLE_CAJERO'),
	 ((select c.company_id from company c where c.nit = '85928592'),'Admin',1, NOW(),'admin','ROLE_ADMIN');


INSERT INTO `domain` (description,enabled,group_dom, last_user,name,value,company_id, last_time) VALUES
	 ('Tipo plato',1,'PRODUCT_TYPE', 'admin','PLATO','Platos',(select c.company_id from company c where c.nit = '85928592'), NOW()),
	 ('Tipo refresco',1,'PRODUCT_TYPE', 'admin','REFRESCO','Refrescos',(select c.company_id from company c where c.nit = '85928592'), NOW()),
	 ('Configuracion de empresa',1,'CONFIGURATION', 'admin','DEFAULT_COMPANY','85928592',(select c.company_id from company c where c.nit = '85928592'), NOW()),
	 ('Configuracion Fecha Ticket',1,'CONFIGURATION', 'admin','SEQUENCE_DATE','2024-12-26',(select c.company_id from company c where c.nit = '85928592'), NOW()),
	 ('Configuracion Ticket',1,'CONFIGURATION', 'admin','SEQUENCE_TICKET','2',(select c.company_id from company c where c.nit = '85928592'), NOW()),
	 ('Tipo plato pedidos ya',1,'PRODUCT_TYPE', 'admin','PLATO PEYA','Platos PedidosYa',(select c.company_id from company c where c.nit = '85928592'), NOW());


INSERT INTO `domain` (description,enabled,group_dom, last_user,name,value,company_id, last_time) VALUES
	 ('EFECTIVO',1,'PAYMENT_TYPE', 'admin','PAGO_EFECTIVO','Platos',(select c.company_id from company c where c.nit = '85928592'), NOW()),
	 ('QR',1,'PAYMENT_TYPE', 'admin','PAGO_QR','Platos',(select c.company_id from company c where c.nit = '85928592'), NOW()),
	 ('LINEA',1,'PAYMENT_TYPE', 'admin','PAGO_LINEA','Refrescos',(select c.company_id from company c where c.nit = '85928592'), NOW());