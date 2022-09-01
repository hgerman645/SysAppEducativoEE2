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
public class UserDAL {
//Metodo para encriptar la password del usuario
    public static String encryptMD5(String txt) throws Exception {
        try {
            StringBuffer sb;
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(txt.getBytes());
            sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException ex) {
            throw ex;
        }
    }
    
    // Metodo para obtener los campos a utilizar en la consulta SELECT de la tabla de Usuario
    static String getCampos() {
        return "u.IdUser, u.Name, u.LastName, u.Telephone, u.Login, u.IdRol";
    }
    
    // Metodo para obtener el SELECT a la tabla Usuario y el top en el caso que se utilice una base de datos SQL SERVER
    private static String getSelect(User pUser) {
        String sql;
        sql = "SELECT ";
        if (pUser.getTop_aux() > 0 && ComunDB.typeDB == ComunDB.TypeDB.SQLSERVER) {
             // Agregar el TOP a la consulta SELECT si el gestor de base de datos es SQL SERVER y getTop_aux es mayor a cero
            sql += "TOP " + pUser.getTop_aux() + " ";
        }
        sql += (getCampos() + " FROM Usuario u");
        return sql;
    }
    
     // Metodo para obtener Order by a la consulta SELECT de la tabla Usuario y ordene los registros de mayor a menor 
    private static String addOrderBy(User pUser) {
        String sql = " ORDER BY u.IdUser DESC";
        if (pUser.getTop_aux() > 0 && ComunDB.typeDB == ComunDB.TypeDB.MYSQL) {
            // Agregar el LIMIT a la consulta SELECT de la tabla de Usuario en el caso que getTop_aux() sea mayor a cero y el gestor de base de datos
            // sea MYSQL
            sql += " LIMIT " + pUser.getTop_aux() + " ";
        }
        return sql;
    }
    
    private static boolean existsLogin(User pUser) throws Exception {
        boolean exists = false;
        ArrayList<User> users = new ArrayList();
        try (Connection conn = ComunDB.getConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = getSelect(pUser);  // Obtener la consulta SELECT de la tabla Usuario
            // Concatenar a la consulta SELECT de la tabla Usuario el WHERE y el filtro para saber si existe el login
            sql += " WHERE u.IdUser<>? AND u.Login=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pUser.getIdUser());  // Agregar el parametros a la consulta donde estan el simbolo ? #1 
                ps.setString(2, pUser.getLogin());  // Agregar el parametros a la consulta donde estan el simbolo ? #2 
                getData(ps, users); // Llenar el ArrayList de USuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement el en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        if (users.size() > 0) { // Verificar si el ArrayList de Usuario trae mas de un registro en tal caso solo debe de traer uno
            User user;
             // Se solucciono tenia valor de 1 cuando debe de ser cero
            user = users.get(0); // Si el ArrayList de Usuario trae un registro o mas obtenemos solo el primero 
            if (user.getIdUser() > 0 && user.getLogin().equals(pUser.getLogin())) {
                // Si el Id de Usuario es mayor a cero y el Login que se busco en la tabla de Usuario es igual al que solicitamos
                // en los parametros significa que el login ya existe en la base de datos y devolvemos true en la variable "existe"
                exists = true;
            }
        }
        return exists; //Devolver la variable "existe" con el valor true o false si existe o no el Login en la tabla de Usuario de la base de datos

    }
    
