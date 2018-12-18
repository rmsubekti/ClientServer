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
import retail.dao.api.IJualDao;
import retail.model.Jual;

/**
 *
 * @author su
 */
public class JualDao extends UnicastRemoteObject implements IJualDao {
    private Connection conn = null;
    private String strSql="";

    public JualDao(Connection conn) throws RemoteException{
        this.conn = conn;
    }
    
    @Override
    public int save(Jual jual) throws RemoteException {
        int result = 0;
        System.out.println("retail.dao.impl.JualDao.save()");
        strSql = "insert into jual (jual.[nota_jual], jual.[tanggal]) values(?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ps.setString(1,jual.getNotaJual());
            ps.setTimestamp(2,(Timestamp) jual.getTanggal());
            
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(JualDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<Jual> getAll() throws RemoteException {
        List<Jual> daftarJual = new ArrayList<Jual>();
        System.out.println("remote execute : retail.dao.impl.JualDao.getAll()");
        strSql="select jual.[nota_jual], jual.[tanggal]"
                + "from jual";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Jual jual = new Jual(
                        rs.getString("nota_jual"),
                        rs.getTimestamp("tanggal")
                );
                daftarJual.add(jual);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeliDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return daftarJual;
    }
    
}
