package org.johantojin.system;


import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.johantojin.controller.MenuCargosController;
import org.johantojin.controller.MenuClientesController;
import org.johantojin.controller.MenuComprasController;
import org.johantojin.controller.MenuDetalleCompraController;
import org.johantojin.controller.MenuDetalleFacturaController;
import org.johantojin.controller.MenuEmpleadoController;
import org.johantojin.controller.MenuFacturaController;
import org.johantojin.controller.MenuGmailController;
import org.johantojin.controller.MenuPrincipalController;
import org.johantojin.controller.MenuPRDController;
import org.johantojin.controller.MenuProgramadorController;
import org.johantojin.controller.MenuProveedoresController;
import org.johantojin.controller.MenuTelefonoController;
import org.johantojin.controller.MenuTipoDeProductoController;


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

public class Principal extends Application {
  private Stage escenarioPrincipal;
  private Scene escena;
  private final String URLVIEW = "/org/johantojin/view/";
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
       this.escenarioPrincipal = escenarioPrincipal;
       this.escenarioPrincipal.setTitle("SUPERR");
       menuPrincipalView();
      //Parent root = FXMLLoader.load(getClass().getResource("/org/luishernandez/view/MenuPrincipalView.fxml"));
      // Scene escena = new Scene(root);
      // escenarioPrincipal.setScene(escena);
       escenarioPrincipal.show();      
    }

    
    
    
     public Initializable cambiarEscena(String fxmlName, int width, int heigth) throws Exception{
         Initializable resultado;
         FXMLLoader loader = new FXMLLoader();
         
         InputStream file = Principal.class.getResourceAsStream( URLVIEW + fxmlName);
         loader.setBuilderFactory(new JavaFXBuilderFactory());
         loader.setLocation(Principal.class.getResource(URLVIEW + fxmlName));
         
         escena = new Scene ((AnchorPane)loader.load(file), width, heigth);
         escenarioPrincipal.setScene(escena);
         escenarioPrincipal.sizeToScene();
         
         resultado = (Initializable)loader.getController();
         
        return resultado;
              }
    
    public void menuPrincipalView (){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 900,560);
            menuPrincipalView.setEscenarioPrincipal(this);  
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    // vista 1
    public void menuClientesView(){
        try{
            MenuClientesController menuClienteView = (MenuClientesController)cambiarEscena("MenuClientesView.fxml", 900,560);
            menuClienteView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    
   
    public void menuProgramadorView(){
            try{
            MenuProgramadorController menuProgramadorView = (MenuProgramadorController)cambiarEscena("MenuProgramadorView.fxml", 900,560);
            menuProgramadorView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }   
    }
    
    
     // vista 2
    public void menuComprasView(){
            try{
            MenuComprasController menuComprasView = (MenuComprasController)cambiarEscena("MenuComprasView.fxml", 900,560);
            menuComprasView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }   
    }
    
    
    // vista 3
     public void menuProveedoresView(){
            try{
            MenuProveedoresController menuProveedoresView = (MenuProveedoresController)cambiarEscena("MenuProveedoresView.fxml", 900,560);
            menuProveedoresView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }   
    }
    
    // vista 4 de cargos
     public void menuCargosView(){
        try{
         MenuCargosController menuCargosView = (MenuCargosController)cambiarEscena("MenuCargosView.fxml", 900,560);
            menuCargosView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
     
     }
     
     
     
     
    // vista 6 Tipo de Producto
     public void menuTipoDeProductosView(){
            try{
            MenuTipoDeProductoController menuTipoDeProductoView = (MenuTipoDeProductoController)cambiarEscena("MenuTipoDeProductoView.fxml", 900,560);
            menuTipoDeProductoView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }   
    }
     
     // implemantaciond de las vistas que tienen FK
    // vista 1
    public void menuPRDView(){
        try{
            MenuPRDController menuPRDView =(MenuPRDController) cambiarEscena("MenuPRDView.fxml",900,560);
            menuPRDView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    // vista 2
    public void menuDetalleCompraView(){
        try{
            MenuDetalleCompraController menuDetalleCompraView =(MenuDetalleCompraController) cambiarEscena("MenuDetalleCompraView.fxml",900,560);
            menuDetalleCompraView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
     public void menuDetalleFacturaView(){
        try{
            MenuDetalleFacturaController menuDetalleFacturaView =(MenuDetalleFacturaController) cambiarEscena("MenuDetalleFacturaView.fxml",900,560);
            menuDetalleFacturaView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     
      public void menuEmpleadoView(){
        try{
            MenuEmpleadoController menuEmpleadoView =(MenuEmpleadoController) cambiarEscena("MenuEmpleadoView.fxml",900,560);
            menuEmpleadoView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
      
       public void menuFacturaView(){
        try{
            MenuFacturaController menuFacturaView =(MenuFacturaController) cambiarEscena("MenuFacturaView.fxml",900,560);
            menuFacturaView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
       
       public void menuTelefonoProveedorView(){
        try{
            MenuTelefonoController menuTelefonoProveedorView =(MenuTelefonoController) cambiarEscena("MenuTelefonoProveedorView.fxml",900,560);
            menuTelefonoProveedorView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
       
       public void menuGmailProveedorView(){
        try{
            MenuGmailController menuGmailProveedorView =(MenuGmailController) cambiarEscena("MenuGmailProveedorView.fxml",900,560);
            menuGmailProveedorView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
