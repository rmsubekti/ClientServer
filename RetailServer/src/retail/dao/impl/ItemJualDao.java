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
import retail.dao.api.IItemJualDao;
import retail.model.Barang;
import retail.model.ItemJual;
import retail.model.Jual;

/**
 *
 * @author su
 */
public class ItemJualDao extends UnicastRemoteObject implements IItemJualDao{
    private Connection conn = null;
    private String strSql="";

    public ItemJualDao(Connection conn) throws RemoteException{
        this.conn = conn;
    }

    @Override
    public int save(ItemJual itemJual) throws RemoteException {
        int result = 0;
        System.out.println("retail.dao.impl.ItemBeliDao.save()");
        strSql = "insert into item_beli (item_beli.[nota_beli], item_beli.[kode_barang], item_beli.[jumlah], item_beli.[harga_beli], item_beli.[harga_jual]) values(?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ps.setString(1,itemJual.getJual().getNotaJual());
            ps.setString(2,itemJual.getBarang().getKodeBarang());
            ps.setInt(3,itemJual.getJumlah());
            ps.setInt(4,itemJual.getHargaBeli());
            ps.setInt(5,itemJual.getHargaJual());
            
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ItemJualDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<ItemJual> getByIdBeli(String idJual) throws RemoteException {
        List<ItemJual> daftarItem = new ArrayList<>();
        System.out.println("remote execute : retail.dao.impl.ItemJualDao.getAll()");
        strSql="select * from v_item_jual where nota_jual=?";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ps.setString(1, idJual);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Jual jual = new Jual();
                jual.setNotaJual(rs.getString("nota_jual"));
                Barang barang = new Barang();
                barang.setKodeBarang(rs.getString("kode_barang"));
                barang.setNamaBarang(rs.getString("nama_barang"));
                ItemJual item = new ItemJual(
                        jual,
                        barang,
                        rs.getInt("jumlah"),
                        rs.getInt("sub_total"),
                        rs.getInt("sub_total")
                );
                daftarItem.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemBeliDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return daftarItem;
    }
    
}
