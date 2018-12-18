/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retail.dao.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import retail.model.Supplier;

/**
 *
 * @author Amikom
 */
public interface ISupplierDao extends Remote{
    int save(Supplier supplier)throws RemoteException;
    int update(Supplier supplier)throws RemoteException;
    int delete(Supplier supplier)throws RemoteException;
    List<Supplier> getAll()throws RemoteException;
    List<Supplier> getByName(String namaSupplier) throws RemoteException;
}
