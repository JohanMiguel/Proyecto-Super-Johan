package org.johantojin.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import javax.swing.JOptionPane;
import org.johantojin.bean.Cargo;
import org.johantojin.db.Conexion;
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


public class MenuCargosController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Cargo> listaCargo;
    
    // agregar los botones, tienen que ser los mismos que en el de escene sceneBulber
   @FXML private Button btnRegresar;
    
   // botones de tipo Txt
   @FXML private TextField txtCodigoCa;
   @FXML private TextField txtNombreCa;
   @FXML private TextField txtDescripcionCa;
   
    // botones de la tableView junto con sus columnas
   @FXML private TableView tblCargo;
   @FXML private TableColumn colCodigoCa;
   @FXML private TableColumn colNombreCa;
   @FXML private TableColumn colDescripcionCa;
   
   // botones de acciones
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    // para la accion de editar, primero se selecciona el registro, luego se 
    //preciona el boton de editar, se ingresan los nuevos registros y se confirma 
     // en el boton ACTUALIZAR (SE SE ACTUALIZA SIN QUE LOS REGISTROS VIEGOS SE MUETREN)
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    // imagene tipo ImageView
   @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
   
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos(){
       tblCargo.setItems(getCargo());
        colCodigoCa.setCellValueFactory(new PropertyValueFactory<Cargo, Integer>("codigoCargoEmpleado"));
        colNombreCa.setCellValueFactory(new PropertyValueFactory<Cargo, String>("nombreCargo"));
        colDescripcionCa.setCellValueFactory(new PropertyValueFactory<Cargo, String>("descripcionCargo"));       
    }
    


    public void seleccionarElemento(){
        txtCodigoCa.setText(String.valueOf(((Cargo)tblCargo.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        txtNombreCa.setText(((Cargo)tblCargo.getSelectionModel().getSelectedItem()).getNombreCargo());
        txtDescripcionCa.setText(((Cargo)tblCargo.getSelectionModel().getSelectedItem()).getDescripcionCargo());
    }
    


    public ObservableList<Cargo> getCargo(){
        ArrayList<Cargo> Lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargoEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                Lista.add(new Cargo (resultado.getInt("codigoCargoEmpleado"),
                                       resultado.getString("nombreCargo"),
                                       resultado.getString("descripcionCargo")
                ));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCargo = FXCollections.observableArrayList(Lista);
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
        Cargo registro = new Cargo();
        registro.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCa.getText()));
        registro.setNombreCargo(txtNombreCa.getText());
        registro.setDescripcionCargo(txtDescripcionCa.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCargoEmpleado(?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
            listaCargo.add(registro);
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
                if(tblCargo.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL REGISTRO?",
                            "Eliminar Cargo Empleado", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCargoEmpleado(?)}");
                            procedimiento.setInt(1, ((Cargo)tblCargo.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            procedimiento.execute();
                            listaCargo.remove(tblCargo.getSelectionModel().getSelectedItem());                         
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    limpiarControles();
                }else 
                    JOptionPane.showMessageDialog(null,"SELECIONA UN REGISTRO");
        } 
    }    
    
    

    
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblCargo.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/johantojin/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/johantojin/images/Borrar.png"));
                    activarControles();
                    txtCodigoCa.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCargoEmpleado(?, ?, ?)}");
            Cargo registro = (Cargo)tblCargo.getSelectionModel().getSelectedItem();
            registro.setNombreCargo(txtNombreCa.getText());
            registro.setDescripcionCargo(txtDescripcionCa.getText());
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();

        }catch (Exception e){
            e.printStackTrace();
        }
    }    
    

    
    public void reporte(){
        switch (tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/og/johantojin/images/Editar.png"));
                imgReporte.setImage(new Image("/org/johantojin/images/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                        
        }
    }
    
    
    
    public void desactivarControles(){
        txtCodigoCa.setEditable(false);
        txtNombreCa.setEditable(false);
        txtDescripcionCa.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoCa.setEditable(true);
        txtNombreCa.setEditable(true);
        txtDescripcionCa.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoCa.clear();
        txtNombreCa.clear();
        txtDescripcionCa.clear();
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
