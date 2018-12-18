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
public class ItemBeli implements Serializable{
    private Beli beli;
    private Barang barang;
    private int jumlah;
    private int hargaBeli;
    private int hargaJual;

    public ItemBeli() {
    }

    public ItemBeli(Beli beli, Barang barang, int jumlah, int hargaBeli, int hargaJual) {
        this.beli = beli;
        this.barang = barang;
        this.jumlah = jumlah;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
    }

    public Beli getBeli() {
        return beli;
    }

    public void setBeli(Beli beli) {
        this.beli = beli;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(int hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public int getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(int hargaJual) {
        this.hargaJual = hargaJual;
    }
    
    
}
