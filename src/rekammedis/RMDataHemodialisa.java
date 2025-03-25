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
    private int whales23;

    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMDataHemodialisa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);
        setSize(628, 674);

        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "No.R.M.", "Nama Pasien", "Umur", "JK", "Tgl.Lahir", "Tanggal", "Jam Mulai", "QB", "QD",
            "Akses", "Keluhan Utama", "SLED", "Dialist", "Transfusi", "Penarikan", "UFG", "Heparin Awal", "Heparin Continous",
            "Free Heparin", "LMWH", "Volume Priming", "NIP", "Nama Petugas"
        }) {
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 24; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) column.setPreferredWidth(105);
            else if (i == 1) column.setPreferredWidth(65);
            else if (i == 2) column.setPreferredWidth(160);
            else if (i == 3) column.setPreferredWidth(35);
            else if (i == 4) column.setPreferredWidth(20);
            else if (i == 5) column.setPreferredWidth(65);
            else if (i == 6) column.setPreferredWidth(65);
            else if (i == 7) column.setPreferredWidth(60);
            else if (i == 8) column.setPreferredWidth(50);
            else if (i == 9) column.setPreferredWidth(50);
            else if (i == 10) column.setPreferredWidth(90);
            else if (i == 11) column.setPreferredWidth(150);
            else if (i == 12) column.setPreferredWidth(50);
            else if (i == 13) column.setPreferredWidth(90);
            else if (i == 14) column.setPreferredWidth(50);
            else if (i == 15) column.setPreferredWidth(50);
            else if (i == 16) column.setPreferredWidth(50);
            else if (i == 17) column.setPreferredWidth(50);
            else if (i == 18) column.setPreferredWidth(50);
            else if (i == 19) column.setPreferredWidth(50);
            else if (i == 20) column.setPreferredWidth(50);
            else if (i == 21) column.setPreferredWidth(50);
            else if (i == 22) column.setPreferredWidth(90);
            else if (i == 23) column.setPreferredWidth(160);
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        NIP.setDocument(new batasInput((byte)20).getKata(NIP));
        QB.setDocument(new batasInput((byte)5).getKata(QB));
        QD.setDocument(new batasInput((byte)5).getKata(QD));
        Akses.setDocument(new batasInput((byte)30).getKata(Akses));
        KeluhanUtama.setDocument(new batasInput((int)2000).getKata(KeluhanUtama));
        SLED.setDocument(new batasInput((byte)10).getKata(SLED));
        Dialist.setDocument(new batasInput((byte)30).getKata(Dialist));
        Transfusi.setDocument(new batasInput((byte)5).getKata(Transfusi));
        Penarikan.setDocument(new batasInput((byte)5).getKata(Penarikan));
        UFG.setDocument(new batasInput((byte)20).getKata(UFG));
        HeparinAwal.setDocument(new batasInput((byte)10).getKata(HeparinAwal));
        HeparinContinous.setDocument(new batasInput((byte)10).getKata(HeparinContinous));
        FreeHeparin.setDocument(new batasInput((byte)10).getKata(FreeHeparin));
        LMWH.setDocument(new batasInput((byte)10).getKata(LMWH));
        VolumePriming.setDocument(new batasInput((byte)10).getKata(VolumePriming));
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
    private void initComponents() {
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCatatanObservasiHemodialisa = new javax.swing.JMenuItem();
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
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel18 = new widget.Label();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel12 = new widget.Label();
        QB = new widget.TextBox();
        jLabel13 = new widget.Label();
        QD = new widget.TextBox();
        jLabel14 = new widget.Label();
        Akses = new widget.TextBox();
        jLabel15 = new widget.Label();
        KeluhanUtama = new widget.TextBox();
        jLabel17 = new widget.Label();
        SLED = new widget.TextBox();
        jLabel20 = new widget.Label();
        Dialist = new widget.TextBox();
        jLabel23 = new widget.Label();
        Transfusi = new widget.TextBox();
        jLabel24 = new widget.Label();
        Penarikan = new widget.TextBox();
        jLabel26 = new widget.Label();
        UFG = new widget.TextBox();
        jLabel29 = new widget.Label();
        HeparinAwal = new widget.TextBox();
        jLabel30 = new widget.Label();
        HeparinContinous = new widget.TextBox();
        jLabel31 = new widget.Label();
        FreeHeparin = new widget.TextBox();
        jLabel32 = new widget.Label();
        LMWH = new widget.TextBox();
        jLabel33 = new widget.Label();
        VolumePriming = new widget.TextBox();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1");

        MnCatatanObservasiHemodialisa.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanObservasiHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnCatatanObservasiHemodialisa.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanObservasiHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png")));
        MnCatatanObservasiHemodialisa.setText("Formulir Catatan Hemodialisa");
        MnCatatanObservasiHemodialisa.setName("MnCatatanObservasiHemodialisa");
        MnCatatanObservasiHemodialisa.setPreferredSize(new java.awt.Dimension(260, 26));
        MnCatatanObservasiHemodialisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCatatanObservasiHemodialisaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCatatanObservasiHemodialisa);

        JK.setHighlighter(null);
        JK.setName("JK");

        Umur.setHighlighter(null);
        Umur.setName("Umur");

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Catatan Hemodialisa ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50)));
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12));
        internalFrame1.setName("internalFrame1");
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll");
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat");
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

        jPanel3.setName("jPanel3");
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8");
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png")));
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan");
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

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png")));
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal");
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

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png")));
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus");
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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png")));
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit");
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

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png")));
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint");
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
        jLabel7.setName("jLabel7");
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount");
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar");
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

        panelGlass9.setName("panelGlass9");
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19");
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-03-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1");
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21");
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-03-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2");
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6");
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari");
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari");
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png")));
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll");
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

        PanelInput.setName("PanelInput");
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 244));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput");
        FormInput.setPreferredSize(new java.awt.Dimension(100, 225));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4");
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 80, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw");
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien");
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
        Tanggal.setName("Tanggal");
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
        TNoRM.setName("TNoRM");
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16");
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 80, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam");
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(178, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit");
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(243, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik");
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(308, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11));
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian");
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(373, 40, 23, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18");
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP");
        NIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPKeyPressed(evt);
            }
        });
        FormInput.add(NIP);
        NIP.setBounds(474, 40, 94, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas");
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(570, 40, 187, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png")));
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas");
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
        btnPetugas.setBounds(761, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8");
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir");
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel12.setText("QB :");
        jLabel12.setName("jLabel12");
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 70, 80, 23);

        QB.setFocusTraversalPolicyProvider(true);
        QB.setName("QB");
        QB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                QBKeyPressed(evt);
            }
        });
        FormInput.add(QB);
        QB.setBounds(84, 70, 50, 23);

        jLabel13.setText("QD :");
        jLabel13.setName("jLabel13");
        FormInput.add(jLabel13);
        jLabel13.setBounds(138, 70, 40, 23);

        QD.setFocusTraversalPolicyProvider(true);
        QD.setName("QD");
        QD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                QDKeyPressed(evt);
            }
        });
        FormInput.add(QD);
        QD.setBounds(182, 70, 50, 23);

        jLabel14.setText("Akses :");
        jLabel14.setName("jLabel14");
        FormInput.add(jLabel14);
        jLabel14.setBounds(236, 70, 60, 23);

        Akses.setFocusTraversalPolicyProvider(true);
        Akses.setName("Akses");
        Akses.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AksesKeyPressed(evt);
            }
        });
        FormInput.add(Akses);
        Akses.setBounds(300, 70, 90, 23);

        jLabel15.setText("Keluhan :");
        jLabel15.setName("jLabel15");
        FormInput.add(jLabel15);
        jLabel15.setBounds(0, 100, 80, 23);

        KeluhanUtama.setFocusTraversalPolicyProvider(true);
        KeluhanUtama.setName("KeluhanUtama");
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        FormInput.add(KeluhanUtama);
        KeluhanUtama.setBounds(84, 100, 400, 23);

        jLabel17.setText("SLED :");
        jLabel17.setName("jLabel17");
        FormInput.add(jLabel17);
        jLabel17.setBounds(488, 100, 40, 23);

        SLED.setFocusTraversalPolicyProvider(true);
        SLED.setName("SLED");
        SLED.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SLEDKeyPressed(evt);
            }
        });
        FormInput.add(SLED);
        SLED.setBounds(532, 100, 50, 23);

        jLabel20.setText("Dialist :");
        jLabel20.setName("jLabel20");
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 130, 80, 23);

        Dialist.setFocusTraversalPolicyProvider(true);
        Dialist.setName("Dialist");
        Dialist.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DialistKeyPressed(evt);
            }
        });
        FormInput.add(Dialist);
        Dialist.setBounds(84, 130, 90, 23);

        jLabel23.setText("Transfusi :");
        jLabel23.setName("jLabel23");
        FormInput.add(jLabel23);
        jLabel23.setBounds(178, 130, 60, 23);

        Transfusi.setFocusTraversalPolicyProvider(true);
        Transfusi.setName("Transfusi");
        Transfusi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TransfusiKeyPressed(evt);
            }
        });
        FormInput.add(Transfusi);
        Transfusi.setBounds(242, 130, 50, 23);

        jLabel24.setText("Penarikan :");
        jLabel24.setName("jLabel24");
        FormInput.add(jLabel24);
        jLabel24.setBounds(296, 130, 60, 23);

        Penarikan.setFocusTraversalPolicyProvider(true);
        Penarikan.setName("Penarikan");
        Penarikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenarikanKeyPressed(evt);
            }
        });
        FormInput.add(Penarikan);
        Penarikan.setBounds(360, 130, 50, 23);

        jLabel26.setText("UFG :");
        jLabel26.setName("jLabel26");
        FormInput.add(jLabel26);
        jLabel26.setBounds(414, 130, 40, 23);

        UFG.setFocusTraversalPolicyProvider(true);
        UFG.setName("UFG");
        UFG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UFGKeyPressed(evt);
            }
        });
        FormInput.add(UFG);
        UFG.setBounds(458, 130, 50, 23);

        jLabel29.setText("Heparin Awal :");
        jLabel29.setName("jLabel29");
        FormInput.add(jLabel29);
        jLabel29.setBounds(0, 160, 80, 23);

        HeparinAwal.setFocusTraversalPolicyProvider(true);
        HeparinAwal.setName("HeparinAwal");
        HeparinAwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HeparinAwalKeyPressed(evt);
            }
        });
        FormInput.add(HeparinAwal);
        HeparinAwal.setBounds(84, 160, 50, 23);

        jLabel30.setText("Heparin Cont. :");
        jLabel30.setName("jLabel30");
        FormInput.add(jLabel30);
        jLabel30.setBounds(138, 160, 80,  whales23);

        HeparinContinous.setFocusTraversalPolicyProvider(true);
        HeparinContinous.setName("HeparinContinous");
        HeparinContinous.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HeparinContinousKeyPressed(evt);
            }
        });
        FormInput.add(HeparinContinous);
        HeparinContinous.setBounds(222, 160, 50, 23);

        jLabel31.setText("Free Heparin :");
        jLabel31.setName("jLabel31");
        FormInput.add(jLabel31);
        jLabel31.setBounds(276, 160, 80, 23);

        FreeHeparin.setFocusTraversalPolicyProvider(true);
        FreeHeparin.setName("FreeHeparin");
        FreeHeparin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FreeHeparinKeyPressed(evt);
            }
        });
        FormInput.add(FreeHeparin);
        FreeHeparin.setBounds(360, 160, 50, 23);

        jLabel32.setText("LMWH :");
        jLabel32.setName("jLabel32");
        FormInput.add(jLabel32);
        jLabel32.setBounds(414, 160, 40, 23);

        LMWH.setFocusTraversalPolicyProvider(true);
        LMWH.setName("LMWH");
        LMWH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LMWHKeyPressed(evt);
            }
        });
        FormInput.add(LMWH);
        LMWH.setBounds(458, 160, 50, 23);

        jLabel33.setText("Vol. Priming :");
        jLabel33.setName("jLabel33");
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 190, 80, 23);

        VolumePriming.setFocusTraversalPolicyProvider(true);
        VolumePriming.setName("VolumePriming");
        VolumePriming.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VolumePrimingKeyPressed(evt);
            }
        });
        FormInput.add(VolumePriming);
        VolumePriming.setBounds(84, 190, 50, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png")));
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput");
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png")));
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png")));
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png")));
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }

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
                if (Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem()) == true) {
                    simpan();
                }
            } 
        }
    }                                         

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {                                     
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, VolumePriming, BtnBatal);
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
                if (NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString())) {
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
                    if (NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString())) {
                        if (Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString() + " " + tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString(), Sequel.ambiltanggalsekarang()) == true) {
                            if (TanggalRegistrasi.getText().equals("")) {
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?", TNoRw.getText()));
                            }
                            if (Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem()) == true) {
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
                Valid.MyReportqry("rptDataCatatanHemodialisa.jasper", "report", "::~[ Data Catatan Hemodialisa ]::~",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur," +
                    "pasien.jk,pasien.tgl_lahir,hemodialisa.tanggal,hemodialisa.jam_mulai,hemodialisa.qb," +
                    "hemodialisa.qd,hemodialisa.akses,hemodialisa.keluhan_utama,hemodialisa.sled,hemodialisa.dialist," +
                    "hemodialisa.transfusi,hemodialisa.penarikan,hemodialisa.ufg,hemodialisa.heparin_awal,hemodialisa.heparin_continous," +
                    "hemodialisa.free_heparin,hemodialisa.lmwh,hemodialisa.volume_priming,petugas.nip,petugas.nama " +
                    "from hemodialisa inner join reg_periksa on hemodialisa.no_rawat=reg_periksa.no_rawat " +
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                    "inner join petugas on hemodialisa.nip=petugas.nip where " +
                    "hemodialisa.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " +
                    "order by hemodialisa.tanggal,hemodialisa.jam_mulai", param);
            } else {
                Valid.MyReportqry("rptDataCatatanHemodialisa.jasper", "report", "::~[ Data Catatan Hemodialisa ]::~",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur," +
                    "pasien.jk,pasien.tgl_lahir,hemodialisa.tanggal,hemodialisa.jam_mulai,hemodialisa.qb," +
                    "hemodialisa.qd,hemodialisa.akses,hemodialisa.keluhan_utama,hemodialisa.sled,hemodialisa.dialist," +
                    "hemodialisa.transfusi,hemodialisa.penarikan,hemodialisa.ufg,hemodialisa.heparin_awal,hemodialisa.heparin_continous," +
                    "hemodialisa.free_heparin,hemodialisa.lmwh,hemodialisa.volume_priming,petugas.nip,petugas.nama " +
                    "from hemodialisa inner join reg_periksa on hemodialisa.no_rawat=reg_periksa.no_rawat " +
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                    "inner join petugas on hemodialisa.nip=petugas.nip where " +
                    "hemodialisa.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and " +
                    "(reg_periksa.no_rawat like '%" + TCari.getText().trim() + "%' or pasien.no_rkm_medis like '%" + TCari.getText().trim() + "%' or " +
                    "pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or hemodialisa.nip like '%" + TCari.getText().trim() + "%' or petugas.nama like '%" + TCari.getText().trim() + "%') " +
                    "order by hemodialisa.tanggal,hemodialisa.jam_mulai ", param);
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
        Valid.pindah(evt, TCari, Jam);
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

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, Tanggal, Menit);
    }                              

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {                                 
        Valid.pindah(evt, Jam, Detik);
    }                                

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {                                 
        Valid.pindah(evt, Menit, btnPetugas);
    }                                

    private void NIPKeyPressed(java.awt.event.KeyEvent evt) {                               
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            NamaPetugas.setText(petugas.tampil3(NIP.getText()));
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Detik.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            QB.requestFocus();
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
        Valid.pindah(evt, Detik, QB);
    }                                     

    private void MnCatatanObservasiHemodialisaActionPerformed(java.awt.event.ActionEvent evt) {                                                      
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
            Valid.MyReportqry("rptFormulirCatatanHemodialisa.jasper", "report", "::~[ Formulir Catatan Hemodialisa ]::~",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,reg_periksa.tgl_registrasi,reg_periksa.jam_reg," +
                    "pasien.jk,pasien.tgl_lahir,hemodialisa.tanggal,hemodialisa.jam_mulai,hemodialisa.qb," +
                    "hemodialisa.qd,hemodialisa.akses,hemodialisa.keluhan_utama,hemodialisa.sled,hemodialisa.dialist," +
                    "hemodialisa.transfusi,hemodialisa.penarikan,hemodialisa.ufg,hemodialisa.heparin_awal,hemodialisa.heparin_continous," +
                    "hemodialisa.free_heparin,hemodialisa.lmwh,hemodialisa.volume_priming,petugas.nama " +
                    "from hemodialisa inner join reg_periksa on hemodialisa.no_rawat=reg_periksa.no_rawat " +
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                    "inner join petugas on hemodialisa.nip=petugas.nip where reg_periksa.no_rawat='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "' " +
                    "order by hemodialisa.tanggal,hemodialisa.jam_mulai", param);
        }
    }                                                     

    private void QBKeyPressed(java.awt.event.KeyEvent evt) {                              
        Valid.pindah(evt, btnPetugas, QD);
    }                             

    private void QDKeyPressed(java.awt.event.KeyEvent evt) {                              
        Valid.pindah(evt, QB, Akses);
    }                             

    private void AksesKeyPressed(java.awt.event.KeyEvent evt) {                                         
        Valid.pindah(evt, QD, KeluhanUtama);
    }                                        

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {                                       
        Valid.pindah(evt, Akses, SLED);
    }                                      

    private void SLEDKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, KeluhanUtama, Dialist);
    }                              

    private void DialistKeyPressed(java.awt.event.KeyEvent evt) {                               
        Valid.pindah(evt, SLED, Transfusi);
    }                              

    private void TransfusiKeyPressed(java.awt.event.KeyEvent evt) {                                 
        Valid.pindah(evt, Dialist, Penarikan);
    }                                

    private void PenarikanKeyPressed(java.awt.event.KeyEvent evt) {                                
        Valid.pindah(evt, Transfusi, UFG);
    }                               

    private void UFGKeyPressed(java.awt.event.KeyEvent evt) {                                
        Valid.pindah(evt, Penarikan, HeparinAwal);
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
        Valid.pindah(evt, LMWH, BtnSimpan);
    }                              

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

    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.Label LCount;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnCatatanObservasiHemodialisa;
    private widget.TextBox NIP;
    private widget.TextBox NamaPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox QB;
    private widget.TextBox QD;
    private widget.TextBox Akses;
    private widget.TextBox KeluhanUtama;
    private widget.TextBox SLED;
    private widget.TextBox Dialist;
    private widget.TextBox Transfusi;
    private widget.TextBox Penarikan;
    private widget.TextBox UFG;
    private widget.TextBox HeparinAwal;
    private widget.TextBox HeparinContinous;
    private widget.TextBox FreeHeparin;
    private widget.TextBox LMWH;
    private widget.TextBox VolumePriming;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox Umur;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel26;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            if (TCari.getText().trim().equals("")) {
                ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur," +
                    "pasien.jk,pasien.tgl_lahir,hemodialisa.tanggal,hemodialisa.jam_mulai,hemodialisa.qb," +
                    "hemodialisa.qd,hemodialisa.akses,hemodialisa.keluhan_utama,hemodialisa.sled,hemodialisa.dialist," +
                    "hemodialisa.transfusi,hemodialisa.penarikan,hemodialisa.ufg,hemodialisa.heparin_awal,hemodialisa.heparin_continous," +
                    "hemodialisa.free_heparin,hemodialisa.lmwh,hemodialisa.volume_priming,petugas.nip,petugas.nama " +
                    "from hemodialisa inner join reg_periksa on hemodialisa.no_rawat=reg_periksa.no_rawat " +
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                    "inner join petugas on hemodialisa.nip=petugas.nip where " +
                    "hemodialisa.tanggal between ? and ? order by hemodialisa.tanggal,hemodialisa.jam_mulai");
            } else {
                ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur," +
                    "pasien.jk,pasien.tgl_lahir,hemodialisa.tanggal,hemodialisa.jam_mulai,hemodialisa.qb," +
                    "hemodialisa.qd,hemodialisa.akses,hemodialisa.keluhan_utama,hemodialisa.sled,hemodialisa.dialist," +
                    "hemodialisa.transfusi,hemodialisa.penarikan,hemodialisa.ufg,hemodialisa.heparin_awal,hemodialisa.heparin_continous," +
                    "hemodialisa.free_heparin,hemodialisa.lmwh,hemodialisa.volume_priming,petugas.nip,petugas.nama " +
                    "from hemodialisa inner join reg_periksa on hemodialisa.no_rawat=reg_periksa.no_rawat " +
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                    "inner join petugas on hemodialisa.nip=petugas.nip where " +
                    "hemodialisa.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or hemodialisa.nip like ? or petugas.nama like ?) " +
                    "order by hemodialisa.tanggal,hemodialisa.jam_mulai");
            }
            try {
                if (TCari.getText().trim().equals("")) {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                } else {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                    ps.setString(3, "%" + TCari.getText() + "%");
                    ps.setString(4, "%" + TCari.getText() + "%");
                    ps.setString(5, "%" + TCari.getText() + "%");
                    ps.setString(6, "%" + TCari.getText() + "%");
                    ps.setString(7, "%" + TCari.getText() + "%");
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),
                        rs.getString("umurdaftar") + " " + rs.getString("sttsumur"),
                        rs.getString("jk"),
                        rs.getString("tgl_lahir"),
                        rs.getString("tanggal"),
                        rs.getString("jam_mulai"),
                        rs.getString("qb"),
                        rs.getString("qd"),
                        rs.getString("akses"),
                        rs.getString("keluhan_utama"),
                        rs.getString("sled"),
                        rs.getString("dialist"),
                        rs.getString("transfusi"),
                        rs.getString("penarikan"),
                        rs.getString("ufg"),
                        rs.getString("heparin_awal"),
                        rs.getString("heparin_continous"),
                        rs.getString("free_heparin"),
                        rs.getString("lmwh"),
                        rs.getString("volume_priming"),
                        rs.getString("nip"),
                        rs.getString("nama")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            e.printStackTrace();
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    public void emptTeks() {
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        TglLahir.setText("");
        Umur.setText("");
        JK.setText("");
        QB.setText("");
        QD.setText("");
        Akses.setText("");
        KeluhanUtama.setText("");
        SLED.setText("");
        Dialist.setText("");
        Transfusi.setText("");
        Penarikan.setText("");
        UFG.setText("");
        HeparinAwal.setText("");
        HeparinContinous.setText("");
        FreeHeparin.setText("");
        LMWH.setText("");
        VolumePriming.setText("");
        NIP.setText("");
        NamaPetugas.setText("");
        Tanggal.setDate(new Date());
        Tanggal.requestFocus();
    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            Valid.SetTgl(Tanggal, tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            String[] jamMulai = tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString().split(":");
            Jam.setSelectedItem(jamMulai[0]);
            Menit.setSelectedItem(jamMulai[1]);
            Detik.setSelectedItem(jamMulai[2]);
            QB.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
            QD.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());
            Akses.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            SLED.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
            Dialist.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString());
            Transfusi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString());
            Penarikan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());
            UFG.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
            HeparinAwal.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString());
            HeparinContinous.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString());
            FreeHeparin.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 19).toString());
            LMWH.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString());
            VolumePriming.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 21).toString());
            NIP.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());
            NamaPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString());
        }
    }

    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                "select reg_periksa.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur," +
                "pasien.jk,pasien.tgl_lahir from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "where reg_periksa.no_rawat=?");
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
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            }
        }
    }

    private void simpan() {
        if (Sequel.menyimpantf("hemodialisa", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat dan Tanggal", 19, new String[]{
                TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), 
                Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
                QB.getText(), QD.getText(), Akses.getText(), KeluhanUtama.getText(), SLED.getText(), Dialist.getText(),
                Transfusi.getText(), Penarikan.getText(), UFG.getText(), HeparinAwal.getText(), HeparinContinous.getText(),
                FreeHeparin.getText(), LMWH.getText(), VolumePriming.getText(), NIP.getText(), "Belum Tercatat"
            }) == true) {
            tabMode.addRow(new String[]{
                TNoRw.getText(), TNoRM.getText(), TPasien.getText(), Umur.getText(), JK.getText(), TglLahir.getText(),
                Valid.SetTgl(Tanggal.getSelectedItem() + ""), Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
                QB.getText(), QD.getText(), Akses.getText(), KeluhanUtama.getText(), SLED.getText(), Dialist.getText(),
                Transfusi.getText(), Penarikan.getText(), UFG.getText(), HeparinAwal.getText(), HeparinContinous.getText(),
                FreeHeparin.getText(), LMWH.getText(), VolumePriming.getText(), NIP.getText(), NamaPetugas.getText()
            });
            emptTeks();
            LCount.setText("" + tabMode.getRowCount());
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data!");
        }
    }

    private void hapus() {
        if (Sequel.queryu2tf("delete from hemodialisa where no_rawat=? and tanggal=? and jam_mulai=?", 3, new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString()}) == true) {
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText("" + tabMode.getRowCount());
            emptTeks();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data!");
        }
    }

    private void ganti() {
        if (Sequel.mengedittf("hemodialisa", "no_rawat=? and tanggal=? and jam_mulai=?", 
                "no_rawat=?,tanggal=?,jam_mulai=?,qb=?,qd=?,akses=?,keluhan_utama=?,sled=?,dialist=?,transfusi=?,penarikan=?,ufg=?,heparin_awal=?,heparin_continous=?,free_heparin=?,lmwh=?,volume_priming=?,nip=?,status='Belum Tercatat'", 
                22, new String[]{
                    TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), 
                    Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
                    QB.getText(), QD.getText(), Akses.getText(), KeluhanUtama.getText(), SLED.getText(), Dialist.getText(),
                    Transfusi.getText(), Penarikan.getText(), UFG.getText(), HeparinAwal.getText(), HeparinContinous.getText(),
                    FreeHeparin.getText(), LMWH.getText(), VolumePriming.getText(), NIP.getText(),
                    tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString(),
                    tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString()
                }) == true) {
            tbObat.setValueAt(TNoRw.getText(), tbObat.getSelectedRow(), 0);
            tbObat.setValueAt(TNoRM.getText(), tbObat.getSelectedRow(), 1);
            tbObat.setValueAt(TPasien.getText(), tbObat.getSelectedRow(), 2);
            tbObat.setValueAt(Umur.getText(), tbObat.getSelectedRow(), 3);
            tbObat.setValueAt(JK.getText(), tbObat.getSelectedRow(), 4);
            tbObat.setValueAt(TglLahir.getText(), tbObat.getSelectedRow(), 5);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem() + ""), tbObat.getSelectedRow(), 6);
            tbObat.setValueAt(Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(), tbObat.getSelectedRow(), 7);
            tbObat.setValueAt(QB.getText(), tbObat.getSelectedRow(), 8);
            tbObat.setValueAt(QD.getText(), tbObat.getSelectedRow(), 9);
            tbObat.setValueAt(Akses.getText(), tbObat.getSelectedRow(), 10);
            tbObat.setValueAt(KeluhanUtama.getText(), tbObat.getSelectedRow(), 11);
            tbObat.setValueAt(SLED.getText(), tbObat.getSelectedRow(), 12);
            tbObat.setValueAt(Dialist.getText(), tbObat.getSelectedRow(), 13);
            tbObat.setValueAt(Transfusi.getText(), tbObat.getSelectedRow(), 14);
            tbObat.setValueAt(Penarikan.getText(), tbObat.getSelectedRow(), 15);
            tbObat.setValueAt(UFG.getText(), tbObat.getSelectedRow(), 16);
            tbObat.setValueAt(HeparinAwal.getText(), tbObat.getSelectedRow(), 17);
            tbObat.setValueAt(HeparinContinous.getText(), tbObat.getSelectedRow(), 18);
            tbObat.setValueAt(FreeHeparin.getText(), tbObat.getSelectedRow(), 19);
            tbObat.setValueAt(LMWH.getText(), tbObat.getSelectedRow(), 20);
            tbObat.setValueAt(VolumePriming.getText(), tbObat.getSelectedRow(), 21);
            tbObat.setValueAt(NIP.getText(), tbObat.getSelectedRow(), 22);
            tbObat.setValueAt(NamaPetugas.getText(), tbObat.getSelectedRow(), 23);
            emptTeks();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal mengedit data!");
        }
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(192, 244));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(192, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String nol_jam = "", nol_menit = "", nol_detik = "";
                Date now = Calendar.getInstance().getTime();
                int jam = now.getHours();
                int menit = now.getMinutes();
                int detik = now.getSeconds();

                if (jam <= 9) nol_jam = "0";
                if (menit <= 9) nol_menit = "0";
                if (detik <= 9) nol_detik = "0";

                String time = nol_jam + jam + ":" + nol_menit + menit + ":" + nol_detik + detik;
                Jam.setSelectedItem(jam);
                Menit.setSelectedItem(menit);
                Detik.setSelectedItem(detik);
            }
        };
        new Timer(1000, taskPerformer).start();
    }

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm();
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.gethemodialisa());
        BtnHapus.setEnabled(akses.gethemodialisa());
        BtnEdit.setEnabled(akses.gethemodialisa());
        BtnPrint.setEnabled(akses.gethemodialisa());
    }
}