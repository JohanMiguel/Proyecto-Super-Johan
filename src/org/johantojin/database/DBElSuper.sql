-- drop database if exists DBElSuper;
create database DBElSuper;

use DBElSuper;

-- 
create table Clientes(
	codigoCliente int not null,
	nombreCliente varchar (50) not null,
	apellidoCliente varchar (50) not null,
    NITCliente varchar(10) not null,
	telefonoCliente varchar(8) not null,
    direccionCliente varchar (150) not null,
	correoCliente varchar (45),
	primary Key PK_codigoCliente (codigoCliente)
	 
);

create table Proveedores(
	codigoProveedor int not null, 
	NITProveedor varchar (10) not null,
	nombreProveedor varchar(60) not null,
	apellidoProveedor varchar(60) not null,
	direccionProveedor varchar (150) not null,
	razonSocial varchar (60) not null,
	contactoPrincipal varchar (100) not null,
	paginaWeb varchar (50) not null,
	primary Key PK_codigoProveedor (codigoProveedor)
);

create table Compras (
	numeroDocumento int not null,
	fechaDocumento varchar(50),
    descripcion varchar (60),
	totalDocumento decimal (10,2),
    primary key PK_numeroDocumento (numeroDocumento)
);

create table TipoProducto (
	codigoTipoProducto int not null,
	descripcion varchar (45) not null,
	primary key PK_codigoTipoProducto (codigoTipoProducto)
);

create table CargoEmpleado (
	codigoCargoEmpleado int not null,
	nombreCargo varchar (45) not null,
	descripcionCargo varchar (45) not null,
	primary key PK_codigoCargoEmpleado (codigoCargoEmpleado)
);

-- DUPLAS CON FK
create table Productos (
    codigoProducto int not null ,
    descripcionProducto varchar(100) not null,
    precioUnitario decimal(10,2) not null,
    precioDocena decimal(10,2) not null,
    precioMayor decimal(10,2) not null,
    imagenProducto varchar(25),
    existencia int not null,
    
    codigoTipoProducto int not null,
    codigoProveedor int not null,
    
    primary key  PK_codigoCargoEmpleado(codigoProducto),
    foreign key FK_codigotipoproducto(codigoTipoProducto) references TipoProducto(codigoTipoProducto),
    foreign key FK_codigoproveedor(codigoProveedor) references Proveedores(codigoProveedor)
);


create table DealleCompra (
	codigoDetalleCompra int  not null,
    costoUnitario decimal (10.2),
    cantidad int not null,
    
    codigoProducto int not null,
	numeroDocumento int not null,
    
    primary key PK_codigoDetalleCompra(codigoDetalleCompra),
    foreign key FK_codigoProducto(codigoProducto) references Productos(codigoProducto),
    foreign key FK_numeroDocumento(numeroDocumento) references Compras(numeroDocumento)
);

create table Factura (
	numeroFactura int not null, 
    estado varchar(50),
    totalFactura decimal(10,2),
    fechaFactura varchar(45),
    
    codigoCliente int not null,
    codigoEmpleado int not null,
    primary key PK_numeroFactura(numeroFactura),
    foreign key FK_codigoCliente(codigoCliente) references Clientes(codigoCliente),
    foreign key FK_codigoEmpleado(codigoEmpleado) references Empleado(codigoEmpleado)
	

);



-- dupla de detalle factura
create table DetalleFactura (
	codigoDetalleFactura int not null,
    precioUnitario decimal (10,2) not null,
    cantidad int not null,
	numeroFactura int not null,
	codigoProducto varchar(15) not null,
	primary key PK_codigoDetalleFactura (codigoDetalleFactura),
	constraint FK_Factura_DetalleFactura foreign key (numeroFactura)
	references Factura (numeroFactura),
	constraint FK_Productos_DetalleFactura foreign key (codigoProducto)
	references Productos (codigoProducto)
);

create table Empleado (
	codigoEmpleado int not null,
    nombresEmpleado varchar(50),
    apellidosEmpleado varchar(50),
    sueldo decimal(10,2),
    direccion varchar(150),
    turno varchar(15),
    
    codigoCragoEmpleado int not null,
    
    primary key PK_codigoEmpleado(codigoEmpleado),
    foreign key FK_codigoCargoEmpleado(codigoCargoEmpleado) references CargoEmpleado (codigoCargoEmpleado)
    
);



create table EmailProveedor(
	codigoEmailProveedor int not null,
    emailProveedor varchar(50),
    descripcion varchar(100),
    
    primary key PK_codigoEmailProveedor(codigoEmailProveedor),
	foreign key FK_codigoProveedor(codigoProveedor) references Proveedor(codigoProveedor)
);



