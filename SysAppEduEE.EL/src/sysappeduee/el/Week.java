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
public class Week {
    private int Id;
    private String NameWeek;
    private ArrayList<Week> Matter;
     private int TopAux;

    public Week() {
    }

    public Week(int Id, String NameWeek, ArrayList<Week> Matter, int TopAux) {
        this.Id = Id;
        this.NameWeek = NameWeek;
        this.Matter = Matter;
        this.TopAux = TopAux;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNameWeek() {
        return NameWeek;
    }

    public void setNameWeek(String NameWeek) {
        this.NameWeek = NameWeek;
    }

    public ArrayList<Week> getMatter() {
        return Matter;
    }

    public void setMatter(ArrayList<Week> Matter) {
        this.Matter = Matter;
    }

    public int getTopAux() {
        return TopAux;
    }

    public void setTopAux(int TopAux) {
        this.TopAux = TopAux;
    }

   
    
}
