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
import retail.dao.api.IItemBeliDao;
import retail.model.Barang;
import retail.model.Beli;
import retail.model.ItemBeli;

/**
 *
 * @author Amikom
 */
public class ItemBeliDao extends UnicastRemoteObject implements IItemBeliDao{
    private Connection conn = null;
    private String strSql="";
    
    public ItemBeliDao(Connection conn) throws RemoteException{
        this.conn = conn;
    }
    @Override
    public int save(ItemBeli itemBeli) throws RemoteException {
        int result = 0;
        System.out.println("retail.dao.impl.ItemBeliDao.save()");
        strSql = "insert into item_beli (item_beli.[nota_beli], item_beli.[kode_barang], item_beli.[jumlah], item_beli.[harga_beli], item_beli.[harga_jual]) values(?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ps.setString(1,itemBeli.getBeli().getNotaBeli());
            ps.setString(2,itemBeli.getBarang().getKodeBarang());
            ps.setInt(3,itemBeli.getJumlah());
            ps.setInt(4,itemBeli.getHargaBeli());
            ps.setInt(5,itemBeli.getHargaJual());
            
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ItemBeliDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<ItemBeli> getByIdBeli(String idBeli) throws RemoteException {
        List<ItemBeli> daftarItem = new ArrayList<ItemBeli>();
        System.out.println("remote execute : retail.dao.impl.ItemBeliDao.getAll()");
        strSql="select * from v_item_beli where nota_beli=?";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ps.setString(1, idBeli);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Beli beli = new Beli();
                beli.setNotaBeli(rs.getString("nota_beli"));
                Barang barang = new Barang();
                barang.setKodeBarang(rs.getString("kode_barang"));
                barang.setNamaBarang(rs.getString("nama_barang"));
                ItemBeli item = new ItemBeli(
                        beli,
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
