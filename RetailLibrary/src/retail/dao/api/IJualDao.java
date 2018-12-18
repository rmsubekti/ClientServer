/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retail.dao.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import retail.model.Jual;

/**
 *
 * @author su
 */
public interface IJualDao extends Remote{
    int save(Jual jual) throws RemoteException;
    List<Jual> getAll()throws RemoteException;
}
