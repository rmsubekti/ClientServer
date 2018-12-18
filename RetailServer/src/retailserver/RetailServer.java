/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retailserver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import retail.dao.impl.BarangDao;
import retail.dao.impl.BeliDao;
import retail.dao.impl.ItemBeliDao;
import retail.dao.impl.ItemJualDao;
import retail.dao.impl.JualDao;
import retail.dao.impl.SupplierDao;

/**
 *
 * @author Amikom
 */
public class RetailServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            
            try {            
                String url = "jdbc:jtds:sqlserver://localhost:1433/DB_PCS_18222182";
                String user="sa"; 
                String password="amikom";

                Connection conn = DriverManager.getConnection(url, user,password);
                
                try {
                    BarangDao barangDao = new BarangDao(conn);
                    SupplierDao supplierDao = new SupplierDao(conn);
                    BeliDao beliDao = new BeliDao(conn);
                    ItemBeliDao itemBeliDao = new ItemBeliDao(conn);
                    JualDao jualDao = new JualDao(conn);
                    ItemJualDao itemJualDao = new ItemJualDao(conn);
                    Registry reg = LocateRegistry.createRegistry(1099);
                    reg.rebind("brgDao", barangDao);
                    reg.rebind("supplierDao", supplierDao);
                    reg.rebind("beliDao", beliDao);
                    reg.rebind("itemBeliDao", itemBeliDao);
                    reg.rebind("jualDao", jualDao);
                    reg.rebind("itemJualDao", itemJualDao);
                    System.out.println(">>Server running <</n");
                } catch (RemoteException ex) {
                    Logger.getLogger(RetailServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(RetailServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RetailServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
