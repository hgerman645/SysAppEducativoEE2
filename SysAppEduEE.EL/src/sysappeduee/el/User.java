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
public class User {
     private int idUser;
    private String name;
    private String lastName;
    private String telephone;
    private String login;
    private String password;
    private int idRol;
    
    private int top_aux;
    private Rol rol;
    private String confirmPassword_aux;
    
    
    public User(){
        
    }
    
    public User(int idUser, String name, String lastName, String telephone, String login, String password) {
        this.idUser = idUser;
        this.name = name;
        this.lastName = lastName;
        this.telephone = telephone;
        this.login = login;
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getIdRol(){
        return idRol;
    }
    
    public void setIdRol(int idRol){
        this.idRol = idRol;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }
    
    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getConfirmPassword_aux() {
        return confirmPassword_aux;
    }

    public void setConfirmPassword_aux(String confirmPassword_aux) {
        this.confirmPassword_aux = confirmPassword_aux;
    }

    
    
}