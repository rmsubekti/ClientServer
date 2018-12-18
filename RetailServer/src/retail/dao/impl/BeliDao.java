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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import retail.dao.api.IBeliDao;
import retail.model.Beli;
import retail.model.Supplier;

/**
 *
 * @author Amikom
 */
public class BeliDao extends UnicastRemoteObject implements IBeliDao{
    private Connection conn = null;
    private String strSql="";
    
    public BeliDao(Connection conn) throws RemoteException{
        this.conn = conn;
    }
    @Override
    public int save(Beli beli) throws RemoteException {
        
        int result = 0;
        System.out.println("retail.dao.impl.BeliDao.save()");
        strSql = "insert into beli (beli.[nota_beli], beli.[tanggal], beli.[kode_supplier]) values(?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ps.setString(1,beli.getNotaBeli());
            ps.setTimestamp(2,(Timestamp) beli.getTanggal());
            ps.setString(3,beli.getSupplier().getKodeSupplier());
            
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BeliDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<Beli> getAll() throws RemoteException {
        List<Beli> daftarBeli = new ArrayList<Beli>();
        System.out.println("remote execute : retail.dao.impl.BeliDao.getAll()");
        strSql="select v_beli.[nota_beli], v_beli.[nama_supplier], v_beli.[tanggal]"
                + "from v_beli";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Supplier supplier = new Supplier();
                supplier.setNamaSupplier(rs.getString("nama_supplier"));
                Beli beli = new Beli(
                        rs.getString("nota_beli"),
                        supplier,
                        rs.getTimestamp("tanggal")
                );
                daftarBeli.add(beli);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeliDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return daftarBeli;
    }
    
}