create table telefonoProveedor(
	codigoTelefonoProveedor int not null,
    numeroPrincipal varchar(8),
    numeroSecundario varchar(8),
    observaciones varchar(45),
    codigoProveedor int not null,
    
    primary key PK_codigoTelefonoProveedor(codigoTelefonoProveedor),
	foreign key FK_codigoProveedor(codigoProveedor) references Proveedor(codigoProveedor)
);
-- ----------------------------------------------------------------------------------------------------------
-- --------------------------------------------------------------------------------------------------------



-- AGREGAR CLIENTES

Delimiter $$
	create procedure sp_AgregarClientes (in codigoCliente int , NITCliente varchar(10), in nombreCliente varchar (50), in apellidoCliente varchar (50),
    in direccionCliente varchar (150),in telefonoCliente varchar(8), in correoCliente varchar (45)) 
		Begin 
			Insert into Clientes(codigoCliente, NITCliente, nombreCliente, apellidoCliente,direccionCliente,
            telefonoCliente, correoCliente) values 
            (codigoCliente, NITCliente, nombreCliente, apellidoCliente,direccionCliente,
            telefonoCliente, correoCliente);
            
		End $$
Delimiter ;

call sp_AgregarClientes(1, '59486218', 'usuario1', 'usuario1', 'usuario1', '84629518', 'usuario1@gmail.com');
call sp_AgregarClientes(2, '48956217', 'usuario2', 'usuario2', 'usuario2', '98785675', 'usuario2@gmail.com');
 
 
 

-- LISTAR CLIENTES
Delimiter $$
	create procedure sp_ListarClientes()
		Begin
			select 
            C.codigoCliente,
            C.NITCliente,
            C.nombreCliente,
            C.apellidoCliente,
            C.direccionCliente,
            C.telefonoCliente,
            C.correoCliente
            from Clientes C;
		End$$
Delimiter ;

call sp_ListarClientes();



-- BUSCAR CLIENTES
Delimiter $$
	create procedure sp_BuscarClientes (in codCli int)
		Begin
			Select 
				C.codigoCliente,
				C.NITCliente,
				C.nombreCliente,
				C.apellidoCliente,
				C.direccionCliente,
				C.telefonoCliente,
				C.correoCliente
            From Clientes C
            where codigoCliente = codCli;
		End$$
Delimiter ;

call sp_BuscarClientes(1);





-- ELIMINAR CLIENTES
Delimiter $$
	create procedure sp_EliminarClientes (in codCli int)
		Begin
			Delete from Clientes
				where codigoCliente = codCli;
        End $$
Delimiter ;

call sp_EliminarClientes(1);
call sp_ListarClientes;







-- EDITAR CLIENTES
Delimiter $$
	create procedure sp_EditarClientes (in codCli int, in nCliente varchar (10), in nomCliente varchar (50), in apCliente varchar (50),
    in direCliente varchar(150), in telCliente varchar (8), in corrCliente varchar (45))
		Begin
			Update Clientes C
					set
			C.NITcliente = nCliente,
            C.nombreCliente = nomCliente,
            C.apellidoCliente = apCliente,
            C.direccionCliente = direCliente,
            C.telefonoCliente = telCliente,
            C.correoCliente = corrCliente
            where codigoCliente = codCli;
		End $$
Delimiter ;

call sp_EditarClientes(2, '59482615', 'usuario3', 'usuario3', 'usuario3 ', '84628452', 'usuario3@gmail.com');
call sp_ListarClientes;








-- ----------------------------------------------------------------------------------------------------------------------------------------

-- AGREGAR PROVEEDORES
Delimiter $$
	create procedure sp_AgregarProveedores(in codigoProveedor int, in NITProveedor varchar(10), in nombreProveedor varchar(50),
    in apellidoProveedor varchar(50), in direccionProveedor varchar(60), in razonSocial varchar(60), in contactoPrincipal varchar
    (100), in paginaWeb varchar(50))
		Begin 
			 Insert into Proveedores (codigoProveedor, NITProveedor, nombreProveedor, apellidoProveedor, direccionProveedor, 
             razonSocial , contactoPrincipal, paginaWeb )values
             (codigoProveedor, NITProveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial , 
             contactoPrincipal, paginaWeb );
		End $$
Delimiter ;
 
call sp_AgregarProveedores(4,'124585003','proveedor1','proveedor1','proveedor1','proveedor1','59846251','proveedor1@gmail.com');
call sp_AgregarProveedores(5,'124581250','proveedor2','proveedor2','proveedor2','proveedor2','48624859','proveedor1@gmail.com');




