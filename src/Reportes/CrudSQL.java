package Reportes;

import getset.variables;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class CrudSQL extends ConexionSQL {
    
   ResultSet rs;
   variables var=new variables();
   
   
   
   public CrudSQL() {
        // Puedes agregar inicializaciones aquí si es necesario
    }
   
   
    public void insertar(String DPI,String NOMBRE, String APELLIDO, String EDAD, String FECHANC, String TELEFONO, String DIRRECCION, String SEXO, String SANGRE, String ALERGIAS) {
        Connection conexion = null;
        Statement st = null;

        try {
            conexion = conectar();
            st = conexion.createStatement();
           String sql = "INSERT INTO hospital(DPI,NOMBRE, APELLIDO, EDAD, \"FECHANC\", TELEFONO, DIRRECION, SEXO, \"SANGRE\", ALERGIAS) VALUES('" + DPI + "','" + NOMBRE + "','" + APELLIDO + "','" + EDAD + "','" + FECHANC + "','" + TELEFONO + "','" + DIRRECCION + "','" + SEXO + "','"+ SANGRE + "','" + ALERGIAS + "');";
            JOptionPane.showMessageDialog(null, "El registro se ha guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            st.execute(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "El registro no se pudo guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
       public void cargarDatosEnTabla(JTable tbnDatos) {
    Connection conexion = null;
    Statement statement = null;
    ResultSet resultSet = null;

    try {
        // Establecer la conexión a la base de datos (asegúrate de tener una conexión a tu base de datos PostgreSQL configurada).
        conexion = conectar();
        
        // Crear un Statement para ejecutar la consulta SQL.
        statement = conexion.createStatement();
        
        // Ejecutar la consulta SQL para seleccionar todos los registros de la tabla "pacientes".
        String sql = "SELECT * FROM hospital";
        resultSet = statement.executeQuery(sql);

        // Crear un modelo de tabla para "tbnDatos".
        DefaultTableModel model = (DefaultTableModel) tbnDatos.getModel();
        
        // Limpia la tabla antes de cargar nuevos datos.
        model.setRowCount(0);

        // Recorre el conjunto de resultados y agrega cada fila a la tabla "tbnDatos".
        while (resultSet.next()) {
            Object[] fila = {
                resultSet.getString("DPI"),
                resultSet.getString("NOMBRE"),
                resultSet.getString("APELLIDO"),
                resultSet.getString("EDAD"),
                resultSet.getString("FECHANC"),
                resultSet.getString("TELEFONO"),
                resultSet.getString("DIRRECION"),
                resultSet.getString("SEXO"),
                resultSet.getString("SANGRE"),
                resultSet.getString("ALERGIAS")
            };
            model.addRow(fila);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Cierra los recursos.
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (conexion != null) conexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
  public void mostrar(String DPI) throws SQLException{
      
      
      try {
          Connection conexion = conectar();
        Statement st = conexion.createStatement();
        
        String sql = "SELECT * FROM hospital WHERE DPI = '" + DPI + "';";

        rs=st.executeQuery(sql);
        if(rs.next()){
            var.setId(rs.getString("DPI"));
            var.setNombre(rs.getString("NOMBRE"));
            var.setApellido(rs.getString("APELLIDO"));
            var.setEdad(rs.getString("EDAD"));
            var.setFechanc(rs.getString("FECHANC"));
            var.setTelefono(rs.getString("TELEFONO"));
            var.setDirrecion(rs.getString("DIRRECION"));
            var.setSexo(rs.getString("SEXO"));
            var.setSangre(rs.getString("SANGRE"));
            var.setAlergias(rs.getString("ALERGIAS"));
        }else{
            var.setId("");
            var.setNombre("");
            var.setApellido("");
            var.setEdad("");
            var.setFechanc("");
            var.setTelefono("");
            var.setDirrecion("");
            var.setSexo("");
            var.setSangre("");
            var.setAlergias("");
             JOptionPane.showMessageDialog(null, "no se encontro registro","sin registro",JOptionPane.INFORMATION_MESSAGE);
            
        }
        
        st.close();
        conexion.close();
        
      } catch (Exception e) {
         String mensajeError = "ERROR EN EL SISTEMA DE BUSQUEDA: " + e.getMessage();
    JOptionPane.showMessageDialog(null, mensajeError, "ERROR", JOptionPane.ERROR_MESSAGE);
            
      }
      
  
        
  }
  
  
  public void eliminar(String DPI, JTextField txtId2, JTextField txtNombre, JTextField txtApellido, JTextField txtEdad, JTextField txtFechanc, JTextField txtTelefono, JTextField txtDirrecion, JTextField txtSexo, JTextField txtSangre, JTextField txtAlergias) throws SQLException {
    try {
        // Confirmación de eliminación
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar a esta persona?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Si el usuario confirma la eliminación, se ejecuta la consulta de eliminación
            Connection conexion = conectar();
            Statement st = conexion.createStatement();
            String sql = "DELETE FROM hospital WHERE DPI = '" + DPI + "';";
            int filasAfectadas = st.executeUpdate(sql);
            st.close();
            conexion.close();

            if (filasAfectadas > 0) {
                // Si se eliminó el registro, también borra los datos en los campos de texto.
                txtId2.setText("");
                txtNombre.setText("");
                txtApellido.setText("");
                txtEdad.setText("");
                txtFechanc.setText("");
                txtTelefono.setText("");
                txtDirrecion.setText("");
                txtSexo.setText("");
                txtSangre.setText("");
                txtAlergias.setText("");

                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún registro con el ID especificado", "Registro no encontrado", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    } catch (Exception e) {
        String mensajeError = "ERROR EN EL SISTEMA DE ELIMINACIÓN: " + e.getMessage();
        JOptionPane.showMessageDialog(null, mensajeError, "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}

public void modificar(String DPI, String NOMBRE, String APELLIDO, String EDAD, String FECHANC, String TELEFONO, String DIRRECCION, String SEXO, String SANGRE, String ALERGIAS) {
    Connection conexion = null;
    Statement st = null;

    try {
        conexion = conectar();
        st = conexion.createStatement();
        
        // Construye la sentencia SQL de actualización
        StringBuilder sqlBuilder = new StringBuilder("UPDATE hospital SET ");
        List<String> camposModificados = new ArrayList<>();

        if (!NOMBRE.isEmpty()) {
            sqlBuilder.append("NOMBRE = '").append(NOMBRE).append("', ");
            camposModificados.add("nombre");
        }
        if (!APELLIDO.isEmpty()) {
            sqlBuilder.append("APELLIDO = '").append(APELLIDO).append("', ");
            camposModificados.add("apellido");
        }
        if (!EDAD.isEmpty()) {
            sqlBuilder.append("EDAD = '").append(EDAD).append("', ");
            camposModificados.add("edad");
        }
        // Continuar con el resto de los campos

        if (!camposModificados.isEmpty()) {
            // Eliminar la coma al final de la última columna actualizada
            sqlBuilder.delete(sqlBuilder.length() - 2, sqlBuilder.length());
            sqlBuilder.append(" WHERE DPI = '").append(DPI).append("';");
            
            String sql = sqlBuilder.toString();

            int filasAfectadas = st.executeUpdate(sql);

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Los datos se han modificado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún registro con el DPI especificado", "Registro no encontrado", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se especificaron campos para actualizar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "No se pudieron modificar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (st != null) {
                st.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

  


   
  

    public void insertar(String text, String text0, String text1, String text2, String text3, String text4, String text5, String text6, String text7, String text8,String text9) {
        throw new UnsupportedOperationException("Not supported yet."); // Para cambiar el cuerpo de los métodos generados, elija Herramientas | Plantillas
    }

    public void consultar(String text, String text0, String text1, String text2, String text3, String text4, String text5, String text6, String text7) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ResultSet consultar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void initComponents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void eliminar(String ID, JTextField txtNombre, JTextField txtApellido, JTextField txtEdad, JTextField txtFechanc, JTextField txtTelefono, JTextField txtDirrecion, JTextField txtSexo, JTextField txtSangre, JTextField txtAlergias) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

    

