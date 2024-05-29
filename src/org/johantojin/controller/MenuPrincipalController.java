package org.johantojin.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.johantojin.system.Principal;

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





// se implementa herencias

public class MenuPrincipalController implements Initializable {
    private Principal escenarioPrincipal;
    
    @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnMenuProgramador;
    @FXML MenuItem btnMenuCompras;
    @FXML MenuItem btnMenuProveedores;
    @FXML MenuItem btnMenuCargos;
    @FXML MenuItem btnMenuTipoDeProducto;
    
    //BTN con FK
    @FXML MenuItem btnMenuPRD;
    @FXML MenuItem btnDetalleCompra;
    @FXML MenuItem btnDetalleFactura;
    @FXML MenuItem btnEmpleado;
    @FXML MenuItem btnFactura;
    @FXML MenuItem btnTelefono;
    @FXML MenuItem btnGmail;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    
// vista finalizaday funcional
    @FXML
    public void clicClientes (ActionEvent event){
        if (event.getSource() == btnMenuClientes){
            escenarioPrincipal.menuClientesView();
        }
    }
    
    
  // vista finalizaday funcional
    @FXML
    public void clicProgramador (ActionEvent event){
        if (event.getSource() == btnMenuProgramador){
            escenarioPrincipal.menuProgramadorView();
        }
    }
    
    
    
  // vista finalizaday funcional
    @FXML
    public void clicProveedores (ActionEvent event){
        if (event.getSource() == btnMenuProveedores){
            escenarioPrincipal.menuProveedoresView();
        }
    }
 
    
    // vista  
    @FXML
    public void clicCompras (ActionEvent event){
        if (event.getSource() == btnMenuCompras){
            escenarioPrincipal.menuComprasView();
        }
    }

    // vista finalizaday funcional
    @FXML
    public void clicCargos (ActionEvent event){
    if (event.getSource() == btnMenuCargos){
            escenarioPrincipal.menuCargosView();
        }
    }
    
    // vista finalizaday funcional
    @FXML
    public void clicTipoDeProducto (ActionEvent event){
    if (event.getSource() == btnMenuTipoDeProducto){
            escenarioPrincipal.menuTipoDeProductosView();
        }
    }
    
    // vistas con FK
    
     @FXML
    public void clicPRD (ActionEvent event){
    if (event.getSource() == btnMenuPRD){
            escenarioPrincipal.menuPRDView();
        }
    }
    
     @FXML
    public void clicDetalleCompra (ActionEvent event){
        if (event.getSource() == btnDetalleCompra){
            escenarioPrincipal.menuDetalleCompraView();
        }
    }
    
     @FXML
    public void clicDetalleFactura (ActionEvent event){
        if (event.getSource() == btnDetalleFactura){
            escenarioPrincipal.menuDetalleFacturaView();
        }
    }
    
    @FXML
    public void clicFactura (ActionEvent event){
        if (event.getSource() == btnFactura){
            escenarioPrincipal.menuFacturaView();
        }
    }
    
    
    
    @FXML
    public void clicEmpleado (ActionEvent event){
        if (event.getSource() == btnEmpleado){
            escenarioPrincipal.menuEmpleadoView();
        }
    }
    
    
    @FXML
    public void clicTelefono (ActionEvent event){
        if (event.getSource() == btnTelefono){
            escenarioPrincipal.menuTelefonoProveedorView();
        }
    }
    
    
    @FXML
    public void clicGmail (ActionEvent event){
        if (event.getSource() == btnGmail){
            escenarioPrincipal.menuGmailProveedorView()
                    
                    ;
        }
    }
    
    
}
