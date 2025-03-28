/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
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
 import java.sql.SQLException;
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
 import kepegawaian.DlgCariDokter;
 
 /**
  *
  * @author perpustakaan
  */
 public final class RMHemodialisa extends javax.swing.JDialog {
     private final DefaultTableModel tabMode;
     private Connection koneksi=koneksiDB.condb();
     private sekuel Sequel=new sekuel();
     private validasi Valid=new validasi();
     private PreparedStatement ps;
     private ResultSet rs;
     private int i=0;    
     private DlgCariDokter dokter=new DlgCariDokter(null,false);
     private String dpjp="";
     private String TANGGALMUNDUR="yes";
     /** Creates new form DlgRujuk
      * @param parent
      * @param modal */
     public RMHemodialisa(java.awt.Frame parent, boolean modal) {
         super(parent, modal);
         initComponents();
         this.setLocation(8,1);
         setSize(628,674);
 
         tabMode=new DefaultTableModel(null,new Object[]{
             "No.Rawat","No.R.M.","Nama Pasien","Umur","JK","Tanggal","HD Ke","No Mesin","BB Kering","BB HD Terakhir",
             "Lama","Akses","Dializer","Dialisat","Transfusi","Penarikan Cairan",
             "QB","QD","UFG","Heparin Awal","Heparin Pemeliharaan","Heparin Sirkulasi","Volume Priming","Kode Dokter","Dokter"
         }){
               @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
         };
         tbObat.setModel(tabMode);
 
         //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
         tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
         tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
 
         for (i = 0; i < 25; i++) {
             TableColumn column = tbObat.getColumnModel().getColumn(i);
             if(i==0){
                 column.setPreferredWidth(105);
             }else if(i==1){
                 column.setPreferredWidth(65);
             }else if(i==2){
                 column.setPreferredWidth(160);
             }else if(i==3){
                 column.setPreferredWidth(35);
             }else if(i==4){
                 column.setPreferredWidth(20);
             }else if(i==5){
                 column.setPreferredWidth(120);
             }else if(i==6){
                 column.setPreferredWidth(35);
             }else if(i==7){
                 column.setPreferredWidth(60);
             }else if(i==8){
                 column.setPreferredWidth(60);
             }else if(i==9){
                 column.setPreferredWidth(90);
             }else if(i==10){
                 column.setPreferredWidth(35);
             }else if(i==11){
                 column.setPreferredWidth(110);
             }else if(i==12){
                 column.setPreferredWidth(110);
             }else if(i==13){
                 column.setPreferredWidth(100);
             }else if(i==14){
                 column.setPreferredWidth(60);
             }else if(i==15){
                 column.setPreferredWidth(100);
             }else if(i==16){
                 column.setPreferredWidth(70);
             }else if(i==17){
                 column.setPreferredWidth(70);
             }else if(i==18){
                 column.setPreferredWidth(70);
             }else if(i==19){
                 column.setPreferredWidth(80);
             }else if(i==20){
                 column.setPreferredWidth(120);
             }else if(i==21){
                 column.setPreferredWidth(100);
             }else if(i==22){
                 column.setPreferredWidth(100);
             }else if(i==23){
                 column.setMinWidth(0);
                 column.setMaxWidth(0);
             }else if(i==24){
                 column.setPreferredWidth(150);
             }
         }
         tbObat.setDefaultRenderer(Object.class, new WarnaTable());
         TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        kddok.setDocument(new batasInput((byte)20).getKata(kddok));
        THDKe.setDocument(new batasInput((byte)5).getKata(THDKe));
        TNoMesin.setDocument(new batasInput((byte)20).getKata(TNoMesin));
        TBBKering.setDocument(new batasInput((byte)10).getKata(TBBKering));
        TBBHDTerakhir.setDocument(new batasInput((byte)10).getKata(TBBHDTerakhir));
        TLama.setDocument(new batasInput((int)5).getKata(TLama));
        TAkses.setDocument(new batasInput((int)30).getKata(TAkses));
        TDializer.setDocument(new batasInput((int)30).getKata(TDializer));
        TDialisat.setDocument(new batasInput((int)30).getKata(TDialisat));
        TTransfusi.setDocument(new batasInput((int)5).getKata(TTransfusi));
        TPenarikan.setDocument(new batasInput((int)5).getKata(TPenarikan));
        TQB.setDocument(new batasInput((int)5).getKata(TQB));
        TQD.setDocument(new batasInput((int)5).getKata(TQD));
        TUFG.setDocument(new batasInput((int)20).getKata(TUFG));
        THeparinAwal.setDocument(new batasInput((int)10).getKata(THeparinAwal));
        THeparinPemeliharaan.setDocument(new batasInput((int)10).getKata(THeparinPemeliharaan));
        THeparinSirkulasi.setDocument(new batasInput((int)10).getKata(THeparinSirkulasi));
        TVolumePriming.setDocument(new batasInput((int)10).getKata(TVolumePriming));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){                   
                    kddok.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    namadokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }  
                kddok.requestFocus();
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
        
        kddok.setText(Sequel.cariIsi("select set_pjlab.kd_dokterhemodialisa from set_pjlab"));
        namadokter.setText(dokter.tampil3(kddok.getText()));
        
        jam();
        
        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        kddok = new widget.TextBox();
        namadokter = new widget.TextBox();
        btnDokter = new widget.Button();
        jLabel24 = new widget.Label();
        jLabel43 = new widget.Label();
        THDKe = new widget.TextBox();
        jLabel44 = new widget.Label();
        TNoMesin = new widget.TextBox();
        jLabel45 = new widget.Label();
        TBBKering = new widget.TextBox();
        jLabel46 = new widget.Label();
        TBBHDTerakhir = new widget.TextBox();
        jLabel47 = new widget.Label();
        jLabel54 = new widget.Label();
        jLabel25 = new widget.Label();
        TLama = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel27 = new widget.Label();
        TAkses = new widget.TextBox();
        jLabel36 = new widget.Label();
        TDializer = new widget.TextBox();
        jLabel48 = new widget.Label();
        TDialisat = new widget.TextBox();
        jLabel37 = new widget.Label();
        TTransfusi = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel41 = new widget.Label();
        TPenarikan = new widget.TextBox();
        jLabel39 = new widget.Label();
        jLabel29 = new widget.Label();
        TQB = new widget.TextBox();
        jLabel42 = new widget.Label();
        TQD = new widget.TextBox();
        jLabel49 = new widget.Label();
        TUFG = new widget.TextBox();
        jLabel50 = new widget.Label();
        THeparinAwal = new widget.TextBox();
        jLabel51 = new widget.Label();
        THeparinPemeliharaan = new widget.TextBox();
        jLabel52 = new widget.Label();
        THeparinSirkulasi = new widget.TextBox();
        jLabel53 = new widget.Label();
        TVolumePriming = new widget.TextBox();
        ChkInput = new widget.CekBox();

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Hemodialisa ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-03-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-03-2025" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 366));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 343));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 75, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 450, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-03-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 90, 23);

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
        jLabel16.setBounds(0, 40, 75, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(173, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(238, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(303, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(368, 40, 23, 23);

        jLabel18.setText("Dokter P.J. :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        kddok.setEditable(false);
        kddok.setHighlighter(null);
        kddok.setName("kddok"); // NOI18N
        kddok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokKeyPressed(evt);
            }
        });
        FormInput.add(kddok);
        kddok.setBounds(474, 40, 94, 23);

        namadokter.setEditable(false);
        namadokter.setName("namadokter"); // NOI18N
        FormInput.add(namadokter);
        namadokter.setBounds(570, 40, 185, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('2');
        btnDokter.setToolTipText("ALt+2");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        btnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterKeyPressed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(758, 40, 28, 23);

        jLabel24.setText("Data Hemodialisa :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(0, 70, 115, 23);

        jLabel43.setText("HD Ke :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(0, 100, 75, 23);

        THDKe.setHighlighter(null);
        THDKe.setName("THDKe"); // NOI18N
        THDKe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THDKeKeyPressed(evt);
            }
        });
        FormInput.add(THDKe);
        THDKe.setBounds(79, 100, 50, 23);

        jLabel44.setText("No Mesin :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(450, 100, 75, 23);

        TNoMesin.setHighlighter(null);
        TNoMesin.setName("TNoMesin"); // NOI18N
        TNoMesin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoMesinKeyPressed(evt);
            }
        });
        FormInput.add(TNoMesin);
        TNoMesin.setBounds(530, 100, 60, 23);

        jLabel45.setText("BB Kering :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(130, 100, 65, 23);

        TBBKering.setHighlighter(null);
        TBBKering.setName("TBBKering"); // NOI18N
        TBBKering.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBBKeringKeyPressed(evt);
            }
        });
        FormInput.add(TBBKering);
        TBBKering.setBounds(200, 100, 60, 23);

        jLabel46.setText("BB HD Terakhir :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(270, 100, 100, 23);

        TBBHDTerakhir.setHighlighter(null);
        TBBHDTerakhir.setName("TBBHDTerakhir"); // NOI18N
        TBBHDTerakhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBBHDTerakhirKeyPressed(evt);
            }
        });
        FormInput.add(TBBHDTerakhir);
        TBBHDTerakhir.setBounds(380, 100, 60, 23);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("kg");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(270, 100, 20, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("kg");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(450, 100, 20, 23);

        jLabel25.setText("Lama :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(580, 100, 75, 23);

        TLama.setHighlighter(null);
        TLama.setName("TLama"); // NOI18N
        TLama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TLamaKeyPressed(evt);
            }
        });
        FormInput.add(TLama);
        TLama.setBounds(660, 100, 50, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("Jam");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(720, 100, 30, 23);

        jLabel27.setText("Akses :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 140, 75, 23);

        TAkses.setHighlighter(null);
        TAkses.setName("TAkses"); // NOI18N
        TAkses.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAksesKeyPressed(evt);
            }
        });
        FormInput.add(TAkses);
        TAkses.setBounds(80, 140, 180, 23);

        jLabel36.setText("Dializer :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(270, 140, 60, 23);

        TDializer.setHighlighter(null);
        TDializer.setName("TDializer"); // NOI18N
        TDializer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDializerKeyPressed(evt);
            }
        });
        FormInput.add(TDializer);
        TDializer.setBounds(330, 140, 180, 23);

        jLabel48.setText("Dialisat :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(520, 140, 60, 23);

        TDialisat.setHighlighter(null);
        TDialisat.setName("TDialisat"); // NOI18N
        TDialisat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDialisatKeyPressed(evt);
            }
        });
        FormInput.add(TDialisat);
        TDialisat.setBounds(580, 140, 180, 23);

        jLabel37.setText("Transfusi :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(0, 180, 75, 23);

        TTransfusi.setHighlighter(null);
        TTransfusi.setName("TTransfusi"); // NOI18N
        TTransfusi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTransfusiKeyPressed(evt);
            }
        });
        FormInput.add(TTransfusi);
        TTransfusi.setBounds(80, 180, 50, 23);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("Kalf/Durante HD");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(140, 180, 90, 23);

        jLabel41.setText("Penarikan Cairan :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(270, 180, 100, 23);

        TPenarikan.setHighlighter(null);
        TPenarikan.setName("TPenarikan"); // NOI18N
        TPenarikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPenarikanKeyPressed(evt);
            }
        });
        FormInput.add(TPenarikan);
        TPenarikan.setBounds(370, 180, 50, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("ml");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(430, 180, 20, 23);

        jLabel29.setText("QB :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(440, 180, 35, 23);

        TQB.setHighlighter(null);
        TQB.setName("TQB"); // NOI18N
        TQB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TQBKeyPressed(evt);
            }
        });
        FormInput.add(TQB);
        TQB.setBounds(480, 180, 50, 23);

        jLabel42.setText("QD :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(530, 180, 30, 23);

        TQD.setHighlighter(null);
        TQD.setName("TQD"); // NOI18N
        TQD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TQDKeyPressed(evt);
            }
        });
        FormInput.add(TQD);
        TQD.setBounds(570, 180, 50, 23);

        jLabel49.setText("UFG :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(620, 180, 40, 23);

        TUFG.setHighlighter(null);
        TUFG.setName("TUFG"); // NOI18N
        TUFG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TUFGKeyPressed(evt);
            }
        });
        FormInput.add(TUFG);
        TUFG.setBounds(670, 180, 97, 23);

        jLabel50.setText("Heparin Awal :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(20, 220, 75, 23);

        THeparinAwal.setHighlighter(null);
        THeparinAwal.setName("THeparinAwal"); // NOI18N
        THeparinAwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THeparinAwalKeyPressed(evt);
            }
        });
        FormInput.add(THeparinAwal);
        THeparinAwal.setBounds(100, 220, 100, 23);

        jLabel51.setText("Heparin Pemeliharaan :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(210, 220, 140, 23);

        THeparinPemeliharaan.setHighlighter(null);
        THeparinPemeliharaan.setName("THeparinPemeliharaan"); // NOI18N
        THeparinPemeliharaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THeparinPemeliharaanKeyPressed(evt);
            }
        });
        FormInput.add(THeparinPemeliharaan);
        THeparinPemeliharaan.setBounds(350, 220, 100, 23);

        jLabel52.setText("Heparin Sirkulasi :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(450, 220, 115, 23);

        THeparinSirkulasi.setHighlighter(null);
        THeparinSirkulasi.setName("THeparinSirkulasi"); // NOI18N
        THeparinSirkulasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THeparinSirkulasiKeyPressed(evt);
            }
        });
        FormInput.add(THeparinSirkulasi);
        THeparinSirkulasi.setBounds(570, 220, 100, 23);

        jLabel53.setText("Volume Priming :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(0, 260, 100, 23);

        TVolumePriming.setHighlighter(null);
        TVolumePriming.setName("TVolumePriming"); // NOI18N
        TVolumePriming.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TVolumePrimingKeyPressed(evt);
            }
        });
        FormInput.add(TVolumePriming);
        TVolumePriming.setBounds(100, 260, 100, 23);

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
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,Tanggal);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(kddok.getText().trim().equals("")||namadokter.getText().trim().equals("")){
            Valid.textKosong(kddok,"Dokter P.J");
        }else if(TLama.getText().trim().equals("")){
            Valid.textKosong(TLama,"Lama Instruksi");
        }else if(TAkses.getText().trim().equals("")){
            Valid.textKosong(TAkses,"Akses Instruksi");
        }else if(TDializer.getText().trim().equals("")){
            Valid.textKosong(TDializer,"Dializer");
        }else if(TDialisat.getText().trim().equals("")){
            Valid.textKosong(TDialisat,"Dialisat");
        }else if(TTransfusi.getText().trim().equals("")){
            Valid.textKosong(TTransfusi,"Transfusi");
        }else if(TPenarikan.getText().trim().equals("")){
            Valid.textKosong(TPenarikan,"Penarikan Cairan");
        }else if(TQB.getText().trim().equals("")){
            Valid.textKosong(TQB,"QB");
        }else if(TQD.getText().trim().equals("")){
            Valid.textKosong(TQD,"QD");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                    simpan();
                }
            } 
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TVolumePriming,BtnBatal);
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
        if(tbObat.getSelectedRow()!= -1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(kddok.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
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
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(kddok.getText().trim().equals("")||namadokter.getText().trim().equals("")){
            Valid.textKosong(kddok,"Dokter P.J");
        }else if(TLama.getText().trim().equals("")){
            Valid.textKosong(TLama,"Lama Instruksi");
        }else if(TAkses.getText().trim().equals("")){
            Valid.textKosong(TAkses,"Akses Instruksi");
        }else if(TDializer.getText().trim().equals("")){
            Valid.textKosong(TDializer,"Dializer");
        }else if(TDialisat.getText().trim().equals("")){
            Valid.textKosong(TDialisat,"Dialisat");
        }else if(TTransfusi.getText().trim().equals("")){
            Valid.textKosong(TTransfusi,"Transfusi");
        }else if(TPenarikan.getText().trim().equals("")){
            Valid.textKosong(TPenarikan,"Penarikan Cairan");
        }else if(TQB.getText().trim().equals("")){
            Valid.textKosong(TQB,"QB");
        }else if(TQD.getText().trim().equals("")){
            Valid.textKosong(TQD,"QD");
        }else{        
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(kddok.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
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
        dokter.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
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
            if(TCari.getText().equals("")){ 
                Valid.MyReportqry("rptDataHemodialisa.jasper","report","::[ Data Hemodialis ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,hemodialisa.tanggal,hemodialisa.hd_ke,hemodialisa.no_mesin,hemodialisa.bb_kering,hemodialisa.bb_hd_terakhir, "+
                    "hemodialisa.lama,hemodialisa.akses,hemodialisa.dializer,hemodialisa.dialisat,hemodialisa.transfusi,hemodialisa.penarikan, "+
                    "hemodialisa.qb,hemodialisa.qd,hemodialisa.ufg,hemodialisa.heparin_awal,hemodialisa.heparin_pemeliharaan, "+
                    "hemodialisa.heparin_sirkulasi,hemodialisa.volume_priming,hemodialisa.kd_dokter,dokter.nm_dokter "+
                    "from hemodialisa inner join reg_periksa on hemodialisa.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on hemodialisa.kd_dokter=dokter.kd_dokter where "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' order by hemodialisa.tanggal ",param);
            }else{
                Valid.MyReportqry("rptDataHemodialisa.jasper","report","::[ Data Hemodialis ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,hemodialisa.tanggal,hemodialisa.hd_ke,hemodialisa.no_mesin,hemodialisa.bb_kering,hemodialisa.bb_hd_terakhir, "+
                    "hemodialisa.lama,hemodialisa.akses,hemodialisa.dializer,hemodialisa.dialisat,hemodialisa.transfusi,hemodialisa.penarikan, "+
                    "hemodialisa.qb,hemodialisa.qd,hemodialisa.ufg,hemodialisa.heparin_awal,hemodialisa.heparin_pemeliharaan, "+
                    "hemodialisa.heparin_sirkulasi,hemodialisa.volume_priming,hemodialisa.kd_dokter,dokter.nm_dokter "+
                    "from hemodialisa inner join reg_periksa on hemodialisa.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on hemodialisa.kd_dokter=dokter.kd_dokter where "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and hemodialisa.akses like '%"+TCari.getText().trim()+"%' or "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and hemodialisa.dializer like '%"+TCari.getText().trim()+"%' or "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and hemodialisa.dialisat like '%"+TCari.getText().trim()+"%' or "+
                    "hemodialisa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' "+
                    "order by hemodialisa.tanggal ",param);
            }  
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
        Valid.pindah(evt, BtnCari, TPasien);
    }
}//GEN-LAST:event_BtnAllKeyPressed

private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
    Valid.pindah(evt,TCari,Jam);
}//GEN-LAST:event_TanggalKeyPressed

private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
    // Valid.pindah(evt, TNm, BtnSimpan);
}//GEN-LAST:event_TNoRMKeyPressed

private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
    if(tabMode.getRowCount()!=0){
        try {
            getData();
        } catch (java.lang.NullPointerException e) {
        }
    }
}//GEN-LAST:event_tbObatMouseClicked

