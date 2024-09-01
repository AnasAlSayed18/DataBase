
drop database factory;
create database factory;
use factory;

-- Table: Users
CREATE TABLE Users (
    email VARCHAR(255) NOT NULL PRIMARY KEY,
        username VARCHAR(255) NOT NULL,

    U_password VARCHAR(255) NOT NULL,
	addrees VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    U_role VARCHAR(255) NOT NULL
);


CREATE TABLE Supplier (
    id int PRIMARY KEY auto_increment,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL
);

-- Table: Employee
CREATE TABLE Employee (
    id INT PRIMARY KEY auto_increment,
    contact_info VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    salary double NOT NULL,
    E_role VARCHAR(255) NOT NULL
);



-- Table: Material
CREATE TABLE Material (
    id INT PRIMARY KEY auto_increment,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL
);

-- Table: Supplier_Order
CREATE TABLE Supplier_Order (
    id INT PRIMARY KEY auto_increment,
    supplier_id INT,
     material_id INT,
     employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES Employee(id),
    FOREIGN KEY (supplier_id) REFERENCES Supplier(id),
     FOREIGN KEY (material_id) REFERENCES Material(id)
);
-- Table: Machines
CREATE TABLE Machines (
    id INT PRIMARY KEY auto_increment,
    production VARCHAR(255) NOT NULL,
    rate DECIMAL(10, 2) NOT NULL,
    Mtype VARCHAR(255) NOT NULL
);

-- Table: machine_Requires_material
CREATE TABLE machine_Requires_material (
    machine_id INT NOT NULL,
    material_id INT NOT NULL,
    PRIMARY KEY (machine_id, material_id),
    FOREIGN KEY (machine_id) REFERENCES Machines(id),
    FOREIGN KEY (material_id) REFERENCES Material(id)
);


-- Table: Warehouse
CREATE TABLE Warehouse (
    id INT PRIMARY KEY auto_increment,
    address VARCHAR(255) NOT NULL,
    capacity INT NOT NULL
);
-- Table: Products
CREATE TABLE Products (
    id INT PRIMARY KEY auto_increment,
    Pname VARCHAR(255) NOT NULL,
    cost DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    warehouse_limit INT NOT NULL,
    Warehouse_id INT NOT NULL,
        FOREIGN KEY (Warehouse_id) REFERENCES Warehouse(id)
);

-- Table: Produce
CREATE TABLE Produce (
    machine_id INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (machine_id, product_id),
    FOREIGN KEY (machine_id) REFERENCES Machines(id),
    FOREIGN KEY (product_id) REFERENCES Products(id)
);




-- Table: Customers
CREATE TABLE Customers (
    id INT PRIMARY KEY auto_increment,
    Cname VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    Ctype VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
    
);
-- Table: Orders
CREATE TABLE F_Order (
    Onumber INT PRIMARY KEY auto_increment,
    quantity INT NOT NULL,
    Odate DATE NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
     customer_id INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Customers(id)
);
-- Table: Order_Line
CREATE TABLE Order_Line (
    id INT PRIMARY KEY auto_increment,
     order_id INT NOT NULL,
     product_id INT NOT NULL,
    quantity INT NOT NULL,
     FOREIGN KEY (order_id) REFERENCES F_Order(Onumber),
   FOREIGN KEY (product_id) REFERENCES Products(id)
);







-- Table: WarehouseTake
CREATE TABLE Takes (
    employee_id INT NOT NULL,
    order_id INT NOT NULL,
	warehouse_id INT NOT NULL,
    PRIMARY KEY (employee_id,order_id,warehouse_id),
    FOREIGN KEY (employee_id) REFERENCES Employee(id),
    FOREIGN KEY (order_id) REFERENCES F_Order(Onumber),
	FOREIGN KEY (warehouse_id) REFERENCES Warehouse(id)

);

-- Table: Submit maaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa   هل هي 1-1 او m-m
CREATE TABLE Submit (
    customer_id INT NOT NULL,
    order_id INT NOT NULL,
    PRIMARY KEY (customer_id, order_id),
    FOREIGN KEY (customer_id) REFERENCES Customers(id),
    FOREIGN KEY (order_id) REFERENCES F_Order(Onumber)
);


-- Table: Delivers
CREATE TABLE Delivers (
    employee_id INT NOT NULL,
    order_id INT NOT NULL,
    Onumber INT NOT NULL,
    PRIMARY KEY (employee_id, order_id,Onumber),
    FOREIGN KEY (employee_id) REFERENCES Employee(id),
    FOREIGN KEY (Onumber) REFERENCES F_Order(Onumber)
);