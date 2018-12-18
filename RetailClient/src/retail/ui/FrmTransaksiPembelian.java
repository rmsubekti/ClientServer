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
import retail.dao.api.IBeliDao;
import retail.dao.api.IItemBeliDao;
import retail.dao.api.ISupplierDao;
import retail.model.Barang;
import retail.model.Beli;
import retail.model.ItemBeli;
import retail.model.Supplier;

/**
 *
 * @author Amikom
 */
public class FrmTransaksiPembelian extends javax.swing.JInternalFrame {

    private final Object[] supplierColumnNames = {
        "No","Kode","Name","Alamat"
    };
    private final DefaultTableModel tableModelSupplier = new DefaultTableModel();
    private List<Supplier> recordSupplier = new ArrayList<Supplier> ();
    private ISupplierDao supplierDao = null;
    
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
    private List<ItemBeli> recordItem = new ArrayList<ItemBeli> ();
    
    private int totalHarga =0;
    private int harga_beli =0;
    private int harga_jual =0;
    
    private IBeliDao beliDao = null;
    private IItemBeliDao itemBeliDao = null;
    /**
     * Creates new form FrmTransaksi
     */
    public FrmTransaksiPembelian() {
        initComponents();
        initTableSupplier();
        initSupplierDao();
        loadSupplier();
        initTableBarang();
        initBarangDao();
        loadBarang();
        initTableItem();
        initBeliDao();
        initItemBeliDao();
        
    }
    