private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
    if(tabMode.getRowCount()!=0){
        if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }
}//GEN-LAST:event_tbObatKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
    isForm();
}//GEN-LAST:event_ChkInputActionPerformed

private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
    Valid.pindah(evt,Tanggal,Menit);
}//GEN-LAST:event_JamKeyPressed

private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
    Valid.pindah(evt,Jam,Detik);
}//GEN-LAST:event_MenitKeyPressed

private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
    Valid.pindah(evt,Menit,btnDokter);
}//GEN-LAST:event_DetikKeyPressed

private void kddokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        namadokter.setText(dokter.tampil3(kddok.getText()));
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Detik.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        THDKe.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnDokterActionPerformed(null);
    }
}//GEN-LAST:event_kddokKeyPressed

private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
    dokter.emptTeks();
    dokter.isCek();
    dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    dokter.setLocationRelativeTo(internalFrame1);
    dokter.setVisible(true);
}//GEN-LAST:event_btnDokterActionPerformed

private void TLamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TLamaKeyPressed
    Valid.pindah(evt,TBBHDTerakhir,TAkses);
}//GEN-LAST:event_TLamaKeyPressed

private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed
    Valid.pindah(evt,Detik,THDKe);
}//GEN-LAST:event_btnDokterKeyPressed

private void MnHemodialisaActionPerformed(java.awt.event.ActionEvent evt) {                                                              
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            dpjp=Sequel.cariIsi("select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            if(dpjp.equals("")){
                dpjp=Sequel.cariIsi("select dokter.nm_dokter from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            }
            param.put("dpjp",dpjp);   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptFormulirCatatanObservasiHemodialisa.jasper","report","::[ Formulir Catatan Observasi Hemodialisa ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                    "pasien.jk,pasien.tgl_lahir,catatan_observasi_hemodialisa.tgl_perawatan,catatan_observasi_hemodialisa.jam_rawat,catatan_observasi_hemodialisa.qb,catatan_observasi_hemodialisa.qd,"+
                    "catatan_observasi_hemodialisa.tekanan_arteri,catatan_observasi_hemodialisa.tekanan_vena,catatan_observasi_hemodialisa.tmp,catatan_observasi_hemodialisa.ufr,catatan_observasi_hemodialisa.tensi,"+
                    "catatan_observasi_hemodialisa.nadi,catatan_observasi_hemodialisa.suhu,catatan_observasi_hemodialisa.spo2,catatan_observasi_hemodialisa.tindakan,catatan_observasi_hemodialisa.ufg,"+
                    "catatan_observasi_hemodialisa.nip,petugas.nama from catatan_observasi_hemodialisa inner join reg_periksa on catatan_observasi_hemodialisa.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on catatan_observasi_hemodialisa.nip=petugas.nip where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' "+
                    "order by catatan_observasi_hemodialisa.tgl_perawatan,catatan_observasi_hemodialisa.jam_rawat",param);
        }
    }  

private void TAksesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAksesKeyPressed
    Valid.pindah(evt, TLama, TDializer);
}//GEN-LAST:event_TAksesKeyPressed

