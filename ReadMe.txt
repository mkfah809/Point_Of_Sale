If you are running the application for the first time please follow these instructions: 

1. COPY/PASTE thses following SQL statements in  MySQL:

	INSERT INTO `hotstone_pos`.`employees` (`password`, `title`, `username`) VALUES ('$2a$10$XJrPkYn2bDhDqMSZ3B5MAODwcRQygAuV8SANbR.dRJLzckay2sZ.y', 'Manager', 'mfahmy');
	INSERT INTO `hotstone_pos`.`authorities` (`authority`, `emp_id`) VALUES ('ROLE_ADMIN', '1');

2. In application.properties file

	Change spring.jpa.hibernate.ddl-auto = update INSTEAD OF  spring.jpa.hibernate.ddl-auto = create

3. Add Toppings through Dashboard page.