/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retail.dao.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import retail.model.Beli;

/**
 *
 * @author Amikom
 */
public interface IBeliDao extends Remote{
    int save(Beli beli)throws RemoteException;
    List<Beli> getAll()throws RemoteException;
}
