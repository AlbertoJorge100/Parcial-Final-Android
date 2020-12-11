package com.example.parcialfinal_pn17_i04_001.BaseDatos;

import com.example.parcialfinal_pn17_i04_001.Clases.Datos;

import java.util.ArrayList;

public class RespuestaDatos {
    private ArrayList<Datos> data;
    public ArrayList<Datos> getData(){
        return data;
    }
    public void setData(ArrayList<Datos> data){
        this.data=data;
    }
}
