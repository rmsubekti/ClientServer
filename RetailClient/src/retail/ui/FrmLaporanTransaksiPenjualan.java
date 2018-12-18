/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retail.ui;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import retail.config.Config;
import retail.dao.api.IBeliDao;
import retail.dao.api.IItemBeliDao;
import retail.dao.api.IItemJualDao;
import retail.dao.api.IJualDao;
import retail.model.Beli;
import retail.model.ItemBeli;
import retail.model.ItemJual;
import retail.model.Jual;

/**
 *
 * @author su
 */
public class FrmLaporanTransaksiPenjualan extends javax.swing.JInternalFrame {

    private final Object[] transaksiColumnNames = {
        "No","Nota","Tanggal"
    };
    private DefaultTableModel tableModelTransaksi = new DefaultTableModel();
    private List<Jual> recordTransaksi = new ArrayList<> ();
    private IJualDao transaksiDao = null;
    
    private final Object[] itemColumnNames = {
        "No","Kode Barang","Barang","Jumlah","Total"
    };
    private DefaultTableModel tableModelItem = new DefaultTableModel();
    private List<ItemJual> recordItem = new ArrayList<> ();
    private IItemJualDao itemDao = null;
    /**
     * Creates new form FrmLaporanTransaksiPenjualan
     */
    public FrmLaporanTransaksiPenjualan() {
        initComponents();
        initTableItem();
        initTableTransaksi();
        initBeliDao();
        initItemBeliDao();
        loadTransaksi();
    }

        // method untuk mengambil referensi remote objek
    private void initBeliDao() {
        String url = "rmi://"+ Config.ip_server +":1099/jualDao";
        try {
            // ambil referensi dari remote object yg ada di server
            transaksiDao = (IJualDao) Naming.lookup(url);
        } catch (NotBoundException ex) {
            Logger.getLogger(FrmBarang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(FrmBarang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            //Logger.getLogger(FrmBarang.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Koneksi ke Service "+url+" [GAGAL], cek kembali app server",
            "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // method untuk mengambil referensi remote objek
    private void initItemBeliDao() {
        String url = "rmi://"+ Config.ip_server +":1099/itemJualDao";
        try {
            // ambil referensi dari remote object yg ada di server
            itemDao = (IItemJualDao) Naming.lookup(url);
        } catch (NotBoundException ex) {
            Logger.getLogger(FrmBarang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(FrmBarang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(FrmBarang.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Koneksi ke Service "+url+" [GAGAL], cek kembali app server",
            "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // method untuk mengeset nilai awal objek JTable pada tabel item transaksi pembelian
    private void initTableItem() {
        // set header table
        tableModelItem.setColumnIdentifiers(itemColumnNames);
        tblItem.setModel(tableModelItem);
        tblItem.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    // method untuk mengeset nilai awal objek JTable pada tabel item transaksi pembelian
    private void initTableTransaksi() {
        // set header table
        tableModelTransaksi.setColumnIdentifiers(transaksiColumnNames);
        tblTransaksi.setModel(tableModelTransaksi);
        tblTransaksi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    // method untuk menampilkan semua data barang
    private void loadTransaksi() {
    try {
            // reset data di tabel
            tableModelTransaksi = (DefaultTableModel) tblTransaksi.getModel();
            tableModelTransaksi.setRowCount(0);
            // mengambil data barang dari server
            // kemudian menyimpannya ke objek list
            recordTransaksi = transaksiDao.getAll();
            // ekstrak data barang yg ada di dalam objek list
            // kemudian menampilkannya ke objek tabel
            for (Jual t : recordTransaksi) {
                // ambil nomor urut terakhir
                int noUrut = tableModelTransaksi.getRowCount() + 1;
                Object[] objects = new Object[transaksiColumnNames.length];
                objects[0] = noUrut;
                objects[1] = t.getNotaJual();
                objects[2] = t.getTanggal();
                    // tambahkan data trnasaksi ke dalam tabel
                tableModelTransaksi.addRow(objects);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FrmLaporanTransaksiPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // method untuk menampilkan semua data barang
    private void loadItem(String id) {
    try {
            // reset data di tabel
            tableModelItem = (DefaultTableModel) tblItem.getModel();
            tableModelItem.setRowCount(0);
            // mengambil data barang dari server
            // kemudian menyimpannya ke objek list
            recordItem = itemDao.getByIdBeli(id);
            // ekstrak data barang yg ada di dalam objek list
            // kemudian menampilkannya ke objek tabel
            for (ItemJual t : recordItem) {
                // ambil nomor urut terakhir
                int noUrut = tableModelItem.getRowCount() + 1;
                Object[] objects = new Object[itemColumnNames.length];
                objects[0] = noUrut;
                objects[1] = t.getBarang().getKodeBarang();
                objects[2] = t.getBarang().getNamaBarang();
                objects[3] = t.getJumlah();
                objects[4] = t.getHargaBeli();
                    // tambahkan data barang ke dalam tabel
                tableModelItem.addRow(objects);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FrmLaporanTransaksiPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblTransaksi = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblItem = new javax.swing.JTable();

        tblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTransaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTransaksi);

        tblItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblItem);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTransaksiMouseClicked
        // TODO add your handling code here:
        int row = tblTransaksi.getSelectedRow();
        loadItem(tblTransaksi.getValueAt(row, 1).toString());
    }//GEN-LAST:event_tblTransaksiMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblItem;
    private javax.swing.JTable tblTransaksi;
    // End of variables declaration//GEN-END:variables
}
