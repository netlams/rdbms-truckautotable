CREATE DATABASE Dealership;

USE Dealership;

CREATE TABLE Truck
(
	VIN char(9) not null,
	make varchar(15),
	model varchar(15),
	color varchar(15),
	year date,
	cabSize double,
	baySize double,
	price double,
	PRIMARY KEY (VIN)
);

insert into Truck values ('ok2', 'Honda', 'Civic', 'Lime', '2008-12-12', '12', '11', '25000');
insert into Truck values ('NETNSO90G', 'Ford', 'F-150', 'Red', '2012-02-12', '4', '2', '27000');
insert into Truck values ('IM1', 'KIA', 'Rio', 'Silver', '2009-12-12', '4', '4', '24000');
insert into Truck values ('new9', 'Lexus', 'R19', 'Red', '2008-12-12', '12', '11', '32000');

select * from truck;

select make, model, color, year, cabSize, baySize, price from Truck where Truck.VIN ='ok2';

truncate truck;

update truck
	set make = 'new make'
	where VIN = 'ok2';
	
delete 
	from truck
	where VIN = 'ok2';	
	

