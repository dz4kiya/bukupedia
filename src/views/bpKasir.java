/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import inc.config;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Dzakiya
 */
public class bpKasir extends javax.swing.JFrame {

    Connection conn = config.Conn();
    Statement st;
    ResultSet rs;
    public static int id;

    private void getJumlah(int jumlahTB) {
        return;
    }

    private void tampilSupp() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("No Telp");

        try {
            String sql = "SELECT * FROM SUPPLIER";
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("ID_SUPPLIER"), rs.getString("NAMA_SUPPLIER"), rs.getString("ALAMAT_SUPPLIER"), rs.getString("NO_TELP_SUPPLIER")

                });
            }
            tbSupp.setModel(model);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void tampilCust() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("No Telp");

        try {
            String sql = "SELECT * FROM PELANGGAN";
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("ID_PELANGGAN"), rs.getString("NAMA_PELANGGAN"), rs.getString("ALAMAT_PELANGGAN"), rs.getString("NO_TELP_PELANGGAN")

                });
            }
            tbCust.setModel(model);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void tampilBuku() {

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Tanggal Masuk");
        model.addColumn("ID BUKU");
        model.addColumn("ISBN");
        model.addColumn("Judul");
        model.addColumn("Penulis");
        model.addColumn("Stok");
        model.addColumn("Harga");
        model.addColumn("Kategori");
        model.addColumn("Penerbit");
        model.addColumn("Tahun Terbit");
        model.addColumn("Rak");

        try {
            String sql = "SELECT * FROM BUKU";
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("TANGGAL_MASUK"), rs.getString("ID_BUKU"), rs.getString("ISBN"), rs.getString("JUDUL"), rs.getString("PENULIS"), rs.getString("STOK"), rs.getString("HARGA"), rs.getString("KATEGORI"), rs.getString("PENERBIT"), rs.getString("TAHUN_TERBIT"), rs.getString("RAK")

                });
            }
            tbBuku.setModel(model);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void tampilpenjualan() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Penjualan");
        model.addColumn("Tgl Penjualan");
        model.addColumn("ID Buku");
        model.addColumn("ID Kasir");
        model.addColumn("ID Pelanggan");
        model.addColumn("Jumlah");
        model.addColumn("Harga");
        model.addColumn("Total Harga");

        try {
            String sql = "SELECT * FROM TRANSAKSI_PENJUALAN";
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("ID_PENJUALAN"), rs.getString("TGL_PENJUALAN"), rs.getString("ID_BUKU"), rs.getString("ID_KASIR"), rs.getString("ID_PELANGGAN"), rs.getString("JUMLAH"), rs.getString("HARGA"), rs.getString("TOTAL")

                });
            }
            tbPenjualan.setModel(model);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void Total() {
        int jumlahBaris = tbPenjualan.getRowCount();
        int jlhpinjam = 0;
        int jumlahtotalpinjam;

        TableModel tabelmodel;
        tabelmodel = tbPenjualan.getModel();
        for (int i = 0; i < jumlahBaris; i++) {
            jumlahtotalpinjam = Integer.parseInt(tabelmodel.getValueAt(i, 7).toString());
            jlhpinjam = jumlahtotalpinjam + jlhpinjam;
        }
        totall.setText(String.valueOf(jlhpinjam));
        tfTotal.setText(String.valueOf(totall.getText()));

    }

    private void simpandetail() {
        int jumlah_baris = tbPenjualan.getRowCount();
        if (jumlah_baris == 0) {
        } else {
            try {

                int i = 0;
                while (i < jumlah_baris) {

                    String idpenjualan = tbPenjualan.getValueAt(i, 0).toString();
                    String tanggalpenjualan = tbPenjualan.getValueAt(i, 1).toString();
                    String idbuku = tbPenjualan.getValueAt(i, 2).toString();
                    String idkasir = tbPenjualan.getValueAt(i, 3).toString();
                    String idpelanggan = tbPenjualan.getValueAt(i, 4).toString();
                    String jumlah = tbPenjualan.getValueAt(i, 5).toString();
                    String harga = tbPenjualan.getValueAt(i, 6).toString();
                    String total = tbPenjualan.getValueAt(i, 7).toString();

                    String sql = "INSERT INTO TRANSAKSI_PENJUALAN (ID_PENJUALAN,TGL_PENJUALAN,ID_BUKU,ID_KASIR,ID_PELANGGAN,JUMLAH,HARGA,TOTAL) VALUES ('" + idpenjualan + "', '" + tanggalpenjualan + "','" + idbuku + "', '" + idkasir + "', '" + idpelanggan + "', '" + jumlah + "', '" + harga + "','" + total + "')";
                    java.sql.PreparedStatement stm = conn.prepareStatement(sql);
                    stm.execute();

                    i++;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan..!!" + e);
            }
        }
    }

    public void prosestambah() {
        try {

            DefaultTableModel tableModel = (DefaultTableModel) tbPenjualan.getModel();
            String[] data = new String[8];
            data[0] = tfIDPenjualan.getText();
            data[1] = tfTanggalJual.getText();
            data[2] = tfIDbuku.getText();
            data[3] = tfIDKasir.getText();
            data[4] = tfIDPelanggan.getText();
            data[5] = tfJumlah.getText();
            data[6] = tfharga.getText();
            data[7] = tftotal.getText();
            tableModel.addRow(data);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error masukkan data \n" + e.getMessage());
        }
    }

    private void tampilPembelian() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Pembelian");
        model.addColumn("Tanggal");
        model.addColumn("ID Supplier");
        model.addColumn("ID Buku");
        model.addColumn("Jumlah");
        model.addColumn("Harga");
        model.addColumn("Total");

        try {
            String sql = "SELECT * FROM TRANSAKSI_PEMBELIAN";
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("ID_PEMBELIAN"),
                    rs.getString("TGL_PEMBELIAN"),
                    rs.getString("ID_SUPPLIER"),
                    rs.getString("ID_BUKU"),
                    rs.getString("JUMLAH"),
                    rs.getString("HARGA"),
                    rs.getString("TOTAL")

                });
            }
            tbPembelian.setModel(model);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void tampilTotalPembelian() {
        int jumlahBaris = tbPembelian.getRowCount();
        int jlhpinjam = 0;
        int jumlahtotalpinjam;

        TableModel tabelmodel;
        tabelmodel = tbPembelian.getModel();
        for (int i = 0; i < jumlahBaris; i++) {
            jumlahtotalpinjam = Integer.parseInt(tabelmodel.getValueAt(i, 6).toString());
            jlhpinjam = jumlahtotalpinjam + jlhpinjam;
        }
        ltotall.setText(String.valueOf(jlhpinjam));
        tfTotalTB.setText(String.valueOf(ltotall.getText()));

    }

    private void simpandetailPembelian() {
        int jumlah_baris = tbPembelian.getRowCount();
        if (jumlah_baris == 0) {
        } else {
            try {

                int i = 0;
                while (i < jumlah_baris) {

                    String idpembelian = tbPembelian.getValueAt(i, 0).toString();
                    String tanggal = tbPembelian.getValueAt(i, 1).toString();
                    String idsupplier = tbPembelian.getValueAt(i, 2).toString();
                    String idbuku = tbPembelian.getValueAt(i, 3).toString();
                    String harga = tbPembelian.getValueAt(i, 4).toString();
                    String jumlah = tbPembelian.getValueAt(i, 5).toString();
                    String total = tbPembelian.getValueAt(i, 6).toString();

                    String sql = "INSERT INTO TRANSAKSI_PEMBELIAN (ID_PEMBELIAN,TGL_PEMBELIAN,ID_SUPPLIER,ID_BUKU,HARGA,JUMLAH,TOTAL) VALUES ('" + idpembelian + "', '" + tanggal + "','" + idsupplier + "', '" + idbuku + "', '" + harga + "', '" + jumlah + "', '" + total + "')";
                    java.sql.PreparedStatement stm = conn.prepareStatement(sql);
                    stm.execute();

                    i++;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Pembayaran Gagal!!" + e);
            }
        }
    }

    public void prosestambahPembelian() {
        try {

            DefaultTableModel tableModel = (DefaultTableModel) tbPembelian.getModel();
            String[] data = new String[8];
            data[0] = tfIDPembelian.getText();
            data[1] = tfTanggalPembelian.getText();
            data[2] = tfIDSupplierPembelian.getText();
            data[3] = tfIDBukuPembelian.getText();
            data[4] = tfHargaPembelian.getText();
            data[5] = tfJumlahPembelian.getText();
            data[6] = tfTotalPembelian.getText();
            tableModel.addRow(data);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error masukkan data \n" + e.getMessage());
        }
    }

    private void clear() {
        tfIDSupp.setText("");
        tfNamaSupp.setText("");
        tfAlamatSupp.setText("");
        tfNoSupp.setText("");
        tfIDCust.setText("");
        tfNamaCust.setText("");
        tfAlamatCust.setText("");
        tfNoCust.setText("");
        tfIDBuku.setText("");
        tfPenulis.setText("");
        tfPenerbit.setText("");
        tfKategori.setText("");
        tfISBN.setText("");
        tfJudulBuku.setText("");
        tfStok.setText("");
        tfRak.setText("");
        tfHarga.setText("");
        tfTanggal.setText("");
        tfTahun.setText("");
        tfIDPembelian.setText("");
        tfTanggalPembelian.setText("");
        tfIDBukuPembelian.setText("");
        tfIDSupplierPembelian.setText("");
        tfHargaPembelian.setText("");
        tfJumlahPembelian.setText("");
        tfTotalPembelian.setText("");
        tfIDPenjualan.setText("");
        tfTanggalJual.setText("");
        tfIDbuku.setText("");
        tfIDKasir.setText("");
        tfIDPelanggan.setText("");
        tfJumlah.setText("");
        tfharga.setText("");
        tftotal.setText("");

    }

    public bpKasir() {
        initComponents();
        ID.setText(String.valueOf(bpLogin.getID()));
        tampilBuku();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        HEAD = new javax.swing.JPanel();
        head = new javax.swing.JLabel();
        ID = new javax.swing.JLabel();
        LEFT = new javax.swing.JPanel();
        btnBuku = new javax.swing.JButton();
        btnCust = new javax.swing.JButton();
        btnSupp = new javax.swing.JButton();
        btnJual = new javax.swing.JButton();
        btnBeli = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        logout = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        panelBuku = new javax.swing.JPanel();
        lBuku = new javax.swing.JLabel();
        lIDBuku = new javax.swing.JLabel();
        lNamaCust1 = new javax.swing.JLabel();
        lAlamatCust1 = new javax.swing.JLabel();
        tfIDBuku = new javax.swing.JTextField();
        lNoCust1 = new javax.swing.JLabel();
        tfHarga = new javax.swing.JTextField();
        btnSimpanBuku = new javax.swing.JButton();
        btnUbahBuku = new javax.swing.JButton();
        btnHapusBuku = new javax.swing.JButton();
        btnResetBuku = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbBuku = new javax.swing.JTable();
        tfSearchBuku = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lNoCust2 = new javax.swing.JLabel();
        lNoCust3 = new javax.swing.JLabel();
        lNoCust4 = new javax.swing.JLabel();
        lIDBuku1 = new javax.swing.JLabel();
        lNoCust5 = new javax.swing.JLabel();
        lNoCust6 = new javax.swing.JLabel();
        tfISBN = new javax.swing.JTextField();
        tfStok = new javax.swing.JTextField();
        tfPenulis = new javax.swing.JTextField();
        tfJudulBuku = new javax.swing.JTextField();
        tfKategori = new javax.swing.JTextField();
        tfPenerbit = new javax.swing.JTextField();
        lNoCust7 = new javax.swing.JLabel();
        tfRak = new javax.swing.JTextField();
        tfTanggal = new javax.swing.JTextField();
        tfTahun = new javax.swing.JTextField();
        panelCust = new javax.swing.JPanel();
        lCust = new javax.swing.JLabel();
        lIDCust = new javax.swing.JLabel();
        lNamaCust = new javax.swing.JLabel();
        lAlamatCust = new javax.swing.JLabel();
        lNoCust = new javax.swing.JLabel();
        tfIDCust = new javax.swing.JTextField();
        tfNamaCust = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tfAlamatCust = new javax.swing.JTextArea();
        tfNoCust = new javax.swing.JTextField();
        btnSimpanCust = new javax.swing.JButton();
        btnUbahCust = new javax.swing.JButton();
        btnHapusCust = new javax.swing.JButton();
        btnResetCust = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbCust = new javax.swing.JTable();
        tfSearchCust = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        panelSupp = new javax.swing.JPanel();
        lSupp = new javax.swing.JLabel();
        lID = new javax.swing.JLabel();
        lNama = new javax.swing.JLabel();
        lAlamat = new javax.swing.JLabel();
        lNo = new javax.swing.JLabel();
        tfIDSupp = new javax.swing.JTextField();
        tfNamaSupp = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tfAlamatSupp = new javax.swing.JTextArea();
        tfNoSupp = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbSupp = new javax.swing.JTable();
        tfSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        panelReport = new javax.swing.JPanel();
        lReport = new javax.swing.JLabel();
        btncetakTJ = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btncetakTB = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        panelTJ = new javax.swing.JPanel();
        lTJ = new javax.swing.JLabel();
        lTJ1 = new javax.swing.JLabel();
        lTJ2 = new javax.swing.JLabel();
        lTJ4 = new javax.swing.JLabel();
        lTJ5 = new javax.swing.JLabel();
        lTJ6 = new javax.swing.JLabel();
        lTJ7 = new javax.swing.JLabel();
        tfIDPelanggan = new javax.swing.JTextField();
        tfIDbuku = new javax.swing.JTextField();
        tfIDKasir = new javax.swing.JTextField();
        tfharga = new javax.swing.JTextField();
        tfJumlah = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbPenjualan = new javax.swing.JTable();
        reset = new javax.swing.JButton();
        tambah = new javax.swing.JButton();
        lTJ8 = new javax.swing.JLabel();
        lTJ9 = new javax.swing.JLabel();
        lTJ10 = new javax.swing.JLabel();
        tfTotal = new javax.swing.JTextField();
        tfBayar = new javax.swing.JTextField();
        tfKembalian = new javax.swing.JTextField();
        tfIDPenjualan = new javax.swing.JTextField();
        tftotal = new javax.swing.JTextField();
        tfTanggalJual = new javax.swing.JTextField();
        hapus = new javax.swing.JButton();
        lTJ11 = new javax.swing.JLabel();
        lTJ12 = new javax.swing.JLabel();
        totall = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        panelTB = new javax.swing.JPanel();
        lTB = new javax.swing.JLabel();
        lIDPembelian = new javax.swing.JLabel();
        lTanggalPembelian = new javax.swing.JLabel();
        lHargaPembelian = new javax.swing.JLabel();
        lJumlahPembelian = new javax.swing.JLabel();
        tfIDPembelian = new javax.swing.JTextField();
        tfTanggalPembelian = new javax.swing.JTextField();
        tfHargaPembelian = new javax.swing.JTextField();
        tfJumlahPembelian = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbPembelian = new javax.swing.JTable();
        btnSimpanPembelian = new javax.swing.JButton();
        btnHapusPembelian = new javax.swing.JButton();
        btnResetPembelian = new javax.swing.JButton();
        btnBayarPembelian = new javax.swing.JButton();
        lTotalTB = new javax.swing.JLabel();
        lTunaiPembelian = new javax.swing.JLabel();
        lKembalianPembelian = new javax.swing.JLabel();
        tfTotalTB = new javax.swing.JTextField();
        tfTunaiPembelian = new javax.swing.JTextField();
        tfKembalianPembelian = new javax.swing.JTextField();
        lIDBukuPembelian = new javax.swing.JLabel();
        lIDSupplierPembelian = new javax.swing.JLabel();
        tfIDBukuPembelian = new javax.swing.JTextField();
        tfTotalPembelian = new javax.swing.JTextField();
        tfIDSupplierPembelian = new javax.swing.JTextField();
        lTotalPembelian = new javax.swing.JLabel();
        ltotall = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BUKUPEDIA Kasir");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        HEAD.setBackground(new java.awt.Color(48, 58, 82));

        head.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        head.setForeground(new java.awt.Color(204, 204, 204));
        head.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        head.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/bpputih50.png"))); // NOI18N
        head.setText("BUKUPEDIA Kasir");

        ID.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        ID.setForeground(new java.awt.Color(204, 204, 204));
        ID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ID.setText("ID");

        javax.swing.GroupLayout HEADLayout = new javax.swing.GroupLayout(HEAD);
        HEAD.setLayout(HEADLayout);
        HEADLayout.setHorizontalGroup(
            HEADLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HEADLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(head, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(194, 194, 194)
                .addComponent(ID, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        HEADLayout.setVerticalGroup(
            HEADLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HEADLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(HEADLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(head)
                    .addComponent(ID))
                .addGap(13, 13, 13))
        );

        LEFT.setBackground(new java.awt.Color(204, 204, 204));
        LEFT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 6, 6, 0, new java.awt.Color(48, 58, 82)));
        LEFT.setLayout(new java.awt.GridLayout(7, 1, 1, 4));

        btnBuku.setBackground(new java.awt.Color(204, 204, 204));
        btnBuku.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnBuku.setForeground(new java.awt.Color(48, 58, 82));
        btnBuku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/buku20.png"))); // NOI18N
        btnBuku.setText("Buku");
        btnBuku.setBorder(null);
        btnBuku.setIconTextGap(10);
        btnBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBukuActionPerformed(evt);
            }
        });
        LEFT.add(btnBuku);

        btnCust.setBackground(new java.awt.Color(204, 204, 204));
        btnCust.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnCust.setForeground(new java.awt.Color(48, 58, 82));
        btnCust.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/cust20.png"))); // NOI18N
        btnCust.setText("Customer");
        btnCust.setBorder(null);
        btnCust.setIconTextGap(10);
        btnCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustActionPerformed(evt);
            }
        });
        LEFT.add(btnCust);

        btnSupp.setBackground(new java.awt.Color(204, 204, 204));
        btnSupp.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnSupp.setForeground(new java.awt.Color(48, 58, 82));
        btnSupp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/supp20.png"))); // NOI18N
        btnSupp.setText("Supplier");
        btnSupp.setBorder(null);
        btnSupp.setIconTextGap(10);
        btnSupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuppActionPerformed(evt);
            }
        });
        LEFT.add(btnSupp);

        btnJual.setBackground(new java.awt.Color(204, 204, 204));
        btnJual.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnJual.setForeground(new java.awt.Color(48, 58, 82));
        btnJual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/tjual20.png"))); // NOI18N
        btnJual.setText("Transaksi Penjualan");
        btnJual.setBorder(null);
        btnJual.setIconTextGap(10);
        btnJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJualActionPerformed(evt);
            }
        });
        LEFT.add(btnJual);

        btnBeli.setBackground(new java.awt.Color(204, 204, 204));
        btnBeli.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnBeli.setForeground(new java.awt.Color(48, 58, 82));
        btnBeli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/tbeli20.png"))); // NOI18N
        btnBeli.setText("Transaksi Pembelian");
        btnBeli.setBorder(null);
        btnBeli.setIconTextGap(10);
        btnBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBeliActionPerformed(evt);
            }
        });
        LEFT.add(btnBeli);

        btnReport.setBackground(new java.awt.Color(204, 204, 204));
        btnReport.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnReport.setForeground(new java.awt.Color(48, 58, 82));
        btnReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/report20.png"))); // NOI18N
        btnReport.setText("Laporan");
        btnReport.setBorder(null);
        btnReport.setIconTextGap(10);
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });
        LEFT.add(btnReport);

        logout.setBackground(new java.awt.Color(204, 204, 204));
        logout.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        logout.setForeground(new java.awt.Color(48, 58, 82));
        logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/logout.png"))); // NOI18N
        logout.setText("Log Out");
        logout.setBorder(null);
        logout.setIconTextGap(10);
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        LEFT.add(logout);

        mainPanel.setBackground(new java.awt.Color(204, 204, 204));
        mainPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 3, new java.awt.Color(48, 58, 82)));
        mainPanel.setLayout(new java.awt.CardLayout());

        panelBuku.setBackground(new java.awt.Color(204, 204, 204));
        panelBuku.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 3, new java.awt.Color(48, 58, 82)));

        lBuku.setBackground(new java.awt.Color(48, 58, 82));
        lBuku.setFont(new java.awt.Font("Open Sans", 1, 18)); // NOI18N
        lBuku.setForeground(new java.awt.Color(48, 58, 82));
        lBuku.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lBuku.setText("Data Buku");

        lIDBuku.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lIDBuku.setForeground(new java.awt.Color(48, 58, 82));
        lIDBuku.setText("Tanggal Masuk");

        lNamaCust1.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lNamaCust1.setForeground(new java.awt.Color(48, 58, 82));
        lNamaCust1.setText("ISBN");

        lAlamatCust1.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lAlamatCust1.setForeground(new java.awt.Color(48, 58, 82));
        lAlamatCust1.setText("Judul");

        tfIDBuku.setBackground(new java.awt.Color(204, 204, 204));
        tfIDBuku.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfIDBuku.setForeground(new java.awt.Color(48, 58, 82));
        tfIDBuku.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfIDBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfIDBukuMouseClicked(evt);
            }
        });
        tfIDBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfIDBukuActionPerformed(evt);
            }
        });

        lNoCust1.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lNoCust1.setForeground(new java.awt.Color(48, 58, 82));
        lNoCust1.setText("Penulis");

        tfHarga.setBackground(new java.awt.Color(204, 204, 204));
        tfHarga.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfHarga.setForeground(new java.awt.Color(48, 58, 82));
        tfHarga.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));

        btnSimpanBuku.setBackground(new java.awt.Color(48, 58, 82));
        btnSimpanBuku.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnSimpanBuku.setText("Simpan");
        btnSimpanBuku.setBorder(null);
        btnSimpanBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanBukuActionPerformed(evt);
            }
        });

        btnUbahBuku.setBackground(new java.awt.Color(48, 58, 82));
        btnUbahBuku.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnUbahBuku.setText("Ubah");
        btnUbahBuku.setBorder(null);
        btnUbahBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahBukuActionPerformed(evt);
            }
        });

        btnHapusBuku.setBackground(new java.awt.Color(48, 58, 82));
        btnHapusBuku.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnHapusBuku.setText("Hapus");
        btnHapusBuku.setBorder(null);
        btnHapusBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusBukuActionPerformed(evt);
            }
        });

        btnResetBuku.setBackground(new java.awt.Color(48, 58, 82));
        btnResetBuku.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnResetBuku.setText("Reset");
        btnResetBuku.setBorder(null);
        btnResetBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetBukuActionPerformed(evt);
            }
        });

        jScrollPane6.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane6.setForeground(new java.awt.Color(48, 58, 82));
        jScrollPane6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tbBuku.setBackground(new java.awt.Color(204, 204, 204));
        tbBuku.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        tbBuku.setForeground(new java.awt.Color(48, 58, 82));
        tbBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tanggal Masuk", "ID Buku", "ISBN", "Judul", "Penulis", "Stok", "Harga", "Kategori", "Penerbit", "Rak", "Tahun Terbit"
            }
        ));
        tbBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBukuMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbBuku);

        tfSearchBuku.setBackground(new java.awt.Color(204, 204, 204));
        tfSearchBuku.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        tfSearchBuku.setForeground(new java.awt.Color(48, 58, 82));
        tfSearchBuku.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfSearchBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSearchBukuActionPerformed(evt);
            }
        });
        tfSearchBuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfSearchBukuKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(48, 58, 82));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/icons8_google_web_search_20px_2.png"))); // NOI18N
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel4.setIconTextGap(1);

        lNoCust2.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lNoCust2.setForeground(new java.awt.Color(48, 58, 82));
        lNoCust2.setText("Kategori");

        lNoCust3.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lNoCust3.setForeground(new java.awt.Color(48, 58, 82));
        lNoCust3.setText("Penerbit");

        lNoCust4.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lNoCust4.setForeground(new java.awt.Color(48, 58, 82));
        lNoCust4.setText("Tahun Terbit");

        lIDBuku1.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lIDBuku1.setForeground(new java.awt.Color(48, 58, 82));
        lIDBuku1.setText("ID Buku");

        lNoCust5.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lNoCust5.setForeground(new java.awt.Color(48, 58, 82));
        lNoCust5.setText("Stok");

        lNoCust6.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lNoCust6.setForeground(new java.awt.Color(48, 58, 82));
        lNoCust6.setText("Harga");

        tfISBN.setBackground(new java.awt.Color(204, 204, 204));
        tfISBN.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfISBN.setForeground(new java.awt.Color(48, 58, 82));
        tfISBN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));

        tfStok.setBackground(new java.awt.Color(204, 204, 204));
        tfStok.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfStok.setForeground(new java.awt.Color(48, 58, 82));
        tfStok.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));

        tfPenulis.setBackground(new java.awt.Color(204, 204, 204));
        tfPenulis.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfPenulis.setForeground(new java.awt.Color(48, 58, 82));
        tfPenulis.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));

        tfJudulBuku.setBackground(new java.awt.Color(204, 204, 204));
        tfJudulBuku.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfJudulBuku.setForeground(new java.awt.Color(48, 58, 82));
        tfJudulBuku.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));

        tfKategori.setBackground(new java.awt.Color(204, 204, 204));
        tfKategori.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfKategori.setForeground(new java.awt.Color(48, 58, 82));
        tfKategori.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));

        tfPenerbit.setBackground(new java.awt.Color(204, 204, 204));
        tfPenerbit.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfPenerbit.setForeground(new java.awt.Color(48, 58, 82));
        tfPenerbit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfPenerbit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPenerbitActionPerformed(evt);
            }
        });

        lNoCust7.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lNoCust7.setForeground(new java.awt.Color(48, 58, 82));
        lNoCust7.setText("Rak");

        tfRak.setBackground(new java.awt.Color(204, 204, 204));
        tfRak.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfRak.setForeground(new java.awt.Color(48, 58, 82));
        tfRak.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));

        tfTanggal.setBackground(new java.awt.Color(204, 204, 204));
        tfTanggal.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfTanggal.setForeground(new java.awt.Color(48, 58, 82));
        tfTanggal.setText("yyyy-MM-dd");
        tfTanggal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfTanggal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfTanggalMouseClicked(evt);
            }
        });
        tfTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTanggalActionPerformed(evt);
            }
        });

        tfTahun.setBackground(new java.awt.Color(204, 204, 204));
        tfTahun.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfTahun.setForeground(new java.awt.Color(48, 58, 82));
        tfTahun.setText("yyyy");
        tfTahun.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfTahun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfTahunMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelBukuLayout = new javax.swing.GroupLayout(panelBuku);
        panelBuku.setLayout(panelBukuLayout);
        panelBukuLayout.setHorizontalGroup(
            panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBukuLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBukuLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lNamaCust1)
                            .addComponent(lAlamatCust1)
                            .addComponent(lNoCust1)
                            .addComponent(lNoCust2)
                            .addComponent(lIDBuku1)
                            .addComponent(lIDBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lNoCust5)
                            .addComponent(lNoCust6)))
                    .addComponent(lNoCust3)
                    .addComponent(lNoCust7)
                    .addComponent(lNoCust4))
                .addGap(26, 26, 26)
                .addGroup(panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfTahun)
                    .addComponent(tfStok, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfIDBuku)
                    .addComponent(tfHarga)
                    .addComponent(tfISBN)
                    .addComponent(tfPenulis)
                    .addComponent(tfJudulBuku)
                    .addComponent(tfKategori)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tfPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelBukuLayout.createSequentialGroup()
                            .addComponent(btnSimpanBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnUbahBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelBukuLayout.createSequentialGroup()
                            .addComponent(btnHapusBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnResetBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tfRak, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tfTanggal))
                .addGap(18, 18, 18)
                .addGroup(panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBukuLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfSearchBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE))
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBukuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lBuku, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBukuLayout.setVerticalGroup(
            panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBukuLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lBuku)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lIDBuku)
                        .addComponent(tfTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfSearchBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addGap(16, 16, 16)
                .addGroup(panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelBukuLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))
                    .addGroup(panelBukuLayout.createSequentialGroup()
                        .addGroup(panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfIDBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lIDBuku1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(13, 13, 13)
                        .addGroup(panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lNamaCust1)
                            .addComponent(tfISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBukuLayout.createSequentialGroup()
                                .addComponent(lAlamatCust1)
                                .addGap(12, 12, 12)
                                .addComponent(lNoCust1))
                            .addGroup(panelBukuLayout.createSequentialGroup()
                                .addComponent(tfJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(tfPenulis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
                        .addGroup(panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBukuLayout.createSequentialGroup()
                                .addComponent(lNoCust5)
                                .addGap(12, 12, 12)
                                .addComponent(lNoCust6)
                                .addGap(12, 12, 12)
                                .addComponent(lNoCust2)
                                .addGap(12, 12, 12)
                                .addComponent(lNoCust3)
                                .addGap(18, 18, 18)
                                .addComponent(lNoCust7))
                            .addGroup(panelBukuLayout.createSequentialGroup()
                                .addComponent(tfStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(tfHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(tfKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(tfPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(tfRak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lNoCust4)
                            .addComponent(tfTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSimpanBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUbahBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHapusBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnResetBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40))))
        );

        mainPanel.add(panelBuku, "card2");

        panelCust.setBackground(new java.awt.Color(204, 204, 204));
        panelCust.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 3, new java.awt.Color(48, 58, 82)));

        lCust.setBackground(new java.awt.Color(48, 58, 82));
        lCust.setFont(new java.awt.Font("Open Sans", 1, 18)); // NOI18N
        lCust.setForeground(new java.awt.Color(48, 58, 82));
        lCust.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lCust.setText("Data Customer");

        lIDCust.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lIDCust.setForeground(new java.awt.Color(48, 58, 82));
        lIDCust.setText("ID Customer");

        lNamaCust.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lNamaCust.setForeground(new java.awt.Color(48, 58, 82));
        lNamaCust.setText("Nama");

        lAlamatCust.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lAlamatCust.setForeground(new java.awt.Color(48, 58, 82));
        lAlamatCust.setText("Alamat");

        lNoCust.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lNoCust.setForeground(new java.awt.Color(48, 58, 82));
        lNoCust.setText("No. Telp");

        tfIDCust.setBackground(new java.awt.Color(204, 204, 204));
        tfIDCust.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfIDCust.setForeground(new java.awt.Color(48, 58, 82));
        tfIDCust.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfIDCust.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfIDCustMouseClicked(evt);
            }
        });
        tfIDCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfIDCustActionPerformed(evt);
            }
        });

        tfNamaCust.setBackground(new java.awt.Color(204, 204, 204));
        tfNamaCust.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfNamaCust.setForeground(new java.awt.Color(48, 58, 82));
        tfNamaCust.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));

        jScrollPane3.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane3.setBorder(null);
        jScrollPane3.setForeground(new java.awt.Color(48, 58, 82));

        tfAlamatCust.setBackground(new java.awt.Color(204, 204, 204));
        tfAlamatCust.setColumns(20);
        tfAlamatCust.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfAlamatCust.setForeground(new java.awt.Color(48, 58, 82));
        tfAlamatCust.setRows(5);
        tfAlamatCust.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        jScrollPane3.setViewportView(tfAlamatCust);

        tfNoCust.setBackground(new java.awt.Color(204, 204, 204));
        tfNoCust.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfNoCust.setForeground(new java.awt.Color(48, 58, 82));
        tfNoCust.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));

        btnSimpanCust.setBackground(new java.awt.Color(48, 58, 82));
        btnSimpanCust.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnSimpanCust.setText("Simpan");
        btnSimpanCust.setBorder(null);
        btnSimpanCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanCustActionPerformed(evt);
            }
        });

        btnUbahCust.setBackground(new java.awt.Color(48, 58, 82));
        btnUbahCust.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnUbahCust.setText("Ubah");
        btnUbahCust.setBorder(null);
        btnUbahCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahCustActionPerformed(evt);
            }
        });

        btnHapusCust.setBackground(new java.awt.Color(48, 58, 82));
        btnHapusCust.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnHapusCust.setText("Hapus");
        btnHapusCust.setBorder(null);
        btnHapusCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusCustActionPerformed(evt);
            }
        });

        btnResetCust.setBackground(new java.awt.Color(48, 58, 82));
        btnResetCust.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnResetCust.setText("Reset");
        btnResetCust.setBorder(null);
        btnResetCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetCustActionPerformed(evt);
            }
        });

        jScrollPane4.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane4.setForeground(new java.awt.Color(48, 58, 82));
        jScrollPane4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane4MouseClicked(evt);
            }
        });

        tbCust.setBackground(new java.awt.Color(204, 204, 204));
        tbCust.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        tbCust.setForeground(new java.awt.Color(48, 58, 82));
        tbCust.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nama", "Alamat", "No Telp"
            }
        ));
        tbCust.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCustMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbCust);

        tfSearchCust.setBackground(new java.awt.Color(204, 204, 204));
        tfSearchCust.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        tfSearchCust.setForeground(new java.awt.Color(48, 58, 82));
        tfSearchCust.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfSearchCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSearchCustActionPerformed(evt);
            }
        });
        tfSearchCust.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfSearchCustKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(48, 58, 82));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/icons8_google_web_search_20px_2.png"))); // NOI18N
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel2.setIconTextGap(1);

        javax.swing.GroupLayout panelCustLayout = new javax.swing.GroupLayout(panelCust);
        panelCust.setLayout(panelCustLayout);
        panelCustLayout.setHorizontalGroup(
            panelCustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lCust, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelCustLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(panelCustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lIDCust)
                    .addComponent(lNamaCust)
                    .addComponent(lAlamatCust)
                    .addComponent(lNoCust))
                .addGap(32, 32, 32)
                .addGroup(panelCustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(tfNamaCust)
                        .addComponent(tfIDCust)
                        .addComponent(tfNoCust, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCustLayout.createSequentialGroup()
                        .addComponent(btnSimpanCust, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUbahCust, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCustLayout.createSequentialGroup()
                        .addComponent(btnHapusCust, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetCust, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelCustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCustLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfSearchCust, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );
        panelCustLayout.setVerticalGroup(
            panelCustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lCust)
                .addGap(24, 24, 24)
                .addGroup(panelCustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lIDCust)
                    .addComponent(tfIDCust, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSearchCust, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCustLayout.createSequentialGroup()
                        .addGroup(panelCustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lNamaCust)
                            .addComponent(tfNamaCust, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelCustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lAlamatCust)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelCustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCustLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(lNoCust))
                            .addGroup(panelCustLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(tfNoCust, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(panelCustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSimpanCust, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUbahCust, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHapusCust, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnResetCust, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        mainPanel.add(panelCust, "card2");

        panelSupp.setBackground(new java.awt.Color(204, 204, 204));
        panelSupp.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 3, new java.awt.Color(48, 58, 82)));

        lSupp.setBackground(new java.awt.Color(48, 58, 82));
        lSupp.setFont(new java.awt.Font("Open Sans", 1, 18)); // NOI18N
        lSupp.setForeground(new java.awt.Color(48, 58, 82));
        lSupp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lSupp.setText("Data Supplier");

        lID.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lID.setForeground(new java.awt.Color(48, 58, 82));
        lID.setText("ID Supplier");

        lNama.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lNama.setForeground(new java.awt.Color(48, 58, 82));
        lNama.setText("Nama");

        lAlamat.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lAlamat.setForeground(new java.awt.Color(48, 58, 82));
        lAlamat.setText("Alamat");

        lNo.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        lNo.setForeground(new java.awt.Color(48, 58, 82));
        lNo.setText("No. Telp");

        tfIDSupp.setBackground(new java.awt.Color(204, 204, 204));
        tfIDSupp.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfIDSupp.setForeground(new java.awt.Color(48, 58, 82));
        tfIDSupp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfIDSupp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfIDSuppMouseClicked(evt);
            }
        });
        tfIDSupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfIDSuppActionPerformed(evt);
            }
        });

        tfNamaSupp.setBackground(new java.awt.Color(204, 204, 204));
        tfNamaSupp.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfNamaSupp.setForeground(new java.awt.Color(48, 58, 82));
        tfNamaSupp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));

        jScrollPane1.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(48, 58, 82));

        tfAlamatSupp.setBackground(new java.awt.Color(204, 204, 204));
        tfAlamatSupp.setColumns(20);
        tfAlamatSupp.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfAlamatSupp.setForeground(new java.awt.Color(48, 58, 82));
        tfAlamatSupp.setRows(5);
        tfAlamatSupp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        jScrollPane1.setViewportView(tfAlamatSupp);

        tfNoSupp.setBackground(new java.awt.Color(204, 204, 204));
        tfNoSupp.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        tfNoSupp.setForeground(new java.awt.Color(48, 58, 82));
        tfNoSupp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));

        btnSimpan.setBackground(new java.awt.Color(48, 58, 82));
        btnSimpan.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.setBorder(null);
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnUbah.setBackground(new java.awt.Color(48, 58, 82));
        btnUbah.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnUbah.setText("Ubah");
        btnUbah.setBorder(null);
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(48, 58, 82));
        btnHapus.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.setBorder(null);
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(48, 58, 82));
        btnReset.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnReset.setText("Reset");
        btnReset.setBorder(null);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane2.setForeground(new java.awt.Color(48, 58, 82));

        tbSupp.setBackground(new java.awt.Color(204, 204, 204));
        tbSupp.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        tbSupp.setForeground(new java.awt.Color(48, 58, 82));
        tbSupp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nama", "Alamat", "No Telp"
            }
        ));
        tbSupp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSuppMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbSupp);

        tfSearch.setBackground(new java.awt.Color(204, 204, 204));
        tfSearch.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        tfSearch.setForeground(new java.awt.Color(48, 58, 82));
        tfSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSearchActionPerformed(evt);
            }
        });
        tfSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfSearchKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(48, 58, 82));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/icons8_google_web_search_20px_2.png"))); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel1.setIconTextGap(1);

        javax.swing.GroupLayout panelSuppLayout = new javax.swing.GroupLayout(panelSupp);
        panelSupp.setLayout(panelSuppLayout);
        panelSuppLayout.setHorizontalGroup(
            panelSuppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lSupp, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
            .addGroup(panelSuppLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(panelSuppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lID)
                    .addComponent(lNama)
                    .addComponent(lAlamat)
                    .addComponent(lNo))
                .addGap(39, 39, 39)
                .addGroup(panelSuppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSuppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                        .addComponent(tfNamaSupp)
                        .addComponent(tfIDSupp)
                        .addComponent(tfNoSupp))
                    .addGroup(panelSuppLayout.createSequentialGroup()
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSuppLayout.createSequentialGroup()
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelSuppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSuppLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );
        panelSuppLayout.setVerticalGroup(
            panelSuppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuppLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lSupp)
                .addGap(24, 24, 24)
                .addGroup(panelSuppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lID)
                    .addComponent(tfIDSupp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSuppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSuppLayout.createSequentialGroup()
                        .addGroup(panelSuppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lNama)
                            .addComponent(tfNamaSupp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelSuppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lAlamat)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelSuppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSuppLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(lNo))
                            .addGroup(panelSuppLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(tfNoSupp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(panelSuppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSuppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        mainPanel.add(panelSupp, "card2");

        panelReport.setBackground(new java.awt.Color(204, 204, 204));
        panelReport.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 3, new java.awt.Color(48, 58, 82)));

        lReport.setBackground(new java.awt.Color(48, 58, 82));
        lReport.setFont(new java.awt.Font("Open Sans", 1, 18)); // NOI18N
        lReport.setForeground(new java.awt.Color(48, 58, 82));
        lReport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lReport.setText("Laporan Transaksi");

        btncetakTJ.setBackground(new java.awt.Color(204, 204, 204));
        btncetakTJ.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82), 3));
        btncetakTJ.setForeground(new java.awt.Color(204, 204, 204));
        btncetakTJ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btncetakTJMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(48, 58, 82));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/tpenjualan.png"))); // NOI18N
        jLabel3.setText("Cetak Laporan Transaksi Penjualan");
        jLabel3.setIconTextGap(10);

        javax.swing.GroupLayout btncetakTJLayout = new javax.swing.GroupLayout(btncetakTJ);
        btncetakTJ.setLayout(btncetakTJLayout);
        btncetakTJLayout.setHorizontalGroup(
            btncetakTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
        );
        btncetakTJLayout.setVerticalGroup(
            btncetakTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btncetakTJLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        btncetakTB.setBackground(new java.awt.Color(204, 204, 204));
        btncetakTB.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82), 3));
        btncetakTB.setForeground(new java.awt.Color(204, 204, 204));
        btncetakTB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btncetakTBMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(48, 58, 82));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/tpembelian.png"))); // NOI18N
        jLabel6.setText("Cetak Laporan Transaksi Pembelian");
        jLabel6.setIconTextGap(10);

        javax.swing.GroupLayout btncetakTBLayout = new javax.swing.GroupLayout(btncetakTB);
        btncetakTB.setLayout(btncetakTBLayout);
        btncetakTBLayout.setHorizontalGroup(
            btncetakTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
        );
        btncetakTBLayout.setVerticalGroup(
            btncetakTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btncetakTBLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel6)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelReportLayout = new javax.swing.GroupLayout(panelReport);
        panelReport.setLayout(panelReportLayout);
        panelReportLayout.setHorizontalGroup(
            panelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lReport, javax.swing.GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelReportLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btncetakTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncetakTJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(184, 184, 184))
        );
        panelReportLayout.setVerticalGroup(
            panelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReportLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lReport)
                .addGap(42, 42, 42)
                .addComponent(btncetakTJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btncetakTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainPanel.add(panelReport, "card2");

        panelTJ.setBackground(new java.awt.Color(204, 204, 204));
        panelTJ.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 3, new java.awt.Color(48, 58, 82)));

        lTJ.setBackground(new java.awt.Color(48, 58, 82));
        lTJ.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lTJ.setForeground(new java.awt.Color(48, 58, 82));
        lTJ.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTJ.setText("ID Pelanggan");

        lTJ1.setBackground(new java.awt.Color(48, 58, 82));
        lTJ1.setFont(new java.awt.Font("Open Sans", 1, 18)); // NOI18N
        lTJ1.setForeground(new java.awt.Color(48, 58, 82));
        lTJ1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lTJ1.setText("Transaksi Penjualan");

        lTJ2.setBackground(new java.awt.Color(48, 58, 82));
        lTJ2.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lTJ2.setForeground(new java.awt.Color(48, 58, 82));
        lTJ2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTJ2.setText("ID Buku");

        lTJ4.setBackground(new java.awt.Color(48, 58, 82));
        lTJ4.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lTJ4.setForeground(new java.awt.Color(48, 58, 82));
        lTJ4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTJ4.setText("Harga");

        lTJ5.setBackground(new java.awt.Color(48, 58, 82));
        lTJ5.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lTJ5.setForeground(new java.awt.Color(48, 58, 82));
        lTJ5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTJ5.setText("ID Kasir");

        lTJ6.setBackground(new java.awt.Color(48, 58, 82));
        lTJ6.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lTJ6.setForeground(new java.awt.Color(48, 58, 82));
        lTJ6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTJ6.setText("Jumlah");

        lTJ7.setBackground(new java.awt.Color(48, 58, 82));
        lTJ7.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lTJ7.setForeground(new java.awt.Color(48, 58, 82));
        lTJ7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTJ7.setText("Total");

        tfIDPelanggan.setBackground(new java.awt.Color(204, 204, 204));
        tfIDPelanggan.setForeground(new java.awt.Color(48, 58, 82));
        tfIDPelanggan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfIDPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfIDPelangganActionPerformed(evt);
            }
        });

        tfIDbuku.setBackground(new java.awt.Color(204, 204, 204));
        tfIDbuku.setForeground(new java.awt.Color(48, 58, 82));
        tfIDbuku.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfIDbuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfIDbukuActionPerformed(evt);
            }
        });

        tfIDKasir.setBackground(new java.awt.Color(204, 204, 204));
        tfIDKasir.setForeground(new java.awt.Color(48, 58, 82));
        tfIDKasir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfIDKasir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfIDKasirMouseClicked(evt);
            }
        });
        tfIDKasir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfIDKasirActionPerformed(evt);
            }
        });

        tfharga.setBackground(new java.awt.Color(204, 204, 204));
        tfharga.setForeground(new java.awt.Color(48, 58, 82));
        tfharga.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfhargaActionPerformed(evt);
            }
        });
        tfharga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfhargaKeyPressed(evt);
            }
        });

        tfJumlah.setBackground(new java.awt.Color(204, 204, 204));
        tfJumlah.setForeground(new java.awt.Color(48, 58, 82));
        tfJumlah.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfJumlahActionPerformed(evt);
            }
        });
        tfJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfJumlahKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfJumlahKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfJumlahKeyTyped(evt);
            }
        });

        tbPenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Penjualan", "Tgl Penjualan", "ID Buku", "ID Kasir", "ID Pelanggan", "Jumlah", "Harga", "Total"
            }
        ));
        tbPenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPenjualanMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tbPenjualan);

        reset.setBackground(new java.awt.Color(48, 58, 82));
        reset.setForeground(new java.awt.Color(204, 204, 204));
        reset.setText("Reset");
        reset.setBorder(null);
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        tambah.setBackground(new java.awt.Color(48, 58, 82));
        tambah.setForeground(new java.awt.Color(204, 204, 204));
        tambah.setText("Tambah");
        tambah.setBorder(null);
        tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahActionPerformed(evt);
            }
        });

        lTJ8.setBackground(new java.awt.Color(48, 58, 82));
        lTJ8.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lTJ8.setForeground(new java.awt.Color(48, 58, 82));
        lTJ8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTJ8.setText("Total");

        lTJ9.setBackground(new java.awt.Color(48, 58, 82));
        lTJ9.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lTJ9.setForeground(new java.awt.Color(48, 58, 82));
        lTJ9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTJ9.setText("Bayar");

        lTJ10.setBackground(new java.awt.Color(48, 58, 82));
        lTJ10.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lTJ10.setForeground(new java.awt.Color(48, 58, 82));
        lTJ10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTJ10.setText("Kembalian");

        tfTotal.setBackground(new java.awt.Color(204, 204, 204));
        tfTotal.setForeground(new java.awt.Color(48, 58, 82));
        tfTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTotalActionPerformed(evt);
            }
        });

        tfBayar.setBackground(new java.awt.Color(204, 204, 204));
        tfBayar.setForeground(new java.awt.Color(48, 58, 82));
        tfBayar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfBayarActionPerformed(evt);
            }
        });
        tfBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfBayarKeyPressed(evt);
            }
        });

        tfKembalian.setEditable(false);
        tfKembalian.setBackground(new java.awt.Color(204, 204, 204));
        tfKembalian.setForeground(new java.awt.Color(48, 58, 82));
        tfKembalian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfKembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfKembalianActionPerformed(evt);
            }
        });

        tfIDPenjualan.setBackground(new java.awt.Color(204, 204, 204));
        tfIDPenjualan.setForeground(new java.awt.Color(48, 58, 82));
        tfIDPenjualan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfIDPenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfIDPenjualanMouseClicked(evt);
            }
        });
        tfIDPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfIDPenjualanActionPerformed(evt);
            }
        });

        tftotal.setEditable(false);
        tftotal.setBackground(new java.awt.Color(204, 204, 204));
        tftotal.setForeground(new java.awt.Color(48, 58, 82));
        tftotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tftotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tftotalActionPerformed(evt);
            }
        });

        tfTanggalJual.setBackground(new java.awt.Color(204, 204, 204));
        tfTanggalJual.setForeground(new java.awt.Color(48, 58, 82));
        tfTanggalJual.setText("yyy-MM-dd");
        tfTanggalJual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfTanggalJual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfTanggalJualMouseClicked(evt);
            }
        });
        tfTanggalJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTanggalJualActionPerformed(evt);
            }
        });
        tfTanggalJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfTanggalJualKeyPressed(evt);
            }
        });

        hapus.setBackground(new java.awt.Color(48, 58, 82));
        hapus.setForeground(new java.awt.Color(204, 204, 204));
        hapus.setText("Hapus");
        hapus.setBorder(null);
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });

        lTJ11.setBackground(new java.awt.Color(48, 58, 82));
        lTJ11.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lTJ11.setForeground(new java.awt.Color(48, 58, 82));
        lTJ11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTJ11.setText("Tanggal");

        lTJ12.setBackground(new java.awt.Color(48, 58, 82));
        lTJ12.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lTJ12.setForeground(new java.awt.Color(48, 58, 82));
        lTJ12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTJ12.setText("ID Penjualan");

        totall.setForeground(new java.awt.Color(204, 204, 204));
        totall.setText("0,");

        jButton1.setBackground(new java.awt.Color(48, 58, 82));
        jButton1.setForeground(new java.awt.Color(204, 204, 204));
        jButton1.setText("Selesai");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTJLayout = new javax.swing.GroupLayout(panelTJ);
        panelTJ.setLayout(panelTJLayout);
        panelTJLayout.setHorizontalGroup(
            panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTJLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelTJLayout.createSequentialGroup()
                            .addComponent(lTJ2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(tfIDbuku, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelTJLayout.createSequentialGroup()
                            .addComponent(lTJ11, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(tfTanggalJual, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelTJLayout.createSequentialGroup()
                            .addComponent(lTJ12, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(tfIDPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelTJLayout.createSequentialGroup()
                                .addComponent(lTJ5, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfIDKasir))
                            .addGroup(panelTJLayout.createSequentialGroup()
                                .addComponent(lTJ6, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelTJLayout.createSequentialGroup()
                                .addComponent(lTJ, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfIDPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelTJLayout.createSequentialGroup()
                                .addComponent(lTJ4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfharga, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panelTJLayout.createSequentialGroup()
                            .addComponent(lTJ7, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(tftotal, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelTJLayout.createSequentialGroup()
                        .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(41, 41, 41)
                .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelTJLayout.createSequentialGroup()
                        .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lTJ10, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lTJ9, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lTJ8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTJLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(tfKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelTJLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 214, Short.MAX_VALUE)
                        .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTJLayout.createSequentialGroup()
                                .addComponent(totall)
                                .addGap(34, 34, 34))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTJLayout.createSequentialGroup()
                                .addComponent(hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47))))))
            .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTJLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lTJ1, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        panelTJLayout.setVerticalGroup(
            panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTJLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTJLayout.createSequentialGroup()
                        .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lTJ12)
                            .addComponent(tfIDPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lTJ11)
                            .addComponent(tfTanggalJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lTJ2)
                            .addComponent(tfIDbuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lTJ5)
                            .addComponent(tfIDKasir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lTJ)
                            .addComponent(tfIDPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lTJ4)
                            .addComponent(tfharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lTJ6))
                        .addGap(15, 15, 15)
                        .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lTJ7)
                            .addComponent(tftotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelTJLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelTJLayout.createSequentialGroup()
                                .addComponent(totall)
                                .addGap(18, 18, 18)
                                .addComponent(hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelTJLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lTJ8)
                                    .addComponent(tfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lTJ9)
                                    .addComponent(tfBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lTJ10)
                                    .addComponent(tfKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(0, 41, Short.MAX_VALUE))
            .addGroup(panelTJLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTJLayout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addComponent(lTJ1)
                    .addContainerGap(430, Short.MAX_VALUE)))
        );

        mainPanel.add(panelTJ, "card2");

        panelTB.setBackground(new java.awt.Color(204, 204, 204));
        panelTB.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 3, new java.awt.Color(48, 58, 82)));

        lTB.setBackground(new java.awt.Color(48, 58, 82));
        lTB.setFont(new java.awt.Font("Open Sans", 1, 18)); // NOI18N
        lTB.setForeground(new java.awt.Color(48, 58, 82));
        lTB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lTB.setText("Transaksi Pembelian");

        lIDPembelian.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lIDPembelian.setForeground(new java.awt.Color(48, 58, 82));
        lIDPembelian.setText("ID Pembelian");

        lTanggalPembelian.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lTanggalPembelian.setForeground(new java.awt.Color(48, 58, 82));
        lTanggalPembelian.setText("Tanggal");

        lHargaPembelian.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lHargaPembelian.setForeground(new java.awt.Color(48, 58, 82));
        lHargaPembelian.setText("Harga");

        lJumlahPembelian.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lJumlahPembelian.setForeground(new java.awt.Color(48, 58, 82));
        lJumlahPembelian.setText("Jumlah");

        tfIDPembelian.setBackground(new java.awt.Color(204, 204, 204));
        tfIDPembelian.setForeground(new java.awt.Color(48, 58, 82));
        tfIDPembelian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfIDPembelian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfIDPembelianMouseClicked(evt);
            }
        });
        tfIDPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfIDPembelianActionPerformed(evt);
            }
        });

        tfTanggalPembelian.setBackground(new java.awt.Color(204, 204, 204));
        tfTanggalPembelian.setForeground(new java.awt.Color(48, 58, 82));
        tfTanggalPembelian.setText("yyyy-MM-dd");
        tfTanggalPembelian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfTanggalPembelian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfTanggalPembelianMouseClicked(evt);
            }
        });
        tfTanggalPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTanggalPembelianActionPerformed(evt);
            }
        });

        tfHargaPembelian.setBackground(new java.awt.Color(204, 204, 204));
        tfHargaPembelian.setForeground(new java.awt.Color(48, 58, 82));
        tfHargaPembelian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));

        tfJumlahPembelian.setBackground(new java.awt.Color(204, 204, 204));
        tfJumlahPembelian.setForeground(new java.awt.Color(48, 58, 82));
        tfJumlahPembelian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfJumlahPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfJumlahPembelianActionPerformed(evt);
            }
        });
        tfJumlahPembelian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfJumlahPembelianKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfJumlahPembelianKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfJumlahPembelianKeyTyped(evt);
            }
        });

        tbPembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Pembelian", "Tanggal", "ID Supplier", "ID Buku", "Harga", "Jumlah", "Total"
            }
        ));
        tbPembelian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPembelianMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbPembelian);

        btnSimpanPembelian.setBackground(new java.awt.Color(48, 58, 82));
        btnSimpanPembelian.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnSimpanPembelian.setText("Tambah");
        btnSimpanPembelian.setBorder(null);
        btnSimpanPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanPembelianActionPerformed(evt);
            }
        });

        btnHapusPembelian.setBackground(new java.awt.Color(48, 58, 82));
        btnHapusPembelian.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnHapusPembelian.setText("Hapus");
        btnHapusPembelian.setBorder(null);
        btnHapusPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusPembelianActionPerformed(evt);
            }
        });

        btnResetPembelian.setBackground(new java.awt.Color(48, 58, 82));
        btnResetPembelian.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnResetPembelian.setText("Reset");
        btnResetPembelian.setBorder(null);
        btnResetPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetPembelianActionPerformed(evt);
            }
        });

        btnBayarPembelian.setBackground(new java.awt.Color(48, 58, 82));
        btnBayarPembelian.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        btnBayarPembelian.setText("Simpan");
        btnBayarPembelian.setBorder(null);
        btnBayarPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarPembelianActionPerformed(evt);
            }
        });

        lTotalTB.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lTotalTB.setForeground(new java.awt.Color(48, 58, 82));
        lTotalTB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTotalTB.setText("Total");

        lTunaiPembelian.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lTunaiPembelian.setForeground(new java.awt.Color(48, 58, 82));
        lTunaiPembelian.setText("Bayar");

        lKembalianPembelian.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lKembalianPembelian.setForeground(new java.awt.Color(48, 58, 82));
        lKembalianPembelian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lKembalianPembelian.setText("Kembalian");

        tfTotalTB.setBackground(new java.awt.Color(204, 204, 204));
        tfTotalTB.setForeground(new java.awt.Color(48, 58, 82));
        tfTotalTB.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));

        tfTunaiPembelian.setBackground(new java.awt.Color(204, 204, 204));
        tfTunaiPembelian.setForeground(new java.awt.Color(48, 58, 82));
        tfTunaiPembelian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfTunaiPembelian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfTunaiPembelianKeyPressed(evt);
            }
        });

        tfKembalianPembelian.setEditable(false);
        tfKembalianPembelian.setBackground(new java.awt.Color(204, 204, 204));
        tfKembalianPembelian.setForeground(new java.awt.Color(48, 58, 82));
        tfKembalianPembelian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));

        lIDBukuPembelian.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lIDBukuPembelian.setForeground(new java.awt.Color(48, 58, 82));
        lIDBukuPembelian.setText("ID Buku");

        lIDSupplierPembelian.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lIDSupplierPembelian.setForeground(new java.awt.Color(48, 58, 82));
        lIDSupplierPembelian.setText("ID Supplier");

        tfIDBukuPembelian.setBackground(new java.awt.Color(204, 204, 204));
        tfIDBukuPembelian.setForeground(new java.awt.Color(48, 58, 82));
        tfIDBukuPembelian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfIDBukuPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfIDBukuPembelianActionPerformed(evt);
            }
        });

        tfTotalPembelian.setEditable(false);
        tfTotalPembelian.setBackground(new java.awt.Color(204, 204, 204));
        tfTotalPembelian.setForeground(new java.awt.Color(48, 58, 82));
        tfTotalPembelian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfTotalPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTotalPembelianActionPerformed(evt);
            }
        });

        tfIDSupplierPembelian.setBackground(new java.awt.Color(204, 204, 204));
        tfIDSupplierPembelian.setForeground(new java.awt.Color(48, 58, 82));
        tfIDSupplierPembelian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(48, 58, 82)));
        tfIDSupplierPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfIDSupplierPembelianActionPerformed(evt);
            }
        });

        lTotalPembelian.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lTotalPembelian.setForeground(new java.awt.Color(48, 58, 82));
        lTotalPembelian.setText("Total");

        ltotall.setForeground(new java.awt.Color(204, 204, 204));
        ltotall.setText("0");

        javax.swing.GroupLayout panelTBLayout = new javax.swing.GroupLayout(panelTB);
        panelTB.setLayout(panelTBLayout);
        panelTBLayout.setHorizontalGroup(
            panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lTB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTBLayout.createSequentialGroup()
                .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelTBLayout.createSequentialGroup()
                        .addGap(784, 784, 784)
                        .addComponent(ltotall, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelTBLayout.createSequentialGroup()
                        .addContainerGap(40, Short.MAX_VALUE)
                        .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelTBLayout.createSequentialGroup()
                                .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelTBLayout.createSequentialGroup()
                                        .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lTanggalPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lIDPembelian)
                                            .addComponent(lIDSupplierPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lIDBukuPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lJumlahPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lHargaPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18))
                                    .addGroup(panelTBLayout.createSequentialGroup()
                                        .addComponent(lTotalPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(56, 56, 56)))
                                .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfTotalPembelian, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfJumlahPembelian, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tfIDPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfTanggalPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfIDSupplierPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfIDBukuPembelian, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfHargaPembelian, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(panelTBLayout.createSequentialGroup()
                                .addComponent(btnResetPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSimpanPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(35, 35, 35)
                        .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelTBLayout.createSequentialGroup()
                                .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelTBLayout.createSequentialGroup()
                                        .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lTunaiPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lTotalTB, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(43, 43, 43)
                                        .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(tfTunaiPembelian)
                                                .addComponent(tfTotalTB, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panelTBLayout.createSequentialGroup()
                                                .addGap(32, 32, 32)
                                                .addComponent(btnBayarPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(panelTBLayout.createSequentialGroup()
                                        .addComponent(lKembalianPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(tfKembalianPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(259, 259, 259)
                                .addComponent(btnHapusPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(30, 30, 30))
        );
        panelTBLayout.setVerticalGroup(
            panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTBLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lTB)
                .addGap(30, 30, 30)
                .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTBLayout.createSequentialGroup()
                        .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfIDPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lIDPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lTanggalPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfTanggalPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfIDSupplierPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lIDSupplierPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lIDBukuPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfIDBukuPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lHargaPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfHargaPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfJumlahPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lJumlahPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lTotalPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfTotalPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnResetPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSimpanPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelTBLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelTBLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ltotall, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnHapusPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelTBLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfTotalTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lTotalTB, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfTunaiPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lTunaiPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(13, 13, 13)
                                .addGroup(panelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfKembalianPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lKembalianPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(btnBayarPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        mainPanel.add(panelTB, "card2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HEAD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(LEFT, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(HEAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LEFT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBukuActionPerformed

        // remove panel
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        // add panel
        mainPanel.add(panelBuku);
        mainPanel.repaint();
        mainPanel.revalidate();

        tampilBuku();

    }//GEN-LAST:event_btnBukuActionPerformed

    private void btnJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJualActionPerformed

        // remove panel
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        // add panel
        mainPanel.add(panelTJ);
        mainPanel.repaint();
        mainPanel.revalidate();

    }//GEN-LAST:event_btnJualActionPerformed

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed

        // remove panel
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        // add panel
        mainPanel.add(panelReport);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnReportActionPerformed

    private void btnCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustActionPerformed

        // remove panel
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        // add panel
        mainPanel.add(panelCust);
        mainPanel.repaint();
        mainPanel.revalidate();

        tampilCust();
    }//GEN-LAST:event_btnCustActionPerformed

    private void btnSuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuppActionPerformed
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        // add panel
        mainPanel.add(panelSupp);
        mainPanel.repaint();
        mainPanel.revalidate();

        tampilSupp();
    }//GEN-LAST:event_btnSuppActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
        int btnDialog = JOptionPane.YES_NO_OPTION;
        int rsDialog = JOptionPane.showConfirmDialog(this, "Yakin mau keluar?", "PERINGATAN", btnDialog);
        if (rsDialog == 0) {
            System.exit(0);
        } else {
        }
    }//GEN-LAST:event_logoutActionPerformed

    private void tfIDSuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfIDSuppActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfIDSuppActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // Menyimpan Supp
        try {
            String sql = "INSERT INTO SUPPLIER VALUES ('" + tfIDSupp.getText() + "','" + tfNamaSupp.getText() + "','" + tfAlamatSupp.getText() + "','" + tfNoSupp.getText() + "')";
            st = conn.createStatement();
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Simpan Berhasil!");
            clear();
            tampilSupp();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // Merubah Supp
        try {
            String sql = "UPDATE SUPPLIER SET NAMA_SUPPLIER = '" + tfNamaSupp.getText() + "', ALAMAT_SUPPLIER = '" + tfAlamatSupp.getText() + "', NO_TELP_SUPPLIER = '" + tfNoSupp.getText() + "' WHERE ID_SUPPLIER = '" + tfIDSupp.getText() + "'";
            st = conn.createStatement();
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data telah diperbarui!");
            clear();
            tampilSupp();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // Menghapus Supp
        try {
            String sql = "DELETE FROM SUPPLIER WHERE ID_SUPPLIER = '" + tfIDSupp.getText() + "'";

            int btnDialog = JOptionPane.YES_NO_OPTION;
            int rsDialog = JOptionPane.showConfirmDialog(this, "Yakin mau dihapus?", "PERINGATAN", btnDialog);
            if (rsDialog == 0) {
                st = conn.createStatement();
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data terhapus!");
            } else {
            }

            clear();
            tampilSupp();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // Clear fields
        clear();
    }//GEN-LAST:event_btnResetActionPerformed

    private void tfSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfSearchActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:

    }//GEN-LAST:event_formWindowOpened

    private void tfSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchKeyPressed
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("No Telp");

        try {
            String sql = "SELECT * FROM SUPPLIER WHERE ID_SUPPLIER LIKE '%" + tfSearch.getText() + "%' OR NAMA_SUPPLIER LIKE '%" + tfSearch.getText() + "%' OR ALAMAT_SUPPLIER LIKE '%" + tfSearch.getText() + "%' OR NO_TELP_SUPPLIER LIKE '%" + tfSearch.getText() + "%'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("ID_SUPPLIER"), rs.getString("NAMA_SUPPLIER"), rs.getString("ALAMAT_SUPPLIER"), rs.getString("NO_TELP_SUPPLIER")

                });
            }
            tbSupp.setModel(model);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_tfSearchKeyPressed

    private void tbSuppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSuppMouseClicked

        int baris = tbSupp.getSelectedRow();
        tfIDSupp.setText(tbSupp.getModel().getValueAt(baris, 0).toString());
        tfNamaSupp.setText(tbSupp.getModel().getValueAt(baris, 1).toString());
        tfAlamatSupp.setText(tbSupp.getModel().getValueAt(baris, 2).toString());
        tfNoSupp.setText(tbSupp.getModel().getValueAt(baris, 3).toString());
    }//GEN-LAST:event_tbSuppMouseClicked

    private void btnSimpanCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanCustActionPerformed
        // Menyimpan Cust
        try {
            String sql = "INSERT INTO PELANGGAN VALUES ('" + tfIDCust.getText() + "','" + tfNamaCust.getText() + "','" + tfAlamatCust.getText() + "','" + tfNoCust.getText() + "')";
            st = conn.createStatement();
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Simpan Berhasil!");
            clear();
            tampilCust();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnSimpanCustActionPerformed

    private void tfIDCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfIDCustActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfIDCustActionPerformed

    private void btnUbahCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahCustActionPerformed

        // Merubah Cust
        try {
            String sql = "UPDATE PELANGGAN SET NAMA_PELANGGAN = '" + tfNamaCust.getText() + "', ALAMAT_PELANGGAN = '" + tfAlamatCust.getText() + "', NO_TELP_PELANGGAN = '" + tfNoCust.getText() + "' WHERE ID_PELANGGAN = '" + tfIDCust.getText() + "'";
            st = conn.createStatement();
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data telah diperbarui!");
            clear();
            tampilCust();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnUbahCustActionPerformed

    private void btnHapusCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusCustActionPerformed

        // Menghapus Cust
        try {
            String sql = "DELETE FROM PELANGGAN WHERE ID_PELANGGAN = '" + tfIDCust.getText() + "'";

            int btnDialog = JOptionPane.YES_NO_OPTION;
            int rsDialog = JOptionPane.showConfirmDialog(this, "Yakin mau dihapus?", "PERINGATAN", btnDialog);
            if (rsDialog == 0) {
                st = conn.createStatement();
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data terhapus!");
            } else {
            }

            clear();
            tampilCust();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnHapusCustActionPerformed

    private void btnResetCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetCustActionPerformed
        // Clear Fields
        clear();
    }//GEN-LAST:event_btnResetCustActionPerformed

    private void tbCustMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCustMouseClicked
        // agar saat klik, text muncul di text field
        int baris = tbCust.getSelectedRow();
        tfIDCust.setText(tbCust.getModel().getValueAt(baris, 0).toString());
        tfNamaCust.setText(tbCust.getModel().getValueAt(baris, 1).toString());
        tfAlamatCust.setText(tbCust.getModel().getValueAt(baris, 2).toString());
        tfNoCust.setText(tbCust.getModel().getValueAt(baris, 3).toString());
    }//GEN-LAST:event_tbCustMouseClicked

    private void tfSearchCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSearchCustActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfSearchCustActionPerformed

    private void tfSearchCustKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchCustKeyPressed
        // Search Cust
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("No Telp");

        try {
            String sql = "SELECT * FROM PELANGGAN WHERE ID_PELANGGAN LIKE '%" + tfSearchCust.getText() + "%' OR NAMA_PELANGGAN LIKE '%" + tfSearchCust.getText() + "%' OR ALAMAT_PELANGGAN LIKE '%" + tfSearchCust.getText() + "%' OR NO_TELP_PELANGGAN LIKE '%" + tfSearchCust.getText() + "%'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("ID_PELANGGAN"), rs.getString("NAMA_PELANGGAN"), rs.getString("ALAMAT_PELANGGAN"), rs.getString("NO_TELP_PELANGGAN")

                });
            }
            tbCust.setModel(model);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_tfSearchCustKeyPressed

    private void btnBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBeliActionPerformed
        // remove panel
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        // add panel
        mainPanel.add(panelTB);
        mainPanel.repaint();
        mainPanel.revalidate();
   
    }//GEN-LAST:event_btnBeliActionPerformed

    private void btnSimpanBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanBukuActionPerformed
        // Menyimpan data buku

        try {
            String sql = "INSERT INTO BUKU VALUES ('"
                    + tfTanggal.getText() + "','"
                    + tfIDBuku.getText() + "','"
                    + tfISBN.getText() + "','"
                    + tfJudulBuku.getText() + "','"
                    + tfPenulis.getText() + "','"
                    + tfStok.getText() + "','" //stok
                    + tfHarga.getText() + "','" //harga
                    + tfKategori.getText() + "','"
                    + tfPenerbit.getText() + "', '"
                    + tfRak.getText() + "','" //rak
                    + tfTahun.getText() + "', null)"; //tahun
            st = conn.createStatement();
            st.executeUpdate(sql);

            JOptionPane.showMessageDialog(null, "Simpan Berhasil!");
            clear();
            tampilBuku();
            System.out.println(sql);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_btnSimpanBukuActionPerformed

    private void btnUbahBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahBukuActionPerformed
        // Merubah Buku

        try {
            String sql = "UPDATE BUKU SET TANGGAL_MASUK = '"
                    + tfTanggal.getText() + "', ISBN = '"
                    + tfISBN.getText() + "', JUDUL = '"
                    + tfJudulBuku.getText() + "', PENULIS = '"
                    + tfPenulis.getText() + "', STOK = '"
                    + tfStok.getText() + "', HARGA = '" //stok
                    + tfHarga.getText() + "', KATEGORI = '" //harga
                    + tfKategori.getText() + "', PENERBIT = '"
                    + tfPenerbit.getText() + "', RAK ='"
                    + tfRak.getText() + "', TAHUN_TERBIT = '" //rak
                    + tfTahun.getText() + "'" //tahun
                    + "WHERE ID_BUKU = '" + tfIDBuku.getText() + "'";
            st = conn.createStatement();
            st.executeUpdate(sql);

            JOptionPane.showMessageDialog(null, "Data telah diperbarui!");
            clear();
            tampilBuku();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnUbahBukuActionPerformed

    private void btnHapusBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusBukuActionPerformed

        // Menghapus Buku
        try {
            String sql = "DELETE FROM BUKU WHERE ID_BUKU = '" + tfIDBuku.getText() + "'";

            int btnDialog = JOptionPane.YES_NO_OPTION;
            int rsDialog = JOptionPane.showConfirmDialog(this, "Yakin mau dihapus?", "PERINGATAN", btnDialog);
            if (rsDialog == 0) {
                st = conn.createStatement();
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data terhapus!");
            } else {
            }

            clear();
            tampilBuku();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnHapusBukuActionPerformed

    private void btnResetBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetBukuActionPerformed
        // Clear text field
        clear();
    }//GEN-LAST:event_btnResetBukuActionPerformed

    private void tbBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBukuMouseClicked

        int baris = tbBuku.getSelectedRow();

        tfTahun.setText(tbBuku.getModel().getValueAt(baris, 9).toString());
        tfTanggal.setText(tbBuku.getModel().getValueAt(baris, 0).toString());
        tfIDBuku.setText(tbBuku.getModel().getValueAt(baris, 1).toString());
        tfPenulis.setText(tbBuku.getModel().getValueAt(baris, 4).toString());
        tfPenerbit.setText(tbBuku.getModel().getValueAt(baris, 8).toString());
        tfKategori.setText(tbBuku.getModel().getValueAt(baris, 7).toString());
        tfISBN.setText(tbBuku.getModel().getValueAt(baris, 2).toString());
        tfJudulBuku.setText(tbBuku.getModel().getValueAt(baris, 3).toString());
        tfStok.setText(tbBuku.getModel().getValueAt(baris, 5).toString());
        tfHarga.setText(tbBuku.getModel().getValueAt(baris, 6).toString());
        tfRak.setText(tbBuku.getModel().getValueAt(baris, 10).toString());
    }//GEN-LAST:event_tbBukuMouseClicked

    private void tfSearchBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSearchBukuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfSearchBukuActionPerformed

    private void tfSearchBukuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchBukuKeyPressed
        // Search Buku
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Tanggal Masuk");
        model.addColumn("ID BUKU");
        model.addColumn("ISBN");
        model.addColumn("Judul");
        model.addColumn("Penulis");
        model.addColumn("Stok");
        model.addColumn("Harga");
        model.addColumn("Kategori");
        model.addColumn("Penerbit");
        model.addColumn("Rak");
        model.addColumn("Tahun Terbit");
        try {
            String sql = "SELECT * FROM BUKU WHERE ID_BUKU LIKE '%" + tfSearchBuku.getText() + "%' OR ISBN LIKE '%" + tfSearchBuku.getText() + "%' OR JUDUL LIKE '%" + tfSearchBuku.getText() + "%' OR PENULIS LIKE '%" + tfSearchBuku.getText() + "%'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("TANGGAL_MASUK"), rs.getString("ID_BUKU"), rs.getString("ISBN"), rs.getString("JUDUL"), rs.getString("PENULIS"), rs.getString("STOK"), rs.getString("HARGA"), rs.getString("KATEGORI"), rs.getString("PENERBIT"), rs.getString("RAK"), rs.getString("TAHUN_TERBIT")

                });
            }
            tbBuku.setModel(model);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_tfSearchBukuKeyPressed

    private void tfPenerbitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPenerbitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPenerbitActionPerformed

    private void tfTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTanggalActionPerformed

    private void tfTanggalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfTanggalMouseClicked
        // TODO add your handling code here:
        tfTanggal.setText("");
    }//GEN-LAST:event_tfTanggalMouseClicked

    private void tfTahunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfTahunMouseClicked
        // TODO add your handling code here:
        tfTahun.setText("");
    }//GEN-LAST:event_tfTahunMouseClicked

    private void btnSimpanPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanPembelianActionPerformed
        // Menyimpan Pembelian
        prosestambahPembelian();
        tampilTotalPembelian();
        clear();


    }//GEN-LAST:event_btnSimpanPembelianActionPerformed

    private void btnHapusPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusPembelianActionPerformed
        // Menghapus Pembelian
        try {
            String sql = "DELETE FROM TRANSAKSI_PEMBELIAN WHERE ID_PEMBELIAN = '" + tfIDPembelian.getText() + "'";

            int btnDialog = JOptionPane.YES_NO_OPTION;
            int rsDialog = JOptionPane.showConfirmDialog(this, "Yakin mau dihapus?", "PERINGATAN", btnDialog);
            if (rsDialog == 0) {
                st = conn.createStatement();
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data terhapus!");
            } else {
            }

            clear();
            tampilPembelian();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnHapusPembelianActionPerformed

    private void btnResetPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetPembelianActionPerformed
        clear();
    }//GEN-LAST:event_btnResetPembelianActionPerformed

    private void btnBayarPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarPembelianActionPerformed
        // TODO add your handling code here:
        simpandetailPembelian();
        JOptionPane.showMessageDialog(null, "Data berhasil diupload!");
    }//GEN-LAST:event_btnBayarPembelianActionPerformed

    private void tfIDPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfIDPembelianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfIDPembelianActionPerformed

    private void tfIDBukuPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfIDBukuPembelianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfIDBukuPembelianActionPerformed

    private void tfIDSupplierPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfIDSupplierPembelianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfIDSupplierPembelianActionPerformed

    private void tfTanggalPembelianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfTanggalPembelianMouseClicked
        tfTanggalPembelian.setText("");
    }//GEN-LAST:event_tfTanggalPembelianMouseClicked

    private void tbPembelianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPembelianMouseClicked
        // agar saat klik, text muncul di text field
        int baris = tbPembelian.getSelectedRow();
        tfIDPembelian.setText(tbPembelian.getModel().getValueAt(baris, 0).toString());
        tfTanggalPembelian.setText(tbPembelian.getModel().getValueAt(baris, 1).toString());
        tfIDSupplierPembelian.setText(tbPembelian.getModel().getValueAt(baris, 2).toString());
        tfIDBukuPembelian.setText(tbPembelian.getModel().getValueAt(baris, 3).toString());
        tfHargaPembelian.setText(tbPembelian.getModel().getValueAt(baris, 4).toString());//jumlah
        tfJumlahPembelian.setText(tbPembelian.getModel().getValueAt(baris, 5).toString());//harga
        tfTotalPembelian.setText(tbPembelian.getModel().getValueAt(baris, 6).toString());//total
    }//GEN-LAST:event_tbPembelianMouseClicked

    private void jScrollPane4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane4MouseClicked

    private void tfTotalPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTotalPembelianActionPerformed
        /*// TODO add your handling code here:
        int harga = Integer.parseInt(tfHargaPembelian.getText());
        int jumlah = Integer.parseInt(tfJumlahPembelian.getText());
        int totalH = harga*jumlah;
        tfTotalPembelian.setText(Integer.toString(totalH));*/

    }//GEN-LAST:event_tfTotalPembelianActionPerformed

    private void tfJumlahPembelianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfJumlahPembelianKeyPressed
        if (evt.getKeyCode() == 10) {
            int harga = Integer.parseInt(tfHargaPembelian.getText());
            int bayar = Integer.parseInt(tfJumlahPembelian.getText());
            int total = harga * bayar;
            tfTotalPembelian.setText(Integer.toString(total));
        }
    }//GEN-LAST:event_tfJumlahPembelianKeyPressed

    private void tfJumlahPembelianKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfJumlahPembelianKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tfJumlahPembelianKeyReleased

    private void tfJumlahPembelianKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfJumlahPembelianKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tfJumlahPembelianKeyTyped

    private void tfTunaiPembelianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfTunaiPembelianKeyPressed
        if (evt.getKeyCode() == 10) {
            int totalbayar = Integer.parseInt(tfTotalTB.getText());
            int uang = Integer.parseInt(tfTunaiPembelian.getText());
            if (uang < totalbayar) {
                JOptionPane.showMessageDialog(this, "Jumlah tunai tidak cukup");
            } else {
                int total = uang - totalbayar;
                tfKembalianPembelian.setText(Integer.toString(total));
            }
        }
    }//GEN-LAST:event_tfTunaiPembelianKeyPressed

    private void tfJumlahPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfJumlahPembelianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfJumlahPembelianActionPerformed

    private void tfTanggalPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTanggalPembelianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTanggalPembelianActionPerformed

    private void tfIDPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfIDPelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfIDPelangganActionPerformed

    private void tfIDbukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfIDbukuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfIDbukuActionPerformed

    private void tfIDKasirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfIDKasirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfIDKasirActionPerformed

    private void tfhargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfhargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfhargaActionPerformed

    private void tfJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfJumlahActionPerformed

    private void tfJumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfJumlahKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            int harga = Integer.parseInt(tfharga.getText());
            int jumlah = Integer.parseInt(tfJumlah.getText());
            int totalHarga = harga * jumlah;
            tftotal.setText(Integer.toString(totalHarga));
        }
    }//GEN-LAST:event_tfJumlahKeyPressed

    private void tfJumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfJumlahKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_tfJumlahKeyReleased

    private void tfJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfJumlahKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tfJumlahKeyTyped

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here
        clear();
    }//GEN-LAST:event_resetActionPerformed

    private void tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahActionPerformed
        // TODO add your handling code here:
        prosestambah();
        Total();
        clear();
    }//GEN-LAST:event_tambahActionPerformed

    private void tfTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTotalActionPerformed

    private void tfBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfBayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfBayarActionPerformed

    private void tfBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfBayarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            int totalbayar = Integer.parseInt(tfTotal.getText());
            int uang = Integer.parseInt(tfBayar.getText());
            if (uang < totalbayar) {
                JOptionPane.showMessageDialog(this, "Jumlah tunai tidak cukup");
            } else {
                int total = uang - totalbayar;
                tfKembalian.setText(Integer.toString(total));
            }
        }
    }//GEN-LAST:event_tfBayarKeyPressed

    private void tfKembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfKembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfKembalianActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "DELETE FROM TRANSAKSI_PENJUALAN WHERE ID_PENJUALAN = '" + tfIDPenjualan.getText() + "'";

            int btnDialog = JOptionPane.YES_NO_OPTION;
            int rsDialog = JOptionPane.showConfirmDialog(this, "Yakin mau dihapus?", "PERINGATAN", btnDialog);
            if (rsDialog == 0) {
                st = conn.createStatement();
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data terhapus!");
            } else {
            }

            clear();
            tampilpenjualan();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_hapusActionPerformed

    private void tfTanggalJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTanggalJualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTanggalJualActionPerformed

    private void tfIDPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfIDPenjualanActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_tfIDPenjualanActionPerformed

    private void tftotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tftotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tftotalActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        simpandetail();
        JOptionPane.showMessageDialog(null, "Data berhasil di Upload!");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbPenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPenjualanMouseClicked
        // TODO add your handling code here:
        // agar saat klik, text muncul di text field
        int baris = tbPenjualan.getSelectedRow();
        tfIDPenjualan.setText(tbPenjualan.getModel().getValueAt(baris, 0).toString());
        tfTanggalJual.setText(tbPenjualan.getModel().getValueAt(baris, 1).toString());
        tfIDbuku.setText(tbPenjualan.getModel().getValueAt(baris, 2).toString());
        tfIDKasir.setText(tbPenjualan.getModel().getValueAt(baris, 3).toString());
        tfIDPelanggan.setText(tbPenjualan.getModel().getValueAt(baris, 4).toString());
        tfJumlah.setText(tbPenjualan.getModel().getValueAt(baris, 5).toString());//jumlah
        tfharga.setText(tbPenjualan.getModel().getValueAt(baris, 6).toString());//harga
        tftotal.setText(tbPenjualan.getModel().getValueAt(baris, 7).toString());//total
    }//GEN-LAST:event_tbPenjualanMouseClicked

    private void tfTanggalJualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfTanggalJualKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTanggalJualKeyPressed

    private void tfTanggalJualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfTanggalJualMouseClicked
        // TODO add your handling code here:
        tfTanggalJual.setText("");
    }//GEN-LAST:event_tfTanggalJualMouseClicked

    private void tfIDBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfIDBukuActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_tfIDBukuActionPerformed

    private void tfIDBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfIDBukuMouseClicked
        try {
            String sql = "SELECT * FROM BUKU ORDER BY ID_BUKU DESC";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String kode = rs.getString("ID_BUKU").substring(3);
                String AN = "" + (Integer.parseInt(kode) + 1);
                String Nol = "";

                if (AN.length() == 1) {
                    Nol = "000";
                } else if (AN.length() == 2) {
                    Nol = "00";
                } else if (AN.length() == 3) {
                    Nol = "0";
                } else if (AN.length() == 4) {
                    Nol = "";
                }
                tfIDBuku.setText("B" + Nol + AN);
            } else {
                tfIDBuku.setText("B0001");
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tfIDBukuMouseClicked

    private void tfIDCustMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfIDCustMouseClicked
        // TODO add your handling code here:
        try {
            String sql = "SELECT * FROM PELANGGAN ORDER BY ID_PELANGGAN DESC";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String kode = rs.getString("ID_PELANGGAN").substring(3);
                String AN = "" + (Integer.parseInt(kode) + 1);
                String Nol = "";

                if (AN.length() == 1) {
                    Nol = "000";
                } else if (AN.length() == 2) {
                    Nol = "00";
                } else if (AN.length() == 3) {
                    Nol = "0";
                } else if (AN.length() == 4) {
                    Nol = "";
                }
                tfIDCust.setText("C" + Nol + AN);
            } else {
                tfIDCust.setText("C0001");
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tfIDCustMouseClicked

    private void tfIDSuppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfIDSuppMouseClicked
        // TODO add your handling code here:
        try {
            String sql = "SELECT * FROM SUPPLIER ORDER BY ID_SUPPLIER DESC";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String kode = rs.getString("ID_SUPPLIER").substring(3);
                String AN = "" + (Integer.parseInt(kode) + 1);
                String Nol = "";

                if (AN.length() == 1) {
                    Nol = "000";
                } else if (AN.length() == 2) {
                    Nol = "00";
                } else if (AN.length() == 3) {
                    Nol = "0";
                } else if (AN.length() == 4) {
                    Nol = "";
                }
                tfIDSupp.setText("S" + Nol + AN);
            } else {
                tfIDSupp.setText("S0001");
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tfIDSuppMouseClicked

    private void tfIDPenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfIDPenjualanMouseClicked
        // TODO add your handling code here:
        try {
            String sql = "SELECT * FROM TRANSAKSI_PENJUALAN ORDER BY ID_PENJUALAN DESC";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String kode = rs.getString("ID_PENJUALAN").substring(3);
                String AN = "" + (Integer.parseInt(kode) + 1);
                String Nol = "";

                if (AN.length() == 1) {
                    Nol = "000";
                } else if (AN.length() == 2) {
                    Nol = "00";
                } else if (AN.length() == 3) {
                    Nol = "0";
                } else if (AN.length() == 4) {
                    Nol = "";
                }
                tfIDPenjualan.setText("J" + Nol + AN);
            } else {
                tfIDPenjualan.setText("J0001");
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tfIDPenjualanMouseClicked

    private void tfhargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfhargaKeyPressed
        // TODO add your handling code here:
//        try {
//            String sql = "SELECT HARGA FROM BUKU WHERE ID_BUKU =" + "'" + tfIDBuku.getText() + "'";
//            st = conn.createStatement();
//            rs = st.executeQuery(sql);
//            System.out.println(sql);
//            rs.close();
//           // tfharga.setText(rs);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, e.getMessage());
//        }
    }//GEN-LAST:event_tfhargaKeyPressed

    private void tfIDKasirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfIDKasirMouseClicked
        // TODO add your handling code here:
        tfIDKasir.setText("000" + String.valueOf(bpLogin.getID()));
    }//GEN-LAST:event_tfIDKasirMouseClicked

    private void btncetakTJMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncetakTJMouseClicked
        // cetak laporan TJ
        java.io.File namaFile = new java.io.File("./CetakTJ.jasper");
        try {
            net.sf.jasperreports.engine.JasperReport jasper;
            jasper = (net.sf.jasperreports.engine.JasperReport) net.sf.jasperreports.engine.util.JRLoader.loadObject(namaFile.getPath());
            net.sf.jasperreports.engine.JasperPrint jp;
            jp = net.sf.jasperreports.engine.JasperFillManager.fillReport(jasper, null, config.Conn());
            net.sf.jasperreports.view.JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btncetakTJMouseClicked

    private void btncetakTBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncetakTBMouseClicked
        // cetak laporanTB
        java.io.File namaFile = new java.io.File("./CetakTPEM.jasper");
        try {
            net.sf.jasperreports.engine.JasperReport jasper;
            jasper = (net.sf.jasperreports.engine.JasperReport) net.sf.jasperreports.engine.util.JRLoader.loadObject(namaFile.getPath());
            net.sf.jasperreports.engine.JasperPrint jp;
            jp = net.sf.jasperreports.engine.JasperFillManager.fillReport(jasper, null, config.Conn());
            net.sf.jasperreports.view.JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btncetakTBMouseClicked

    private void tfIDPembelianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfIDPembelianMouseClicked
        // TODO add your handling code here:
        try {
            String sql = "SELECT * FROM TRANSAKSI_PEMBELIAN ORDER BY ID_PEMBELIAN DESC";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String kode = rs.getString("ID_PEMBELIAN").substring(3);
                String AN = "" + (Integer.parseInt(kode) + 1);
                String Nol = "";

                if (AN.length() == 1) {
                    Nol = "000";
                } else if (AN.length() == 2) {
                    Nol = "00";
                } else if (AN.length() == 3) {
                    Nol = "0";
                } else if (AN.length() == 4) {
                    Nol = "";
                }
                tfIDPembelian.setText("P" + Nol + AN);
            } else {
                tfIDPembelian.setText("P0001");
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_tfIDPembelianMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(bpKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(bpKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(bpKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(bpKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new bpKasir().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HEAD;
    private javax.swing.JLabel ID;
    private javax.swing.JPanel LEFT;
    private javax.swing.JButton btnBayarPembelian;
    private javax.swing.JButton btnBeli;
    private javax.swing.JButton btnBuku;
    private javax.swing.JButton btnCust;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnHapusBuku;
    private javax.swing.JButton btnHapusCust;
    private javax.swing.JButton btnHapusPembelian;
    private javax.swing.JButton btnJual;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnResetBuku;
    private javax.swing.JButton btnResetCust;
    private javax.swing.JButton btnResetPembelian;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnSimpanBuku;
    private javax.swing.JButton btnSimpanCust;
    private javax.swing.JButton btnSimpanPembelian;
    private javax.swing.JButton btnSupp;
    private javax.swing.JButton btnUbah;
    private javax.swing.JButton btnUbahBuku;
    private javax.swing.JButton btnUbahCust;
    private javax.swing.JPanel btncetakTB;
    private javax.swing.JPanel btncetakTJ;
    private javax.swing.JButton hapus;
    private javax.swing.JLabel head;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lAlamat;
    private javax.swing.JLabel lAlamatCust;
    private javax.swing.JLabel lAlamatCust1;
    private javax.swing.JLabel lBuku;
    private javax.swing.JLabel lCust;
    private javax.swing.JLabel lHargaPembelian;
    private javax.swing.JLabel lID;
    private javax.swing.JLabel lIDBuku;
    private javax.swing.JLabel lIDBuku1;
    private javax.swing.JLabel lIDBukuPembelian;
    private javax.swing.JLabel lIDCust;
    private javax.swing.JLabel lIDPembelian;
    private javax.swing.JLabel lIDSupplierPembelian;
    private javax.swing.JLabel lJumlahPembelian;
    private javax.swing.JLabel lKembalianPembelian;
    private javax.swing.JLabel lNama;
    private javax.swing.JLabel lNamaCust;
    private javax.swing.JLabel lNamaCust1;
    private javax.swing.JLabel lNo;
    private javax.swing.JLabel lNoCust;
    private javax.swing.JLabel lNoCust1;
    private javax.swing.JLabel lNoCust2;
    private javax.swing.JLabel lNoCust3;
    private javax.swing.JLabel lNoCust4;
    private javax.swing.JLabel lNoCust5;
    private javax.swing.JLabel lNoCust6;
    private javax.swing.JLabel lNoCust7;
    private javax.swing.JLabel lReport;
    private javax.swing.JLabel lSupp;
    private javax.swing.JLabel lTB;
    private javax.swing.JLabel lTJ;
    private javax.swing.JLabel lTJ1;
    private javax.swing.JLabel lTJ10;
    private javax.swing.JLabel lTJ11;
    private javax.swing.JLabel lTJ12;
    private javax.swing.JLabel lTJ2;
    private javax.swing.JLabel lTJ4;
    private javax.swing.JLabel lTJ5;
    private javax.swing.JLabel lTJ6;
    private javax.swing.JLabel lTJ7;
    private javax.swing.JLabel lTJ8;
    private javax.swing.JLabel lTJ9;
    private javax.swing.JLabel lTanggalPembelian;
    private javax.swing.JLabel lTotalPembelian;
    private javax.swing.JLabel lTotalTB;
    private javax.swing.JLabel lTunaiPembelian;
    private javax.swing.JButton logout;
    private javax.swing.JLabel ltotall;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel panelBuku;
    private javax.swing.JPanel panelCust;
    private javax.swing.JPanel panelReport;
    private javax.swing.JPanel panelSupp;
    private javax.swing.JPanel panelTB;
    private javax.swing.JPanel panelTJ;
    private javax.swing.JButton reset;
    private javax.swing.JButton tambah;
    private javax.swing.JTable tbBuku;
    private javax.swing.JTable tbCust;
    private javax.swing.JTable tbPembelian;
    private javax.swing.JTable tbPenjualan;
    private javax.swing.JTable tbSupp;
    private javax.swing.JTextArea tfAlamatCust;
    private javax.swing.JTextArea tfAlamatSupp;
    private javax.swing.JTextField tfBayar;
    private javax.swing.JTextField tfHarga;
    private javax.swing.JTextField tfHargaPembelian;
    private javax.swing.JTextField tfIDBuku;
    private javax.swing.JTextField tfIDBukuPembelian;
    private javax.swing.JTextField tfIDCust;
    private javax.swing.JTextField tfIDKasir;
    private javax.swing.JTextField tfIDPelanggan;
    private javax.swing.JTextField tfIDPembelian;
    private javax.swing.JTextField tfIDPenjualan;
    private javax.swing.JTextField tfIDSupp;
    private javax.swing.JTextField tfIDSupplierPembelian;
    private javax.swing.JTextField tfIDbuku;
    private javax.swing.JTextField tfISBN;
    private javax.swing.JTextField tfJudulBuku;
    private javax.swing.JTextField tfJumlah;
    private javax.swing.JTextField tfJumlahPembelian;
    private javax.swing.JTextField tfKategori;
    private javax.swing.JTextField tfKembalian;
    private javax.swing.JTextField tfKembalianPembelian;
    private javax.swing.JTextField tfNamaCust;
    private javax.swing.JTextField tfNamaSupp;
    private javax.swing.JTextField tfNoCust;
    private javax.swing.JTextField tfNoSupp;
    private javax.swing.JTextField tfPenerbit;
    private javax.swing.JTextField tfPenulis;
    private javax.swing.JTextField tfRak;
    private javax.swing.JTextField tfSearch;
    private javax.swing.JTextField tfSearchBuku;
    private javax.swing.JTextField tfSearchCust;
    private javax.swing.JTextField tfStok;
    private javax.swing.JTextField tfTahun;
    private javax.swing.JTextField tfTanggal;
    private javax.swing.JTextField tfTanggalJual;
    private javax.swing.JTextField tfTanggalPembelian;
    private javax.swing.JTextField tfTotal;
    private javax.swing.JTextField tfTotalPembelian;
    private javax.swing.JTextField tfTotalTB;
    private javax.swing.JTextField tfTunaiPembelian;
    private javax.swing.JTextField tfharga;
    private javax.swing.JTextField tftotal;
    private javax.swing.JLabel totall;
    // End of variables declaration//GEN-END:variables
}