    private void initTableSupplier() {
        tableModelSupplier.setColumnIdentifiers(supplierColumnNames);
        tblSupplier.setModel(tableModelSupplier);
        tblSupplier.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initSupplierDao(){
        String url = "rmi://"+ Config.ip_server + ":1099/supplierDao";
        try {
            supplierDao = (ISupplierDao) Naming.lookup(url);
        } catch (NotBoundException ex) {
            Logger.getLogger(FrmBarang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(FrmBarang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null,"Koneksi ke service "+url+"[Gagal]: cek kembali aplikasi server", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void loadSupplier() {
        try {
            // reset data di tabel
            tableModelSupplier.setRowCount(0);
            // mengambil data supplier dari server
            // kemudian menyimpannya ke objek list
            recordSupplier = supplierDao.getAll();
            // ekstrak data supplier yg ada di dalam objek list
            // kemudian menampilkannya ke objek tabel
            for (Supplier supplier : recordSupplier) {
                // ambil nomor urut terakhir
                int noUrut = tableModelSupplier.getRowCount() + 1;
                Object[] objects = new Object[supplierColumnNames.length];
                objects[0] = noUrut;
                objects[1] = supplier.getKodeSupplier();
                objects[2] = supplier.getNamaSupplier();
                objects[3] = supplier.getAlamat();
                // tambahkan data supplier ke dalam tabel
                tableModelSupplier.addRow(objects);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FrmSupplier.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    }
    
    // method untuk mengeset nilai awal objek JTable pada tabel item transaksi pembelian
    private void initTableItem() {
        // set header table
        tableModelItem.setColumnIdentifiers(itemColumnNames);
        tblItem.setModel(tableModelItem);
        tblItem.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
        // method untuk mengambil referensi remote objek
    private void initBeliDao() {
        String url = "rmi://"+ Config.ip_server +":1099/beliDao";
        try {
            // ambil referensi dari remote object yg ada di server
            beliDao = (IBeliDao) Naming.lookup(url);
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
        String url = "rmi://"+ Config.ip_server +":1099/itemBeliDao";
        try {
            // ambil referensi dari remote object yg ada di server
            itemBeliDao = (IItemBeliDao) Naming.lookup(url);
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
        txtKodeSupplier.setText("");
        txtKodeSupplier.requestFocus();
        lblNamaSupplier.setText("[KODE SUPPLIER BELUM DI PILIH]");
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

        frmCariSupplier = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSupplier = new javax.swing.JTable();
        btnPilihDSupplier = new javax.swing.JButton();
        btnTutupDSupplier = new javax.swing.JButton();
        frmCariBarang = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblBarang = new javax.swing.JTable();
        btnPilihDBarang = new javax.swing.JButton();
        btnTutupDBarang = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtKodeSupplier = new javax.swing.JTextField();
        btnCariSupplier = new javax.swing.JButton();
        lblNamaSupplier = new javax.swing.JLabel();
        lblTotalHarga = new javax.swing.JLabel();
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

        tblSupplier.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblSupplier);

        btnPilihDSupplier.setText("Pilih");
        btnPilihDSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihDSupplierActionPerformed(evt);
            }
        });

        btnTutupDSupplier.setText("Tutup");
        btnTutupDSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTutupDSupplierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frmCariSupplierLayout = new javax.swing.GroupLayout(frmCariSupplier.getContentPane());
        frmCariSupplier.getContentPane().setLayout(frmCariSupplierLayout);
        frmCariSupplierLayout.setHorizontalGroup(
            frmCariSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmCariSupplierLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmCariSupplierLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPilihDSupplier)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTutupDSupplier)
                .addContainerGap())
        );
        frmCariSupplierLayout.setVerticalGroup(
            frmCariSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmCariSupplierLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frmCariSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPilihDSupplier)
                    .addComponent(btnTutupDSupplier))
                .addContainerGap())
        );

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

        setClosable(true);

        jLabel1.setText("TANGGAL");

        jLabel2.setText("KODE SUPPLIER");

        jLabel3.setText("NAMA SUPPLIER");

        jLabel4.setText("12 OKTOBER 2016");

        btnCariSupplier.setText("Cari Supplier");
        btnCariSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariSupplierActionPerformed(evt);
            }
        });

        lblNamaSupplier.setText("[KODE SUPPLIER BELUM DIPILIH]");

        lblTotalHarga.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTotalHarga.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(lblNamaSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtKodeSupplier)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCariSupplier)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotalHarga)
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtKodeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCariSupplier)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblNamaSupplier))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(53, Short.MAX_VALUE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnProses)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTutup)
                        .addGap(24, 24, 24))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBatal)
                .addGap(37, 37, 37))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBatal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProses)
                    .addComponent(btnTutup))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariSupplierActionPerformed
        // TODO add your handling code here:
        frmCariSupplier.pack();
        frmCariSupplier.setLocationRelativeTo(this);
        frmCariSupplier.setModal(true);
        frmCariSupplier.setVisible(true);
        
    }//GEN-LAST:event_btnCariSupplierActionPerformed

    private void btnCariKodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariKodeBarangActionPerformed
        // TODO add your handling code here:
        
        frmCariBarang.pack();
        frmCariBarang.setLocationRelativeTo(this);
        frmCariBarang.setModal(true);
        frmCariBarang.setVisible(true);
    }//GEN-LAST:event_btnCariKodeBarangActionPerformed

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
        String no_nota_beli = sqlDate.toString();
        no_nota_beli = no_nota_beli.replaceAll("-", "");
        no_nota_beli = no_nota_beli.replaceAll(" ", "");
        no_nota_beli = no_nota_beli.replaceAll(":", "");
        no_nota_beli = no_nota_beli.replaceAll("\\.", "");
        Beli beli = new Beli();
        beli.setNotaBeli(no_nota_beli);
        beli.setTanggal(sqlDate);
        Supplier supplier = new Supplier();
        supplier.setKodeSupplier(txtKodeSupplier.getText());
        supplier.setNamaSupplier(lblNamaSupplier.getText());
        beli.setSupplier(supplier);
        try {
            int hasil = beliDao.save(beli);
            if (hasil > 0) {
                int rowCount = tblItem.getRowCount();
                int column = tblItem.getColumnCount();
                for (int row = 0; row < rowCount; row++) {
                    Barang barang = new Barang();
                    barang.setKodeBarang(tblItem.getValueAt(row, 1).toString());
                    barang.setNamaBarang(tblItem.getValueAt(row, 2).toString());
                    ItemBeli itemBeli = new ItemBeli();
                    itemBeli.setBarang(barang);
                    itemBeli.setBeli(beli);
                    itemBeli.setJumlah(Integer.parseInt(tblItem.getValueAt(row, 3).toString()));
                    itemBeli.setHargaBeli(Integer.parseInt(tblItem.getValueAt(row, 4).toString()));
                    itemBeli.setHargaJual(Integer.parseInt(tblItem.getValueAt(row, 5).toString()));
                    itemBeliDao.save(itemBeli);
                }
                resetForm();
                // tampilkan pesan berhasil
                JOptionPane.showMessageDialog(this, "Data Transaksi Pembelian berhasil disimpan",
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

    private void txtBanyaknyaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBanyaknyaActionPerformed
        // TODO add your handling code here:
        String kodeBarang = txtKodeBarang.getText();
        String namaBarang = txtNamaBarang.getText();
        int banyaknya = Integer.parseInt(txtBanyaknya.getText());
        int sub_total = harga_beli * banyaknya;
        totalHarga = totalHarga + sub_total;
        lblTotalHarga.setText(convertRupiah(totalHarga));
        
        // pencarian jika ada barang yang sama
        int row = getRowByValue(tableModelItem,kodeBarang);
        
        if(row>=0){
            // update data item yg ada di tabel
            banyaknya = banyaknya + Integer.parseInt(tableModelItem.getValueAt(row, 3).toString());
            //revisi sub total harga
            sub_total = harga_beli * banyaknya;
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

    private void btnPilihDSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihDSupplierActionPerformed
        // TODO add your handling code here:
        int row = tblSupplier.getSelectedRow();
        Supplier supplier = recordSupplier.get(row);
        
        txtKodeSupplier.setText(supplier.getKodeSupplier());
        lblNamaSupplier.setText(supplier.getNamaSupplier());
        frmCariSupplier.setVisible(false);
        txtKodeBarang.requestFocus();
        txtKodeBarang.selectAll();
    }//GEN-LAST:event_btnPilihDSupplierActionPerformed

    private void btnTutupDSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupDSupplierActionPerformed
        // TODO add your handling code here:
        frmCariSupplier.setVisible(false);
    }//GEN-LAST:event_btnTutupDSupplierActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCariKodeBarang;
    private javax.swing.JButton btnCariSupplier;
    private javax.swing.JButton btnPilihDBarang;
    private javax.swing.JButton btnPilihDSupplier;
    private javax.swing.JButton btnProses;
    private javax.swing.JButton btnTutup;
    private javax.swing.JButton btnTutupDBarang;
    private javax.swing.JButton btnTutupDSupplier;
    private javax.swing.JDialog frmCariBarang;
    private javax.swing.JDialog frmCariSupplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblNamaSupplier;
    private javax.swing.JLabel lblTotalHarga;
    private javax.swing.JTable tblBarang;
    private javax.swing.JTable tblItem;
    private javax.swing.JTable tblSupplier;
    private javax.swing.JTextField txtBanyaknya;
    private javax.swing.JTextField txtHargaBeli;
    private javax.swing.JTextField txtKodeBarang;
    private javax.swing.JTextField txtKodeSupplier;
    private javax.swing.JTextField txtNamaBarang;
    // End of variables declaration//GEN-END:variables
}
