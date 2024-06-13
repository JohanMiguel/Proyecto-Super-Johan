
package org.johantojin.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.johantojin.bean.Producto;
import org.johantojin.bean.Proveedores;
import org.johantojin.bean.TipoDeProducto;
import org.johantojin.db.Conexion;
import org.johantojin.reportes.GenerarReportes;
import org.johantojin.system.Principal;

/**
 *
 * @author CFCDC012
 */
public class MenuPRDController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Producto> listarProductos;
    private ObservableList<Proveedores> listarProveedores;
    private ObservableList<TipoDeProducto> listarTipoProducto;
    

//Setiar los Objetos
    //Iniciales de ComboBox cmb"Funcion"
    @FXML private Button btnRegresar;
    
    @FXML private TextField txtCodigoProd;
    @FXML private TextField txtDescPro;
    @FXML private TextField txtPrecioU;
    @FXML private TextField txtPrecioD;
    @FXML private TextField txtPrecioM;
    @FXML private TextField txtExistencia;
    
    @FXML private ComboBox cmbCodigoTipoP;
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
    
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
 
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoTipoP.setItems(getCodigoTipoPro());
        cmbCodProv.setItems(getProveedores());
    }

    
    // cargar datos completo
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

    
    public void seleccionarElementos(){
       txtCodigoProd.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        txtDescPro.setText(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        txtPrecioU.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtPrecioD.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtPrecioM.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        txtExistencia.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getExistencia()));
        cmbCodigoTipoP.getSelectionModel().select(buscarTipoP(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
 
    }
    
    
    // mejorardo
    public TipoDeProducto buscarTipoP (int codigoTipoProducto){
        TipoDeProducto resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoProducto(?)}");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next());
                resultado = new TipoDeProducto(registro.getInt("CodigoTipoProducto"),
                                            registro.getString("descripcionProducto")
                );
        }catch(Exception e){
            e.printStackTrace();
        }

        return resultado;
    }
    
    
    
    
    
    public ObservableList<Producto> getProducto(){
    ArrayList<Producto> lista = new ArrayList<Producto>();
    try{
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos()}");
        ResultSet resultado = procedimiento.executeQuery();
        while(resultado.next()){
            lista.add(new Producto (
                    resultado.getInt("codigoProducto"),
                    resultado.getString("descripcionProducto"),
                                    resultado.getDouble("precioUnitario"),
                                    resultado.getDouble("precioDocena"),
                                    resultado.getDouble("precioMayor"),
                                    resultado.getString("imagenProducto"),
                                    resultado.getInt("existencia"),
                                    resultado.getInt("codigoTipoProducto"),
                                    resultado.getInt("codigoProveedor")            
            ));
        }
    }catch (Exception e){
        e.printStackTrace();
    }
    
    
    return listarProductos = FXCollections.observableArrayList(lista);
        
    }





    public ObservableList<TipoDeProducto> getCodigoTipoPro (){
        ArrayList<TipoDeProducto> lista = new ArrayList<>();
        try{                             
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();

            while(resultado.next()){
                lista.add(new TipoDeProducto (resultado.getInt("codigoTipoProducto"),
                                        resultado.getString("descripcion")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    return listarTipoProducto = FXCollections.observableArrayList(lista);
    }
    
    
    
    public ObservableList<Proveedores> getProveedores (){
                     
        ArrayList<Proveedores> lista = new ArrayList<>();
     
        try{                             
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
           
            while(resultado.next()){
                lista.add(new Proveedores (resultado.getInt("codigoProveedor"),
                                        resultado.getString("NITProveedor"),
                                        resultado.getString("nombreProveedor"),
                                        resultado.getString("apellidoProveedor"),
                                        resultado.getString("direccionProveedor"),
                                        resultado.getString("razonSocial"),
                                        resultado.getString("contactoPrincipal"),
                                        resultado.getString("paginaWeb")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    return listarProveedores = FXCollections.observableArrayList(lista);
    }
    
    


    public void agregar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    public void guardar(){
    Producto registro = new Producto();
    registro.setCodigoProducto(Integer.parseInt(txtCodigoProd.getText()));
    registro.setDescripcionProducto(txtDescPro.getText());
    registro.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
    registro.setPrecioDocena(Double.parseDouble(txtPrecioD.getText()));
    registro.setPrecioMayor(Double.parseDouble(txtPrecioM.getText()));
    registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
    registro.setCodigoTipoProducto(((TipoDeProducto)cmbCodigoTipoP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
    registro.setCodigoProveedor(((Proveedores)cmbCodProv.getSelectionModel().getSelectedItem()).getCodigoProveedor());

    try {
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProductos (?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        procedimiento.setInt(1, registro.getCodigoProducto());
        procedimiento.setString(2, registro.getDescripcionProducto());
        procedimiento.setDouble(3, registro.getPrecioUnitario());
        procedimiento.setDouble(4, registro.getPrecioDocena());
        procedimiento.setDouble(5, registro.getPrecioMayor());
        procedimiento.setString(6, registro.getImagenProducto()); 
        procedimiento.setInt(7, registro.getExistencia());
        procedimiento.setInt(8, registro.getCodigoTipoProducto());
        procedimiento.setInt(9, registro.getCodigoProveedor());
        procedimiento.execute();
        listarProductos.add(registro);
        tblProductos.refresh(); 
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    
          public void reporte(){
        switch (tipoDeOperaciones){
            case NINGUNO:
            imprimirReporte();
            break;
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
            
                tipoDeOperaciones = operaciones.NINGUNO;
                        
        }
    }
      
      
      
      
      public void imprimirReporte(){
      Map parametro = new HashMap();
      parametro.put("codigoProducto", null);
      GenerarReportes.mostrarReportes("reporteProducto.jasper", "Reporte de los Productos", parametro);
      }

    public void desactivarControles(){
        txtCodigoProd.setEditable(false);
        txtDescPro.setEditable(false);
        txtPrecioU.setEditable(false);
        txtPrecioD.setEditable(false);
        txtPrecioM.setEditable(false);
        cmbCodigoTipoP.setEditable(false);
        cmbCodProv.setEditable(false);
    }
    public void activarControles(){
        txtCodigoProd.setEditable(true);
        txtDescPro.setEditable(true);
        txtPrecioU.setEditable(true);
        txtPrecioD.setEditable(true);
        txtPrecioM.setEditable(true);
        cmbCodigoTipoP.setEditable(true);
        cmbCodProv.setEditable(true);
    }
    public void limpiarControles(){
        txtCodigoProd.clear();
        txtDescPro.clear();
        txtPrecioU.clear();
        txtPrecioD.clear();
        txtPrecioM.clear();
        txtExistencia.clear();
        cmbCodigoTipoP.getSelectionModel().getSelectedItem();
        cmbCodProv.getSelectionModel().getSelectedItem();
    }
    
   public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    
    @FXML
  public void regresar (ActionEvent event){
        if (event.getSource() == btnRegresar){
        escenarioPrincipal.menuPrincipalView();
        }
    }
}
