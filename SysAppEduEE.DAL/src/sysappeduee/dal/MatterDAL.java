/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sysappeduee.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sysappeduee.el.*;
import sysappeduee.el.Matter;
/**
 *
 * @author german
 */
public class MatterDAL {
     static String getFields() {
        return "r.Id,r.Name";
    }
 private static String getSelect(Matter pMatter) {
        String sql;
        sql = "SELECT";
        if (pMatter.getTopAux() > 0 && ComunDB.typeDB == ComunDB.TypeDB.SQLSERVER) {
            sql += "TOP " + pMatter.getTopAux() + " ";
        }
        sql += (getFields() + "FROM Matter r"); 
        return sql;
    }
    private static String addOrderBy(Matter pMatter) {
        String sql = "ORDER BY r.Id DESC";
        if (pMatter.getId() > 0 && ComunDB.typeDB == ComunDB.TypeDB.MYSQL) {
         
            sql += " LIMIT " + pMatter.getId() + " ";
        }
        return sql;
    }
   
    public static int create(Matter pMatter) throws Exception {
        int result;
        String sql;
        try ( Connection conn = ComunDB.getConexion();) { 
            sql = "INSERT INTO Matter (name) VALUES(?)";
            try ( PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { 
                ps.setString(1, pMatter.getNameMatter());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
    public static int Update(Matter pMatter) throws Exception {
        int result;
        String sql;
        try ( Connection conn = ComunDB.getConexion();) {
            sql = "UPDATE Week SET Name=? WHERE Id=?"; 
            try ( PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) 
            {  
                ps.setString(1, pMatter.getNameMatter());
                ps.setInt(2, pMatter.getId());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
        return result; 
    }
    
     public  static int delete(Matter pMatter)throws Exception{
        int result;
        String sql;
        try(Connection conn = ComunDB.getConexion();){
            sql = "DELETE FROM Matter WHERE Id=?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1, pMatter.getId());
                result = ps.executeUpdate();
                ps.close();
            }catch(SQLException ex){
                throw ex;
            }
            conn.close();
        }catch (SQLException ex){
            throw ex;
        }
        return result;
    }
   static int assignDataResultSet(Matter pMatter, ResultSet pResultSet, int pIndex ) throws Exception{
        
        pIndex++;
        pMatter.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pMatter.setNameMatter(pResultSet.getString(pIndex));
        return pIndex;
    }
      
    private static void getData(PreparedStatement pPS, ArrayList<Matter> pMatter)throws Exception{
        try(ResultSet pResultSet = ComunDB.obtenerResultSet(pPS);){
            while( pResultSet.next()){
                Matter matter = new Matter();
                assignDataResultSet(matter, pResultSet, 0);
                pMatter.add(matter);
            }
          pResultSet.close();
        } catch (SQLException ex){
            throw  ex;
        }
    }
    

     public static Matter getById(Matter pMatter)throws Exception{
         Matter matter = new Matter();
        ArrayList<Matter> matters = new ArrayList();
        try (Connection conn = ComunDB.getConexion();){
            String sql = getSelect(pMatter);
            sql += " WHERE r.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1,pMatter.getId());
                getData(ps, matters);
                ps.close();
            }catch (SQLException ex){
                throw ex;
            }
            conn.close();
        }catch (SQLException ex){
            throw ex;
        }
        if(matters.size() >0 ){
            matter = matters.get(0); 
        }
        return matter;
    }
 
    static void  querySelect(Matter pMatter , ComunDB.UtilQuery pUtilQuery) throws SQLException{
        PreparedStatement statement = pUtilQuery.getStatement();
        if(pMatter.getId() >0){
            pUtilQuery.AgregarWhereAnd(" r.Id=? ");
            if (statement != null){
                statement.setInt(pUtilQuery.getNumWhere(), pMatter.getId());
            }
        }
        if(pMatter.getNameMatter() != null && pMatter.getNameMatter().trim().isEmpty() == false){
            pUtilQuery.AgregarWhereAnd("r.Name LIKE ?");
            if(statement != null){
                statement.setString(pUtilQuery.getNumWhere(), "%" + pMatter.getNameMatter() + "%");
            }
        }
    }
    public static ArrayList<Matter> buscar(Matter pMatter) throws Exception {
        ArrayList<Matter> matter = new ArrayList();
        try (Connection conn = ComunDB.getConexion();) { 
            String sql = getSelect(pMatter); 
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0); 
            querySelect(pMatter, utilQuery); 
            sql = utilQuery.getSQL(); 
            sql +=  addOrderBy(pMatter); 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { 
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pMatter, utilQuery);  
                getData(ps, matter);  
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;  
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex; 
        }
        return matter; 
    }
}



