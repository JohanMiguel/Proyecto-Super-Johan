/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.johantojin.bean;

/**
 *
 * 
create table DetallesCompra(
	costoUnitario decimal(10,2) not null,
	primary key PK_codigoDetalles(codigoDetalles),
	foreign key FK_codigoProductos(codigoProductos) references Productos(codigoProductos),
	foreign key FK_numeroDocumento(numeroDocumento) references Compras(numeroDocumento)
);
 */
public class DetalleCompra {
    private int codigoDetalles;
    private double costoUnitario;
    private int cantidad;
    private int codigoProductos;
    private int numeroDocumento;

    public DetalleCompra() {
    }

    public DetalleCompra(int codigoDetalles, double costoUnitario, int cantidad, int codigoProductos, int numeroDocumento) {
        this.codigoDetalles = codigoDetalles;
        this.costoUnitario = costoUnitario;
        this.cantidad = cantidad;
        this.codigoProductos = codigoProductos;
        this.numeroDocumento = numeroDocumento;
    }

    public int getCodigoDetalles() {
        return codigoDetalles;
    }

    public void setCodigoDetalles(int codigoDetalles) {
        this.codigoDetalles = codigoDetalles;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCodigoProductos() {
        return codigoProductos;
    }

    public void setCodigoProductos(int codigoProductos) {
        this.codigoProductos = codigoProductos;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
