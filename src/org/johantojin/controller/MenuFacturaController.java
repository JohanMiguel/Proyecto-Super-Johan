
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
import org.johantojin.bean.Clientes;
import org.johantojin.bean.Empleado;
import org.johantojin.bean.Factura;
import org.johantojin.db.Conexion;
import org.johantojin.reportes.GenerarReportes;
import org.johantojin.system.Principal;

/**
 *
 * @author CFCDC012
 */
public class MenuFacturaController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Factura> listarFactura;
    private ObservableList<Clientes> listarClientes;
    private ObservableList<Empleado> listarEmpleado;
 
    
    //Setiar los Objetos
    //Iniciales de ComboBox cmb"Funcion"
    @FXML private Button btnRegresar;
    @FXML private TextField txtNumeroFactura;
    @FXML private TextField txtEstado;
    @FXML private TextField txtTotalFactura;
    @FXML private TextField txtFechaFactura;
    @FXML private ComboBox<Clientes> cmbCodigoCliente;
    @FXML private ComboBox<Empleado> cmbCodigoEmpleado;
    @FXML private TableView<Factura> tblFacturas;
    @FXML private TableColumn<Factura, Integer> colNumeroFactura;
    @FXML private TableColumn<Factura, String> colEstado;
    @FXML private TableColumn<Factura, Double> colTotalFactura;
    @FXML private TableColumn<Factura, String> colFechaFactura;
    @FXML private TableColumn<Factura, Integer> colCodigoCliente;
    @FXML private TableColumn<Factura, Integer> colCodigoEmpleado;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoCliente.setItems(getClientes());
        cmbCodigoEmpleado.setItems(getEmpleados());
    }

    public void cargarDatos() {
        tblFacturas.setItems(getFactura());
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<>("numeroFactura"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colTotalFactura.setCellValueFactory(new PropertyValueFactory<>("totalFactura"));
        colFechaFactura.setCellValueFactory(new PropertyValueFactory<>("fechaFactura"));
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<>("codigoCliente"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<>("codigoEmpleado"));
    }

    public void seleccionarElementos() {
        if (tblFacturas.getSelectionModel().getSelectedItem() != null) {
            Factura facturaSeleccionada = tblFacturas.getSelectionModel().getSelectedItem();
            txtNumeroFactura.setText(String.valueOf(facturaSeleccionada.getNumeroFactura()));
            txtEstado.setText(facturaSeleccionada.getEstado());
            txtTotalFactura.setText(String.valueOf(facturaSeleccionada.getTotalFactura()));
            txtFechaFactura.setText(facturaSeleccionada.getFechaFactura());
            cmbCodigoCliente.getSelectionModel().select(buscarCliente(facturaSeleccionada.getCodigoCliente()));
            cmbCodigoEmpleado.getSelectionModel().select(buscarEmpleado(facturaSeleccionada.getCodigoEmpleado()));
        }
    }

    public Clientes buscarCliente(int codigoCliente) {
        Clientes resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCliente(?)}");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            if (registro.next()) {
                resultado = new Clientes(
                    registro.getInt("codigoCliente"),
                    registro.getString("NITCliente"),
                    registro.getString("nombreCliente"),
                    registro.getString("apellidoCliente"),
                    registro.getString("direccionCliente"),
                    registro.getString("telefonoCliente"),
                    registro.getString("correoCliente")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Empleado buscarEmpleado(int codigoEmpleado) {
        Empleado resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpleado(?)}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            if (registro.next()) {
                resultado = new Empleado(
                    registro.getInt("codigoEmpleado"),
                    registro.getString("nombresEmpleado"),
                    registro.getString("apellidosEmpleado"),
                    registro.getDouble("sueldo"),
                    registro.getString("direccion"),
                    registro.getString("turno"),
                    registro.getInt("codigoCargoEmpleado")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<Factura> getFactura() {
        ArrayList<Factura> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Factura(
                    resultado.getInt("numeroFactura"),
                    resultado.getString("estado"),
                    resultado.getDouble("totalFactura"),
                    resultado.getString("fechaFactura"),
                    resultado.getInt("codigoCliente"),
                    resultado.getInt("codigoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarFactura = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(
                    resultado.getInt("codigoCliente"),
                    resultado.getString("NITCliente"),
                    resultado.getString("nombreCliente"),
                    resultado.getString("apellidoCliente"),
                    resultado.getString("direccionCliente"),
                    resultado.getString("telefonoCliente"),
                    resultado.getString("correoCliente")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarClientes = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Empleado> getEmpleados() {
        ArrayList<Empleado> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleado(
                    resultado.getInt("codigoEmpleado"),
                    resultado.getString("nombresEmpleado"),
                    resultado.getString("apellidosEmpleado"),
                    resultado.getDouble("sueldo"),
                    resultado.getString("direccion"),
                    resultado.getString("turno"),
                    resultado.getInt("codigoCargoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarEmpleado = FXCollections.observableArrayList(lista);
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
        Factura registro = new Factura();
        registro.setNumeroFactura(Integer.parseInt(txtNumeroFactura.getText()));
        registro.setEstado(txtEstado.getText());
        registro.setTotalFactura(Double.parseDouble(txtTotalFactura.getText()));
        registro.setFechaFactura(txtFechaFactura.getText());
        registro.setCodigoCliente(((Clientes)cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoEmpleado(((Empleado)cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarFacturas (?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());
            procedimiento.execute();
            listarFactura.add(registro);
            tblFacturas.refresh(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        if (tblFacturas.getSelectionModel().getSelectedItem() != null) {
            Factura registro = tblFacturas.getSelectionModel().getSelectedItem();
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarFacturas(?)}");
                procedimiento.setInt(1, registro.getNumeroFactura());
                procedimiento.execute();
                listarFactura.remove(registro);
                limpiarControles();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Mensaje de alerta
        }
    }

    public void editar() {
        if (tblFacturas.getSelectionModel().getSelectedItem() != null) {
            Factura registro = tblFacturas.getSelectionModel().getSelectedItem();
            registro.setEstado(txtEstado.getText());
            registro.setTotalFactura(Double.parseDouble(txtTotalFactura.getText()));
            registro.setFechaFactura(txtFechaFactura.getText());
            registro.setCodigoCliente(((Clientes)cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            registro.setCodigoEmpleado(((Empleado)cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());

            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarFacturas(?, ?, ?, ?, ?, ?, ?)}");
                procedimiento.setInt(1, registro.getNumeroFactura());
                procedimiento.setString(2, registro.getEstado());
                procedimiento.setDouble(3, registro.getTotalFactura());
                procedimiento.setString(4, registro.getFechaFactura());
                procedimiento.setInt(5, registro.getCodigoCliente());
                procedimiento.setInt(6, registro.getCodigoEmpleado());
                procedimiento.execute();
                tblFacturas.refresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Mensaje de alerta
        }
    }

    public void reporte() {
        Map parametros = new HashMap();
        parametros.put("numeroFactura", null);
        GenerarReportes.mostrarReportes("ReporteFacturas.jasper", "Reporte de Facturas", parametros);
    }

    public void desactivarControles() {
        txtNumeroFactura.setEditable(false);
        txtEstado.setEditable(false);
        txtTotalFactura.setEditable(false);
        txtFechaFactura.setEditable(false);
        cmbCodigoCliente.setDisable(true);
        cmbCodigoEmpleado.setDisable(true);
    }

    public void activarControles() {
        txtNumeroFactura.setEditable(true);
        txtEstado.setEditable(true);
        txtTotalFactura.setEditable(true);
        txtFechaFactura.setEditable(true);
        cmbCodigoCliente.setDisable(false);
        cmbCodigoEmpleado.setDisable(false);
    }

    public void limpiarControles() {
        txtNumeroFactura.clear();
        txtEstado.clear();
        txtTotalFactura.clear();
        txtFechaFactura.clear();
        cmbCodigoCliente.getSelectionModel().clearSelection();
        cmbCodigoEmpleado.getSelectionModel().clearSelection();
    }

    public void regresar() {
        escenarioPrincipal.menuPrincipalView();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
}