-- listar proveedores
Delimiter $$
	create procedure sp_ListarProveedores()
		Begin 
			select
            P.codigoProveedor,
			P.NITProveedor,
			P.nombreProveedor,
			P.apellidoProveedor,
			P.direccionProveedor,
			P.razonSocial ,
			P.contactoPrincipal, 
			P.paginaWeb
			from Proveedores P;
		End $$
Delimiter ;
 
call sp_ListarProveedores();




-- BUSCAR PROVEEDORES
Delimiter $$
	create procedure sp_BuscarProveedores(in prID int)
		Begin 
			select
			P.codigoProveedor,
			P.NITProveedor,
			P.nombreProveedor,
			P.apellidoProveedor,
			P.direccionProveedor,
			P.razonSocial ,
			P.contactoPrincipal, 
			P.paginaWeb
			from Proveedores P
			where codigoProveedor = prID;
		End $$
Delimiter ;
call sp_BuscarProveedores(1);
 







-- ELIMINAR PROVEEDORES
Delimiter $$
	create procedure sp_EliminarProveedores(in prCo int)
		Begin 
			delete from Proveedores
				where codigoProveedor = prCo;
		End $$
Delimiter ;
 
call sp_EliminarProveedores(1);
call sp_ListarProveedores(); 







-- EDITAR PROVEEDORES
Delimiter $$
	create procedure sp_EditarProveedores(in codiProve int, in NProv varchar(10), in nomProve varchar(50),
    in apeProve varchar(50), in direcProve varchar(60), in raSocial varchar(60), in contacPrin varchar
    (100), in pagWeb varchar(50))
		Begin 
			update Proveedores P
				set
			P.NITProveedor = NProv,
			P.nombreProveedor = nomProve,
			P.apellidoProveedor = apeProve,
			P.direccionProveedor = direcProve,
			P.razonSocial = raSocial,
			P.contactoPrincipal = contacPrin, 
			P.paginaWeb = pagWeb
            where codigoProveedor = codiProve;
		End $$
Delimiter ;
 
call sp_EditarProveedores (2,'48592618','proveedor4','proveedor4','proveedor4', 'proveedor4', '485852','proveedor4@gmail.com');
call sp_ListarProveedores(); 
  
  
  
  
  
  
  
  
  
  
  
  
  
   -- ----------------------- COMPRAS -------------------------------------------------------------------------------------
 Delimiter $$
	create procedure sp_AgregarCompras (in numeroDocumento int , fechaDocumento varchar(50), in descripcion varchar (60), in totalDocumento decimal (10,2)) 
		Begin 
			Insert into Compras(numeroDocumento, fechaDocumento, descripcion, totalDocumento)
            values 
            (numeroDocumento, fechaDocumento, descripcion, totalDocumento);
            
		End $$
Delimiter ;
call sp_AgregarCompras (4, 'mayo 9', 'producocomprado', '50');

-- LISTAR COMPRAS
Delimiter $$
	create procedure sp_ListarCompras()
		Begin
			select 
            Cp.numeroDocumento,
            Cp.fechaDocumento,
            Cp.descripcion,
            Cp.totalDocumento
            from Compras Cp;
		End$$
Delimiter ;

call sp_ListarCompras();



-- BUSCAR COMPRAS
Delimiter $$
	create procedure sp_BuscarCompras (in numDoc int)
		Begin
			Select 
				 Cp.numeroDocumento,
				Cp.fechaDocumento,
				Cp.descripcion,
				Cp.totalDocumento
            From Compras Cp
            where numeroDocumento = numDoc;
		End$$
Delimiter ;

call sp_BuscarCompras(1);





-- ELIMINAR COMPRAS
Delimiter $$
	create procedure sp_EliminarCompras (in numDoc int)
		Begin
			Delete from Compras
				where numeroDocumento = numDoc;
        End $$
Delimiter ;

call sp_EliminarCompras(1);
call sp_ListarCompras;



-- EDITAR COMPRAS
delimiter $$

create procedure sp_EditarCompras (in numeroDocumento int,in fechaDocumento varchar(50),in descripcion varchar(60),in totalDocumento decimal(10,2))
begin
    update compras cp
    set
        cp.fechaDocumento = fechaDocumento,
        cp.descripcion = descripcion,
        cp.totalDocumento = totalDocumento
    where cp.numeroDocumento = numeroDocumento;
end $$

delimiter ;

call sp_EditarCompras(4, 'mayo10', '144', '100');
call sp_ListarCompras;





