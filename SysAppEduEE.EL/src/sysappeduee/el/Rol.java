/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sysappeduee.el;
import java.util.*;
/**
 *
 * @author cliente
 */
public class Rol {
 private int idRol;
 private String name;
 private int top_aux;
 private ArrayList<User>users;

    public Rol() {
    }

    public Rol(int idRol, String name, int top_aux, ArrayList<User> users) {
        this.idRol = idRol;
        this.name = name;
        this.top_aux = top_aux;
        this.users = users;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
 
 
}