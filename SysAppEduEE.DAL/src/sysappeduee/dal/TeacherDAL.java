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
import sysappeduee.dal.*;
import sysappeduee.el.*;

public class TeacherDAL {
     static String GetFields(){
        return "r.Id,r.Name,r.LastName,r.Telephone,r.Email";
    }
    private static String GetSelect(Teacher pTeacher){
        String Sql;
        Sql="SELeCT";
        if(pTeacher.getTopAux()>0&& ComunDB.typeDB==ComunDB.TypeDB.SQLSERVER){
            Sql+= "Top" +pTeacher.getTopAux() + " ";
        }
        Sql+=(GetFields() + " FROM Teacher r");
        return Sql;
    }
    private static String AddOrderBy(Teacher pTeacher){
        String Sql = "ORDER BY r.Id DESC";
        if (pTeacher.getTopAux()>0&& ComunDB.typeDB==ComunDB.TypeDB.MYSQL){
            Sql+= " LIMIT " + pTeacher.getTopAux();
        }
        return Sql;
    }
    private static int Create(Teacher pTeacher)throws Exception{
        int Result;
        String Sql;
        try ( Connection Conn= ComunDB.getConexion();){
            Sql = "INSERT INTO Teacher(Name,LastName,Telephone,Email) VALUES (?,?,?,?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(Conn, Sql);){
                ps.setInt(1,pTeacher.getId());
                ps.setString(2, pTeacher.getName());
                ps.setString(3, pTeacher.getLastName());
                ps.setString(4, pTeacher.getTelephone());
                ps.setString(5, pTeacher.getEmail());
                
                Result= ps.executeUpdate();
                ps.close();
            }catch(SQLException ex){
                throw ex;
            }
            Conn.close();
        }
        return Result;
    }
    public static int Modify(Teacher pTeacher)throws Exception{
        int Result;
        String Sql;
        try ( Connection Conn=ComunDB.getConexion();){
            Sql= "UPDATE Teacher SET Name=? WHERE Id=?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(Conn, Sql);){
                ps.setString(1, pTeacher.getName());
                ps.setInt(2, pTeacher.getId());
                Result = ps.executeUpdate();
                ps.close();
            }catch (SQLException ex){
                throw ex;
            }Conn.close();
        }catch(SQLException ex){
            throw ex;
        }
        return Result;
    }
    public static int Delete(Teacher pTeacher)throws Exception{
        int Result;
        String Sql;
        try ( Connection Conn= ComunDB.getConexion();){
            Sql=" DELETE FROM Teacher WHERE Id=?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(Conn, Sql);){
                ps.setInt(1, pTeacher.getId());
                Result=ps.executeUpdate();
                ps.close();
            }catch (SQLException ex){
                throw ex;
            }Conn.close();
        }catch (SQLException ex){
            throw ex;
        }
        return Result;
    }
    static int AssignDataResultSet(Teacher pTeacher,ResultSet pResultSet,int pIndex)throws Exception{
        pIndex++;
        pTeacher.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pTeacher.setId(pResultSet.getInt(pIndex));
        return pIndex;
    }
    private static void GetData(PreparedStatement pPS, ArrayList<Teacher>pTeachers)throws Exception{
        try(ResultSet resultSet=ComunDB.obtenerResultSet(pPS);){
            while (resultSet.next()){
                Teacher teacher = new Teacher();
                AssignDataResultSet(teacher, resultSet,0);
                pTeachers.add(teacher);
            }
            resultSet.close();
        }catch(SQLException ex){
            throw ex;
        }
    }
    public static Teacher GetById(Teacher pTeacher)throws Exception{
        Teacher teacher = new Teacher();
        ArrayList<Teacher>teachers = new ArrayList();
        try (Connection Conn = ComunDB.getConexion();){
            String Sql = GetSelect(pTeacher);
            Sql = " WHERE r.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(Conn, Sql);){
                ps.setInt(1, pTeacher.getId());
                GetData(ps, teachers);
                ps.close();
            }catch (SQLException ex){
                throw ex;
            }Conn.close();
        }catch (SQLException ex){
            throw ex;
        }
        if (teachers.size()>0){
            teacher = teachers.get(0);
        }
        return teacher;
    }
    static void QuerySelect(Teacher pTeacher, ComunDB.UtilQuery pUtilQuery)throws SQLException{
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pTeacher.getId()>0){
            pUtilQuery.AgregarWhereAnd(" r.Id ");
            if (statement !=null){
                statement.setInt(pUtilQuery.getNumWhere(), pTeacher.getId());
            }
        }
        if (pTeacher.getLastName()!= null && pTeacher.getName().trim().isEmpty()==false){
            pUtilQuery.AgregarWhereAnd(" r.Name LIKE ? ");
            if (statement != null){
                statement.setString(pUtilQuery.getNumWhere(),"%"+pTeacher.getName()+"%");
            }
        }
    }
    public static ArrayList<Teacher>Search(Teacher pTeacher)throws Exception{
        ArrayList<Teacher>teachers = new ArrayList();
        try(Connection Conn= ComunDB.getConexion();){
            String Sql=GetSelect(pTeacher);
            ComunDB comunDB= new ComunDB();
            ComunDB.UtilQuery utilQuery =comunDB.new UtilQuery(Sql,null,0);
            QuerySelect(pTeacher, utilQuery);
            Sql= utilQuery.getSQL();
            Sql+= AddOrderBy(pTeacher);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(Conn, Sql);){
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                QuerySelect(pTeacher, utilQuery);
                GetData(ps,teachers);
                ps.close();
            }catch (SQLException ex){
                throw ex;
            }
            Conn.close();
        }catch (SQLException ex){
            throw ex;
        }
        return teachers;
    }

}
