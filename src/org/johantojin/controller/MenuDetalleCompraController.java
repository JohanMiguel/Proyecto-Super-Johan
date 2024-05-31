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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.johantojin.bean.DetalleCompra;
import org.johantojin.bean.Producto;
import org.johantojin.bean.Compras;
import org.johantojin.db.Conexion;
import org.johantojin.system.Principal;

public class MenuDetalleCompraController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetalleCompra> listarDetallesCompra;
    private ObservableList<Producto> listarProducto;
    private ObservableList<Compras> listarCompras;

 @FXML private Button btnRegresar;
    
    @FXML private TextField txtCodigoDetalles;
    @FXML private TextField txtCostoUnitario;
    @FXML private TextField txtCantidad;
    
    
    @FXML private ComboBox cmbCodigoProd;
    @FXML private ComboBox cmbNumeroDocumento;
    
    @FXML private TableView tblDetalleCompras;
    @FXML private TableColumn colCodigoDetalles;
    @FXML private TableColumn colCostoUnitario;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colCodigoProd;
    @FXML private TableColumn colNumeroDocumento;
    
    
   
    
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
 
    
    
    



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoProd.setItems(getProducto());
        cmbNumeroDocumento.setItems(getCompras());
    }

    public void cargarDatos() {
        tblDetalleCompras.setItems(getDetalleCompras());
        colCodigoDetalles.setCellValueFactory(new PropertyValueFactory<>("codigoDetalles"));
        colCostoUnitario.setCellValueFactory(new PropertyValueFactory<>("costoUnitario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCodigoProd.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        colNumeroDocumento.setCellValueFactory(new PropertyValueFactory<>("numeroDocumento"));
    }


    public ObservableList<DetalleCompra> getDetalleCompras() {
        ArrayList<DetalleCompra> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetalleCompra()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleCompra(
                    resultado.getInt("codigoDetalles"),
                    resultado.getDouble("costoUnitario"),
                    resultado.getInt("cantidad"),
                    resultado.getInt("codigoProducto"),
                    resultado.getInt("numeroDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarDetallesCompra = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Producto> getProducto() {
        ArrayList<Producto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Producto(
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarProducto = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compras(
                    resultado.getInt("numeroDocumento"),
                    resultado.getString("fechaDocumento"),
                    resultado.getString("descripcion"),
                    resultado.getDouble("totalDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarCompras = FXCollections.observableArrayList(lista);
    }
    public void agregar() {
        switch (tipoDeOperaciones) {
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

    public void guardar() {
        DetalleCompra registro = new DetalleCompra();
        registro.setCodigoDetalles(Integer.parseInt(txtCodigoDetalles.getText()));
        registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setCodigoProductos(((Producto) cmbCodigoProd.getSelectionModel().getSelectedItem()).getCodigoProducto());
        registro.setNumeroDocumento(((Compras) cmbNumeroDocumento.getSelectionModel().getSelectedItem()).getNumeroDocumento());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDetalleCompra(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoDetalles());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getCodigoProductos());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();
            listarDetallesCompra.add(registro);
            tblDetalleCompras.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtCodigoDetalles.setEditable(false);
        txtCostoUnitario.setEditable(false);
        txtCantidad.setEditable(false);
        cmbCodigoProd.setDisable(true);
        cmbNumeroDocumento.setDisable(true);
    }

    public void activarControles() {
        txtCodigoDetalles.setEditable(true);
        txtCostoUnitario.setEditable(true);
        txtCantidad.setEditable(true);
        cmbCodigoProd.setDisable(false);
        cmbNumeroDocumento.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoDetalles.clear();
        txtCostoUnitario.clear();
        txtCantidad.clear();
        cmbCodigoProd.getSelectionModel().clearSelection();
        cmbNumeroDocumento.getSelectionModel().clearSelection();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void regresar(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