private void TDializerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDializerKeyPressed
    Valid.pindah(evt, TAkses, TDialisat);
}//GEN-LAST:event_TDializerKeyPressed

private void TDialisatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDialisatKeyPressed
    Valid.pindah(evt, TDializer, TTransfusi);
}//GEN-LAST:event_TDialisatKeyPressed

private void TTransfusiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTransfusiKeyPressed
    Valid.pindah(evt, TDialisat, TPenarikan);
}//GEN-LAST:event_TTransfusiKeyPressed

private void TPenarikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPenarikanKeyPressed
    Valid.pindah(evt, TTransfusi, TQB);
}//GEN-LAST:event_TPenarikanKeyPressed

private void TQBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TQBKeyPressed
    Valid.pindah(evt, TPenarikan, TQD);
}//GEN-LAST:event_TQBKeyPressed

private void TQDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TQDKeyPressed
    Valid.pindah(evt,TQB,TUFG);
}//GEN-LAST:event_TQDKeyPressed

private void THDKeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_THDKeKeyPressed
    Valid.pindah(evt,kddok,TNoMesin);
}//GEN-LAST:event_THDKeKeyPressed

private void TNoMesinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoMesinKeyPressed
    Valid.pindah(evt,THDKe,TBBKering);
}//GEN-LAST:event_TNoMesinKeyPressed

