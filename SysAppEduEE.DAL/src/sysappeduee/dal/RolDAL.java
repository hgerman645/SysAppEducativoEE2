/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sysappeduee.dal;

import java.util.*;
import java.sql.*;

import sysappeduee.el.*;
/**
 *
 * @author cliente
 */
public class RolDAL {
   
    // Metodo para obtener los campos a utilizar en la consulta SELECT de la tabla de Rol
    static String getCampos() {
        return "r.IdRol,r.Name";
    }

    // Metodo para obtener el SELECT a la tabla Rol y el TOP en el caso que se utilice una base de datos SQL SERVER
    private static String getSelect(Rol pRol) {
        String sql;
        sql = "SELECT ";
        if (pRol.getTop_aux() > 0 && ComunDB.typeDB == ComunDB.TypeDB.SQLSERVER) {
            // Agregar el TOP a la consulta SELECT si el gestor de base de datos es SQL SERVER y "getTop_aux" es mayor a cero
            sql += "TOP " + pRol.getTop_aux() + " ";
        }
        sql += (getCampos() + " FROM Rol r"); // Agregar los campos de la tabla de Rol mas el FROM a la tabla Rol con su alias "r"
        return sql;
    }

    // Metodo para obtener Order by a la consulta SELECT de la tabla Rol y ordene los registros de mayor a menor 
    private static String addOrderBy(Rol pRol) {
        String sql = " ORDER BY r.IdRol DESC";
        if (pRol.getTop_aux() > 0 && ComunDB.typeDB == ComunDB.TypeDB.MYSQL) {
            // Agregar el LIMIT a la consulta SELECT de la tabla de Rol en el caso que getTop_aux() sea mayor a cero y el gestor de base de datos
            // sea MYSQL
            sql += " LIMIT " + pRol.getTop_aux() + " ";
        }
        return sql;
    }
    // Metodo para poder insertar un nuevo registro en la tabla de Rol
    public static int create(Rol pRol) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.getConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "INSERT INTO Rol(Name) VALUES(?)"; // Definir la consulta INSERT a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pRol.getName()); // Agregar el parametro a la consulta donde estan el simbolo ? #1  
                result = ps.executeUpdate(); // Ejecutar la consulta INSERT en la base de datos
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el INSERT en la base de datos 
    }

    // Metodo para poder actualizar un registro en la tabla de Rol
    public static int update(Rol pRol) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.getConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "UPDATE Rol SET Name=? WHERE IdRol=?"; // Definir la consulta UPDATE a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pRol.getName()); // Agregar el parametro a la consulta donde estan el simbolo ? #1  
                ps.setInt(2, pRol.getIdRol()); // Agregar el parametro a la consulta donde estan el simbolo ? #2  
                result = ps.executeUpdate(); // Ejecutar la consulta UPDATE en la base de datos
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el UPDATE en la base de datos 
    }

    // Metodo para poder eliminar un registro en la tabla de Rol
    public static int delete(Rol pRol) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.getConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "DELETE FROM Rol WHERE IdRol=?";  // Definir la consulta DELETE a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pRol.getIdRol()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
                result = ps.executeUpdate();  // Ejecutar la consulta DELETE en la base de datos
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close();  // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el DELETE en la base de datos 
    }    
    
    // Metodo para llenar las propiedades de la entidad de Rol con los datos que viene en el ResultSet,
    // el metodo nos ayudara a no preocuparlos por los indices al momento de obtener los valores del ResultSet
    static int asignDataResultSet(Rol pRol, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT r.Id(indice 1),r.Nombre(indice 2) * FROM Rol
        pIndex++;
        pRol.setIdRol(pResultSet.getInt(pIndex)); // index 1
        pIndex++;
        pRol.setName(pResultSet.getString(pIndex)); // index 2
        return pIndex;
    }
    
    // Metodo para  ejecutar el ResultSet de la consulta SELECT a la tabla de Rol 
    private static void getData(PreparedStatement pPS, ArrayList<Rol> pRoles) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Rol
                Rol rol = new Rol(); 
                asignDataResultSet(rol, resultSet, 0); // Llenar las propiedaddes de la Entidad Rol con los datos obtenidos de la fila en el ResultSet
                pRoles.add(rol); // Agregar la entidad Rol al ArrayList de Rol
            }
            resultSet.close(); // Cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }
    
   // Metodo para obtener por Id un registro de la tabla de Rol 
    public static Rol getById(Rol pRol) throws Exception {
        Rol rol = new Rol();
        ArrayList<Rol> roles = new ArrayList();
        try (Connection conn = ComunDB.getConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = getSelect(pRol); // Obtener la consulta SELECT de la tabla Rol
            sql += " WHERE r.IdRol=?"; // Concatenar a la consulta SELECT de la tabla Rol el WHERE 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pRol.getIdRol()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
                getData(ps, roles); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close();  // Cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        if (roles.size() > 0) { // Verificar si el ArrayList de Rol trae mas de un registro en tal caso solo debe de traer uno
            rol = roles.get(0); // Si el ArrayList de Rol trae un registro o mas obtenemos solo el primero 
        }
        return rol; // Devolver el rol encontrado por Id 
    }

    // Metodo para obtener todos los registro de la tabla de Rol
    public static ArrayList<Rol> getAll() throws Exception {
        ArrayList<Rol> roles;
        roles = new ArrayList<>();
        try (Connection conn = ComunDB.getConexion();) {// Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = getSelect(new Rol());  // Obtener la consulta SELECT de la tabla Rol
            sql += addOrderBy(new Rol());  // Concatenar a la consulta SELECT de la tabla Rol el ORDER BY por Id 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                getData(ps, roles); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } 
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return roles; // Devolver el ArrayList de Rol
    }
    
    // Metodo para asignar los filtros de la consulta SELECT de la tabla de Rol de forma dinamica
    static void querySelect(Rol pRol, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); // Obtener el PreparedStatement al cual aplicar los parametros
        if (pRol.getIdRol() > 0) { // Verificar si se va incluir el campo Id en el filtro de la consulta SELECT de la tabla de Rol
            pUtilQuery.AgregarWhereAnd(" r.IdRol=? "); // Agregar el campo Id al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) { 
                // Agregar el parametro del campo Id a la consulta SELECT de la tabla de Rol
                statement.setInt(pUtilQuery.getNumWhere(), pRol.getIdRol()); 
            }
        }
        // Verificar si se va incluir el campo Nombre en el filtro de la consulta SELECT de la tabla de Rol
        if (pRol.getName() != null && pRol.getName().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" r.Name LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Rol
                statement.setString(pUtilQuery.getNumWhere(), "%" + pRol.getName() + "%"); 
            }
        }
    }

     // Metodo para obtener todos los registro de la tabla de Rol que cumplan con los filtros agregados 
     // a la consulta SELECT de la tabla de Rol 
    public static ArrayList<Rol> search(Rol pRol) throws Exception {
        ArrayList<Rol> roles = new ArrayList();
        try (Connection conn = ComunDB.getConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = getSelect(pRol); // Obtener la consulta SELECT de la tabla Rol
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0); 
            querySelect(pRol, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Rol 
            sql = utilQuery.getSQL(); 
            sql += addOrderBy(pRol); // Concatenar a la consulta SELECT de la tabla Rol el ORDER BY por Id
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pRol, utilQuery);  // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Rol
                getData(ps, roles); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return roles; // Devolver el ArrayList de Rol
    }
}