create database DB_PCS_18222182;
use  DB_PCS_18222182
create table barang (
	kode_barang bigint not null primary key,
	nama_barang varchar(50),
	stok int not null,
	harga_beli decimal not null,
	harga_jual decimal not null
)

create table supplier (
	kode_supplier bigint not null primary key,
	nama_supplier varchar(50),
	alamat varchar(200)
)

create table beli (
	nota_beli bigint not null primary key,
	kode_supplier bigint foreign key references supplier(kode_supplier),
	TANGGAL datetime
)

create table item_beli (
	nota_beli bigint foreign key references beli(nota_beli),
	kode_barang bigint foreign key references barang(kode_barang),
	jumlah int not null,
	harga_beli decimal not null,
	harga_jual decimal not null,
	CONSTRAINT pk_item_beli PRIMARY KEY (nota_beli,kode_barang)
)

create table jual (
	nota_jual bigint not null primary key,
	tanggal datetime
)

create table item_jual (
	nota_jual bigint foreign key references jual(nota_jual),
	kode_barang bigint foreign key references barang(kode_barang),
	jumlah int not null,
	harga_beli decimal not null,
	harga_jual decimal not null,
	CONSTRAINT pk_item_jual PRIMARY KEY (nota_jual,kode_barang)
)


insert into supplier values (2016090001,'YanixAsia','Jalan Afandi');
insert into supplier values (2016090002,'Anandam Computer','Jalan Gejayan');
insert into supplier values (2016090003,'ASC','Jalan Kaliurang');
insert into supplier values (2016090004,'ELS Computer','Jalan C. Simanjuntak');
insert into supplier values (2016090005,'Aizza Komputer','Jalan Monjali');
insert into supplier values (2016090006,'Mx Komputer Jogja','Jalan Wates');
insert into supplier values (2016090007,'Alnect Center','Jalan Laksa Adisucipto');

--select * from supplier;

insert into barang values (2016072021,'VGA Card AMD Radeon MSI HD 7730 1GB DDR5',5,765000,825000);
insert into barang values (2016072022,'Mouse USB Gaming A4Tech XL-747H',5,190000,235000);
insert into barang values (2016072023,'Memory DDR3 TEAM Zeus 8GB Kit PC1600 CL9',5,905000,960000);
insert into barang values (2016072024,'Processor INTEL Pentium Dual Core G3220 Box',8,360000,570000);
insert into barang values (2016072025,'SSD TEAM Dark L3 120GB Sata III',10,760000,890000);
insert into barang values (2016072026,'Powerbank Cooler Master Power Fort 6600 mAh',3,370000,450000);
insert into barang values (2016072027,'Wireless Router GSM/CDMA 3G/EVDO TP Link TL-MR3040',5,295000,315000);

--select * from barang;

insert into beli values (20161001001,2016090002,'2016-10-01 10:20');
insert into beli values (20161001002,2016090003,'2016-10-01 11:10');
insert into beli values (20161001003,2016090002,'2016-10-02 09:12');
insert into beli values (20161001004,2016090003,'2016-10-03 11:17');

--select * from beli;

insert into item_beli values (20161001001,2016072021,2,765000,825000);
insert into item_beli values (20161001001,2016072022,3,190000,235000);
insert into item_beli values (20161001001,2016072023,1,905000,960000);

insert into item_beli values (20161001002,2016072024,10,360000,570000);
insert into item_beli values (20161001002,2016072025,20,760000,890000);

insert into item_beli values (20161001003,2016072026,5,370000,450000);

insert into item_beli values (20161001004,2016072027,5,295000,315000);

--select * from item_beli;

insert into jual values (20161002001,'2016-10-02 17:20');
insert into jual values (20161002002,'2016-10-02 18:10');

--select * from jual;

insert into item_jual values (20161002001,2016072021,1,765000,825000);
insert into item_jual values (20161002001,2016072022,2,190000,235000);

insert into item_jual values (20161002002,2016072024,1,360000,570000);
insert into item_jual values (20161002002,2016072025,3,760000,890000);

--select * from v_beli;
create view v_beli as
select b.nota_beli, s.nama_supplier, b.tanggal 
from beli b join supplier s on b.kode_supplier = s.kode_supplier

----select * from v_item_beli where nota_beli='20161001002'
create view v_item_beli as 
select i.nota_beli, i.kode_barang, b.nama_barang,i.jumlah,
	(i.jumlah * i.harga_beli) as sub_total
from item_beli i join barang b on i.kode_barang = b.kode_barang;
----view select * from v_item_jual
create view v_item_jual as 
select i.nota_jual, i.kode_barang, b.nama_barang,i.jumlah,
	(i.jumlah * i.harga_jual) as sub_total
from item_jual i join barang b on i.kode_barang = b.kode_barang;

