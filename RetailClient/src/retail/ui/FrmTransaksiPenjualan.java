/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retail.ui;

import java.beans.PropertyVetoException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import retail.config.Config;
import retail.dao.api.IBarangDao;
import retail.dao.api.IItemJualDao;
import retail.dao.api.IJualDao;
import retail.model.Barang;
import retail.model.ItemJual;
import retail.model.Jual;

/**
 *
 * @author su
 */
public class FrmTransaksiPenjualan extends javax.swing.JInternalFrame {
    private final Object[] barangColumnNames = {
        "No","Kode","Name",
        "Harga Beli","Harga Jual","Stok"
    };
    private final DefaultTableModel tableModelBarang = new DefaultTableModel();
    private List<Barang> recordBarang = new ArrayList<Barang> ();
    private IBarangDao barangDao = null;
    
    
    private final Object[] itemColumnNames = {
        "No","Kode","Nama","Banyaknya","Harga Beli",
        "Harga Jual","Sub Total"};
    private final DefaultTableModel tableModelItem = new DefaultTableModel();
    private List<ItemJual> recordItem = new ArrayList<ItemJual> ();
    
    private int totalHarga =0;
    private int harga_beli =0;
    private int harga_jual =0;
    
    private IJualDao jualDao = null;
    private IItemJualDao itemJualDao = null;
    /**
     * Creates new form FrmTransaksiPenjualan
     */
    public FrmTransaksiPenjualan() {
        initComponents();
        initTableBarang();
        initBarangDao();
        loadBarang();
        initTableItem();
        initJualDao();
        initItemJualDao();
    }
    private void initTableBarang() {
            // set header table
            tableModelBarang.setColumnIdentifiers(barangColumnNames);
            tblBarang.setModel(tableModelBarang);
            tblBarang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        // method untuk mengambil referensi remote objek
    private void initBarangDao() {
        String url = "rmi://"+ Config.ip_server +":1099/brgDao";
        try {
            // ambil referensi dari remote object yg ada di server
            barangDao = (IBarangDao) Naming.lookup(url);
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
    
    // method untuk menampilkan semua data barang
    private void loadBarang() {
    try {
            // reset data di tabel
            tableModelBarang.setRowCount(0);
            // mengambil data barang dari server
            // kemudian menyimpannya ke objek list
            recordBarang = barangDao.getAll();
            // ekstrak data barang yg ada di dalam objek list
            // kemudian menampilkannya ke objek tabel
            for (Barang barang : recordBarang) {
                // ambil nomor urut terakhir
                int noUrut = tableModelBarang.getRowCount() + 1;
                Object[] objects = new Object[barangColumnNames.length];
                objects[0] = noUrut;
                objects[1] = barang.getKodeBarang();
                objects[2] = barang.getNamaBarang();
                objects[3] = barang.getHargaBeli();
                objects[4] = barang.getHargaJual();
                objects[5] = barang.getStok();
                    // tambahkan data barang ke dalam tabel
                tableModelBarang.addRow(objects);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FrmBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// method untuk mengeset nilai awal objek JTable pada tabel item transaksi pembelian
    private void initTableItem() {
        // set header table
        tableModelItem.setColumnIdentifiers(itemColumnNames);
        tblItem.setModel(tableModelItem);
        tblItem.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
        // method untuk mengambil referensi remote objek
    private void initJualDao() {
        String url = "rmi://"+ Config.ip_server +":1099/jualDao";
        try {
            // ambil referensi dari remote object yg ada di server
            jualDao = (IJualDao) Naming.lookup(url);
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
    private void initItemJualDao() {
        String url = "rmi://"+ Config.ip_server +":1099/itemJualDao";
        try {
            // ambil referensi dari remote object yg ada di server
            itemJualDao = (IItemJualDao) Naming.lookup(url);
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
    
    private void resetForm(){
        totalHarga = 0;
        lblTotalHarga.setText(convertRupiah(totalHarga));
        txtKodeBarang.setText("");
        txtNamaBarang.setText("");
        txtHargaBeli.setText("");
        txtBanyaknya.setText("");
        tableModelItem.setRowCount(0);
    }
    
    public String convertRupiah (int harga) {
        DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("");
        dfs.setMonetaryDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        String hsl = "Rp." + df.format(harga);
        return hsl;
    }
    
    public int getRowByValue(TableModel model, Object value) {
        for (int i = model.getRowCount() - 1; i >= 0; --i) {
            for (int j = model.getColumnCount() - 1; j >= 0; --j) {
                if (model.getValueAt(i, j).equals(value)) {
                    // what if value is not unique?
                    return i;
                }
            }
        }
        return -1;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frmCariBarang = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblBarang = new javax.swing.JTable();
        btnPilihDBarang = new javax.swing.JButton();
        btnTutupDBarang = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItem = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnCariKodeBarang = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtKodeBarang = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNamaBarang = new javax.swing.JTextField();
        txtHargaBeli = new javax.swing.JTextField();
        txtBanyaknya = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnBatal = new javax.swing.JButton();
        btnProses = new javax.swing.JButton();
        btnTutup = new javax.swing.JButton();
        lblTotalHarga = new javax.swing.JLabel();

        tblBarang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblBarang);

        btnPilihDBarang.setText("Pilih");
        btnPilihDBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihDBarangActionPerformed(evt);
            }
        });

        btnTutupDBarang.setText("Tutup");
        btnTutupDBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTutupDBarangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frmCariBarangLayout = new javax.swing.GroupLayout(frmCariBarang.getContentPane());
        frmCariBarang.getContentPane().setLayout(frmCariBarangLayout);
        frmCariBarangLayout.setHorizontalGroup(
            frmCariBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmCariBarangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmCariBarangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPilihDBarang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTutupDBarang)
                .addContainerGap())
        );
        frmCariBarangLayout.setVerticalGroup(
            frmCariBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmCariBarangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frmCariBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPilihDBarang)
                    .addComponent(btnTutupDBarang))
                .addContainerGap())
        );

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
        jScrollPane1.setViewportView(tblItem);

        btnCariKodeBarang.setText("Cari");
        btnCariKodeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariKodeBarangActionPerformed(evt);
            }
        });

