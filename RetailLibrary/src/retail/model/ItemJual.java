/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retail.model;

import java.io.Serializable;

/**
 *
 * @author su
 */
public class ItemJual implements Serializable{
    private Jual jual;
    private Barang barang;
    private int jumlah;
    private int hargaBeli;
    private int hargaJual;

    public ItemJual() {
    }

    public ItemJual(Jual jual, Barang barang, int jumlah, int hargaBeli, int hargaJual) {
        this.jual = jual;
        this.barang = barang;
        this.jumlah = jumlah;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
    }

    public Jual getJual() {
        return jual;
    }

    public void setJual(Jual jual) {
        this.jual = jual;
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
