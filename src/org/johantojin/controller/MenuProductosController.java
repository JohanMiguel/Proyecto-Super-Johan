package org.johantojin.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.johantojin.bean.Producto;
import org.johantojin.bean.Proveedores;
import org.johantojin.bean.TipoDeProducto;
import org.johantojin.db.Conexion;
import org.johantojin.system.Principal;


public class MenuProductosController implements Initializable{
     private Principal escenarioPrincipal;
     private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO }
     private operaciones tipoDeOperacion = operaciones.NINGUNO;
     private ObservableList <Producto> listaProducto;
     private ObservableList <Proveedores> listaProveedor;
     private ObservableList <TipoDeProducto> listaP;
     
     
     // setiar los objetos
     // iniciales de combox cmb y su funcion
     @FXML private TextField txtCodigoProd;
     @FXML private TextField txtDescPro;
     @FXML private TextField txtPrecioU;
     @FXML private TextField txtPrecioD;
     @FXML private TextField txtPrecioM;
     @FXML private TextField txtExistencia;
     
     @FXML private ComboBox cmbCodigoTopoP;
     @FXML private ComboBox cmbCodProv;
     
     @FXML private TableView tblProductos;
     @FXML private TableColumn colCodProd;
     @FXML private TableColumn colDescProd;
     @FXML private TableColumn colPrecioU;
     @FXML private TableColumn colPrecioD;
     @FXML private TableColumn colPrecioM;
     @FXML private TableColumn colExistencia;
     @FXML private TableColumn colCodTipoProd;
     @FXML private TableColumn colCodProv;
     
      // botones de acciones
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
     
     
     
     
     
     
      public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoTopoP.setItems(getTipop());
        cmbCodProv.setItems(getProveedores());
    }
      
      
      // 
      
      
      
      
      
      
      
      
      // ------------cargarDatos-----------
      public void cargarDatos(){
      tblProductos.setItems(getProducto());
        colCodProd.setCellValueFactory(new PropertyValueFactory<Producto, String>("codigoProducto"));
        colDescProd.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcionProducto"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioUnitario"));
        colPrecioD.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioDocena"));
        colPrecioM.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioMayor"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("existencia"));
        colCodTipoProd.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("codigoTipoProducto"));
        colCodProv.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("codigoProveedor"));
      
      }
      

       public void seleccionarElemento(){
        txtCodigoProd.setText(((TipoDeProducto)tblTipoDeProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        txtDescPro.setText(((TipoDeProducto)tblTipoDeProducto.getSelectionModel().getSelectedItem()).getDescripcion());
    }
      
      // es una funcion ya que retorna, los metodos no retornas, las funciones no usan la palabra reservada void 
       
      public TipoDeProducto buscarTipoP (int codigoTipoProduscto){
      
      
      } 
       
       
       
       
       
       
       
       
      
      public ObservableList<Producto> getProducto(){
      ArrayList<Producto> lista = new ArrayList<Producto>();
      try{
      PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call}")
      ResultSet resultado = procedimiento.executeQuery();
      while(resultado.next()){
      lista.add(new Producto (resultado.getString()))
      
      }
      }catch(Exceptio e){
      
      }
      }

      
         public void agregar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                
               tipoDeOperacion = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;                
        }
        
   
    }
      
         
           public void guardar(){
        Producto registro = new Producto();
        registro.setCodigoProducto(txtCodigoProd.getText());
        registro.setCodigoProveedor((TipoDeProducto)cmbCodProv.getSelectionModel().getSelectedItem());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoProducto(?, ?)}");
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
            listaTipoDeProducto.add(registro);
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }   
      
      // los comboxBox son disable por que no se pueden editar
      public void activarControles(){
      txtCodigoProd.setEditable(true);
      txtDescPro.setEditable(true);
      txtPrecioU.setEditable(true);
      txtPrecioD.setEditable(true);
      txtPrecioM.setEditable(true);
      txtExistencia.setEditable(true);
      
      }
      
       public void desactivarControles(){
      txtCodigoProd.setEditable(false);
      txtDescPro.setEditable(false);
      txtPrecioU.setEditable(false);
      txtPrecioD.setEditable(false);
      txtPrecioM.setEditable(false);
      txtExistencia.setEditable(false);
      
      }
      
      public void limpiarControles(){
      txtCodigoProd.clear();
      txtDescPro.clear();
      txtPrecioU.clear();
      txtPrecioD.clear();
      txtPrecioM.clear();
      txtExistencia.clear();
      }
      
      
      
}
