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
/**
 *
 * @author german
 */
public class WeekDAL {
     static String getFields() {
        return "r.Id,r.Name";
    }
 private static String getSelect(Week pWeek) {
        String sql;
        sql = "SELECT";
        if (pWeek.getTopAux() > 0 && ComunDB.typeDB == ComunDB.TypeDB.SQLSERVER) {
            sql += "TOP " + pWeek.getTopAux() + " ";
        }
        sql += (getFields() + "FROM Week r"); 
        return sql;
    }
    private static String addOrderBy(Week pWeek) {
        String sql = "ORDER BY r.Id DESC";
        if (pWeek.getTopAux() > 0 && ComunDB.typeDB == ComunDB.TypeDB.MYSQL) {
         
            sql += " LIMIT " + pWeek.getTopAux() + " ";
        }
        return sql;
    }
    public static int create(Week pWeek) throws Exception {
        int result;
        String sql;
        try ( Connection conn = ComunDB.getConexion();) { 
            sql = "INSERT INTO Week(name) VALUES(?)";
            try ( PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { 
                ps.setString(1, pWeek.getNameWeek());
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
    public static int Update(Week pWeek) throws Exception {
        int result;
        String sql;
        try ( Connection conn = ComunDB.getConexion();) {
            sql = "UPDATE Week SET Name=? WHERE Id=?"; 
            try ( PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) 
            {  
                ps.setString(1, pWeek.getNameWeek());
                ps.setInt(2, pWeek.getId());
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
     public  static int delete(Week pWeek)throws Exception{
        int result;
        String sql;
        try(Connection conn = ComunDB.getConexion();){
            sql = "DELETE FROM Week WHERE Id=?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1, pWeek.getId());
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
     static int assignDataResultSet(Week pWeek, ResultSet pResultSet, int pIndex ) throws Exception{
        
        pIndex++;
        pWeek.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pWeek.setNameWeek(pResultSet.getString(pIndex));
        return pIndex;
    }
      
    private static void getData(PreparedStatement pPS, ArrayList<Week> pWeek)throws Exception{
        try(ResultSet pResultSet = ComunDB.obtenerResultSet(pPS);){
            while( pResultSet.next()){
                Week week = new Week();
                assignDataResultSet(week, pResultSet, 0);
                pWeek.add(week);
            }
          pResultSet.close();
        } catch (SQLException ex){
            throw  ex;
        }
    }
    
    public static Week getById(Week pWeek)throws Exception{
        Week week = new Week();
        ArrayList<Week> weeks = new ArrayList();
        try (Connection conn = ComunDB.getConexion();){
            String sql = getSelect(pWeek);
            sql += " WHERE r.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1,pWeek.getId());
                getData(ps, weeks);
                ps.close();
            }catch (SQLException ex){
                throw ex;
            }
            conn.close();
        }catch (SQLException ex){
            throw ex;
        }
        if(weeks.size() >0 ){
            week = weeks.get(0); 
        }
        return week;
    }
    
    public  static  ArrayList<Week> getAll()throws Exception {
        ArrayList<Week> weeks;
        weeks = new ArrayList<>();
        try(Connection conn = ComunDB.getConexion()){
            String sql = getSelect(new Week());
            sql += addOrderBy(new Week());
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                getData(ps, weeks);
                ps.close();
            } catch (SQLException ex){
                throw  ex;
            }
            conn.close();
        }catch (SQLException ex){
            throw ex;
        }
        return weeks;
    }
    
    static void  querySelect(Week pweek , ComunDB.UtilQuery pUtilQuery) throws SQLException{
        PreparedStatement statement = pUtilQuery.getStatement();
        if(pweek.getId() >0){
            pUtilQuery.AgregarWhereAnd(" r.Id=? ");
            if (statement != null){
                statement.setInt(pUtilQuery.getNumWhere(), pweek.getId());
            }
        }
        if(pweek.getNameWeek() != null && pweek.getNameWeek().trim().isEmpty() == false){
            pUtilQuery.AgregarWhereAnd("r.Name LIKE ?");
            if(statement != null){
                statement.setString(pUtilQuery.getNumWhere(), "%" + pweek.getNameWeek() + "%");
            }
        }
    }
    
    public static ArrayList<Week> buscar(Week pWeek) throws Exception {
        ArrayList<Week> week = new ArrayList();
        try (Connection conn = ComunDB.getConexion();) { 
            String sql = getSelect(pWeek); 
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0); 
            querySelect(pWeek, utilQuery); 
            sql = utilQuery.getSQL(); 
            sql +=  addOrderBy(pWeek); 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { 
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pWeek, utilQuery);  
                getData(ps, week);  
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;  
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex; 
        }
        return week; 
    }
}
