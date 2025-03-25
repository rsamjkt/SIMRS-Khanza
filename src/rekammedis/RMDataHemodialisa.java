/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public final class RMDataHemodialisa extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private String dpjp = "";
    private String TANGGALMUNDUR = "yes";

    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMDataHemodialisa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);
        setSize(628, 674);

        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "No.R.M.", "Nama Pasien", "Umur", "JK", "Tgl.Lahir", "Tanggal", "Jam Mulai", "Lama", "Akses", 
            "Keluhan Utama", "Penyakit Penyerta", "SLED", "Dialist", "Transfusi", "Penarikan", "QB", "QD", "UFG", 
            "Heparin Awal", "Heparin Continous", "Free Heparin", "LMWH", "Volume Priming", "Ureum", "HB", "HBsAg", 
            "Creatinin", "HIV", "HCV", "Lain", "NIP", "Nama Petugas"
        }) {
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 33; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) column.setPreferredWidth(105);
            else if (i == 1) column.setPreferredWidth(65);
            else if (i == 2) column.setPreferredWidth(160);
            else if (i == 3) column.setPreferredWidth(35);
            else if (i == 4) column.setPreferredWidth(20);
            else if (i == 5) column.setPreferredWidth(65);
            else if (i == 6) column.setPreferredWidth(75);
            else if (i == 7) column.setPreferredWidth(60);
            else if (i == 8) column.setPreferredWidth(40);
            else if (i == 9) column.setPreferredWidth(60);
            else if (i == 10) column.setPreferredWidth(150);
            else if (i == 11) column.setPreferredWidth(100);
            else if (i == 12) column.setPreferredWidth(40);
            else if (i == 13) column.setPreferredWidth(60);
            else if (i == 14) column.setPreferredWidth(50);
            else if (i == 15) column.setPreferredWidth(50);
            else if (i == 16) column.setPreferredWidth(50);
            else if (i == 17) column.setPreferredWidth(50);
            else if (i == 18) column.setPreferredWidth(50);
            else if (i == 19) column.setPreferredWidth(60);
            else if (i == 20) column.setPreferredWidth(60);
            else if (i == 21) column.setPreferredWidth(60);
            else if (i == 22) column.setPreferredWidth(60);
            else if (i == 23) column.setPreferredWidth(60);
            else if (i == 24) column.setPreferredWidth(50);
            else if (i == 25) column.setPreferredWidth(50);
            else if (i == 26) column.setPreferredWidth(50);
            else if (i == 27) column.setPreferredWidth(50);
            else if (i == 28) column.setPreferredWidth(50);
            else if (i == 29) column.setPreferredWidth(50);
            else if (i == 30) column.setPreferredWidth(100);
            else if (i == 31) column.setPreferredWidth(90);
            else if (i == 32) column.setPreferredWidth(160);
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        NIP.setDocument(new batasInput((byte)20).getKata(NIP));
        JamMulai.setDocument(new batasInput((byte)8).getKata(JamMulai));
        Lama.setDocument(new batasInput((byte)5).getKata(Lama));
        Akses.setDocument(new batasInput((byte)30).getKata(Akses));
        KeluhanUtama.setDocument(new batasInput((int)2000).getKata(KeluhanUtama));
        PenyakitPenyerta.setDocument(new batasInput((byte)200).getKata(PenyakitPenyerta));
        SLED.setDocument(new batasInput((byte)10).getKata(SLED));
        Dialist.setDocument(new batasInput((byte)30).getKata(Dialist));
        Transfusi.setDocument(new batasInput((byte)5).getKata(Transfusi));
        Penarikan.setDocument(new batasInput((byte)5).getKata(Penarikan));
        QB.setDocument(new batasInput((byte)5).getKata(QB));
        QD.setDocument(new batasInput((byte)5).getKata(QD));
        UFG.setDocument(new batasInput((byte)20).getKata(UFG));
        HeparinAwal.setDocument(new batasInput((byte)10).getKata(HeparinAwal));
        HeparinContinous.setDocument(new batasInput((byte)10).getKata(HeparinContinous));
        FreeHeparin.setDocument(new batasInput((byte)10).getKata(FreeHeparin));
        LMWH.setDocument(new batasInput((byte)10).getKata(LMWH));
        VolumePriming.setDocument(new batasInput((byte)10).getKata(VolumePriming));
        Ureum.setDocument(new batasInput((byte)10).getKata(Ureum));
        HB.setDocument(new batasInput((byte)10).getKata(HB));
        HBsAg.setDocument(new batasInput((byte)10).getKata(HBsAg));
        Creatinin.setDocument(new batasInput((byte)10).getKata(Creatinin));
        HIV.setDocument(new batasInput((byte)10).getKata(HIV));
        HCV.setDocument(new batasInput((byte)10).getKata(HCV));
        Lain.setDocument(new batasInput((byte)200).getKata(Lain));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }

        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (petugas.getTable().getSelectedRow() != -1) {
                    NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                }
                NIP.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        ChkInput.setSelected(false);
        isForm();
        jam();

        try {
            TANGGALMUNDUR = koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR = "yes";
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnDataHemodialisa = new javax.swing.JMenuItem();
        JK = new widget.TextBox();
        Umur = new widget.TextBox();
        TanggalRegistrasi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        JamMulai = new widget.TextBox();
        jLabel17 = new widget.Label();
        Lama = new widget.TextBox();
        jLabel18 = new widget.Label();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel9 = new widget.Label();
        Akses = new widget.TextBox();
        jLabel10 = new widget.Label();
        KeluhanUtama = new widget.TextBox();
        jLabel11 = new widget.Label();
        PenyakitPenyerta = new widget.TextBox();
        jLabel12 = new widget.Label();
        SLED = new widget.TextBox();
        jLabel13 = new widget.Label();
        Dialist = new widget.TextBox();
        jLabel14 = new widget.Label();
        Transfusi = new widget.TextBox();
        jLabel15 = new widget.Label();
        Penarikan = new widget.TextBox();
        jLabel20 = new widget.Label();
        QB = new widget.TextBox();
        jLabel21 = new widget.Label();
        QD = new widget.TextBox();
        jLabel22 = new widget.Label();
        UFG = new widget.TextBox();
        jLabel23 = new widget.Label();
        HeparinAwal = new widget.TextBox();
        jLabel24 = new widget.Label();
        HeparinContinous = new widget.TextBox();
        jLabel25 = new widget.Label();
        FreeHeparin = new widget.TextBox();
        jLabel26 = new widget.Label();
        LMWH = new widget.TextBox();
        jLabel27 = new widget.Label();
        VolumePriming = new widget.TextBox();
        jLabel28 = new widget.Label();
        Ureum = new widget.TextBox();
        jLabel29 = new widget.Label();
        HB = new widget.TextBox();
        jLabel30 = new widget.Label();
        HBsAg = new widget.TextBox();
        jLabel31 = new widget.Label();
        Creatinin = new widget.TextBox();
        jLabel32 = new widget.Label();
        HIV = new widget.TextBox();
        jLabel33 = new widget.Label();
        HCV = new widget.TextBox();
        jLabel34 = new widget.Label();
        Lain = new widget.TextBox();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnDataHemodialisa.setBackground(new java.awt.Color(255, 255, 254));
        MnDataHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataHemodialisa.setForeground(new java.awt.Color(50, 50, 50));
        MnDataHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataHemodialisa.setText("Data Hemodialisa");
        MnDataHemodialisa.setName("MnDataHemodialisa"); // NOI18N
        MnDataHemodialisa.setPreferredSize(new java.awt.Dimension(260, 26));
        MnDataHemodialisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataHemodialisaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDataHemodialisa);

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Catatan Observasi Hemodialisa ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-03-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-03-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 400));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 425));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 80, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 285, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-03-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(84, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 80, 23);

        JamMulai.setHighlighter(null);
        JamMulai.setName("JamMulai"); // NOI18N
        JamMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamMulaiKeyPressed(evt);
            }
        });
        FormInput.add(JamMulai);
        JamMulai.setBounds(178, 40, 70, 23);

        jLabel17.setText("Lama :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(252, 40, 40, 23);

        Lama.setHighlighter(null);
        Lama.setName("Lama"); // NOI18N
        Lama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LamaKeyPressed(evt);
            }
        });
        FormInput.add(Lama);
        Lama.setBounds(296, 40, 50, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(350, 40, 70, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        NIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPKeyPressed(evt);
            }
        });
        FormInput.add(NIP);
        NIP.setBounds(424, 40, 94, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(520, 40, 187, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(709, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel9.setText("Akses :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 70, 80, 23);

        Akses.setHighlighter(null);
        Akses.setName("Akses"); // NOI18N
        Akses.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AksesKeyPressed(evt);
            }
        });
        FormInput.add(Akses);
        Akses.setBounds(84, 70, 100, 23);

        jLabel10.setText("Keluhan Utama :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(188, 70, 90, 23);

        KeluhanUtama.setHighlighter(null);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        FormInput.add(KeluhanUtama);
        KeluhanUtama.setBounds(282, 70, 507, 23);

        jLabel11.setText("Penyakit Penyerta :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 100, 100, 23);

        PenyakitPenyerta.setHighlighter(null);
        PenyakitPenyerta.setName("PenyakitPenyerta"); // NOI18N
        PenyakitPenyerta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitPenyertaKeyPressed(evt);
            }
        });
        FormInput.add(PenyakitPenyerta);
        PenyakitPenyerta.setBounds(104, 100, 200, 23);

        jLabel12.setText("SLED :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(308, 100, 40, 23);

        SLED.setHighlighter(null);
        SLED.setName("SLED"); // NOI18N
        SLED.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SLEDKeyPressed(evt);
            }
        });
        FormInput.add(SLED);
        SLED.setBounds(352, 100, 60, 23);

        jLabel13.setText("Dialist :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(416, 100, 50, 23);

        Dialist.setHighlighter(null);
        Dialist.setName("Dialist"); // NOI18N
        Dialist.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DialistKeyPressed(evt);
            }
        });
        FormInput.add(Dialist);
        Dialist.setBounds(470, 100, 100, 23);

        jLabel14.setText("Transfusi :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(574, 100, 60, 23);

        Transfusi.setHighlighter(null);
        Transfusi.setName("Transfusi"); // NOI18N
        Transfusi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TransfusiKeyPressed(evt);
            }
        });
        FormInput.add(Transfusi);
        Transfusi.setBounds(638, 100, 50, 23);

        jLabel15.setText("Penarikan :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(692, 100, 60, 23);

        Penarikan.setHighlighter(null);
        Penarikan.setName("Penarikan"); // NOI18N
        Penarikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenarikanKeyPressed(evt);
            }
        });
        FormInput.add(Penarikan);
        Penarikan.setBounds(756, 100, 50, 23);

        jLabel20.setText("QB :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 130, 80, 23);

        QB.setHighlighter(null);
        QB.setName("QB"); // NOI18N
        QB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                QBKeyPressed(evt);
            }
        });
        FormInput.add(QB);
        QB.setBounds(84, 130, 50, 23);

        jLabel21.setText("QD :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(138, 130, 40, 23);

        QD.setHighlighter(null);
        QD.setName("QD"); // NOI18N
        QD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                QDKeyPressed(evt);
            }
        });
        FormInput.add(QD);
        QD.setBounds(182, 130, 50, 23);

        jLabel22.setText("UFG :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(236, 130, 40, 23);

        UFG.setHighlighter(null);
        UFG.setName("UFG"); // NOI18N
        UFG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UFGKeyPressed(evt);
            }
        });
        FormInput.add(UFG);
        UFG.setBounds(280, 130, 70, 23);

        jLabel23.setText("Heparin Awal :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(354, 130, 80, 23);

        HeparinAwal.setHighlighter(null);
        HeparinAwal.setName("HeparinAwal"); // NOI18N
        HeparinAwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HeparinAwalKeyPressed(evt);
            }
        });
        FormInput.add(HeparinAwal);
        HeparinAwal.setBounds(438, 130, 60, 23);

        jLabel24.setText("Heparin Continous :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(502, 130, 100, 23);

        HeparinContinous.setHighlighter(null);
        HeparinContinous.setName("HeparinContinous"); // NOI18N
        HeparinContinous.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HeparinContinousKeyPressed(evt);
            }
        });
        FormInput.add(HeparinContinous);
        HeparinContinous.setBounds(606, 130, 60, 23);

        jLabel25.setText("Free Heparin :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(670, 130, 80, 23);

        FreeHeparin.setHighlighter(null);
        FreeHeparin.setName("FreeHeparin"); // NOI18N
        FreeHeparin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FreeHeparinKeyPressed(evt);
            }
        });
        FormInput.add(FreeHeparin);
        FreeHeparin.setBounds(754, 130, 60, 23);

        jLabel26.setText("LMWH :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(0, 160, 80, 23);

        LMWH.setHighlighter(null);
        LMWH.setName("LMWH"); // NOI18N
        LMWH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LMWHKeyPressed(evt);
            }
        });
        FormInput.add(LMWH);
        LMWH.setBounds(84, 160, 60, 23);

        jLabel27.setText("Volume Priming :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(148, 160, 90, 23);

        VolumePriming.setHighlighter(null);
        VolumePriming.setName("VolumePriming"); // NOI18N
        VolumePriming.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VolumePrimingKeyPressed(evt);
            }
        });
        FormInput.add(VolumePriming);
        VolumePriming.setBounds(242, 160, 60, 23);

        jLabel28.setText("Ureum :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(306, 160, 50, 23);

        Ureum.setHighlighter(null);
        Ureum.setName("Ureum"); // NOI18N
        Ureum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UreumKeyPressed(evt);
            }
        });
        FormInput.add(Ureum);
        Ureum.setBounds(360, 160, 60, 23);

        jLabel29.setText("HB :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(424, 160, 40, 23);

        HB.setHighlighter(null);
        HB.setName("HB"); // NOI18N
        HB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HBKeyPressed(evt);
            }
        });
        FormInput.add(HB);
        HB.setBounds(468, 160, 60, 23);

        jLabel30.setText("HBsAg :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(532, 160, 50, 23);

        HBsAg.setHighlighter(null);
        HBsAg.setName("HBsAg"); // NOI18N
        HBsAg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HBsAgKeyPressed(evt);
            }
        });
        FormInput.add(HBsAg);
        HBsAg.setBounds(586, 160, 60, 23);

        jLabel31.setText("Creatinin :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(650, 160, 60, 23);

        Creatinin.setHighlighter(null);
        Creatinin.setName("Creatinin"); // NOI18N
        Creatinin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CreatininKeyPressed(evt);
            }
        });
        FormInput.add(Creatinin);
        Creatinin.setBounds(714, 160, 60, 23);

        jLabel32.setText("HIV :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 190, 80, 23);

        HIV.setHighlighter(null);
        HIV.setName("HIV"); // NOI18N
        HIV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HIVKeyPressed(evt);
            }
        });
        FormInput.add(HIV);
        HIV.setBounds(84, 190, 60, 23);

        jLabel33.setText("HCV :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(148, 190, 40, 23);

        HCV.setHighlighter(null);
        HCV.setName("HCV"); // NOI18N
        HCV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HCVKeyPressed(evt);
            }
        });
        FormInput.add(HCV);
        HCV.setBounds(192, 190, 60, 23);

        jLabel34.setText("Lain :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(256, 190, 40, 23);

        Lain.setHighlighter(null);
        Lain.setName("Lain"); // NOI18N
        Lain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainKeyPressed(evt);
            }
        });
        FormInput.add(Lain);
        Lain.setBounds(300, 190, 489, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>                        

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {                                 
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
        } else {
            Valid.pindah(evt, TCari, Tanggal);
        }
    }                                

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {                                   
        Valid.pindah(evt, TCari, BtnSimpan);
    }                                  

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {                                          
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "pasien");
        } else if (NIP.getText().trim().equals("") || NamaPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIP, "Petugas");
        } else {
            if (akses.getkode().equals("Admin Utama")) {
                simpan();
            } else {
                if (TanggalRegistrasi.getText().equals("")) {
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?", TNoRw.getText()));
                }
                if (Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + JamMulai.getText()) == true) {
                    simpan();
                }
            }
        }
    }                                         

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {                                     
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, Lain, BtnBatal);
        }
    }                                    

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {                                         
        emptTeks();
        ChkInput.setSelected(true);
        isForm();
    }                                        

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {                                    
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
    }                                   

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if (tbObat.getSelectedRow() > -1) {
            if (akses.getkode().equals("Admin Utama")) {
                hapus();
            } else {
                if (NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 31).toString())) {
                    if (Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString() + " " + tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString(), Sequel.ambiltanggalsekarang()) == true) {
                        hapus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
        }
    }                                        

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {                                    
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
    }                                   

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {                                        
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "pasien");
        } else if (NIP.getText().trim().equals("") || NamaPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIP, "Petugas");
        } else {
            if (tbObat.getSelectedRow() > -1) {
                if (akses.getkode().equals("Admin Utama")) {
                    ganti();
                } else {
                    if (NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 31).toString())) {
                        if (Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString() + " " + tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString(), Sequel.ambiltanggalsekarang()) == true) {
                            if (TanggalRegistrasi.getText().equals("")) {
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?", TNoRw.getText()));
                            }
                            if (Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + JamMulai.getText()) == true) {
                                ganti();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
    }                                       

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {                                   
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }                                  

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        petugas.dispose();
        dispose();
    }                                         

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {                                     
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, TCari);
        }
    }                                    

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));

            if (TCari.getText().trim().equals("")) {
                Valid.MyReportqry("rptDataCatatanObservasiHemodialisa.jasper", "report", "::~[ Data Catatan Observasi Hemodialisa ]::~",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur," +
                    "pasien.jk,pasien.tgl_lahir,hemodialisa.tanggal,hemodialisa.jam_mulai,hemodialisa.lama,hemodialisa.akses," +
                    "hemodialisa.keluhan_utama,hemodialisa.penyakit_penyerta,hemodialisa.sled,hemodialisa.dialist,hemodialisa.transfusi," +
                    "hemodialisa.penarikan,hemodialisa.qb,hemodialisa.qd,hemodialisa.ufg,hemodialisa.heparin_awal,hemodialisa.heparin_continous," +
                    "hemodialisa.free_heparin,hemodialisa.lmwh,hemodialisa.volume_priming,hemodialisa.ureum,hemodialisa.hb," +
                    "hemodialisa.hbsag,hemodialisa.creatinin,hemodialisa.hiv,hemodialisa.hcv,hemodialisa.lain,petugas.nama " +
                    "from hemodialisa inner join reg_periksa on hemodialisa.no_rawat=reg_periksa.no_rawat " +
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                    "inner join petugas on hemodialisa.no_rawat=petugas.nip where " +
                    "hemodialisa.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " +
                    "order by hemodialisa.tanggal,hemodialisa.jam_mulai", param);
            } else {
                Valid.MyReportqry("rptDataCatatanObservasiHemodialisa.jasper", "report", "::~[ Data Catatan Observasi Hemodialisa ]::~",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur," +
                    "pasien.jk,pasien.tgl_lahir,hemodialisa.tanggal,hemodialisa.jam_mulai,hemodialisa.lama,hemodialisa.akses," +
                    "hemodialisa.keluhan_utama,hemodialisa.penyakit_penyerta,hemodialisa.sled,hemodialisa.dialist,hemodialisa.transfusi," +
                    "hemodialisa.penarikan,hemodialisa.qb,hemodialisa.qd,hemodialisa.ufg,hemodialisa.heparin_awal,hemodialisa.heparin_continous," +
                    "hemodialisa.free_heparin,hemodialisa.lmwh,hemodialisa.volume_priming,hemodialisa.ureum,hemodialisa.hb," +
                    "hemodialisa.hbsag,hemodialisa.creatinin,hemodialisa.hiv,hemodialisa.hcv,hemodialisa.lain,petugas.nama " +
                    "from hemodialisa inner join reg_periksa on hemodialisa.no_rawat=reg_periksa.no_rawat " +
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                    "inner join petugas on hemodialisa.no_rawat=petugas.nip where " +
                    "hemodialisa.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and " +
                    "(reg_periksa.no_rawat like '%" + TCari.getText().trim() + "%' or pasien.no_rkm_medis like '%" + TCari.getText().trim() + "%' or " +
                    "pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or petugas.nama like '%" + TCari.getText().trim() + "%') " +
                    "order by hemodialisa.tanggal,hemodialisa.jam_mulai", param);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }                                        

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {                                    
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
    }                                   

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {                                 
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
    }                                

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {                                        
        tampil();
    }                                       

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {                                   
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }                                  

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {                                       
        TCari.setText("");
        tampil();
    }                                      

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {                                  
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampil();
            TCari.setText("");
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
    }                                 

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {                                   
        Valid.pindah(evt, TCari, JamMulai);
    }                                  

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {                                 
        // Valid.pindah(evt, TNm, BtnSimpan);
    }                                

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {                                    
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }                                   

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {                                  
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }                                 

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {                                         
        isForm();
    }                                        

    private void JamMulaiKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, Tanggal, Lama);
    }                              

    private void LamaKeyPressed(java.awt.event.KeyEvent evt) {                                 
        Valid.pindah(evt, JamMulai, NIP);
    }                                

    private void NIPKeyPressed(java.awt.event.KeyEvent evt) {                               
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            NamaPetugas.setText(petugas.tampil3(NIP.getText()));
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Lama.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Akses.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnPetugasActionPerformed(null);
        }
    }                              

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {                                           
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }                                          

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {                                      
        Valid.pindah(evt, NIP, Akses);
    }                                     

    private void MnDataHemodialisaActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        if (tbObat.getSelectedRow() > -1) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            dpjp = Sequel.cariIsi("select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=?", tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            if (dpjp.equals("")) {
                dpjp = Sequel.cariIsi("select dokter.nm_dokter from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat=?", tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            }
            param.put("dpjp", dpjp);
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptFormulirCatatanObservasiHemodialisa.jasper", "report", "::~[ Formulir Catatan Observasi Hemodialisa ]::~",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,reg_periksa.tgl_registrasi,reg_periksa.jam_reg," +
                "pasien.jk,pasien.tgl_lahir,hemodialisa.tanggal,hemodialisa.jam_mulai,hemodialisa.lama,hemodialisa.akses," +
                "hemodialisa.keluhan_utama,hemodialisa.penyakit_penyerta,hemodialisa.sled,hemodialisa.dialist,hemodialisa.transfusi," +
                "hemodialisa.penarikan,hemodialisa.qb,hemodialisa.qd,hemodialisa.ufg,hemodialisa.heparin_awal,hemodialisa.heparin_continous," +
                "hemodialisa.free_heparin,hemodialisa.lmwh,hemodialisa.volume_priming,hemodialisa.ureum,hemodialisa.hb," +
                "hemodialisa.hbsag,hemodialisa.creatinin,hemodialisa.hiv,hemodialisa.hcv,hemodialisa.lain,petugas.nama " +
                "from hemodialisa inner join reg_periksa on hemodialisa.no_rawat=reg_periksa.no_rawat " +
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "inner join petugas on hemodialisa.no_rawat=petugas.nip where reg_periksa.no_rawat='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "' " +
                "order by hemodialisa.tanggal,hemodialisa.jam_mulai", param);
        }
    }                                                     

    private void AksesKeyPressed(java.awt.event.KeyEvent evt) {                              
        Valid.pindah(evt, NIP, KeluhanUtama);
    }                             

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {                              
        Valid.pindah(evt, Akses, PenyakitPenyerta);
    }                             

    private void PenyakitPenyertaKeyPressed(java.awt.event.KeyEvent evt) {                                         
        Valid.pindah(evt, KeluhanUtama, SLED);
    }                                        

    private void SLEDKeyPressed(java.awt.event.KeyEvent evt) {                                       
        Valid.pindah(evt, PenyakitPenyerta, Dialist);
    }                                      

    private void DialistKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, SLED, Transfusi);
    }                              

    private void TransfusiKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, Dialist, Penarikan);
    }                              

    private void PenarikanKeyPressed(java.awt.event.KeyEvent evt) {                                 
        Valid.pindah(evt, Transfusi, QB);
    }                                

    private void QBKeyPressed(java.awt.event.KeyEvent evt) {                                
        Valid.pindah(evt, Penarikan, QD);
    }                               

    private void QDKeyPressed(java.awt.event.KeyEvent evt) {                                
        Valid.pindah(evt, QB, UFG);
    }                               

    private void UFGKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, QD, HeparinAwal);
    }                              

    private void HeparinAwalKeyPressed(java.awt.event.KeyEvent evt) {                                    
        Valid.pindah(evt, UFG, HeparinContinous);
    }                                   

    private void HeparinContinousKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, HeparinAwal, FreeHeparin);
    }                              

    private void FreeHeparinKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, HeparinContinous, LMWH);
    }                              

    private void LMWHKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, FreeHeparin, VolumePriming);
    }                              

    private void VolumePrimingKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, LMWH, Ureum);
    }                              

    private void UreumKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, VolumePriming, HB);
    }                              

    private void HBKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, Ureum, HBsAg);
    }                              

    private void HBsAgKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, HB, Creatinin);
    }                              

    private void CreatininKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, HBsAg, HIV);
    }                              

    private void HIVKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, Creatinin, HCV);
    }                              

    private void HCVKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, HIV, Lain);
    }                              

    private void LainKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, HCV, BtnSimpan);
    }                              

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDataHemodialisa dialog = new RMDataHemodialisa(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify                     
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnDataHemodialisa;
    private widget.TextBox NIP;
    private widget.TextBox NamaPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox QB;
    private widget.TextBox QD;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox Akses;
    private widget.TextBox Creatinin;
    private widget.TextBox Dialist;
    private widget.TextBox FreeHeparin;
    private widget.TextBox HB;
    private widget.TextBox HBsAg;
    private widget.TextBox HCV;
    private widget.TextBox HIV;
    private widget.TextBox HeparinAwal;
    private widget.TextBox HeparinContinous;
    private widget.TextBox JamMulai;
    private widget.TextBox KeluhanUtama;
    private widget.TextBox LMWH;
    private widget.TextBox Lain;
    private widget.TextBox Lama;
    private widget.TextBox Penarikan;
    private widget.TextBox PenyakitPenyerta;
    private widget.TextBox SLED;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox Transfusi;
    private widget.TextBox UFG;
    private widget.TextBox Umur;
    private widget.TextBox Ureum;
    private widget.TextBox VolumePriming;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration                   

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            String query = "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur," +
               "pasien.jk,pasien.tgl_lahir,hemodialisa.tanggal,hemodialisa.jam_mulai,hemodialisa.lama,hemodialisa.akses," +
               "hemodialisa.keluhan_utama,hemodialisa.penyakit_penyerta,hemodialisa.sled,hemodialisa.dialist,hemodialisa.transfusi," +
               "hemodialisa.penarikan,hemodialisa.qb,hemodialisa.qd,hemodialisa.ufg,hemodialisa.heparin_awal,hemodialisa.heparin_continous," +
               "hemodialisa.free_heparin,hemodialisa.lmwh,hemodialisa.volume_priming,hemodialisa.ureum,hemodialisa.hb," +
               "hemodialisa.hbsag,hemodialisa.creatinin,hemodialisa.hiv,hemodialisa.hcv,hemodialisa.lain " +
               "from hemodialisa inner join reg_periksa on hemodialisa.no_rawat=reg_periksa.no_rawat " +
               "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
               "where hemodialisa.tanggal between ? and ? " +
               (TCari.getText().trim().equals("") ? "" : "and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or " +
               "pasien.nm_pasien like ?)") + " order by hemodialisa.tanggal,hemodialisa.jam_mulai";
            ps = koneksi.prepareStatement(query);
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                if (!TCari.getText().trim().equals("")) {
                    ps.setString(3, "%" + TCari.getText().trim() + "%");
                    ps.setString(4, "%" + TCari.getText().trim() + "%");
                    ps.setString(5, "%" + TCari.getText().trim() + "%");
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                        rs.getString("umurdaftar"), rs.getString("sttsumur"), rs.getString("jk"),
                        rs.getString("tgl_lahir"), rs.getString("tanggal"), rs.getString("jam_mulai"),
                        rs.getString("lama"), rs.getString("akses"), rs.getString("keluhan_utama"),
                        rs.getString("penyakit_penyerta"), rs.getString("sled"), rs.getString("dialist"),
                        rs.getString("transfusi"), rs.getString("penarikan"), rs.getString("qb"),
                        rs.getString("qd"), rs.getString("ufg"), rs.getString("heparin_awal"),
                        rs.getString("heparin_continous"), rs.getString("free_heparin"), rs.getString("lmwh"),
                        rs.getString("volume_priming"), rs.getString("ureum"), rs.getString("hb"),
                        rs.getString("hbsag"), rs.getString("creatinin"), rs.getString("hiv"),
                        rs.getString("hcv"), rs.getString("lain"), "", "" // Empty placeholders for NIP and NamaPetugas
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    private void simpan() {
        try {
            ps = koneksi.prepareStatement(
                "insert into hemodialisa values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            try {
                ps.setString(1, TNoRw.getText());
                ps.setString(2, TNoRM.getText()); // no_rkm_medis
                ps.setString(3, Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + JamMulai.getText()); // tanggal as datetime
                ps.setString(4, "-"); // kd_dokter
                ps.setString(5, "-"); // status_rawat
                ps.setString(6, "-"); // dignosa_medik
                ps.setString(7, JamMulai.getText());
                ps.setString(8, Lama.getText());
                ps.setString(9, Akses.getText());
                ps.setString(10, KeluhanUtama.getText());
                ps.setString(11, PenyakitPenyerta.getText());
                ps.setString(12, SLED.getText());
                ps.setString(13, Dialist.getText());
                ps.setString(14, Transfusi.getText());
                ps.setString(15, Penarikan.getText());
                ps.setString(16, QB.getText());
                ps.setString(17, QD.getText());
                ps.setString(18, UFG.getText());
                ps.setString(19, HeparinAwal.getText());
                ps.setString(20, HeparinContinous.getText());
                ps.setString(21, FreeHeparin.getText());
                ps.setString(22, LMWH.getText());
                ps.setString(23, VolumePriming.getText());
                ps.setString(24, Ureum.getText());
                ps.setString(25, HB.getText());
                ps.setString(26, HBsAg.getText());
                ps.setString(27, Creatinin.getText());
                ps.setString(28, HIV.getText());
                ps.setString(29, HCV.getText());
                ps.setString(30, Lain.getText());
                ps.setString(31, "-"); // kd_penyakit
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan..!!");
                emptTeks();
                tampil();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + e.getMessage());
            } finally {
                if (ps != null) ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void hapus() {
        if (JOptionPane.showConfirmDialog(null, "Yakin data akan dihapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                ps = koneksi.prepareStatement("delete from hemodialisa where no_rawat=? and tanggal=? and jam_mulai=?");
                try {
                    ps.setString(1, tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
                    ps.setString(2, tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
                    ps.setString(3, tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus..!!");
                    emptTeks();
                    tampil();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + e.getMessage());
                } finally {
                    if (ps != null) ps.close();
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }

    private void ganti() {
        try {
            ps = koneksi.prepareStatement(
                "update hemodialisa set no_rkm_medis=?, tanggal=?, kd_dokter=?, status_rawat=?, dignosa_medik=?, " +
                "jam_mulai=?, lama=?, akses=?, keluhan_utama=?, penyakit_penyerta=?, sled=?, " +
                "dialist=?, transfusi=?, penarikan=?, qb=?, qd=?, ufg=?, heparin_awal=?, heparin_continous=?, free_heparin=?, " +
                "lmwh=?, volume_priming=?, ureum=?, hb=?, hbsag=?, creatinin=?, hiv=?, hcv=?, lain=?, kd_penyakit=? " +
                "where no_rawat=? and tanggal=?");
            try {
                ps.setString(1, TNoRM.getText());
                ps.setString(2, Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + JamMulai.getText());
                ps.setString(3, "-"); // kd_dokter
                ps.setString(4, "-"); // status_rawat
                ps.setString(5, "-"); // dignosa_medik
                ps.setString(6, JamMulai.getText());
                ps.setString(7, Lama.getText());
                ps.setString(8, Akses.getText());
                ps.setString(9, KeluhanUtama.getText());
                ps.setString(10, PenyakitPenyerta.getText());
                ps.setString(11, SLED.getText());
                ps.setString(12, Dialist.getText());
                ps.setString(13, Transfusi.getText());
                ps.setString(14, Penarikan.getText());
                ps.setString(15, QB.getText());
                ps.setString(16, QD.getText());
                ps.setString(17, UFG.getText());
                ps.setString(18, HeparinAwal.getText());
                ps.setString(19, HeparinContinous.getText());
                ps.setString(20, FreeHeparin.getText());
                ps.setString(21, LMWH.getText());
                ps.setString(22, VolumePriming.getText());
                ps.setString(23, Ureum.getText());
                ps.setString(24, HB.getText());
                ps.setString(25, HBsAg.getText());
                ps.setString(26, Creatinin.getText());
                ps.setString(27, HIV.getText());
                ps.setString(28, HCV.getText());
                ps.setString(29, Lain.getText());
                ps.setString(30, "-"); // kd_penyakit
                ps.setString(31, tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()); // no_rawat in WHERE clause
                ps.setString(32, tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString()); // tanggal in WHERE clause
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil diubah..!!");
                emptTeks();
                tampil();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal mengubah data: " + e.getMessage());
            } finally {
                if (ps != null) ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString() + " " + tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            Valid.SetTgl(Tanggal, tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            JamMulai.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString());
            Lama.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
            Akses.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            PenyakitPenyerta.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            SLED.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
            Dialist.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString());
            Transfusi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString());
            Penarikan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());
            QB.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
            QD.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString());
            UFG.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString());
            HeparinAwal.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 19).toString());
            HeparinContinous.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString());
            FreeHeparin.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 21).toString());
            LMWH.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());
            VolumePriming.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString());
            Ureum.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString());
            HB.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString());
            HBsAg.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 26).toString());
            Creatinin.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 27).toString());
            HIV.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString());
            HCV.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 29).toString());
            Lain.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 30).toString());
            NIP.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 31).toString());
            NamaPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 32).toString());
        }
    }

    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                "select reg_periksa.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur," +
                "pasien.jk,pasien.tgl_lahir from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Umur.setText(rs.getString("umurdaftar") + " " + rs.getString("sttsumur"));
                    JK.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void emptTeks() {
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        Umur.setText("");
        JK.setText("");
        TglLahir.setText("");
        Tanggal.setDate(new Date());
        JamMulai.setText("00:00:00");
        Lama.setText("");
        Akses.setText("");
        KeluhanUtama.setText("");
        PenyakitPenyerta.setText("");
        SLED.setText("");
        Dialist.setText("");
        Transfusi.setText("");
        Penarikan.setText("");
        QB.setText("");
        QD.setText("");
        UFG.setText("");
        HeparinAwal.setText("");
        HeparinContinous.setText("");
        FreeHeparin.setText("");
        LMWH.setText("");
        VolumePriming.setText("");
        Ureum.setText("");
        HB.setText("");
        HBsAg.setText("");
        Creatinin.setText("");
        HIV.setText("");
        HCV.setText("");
        Lain.setText("");
        NIP.setText("");
        NamaPetugas.setText("");
        TNoRw.requestFocus();
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 400));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }
public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm();
    }

public void isCek(){
        BtnSimpan.setEnabled(akses.getdata_hemodialisa());
        BtnHapus.setEnabled(akses.getdata_hemodialisa());
        BtnEdit.setEnabled(akses.getdata_hemodialisa());
        BtnPrint.setEnabled(akses.getdata_hemodialisa()); 
        
    }
    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                JamMulai.setText(Valid.SetTgl3(new Date().toString()));
            }
        };
        new Timer(1000, taskPerformer).start();
    }
}