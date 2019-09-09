/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.antecedentes;

import java.io.Serializable;
import java.util.Date;

/**
 *Clase que encapsula los datos de los antecedentes
 * implementa la interfaz serializable
 * @author Duvan Poveda
 */
class Antecedentes implements Serializable{
    private String fecha;
    private String descripcion;
    private String tipo;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
