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
 * @author Amikom
 */
public class Beli implements Serializable{
    private String notaBeli;
    private Supplier supplier;
    private Timestamp tanggal; 

    public Beli() {
    }

    public Beli(String notaBeli, Supplier supplier, Timestamp tanggal) {
        this.notaBeli = notaBeli;
        this.supplier = supplier;
        this.tanggal = tanggal;
    }

    public String getNotaBeli() {
        return notaBeli;
    }

    public void setNotaBeli(String notaBeli) {
        this.notaBeli = notaBeli;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Timestamp getTanggal() {
        return tanggal;
    }

    public void setTanggal(Timestamp tanggal) {
        this.tanggal = tanggal;
    }
    
    
}