private void TBBKeringKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBBKeringKeyPressed
    Valid.pindah(evt,TNoMesin,TBBHDTerakhir);
}//GEN-LAST:event_TBBKeringKeyPressed

private void TBBHDTerakhirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBBHDTerakhirKeyPressed
    Valid.pindah(evt,TBBKering,TLama);
}//GEN-LAST:event_TBBHDTerakhirKeyPressed

private void TUFGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TUFGKeyPressed
    Valid.pindah(evt,TQD,THeparinAwal);
}//GEN-LAST:event_TUFGKeyPressed

private void THeparinAwalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_THeparinAwalKeyPressed
    Valid.pindah(evt,TUFG,THeparinPemeliharaan);
}//GEN-LAST:event_THeparinAwalKeyPressed

private void THeparinPemeliharaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_THeparinPemeliharaanKeyPressed
    Valid.pindah(evt,THeparinAwal,THeparinSirkulasi);
}//GEN-LAST:event_THeparinPemeliharaanKeyPressed

private void THeparinSirkulasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_THeparinSirkulasiKeyPressed
    Valid.pindah(evt,THeparinPemeliharaan,TVolumePriming);
}//GEN-LAST:event_THeparinSirkulasiKeyPressed

private void TVolumePrimingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TVolumePrimingKeyPressed
    Valid.pindah(evt,THeparinSirkulasi,BtnSimpan);
}//GEN-LAST:event_TVolumePrimingKeyPressed