-- ----------------------------------------------------------------------------------------------------------------------

-- Agregar TipoProducto
delimiter $$
create procedure sp_AgregarTipoProducto (in codigotipoproducto int, in descripcion varchar(45))
begin
    insert into tipoproducto (codigoTipoProducto, descripcion)
    values (codigotipoproducto, descripcion);
end $$
delimiter ;

call sp_AgregarTipoProducto(1, 'Tipo A');





-- Listar TipoProducto
delimiter $$
create procedure sp_ListarTipoProducto()
begin
    select codigoTipoProducto, descripcion
    from TipoProducto;
end $$
delimiter ;

call sp_ListarTipoProducto();





-- Buscar TipoProducto por código
delimiter $$
create procedure sp_BuscarTipoProducto (in codTipoProducto int)
begin
    select codigoTipoProducto, descripcion
    from TipoProducto
    where codigoTipoProducto = codTipoProducto;
end $$
delimiter ;


call sp_BuscarTipoProducto(1);




-- Eliminar TipoProducto por código
delimiter $$
create procedure sp_EliminarTipoProducto (in codTipoProducto int)
begin
    delete from TipoProducto
    where codigoTipoProducto = codTipoProducto;
end $$
delimiter ;

call sp_EliminarTipoProducto(1);
call sp_ListarTipoProducto();




-- Editar TipoProducto
delimiter $$
create procedure sp_EditarTipoProducto (in codTipoProducto int, in descripcion varchar(45))
begin
    update TipoProducto
    set descripcion = descripcion
    where codigoTipoProducto = codTipoProducto;
end $$
delimiter ;

call sp_EditarTipoProducto(2, 'Nuevo Tipo A');
call sp_ListarTipoProducto();













-- ---------------------------------------------------------------------------------------------------------------------

-- Agregar CargoEmpleado
delimiter $$
create procedure sp_AgregarCargoEmpleado (in codCarhoEmpleado int, in nomCargo varchar(45), in desCargo varchar(45))
begin
    insert into CargoEmpleado (codigoCargoEmpleado, nombreCargo, descripcionCargo)
    values (codCarhoEmpleado, nomCargo, desCargo);
end $$
delimiter ;
call sp_AgregarCargoEmpleado(1, 'Cargo A', 'Descripción del Cargo A');




-- Listar CargoEmpleado
delimiter $$
create procedure sp_ListarCargoEmpleado()
begin
    select codigoCargoEmpleado, nombreCargo, descripcionCargo
    from CargoEmpleado;
end $$
delimiter ;
call sp_ListarCargoEmpleado();





-- Buscar CargoEmpleado por código
delimiter $$
create procedure sp_BuscarCargoEmpleado (in codCarhoEmpleado int)
begin
    select codigoCargoEmpleado, nombreCargo, descripcionCargo
    from CargoEmpleado
    where codigoCargoEmpleado = codCarhoEmpleado;
end $$
delimiter ;
call sp_BuscarCargoEmpleado(1);





-- Eliminar CargoEmpleado por código
delimiter $$
create procedure sp_EliminarCargoEmpleado (in codCarhoEmpleado int)
begin
    delete from CargoEmpleado
    where codigoCargoEmpleado = codCarhoEmpleado;
end $$
delimiter ;
call sp_EliminarCargoEmpleado(1);




-- Editar CargoEmpleado
delimiter $$
create procedure sp_EditarCargoEmpleado (in codCarhoEmpleado int, in nomCargo varchar(45), in desCargo varchar(45))
begin
    update CargoEmpleado
    set 
    nombreCargo = nomCargo, 
    descripcionCargo = desCargo
    where codigoCargoEmpleado = codCarhoEmpleado;
end $$
delimiter ;

call sp_EditarCargoEmpleado(2, 'Nuevo Cargo Z', 'Nueva Descripción del Cargo Z');












-- ------------------------------------PROCEDIMIENTOS ALMACENADOS DE PRODUCTOS-----------------------------------------------------------------------------------

-- Agregar Productos
delimiter $$
create procedure sp_AgregarProductos (in codigoProducto int,in descripcionProducto varchar(100),in precioUnitario decimal(10,2),in precioDocena decimal(10,2),
									in precioMayor decimal(10,2),in imagenProducto varchar(25),in existencia int,in codigoTipoProducto int,in codigoProveedor int)
begin
    insert into Productos (codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,imagenProducto,existencia,codigoTipoProducto,codigoProveedor) 
    values (codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,imagenProducto,existencia,codigoTipoProducto,codigoProveedor);