        jLabel5.setText("KODE");

        jLabel6.setText("NAMA BARANG");

        jLabel7.setText("HARGA BELI");

        jLabel8.setText("BANYAK");

        txtBanyaknya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBanyaknyaActionPerformed(evt);
            }
        });
        txtBanyaknya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBanyaknyaKeyTyped(evt);
            }
        });

        jLabel9.setText("(enter)");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnCariKodeBarang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel7)
                        .addGap(51, 51, 51)
                        .addComponent(jLabel8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtBanyaknya, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCariKodeBarang)
                    .addComponent(txtKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBanyaknya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnBatal.setText("BATALKAN ITEM");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnProses.setText("PROSES");
        btnProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProsesActionPerformed(evt);
            }
        });

        btnTutup.setText("TUTUP");
        btnTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTutupActionPerformed(evt);
            }
        });

        lblTotalHarga.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTotalHarga.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnBatal)
                                .addGap(1, 1, 1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnProses)
                                .addGap(18, 18, 18)
                                .addComponent(btnTutup))))
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotalHarga)
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBatal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProses)
                    .addComponent(btnTutup))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPilihDBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihDBarangActionPerformed
        // TODO add your handling code here:
        int row = tblBarang.getSelectedRow();
        Barang b = recordBarang.get(row);

        txtKodeBarang.setText(b.getKodeBarang());
        txtNamaBarang.setText(b.getNamaBarang());
        txtHargaBeli.setText(Integer.toString(b.getHargaBeli()));

        harga_beli = b.getHargaBeli();
        harga_jual = b.getHargaJual();

        frmCariBarang.setVisible(false);
        txtBanyaknya.setText("");
        txtBanyaknya.requestFocus();
        txtBanyaknya.selectAll();
    }//GEN-LAST:event_btnPilihDBarangActionPerformed

    private void btnTutupDBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupDBarangActionPerformed
        // TODO add your handling code here:
        frmCariBarang.setVisible(false);
    }//GEN-LAST:event_btnTutupDBarangActionPerformed

    private void btnCariKodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariKodeBarangActionPerformed
        // TODO add your handling code here:
        frmCariBarang.pack();
        frmCariBarang.setLocationRelativeTo(this);
        frmCariBarang.setModal(true);
        frmCariBarang.setVisible(true);
    }//GEN-LAST:event_btnCariKodeBarangActionPerformed

    private void txtBanyaknyaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBanyaknyaActionPerformed
        // TODO add your handling code here:
        String kodeBarang = txtKodeBarang.getText();
        String namaBarang = txtNamaBarang.getText();
        int banyaknya = Integer.parseInt(txtBanyaknya.getText());
        int sub_total = harga_jual * banyaknya;
        totalHarga = totalHarga + sub_total;
        lblTotalHarga.setText(convertRupiah(totalHarga));

        // pencarian jika ada barang yang sama
        int row = getRowByValue(tableModelItem,kodeBarang);

        if(row>=0){
            // update data item yg ada di tabel
            banyaknya = banyaknya + Integer.parseInt(tableModelItem.getValueAt(row, 3).toString());
            //revisi sub total harga
            sub_total = harga_jual * banyaknya;
            //ubah data di Jtable
            tableModelItem.setValueAt(banyaknya, row, 3);
            tableModelItem.setValueAt(sub_total, row, 6);
        }
        else{
            // ambil nomor urut terakhir
            int noUrut = tableModelItem.getRowCount() + 1;
            Object[] objects = new Object[itemColumnNames.length];
            objects[0] = noUrut;
            objects[1] = kodeBarang;
            objects[2] = namaBarang;
            objects[3] = banyaknya;
            objects[4] = harga_beli;
            objects[5] = harga_jual;
            objects[6] = sub_total;
            // tambahkan data item ke dalam tabel
            tableModelItem.addRow(objects);
        }
    }//GEN-LAST:event_txtBanyaknyaActionPerformed

    private void txtBanyaknyaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBanyaknyaKeyTyped
        // TODO add your handling code here:
        try{
            Integer.valueOf(evt.getKeyChar());
        }catch(NumberFormatException e){
            evt.consume();
            getToolkit().beep();
        }
    }//GEN-LAST:event_txtBanyaknyaKeyTyped

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        // ambil nilai record yang dipilih
        int row = tblItem.getSelectedRow();
        if (row < 0) { // data barang belum dipilih
            JOptionPane.showMessageDialog(this, "Data item transaksi belum dipilih",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            // ambil data barang yang dipilih
            int result = JOptionPane.showConfirmDialog(this, "Apakah data barang '" + tableModelItem.getValueAt(row, 2).toString() +"'\nIngin dihapus ?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_NO_OPTION) {
                // ganti total harga
                totalHarga = totalHarga - Integer.parseInt(tableModelItem.getValueAt(row, 6).toString()) ;
                lblTotalHarga.setText(convertRupiah(totalHarga));
                // harus data barang data objek JTable
                tableModelItem.removeRow(row);
            }
        }
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProsesActionPerformed
        // TODO add your handling code here:
        java.util.Date utilDate = new java.util.Date();
        java.sql.Timestamp sqlDate = new java.sql.Timestamp (utilDate.getTime());
        //generate nomor nota beli dari tanggal dan jam
        String no_nota_jual = sqlDate.toString();
        no_nota_jual = no_nota_jual.replaceAll("-", "");
        no_nota_jual = no_nota_jual.replaceAll(" ", "");
        no_nota_jual = no_nota_jual.replaceAll(":", "");
        no_nota_jual = no_nota_jual.replaceAll("\\.", "");
        Jual jual = new Jual();
        jual.setNotaJual(no_nota_jual);
        jual.setTanggal(sqlDate);
        try {
            int hasil = jualDao.save(jual);
            if (hasil > 0) {
                int rowCount = tblItem.getRowCount();
                int column = tblItem.getColumnCount();
                for (int row = 0; row < rowCount; row++) {
                    Barang barang = new Barang();
                    barang.setKodeBarang(tblItem.getValueAt(row, 1).toString());
                    barang.setNamaBarang(tblItem.getValueAt(row, 2).toString());
                    ItemJual itemJual = new ItemJual();
                    itemJual.setBarang(barang);
                    itemJual.setJual(jual);
                    itemJual.setJumlah(Integer.parseInt(tblItem.getValueAt(row, 3).toString()));
                    itemJual.setHargaBeli(Integer.parseInt(tblItem.getValueAt(row, 4).toString()));
                    itemJual.setHargaJual(Integer.parseInt(tblItem.getValueAt(row, 5).toString()));
                    itemJualDao.save(itemJual);
                }
                resetForm();
                // tampilkan pesan berhasil
                JOptionPane.showMessageDialog(this, "Data Transaksi Penjualan berhasil disimpan",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FrmTransaksiPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnProsesActionPerformed

    private void btnTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupActionPerformed
        try {
            // TODO add your handling code here:
            this.setClosed(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrmTransaksiPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTutupActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCariKodeBarang;
    private javax.swing.JButton btnPilihDBarang;
    private javax.swing.JButton btnProses;
    private javax.swing.JButton btnTutup;
    private javax.swing.JButton btnTutupDBarang;
    private javax.swing.JDialog frmCariBarang;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblTotalHarga;
    private javax.swing.JTable tblBarang;
    private javax.swing.JTable tblItem;
    private javax.swing.JTextField txtBanyaknya;
    private javax.swing.JTextField txtHargaBeli;
    private javax.swing.JTextField txtKodeBarang;
    private javax.swing.JTextField txtNamaBarang;
    // End of variables declaration//GEN-END:variables
}
