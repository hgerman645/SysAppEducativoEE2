/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sysappeduee.dal;

import java.sql.*;
/**
 *
 * @author cliente
 */
public class ComunDB {
    class TypeDB {
        static final int SQLSERVER = 1;
        static final int MYSQL = 2;
    }
    
    static int typeDB = TypeDB.SQLSERVER; //Acceciendo al tipo de valor que usamos en la clase TypeDB
    
    static String conncetionUrl = "jdbc:sqlserver://Prueba24.mssql.somee.com;"
            + "database=Prueba24;"
            + "user=hs22001_SQLLogin_1;"
            + "password=lpfixes881;"
            + "loginTimeout=30;encrypt=false;trustServerCertificate=false";
    
    public static Connection getConexion() throws SQLException {
        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        Connection connection = DriverManager.getConnection(conncetionUrl);
        return connection;
    }
    
    public static Statement createStatement(Connection pConn) throws SQLException {
        Statement statement = pConn.createStatement();
        return statement;
    }
    
     public static PreparedStatement createPreparedStatement(Connection pConn, String pSql) throws SQLException {
        PreparedStatement statement = pConn.prepareStatement(pSql);
        return statement;
    }

    public static ResultSet obtenerResultSet(Statement pStatement, String pSql) throws SQLException {
        ResultSet resultSet = pStatement.executeQuery(pSql);
        return resultSet;
    }

    public static ResultSet obtenerResultSet(PreparedStatement pPreparedStatement) throws SQLException {
        ResultSet resultSet = pPreparedStatement.executeQuery();
        return resultSet;
    }

    public static int ejecutarSQL(String pSql) throws SQLException {
        int result;
        try (Connection connection = getConexion();) { // Obtener la conexion y encerrarla en try para cierre automatico 
            Statement statement = connection.createStatement();
            result = statement.executeUpdate(pSql); // Ejecutar la consulta SQL y devolvera un valor entero con la cantidad de filas afectadas
        } catch (SQLException ex) {
            throw ex; // Enviar el error al proximo metodo 
        }
        return result; // Retornar el valor de fila afectadas.
    }
    
    class UtilQuery {

        private String SQL; // Propiedad para almacenar la consulta SELECT
        private PreparedStatement statement; // Propiedad para almacenar PreparedStatement al cual se le agregaran los parametros ?
        private int numWhere; // Propiedad para almacenar la cantidad de campos incluidos en el WHERE de una consulta SELECT

        public UtilQuery() { // Constructor vacio
        }

        public UtilQuery(String SQL, PreparedStatement statement, int numWhere) { // Constructor lleno
            this.SQL = SQL;
            this.statement = statement;
            this.numWhere = numWhere;
        }

        // Encapsulamiento de la propiedades SQL,statement, numWhere
        public String getSQL() {
            return SQL;
        }

        public void setSQL(String SQL) {
            this.SQL = SQL;
        }

        public PreparedStatement getStatement() {
            return statement;
        }

        public void setStatement(PreparedStatement statement) {
            this.statement = statement;
        }

        public int getNumWhere() {
            return numWhere;
        }

        public void setNumWhere(int numWhere) {
            this.numWhere = numWhere;
        }
        // Fin de los metodos del encapsulamiento de las propiedades SQL,statement, numWhere

        /*El metodo AgregarWhereAnd lo utilizaremos para cuando agreguemos un nuevo campo en la consulta SELECT y automaticamente
        agregar el WHERE o AND segun la cantidad de campos agregados en la consulta, tambien incrementara en 1 la cantidad de campos 
        en el WHERE */
        public void AgregarWhereAnd(String pSql) {
            if (this.SQL != null) {
                if (this.numWhere == 0) { 
                    // Si el numWhere es cero significa que es el primer campo agregado en la consulta SELECT entonces agregamos el WHERE
                    this.SQL += " WHERE ";
                } else {
                    // Si el numWhere es diferente a cero significa que agregaremos el AND a la consulta SELECT 
                    this.SQL += " AND ";
                }
                this.SQL += pSql; // Agregar el valor extra a la consulta
            }
            this.numWhere++; // Incrementar en 1 la cantidad de campos actuales en el WHERE de la consulta SELECT
        }
    }
}

