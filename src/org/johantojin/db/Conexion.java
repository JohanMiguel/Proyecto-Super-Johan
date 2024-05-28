package org.johantojin.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *Documentacion
 * Nombre: Johan Tojin
 * Grado: 5to Perito
 * Seccion: IN5BV
 * Carne: 2020591
 * Fecgha de creacion: 11/4/24
 * Fecha de Modificacion: 18/4/24
 * Fecha de Modificacion: 25/4/24
 * Fecha de Modificacion  26/04/24
 * Fecha de Modificacion  2/05/24
 * Fecha de Modificacion  9/05/24
 * Fecha de Modificacion  10/05/24
 */


public class Conexion {
    private Connection conexion;
    private static Conexion instancia;
    
    
    public Conexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBElSuper?useSSL=false", "root", "RootKinal2024$");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public static Conexion getInstance (){
        if(instancia == null)
            instancia = new Conexion();
        
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
    
}