/**
* @param args the command line arguments
*/
public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(() -> {
        RMHemodialisa dialog = new RMHemodialisa(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TAkses;
    private widget.TextBox TBBHDTerakhir;
    private widget.TextBox TBBKering;
    private widget.TextBox TCari;
    private widget.TextBox TDialisat;
    private widget.TextBox TDializer;
    private widget.TextBox THDKe;
    private widget.TextBox THeparinAwal;
    private widget.TextBox THeparinPemeliharaan;
    private widget.TextBox THeparinSirkulasi;
    private widget.TextBox TLama;
    private widget.TextBox TNoMesin;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPenarikan;
    private widget.TextBox TQB;
    private widget.TextBox TQD;
    private widget.TextBox TTransfusi;
    private widget.TextBox TUFG;
    private widget.TextBox TVolumePriming;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox Umur;
    private widget.Button btnDokter;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel29;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kddok;
    private widget.TextBox namadokter;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

public void tampil() {
    Valid.tabelKosong(tabMode);
    try{
        if(TCari.getText().toString().trim().equals("")){
            ps=koneksi.prepareStatement(
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                "pasien.jk,hemodialisa.tanggal,hemodialisa.hd_ke,hemodialisa.no_mesin,hemodialisa.bb_kering,hemodialisa.bb_hd_terakhir, "+
                "hemodialisa.lama,hemodialisa.akses,hemodialisa.dializer,hemodialisa.dialisat,hemodialisa.transfusi,hemodialisa.penarikan, "+
                "hemodialisa.qb,hemodialisa.qd,hemodialisa.ufg,hemodialisa.heparin_awal,hemodialisa.heparin_pemeliharaan, "+
                "hemodialisa.heparin_sirkulasi,hemodialisa.volume_priming,hemodialisa.kd_dokter,dokter.nm_dokter "+
                "from hemodialisa inner join reg_periksa on hemodialisa.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on hemodialisa.kd_dokter=dokter.kd_dokter where "+
                "hemodialisa.tanggal between ? and ? order by hemodialisa.tanggal ");
        }else{
            ps=koneksi.prepareStatement(
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                "pasien.jk,hemodialisa.tanggal,hemodialisa.hd_ke,hemodialisa.no_mesin,hemodialisa.bb_kering,hemodialisa.bb_hd_terakhir, "+
                "hemodialisa.lama,hemodialisa.akses,hemodialisa.dializer,hemodialisa.dialisat,hemodialisa.transfusi,hemodialisa.penarikan, "+
                "hemodialisa.qb,hemodialisa.qd,hemodialisa.ufg,hemodialisa.heparin_awal,hemodialisa.heparin_pemeliharaan, "+
                "hemodialisa.heparin_sirkulasi,hemodialisa.volume_priming,hemodialisa.kd_dokter,dokter.nm_dokter "+
                "from hemodialisa inner join reg_periksa on hemodialisa.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on hemodialisa.kd_dokter=dokter.kd_dokter "+
                "where hemodialisa.tanggal between ? and ? and "+
                "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or hemodialisa.akses like ? or "+
                "hemodialisa.dializer like ? or hemodialisa.dialisat like ? or dokter.nm_dokter like ?) "+
                "order by hemodialisa.tanggal ");
        }
            
        try {
            if(TCari.getText().toString().trim().equals("")){
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
            }else{
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,"%"+TCari.getText()+"%");
                ps.setString(5,"%"+TCari.getText()+"%");
                ps.setString(6,"%"+TCari.getText()+"%");
                ps.setString(7,"%"+TCari.getText()+"%");
                ps.setString(8,"%"+TCari.getText()+"%");
                ps.setString(9,"%"+TCari.getText()+"%");
            }
                
            rs=ps.executeQuery();
            while(rs.next()){
                tabMode.addRow(new Object[]{
                    rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                    rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),rs.getString("jk"),
                    rs.getString("tanggal"),rs.getString("hd_ke"),rs.getString("no_mesin"),
                    rs.getString("bb_kering"),rs.getString("bb_hd_terakhir"),rs.getString("lama"),
                    rs.getString("akses"),rs.getString("dializer"),rs.getString("dialisat"),
                    rs.getString("transfusi"),rs.getString("penarikan"),rs.getString("qb"),
                    rs.getString("qd"),rs.getString("ufg"),rs.getString("heparin_awal"),
                    rs.getString("heparin_pemeliharaan"),rs.getString("heparin_sirkulasi"),
                    rs.getString("volume_priming"),rs.getString("kd_dokter"),rs.getString("nm_dokter")
                });
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } finally{
            if(rs!=null){
                rs.close();
            }
            if(ps!=null){
                ps.close();
            }
        }
    }catch(SQLException e){
        System.out.println("Notifikasi : "+e);
    }
    int b=tabMode.getRowCount();
    LCount.setText(""+b);
}

public void emptTeks() {
    THDKe.setText("");
    TNoMesin.setText("");
    TBBKering.setText("");
    TBBHDTerakhir.setText("");
    TLama.setText("");
    TAkses.setText("Femoral / Cimino");
    TDializer.setText("");
    TDialisat.setText("Bicarbonat");
    TTransfusi.setText("0");
    TPenarikan.setText("0");
    TQB.setText("0");
    TQD.setText("0");
    TUFG.setText("");
    THeparinAwal.setText("");
    THeparinPemeliharaan.setText("");
    THeparinSirkulasi.setText("");
    TVolumePriming.setText("");
    Tanggal.setDate(new Date());
    Tanggal.requestFocus();
} 

private void getData() {
    if(tbObat.getSelectedRow()!= -1){
        TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
        TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
        TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
        Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
        JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
        Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());  
        Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(11,13));
        Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(14,16));
        Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(17,19));
        THDKe.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
        TNoMesin.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
        TBBKering.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
        TBBHDTerakhir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
        TLama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
        TAkses.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());  
        TDializer.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());  
        TDialisat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());  
        TTransfusi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
        TPenarikan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
        TQB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
        TQD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
        TUFG.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
        THeparinAwal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
        THeparinPemeliharaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
        THeparinSirkulasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
        TVolumePriming.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
        kddok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
        namadokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
    }
}

