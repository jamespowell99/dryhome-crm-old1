# Script to migrate from existing DB to new one - currently DampProofers, products, orders and order items

INSERT INTO `dryhome`.`T_CUSTOMER`
(`id`,
`name`,
`contact_title`,
`contact_first`,
`contact_surname`,
`tel`,
`mob`,
`address1`,
`address2`,
`address3`,
`town`,
`post_code`,
`products`,
`interested`,
`paid`,
`notes`)
select id, `Company Name:`, title, first, surname, `tel no`, mobile, `Address 1`, `Address 2`, address3, town, `post code`, products, interested, paid, notes  from dryhome_existing.damp_proofers t;

insert into dryhome.t_product (id, name, description)
select prod_id, prod_name, prod_description from dryhome_existing.products;


INSERT INTO `dryhome`.`T_ORDER`
(`id`,
`order_number`,
`order_date`,
`dispatch_date`,
`invoice_date`,
`placed_by`,
`method`,
`invoice_number`,
`invoice_notes1`,
`invoice_notes2`,
`notes`,
`payment_date`,
`payment_status`,
`payment_type`,
`payment_amount`,
`vat_rate`,
`customer_id`)
select order_id, order_number, date, despatch_date,
invoice_date, placed_by, method, invoice_number, notes1,
notes2, internal_notes, payment_date, payment_status,
payment_type, payment_amount, vatRate, company_id from dryhome_existing.dp_orders
;

INSERT INTO `dryhome`.`T_ORDERITEM`
(`id`,
`price`,
`qty`,
`notes`,
`order_index`,
`serial_number`,
`orderid_id`,
`product_id`)
select id, price, qty, notes, `order`, serial_number, order_id, prod_id
from dryhome_existing.dp_order_items

