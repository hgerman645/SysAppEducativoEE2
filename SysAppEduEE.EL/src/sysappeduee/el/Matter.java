/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sysappeduee.el;

import java.util.ArrayList;

/**
 *
 * @author german
 */
public class Matter {
    
    private int Id;
    private String NameMatter;
    private int IdWeek;
    private Unit IdUnit;
    private Week week;
    private ArrayList<Matter> Teacher;
     private int TopAux;

    public Matter() {
    }
    public Matter(int Id, String NameMatter, int IdWeek, Unit IdUnit, Week week, ArrayList<Matter> Teacher, int TopAux) {
        this.Id = Id;
        this.NameMatter = NameMatter;
        this.IdWeek = IdWeek;
        this.IdUnit = IdUnit;
        this.week = week;
        this.Teacher = Teacher;
        this.TopAux = TopAux;
    }    

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNameMatter() {
        return NameMatter;
    }

    public void setNameMatter(String NameMatter) {
        this.NameMatter = NameMatter;
    }

    public int getIdWeek() {
        return IdWeek;
    }

    public void setIdWeek(int IdWeek) {
        this.IdWeek = IdWeek;
    }

    public Unit getIdUnit() {
        return IdUnit;
    }

    public void setIdUnit(Unit IdUnit) {
        this.IdUnit = IdUnit;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public ArrayList<Matter> getTeacher() {
        return Teacher;
    }

    public void setTeacher(ArrayList<Matter> Teacher) {
        this.Teacher = Teacher;
    }

    public int getTopAux() {
        return TopAux;
    }

    public void setTopAux(int TopAux) {
        this.TopAux = TopAux;
    }

   
    
    
}