    // Metodo para poder insertar un nuevo registro en la tabla de Usuario
    public static int create(User pUser) throws Exception {
        int result;
        String sql;
        boolean exists = existsLogin(pUser); // verificar si el usuario que se va a crear ya existe en nuestra base de datos
        if (exists == false) {
            try (Connection conn = ComunDB.getConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
                 //Definir la consulta INSERT a la tabla de Usuario utilizando el simbolo "?" para enviar parametros
                sql = "INSERT INTO Usuario(Name,LastName,Telephone,Login,Password,IdRol) VALUES(?,?,?,?,?,?)";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                    ps.setString(1, pUser.getName()); // Agregar el parametro a la consulta donde estan el simbolo "?" #1  
                    ps.setString(2, pUser.getLastName()); // Agregar el parametro a la consulta donde estan el simbolo "?" #2 
                    ps.setString(3, pUser.getTelephone()); // agregar el parametro a la consulta donde estan el simbolo "?" #3 
                    ps.setString(4, pUser.getLogin()); // agregar el parametro a la consulta donde estan el simbolo "?" #4 
                    ps.setString(5, encryptMD5(pUser.getPassword())); // agregar el parametro a la consulta donde estan el simbolo "?" #5 
                    ps.setInt(6, pUser.getIdRol());
                    result = ps.executeUpdate(); // ejecutar la consulta INSERT en la base de datos
                    ps.close(); // cerrar el PreparedStatement
                } catch (SQLException ex) {
                    throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
                }
                conn.close();
            } // Handle any errors that may have occurred.
            catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al obtener la conexion en el caso que suceda
            }
        } else {
            result = 0;
            throw new RuntimeException("Login ya existe"); // enviar una exception para notificar que el login existe
        }
        return result; // Retornar el numero de fila afectadas en el INSERT en la base de datos 
    }
    
    // Metodo para poder actualizar un registro en la tabla de Usuario
    public static int update(User pUser) throws Exception {
        int result;
        String sql;
        boolean exists = existsLogin(pUser); // verificar si el usuario que se va a modificar ya existe en nuestra base de datos
        if (exists == false) {
            try (Connection conn = ComunDB.getConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
                //Definir la consulta UPDATE a la tabla de Usuario utilizando el simbolo ? para enviar parametros
                sql = "UPDATE Usuario SET Name=?, LastName=?, Telephone=?, Login=?, IdRol=? WHERE IdUser=?";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase ComunDB
                    ps.setString(1, pUser.getName()); // agregar el parametro a la consulta donde estan el simbolo ? #1  
                    ps.setString(2, pUser.getLastName()); // agregar el parametro a la consulta donde estan el simbolo ? #2  
                    ps.setString(3, pUser.getTelephone()); // agregar el parametro a la consulta donde estan el simbolo ? #3  
                    ps.setString(4, pUser.getLogin()); // agregar el parametro a la consulta donde estan el simbolo ? #4  
                    ps.setInt(5, pUser.getIdRol());
                    ps.setInt(6, pUser.getIdUser()); // agregar el parametro a la consulta donde estan el simbolo ? #6  
                    result = ps.executeUpdate(); // ejecutar la consulta UPDATE en la base de datos
                    ps.close(); // cerrar el PreparedStatement
                } catch (SQLException ex) {
                    throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
                }
                conn.close(); // cerrar la conexion a la base de datos
            } 
            catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al obtener la conexion en el caso que suceda 
            }
        } else {
            result = 0;
            throw new RuntimeException("Login ya existe"); // enviar una exception para notificar que el login existe
        }
        return result; // Retornar el numero de fila afectadas en el UPDATE en la base de datos 
    }
    
    // Metodo para poder eliminar un registro en la tabla de Usuario
    public static int delete(User pUser) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.getConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "DELETE FROM Usuario WHERE IdUser=?"; //definir la consulta DELETE a la tabla de Usuario utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {  // obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pUser.getIdUser()); // agregar el parametro a la consulta donde estan el simbolo ? #1 
                result = ps.executeUpdate(); // ejecutar la consulta DELETE en la base de datos
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex;  // enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el DELETE en la base de datos 
    }
    
    // Metodo para llenar las propiedades de la entidad de Usuario con los datos que viene en el ResultSet,
    // el metodo nos ayudara a no preocuparlos por los indices al momento de obtener los valores del ResultSet
    static int asignDataResultSet(User pUser, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT u.IdUser(indice 1), u.Name(indice 2), u.LastName(indice 3), u.Telephone(indice 4), u.Login(indice 5), 
        // FROM Usuario
        pIndex++;
        pUser.setIdUser(pResultSet.getInt(pIndex)); // index 1
        
        pIndex++;
        pUser.setName(pResultSet.getString(pIndex)); // index 2
        
        pIndex++;
        pUser.setLastName(pResultSet.getString(pIndex)); // index 3
        
        pIndex++;
        pUser.setTelephone(pResultSet.getString(pIndex)); // index 4
        
        pIndex++;
        pUser.setLogin(pResultSet.getString(pIndex)); // index 5
        
        return pIndex;
    }
    
    // Metodo para  ejecutar el ResultSet de la consulta SELECT a la tabla de Usuario
    private static void getData(PreparedStatement pPS, ArrayList<User> pUsers) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Usuario
                User user = new User();
                // Llenar las propiedaddes de la Entidad Usuario con los datos obtenidos de la fila en el ResultSet
                asignDataResultSet(user, resultSet, 0);
                pUsers.add(user); // agregar la entidad Usuario al ArrayList de Usuario
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex;// enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }
    
    // Metodo para  ejecutar el ResultSet de la consulta SELECT a la tabla de Usuario y JOIN a la tabla de Rol
    private static void getDataIncludeRol(PreparedStatement pPS, ArrayList<User> pUsers) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            HashMap<Integer, Rol> rolMap = new HashMap(); //crear un HashMap para automatizar la creacion de instancias de la clase Rol
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Usuario JOIN a la tabla de Rol
                User usuario = new User();
                 // Llenar las propiedaddes de la Entidad Usuario con los datos obtenidos de la fila en el ResultSet
                int index = asignDataResultSet(usuario, resultSet, 0);
                if (rolMap.containsKey(usuario.getIdRol()) == false) { // verificar que el HashMap aun no contenga el Rol actual
                    Rol rol = new Rol();
                    // en el caso que el Rol no este en el HashMap se asignara
                    RolDAL.asignDataResultSet(rol, resultSet, index);
                    rolMap.put(rol.getIdRol(), rol); // agregar el Rol al  HashMap
                    usuario.setRol(rol); // agregar el Rol al Usuario
                } else {
                    // En el caso que el Rol existe en el HashMap se agregara automaticamente al Usuario
                    usuario.setRol(rolMap.get(usuario.getIdRol())); 
                }
                pUsers.add(usuario); // Agregar el Usuario de la fila actual al ArrayList de Usuario
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }
    
    // Metodo para obtener por Id un registro de la tabla de Usuario 
    public static User getById(User pUser) throws Exception {
        User user = new User();
        ArrayList<User> users = new ArrayList();
        try (Connection conn = ComunDB.getConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = getSelect(pUser); // obtener la consulta SELECT de la tabla Usuario
             // Concatenar a la consulta SELECT de la tabla Usuario el WHERE  para comparar el campo Id
            sql += " WHERE u.IdUser=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pUser.getIdUser()); // agregar el parametro a la consulta donde estan el simbolo ? #1 
                getData(ps, users); // Llenar el ArrayList de Usuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        if (users.size() > 0) { // verificar si el ArrayList de Usuario trae mas de un registro en tal caso solo debe de traer uno
            user = users.get(0); // Si el ArrayList de Usuario trae un registro o mas obtenemos solo el primero
        }
        return user; // devolver el Usuario encontrado por Id 
    }
    
    // Metodo para obtener todos los registro de la tabla de Usuario
    public static ArrayList<User> getAll() throws Exception {
        ArrayList<User> users;
        users = new ArrayList<>();
        try (Connection conn = ComunDB.getConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = getSelect(new User()); // obtener la consulta SELECT de la tabla Usuario
            sql += addOrderBy(new User()); // concatenar a la consulta SELECT de la tabla Usuario el ORDER BY por Id 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase ComunDB
                getData(ps, users); // Llenar el ArrayList de Usuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return users; // devolver el ArrayList de Usuario
    }
    
    // Metodo para asignar los filtros de la consulta SELECT de la tabla de Usuario de forma dinamica
    static void querySelect(User pUser, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); // obtener el PreparedStatement al cual aplicar los parametros
        if (pUser.getIdUser() > 0) { // verificar si se va incluir el campo Id en el filtro de la consulta SELECT de la tabla de Usuario
            pUtilQuery.AgregarWhereAnd(" u.IdUser=? "); // agregar el campo Id al filtro de la consulta SELECT y agregar el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo Id a la consulta SELECT de la tabla de Usuario
                statement.setInt(pUtilQuery.getNumWhere(), pUser.getIdUser());
            }
        }
        
        if (pUser.getName() != null && pUser.getName().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" u.Name LIKE ? ");
            if (statement != null) {
              
                statement.setString(pUtilQuery.getNumWhere(), pUser.getName());
            }
        }
       
        if (pUser.getLastName() != null && pUser.getLastName().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" u.LastName LIKE ? "); 
            if (statement != null) {
                 // agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Usuario
                statement.setString(pUtilQuery.getNumWhere(), "%" + pUser.getLastName() + "%");
            }
        }
        
        if (pUser.getTelephone() != null && pUser.getTelephone().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" u.Telephone LIKE ? "); // agregar el campo Apellido al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo Apellido a la consulta SELECT de la tabla de Usuario
                statement.setString(pUtilQuery.getNumWhere(), "%" + pUser.getTelephone() + "%");
            }
        }
        
        if (pUser.getLogin() != null && pUser.getLogin().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" u.Login=? "); // agregar el campo Login al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo Login a la consulta SELECT de la tabla de Usuario
                statement.setString(pUtilQuery.getNumWhere(), pUser.getLogin());
            }
        }
        
        if (pUser.getIdRol() > 0) {
            pUtilQuery.AgregarWhereAnd(" u.IdRol=? "); // agregar el campo IdRol al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo IdRol a la consulta SELECT de la tabla de Usuario
                statement.setInt(pUtilQuery.getNumWhere(), pUser.getIdRol());
            }
        }
    }
    
    // Metodo para obtener todos los registro de la tabla de Usuario que cumplan con los filtros agregados 
     // a la consulta SELECT de la tabla de Usuario 
    public static ArrayList<User> search(User pUser) throws Exception {
        ArrayList<User> users = new ArrayList();
        try (Connection conn = ComunDB.getConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = getSelect(pUser); // obtener la consulta SELECT de la tabla Usuario
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            querySelect(pUser, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Usuario 
            sql = utilQuery.getSQL();
            sql += addOrderBy(pUser); // Concatenar a la consulta SELECT de la tabla Usuario el ORDER BY por Id
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase ComunDB
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pUser, utilQuery); // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Usuario
                getData(ps, users); // Llenar el ArrayList de Usuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } 
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return users; 
    }
    
    // Metodo para verificar si el Usuario puede ser autorizado en el sistema
    // comparando el Login, Password, Estatus en la base de datos
    public static User login(User pUser) throws Exception {
        User user = new User();
        ArrayList<User> users = new ArrayList();
        String password = encryptMD5(pUser.getPassword()); // Encriptar el password en MD5
        try (Connection conn = ComunDB.getConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = getSelect(pUser); // Obtener la consulta SELECT de la tabla Usuario
             // Concatenar a la consulta SELECT de la tabla Usuario el WHERE  para comparar los campos de Login, Password, Estatus
            sql += " WHERE u.Login=? AND u.Password=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pUser.getLogin()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
                ps.setString(2, password); // Agregar el parametro a la consulta donde estan el simbolo ? #2 
                
                getData(ps, users); // Llenar el ArrayList de Usuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } 
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        if (users.size() > 0) { // Verificar si el ArrayList de Usuario trae mas de un registro en tal caso solo debe de traer uno
            user = users.get(0); // Si el ArrayList de Usuario trae un registro o mas obtenemos solo el primero
        }
        return user; // Devolver la instancia de Usuario 
    }
    
    // Metodo para cambiar el password de un Usuario el cual solo se puede cambiar si envia el password actual correctamente
    public static int changePassword(User pUser, String pPasswordAnt) throws Exception {
        int result;
        String sql;
        User userAnt = new User();
        userAnt.setLogin(pUser.getLogin());
        userAnt.setPassword(pPasswordAnt);
        User userAut = login(userAnt); // Obtenemos el Usuario autorizado validandolo en el metodo de login
        // Si el usuario que retorno el metodo de login tiene el Id mayor a cero y el Login es igual que el Login del Usuario que viene
        // en el parametro es un Usuario Autorizado
        if (userAut.getIdUser() > 0 && userAut.getLogin().equals(pUser.getLogin())) {
            try (Connection conn = ComunDB.getConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
                sql = "UPDATE Usuario SET Password=? WHERE IdUser=?"; // Crear la consulta Update a la tabla de Usuario para poder modificar el Password
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                    // Agregar el parametro a la consulta donde estan el simbolo ? #1 pero antes encriptar el password para enviarlo encriptado
                    ps.setString(1, encryptMD5(pUser.getPassword())); //
                    ps.setInt(2, pUser.getIdUser()); // Agregar el parametro a la consulta donde estan el simbolo ? #2 
                    result = ps.executeUpdate(); // Ejecutar la consulta UPDATE en la base de datos
                    ps.close(); // Cerrar el PreparedStatement
                } catch (SQLException ex) {
                    throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
                }
                conn.close(); // Cerrar la conexion a la base de datos
            }
            catch (SQLException ex) {
                throw ex;// Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
            }
        } else {
            result = 0;
            // Enviar la excepcion en el caso que el usuario que intenta modificar el password ingresa un password incorrecto
            throw new RuntimeException("El password actual es incorrecto");
        }
        return result; // Retornar el numero de fila afectadas en el UPDATE en la base de datos 
    }
    
    public static ArrayList<User> searchIncludeRol(User pUsuario) throws Exception {
        ArrayList<User> usuarios = new ArrayList();
        try (Connection conn = ComunDB.getConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = "SELECT "; // Iniciar la variables para el String de la consulta SELECT
            if (pUsuario.getTop_aux() > 0 && ComunDB.typeDB == ComunDB.TypeDB.SQLSERVER) {
                sql += "TOP " + pUsuario.getTop_aux() + " "; // Agregar el TOP en el caso que se este utilizando SQL SERVER
            }
            sql += getCampos(); // Obtener los campos de la tabla de Usuario que iran en el SELECT
            sql += ",";
            sql += RolDAL.getCampos(); // Obtener los campos de la tabla de Rol que iran en el SELECT
            sql += " FROM Usuario u";
            sql += " JOIN Rol r on (u.IdRol=r.IdRol)"; // agregar el join para unir la tabla de Usuario con Rol
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            querySelect(pUsuario, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Usuario 
            sql = utilQuery.getSQL();
            sql += addOrderBy(pUsuario); // Concatenar a la consulta SELECT de la tabla Usuario el ORDER BY por Id
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pUsuario, utilQuery); // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Usuario
                getDataIncludeRol(ps, usuarios);// Llenar el ArrayList de Usuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;// Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex;// Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return usuarios; // Devolver el ArrayList de Usuario
    }
}    