private void isRawat() {
    try {
        ps=koneksi.prepareStatement(
                "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur,reg_periksa.tgl_registrasi,"+
                "reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
        try {
            ps.setString(1,TNoRw.getText());
            rs=ps.executeQuery();
            if(rs.next()){
                TNoRM.setText(rs.getString("no_rkm_medis"));
                TPasien.setText(rs.getString("nm_pasien"));
                JK.setText(rs.getString("jk"));
                Umur.setText(rs.getString("umurdaftar")+" "+rs.getString("sttsumur"));
                TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } finally{
            if(rs!=null){
                rs.close();
            }
            if(ps!=null){
                ps.close();
            }
        }
    } catch (Exception e) {
        System.out.println("Notif : "+e);
    }
}

public void setNoRm(String norwt) {
    TNoRw.setText(norwt);
    TCari.setText(norwt);
    isRawat();
    ChkInput.setSelected(true);
    isForm();
}

private void isForm(){
    if(ChkInput.isSelected()==true){
        ChkInput.setVisible(false);
        PanelInput.setPreferredSize(new Dimension(WIDTH,366));
        FormInput.setVisible(true);      
        ChkInput.setVisible(true);
    }else if(ChkInput.isSelected()==false){           
        ChkInput.setVisible(false);            
        PanelInput.setPreferredSize(new Dimension(WIDTH,20));
        FormInput.setVisible(false);      
        ChkInput.setVisible(true);
    }
}

public void isCek(){
    BtnSimpan.setEnabled(akses.gethemodialisa());
    BtnHapus.setEnabled(akses.gethemodialisa());
    BtnEdit.setEnabled(akses.gethemodialisa());
    BtnPrint.setEnabled(akses.gethemodialisa()); 
    
    if(akses.getjml2()>=1){
        kddok.setEditable(false);
        btnDokter.setEnabled(false);
        kddok.setText(akses.getkode());
        namadokter.setText(dokter.tampil3(kddok.getText()));
        if(namadokter.getText().equals("")){
            kddok.setText("");
            JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
        }
    }
    
    if(TANGGALMUNDUR.equals("no")){
        if(!akses.getkode().equals("Admin Utama")){
            Tanggal.setEditable(false);
            Tanggal.setEnabled(false);
        }
    }
}

private void jam(){
    ActionListener taskPerformer = new ActionListener(){
        private int nilai_jam;
        private int nilai_menit;
        private int nilai_detik;
        public void actionPerformed(ActionEvent e) {
            String nol_jam = "";
            String nol_menit = "";
            String nol_detik = "";
            
            Date now = Calendar.getInstance().getTime();

            // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
            if(ChkKejadian.isSelected()==true){
                nilai_jam = now.getHours();
                nilai_menit = now.getMinutes();
                nilai_detik = now.getSeconds();
            }else if(ChkKejadian.isSelected()==false){
                nilai_jam =Jam.getSelectedIndex();
                nilai_menit =Menit.getSelectedIndex();
                nilai_detik =Detik.getSelectedIndex();
            }

            // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
            if (nilai_jam <= 9) {
                // Tambahkan "0" didepannya
                nol_jam = "0";
            }
            // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
            if (nilai_menit <= 9) {
                // Tambahkan "0" didepannya
                nol_menit = "0";
            }
            // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
            if (nilai_detik <= 9) {
                // Tambahkan "0" didepannya
                nol_detik = "0";
            }
            // Membuat String JAM, MENIT, DETIK
            String jam = nol_jam + Integer.toString(nilai_jam);
            String menit = nol_menit + Integer.toString(nilai_menit);
            String detik = nol_detik + Integer.toString(nilai_detik);
            // Menampilkan pada Layar
            //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
            Jam.setSelectedItem(jam);
            Menit.setSelectedItem(menit);
            Detik.setSelectedItem(detik);
        }
    };
    // Timer
    new Timer(1000, taskPerformer).start();
}

private void simpan() {
    // Menghitung jumlah parameter yang ada dalam query
    // Pastikan jumlah parameter dan tanda tanya (placeholders) sama
    if(Sequel.menyimpantf("hemodialisa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",20,new String[]{
        TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
        kddok.getText(),THDKe.getText(),TNoMesin.getText(),TBBKering.getText(),TBBHDTerakhir.getText(),TLama.getText(),TAkses.getText(),
        TDializer.getText(),TDialisat.getText(),TTransfusi.getText(),TPenarikan.getText(),TQB.getText(),TQD.getText(),TUFG.getText(),
        THeparinAwal.getText(),THeparinPemeliharaan.getText(),THeparinSirkulasi.getText(),TVolumePriming.getText()
    })==true){
        tabMode.addRow(new Object[]{
            TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Umur.getText(),JK.getText(),
            Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            THDKe.getText(),TNoMesin.getText(),TBBKering.getText(),TBBHDTerakhir.getText(),TLama.getText(),TAkses.getText(),TDializer.getText(),
            TDialisat.getText(),TTransfusi.getText(),TPenarikan.getText(),TQB.getText(),TQD.getText(),TUFG.getText(),THeparinAwal.getText(),
            THeparinPemeliharaan.getText(),THeparinSirkulasi.getText(),TVolumePriming.getText(),kddok.getText(),namadokter.getText()
        });
        LCount.setText(""+tabMode.getRowCount());
        emptTeks();
    } 
}

private void ganti() {
    // Menyesuaikan jumlah parameter dengan placeholder di query
    if(Sequel.mengedittf("hemodialisa","tanggal=? and no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,hd_ke=?,no_mesin=?,bb_kering=?,bb_hd_terakhir=?,lama=?,akses=?,dializer=?,dialisat=?,transfusi=?,penarikan=?,qb=?,qd=?,ufg=?,heparin_awal=?,heparin_pemeliharaan=?,heparin_sirkulasi=?,volume_priming=?",22,new String[]{
        TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),kddok.getText(),THDKe.getText(),TNoMesin.getText(),TBBKering.getText(),TBBHDTerakhir.getText(),TLama.getText(),TAkses.getText(),
        TDializer.getText(),TDialisat.getText(),TTransfusi.getText(),TPenarikan.getText(),TQB.getText(),TQD.getText(),TUFG.getText(),
        THeparinAwal.getText(),THeparinPemeliharaan.getText(),THeparinSirkulasi.getText(),TVolumePriming.getText(),
        tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
    })==true){
        tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
        tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
        tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
        tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),3);
        tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
        tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),5);
        tbObat.setValueAt(THDKe.getText(),tbObat.getSelectedRow(),6);
        tbObat.setValueAt(TNoMesin.getText(),tbObat.getSelectedRow(),7);
        tbObat.setValueAt(TBBKering.getText(),tbObat.getSelectedRow(),8);
        tbObat.setValueAt(TBBHDTerakhir.getText(),tbObat.getSelectedRow(),9);
        tbObat.setValueAt(TLama.getText(),tbObat.getSelectedRow(),10);
        tbObat.setValueAt(TAkses.getText(),tbObat.getSelectedRow(),11);
        tbObat.setValueAt(TDializer.getText(),tbObat.getSelectedRow(),12);
        tbObat.setValueAt(TDialisat.getText(),tbObat.getSelectedRow(),13);
        tbObat.setValueAt(TTransfusi.getText(),tbObat.getSelectedRow(),14);
        tbObat.setValueAt(TPenarikan.getText(),tbObat.getSelectedRow(),15);
        tbObat.setValueAt(TQB.getText(),tbObat.getSelectedRow(),16);
        tbObat.setValueAt(TQD.getText(),tbObat.getSelectedRow(),17);
        tbObat.setValueAt(TUFG.getText(),tbObat.getSelectedRow(),18);
        tbObat.setValueAt(THeparinAwal.getText(),tbObat.getSelectedRow(),19);
        tbObat.setValueAt(THeparinPemeliharaan.getText(),tbObat.getSelectedRow(),20);
        tbObat.setValueAt(THeparinSirkulasi.getText(),tbObat.getSelectedRow(),21);
        tbObat.setValueAt(TVolumePriming.getText(),tbObat.getSelectedRow(),22);
        tbObat.setValueAt(kddok.getText(),tbObat.getSelectedRow(),23);
        tbObat.setValueAt(namadokter.getText(),tbObat.getSelectedRow(),24);
        emptTeks();
    }
}

private void hapus() {
    if(Sequel.queryu2tf("delete from hemodialisa where tanggal=? and no_rawat=?",2,new String[]{
        tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
    })==true){
        tabMode.removeRow(tbObat.getSelectedRow());
        LCount.setText(""+tabMode.getRowCount());
        emptTeks();
    }else{
        JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
    }
}

}