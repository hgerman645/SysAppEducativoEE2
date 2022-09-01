/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sysappeduee.el;

/**
 *
 * @author german
 */
public class Teacher {
      private int Id;
    private String Name;
    private String LastName;
    private String Telephone;
    private String Email;
     private int TopAux;
    private int IdStudent; 
    private int IdMatter;
    private Matter matter;

    public Teacher(int Id, String Name, String LastName, String Telephone, String Email, int TopAux, int IdStudent, int IdMatter, Matter matter) {
        this.Id = Id;
        this.Name = Name;
        this.LastName = LastName;
        this.Telephone = Telephone;
        this.Email = Email;
        this.TopAux = TopAux;
        this.IdStudent = IdStudent;
        this.IdMatter = IdMatter;
        this.matter = matter;
    }

    public Teacher(){
        
    }
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public int getTopAux() {
        return TopAux;
    }

    public void setTopAux(int TopAux) {
        this.TopAux = TopAux;
    }

    public int getIdStudent() {
        return IdStudent;
    }

    public void setIdStudent(int IdStudent) {
        this.IdStudent = IdStudent;
    }

    public int getIdMatter() {
        return IdMatter;
    }

    public void setIdMatter(int IdMatter) {
        this.IdMatter = IdMatter;
    }

    public Matter getMatter() {
        return matter;
    }

    public void setMatter(Matter matter) {
        this.matter = matter;
    }
    
}
