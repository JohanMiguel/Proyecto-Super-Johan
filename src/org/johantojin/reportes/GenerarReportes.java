
package org.johantojin.reportes;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.johantojin.db.Conexion;

/**
 *
 * fecha de creacion 7/06/24
 */
public class GenerarReportes {
    public static void mostrarReportes(String nombreReporte, String titulo, Map parametro){
    InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
        try{
            // libreria de la api, (en add jar)
            //
        JasperReport reporteMaestro = (JasperReport)JRLoader.loadObject(reporte);
        JasperPrint reporteImpreso = JasperFillManager.fillReport(reporteMaestro, parametro, Conexion.getInstance().getConexion());
        JasperViewer visor = new JasperViewer(reporteImpreso, false);
        visor.setTitle(titulo);
        visor.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}


/*
Inteface Map
HasMao es uno de los objetos que implementa un conjunto de key-valu,
Tiene un constructos sin parametros new hasMap() y su  finalidad suele 
reerirse para agrupar infomacion en un unico objeto.



Tiene cierta similitud con la coleccion de objeto ArrayList,pero con la diferencia de que estos 
tienen un ordenSel hasing (abierto,cerado)
en la que se alamcena el registro que es generada por una funcion se aplica 
a la llave del registro

Hash hace referencia de una tecica de organizacion de archivp


En java el HasMap posee un espacio de memoria y cuando se guarda un objeto en dicho
espacio se determina su direccion, aplicando una funcion a la llave que le indique
Cada objeto se indentifica mediante algun identificador apropiado
*/