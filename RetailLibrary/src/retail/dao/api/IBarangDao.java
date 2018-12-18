/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retail.dao.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import retail.model.Barang;

/**
 *
 * @author Amikom
 */
public interface IBarangDao extends Remote{
    int save(Barang barang)throws RemoteException;
    int update(Barang barang)throws RemoteException;
    int delete(Barang barang)throws RemoteException;
    List<Barang> getAll()throws RemoteException;
    List<Barang> getByName(String namaBarang)throws RemoteException;
}
