/*
 * Refactored for Surat Pernyataan Pengambilan Sample SHK
 * - Adapted GUI and Logic for SHK Sample Statement
 * - Implemented new isRawat to fetch baby and parent data
 * - Uses surat_pernyataan_shk and related tables
 * - Strictly uses database date for TglLahirBayi, avoids defaulting to current date.
 * - Removed default date setting in emptTeks for TglLahirBayi.
 * - Corrected date formatting for JTable updates using DIRECT SimpleDateFormat("dd-MM-yyyy").
 * - Temporarily disabled WarnaTable for debugging date format issues.
 */

 package surat; // Sesuaikan dengan package Anda

 import fungsi.WarnaTable;
 import fungsi.batasInput;
 import fungsi.koneksiDB;
 import fungsi.sekuel;
 import fungsi.validasi;
 import fungsi.akses;
 import java.awt.Cursor;
 import java.awt.Dimension;
 import java.awt.event.KeyEvent;
 import java.awt.event.WindowEvent;
 import java.awt.event.WindowListener;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException; // Import SQLException
 import java.text.ParseException; // Import ParseException
 import java.text.SimpleDateFormat; // Import SimpleDateFormat
 import java.util.Date;
 import java.util.HashMap;
 import java.util.Map;
 import javax.swing.JOptionPane;
 import javax.swing.JTable;
 import javax.swing.event.DocumentEvent;
 import javax.swing.table.DefaultTableModel;
 import javax.swing.table.TableColumn;
 import javax.swing.text.Document;
 import javax.swing.text.html.HTMLEditorKit;
 import javax.swing.text.html.StyleSheet;
 import kepegawaian.DlgCariPetugas;
 // import kepegawaian.DlgCariDokter; // Tidak perlu dokter lagi

 /**
  *
  * @author windiartohugroho (adapted) & AI Assistant
  */
 public final class SuratPernyataanPengambilanSampleSHK extends javax.swing.JDialog {
     private final DefaultTableModel tabMode;
     private Connection koneksi=koneksiDB.condb();
     private sekuel Sequel=new sekuel();
     private validasi Valid=new validasi(); // Masih digunakan untuk hal lain
     private PreparedStatement ps, psPasienBayi;
     private ResultSet rs, rsPasienBayi;
     private int i=0;
     private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
     private String finger="", lokasifile="";
     private String TglLahirBayiDB = ""; // Helper untuk menyimpan tanggal lahir dari DB
     private String JamLahirBayiDB = ""; // Helper untuk menyimpan jam lahir dari DB
     // Format tanggal standar database (YYYY-MM-DD)
     private final SimpleDateFormat sdfDb = new SimpleDateFormat("yyyy-MM-dd");
     // Format tanggal tampilan (DD-MM-YYYY) <-- TAMBAHKAN INI
     private final SimpleDateFormat sdfDisplay = new SimpleDateFormat("dd-MM-yyyy");


     public SuratPernyataanPengambilanSampleSHK(java.awt.Frame parent, boolean modal) {
         super(parent, modal);
         initComponents();
         this.setLocation(8,1);
         setSize(1100, 550); // Sesuaikan ukuran jika perlu

          // ---> ADD THIS BLOCK <---
    java.awt.Color defaultForeground = javax.swing.UIManager.getColor("ComboBox.foreground");
    if (defaultForeground == null) {
        defaultForeground = java.awt.Color.BLACK; // Fallback to black if LaF doesn't provide one
    }
    cmbJamSample.setForeground(java.awt.Color.BLACK);
    cmbMntSample.setForeground(java.awt.Color.BLACK);
    cmbDtkSample.setForeground(java.awt.Color.BLACK);
    cmbJamLahir.setForeground(java.awt.Color.BLACK);
    cmbMntLahir.setForeground(java.awt.Color.BLACK);
    cmbDtkLahir.setForeground(java.awt.Color.BLACK);
    // ...
    // ---> END OF ADDED BLOCK <---

         // Table Model untuk Surat Pernyataan SHK
         tabMode=new DefaultTableModel(null,new Object[]{
             "No. Pernyataan","No. Rawat","No. R.M.","Nama Bayi","Tgl. Lahir Bayi","Jam Lahir", "Nama Ibu", "Nama Ayah",
             "Alamat Ortu", "No. Telp", "Tgl. Pernyataan","Tgl. Sampel", "Jam Sampel", "Tempat Sampel",
             "NIP Petugas","Nama Petugas"
         }){
               @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
         };
         tbSurat.setModel(tabMode);

         tbSurat.setPreferredScrollableViewportSize(new Dimension(500,500));
         tbSurat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

         // Column widths
         for (i = 0; i < 16; i++) {
             TableColumn column = tbSurat.getColumnModel().getColumn(i);
             if(i==0){ column.setPreferredWidth(115); } else if(i==1){ column.setPreferredWidth(105); } else if(i==2){ column.setPreferredWidth(70);  } else if(i==3){ column.setPreferredWidth(170); } else if(i==4){ column.setPreferredWidth(85);  } else if(i==5){ column.setPreferredWidth(65);  } else if(i==6){ column.setPreferredWidth(170); } else if(i==7){ column.setPreferredWidth(170); } else if(i==8){ column.setPreferredWidth(250); } else if(i==9){ column.setPreferredWidth(90);  } else if(i==10){ column.setPreferredWidth(90); } else if(i==11){ column.setPreferredWidth(85); } else if(i==12){ column.setPreferredWidth(70); } else if(i==13){ column.setPreferredWidth(150); } else if(i==14){ column.setPreferredWidth(100); } else if(i==15){ column.setPreferredWidth(170); }
         }
         // HAPUS SEMENTARA UNTUK DEBUGGING - JIKA FORMAT BENAR, MASALAH ADA DI WarnaTable
         // tbSurat.setDefaultRenderer(Object.class, new WarnaTable()); // <<<<<<=========== KOMENTARI BARIS INI

         // Input limits
         TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
         NoPernyataan.setDocument(new batasInput((byte)25).getKata(NoPernyataan));
         NIP.setDocument(new batasInput((byte)20).getKata(NIP));
         TCari.setDocument(new batasInput((int)100).getKata(TCari));
         TempatPengambilan.setDocument(new batasInput((byte)100).getKata(TempatPengambilan));
         NoTelp.setDocument(new batasInput((byte)30).getOnlyAngka(NoTelp));

         if(koneksiDB.CARICEPAT().equals("aktif")){
             TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                 @Override public void insertUpdate(DocumentEvent e) { if(TCari.getText().length()>2){ tampil(); } }
                 @Override public void removeUpdate(DocumentEvent e) { if(TCari.getText().length()>2){ tampil(); } }
                 @Override public void changedUpdate(DocumentEvent e) { if(TCari.getText().length()>2){ tampil(); } }
             });
         }

         // Listener Petugas
          petugas.addWindowListener(new WindowListener() {
             @Override public void windowOpened(WindowEvent e) {}
             @Override public void windowClosing(WindowEvent e) {}
             @Override public void windowClosed(WindowEvent e) {
                 if(petugas.getTable().getSelectedRow()!= -1){
                     NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                     NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                     NIP.requestFocus();
                 }
             }
             @Override public void windowIconified(WindowEvent e) {}
             @Override public void windowDeiconified(WindowEvent e) {}
             @Override public void windowActivated(WindowEvent e) {}
             @Override public void windowDeactivated(WindowEvent e) {}
         });

         // Inisialisasi ComboBox Waktu
         Valid.LoadTahun(cmbJamLahir); Valid.LoadTahun(cmbMntLahir); Valid.LoadTahun(cmbDtkLahir);
         Valid.LoadTahun(cmbJamSample); Valid.LoadTahun(cmbMntSample); Valid.LoadTahun(cmbDtkSample);
         for(i=0;i<24;i++){ cmbJamLahir.addItem(String.format("%02d", i)); cmbJamSample.addItem(String.format("%02d", i)); }
         for(i=0;i<60;i++){ cmbMntLahir.addItem(String.format("%02d", i)); cmbDtkLahir.addItem(String.format("%02d", i)); cmbMntSample.addItem(String.format("%02d", i)); cmbDtkSample.addItem(String.format("%02d", i)); }
         cmbJamLahir.setSelectedItem("00"); cmbMntLahir.setSelectedItem("00"); cmbDtkLahir.setSelectedItem("00");
         cmbJamSample.setSelectedItem("00"); cmbMntSample.setSelectedItem("00"); cmbDtkSample.setSelectedItem("00");

         ChkInput.setSelected(false); isForm();
         ChkAccor.setSelected(false); isPhoto();

         // HTML Editor Kit Setup
         HTMLEditorKit kit = new HTMLEditorKit();
         LoadHTML2.setEditable(true); LoadHTML2.setEditorKit(kit);
         StyleSheet styleSheet = kit.getStyleSheet();
         styleSheet.addRule(".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;} .isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;} .isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;} .isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;} .isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;} .isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;} .isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;} .isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;} .isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}");
         Document doc = kit.createDefaultDocument(); LoadHTML2.setDocument(doc);
     }


     /** This method is called from within the constructor to
      * initialize the form.
      * WARNING: Do NOT modify this code. The content of this method is
      * always regenerated by the Form Editor.
      */
     @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbSurat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel16 = new widget.Label();
        TglPernyataan = new widget.Tanggal();
        jLabel3 = new widget.Label();
        NoPernyataan = new widget.TextBox();
        jLabel18 = new widget.Label();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel20 = new widget.Label();
        TglLahirBayi = new widget.Tanggal();
        jLabel24 = new widget.Label();
        cmbJamLahir = new widget.ComboBox();
        jLabel25 = new widget.Label();
        cmbMntLahir = new widget.ComboBox();
        jLabel26 = new widget.Label();
        cmbDtkLahir = new widget.ComboBox();
        jLabel27 = new widget.Label();
        NamaIbu = new widget.TextBox();
        jLabel28 = new widget.Label();
        NamaAyah = new widget.TextBox();
        jLabel29 = new widget.Label();
        AlamatOrtu = new widget.TextBox();
        jLabel30 = new widget.Label();
        NoTelp = new widget.TextBox();
        jLabel31 = new widget.Label();
        TglPengambilan = new widget.Tanggal();
        jLabel32 = new widget.Label();
        cmbJamSample = new widget.ComboBox();
        jLabel33 = new widget.Label();
        cmbMntSample = new widget.ComboBox();
        jLabel34 = new widget.Label();
        cmbDtkSample = new widget.ComboBox();
        jLabel35 = new widget.Label();
        TempatPengambilan = new widget.TextBox();
        ChkInput = new widget.CekBox();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        BtnPrint1 = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Surat Pernyataan Pengambilan Sampel SHK ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbSurat.setAutoCreateRowSorter(true);
        tbSurat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbSurat.setName("tbSurat"); // NOI18N
        tbSurat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSuratMouseClicked(evt);
            }
        });
        tbSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbSuratKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbSurat);

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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass8.add(BtnAll);

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

        jLabel19.setText("Tgl. Pernyataan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-05-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-05-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 270));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 250));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 100, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(104, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(453, 10, 290, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(250, 10, 111, 23);

        jLabel17.setText("Nama Bayi :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(jLabel17);
        jLabel17.setBounds(370, 10, 70, 23);

        jLabel16.setText("Tgl. Pernyataan :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(100, 23));
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 130, 100, 23);

        TglPernyataan.setForeground(new java.awt.Color(50, 70, 50));
        TglPernyataan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-05-2025" }));
        TglPernyataan.setDisplayFormat("dd-MM-yyyy");
        TglPernyataan.setName("TglPernyataan"); // NOI18N
        TglPernyataan.setOpaque(false);
        TglPernyataan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglPernyataanKeyPressed(evt);
            }
        });
        FormInput.add(TglPernyataan);
        TglPernyataan.setBounds(104, 130, 90, 23);

        jLabel3.setText("No. Pernyataan :");
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(jLabel3);
        jLabel3.setBounds(430, 130, 100, 23);

        NoPernyataan.setHighlighter(null);
        NoPernyataan.setName("NoPernyataan"); // NOI18N
        NoPernyataan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPernyataanKeyPressed(evt);
            }
        });
        FormInput.add(NoPernyataan);
        NoPernyataan.setBounds(534, 130, 209, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 220, 100, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        FormInput.add(NIP);
        NIP.setBounds(104, 220, 100, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(206, 220, 185, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("ALt+2");
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
        btnPetugas.setBounds(393, 220, 28, 23);

        jLabel20.setText("Tgl. Lahir Bayi :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 40, 100, 23);

        TglLahirBayi.setForeground(new java.awt.Color(50, 70, 50));
        TglLahirBayi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-05-2025" }));
        TglLahirBayi.setDisplayFormat("dd-MM-yyyy");
        TglLahirBayi.setEnabled(false);
        TglLahirBayi.setName("TglLahirBayi"); // NOI18N
        TglLahirBayi.setOpaque(false);
        FormInput.add(TglLahirBayi);
        TglLahirBayi.setBounds(104, 40, 90, 23);

        jLabel24.setText("Jam Lahir :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(200, 40, 70, 23);

        cmbJamLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00" }));
        cmbJamLahir.setEnabled(false);
        cmbJamLahir.setName("cmbJamLahir"); // NOI18N
        FormInput.add(cmbJamLahir);
        cmbJamLahir.setBounds(274, 40, 45, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText(":");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(319, 40, 10, 23);

        cmbMntLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00" }));
        cmbMntLahir.setEnabled(false);
        cmbMntLahir.setName("cmbMntLahir"); // NOI18N
        FormInput.add(cmbMntLahir);
        cmbMntLahir.setBounds(329, 40, 45, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText(":");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(374, 40, 10, 23);

        cmbDtkLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00" }));
        cmbDtkLahir.setEnabled(false);
        cmbDtkLahir.setName("cmbDtkLahir"); // NOI18N
        FormInput.add(cmbDtkLahir);
        cmbDtkLahir.setBounds(384, 40, 45, 23);

        jLabel27.setText("Nama Ibu :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 70, 100, 23);

        NamaIbu.setEditable(true);
        NamaIbu.setHighlighter(null);
        NamaIbu.setName("NamaIbu"); // NOI18N
        FormInput.add(NamaIbu);
        NamaIbu.setBounds(104, 70, 290, 23);

        jLabel28.setText("Nama Ayah :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(430, 70, 100, 23);

        NamaAyah.setEditable(true);
        NamaAyah.setHighlighter(null);
        NamaAyah.setName("NamaAyah"); // NOI18N
        FormInput.add(NamaAyah);
        NamaAyah.setBounds(534, 70, 209, 23);

        jLabel29.setText("Alamat Ortu :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(0, 100, 100, 23);

        AlamatOrtu.setEditable(true);
        AlamatOrtu.setHighlighter(null);
        AlamatOrtu.setName("AlamatOrtu"); // NOI18N
        FormInput.add(AlamatOrtu);
        AlamatOrtu.setBounds(104, 100, 639, 23);

        jLabel30.setText("No. Telp Ortu :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(430, 40, 100, 23);

        NoTelp.setEditable(true);
        NoTelp.setHighlighter(null);
        NoTelp.setName("NoTelp"); // NOI18N
        FormInput.add(NoTelp);
        NoTelp.setBounds(534, 40, 209, 23);

        jLabel31.setText("Tgl. Sampel :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(0, 160, 100, 23);

        TglPengambilan.setForeground(new java.awt.Color(50, 70, 50));
        TglPengambilan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-05-2025" }));
        TglPengambilan.setDisplayFormat("dd-MM-yyyy");
        TglPengambilan.setName("TglPengambilan"); // NOI18N
        TglPengambilan.setOpaque(false);
        TglPengambilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglPengambilanKeyPressed(evt);
            }
        });
        FormInput.add(TglPengambilan);
        TglPengambilan.setBounds(104, 160, 90, 23);

        jLabel32.setText("Jam Sampel :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(200, 160, 70, 23);

        cmbJamSample.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00" }));
        cmbJamSample.setName("cmbJamSample"); // NOI18N
        cmbJamSample.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamSampleKeyPressed(evt);
            }
        });
        FormInput.add(cmbJamSample);
        cmbJamSample.setBounds(274, 160, 65, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText(":");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(344, 160, 10, 23);

        cmbMntSample.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00" }));
        cmbMntSample.setName("cmbMntSample"); // NOI18N
        cmbMntSample.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntSampleKeyPressed(evt);
            }
        });
        FormInput.add(cmbMntSample);
        cmbMntSample.setBounds(359, 160, 65, 23); // Beri lebar sama

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText(":");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(429, 160, 10, 23);

        cmbDtkSample.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00" }));
        cmbDtkSample.setName("cmbDtkSample"); // NOI18N
        cmbDtkSample.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkSampleKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtkSample);
        cmbDtkSample.setBounds(444, 160, 65, 23); // Beri lebar sama

        jLabel35.setText("Tempat Sampel :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(0, 190, 100, 23);

        TempatPengambilan.setHighlighter(null);
        TempatPengambilan.setName("TempatPengambilan"); // NOI18N
        TempatPengambilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TempatPengambilanKeyPressed(evt);
            }
        });
        FormInput.add(TempatPengambilan);
        TempatPengambilan.setBounds(104, 190, 639, 23);

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

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(480, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Bukti/Scan Surat Pernyataan SHK : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass3.setBackground(new java.awt.Color(255, 255, 255));
        FormPass3.setBorder(null);
        FormPass3.setName("FormPass3"); // NOI18N
        FormPass3.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbil.setMnemonic('U');
        btnAmbil.setText("Ambil");
        btnAmbil.setToolTipText("Alt+U");
        btnAmbil.setName("btnAmbil"); // NOI18N
        btnAmbil.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilActionPerformed(evt);
            }
        });
        FormPass3.add(btnAmbil);

        BtnRefreshPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto1.setMnemonic('R');
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Alt+R");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnRefreshPhoto1);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnPrint1.setMnemonic('P');
        BtnPrint1.setText("View");
        BtnPrint1.setToolTipText("Alt+P");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnPrint1);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        FormPhoto.add(Scroll5, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //<editor-fold defaultstate="collapsed" desc="Event Handlers">
    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
          // Validation
         if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
             Valid.textKosong(TNoRw,"Pasien Bayi");
         }else if(NoPernyataan.getText().trim().equals("")){
             Valid.textKosong(NoPernyataan,"No. Pernyataan");
         }else if(TempatPengambilan.getText().trim().equals("")){
             Valid.textKosong(TempatPengambilan,"Tempat Pengambilan Sampel");
         }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
             Valid.textKosong(btnPetugas,"Petugas");
         } else if(NamaIbu.getText().trim().equals("") && NamaAyah.getText().trim().equals("")){
             Valid.textKosong(TNoRw,"Data Orang Tua (Ibu/Ayah) dari Pasien belum terisi");
         } else {
             // INSERT ke tabel surat_pernyataan_shk
             String jamSample = getWaktuSampleFromComboBox(); // Pakai helper
             if(Sequel.menyimpantf("surat_pernyataan_shk","?,?,?,?,?,?,?,?,?,?,?","Data",11,new String[]{ // 11 kolom
                     NoPernyataan.getText(), TNoRw.getText(), Valid.SetTgl(TglPernyataan.getSelectedItem()+""),
                     Valid.SetTgl(TglPengambilan.getSelectedItem()+""), jamSample, TempatPengambilan.getText(),
                     NIP.getText(), NamaIbu.getText(), NamaAyah.getText(), AlamatOrtu.getText(), NoTelp.getText()
                 })==true){

                 // --- BAGIAN KRITIS: Format tanggal untuk tabel GUI ---
                 String tglLahirTableStr = "";
                 Date tglLahirDate = TglLahirBayi.getDate(); // 1. Ambil objek Date
                 if (tglLahirDate != null) {
                     try {
                         // String tglDbFormat = sdfDb.format(tglLahirDate); // 2. Format ke YYYY-MM-DD (Tidak perlu jika Valid.SetTgl menerima Date)
                         // tglLahirTableStr = Valid.SetTgl(tglDbFormat); // 3. Format ke DD-MM-YYYY untuk tabel
                         tglLahirTableStr = sdfDisplay.format(tglLahirDate); // << LANGSUNG FORMAT KE DISPLAY
                     } catch (Exception e) {
                         System.err.println("Error formatting TglLahirBayi for table: " + e);
                         tglLahirTableStr = "ERROR"; // Atau kosongkan
                     }
                 }

                 String tglPernyataanTableStr = "";
                 Date tglPernyataanDate = TglPernyataan.getDate();
                 if (tglPernyataanDate != null) {
                    try {
                         // String tglDbFormat = sdfDb.format(tglPernyataanDate);
                         // tglPernyataanTableStr = Valid.SetTgl(tglDbFormat);
                         tglPernyataanTableStr = sdfDisplay.format(tglPernyataanDate); // << LANGSUNG FORMAT KE DISPLAY
                    } catch (Exception e) { tglPernyataanTableStr = "ERROR"; }
                 }

                 String tglPengambilanTableStr = "";
                 Date tglPengambilanDate = TglPengambilan.getDate();
                 if (tglPengambilanDate != null) {
                     try {
                         // String tglDbFormat = sdfDb.format(tglPengambilanDate);
                         // tglPengambilanTableStr = Valid.SetTgl(tglDbFormat);
                          tglPengambilanTableStr = sdfDisplay.format(tglPengambilanDate); // << LANGSUNG FORMAT KE DISPLAY
                     } catch (Exception e) { tglPengambilanTableStr = "ERROR"; }
                 }
                 // --- AKHIR BAGIAN KRITIS ---

                 // AddRow ke tabel GUI menggunakan string yang SUDAH DIFORMAT BENAR
                  tabMode.addRow(new String[]{
                     NoPernyataan.getText(), TNoRw.getText(), TNoRM.getText(), TPasien.getText(),
                     tglLahirTableStr, // << Gunakan hasil format
                     (JamLahirBayiDB != null && !JamLahirBayiDB.isEmpty() ? JamLahirBayiDB.substring(0, 5) : "00:00"),
                     NamaIbu.getText(), NamaAyah.getText(), AlamatOrtu.getText(), NoTelp.getText(),
                     tglPernyataanTableStr, // << Gunakan hasil format
                     tglPengambilanTableStr, // << Gunakan hasil format
                     jamSample.substring(0, 5), // Jam Sample (HH:MM)
                     TempatPengambilan.getText(),
                     NIP.getText(), NamaPetugas.getText()
                 });
                  LCount.setText(""+tabMode.getRowCount());
                  emptTeks();
             }
         }
     }//GEN-LAST:event_BtnSimpanActionPerformed

     private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
         // Navigasi
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
             BtnSimpanActionPerformed(null);
         }else{
             Valid.pindah(evt,btnPetugas,BtnBatal);
         }
     }//GEN-LAST:event_BtnSimpanKeyPressed

     private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
         emptTeks();
         ChkInput.setSelected(true);
         isForm();
     }//GEN-LAST:event_BtnBatalActionPerformed

     private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
             emptTeks();
         }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
     }//GEN-LAST:event_BtnBatalKeyPressed

     private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
         // Hapus dari surat_pernyataan_shk (index NIP = 14)
         if(tbSurat.getSelectedRow()>-1){
             if(akses.getkode().equals("Admin Utama")){
                 hapus();
             }else{
                 if(NIP.getText().equals(tbSurat.getValueAt(tbSurat.getSelectedRow(), 14).toString())){ // Index NIP = 14
                     hapus();
                 }else{
                     JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas ("+tbSurat.getValueAt(tbSurat.getSelectedRow(), 15).toString()+") yang membuat surat ini!"); // Index Nama Petugas = 15
                 }
             }
         }else{
             JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
         }
     }//GEN-LAST:event_BtnHapusActionPerformed

     private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
             BtnHapusActionPerformed(null);
         }else{
             Valid.pindah(evt, BtnBatal, BtnEdit);
         }
     }//GEN-LAST:event_BtnHapusKeyPressed

     private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
         // Validation
         if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
             Valid.textKosong(TNoRw,"Pasien Bayi");
         }else if(NoPernyataan.getText().trim().equals("")){
             Valid.textKosong(NoPernyataan,"No. Pernyataan");
         }else if(TempatPengambilan.getText().trim().equals("")){
             Valid.textKosong(TempatPengambilan,"Tempat Pengambilan Sampel");
         }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
             Valid.textKosong(btnPetugas,"Petugas");
         }else if(NamaIbu.getText().trim().equals("") && NamaAyah.getText().trim().equals("")){
             Valid.textKosong(TNoRw,"Data Orang Tua (Ibu/Ayah) dari Pasien belum terisi");
         }else{
             if(tbSurat.getSelectedRow()>-1){
                 if(akses.getkode().equals("Admin Utama")){
                     ganti();
                 }else{
                     // Cek NIP (index 14)
                     if(NIP.getText().equals(tbSurat.getValueAt(tbSurat.getSelectedRow(),14).toString())){
                         ganti();
                     }else{
                         JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas ("+tbSurat.getValueAt(tbSurat.getSelectedRow(), 15).toString()+") yang membuat surat ini!"); // Index Nama Petugas = 15
                     }
                 }
             }else{
                 JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
             }
         }
     }//GEN-LAST:event_BtnEditActionPerformed

     private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
             BtnEditActionPerformed(null);
         }else{
             Valid.pindah(evt, BtnHapus, BtnPrint);
         }
     }//GEN-LAST:event_BtnEditKeyPressed

     private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
         petugas.dispose();
         dispose();
     }//GEN-LAST:event_BtnKeluarActionPerformed

     private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
             BtnKeluarActionPerformed(null);
         }else{Valid.pindah(evt,BtnPrint,TCari);}
     }//GEN-LAST:event_BtnKeluarKeyPressed

     private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        // Cetak laporan SHK
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
         if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
             BtnBatal.requestFocus();
         }else if(tabMode.getRowCount()!=0){
             Map<String, Object> param = new HashMap<>();
             param.put("namars",akses.getnamars());
             param.put("alamatrs",akses.getalamatrs());
             param.put("kotars",akses.getkabupatenrs());
             param.put("propinsirs",akses.getpropinsirs());
             param.put("kontakrs",akses.getkontakrs());
             param.put("emailrs",akses.getemailrs());
             param.put("logo",Sequel.cariGambar("select setting.logo from setting"));

             // Filter selection
             String filter = " WHERE s.tgl_pernyataan BETWEEN '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' AND '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
             String keyword = TCari.getText().trim();
             if (!keyword.equals("")) {
                 filter += " AND (s.no_pernyataan LIKE '%"+keyword+"%' OR s.no_rawat LIKE '%"+keyword+"%' OR rp.no_rkm_medis LIKE '%"+keyword+"%' " +
                           "OR p.nm_pasien LIKE '%"+keyword+"%' OR s.nama_ibu LIKE '%"+keyword+"%' OR s.nama_ayah LIKE '%"+keyword+"%' " +
                           "OR s.tempat_pengambilan LIKE '%"+keyword+"%' OR pt.nama LIKE '%"+keyword+"%') ";
             }

             // Jika baris dipilih, prioritaskan
             if (tbSurat.getSelectedRow() > -1 && !NoPernyataan.getText().isEmpty() && NoPernyataan.getText().equals(tbSurat.getValueAt(tbSurat.getSelectedRow(), 0).toString()) ) {
                  filter = " WHERE s.no_pernyataan = '"+tbSurat.getValueAt(tbSurat.getSelectedRow(), 0).toString()+"' ";
             }

             // Query report SHK
             String reportQuery = "SELECT s.no_pernyataan, s.no_rawat, rp.no_rkm_medis, p.nm_pasien AS nama_bayi, " +
                                  "p.tgl_lahir AS tgl_lahir_bayi, COALESCE(pb.jam_lahir,'00:00:00') AS jam_lahir_bayi, " +
                                  "s.nama_ibu, s.nama_ayah, s.alamat_orangtua, s.no_telp_orangtua, " +
                                  "s.tgl_pernyataan, s.tgl_pengambilan_sample, s.jam_pengambilan_sample, s.tempat_pengambilan, " +
                                  "s.nip_petugas, pt.nama AS nama_petugas " +
                                  "FROM surat_pernyataan_shk s " +
                                  "INNER JOIN reg_periksa rp ON s.no_rawat = rp.no_rawat " +
                                  "INNER JOIN pasien p ON rp.no_rkm_medis = p.no_rkm_medis " +
                                  "LEFT JOIN pasien_bayi pb ON p.no_rkm_medis = pb.no_rkm_medis " +
                                  "INNER JOIN petugas pt ON s.nip_petugas = pt.nip " +
                                  filter +
                                  "ORDER BY s.tgl_pernyataan DESC, s.no_pernyataan DESC";

             // *** Ganti "rptSuratPernyataanSHK.jasper" dengan nama file report Anda ***
             Valid.MyReportqry("rptSuratPernyataanSHK.jasper", "report", "::[ Data Surat Pernyataan Pengambilan Sampel SHK ]::", reportQuery, param);
         }
         this.setCursor(Cursor.getDefaultCursor());
     }//GEN-LAST:event_BtnPrintActionPerformed

     private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
             BtnPrintActionPerformed(null);
         }else{
             Valid.pindah(evt, BtnEdit, BtnKeluar);
         }
     }//GEN-LAST:event_BtnPrintKeyPressed

     private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_ENTER){
             BtnCariActionPerformed(null);
         }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
             BtnCari.requestFocus();
         }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
             BtnKeluar.requestFocus();
         }
     }//GEN-LAST:event_TCariKeyPressed

     private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
         tampil();
     }//GEN-LAST:event_BtnCariActionPerformed

     private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
             BtnCariActionPerformed(null);
         }else{
             Valid.pindah(evt, TCari, BtnAll);
         }
     }//GEN-LAST:event_BtnCariKeyPressed

     private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
         TCari.setText("");
         tampil();
     }//GEN-LAST:event_BtnAllActionPerformed

     private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
             tampil();
             TCari.setText("");
         }else{
             Valid.pindah(evt, BtnCari, TNoRw);
         }
     }//GEN-LAST:event_BtnAllKeyPressed

     private void tbSuratMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSuratMouseClicked
         if(tabMode.getRowCount()!=0){
             try {
                 getData();
             } catch (java.lang.NullPointerException e) { }
             // Restore photo calls
             try {
                 isPhoto();
                 if (ChkAccor.isSelected()) {
                    panggilPhoto();
                 }
             } catch (java.lang.NullPointerException e) { }
         }
     }//GEN-LAST:event_tbSuratMouseClicked

     private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
         isForm();
     }//GEN-LAST:event_ChkInputActionPerformed

     private void tbSuratKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbSuratKeyReleased
         if(tabMode.getRowCount()!=0){
             if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                 try {
                     getData();
                 } catch (java.lang.NullPointerException e) { }
                  // Restore photo calls
                 try {
                     isPhoto();
                     if (ChkAccor.isSelected()) {
                        panggilPhoto();
                     }
                 } catch (java.lang.NullPointerException e) { }
             } else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                 try {
                     getData();
                     ChkAccor.setSelected(true);
                     isPhoto();
                     panggilPhoto();
                 } catch (java.lang.NullPointerException e) { }
             }
         }
     }//GEN-LAST:event_tbSuratKeyReleased

     private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
         // Navigasi dari petugas ke Simpan
         Valid.pindah(evt,TempatPengambilan,BtnSimpan);
     }//GEN-LAST:event_btnPetugasKeyPressed

     private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
         petugas.emptTeks();
         petugas.isCek();
         petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
         petugas.setLocationRelativeTo(internalFrame1);
         petugas.setVisible(true);
     }//GEN-LAST:event_btnPetugasActionPerformed

     private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
         // Logic isRawat dipanggil
         if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN || evt.getKeyCode() == KeyEvent.VK_ENTER){
             isRawat(); // Panggil isRawat untuk isi data bayi/ortu
             if (TNoRM.getText().isEmpty()) {
                 TNoRw.requestFocus();
             } else {
                 TglPernyataan.requestFocus();
             }
         }else{
             Valid.pindah(evt,TCari,TglPernyataan);
         }
     }//GEN-LAST:event_TNoRwKeyPressed

     private void TglPernyataanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPernyataanKeyPressed
         Valid.pindah(evt,TNoRw,NoPernyataan);
     }//GEN-LAST:event_TglPernyataanKeyPressed

     private void NoPernyataanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPernyataanKeyPressed
            Valid.pindah(evt,TglPernyataan,TglPengambilan);
     }//GEN-LAST:event_NoPernyataanKeyPressed

     private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
         if(tbSurat.getSelectedRow()!= -1){
             isPhoto();
             if (ChkAccor.isSelected()) {
                 panggilPhoto();
             } else {
                 LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Photo/Scan Belum Tersedia</font></center></body></html>");
                 lokasifile = "";
             }
         }else{
             ChkAccor.setSelected(false);
             isPhoto();
             JOptionPane.showMessageDialog(null,"Silahkan pilih data surat pada tabel terlebih dahulu..!!!");
         }
     }//GEN-LAST:event_ChkAccorActionPerformed

     private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
         // Menggunakan antripernyataanshk dan surat_pernyataan_shk_bukti
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, tidak ada data surat pernyataan SHK...!!!!");
            TCari.requestFocus();
        }else{
            if(tbSurat.getSelectedRow()>-1){
                String noPernyataan = tbSurat.getValueAt(tbSurat.getSelectedRow(),0).toString();
                String noRawat = tbSurat.getValueAt(tbSurat.getSelectedRow(),1).toString();

                Sequel.queryu("delete from antripernyataanshk");
                Sequel.queryu2("insert into antripernyataanshk values(?,?)", 2, new String[]{noPernyataan, noRawat});
                

                JOptionPane.showMessageDialog(rootPane,"Nomor Pernyataan "+ noPernyataan +" siap untuk pengambilan bukti/scan.");
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data surat pernyataan SHK pada tabel terlebih dahulu..!!");
            }
        }
     }//GEN-LAST:event_btnAmbilActionPerformed

     private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
         if(tbSurat.getSelectedRow()>-1){
             panggilPhoto();
         }else{
             JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data surat pada tabel terlebih dahulu..!!");
         }
     }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

     private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        // View Bukti SHK
       if(tbSurat.getSelectedRow()>-1){
            if(lokasifile.equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan ambil photo bukti pernyataan SHK terlebih dahulu..!!!!");
            }else{
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                param.put("photo","http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/pernyataanSHK/"+lokasifile); // Sesuaikan path '/shkphoto/'

                String noPernyataan = tbSurat.getValueAt(tbSurat.getSelectedRow(),0).toString();
                String tanggalPernyataan = Sequel.cariIsi("select tgl_pernyataan from surat_pernyataan_shk where no_pernyataan=?", noPernyataan);

                String nip = Sequel.cariIsi("SELECT nip_petugas FROM surat_pernyataan_shk WHERE no_pernyataan=?", noPernyataan);
                String namaPetugas = Sequel.cariIsi("SELECT nama FROM petugas WHERE nip=?", nip);
                String finger = Sequel.cariIsi("SELECT sha1(sidikjari.sidikjari) FROM sidikjari INNER JOIN pegawai ON pegawai.id=sidikjari.id WHERE pegawai.nik=?", nip);
                param.put("finger_petugas","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+namaPetugas+"\nID "+(finger.equals("")?nip:finger)+"\n"+Valid.SetTgl3(tanggalPernyataan));

                // Ganti "rptSuratPernyataanSHK.jasper" jika ada report khusus view photo
                Valid.MyReportqry("rptSuratPernyataanSHK.jasper","report","::[ Bukti Surat Pernyataan SHK ]::",
                    "SELECT s.no_pernyataan, s.no_rawat, rp.no_rkm_medis, p.nm_pasien AS nama_bayi, p.jk, " +
                    "p.tgl_lahir AS tgl_lahir_bayi, COALESCE(pb.jam_lahir,'00:00:00') AS jam_lahir, " +
                    "s.nama_ibu, s.nama_ayah, s.alamat_orangtua, s.no_telp_orangtua, " +
                    "s.tgl_pernyataan, s.tgl_pengambilan_sample, s.jam_pengambilan_sample, s.tempat_pengambilan, " +
                    "s.nip_petugas, pt.nama AS nama_petugas " +
                    "FROM surat_pernyataan_shk s " +
                    "INNER JOIN reg_periksa rp ON s.no_rawat = rp.no_rawat " +
                    "INNER JOIN pasien p ON rp.no_rkm_medis = p.no_rkm_medis " +
                    "LEFT JOIN pasien_bayi pb ON p.no_rkm_medis = pb.no_rkm_medis " +
                    "INNER JOIN petugas pt ON s.nip_petugas = pt.nip " +
                    "WHERE s.no_pernyataan='"+noPernyataan+"'",param);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }
     }//GEN-LAST:event_BtnPrint1ActionPerformed

      private void TglPengambilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPengambilanKeyPressed
          Valid.pindah(evt, NoPernyataan, cmbJamSample);
      }//GEN-LAST:event_TglPengambilanKeyPressed

      private void cmbJamSampleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamSampleKeyPressed
          Valid.pindah(evt, TglPengambilan, cmbMntSample);
      }//GEN-LAST:event_cmbJamSampleKeyPressed

      private void cmbMntSampleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntSampleKeyPressed
          Valid.pindah(evt, cmbJamSample, cmbDtkSample);
      }//GEN-LAST:event_cmbMntSampleKeyPressed

      private void cmbDtkSampleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkSampleKeyPressed
          Valid.pindah(evt, cmbMntSample, TempatPengambilan);
      }//GEN-LAST:event_cmbDtkSampleKeyPressed

      private void TempatPengambilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TempatPengambilanKeyPressed
          Valid.pindah(evt, cmbDtkSample, btnPetugas);
      }//GEN-LAST:event_TempatPengambilanKeyPressed
     //</editor-fold>

     /**
     * @param args the command line arguments
     */
     public static void main(String args[]) {
         java.awt.EventQueue.invokeLater(() -> {
             SuratPernyataanPengambilanSampleSHK dialog = new SuratPernyataanPengambilanSampleSHK(new javax.swing.JFrame(), true);
             dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                 @Override
                 public void windowClosing(java.awt.event.WindowEvent e) {
                     System.exit(0);
                 }
             });
             dialog.setVisible(true);
         });
     }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.TextBox AlamatOrtu;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.Label LCount;
    private widget.editorpane LoadHTML2;
    private widget.TextBox NIP;
    private widget.TextBox NamaAyah;
    private widget.TextBox NamaIbu;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NoPernyataan;
    private widget.TextBox NoTelp;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TempatPengambilan;
    private widget.Tanggal TglLahirBayi;
    private widget.Tanggal TglPengambilan;
    private widget.Tanggal TglPernyataan;
    private widget.Button btnAmbil;
    private widget.Button btnPetugas;
    private widget.ComboBox cmbDtkLahir;
    private widget.ComboBox cmbDtkSample;
    private widget.ComboBox cmbJamLahir;
    private widget.ComboBox cmbJamSample;
    private widget.ComboBox cmbMntLahir;
    private widget.ComboBox cmbMntSample;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbSurat;
    // End of variables declaration//GEN-END:variables

     //<editor-fold defaultstate="collapsed" desc="Metode-metode lain">
     public void tampil() {
         Valid.tabelKosong(tabMode);
         try{
             // SELECT query untuk surat_pernyataan_shk
             String sql = "SELECT s.no_pernyataan, s.no_rawat, rp.no_rkm_medis, p.nm_pasien AS nama_bayi, " +
                          "p.tgl_lahir AS tgl_lahir_bayi, COALESCE(pb.jam_lahir,'00:00:00') AS jam_lahir_bayi, " +
                          "s.nama_ibu, s.nama_ayah, s.alamat_orangtua, s.no_telp_orangtua, " +
                          "s.tgl_pernyataan, s.tgl_pengambilan_sample, s.jam_pengambilan_sample, s.tempat_pengambilan, " +
                          "s.nip_petugas, pt.nama AS nama_petugas " +
                          "FROM surat_pernyataan_shk s " +
                          "INNER JOIN reg_periksa rp ON s.no_rawat = rp.no_rawat " +
                          "INNER JOIN pasien p ON rp.no_rkm_medis = p.no_rkm_medis " +
                          "LEFT JOIN pasien_bayi pb ON p.no_rkm_medis = pb.no_rkm_medis " +
                          "INNER JOIN petugas pt ON s.nip_petugas = pt.nip " +
                          "WHERE s.tgl_pernyataan BETWEEN ? AND ? "; // Filter tgl pernyataan

             String keyword = TCari.getText().trim();
             if(!keyword.equals("")){
                 sql += "AND (s.no_pernyataan LIKE ? OR s.no_rawat LIKE ? OR rp.no_rkm_medis LIKE ? OR p.nm_pasien LIKE ? " +
                        "OR s.nama_ibu LIKE ? OR s.nama_ayah LIKE ? OR s.tempat_pengambilan LIKE ? OR pt.nama LIKE ?) ";
             }
             sql += "ORDER BY s.tgl_pernyataan DESC, s.no_pernyataan DESC";

             ps=koneksi.prepareStatement(sql);
             try {
                  ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                  ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                 if(!keyword.equals("")){
                     ps.setString(3,"%"+keyword+"%");
                     ps.setString(4,"%"+keyword+"%");
                     ps.setString(5,"%"+keyword+"%");
                     ps.setString(6,"%"+keyword+"%");
                     ps.setString(7,"%"+keyword+"%");
                     ps.setString(8,"%"+keyword+"%");
                     ps.setString(9,"%"+keyword+"%");
                     ps.setString(10,"%"+keyword+"%");
                 }

                 rs=ps.executeQuery();
                 while(rs.next()){
                      // --- BAGIAN KRITIS: Format tanggal untuk tabel GUI di Tampil ---
                     String tglLahirTampil = "";
                     String tglPernyataanTampil = "";
                     String tglPengambilanTampil = "";
                     try {
                        Date dateLahir = sdfDb.parse(rs.getString("tgl_lahir_bayi"));
                        tglLahirTampil = sdfDisplay.format(dateLahir);
                     } catch (Exception e) { System.err.println("Error parsing tgl_lahir_bayi di tampil: "+rs.getString("tgl_lahir_bayi")+" - "+e); }
                     try {
                        Date datePernyataan = sdfDb.parse(rs.getString("tgl_pernyataan"));
                        tglPernyataanTampil = sdfDisplay.format(datePernyataan);
                     } catch (Exception e) { System.err.println("Error parsing tgl_pernyataan di tampil: "+rs.getString("tgl_pernyataan")+" - "+e); }
                     try {
                         Date datePengambilan = sdfDb.parse(rs.getString("tgl_pengambilan_sample"));
                         tglPengambilanTampil = sdfDisplay.format(datePengambilan);
                     } catch (Exception e) { System.err.println("Error parsing tgl_pengambilan_sample di tampil: "+rs.getString("tgl_pengambilan_sample")+" - "+e); }
                     // --- AKHIR BAGIAN KRITIS ---

                     // AddRow sesuai kolom baru MENGGUNAKAN FORMAT TAMPILAN
                     tabMode.addRow(new String[]{
                         rs.getString("no_pernyataan"), rs.getString("no_rawat"), rs.getString("no_rkm_medis"),
                         rs.getString("nama_bayi"), tglLahirTampil, rs.getString("jam_lahir_bayi").substring(0, 5), // Jam lahir HH:MM
                         rs.getString("nama_ibu"), rs.getString("nama_ayah"), rs.getString("alamat_orangtua"),
                         rs.getString("no_telp_orangtua"), tglPernyataanTampil,
                         tglPengambilanTampil, rs.getString("jam_pengambilan_sample").substring(0, 5), // Jam sample HH:MM
                         rs.getString("tempat_pengambilan"), rs.getString("nip_petugas"), rs.getString("nama_petugas")
                     });
                 }
             } catch (Exception e) {
                 System.out.println("Notif (tampil loop) : "+e);
             } finally{
                 if(rs!=null){ try { rs.close(); } catch(SQLException ex){} }
                 if(ps!=null){ try { ps.close(); } catch(SQLException ex){} }
             }
         }catch(Exception e){
             System.out.println("Notifikasi (tampil prepare): "+e);
         }
         LCount.setText(""+tabMode.getRowCount());
     }

     public void emptTeks() {
         TNoRw.setText("");
         TNoRM.setText("");
         TPasien.setText("");
         // TglLahirBayi.setDate(new Date()); // <<< Jangan default ke tanggal sekarang
         // Reset tanggal lahir ke null jika komponen mendukung
         try { TglLahirBayi.setDate(null); } catch (Exception e) { /* abaikan */ }
         setWaktuLahirToComboBox("00:00:00");
         TglLahirBayiDB = "";
         JamLahirBayiDB = "";
         NamaIbu.setText("");
         NamaAyah.setText("");
         AlamatOrtu.setText("");
         NoTelp.setText("");

         TglPernyataan.setDate(new Date()); // Tanggal pernyataan wajar di-default hari ini
         TglPengambilan.setDate(new Date()); // Tanggal sampel wajar di-default hari ini
         setWaktuSampleToComboBox("00:00:00");
         TempatPengambilan.setText("");

         // Auto Number
         Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_pernyataan,4),signed)),0) from surat_pernyataan_shk where tgl_pernyataan='"+Valid.SetTgl(TglPernyataan.getSelectedItem()+"")+"' ",
                 "SPSHK"+TglPernyataan.getSelectedItem().toString().substring(6,10)+TglPernyataan.getSelectedItem().toString().substring(3,5)+TglPernyataan.getSelectedItem().toString().substring(0,2),4,NoPernyataan);

         LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
         lokasifile = "";

         TNoRw.requestFocus();
     }


     private void getData() {
        if(tbSurat.getSelectedRow()!= -1){
           // Data dari tabel surat_pernyataan_shk
           NoPernyataan.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),0).toString());
           TNoRw.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),1).toString());
           TNoRM.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),2).toString());
           TPasien.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),3).toString());

           NamaIbu.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),6).toString());
           NamaAyah.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),7).toString());
           AlamatOrtu.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),8).toString());
           NoTelp.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),9).toString());

           // --- REVISI BAGIAN SET TANGGAL KOMPONEN DARI TABEL ---
           String tglPernyataanStr = tbSurat.getValueAt(tbSurat.getSelectedRow(), 10).toString();
           String tglPengambilanStr = tbSurat.getValueAt(tbSurat.getSelectedRow(), 11).toString();

           try {
               // Parse string DD-MM-YYYY dari tabel menggunakan sdfDisplay
               Date parsedPernyataan = sdfDisplay.parse(tglPernyataanStr);
               // Set komponen Tanggal menggunakan objek Date hasil parse
               TglPernyataan.setDate(parsedPernyataan);
           } catch (ParseException e) {
               System.err.println("Error parsing tglPernyataan dari tabel: " + tglPernyataanStr + " - " + e);
               // Jika gagal parse, kosongkan komponen (atau biarkan default)
               try { TglPernyataan.setDate(null); } catch (Exception ex) {}
           }

           try {
               // Parse string DD-MM-YYYY dari tabel menggunakan sdfDisplay
               Date parsedPengambilan = sdfDisplay.parse(tglPengambilanStr);
               // Set komponen Tanggal menggunakan objek Date hasil parse
               TglPengambilan.setDate(parsedPengambilan);
           } catch (ParseException e) {
               System.err.println("Error parsing tglPengambilan dari tabel: " + tglPengambilanStr + " - " + e);
               // Jika gagal parse, kosongkan komponen (atau biarkan default)
               try { TglPengambilan.setDate(null); } catch (Exception ex) {}
           }
           // --- AKHIR REVISI ---

           // Ambil jam sample langsung dari DB saat getData
           setWaktuSampleToComboBox(Sequel.cariIsi("select jam_pengambilan_sample from surat_pernyataan_shk where no_pernyataan=?", NoPernyataan.getText()));
           TempatPengambilan.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),13).toString());
           NIP.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),14).toString());
           NamaPetugas.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),15).toString());

           // Panggil isRawat setelah TNoRw di-set untuk mengisi TglLahirBayi (ini sudah benar)
           isRawat();
       }
   }

    // --- Method isRawat() --- (DIMODIFIKASI KETAT: Hanya pakai tanggal DB)
    private void isRawat() {
        // 1. Reset fields lain seperti biasa
        TNoRM.setText("");
        TPasien.setText("");
        // TglLahirBayi.setDate(new Date()); // <<< HAPUS INITIAL DEFAULT DI SINI
        setWaktuLahirToComboBox("00:00:00"); // Reset jam tetap ok
        TglLahirBayiDB = "";
        JamLahirBayiDB = "";
        NamaIbu.setText("");
        NamaAyah.setText("");
        AlamatOrtu.setText("");
        NoTelp.setText("");
        boolean tglLahirValid = false; // Flag untuk menandai jika tanggal berhasil di-set dari DB

        if (TNoRw.getText() == null || TNoRw.getText().trim().isEmpty()) {
            System.out.println("isRawat: No Rawat kosong.");
            return;
        }

        // 2. Query seperti biasa
        String sql = "SELECT rp.no_rkm_medis, p.nm_pasien, p.jk, p.tgl_lahir, "
                + "COALESCE(pb.jam_lahir,'00:00:00') as jam_lahir, p.nm_ibu, COALESCE(pb.nama_ayah,'') as nama_ayah, "
                + "COALESCE(p.alamat, '') AS alamat_pasien_saja, "
                + "CONCAT(p.alamat, IFNULL(CONCAT(', ', kl.nm_kel), ''), IFNULL(CONCAT(', ', kc.nm_kec), ''), IFNULL(CONCAT(', ', kb.nm_kab), ''), IFNULL(CONCAT(', ', prop.nm_prop), '')) AS alamat_lengkap, "
                + "p.no_tlp "
                + "FROM reg_periksa rp "
                + "INNER JOIN pasien p ON rp.no_rkm_medis = p.no_rkm_medis "
                + "LEFT JOIN pasien_bayi pb ON p.no_rkm_medis = pb.no_rkm_medis "
                + "LEFT JOIN kelurahan kl ON p.kd_kel = kl.kd_kel "
                + "LEFT JOIN kecamatan kc ON p.kd_kec = kc.kd_kec "
                + "LEFT JOIN kabupaten kb ON p.kd_kab = kb.kd_kab "
                + "LEFT JOIN propinsi prop ON p.kd_prop = prop.kd_prop "
                + "WHERE rp.no_rawat = ?";
        try {
            psPasienBayi = koneksi.prepareStatement(sql);
            try {
                psPasienBayi.setString(1, TNoRw.getText());
                rsPasienBayi = psPasienBayi.executeQuery();
                if (rsPasienBayi.next()) { // Jika data ditemukan
                    TNoRM.setText(rsPasienBayi.getString("no_rkm_medis"));
                    TPasien.setText(rsPasienBayi.getString("nm_pasien"));

                    // ---- Bagian Kunci untuk Tanggal Lahir (Direct Parsing) ----
                    TglLahirBayiDB = rsPasienBayi.getString("tgl_lahir");
                    System.out.println("isRawat: Tgl Lahir DB String = '" + TglLahirBayiDB + "'"); // DEBUG

                    if (TglLahirBayiDB != null && !TglLahirBayiDB.isEmpty() && !TglLahirBayiDB.equals("0000-00-00")) {
                        try {
                            // Coba parsing langsung dengan format YYYY-MM-DD
                            Date parsedDate = sdfDb.parse(TglLahirBayiDB); // Gunakan sdfDb yang sudah didefinisikan
                            TglLahirBayi.setDate(parsedDate); // Set tanggal hasil parsing
                            tglLahirValid = true;
                            System.out.println("isRawat: Parsed and set date directly. Component value: " + TglLahirBayi.getSelectedItem()); // DEBUG
                        } catch (java.text.ParseException e) {
                            // Jika parsing GAGAL
                            tglLahirValid = false;
                            System.err.println("isRawat: Gagal parsing Tgl Lahir DB '" + TglLahirBayiDB + "'. Error: " + e.getMessage());
                            // KOSONGKAN atau beri tahu user, JANGAN set new Date()
                             try { TglLahirBayi.setDate(null); } catch (Exception ex) { /* abaikan jika error */ } // Coba set null jika komponen mendukung
                            JOptionPane.showMessageDialog(this, "Gagal memproses tanggal lahir dari database: " + TglLahirBayiDB + "\nFormat mungkin tidak valid.", "Error Tanggal Lahir", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        // Jika data DB null, kosong, atau "0000-00-00", JANGAN set new Date()
                        tglLahirValid = false;
                        System.out.println("isRawat: Tgl Lahir DB null, kosong, atau '0000-00-00'. Komponen tidak diubah.");
                        try { TglLahirBayi.setDate(null); } catch (Exception ex) { /* abaikan jika error */ } // Coba set null jika komponen mendukung
                         // JOptionPane.showMessageDialog(null, "Data Tanggal Lahir bayi tidak ditemukan atau tidak valid di database.", "Info Tanggal Lahir", JOptionPane.INFORMATION_MESSAGE);
                    }
                    // ------------------------------------------

                    // ---- Bagian Kunci untuk Jam Lahir (Tetap sama) ----
                    JamLahirBayiDB = rsPasienBayi.getString("jam_lahir");
                    setWaktuLahirToComboBox(JamLahirBayiDB);
                    // --------------------------------------

                    // Isi field lain
                    NamaIbu.setText(rsPasienBayi.getString("nm_ibu") != null ? rsPasienBayi.getString("nm_ibu") : "");
                    NamaAyah.setText(rsPasienBayi.getString("nama_ayah") != null ? rsPasienBayi.getString("nama_ayah") : "");
                    String alamat = rsPasienBayi.getString("alamat_lengkap");
                    if (alamat == null || alamat.trim().isEmpty() || alamat.trim().equals(",")) {
                       alamat = rsPasienBayi.getString("alamat_pasien_saja");
                    }
                    AlamatOrtu.setText(alamat != null ? alamat.trim() : "");
                    NoTelp.setText(rsPasienBayi.getString("no_tlp") != null ? rsPasienBayi.getString("no_tlp") : "");

                } else { // Jika data pasien/bayi tidak ditemukan
                    JOptionPane.showMessageDialog(this, "Data Pasien/Bayi dengan No. Rawat '" + TNoRw.getText() + "' tidak ditemukan.");
                    TNoRw.requestFocus();
                }
            } catch (SQLException e) {
                System.out.println("Notif SQL Exception isRawat : " + e);
            } catch (Exception e){
                System.out.println("Notif General Exception isRawat : " + e);
            } finally {
                if (rsPasienBayi != null) { try { rsPasienBayi.close(); } catch (SQLException ignore) {} }
                if (psPasienBayi != null) { try { psPasienBayi.close(); } catch (SQLException ignore) {} }
            }
        } catch (SQLException e) {
            System.out.println("Notif PrepareStatement isRawat : " + e);
        }
    }
    // --- Akhir Method isRawat() ---

     public void setNoRm(String norwt,Date tgl2) {
         TNoRw.setText(norwt);
         TCari.setText(norwt);
         DTPCari2.setDate(tgl2);
         isRawat(); // Panggil isRawat untuk mengisi data bayi/ortu

         ChkInput.setSelected(true);
         isForm();
         tampil(); // Load data surat pernyataan untuk norwt ini

         if (tabMode.getRowCount() == 0) {
             // Jika TIDAK ADA data surat ditemukan
             TNoRw.setText(norwt);
             isRawat(); // Panggil lagi untuk memastikan field terisi jika data pasien ada
             TglPengambilan.setDate(tgl2);
             setWaktuSampleToComboBox(String.format("%tT", new Date()));
             TglPernyataan.requestFocus();
         } else {
             // Jika ADA data surat ditemukan
             tbSurat.setRowSelectionInterval(0, 0);
             getData();
             try {
                 isPhoto();
                 if (ChkAccor.isSelected()) {
                     panggilPhoto();
                 }
             } catch (Exception e) {
                 System.out.println("Error saat menampilkan foto di setNoRm: "+e);
             }
         }
     }

     private void isForm(){
         if(ChkInput.isSelected()==true){
             ChkInput.setVisible(false);
             PanelInput.setPreferredSize(new Dimension(WIDTH,270));
             FormInput.setVisible(true);
             ChkInput.setVisible(true);
             BtnBatal.requestFocus();
         }else if(ChkInput.isSelected()==false){
             ChkInput.setVisible(false);
             PanelInput.setPreferredSize(new Dimension(WIDTH,20));
             FormInput.setVisible(false);
             ChkInput.setVisible(true);
         }
     }

     public void isCek(){
          // Sesuaikan Hak Akses jika perlu
         boolean aksesSuratSHK = akses.getadmin() || akses.getsurat_persetujuan_umum(); // Ganti dg hak akses relevan

         BtnSimpan.setEnabled(aksesSuratSHK);
         BtnHapus.setEnabled(aksesSuratSHK);
         BtnEdit.setEnabled(aksesSuratSHK);
         BtnPrint.setEnabled(aksesSuratSHK);
         btnAmbil.setEnabled(aksesSuratSHK);
         BtnPrint1.setEnabled(aksesSuratSHK);

         if(akses.getjml2()>=1){
             NIP.setEditable(false);
             btnPetugas.setEnabled(false);
             NIP.setText(akses.getkode());
             NamaPetugas.setText(petugas.tampil3(NIP.getText()));
             if(NamaPetugas.getText().equals("")){
                 NIP.setText("");
             }
         }
     }

     private void ganti() {
         // UPDATE ke tabel surat_pernyataan_shk
         String jamSample = getWaktuSampleFromComboBox();
         if(Sequel.mengedittf("surat_pernyataan_shk","no_pernyataan=?","no_rawat=?, tgl_pernyataan=?, tgl_pengambilan_sample=?, jam_pengambilan_sample=?, tempat_pengambilan=?, nip_petugas=?, nama_ibu=?, nama_ayah=?, alamat_orangtua=?, no_telp_orangtua=?",11,new String[]{
             TNoRw.getText(), Valid.SetTgl(TglPernyataan.getSelectedItem()+""), Valid.SetTgl(TglPengambilan.getSelectedItem()+""), // Kirim YYYY-MM-DD ke DB
             jamSample, TempatPengambilan.getText(), NIP.getText(), NamaIbu.getText(), NamaAyah.getText(), AlamatOrtu.getText(), NoTelp.getText(),
             tbSurat.getValueAt(tbSurat.getSelectedRow(),0).toString()
         })==true){

             // --- BAGIAN KRITIS: Format tanggal untuk update tabel GUI ---
             String tglLahirTableStr = "";
             Date tglLahirDate = TglLahirBayi.getDate(); // 1. Ambil objek Date
             if (tglLahirDate != null) {
                  try {
                      // String tglDbFormat = sdfDb.format(tglLahirDate); // 2. Format ke YYYY-MM-DD (Tidak perlu jika Valid.SetTgl menerima Date)
                      // tglLahirTableStr = Valid.SetTgl(tglDbFormat); // 3. Format ke DD-MM-YYYY untuk tabel
                      tglLahirTableStr = sdfDisplay.format(tglLahirDate); // << LANGSUNG FORMAT KE DISPLAY
                  } catch (Exception e) { tglLahirTableStr = "ERROR"; }
             }

             String tglPernyataanTableStr = "";
             Date tglPernyataanDate = TglPernyataan.getDate();
             if (tglPernyataanDate != null) {
                 try {
                      // String tglDbFormat = sdfDb.format(tglPernyataanDate);
                      // tglPernyataanTableStr = Valid.SetTgl(tglDbFormat);
                      tglPernyataanTableStr = sdfDisplay.format(tglPernyataanDate); // << LANGSUNG FORMAT KE DISPLAY
                 } catch (Exception e) { tglPernyataanTableStr = "ERROR"; }
             }

             String tglPengambilanTableStr = "";
             Date tglPengambilanDate = TglPengambilan.getDate();
             if (tglPengambilanDate != null) {
                  try {
                      // String tglDbFormat = sdfDb.format(tglPengambilanDate);
                      // tglPengambilanTableStr = Valid.SetTgl(tglDbFormat);
                       tglPengambilanTableStr = sdfDisplay.format(tglPengambilanDate); // << LANGSUNG FORMAT KE DISPLAY
                  } catch (Exception e) { tglPengambilanTableStr = "ERROR"; }
             }
             // --- AKHIR BAGIAN KRITIS ---

             // Update table model GUI menggunakan string yang SUDAH DIFORMAT BENAR
             int row = tbSurat.getSelectedRow();
             tbSurat.setValueAt(NoPernyataan.getText(),row,0);
             tbSurat.setValueAt(TNoRw.getText(),row,1);
             tbSurat.setValueAt(TNoRM.getText(),row,2);
             tbSurat.setValueAt(TPasien.getText(),row,3);
             tbSurat.setValueAt(tglLahirTableStr, row, 4); // << Gunakan hasil format
             tbSurat.setValueAt(JamLahirBayiDB != null ? JamLahirBayiDB.substring(0,5) : "00:00",row,5);
             tbSurat.setValueAt(NamaIbu.getText(),row,6);
             tbSurat.setValueAt(NamaAyah.getText(),row,7);
             tbSurat.setValueAt(AlamatOrtu.getText(),row,8);
             tbSurat.setValueAt(NoTelp.getText(),row,9);
             tbSurat.setValueAt(tglPernyataanTableStr, row, 10); // << Gunakan hasil format
             tbSurat.setValueAt(tglPengambilanTableStr, row, 11); // << Gunakan hasil format
             tbSurat.setValueAt(jamSample.substring(0,5),row,12);
             tbSurat.setValueAt(TempatPengambilan.getText(),row,13);
             tbSurat.setValueAt(NIP.getText(),row,14);
             tbSurat.setValueAt(NamaPetugas.getText(),row,15);
             emptTeks();
         }
     }

     private void hapus() {
         // Hapus dari surat_pernyataan_shk & bukti
         String noPernyataanToDelete = tbSurat.getValueAt(tbSurat.getSelectedRow(), 0).toString();

         int reply = JOptionPane.showConfirmDialog(rootPane,"Yakin ingin menghapus data surat pernyataan SHK "+noPernyataanToDelete+"?","Konfirmasi Hapus",JOptionPane.YES_NO_OPTION);
         if (reply == JOptionPane.YES_OPTION) {
             // Delete from the main table (bukti akan terhapus karena cascade)
             if(Sequel.queryu2tf("delete from surat_pernyataan_shk where no_pernyataan=?",1,new String[]{
                 noPernyataanToDelete
             })==true){
                  // Hapus dari antrian jika ada
                 Sequel.queryu2("delete from antripernyataanshk where no_pernyataan=?", 1, new String[]{noPernyataanToDelete});

                 tabMode.removeRow(tbSurat.getSelectedRow());
                 LCount.setText(""+tabMode.getRowCount());
                 emptTeks();
             }else{
                 JOptionPane.showMessageDialog(null,"Gagal menghapus data surat pernyataan SHK..!!");
             }
         }
     }

     //<editor-fold defaultstate="collapsed" desc="Photo Panel Methods">
     private void isPhoto(){
         if(ChkAccor.isSelected()==true){
             ChkAccor.setVisible(false);
             PanelAccor.setPreferredSize(new Dimension(480,HEIGHT));
             FormPhoto.setVisible(true);
             ChkAccor.setVisible(true);
         }else if(ChkAccor.isSelected()==false){
             ChkAccor.setVisible(false);
             PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
             FormPhoto.setVisible(false);
             ChkAccor.setVisible(true);
         }
     }

     private void panggilPhoto() {
        // Query ke surat_pernyataan_shk_bukti
        if(FormPhoto.isVisible()==true && tbSurat.getSelectedRow() > -1){
            lokasifile="";
            try {
                ps=koneksi.prepareStatement("select photo from surat_pernyataan_shk_bukti where no_pernyataan=?");
                try {
                    ps.setString(1,tbSurat.getValueAt(tbSurat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo")==null || rs.getString("photo").equals("") || rs.getString("photo").equals("-")){
                            lokasifile="";
                            LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Bukti/Scan Belum Tersedia</font></center></body></html>");
                        }else{
                            lokasifile=rs.getString("photo");
                            LoadHTML2.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/pernyataanSHK/"+lokasifile+"' alt='photo' width='450' height='450'/></center></body></html>"); // Sesuaikan path
                        }
                    }else{
                        lokasifile="";
                        LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Bukti/Scan Belum Tersedia</font></center></body></html>");
                    }
                } catch (Exception e) {
                    lokasifile="";
                    System.out.println("Notif Photo Load: "+e);
                    LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Gagal Memuat Bukti/Scan</font></center></body></html>");
                } finally{
                    if(rs!=null){ try { rs.close(); } catch(SQLException ex){} }
                    if(ps!=null){ try { ps.close(); } catch(SQLException ex){} }
                }
            } catch (Exception e) {
                System.out.println("Notif Prepare Photo Load: "+e);
                LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Gagal Memuat Bukti/Scan</font></center></body></html>");
            }
        } else {
            LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Bukti/Scan Belum Tersedia</font></center></body></html>");
            lokasifile = "";
        }
    }
     //</editor-fold>

     // --- Helper Methods for Time ComboBox ---
     private void setWaktuLahirToComboBox(String jamDb) {
        if (jamDb == null || jamDb.isEmpty() || !jamDb.matches("\\d{2}:\\d{2}:\\d{2}")) {
            jamDb = "00:00:00";
        }
        cmbJamLahir.setSelectedItem(jamDb.substring(0, 2));
        cmbMntLahir.setSelectedItem(jamDb.substring(3, 5));
        cmbDtkLahir.setSelectedItem(jamDb.substring(6, 8));
     }

     private void setWaktuSampleToComboBox(String jamDb) {
        if (jamDb == null || jamDb.isEmpty() || !jamDb.matches("\\d{2}:\\d{2}:\\d{2}")) {
            jamDb = "00:00:00";
        }
        cmbJamSample.setSelectedItem(jamDb.substring(0, 2));
        cmbMntSample.setSelectedItem(jamDb.substring(3, 5));
        cmbDtkSample.setSelectedItem(jamDb.substring(6, 8));

        // --- TAMBAHKAN INI UNTUK FORCE REPAINT (MAC FIX) ---
    cmbJamSample.repaint();
    cmbMntSample.repaint();
    cmbDtkSample.repaint();
     }

     private String getWaktuSampleFromComboBox() {
         return cmbJamSample.getSelectedItem().toString() + ":" +
                cmbMntSample.getSelectedItem().toString() + ":" +
                cmbDtkSample.getSelectedItem().toString();
     }
     // --- End Helper Methods ---

 }// </editor-fold>