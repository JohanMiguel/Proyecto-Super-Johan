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


create table Productos (
    codigoProducto int not null auto_increment,
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
 
 
 



-- -------------------------------------------------------------------------------------------------------------
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





-- CRUD para TipoProducto

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













-- CRUD para CargoEmpleado

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


delimiter $$
create procedure sp_AgregarProductos (in codigoProducto int,in descripcionProducto varchar(100),in precioUnitario decimal(10,2),in precioDocena decimal(10,2),
									in precioMayor decimal(10,2),in imagenProducto varchar(25),in existencia int,in codigoTipoProducto int,in codigoProveedor int)
begin
    insert into Productos (codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,imagenProducto,existencia,codigoTipoProducto,codigoProveedor) 
    values (codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,imagenProducto,existencia,codigoTipoProducto,codigoProveedor);
end$$
delimiter ;

insert into TipoProducto (codigoTipoProducto, descripcion)
values (1, 'tipo a');


insert into Proveedores (codigoProveedor, nitProveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
values (1, '1234567890', 'proveedor', 'apellido', 'direccion', 'razon social', 'contacto', 'paginaweb.com');

call sp_AgregarProductos(1, 'Producto A', 10.00, 100.00, 500.00, 'imagen.jpg', 50, 1, 1);










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















































