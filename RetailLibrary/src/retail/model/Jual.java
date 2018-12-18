/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retail.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author su
 */
public class Jual implements Serializable{
    private String notaJual;
    private Timestamp tanggal; 

    public Jual() {
    }

    public Jual(String notaJual, Timestamp tanggal) {
        this.notaJual = notaJual;
        this.tanggal = tanggal;
    }

    public String getNotaJual() {
        return notaJual;
    }

    public void setNotaJual(String notaJual) {
        this.notaJual = notaJual;
    }

    public Timestamp getTanggal() {
        return tanggal;
    }

    public void setTanggal(Timestamp tanggal) {
        this.tanggal = tanggal;
    }
    
    
}
