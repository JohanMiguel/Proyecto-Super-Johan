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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.johantojin.bean.TipoDeProducto;
import org.johantojin.db.Conexion;
import org.johantojin.reportes.GenerarReportes;
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


public class MenuTipoDeProductoController implements Initializable {

    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<TipoDeProducto> listaTipoDeProducto;
    
   @FXML private Button btnRegresar;
   
   
   @FXML private TextField txtCodigoPo;
   @FXML private TextField txtDescripcionPo;


   @FXML private TableView tblTipoDeProducto;
   @FXML private TableColumn colCodigoPo;
   @FXML private TableColumn colDescripcionPo;

   
  
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
  
   @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }    
    
    public void cargarDatos(){
       tblTipoDeProducto.setItems(getTipoDeProducto());
        colCodigoPo.setCellValueFactory(new PropertyValueFactory<TipoDeProducto, Integer>("codigoTipoProducto"));
        colDescripcionPo.setCellValueFactory(new PropertyValueFactory<TipoDeProducto, String>("descripcion"));
    }
    


    
    public void seleccionarElemento(MouseEvent even){
        txtCodigoPo.setText(String.valueOf(((TipoDeProducto)tblTipoDeProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        txtDescripcionPo.setText(((TipoDeProducto)tblTipoDeProducto.getSelectionModel().getSelectedItem()).getDescripcion());
    }
    
    public ObservableList<TipoDeProducto> getTipoDeProducto(){
        ArrayList<TipoDeProducto> Lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                Lista.add(new TipoDeProducto (resultado.getInt("codigoTipoProducto"),
                                       resultado.getString("descripcion")
                ));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTipoDeProducto = FXCollections.observableArrayList(Lista);
    }
    

    
    public void agregar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/johantojin/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/johantojin/images/Cancelar.png")); 
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
                imgAgregar.setImage(new Image("/org/johantojin/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/johantojin/images/Eliminar.png"));         
                tipoDeOperaciones = operaciones.NINGUNO;
                break;                
        }
        
   
    }
    
    public void guardar(){
        TipoDeProducto registro = new TipoDeProducto();
        registro.setCodigoTipoProducto(Integer.parseInt(txtCodigoPo.getText()));
        registro.setDescripcion(txtDescripcionPo.getText());
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
    

    
    
    public void eliminar(){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/johantojin/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/johantojin/images/Eliminar.png"));                
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if(tblTipoDeProducto.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?",
                            "Eliminar Cargo Empleado", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoProducto(?)}");
                            procedimiento.setInt(1, ((TipoDeProducto)tblTipoDeProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
                            procedimiento.execute();
                            listaTipoDeProducto.remove(tblTipoDeProducto.getSelectionModel().getSelectedItem());                         
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    limpiarControles();
                }else 
                    JOptionPane.showMessageDialog(null,"SELECIONA UN REGISTRO");
        } 
    }
    
// Editar    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblTipoDeProducto.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/johantojin/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/johantojin/images/Borrar.png"));
                    activarControles();
                    txtCodigoPo.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                    
                }else 
                    JOptionPane.showMessageDialog(null, "SELECIONA UN REGISTRO");
                break;
            case ACTUALIZAR:
                    actualizar();
                    desactivarControles();
                    limpiarControles();
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnAgregar.setDisable(false);
                    btnEliminar.setDisable(false);
                    imgEditar.setImage(new Image ("/org/johantojin/images/Editar.png"));
                    imgReporte.setImage(new Image("/org/johantojin/images/Reportes.png"));
                    tipoDeOperaciones = operaciones.NINGUNO;
                    cargarDatos();
                break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarTipoProducto(?, ?)}");
            TipoDeProducto registro = (TipoDeProducto)tblTipoDeProducto.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcionPo.getText());
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();

        }catch (Exception e){
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
      
      
      
      // imprimir
      public void imprimirReporte(){
      Map parametro = new HashMap();
      parametro.put("codigoTipoProducto", null);
      GenerarReportes.mostrarReportes("reportesCompras.jasper", "Reporte de los Tipos de Producto", parametro);
      }


    public void desactivarControles(){
        txtCodigoPo.setEditable(false);
        txtDescripcionPo.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoPo.setEditable(true);
        txtDescripcionPo.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoPo.clear();
        txtDescripcionPo.clear();
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
