/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retail.dao.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import retail.dao.api.IBarangDao;
import retail.model.Barang;

/**
 *
 * @author Amikom
 */
public class BarangDao extends UnicastRemoteObject implements IBarangDao {
    private Connection conn = null;
    private String strSql="";
    
    public BarangDao(Connection conn) throws RemoteException{
        this.conn = conn;
    }
    
    @Override
    public int save(Barang barang) throws RemoteException {
        int result = 0;
        System.out.println("retail.dao.impl.BarangDao.save()");
        strSql = "insert into barang (kode_barang, nama_barang, stok, harga_beli, harga_jual) values(?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ps.setString(1,barang.getKodeBarang());
            ps.setString(2,barang.getNamaBarang());
            ps.setInt(3,barang.getHargaBeli());
            ps.setInt(4,barang.getHargaJual());
            ps.setInt(5,barang.getStok());
            
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BarangDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(Barang barang) throws RemoteException {
        int result= 0;
        System.out.println("retail.dao.impl.BarangDao.update()");
        strSql = "update barang set barang.[nama_barang]=?, stok=?, barang.[harga_beli] = ?, barang.[harga_jual]=? where barang.[kode_barang]=?";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ps.setString(1,barang.getNamaBarang());
            ps.setInt(2,barang.getHargaBeli());
            ps.setInt(3,barang.getHargaJual());
            ps.setInt(4,barang.getStok());
            ps.setString(5,barang.getKodeBarang());
            
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BarangDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(Barang barang) throws RemoteException {
        int result=0;
        System.out.println("retail.dao.impl.BarangDao.delete()");
        strSql = "delete from barang where barang.[kode_barang] =?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ps.setString(1, barang.getKodeBarang());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BarangDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
        
    }

    @Override
    public List<Barang> getAll() throws RemoteException {
        List<Barang> daftarBarang = new ArrayList<Barang>();
        System.out.println("remote execute : retail.dao.impl.BarangDao.getAll()");
        strSql="select barang.[kode_barang], barang.[nama_barang], barang.[stok], barang.[harga_beli], barang.[harga_jual]"
                + "from barang order by barang.[nama_barang]";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Barang barang = new Barang(
                        rs.getString("kode_barang"),
                        rs.getString("nama_barang"),
                        rs.getInt("stok"),
                        rs.getInt("harga_beli"),
                        rs.getInt("harga_jual")
                );
                daftarBarang.add(barang);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BarangDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return daftarBarang;
    }

    @Override
    public List<Barang> getByName(String namaBarang) throws RemoteException {        
        List<Barang> daftarBarang = new ArrayList<Barang>();
        System.out.println("retail.dao.impl.BarangDao.getByName(String namaBarang)");
        strSql="select barang.[kode_barang], barang.[nama_barang], barang.[stok], barang.[harga_beli], barang.[harga_jual]"
                + "from barang where barang.[nama_barang] like ? order by barang.[nama_barang]";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ps.setString(1, "%"+namaBarang+"%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Barang barang = new Barang(
                        rs.getString("kode_barang"),
                        rs.getString("nama_barang"),
                        rs.getInt("stok"),
                        rs.getInt("harga_beli"),
                        rs.getInt("harga_jual")
                );
                daftarBarang.add(barang);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BarangDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return daftarBarang;
    }
    
}
