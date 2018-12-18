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
import retail.dao.api.ISupplierDao;
import retail.model.Supplier;

/**
 *
 * @author Amikom
 */
public class SupplierDao extends UnicastRemoteObject implements ISupplierDao{

    private Connection conn = null;
    private String strSql ="";
    public SupplierDao (Connection conn) throws RemoteException{
        this.conn = conn;
    }
    @Override
    public int save(Supplier supplier) throws RemoteException {
        int result = 0;
        System.out.println("retail.dao.impl.SupplierDao.save()");
        strSql = "insert into supplier (supplier.[kode_supplier], supplier.[nama_supplier], supplier.[alamat]) values(?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ps.setString(1,supplier.getKodeSupplier());
            ps.setString(2,supplier.getNamaSupplier());
            ps.setString(3,supplier.getAlamat());
            
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(Supplier supplier) throws RemoteException {
        
        int result= 0;
        System.out.println("retail.dao.impl.SupplierDao.update()");
        strSql = "update supplier set supplier.[kode_supplier]=?, supplier.[alamat]=?  where supplier.[kode_supplier]=?";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ps.setString(1, supplier.getNamaSupplier());
            ps.setString(2, supplier.getAlamat());
            ps.setString(3, supplier.getKodeSupplier());
            
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(Supplier supplier) throws RemoteException {
        
        int result=0;
        System.out.println("retail.dao.impl.SupplierDao.delete()");
        strSql = "delete from supplier where supplier.[kode_supplier] =?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ps.setString(1, supplier.getKodeSupplier());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
        
    }

    @Override
    public List<Supplier> getAll() throws RemoteException {
        List<Supplier> daftarSupplier = new ArrayList<Supplier>();
        System.out.println("retail.dao.impl.SupplierDao.getAll()");
        strSql="select supplier.[kode_supplier], supplier.[nama_supplier], supplier.[alamat]"
                + "from supplier order by supplier.[nama_supplier]";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Supplier supplier = new Supplier(
                        rs.getString("kode_supplier"),
                        rs.getString("nama_supplier"),
                        rs.getString("alamat")
                );
                daftarSupplier.add(supplier);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return daftarSupplier;
    }

    @Override
    public List<Supplier> getByName(String namaSupplier) throws RemoteException {
        List<Supplier> daftarSupplier = new ArrayList<Supplier>();
        System.out.println("retail.dao.impl.SupplierDao.getByName(String namaSupplier)");
        strSql="select supplier.[kode_supplier], supplier.[nama_supplier], supplier.[alamat]"
                + "from supplier where supplier.[nama_supplier] like ? order by supplier.[nama_supplier]";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            ps.setString(1, "%"+namaSupplier+"%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Supplier supplier = new Supplier(
                        rs.getString("kode_supplier"),
                        rs.getString("nama_supplier"),
                        rs.getString("alamat")
                );
                daftarSupplier.add(supplier);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return daftarSupplier;
    }
    
}
