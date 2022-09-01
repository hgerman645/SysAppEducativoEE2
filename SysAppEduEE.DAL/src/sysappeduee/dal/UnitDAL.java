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
public class UnitDAL {
     static String getFields() {
        return "r.Id,r.Name";
    }

    private static String getSelect(Unit pUnit) {
        String sql;
        sql = "SELECT";
        if (pUnit.getTopAux() > 0 && ComunDB.typeDB == ComunDB.TypeDB.SQLSERVER) {
            sql += "TOP " + pUnit.getTopAux() + " ";
        }
        sql += (getFields() + "FROM Unit r"); 
        return sql;
    }
    private static String addOrderBy(Unit pUnit) {
        String sql = "ORDER BY r.Id DESC";
        if (pUnit.getTopAux() > 0 && ComunDB.typeDB == ComunDB.TypeDB.MYSQL) {
         
            sql += " LIMIT " + pUnit.getTopAux() + " ";
        }
        return sql;
    }
   
    public static int create(Unit pUnit) throws Exception {
        int result;
        String sql;
        try ( Connection conn = ComunDB.getConexion();) { 
            sql = "INSERT INTO Unit(name) VALUES(?)";
            try ( PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { 
                ps.setString(1, pUnit.getNameUnit());
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
    public static int Update(Unit pUnit) throws Exception {
        int result;
        String sql;
        try ( Connection conn = ComunDB.getConexion();) {
            sql = "UPDATE Unit SET Name=? WHERE Id=?"; 
            try ( PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) 
            {  
                ps.setString(1, pUnit.getNameUnit());
                ps.setInt(2, pUnit.getId());
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
    
     public  static int delete(Unit pUnit)throws Exception{
        int result;
        String sql;
        try(Connection conn = ComunDB.getConexion();){
            sql = "DELETE FROM Unit WHERE Id=?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1, pUnit.getId());
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
     static int assignDataResultSet(Unit pUnit, ResultSet pResultSet, int pIndex ) throws Exception{
        
        pIndex++;
        pUnit.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pUnit.setNameUnit(pResultSet.getString(pIndex));
        return pIndex;
    }
      
    private static void getData(PreparedStatement pPS, ArrayList<Unit> pUnit)throws Exception{
        try(ResultSet pResultSet = ComunDB.obtenerResultSet(pPS);){
            while( pResultSet.next()){
                Unit unit = new Unit();
                assignDataResultSet(unit, pResultSet, 0);
                pUnit.add(unit);
            }
          pResultSet.close();
        } catch (SQLException ex){
            throw  ex;
        }
    }
    

    public static Unit getById(Unit pUnit)throws Exception{
        Unit unit = new Unit();
        ArrayList<Unit> units = new ArrayList();
        try (Connection conn = ComunDB.getConexion();){
            String sql = getSelect(pUnit);
            sql += " WHERE r.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1,pUnit.getId());
                getData(ps, units);
                ps.close();
            }catch (SQLException ex){
                throw ex;
            }
            conn.close();
        }catch (SQLException ex){
            throw ex;
        }
        if(units.size() >0 ){
            unit = units.get(0); 
        }
        return unit;
    }
    
   
    public  static  ArrayList<Unit> getAll()throws Exception {
        ArrayList<Unit> units;
        units = new ArrayList<>();
        try(Connection conn = ComunDB.getConexion()){
            String sql = getSelect(new Unit());
            sql += addOrderBy(new Unit());
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                getData(ps, units);
                ps.close();
            } catch (SQLException ex){
                throw  ex;
            }
            conn.close();
        }catch (SQLException ex){
            throw ex;
        }
        return units;
    }
    
    static void  querySelect(Unit punit , ComunDB.UtilQuery pUtilQuery) throws SQLException{
        PreparedStatement statement = pUtilQuery.getStatement();
        if(punit.getId() >0){
            pUtilQuery.AgregarWhereAnd(" r.Id=? ");
            if (statement != null){
                statement.setInt(pUtilQuery.getNumWhere(), punit.getId());
            }
        }
        if(punit.getNameUnit() != null && punit.getNameUnit().trim().isEmpty() == false){
            pUtilQuery.AgregarWhereAnd("r.Name LIKE ?");
            if(statement != null){
                statement.setString(pUtilQuery.getNumWhere(), "%" + punit.getNameUnit() + "%");
            }
        }
    }
    
   
    
    public static ArrayList<Unit> buscar(Unit pUnit) throws Exception {
        ArrayList<Unit> unit = new ArrayList();
        try (Connection conn = ComunDB.getConexion();) { 
            String sql = getSelect(pUnit); 
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0); 
            querySelect(pUnit, utilQuery); 
            sql = utilQuery.getSQL(); 
            sql +=  addOrderBy(pUnit); 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { 
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pUnit, utilQuery);  
                getData(ps, unit);  
                ps.close(); 
            } catch (SQLException ex) {
                throw ex;  
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex; 
        }
        return unit; 
    }
}