end$$
delimiter ;
call sp_AgregarProductos(1, 'Producto A', 10.00, 100.00, 500.00, 'imagen.jpg', 50, 1, 1);


insert into TipoProducto (codigoTipoProducto, descripcion)
values (1, 'tipo a');


insert into Proveedores (codigoProveedor, nitProveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
values (1, '1234567890', 'proveedor', 'apellido', 'direccion', 'razon social', 'contacto', 'paginaweb.com');






-- LISTAR PRODUCTOS

delimiter $$
create procedure sp_ListarProductos()
begin
    select
        codigoProducto,
        descripcionProducto,
        precioUnitario,
        precioDocena,
        precioMayor,
        imagenProducto,
        existencia,
        codigoTipoProducto,
        codigoProveedor
    from productos;
end$$
delimiter ;
call sp_ListarProductos();






-- BUSCAR PRODUCTOS
delimiter $$
create procedure sp_BuscarProducto (
    in codigoProducto int
)
begin
    select
        codigoProducto,
        descripcionProducto,
        precioUnitario,
        precioDocena,
        precioMayor,
        imagenProducto,
        existencia,
        codigoTipoProducto,
        codigoProveedor
    from productos
    where codigoProducto = codigoProducto;
end$$
delimiter ;

call sp_BuscarProducto(1);





-- ELIMINAR PRODUCTO

delimiter $$
create procedure sp_EliminarProducto (
    in codigoProducto int
)
begin
    delete from Productos
    where codigoProducto = codigoProducto;
end$$
delimiter ;
SET SQL_SAFE_UPDATES = 0;

call sp_EliminarProducto(1);








-- EDITAR PRODUCTOS
delimiter $$
create procedure sp_EditarProducto (in codigoProducto int,in descripcionProducto varchar(100),in precioUnitario decimal(10,2),in precioDocena decimal(10,2),in precioMayor decimal(10,2),
									in imagenProducto varchar(25),in existencia int,in codigoTipoProducto int,in codigoProveedor int)
begin
    update Productos
    set
        descripcionProducto = descripcionProducto,
        precioUnitario = precioUnitario,
        precioDocena = precioDocena,
        precioMayor = precioMayor,
        imagenProducto = imagenProducto,
        existencia = existencia,
        codigoTipoProducto = codigoTipoProducto,
        codigoProveedor = codigoProveedor
    where codigoProducto = codigoProducto;
end$$
delimiter ;

call sp_EditarProducto(1, 'Producto B', 12.00, 120.00, 600.00, 'imagen_b.jpg', 60, 1, 1);


-- este es un modo de actualizacion segura que esta aviltado 
set SQL_SAFE_UPDATES = 1;




-- -------------------------------------------------------------------------------------------------------------------
-- DetalleCompra
Delimiter $$
	create procedure sp_AgregarDetalleCompra(in codigoDetalleCompra int, in costoUnitario decimal (10,2),in cantidad int,
	in codigoProducto varchar(15), in numeroDocumento int) 
		Begin 
			Insert into DetalleCompra (codigoDetalleCompra,costoUnitario, cantidad,
			codigoProducto, numeroDocumento) values 
            (codigoDetalleCompra,costoUnitario, cantidad,
			codigoProducto, numeroDocumento);
		End $$
Delimiter ;
 
call sp_AgregarDetalleCompra();
call sp_AgregarDetalleCompra();
call sp_AgregarDetalleCompra();




-- Listar DetalleCompra 
Delimiter $$
	create procedure sp_ListarDetalleCompra()
		Begin 
			select
            DC.codigoDetalleCompra,
			DC.costoUnitario,
            DC.cantidad,
            DC.codigoProducto,
            DC.numeroDocumento
			from DetalleCompra DC;
		End $$
Delimiter ;
 
call sp_ListarDetalleCompra();



-- Buscar DetalleCompra
Delimiter $$
	create procedure sp_BuscarDetalleCompra(in codDetCom int)
		Begin 
			select
            DC.codigoDetalleCompra,
			DC.costoUnitario,
            DC.cantidad,
            DC.codigoProducto,
            DC.numeroDocumento
			from DetalleCompra DC
			where codigoDetalleCompra = codDetCom;
		End $$
Delimiter ;
 
call sp_BuscarDetalleCompra();



--  DetalleCompra
Delimiter $$
	create procedure sp_EliminarDetalleCompra(in codDetCom int)
		Begin
			Delete from DetalleCompra
				where codigoDetalleCompra = codDetCom;
        End $$
Delimiter ;
 
call sp_EliminarDetalleCompra();
call sp_ListarDetalleCompra();



-- Editar DetalleCompra
Delimiter $$
	create procedure sp_EditarDetalleCompra(in codDetCom varchar(15),in cosUni decimal (10,2),
	in cant int, in codProd varchar(15), in numDoc int) 
		Begin 
			update DetalleCompra DC
				set
			DC.codigoDetalleCompra =  codDetCom,
            DC.costoUnitario = cosUni,
            DC.cantidad = cant ,
            DC.codigoProducto = codProd,
            DC.numeroDocumento = numDoc
            where codigoProducto = prodId;
		End $$
Delimiter ;
 
call sp_EditarDetalleCompra(); 
call sp_ListarDetalleCompra();





 
 
-- ------------------------------------------------------------ TelefonoProveedor-----------------------------------------
-- gregar TelefonoProveedor
Delimiter $$
	create procedure sp_AgregarTelefonoProveedor(in codigoTelefonoProveedor int, in numeroPrincipal varchar(8),in numeroSecundario varchar(8),
	in observaciones varchar(45), in codigoProveedor int) 
		Begin 
			Insert into TelefonoProveedor (codigoTelefonoProveedor,numeroPrincipal, numeroSecundario, observaciones, codigoProveedor) values 
            (codigoTelefonoProveedor,numeroPrincipal, numeroSecundario, observaciones, codigoProveedor);
		End $$
Delimiter ;
 
call sp_AgregarTelefonoProveedor();
call sp_AgregarTelefonoProveedor();
call sp_AgregarTelefonoProveedor();





-- Listar TelefonoProveedor
Delimiter $$
	create procedure sp_ListarTelefonoProveedor()
		Begin 
			select
            TP.codigoTelefonoProveedor,
			TP.numeroPrincipal,
            TP.numeroSecundario,
            TP.observaciones,
            TP.codigoProveedor
			from TelefonoProveedor TP;
		End $$
Delimiter ;
 
call sp_ListarTelefonoProveedor();





-- Buscar TelefonoProveedor
Delimiter $$
	create procedure sp_BuscarTelefonoProveedor(in codTeleProv int)
		Begin 
			select
            TP.codigoTelefonoProveedor,
			TP.numeroPrincipal,
            TP.numeroSecundario,
            TP.observaciones,
            TP.codigoProveedor
			from TelefonoProveedor TP
			where codigoTelefonoProveedor = codTeleProv;
		End $$
Delimiter ;
 
call sp_BuscarTelefonoProveedor();




-- Eliminar TelefonoProveedor
Delimiter $$
	create procedure sp_EliminarTelefonoProveedor(in codTeleProv int)
		Begin
			Delete from TelefonoProveedor
				where codigoTelefonoProveedor = codTeleProv;
        End $$
Delimiter ;
 
call sp_EliminarTelefonoProveedor();
call sp_ListarTelefonoProveedor();



-- Editar TelefonoProveedor
Delimiter $$
	create procedure sp_EditarTelefonoProveedor(in codTeleProv varchar(15),in numPri decimal (10,2),
	in numSec int, in observ varchar(15), in codProv int) 
		Begin 
			update TelefonoProveedor TP
				set
            TP.codigoTelefonoProveedor = codTeleProv,
			TP.numeroPrincipal = numPri,
            TP.numeroSecundario = numSec,
            TP.observaciones = observ,
            TP.codigoProveedor = codProv
            where codigoTelefonoProveedor = codTeleProv;
		End $$
Delimiter ;
 
call sp_EditarTelefonoProveedor(); 
call sp_ListarTelefonoProveedor();
-- 



 

-- ----------------------------------------------------- EmailProveedor --------------------------------------------
-- Agregar EmailProveedor¿
Delimiter $$
	create procedure sp_AgregarEmailProveedor(in codigoEmailProveedor int, in emailProveedor varchar(50),in descripcion varchar(100),in codigoProveedor int) 
		Begin 
			Insert into EmailProveedor (codigoEmailProveedor,emailProveedor, descripcion, codigoProveedor) values 
            (codigoEmailProveedor,emailProveedor, descripcion, codigoProveedor);
		End $$
Delimiter ;
 
call sp_AgregarEmailProveedor();
call sp_AgregarEmailProveedor();
call sp_AgregarEmailProveedor();




-- Listar EmailProveedor

Delimiter $$
	create procedure sp_ListarEmailProveedor()
		Begin 
			select
            EP.codigoEmailProveedor,
			EP.emailProveedor,
            EP.descripcion,
            EP.codigoProveedor
			from EmailProveedor EP;
		End $$
Delimiter ;
 
call sp_ListarEmailProveedor();




-- Buscar EmailProveedor
Delimiter $$
	create procedure sp_BuscarEmailProveedor(in codEmProv int)
		Begin 
			select
            EP.codigoEmailProveedor,
			EP.emailProveedor,
            EP.descripcion,
            EP.codigoProveedor
			from EmailProveedor EP
			where codigoEmailProveedor = codEmProv;
		End $$
Delimiter ;
 
call sp_BuscarEmailProveedor();



-- Eliminar EmailProveedor
Delimiter $$
	create procedure sp_EliminarEmailProveedor(in codEmProv int)
		Begin
			Delete from EmailProveedor
				where codigoEmailProveedor = codEmProv;
        End $$
Delimiter ;
 
call sp_EliminarEmailProveedor();
call sp_ListarEmailProveedor();




-- Editar EmailProveedor
Delimiter $$
	create procedure sp_EditarEmailProveedor(in codEmProv int, in emProv varchar(50),in descr varchar(100),in codProv int) 
		Begin 
			update EmailProveedor EP
				set
            EP.codigoEmailProveedor = codEmProv,
			EP.emailProveedor = emProv,
            EP.descripcion = descr,
            EP.codigoProveedor = codProv
			where codigoEmailProveedor = codEmProv;
		End $$
Delimiter ;
 
call sp_EditarEmailProveedor(); 
call sp_ListarEmailProveedor();


 
 
-- -------------------------------------------------------Empleados----------------------------------------------------------------------------
-- Agregar Empleados
Delimiter $$
	create procedure sp_AgregarEmpleados(in codigoEmpleado int, in nombreEmpleado varchar(30),in apellidoEmpleado varchar(100),in sueldo decimal (10,2), 
    in direccion varchar(150), in turno varchar (15), in codigoCargoEmpleado int) 
		Begin 
			Insert into Empleados (codigoEmpleado,nombreEmpleado, apellidoEmpleado, sueldo, direccion, turno, codigoCargoEmpleado) values 
            (codigoEmpleado,nombreEmpleado, apellidoEmpleado, sueldo, direccion, turno, codigoCargoEmpleado);
		End $$
Delimiter ;
 
call sp_AgregarEmpleados();
call sp_AgregarEmpleados();
call sp_AgregarEmpleados();





-- Listar Empleados
Delimiter $$
	create procedure sp_ListarEmpleados()
		Begin 
			select
            E.codigoEmpleado,
			E.nombreEmpleado,
            E.apellidoEmpleado,
            E.sueldo,
            E.direccion,
            E.turno,
            E.codigoCargoEmpleado
			from Empleados E;
		End $$
Delimiter ;
 
call sp_ListarEmpleados();





-- Buscar Empleados
Delimiter $$
	create procedure sp_BuscarEmpleados(in codEmp int)
		Begin 
			select
            E.codigoEmpleado,
			E.nombreEmpleado,
            E.apellidoEmpleado,
            E.sueldo,
            E.direccion,
            E.turno,
            E.codigoCargoEmpleado
			from Empleados E
			where codigoEmpleado = codEmp;
		End $$
Delimiter ;
 
call sp_BuscarEmpleados();


-- Eliminar Empleados
Delimiter $$
	create procedure sp_EliminarEmpleados(in codEmp int)
		Begin
			Delete from Empleados
				where codigoEmpleado = codEmp;
        End $$
Delimiter ;
 
call sp_EliminarEmpleados();
call sp_ListarEmpleados();




-- Editar Empleados 
Delimiter $$
	create procedure sp_EditarEmpleados(in codEmp int, in nomEmp varchar(50),in apellEmp varchar(100),in suel int, in dire varchar(150),
    in tur varchar(15), in codCarEmp int) 
		Begin 
			update Empleados E
				set
            E.codigoEmpleado = codEmp,
			E.nombreEmpleado = nomEmp,
            E.apellidoEmpleado = apellEmp,
            E.sueldo = suel,
            E.direccion = dire,
            E.turno = tur,
            E.codigoCargoEmpleado = codCarEmp
			where codigoEmpleado = codEmp;
		End $$
Delimiter ;
 
call sp_EditarEmpleados(); 
call sp_ListarEmpleados();
 
 
-- ----------------------------------------------------------Factura-------------------------------------------------------------
-- Agregar Factura
Delimiter $$
	create procedure sp_AgregarFactura(in numeroFactura int, in estado varchar(50),in totalFactura decimal(10,2),in fechaFactura varchar(45), 
    in codigoCliente int, in codigoEmpleado int) 
		Begin 
			Insert into Factura (numeroFactura,estado, totalFactura, fechaFactura, codigoCliente, codigoEmpleado) values 
            (numeroFactura,estado, totalFactura, fechaFactura, codigoCliente, codigoEmpleado);
		End $$
Delimiter ;
 
call sp_AgregarFactura();
call sp_AgregarFactura();
call sp_AgregarFactura();




-- Listar Factura
Delimiter $$
	create procedure sp_ListarFactura()
		Begin 
			select
            F.numeroFactura,
			F.estado,
            F.totalFactura,
            F.fechaFactura,
            F.codigoCliente,
            F.codigoEmpleado
			from Factura F;
		End $$
Delimiter ;
 
call sp_ListarFactura();





-- Buscar Factura
Delimiter $$
	create procedure sp_BuscarFactura(in numFact int)
		Begin 
			select
            F.numeroFactura,
			F.estado,
            F.totalFactura,
            F.fechaFactura,
            F.codigoCliente,
            F.codigoEmpleado
			from Factura F
			where numeroFactura = numFact;
		End $$
Delimiter ;
 
call sp_BuscarFactura();





-- Eliminar Factura
Delimiter $$
	create procedure sp_EliminarFactura(in numFact int)
		Begin
			Delete from Factura
				where numeroFactura = numFact;
        End $$
Delimiter ;
 
call sp_EliminarFactura();
call sp_ListarFactura();





-- Editar Factura
Delimiter $$
	create procedure sp_EditarFactura(in numFact int, in est varchar(50),in totFac decimal(10,2),in fechFac varchar(45), in codCli int,
    in codEmp int) 
		Begin 
			update Factura F
				set
            F.numeroFactura = numFact,
			F.estado = est,
            F.totalFactura = totFac,
            F.fechaFactura = fechFac,
            F.codigoCliente = codCli,
            F.codigoEmpleado = codEmp
			where numeroFactura = numFact;
		End $$
Delimiter ;
 
call sp_EditarFactura(); 
call sp_ListarFactura();










-- Agregar DetalleFactura
Delimiter $$
	create procedure sp_AgregarDetalleFactura(in codigoDetalleFactura int, in precioUnitario decimal (10,2),in cantidad int, in numeroFactura int ,in codigoProducto varchar(15)) 
		Begin 
			Insert into Factura (codigoDetalleFactura,precioUnitario, cantidad, numeroFactura, codigoProducto) values 
            (codigoDetalleFactura,precioUnitario, cantidad, numeroFactura, codigoProducto);
		End $$
Delimiter ;
 
call sp_AgregarDetalleFactura();
call sp_AgregarDetalleFactura();
call sp_AgregarDetalleFactura();







-- Listar DetalleFactura
Delimiter $$
	create procedure sp_ListarDetalleFactura()
		Begin 
			select
            DF.codigoDetalleFactura,
			DF.precioUnitario,
			DF.cantidad,
            DF.numeroFactura,
            DF.codigoProducto
			from DetalleFactura DF;
		End $$
Delimiter ;
 
call sp_ListarDetalleFactura();






-- Buscar DetalleFactura
Delimiter $$
	create procedure sp_BuscarDetalleFactura(in codDetFac int)
		Begin 
			select
            DF.codigoDetalleFactura,
			DF.precioUnitario,
			DF.cantidad,
            DF.numeroFactura,
            DF.codigoProducto
			from DetalleFactura DF
			where codigoDetalleFactura = codDetFac;
		End $$
Delimiter ;
 
call sp_BuscarDetalleFactura();






-- Eliminar DetalleFactura
Delimiter $$
	create procedure sp_EliminarDetalleFactura(in codDetFac int)
		Begin
			Delete from DetalleFactura
				where codigoDetalleFactura = codDetFac;
        End $$
Delimiter ;
 
call sp_EliminarDetalleFactura();
call sp_ListarDetalleFactura();




-- ¿Editar DetalleFactra
Delimiter $$
	create procedure sp_EditarDetalleFactura(in codDetFac int, in preUni decimal (10,2),in cant int, in numFac int, in codProd varchar(15)) 
		Begin 
			update DetalleFactura DF
				set
            DF.codigoDetalleFactura = codDetFac,
			DF.precioUnitario = preUni,
			DF.cantidad = cant,
            DF.numeroFactura = numFac,
            DF.codigoProducto = codProd
			where codigoDetalleFactura = codDetFac;
		End $$
Delimiter ;
 
call sp_EditarDetalleFactura(); 
call sp_ListarDetalleFactura();










































