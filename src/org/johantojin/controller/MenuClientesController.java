
package org.johantojin.controller;
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
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;


import org.johantojin.bean.Clientes;
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

public class MenuClientesController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Clientes> listaClientes;
    
    
    // agregar los botones, tienen que ser los mismos que en el de escene sceneBulber
    @FXML private Button btnRegresar;
    
    
    
    // botones de tipo Txt
    @FXML private TextField txtDireccionC;
    @FXML private TextField txtCorreoC;
    @FXML private TextField txtCodigoC;
    @FXML private TextField txtNit;
    @FXML private TextField txtNombreC;
    @FXML private TextField txtApellidoC;
    @FXML private TextField txtTelefonoC;
    
    // botones de la tableView junto con sus columnas
    @FXML private TableView tblClientes;
    @FXML private TableColumn colCodigoC;
    @FXML private TableColumn colNombreC;
    @FXML private TableColumn colApellidoC;
    @FXML private TableColumn colNit;
    @FXML private TableColumn colDireccionC;
    @FXML private TableColumn colTelefonoC;
    @FXML private TableColumn colCorreoC;
    
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
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    
    // metodo para que se cargen los datos de la base de datos, en este caso de la tabla Clientes
    public void cargarDatos(){
                            // Clientes 
        tblClientes.setItems(getClientes());
                                                              // tipo de dato,  tiene que tener el mismo nombre de la base de datos
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
        colNombreC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
        colApellidoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidoCliente"));
        colNit.setCellValueFactory(new PropertyValueFactory<Clientes, String>("NITCliente"));
        colTelefonoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colDireccionC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        colCorreoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
    }
    
    
    // metodo para seleccionar los elementos de la tabla Clientes 
    public void seleccionarElemento(MouseEvent even){
        txtCodigoC.setText(String.valueOf(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        
        
        
        txtNombreC.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getNombreCliente());
        txtApellidoC.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getApellidoCliente());
        txtNit.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getNITCliente());
        txtTelefonoC.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtDireccionC.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtCorreoC.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCorreoCliente());
        
    }
    
    public ObservableList<Clientes> getClientes (){
        ArrayList<Clientes> Lista = new ArrayList<>();
        try{
                                                                                          // se llama el procedimiento almacendo (sp_ListarClientes) 
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
           ResultSet resultado = procedimiento.executeQuery();
           while (resultado.next()){
               Lista.add(new Clientes (resultado.getInt("codigoCliente"),
                                       resultado.getString("nombreCliente"),
                                       resultado.getString("apellidoCliente"),
                                       resultado.getString("NITCliente"),
                                       resultado.getString("telefonoCliente"),
                                       resultado.getString("direccionCliente"),
                                       resultado.getString("correoCliente") 
               ));
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaClientes = FXCollections.observableArrayList(Lista);
    } 
    
    
    // para agregar datos 
    public void agregar(){
                // tipo de dato
        switch(tipoDeOperaciones){
            case NINGUNO:
                // se activan los controles
                activarControles();
                // el boton de agregar pasa a ser GUARDAR
                btnAgregar.setText("Guardar");
                // el boton de eliminar pasa a ser CANCELAR
                btnEliminar.setText("Cancelar");
                // los dos botonrs se desctivan 
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                // estas imagenes se actualizan y pasaan a ser otra imagen
                imgAgregar.setImage(new Image("/org/johantojin/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/johantojin/images/Cancelar.png")); 
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                // se guradan los datps 
                guardar();
                // se desactivan los controlles
                desactivarControles();
                limpiarControles();
                // los botones pasan a se AGREGAR Y ELIMINAR
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                 // estas imagenes se actualizan y pasaan a ser otra imagen
                imgAgregar.setImage(new Image("/org/johantojin/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/johantojin/images/Eliminar.png"));         
                tipoDeOperaciones = operaciones.NINGUNO;
                break;                
        }
        
    
        
    }
    public void guardar(){
        // estos datos se escriben en los textField 
        Clientes registro = new Clientes();
        registro.setCodigoCliente(Integer.parseInt(txtCodigoC.getText()));
        registro.setNombreCliente(txtNombreC.getText());
        registro.setApellidoCliente(txtApellidoC.getText());
        registro.setNITCliente(txtNit.getText());
        registro.setTelefonoCliente(txtTelefonoC.getText());
        registro.setDireccionCliente(txtDireccionC.getText());
        registro.setCorreoCliente(txtCorreoC.getText());
        try{
            
                                                                                             // se llaman al procedimiento almacenado de AgregarClientes 
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarClientes(?, ?, ?, ?, ?, ?, ?)}");
            // procedimiento y su tipo de dato INT y su tipo de registro esto se hace con todos los registros de la tupla
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNombreCliente());
            procedimiento.setString(3, registro.getApellidoCliente());
            procedimiento.setString(4, registro.getNITCliente());
            procedimiento.setString(5, registro.getTelefonoCliente());
            procedimiento.setString(6, registro.getDireccionCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
            listaClientes.add(registro);
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
    // metodo para eliminar los datos
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
                if(tblClientes.getSelectionModel().getSelectedItem() != null){
                    // mustra una pequeña ventana para confirmar la accion
                    int respuesta = JOptionPane.showConfirmDialog(null, "DESA ELIMINAR EL REGISTRO?",
                            "Eliminar Clientes", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{sp_EliminarClientes(?)}");
                            procedimiento.setInt(1, ((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaClientes.remove(tblClientes.getSelectionModel().getSelectedItem());
                            
                            
                            
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else 
                    JOptionPane.showMessageDialog(null,"SELECIONA UN REGISTRO");
        }
        
        
    }
       //metodo para editar la tupla
        public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblClientes.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/johantojin/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/johantojin/images/Borrar.png"));
                    activarControles();
                    txtCodigoC.setEditable(false);
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
     
        // metodo para actualizar los datos 
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarClientes(?, ?, ?, ?, ?, ?, ?)}");
            Clientes registro = (Clientes)tblClientes.getSelectionModel().getSelectedItem();
            registro.setNombreCliente(txtNombreC.getText());
            registro.setApellidoCliente(txtApellidoC.getText());
            registro.setNITCliente(txtNit.getText());
            registro.setTelefonoCliente(txtTelefonoC.getText());
            registro.setDireccionCliente(txtDireccionC.getText());
            registro.setCorreoCliente(txtCorreoC.getText());
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNombreCliente());
            procedimiento.setString(3, registro.getApellidoCliente());
            procedimiento.setString(4, registro.getNITCliente());
            procedimiento.setString(5, registro.getTelefonoCliente());
            procedimiento.setString(6, registro.getDireccionCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    
    
    // metodo para desactivar los coontroladores
    public void desactivarControles(){
        txtCodigoC.setEditable(false);
        txtNombreC.setEditable(false);
        txtApellidoC.setEditable(false);
        txtDireccionC.setEditable(false);
        txtCorreoC.setEditable(false);
        txtNit.setEditable(false);
        txtTelefonoC.setEditable(false);
    }
    
    // metodo para activar los coontroladores
    public void activarControles(){
        txtCodigoC.setEditable(true);
        txtNombreC.setEditable(true);
        txtApellidoC.setEditable(true);
        txtDireccionC.setEditable(true);
        txtCorreoC.setEditable(true);
        txtNit.setEditable(true);
        txtTelefonoC.setEditable(true);
    }
    
    // metodo para limpiar los coontroladores    
    public void limpiarControles(){
        txtCodigoC.clear();
        txtNombreC.clear();
        txtApellidoC.clear();
        txtDireccionC.clear();
        txtCorreoC.clear();
        txtNit.clear();
        txtTelefonoC.clear();
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    
    
    // boton para regresar
    @FXML
  public void regresar (ActionEvent event){
        if (event.getSource() == btnRegresar){
        escenarioPrincipal.menuPrincipalView();
        }
    }

  
}
