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
public class Unit {
      private int Id;
    private String NameUnit;
    private int IdMatter;
     private int TopAux;
    private ArrayList<Unit> Matter;

    public Unit() {
    }

    
    public Unit(int Id, String NameUnit, int IdMatter, int TopAux, ArrayList<Unit> Matter) {
        this.Id = Id;
        this.NameUnit = NameUnit;
        this.IdMatter = IdMatter;
        this.TopAux = TopAux;
        this.Matter = Matter;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNameUnit() {
        return NameUnit;
    }

    public void setNameUnit(String NameUnit) {
        this.NameUnit = NameUnit;
    }

    public int getIdMatter() {
        return IdMatter;
    }

    public void setIdMatter(int IdMatter) {
        this.IdMatter = IdMatter;
    }

    public int getTopAux() {
        return TopAux;
    }

    public void setTopAux(int TopAux) {
        this.TopAux = TopAux;
    }

    public ArrayList<Unit> getMatter() {
        return Matter;
    }

    public void setMatter(ArrayList<Unit> Matter) {
        this.Matter = Matter;
    }

  
}
