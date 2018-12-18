/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retail.model;

import java.io.Serializable;

/**
 *
 * @author Amikom
 */
public class Supplier implements Serializable{
    private String kodeSupplier;
    private String namaSupplier;
    private String alamat;

    public Supplier() {
    }

    public Supplier(String kodeSupplier, String namaSupplier, String alamat) {
        this.kodeSupplier = kodeSupplier;
        this.namaSupplier = namaSupplier;
        this.alamat = alamat;
    }

    public String getKodeSupplier() {
        return kodeSupplier;
    }

    public void setKodeSupplier(String kodeSupplier) {
        this.kodeSupplier = kodeSupplier;
    }

    public String getNamaSupplier() {
        return namaSupplier;
    }

    public void setNamaSupplier(String namaSupplier) {
        this.namaSupplier = namaSupplier;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
}
