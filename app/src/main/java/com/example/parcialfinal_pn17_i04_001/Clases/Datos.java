package com.example.parcialfinal_pn17_i04_001.Clases;

import com.google.gson.annotations.SerializedName;

public class Datos {
    @SerializedName("Descripcion")
    public String Descripcion;

    @SerializedName("Datos")
    public String Datos;
    public Datos(){

    }
    public String getDescripcion() {
        return Descripcion.substring(0,5);
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getDatos() {
        return Datos;
    }

    public void setDatos(String datos) {
        Datos = datos;
    }
}
