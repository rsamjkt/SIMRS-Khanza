/*
 * Adapted for Surat Pemilihan DPJP - with Photo Panel restored
 * - Added logic for BertindakAtas "Diri Sendiri" to autofill NamaPJ and AlamatPJ
 * - Fixed logical error in setNoRm condition check
 */

 package surat;

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
 import kepegawaian.DlgCariDokter;
 import kepegawaian.DlgCariPetugas;


 /**
  *
  * @author windiartohugroho (adapted)
  */
 public final class SuratPemilihanDPJP extends javax.swing.JDialog {
     private final DefaultTableModel tabMode;
     private Connection koneksi=koneksiDB.condb();
     private sekuel Sequel=new sekuel();
     private validasi Valid=new validasi();
     private PreparedStatement ps;
     private ResultSet rs;
     private int i=0;
     private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
     private DlgCariDokter dokter=new DlgCariDokter(null,false);
     private String finger="", lokasifile=""; // Added lokasifile and finger

     public SuratPemilihanDPJP(java.awt.Frame parent, boolean modal) {
         super(parent, modal);
         initComponents();
         this.setLocation(8,1);
         // Adjust size to accommodate photo panel again
         setSize(1090, 500); // Example size adjustment (Wider)

         // Table Model (same as previous DPJP version)
         tabMode=new DefaultTableModel(null,new Object[]{
             "No. Surat","No. Rawat","No. R.M.","Nama Pasien","Tgl. Lahir","Tgl. Surat",
             "Penandatangan","Alamat P.J.","Hubungan","Kode Dokter","Nama Dokter","NIP Petugas","Nama Petugas"
         }){
               @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
         };
         tbSurat.setModel(tabMode);

         tbSurat.setPreferredScrollableViewportSize(new Dimension(500,500));
         tbSurat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

         // Column widths (same as previous DPJP version)
         for (i = 0; i < 13; i++) {
             TableColumn column = tbSurat.getColumnModel().getColumn(i);
              if(i==0){ column.setPreferredWidth(110); // No. Surat
             }else if(i==1){ column.setPreferredWidth(105); // No. Rawat
             }else if(i==2){ column.setPreferredWidth(70); // No. R.M.
             }else if(i==3){ column.setPreferredWidth(170); // Nama Pasien
             }else if(i==4){ column.setPreferredWidth(75); // Tgl. Lahir
             }else if(i==5){ column.setPreferredWidth(75); // Tgl. Surat
             }else if(i==6){ column.setPreferredWidth(170); // Penandatangan (nama_pj)
             }else if(i==7){ column.setPreferredWidth(250); // Alamat P.J.
             }else if(i==8){ column.setPreferredWidth(80); // Hubungan (bertindak_atas)
             }else if(i==9){ column.setPreferredWidth(80); // Kode Dokter
             }else if(i==10){ column.setPreferredWidth(170); // Nama Dokter
             }else if(i==11){ column.setPreferredWidth(100); // NIP Petugas
             }else if(i==12){ column.setPreferredWidth(170); // Nama Petugas
             }
         }
         tbSurat.setDefaultRenderer(Object.class, new WarnaTable());

         // Input limits (same as previous DPJP version)
         TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
         NIP.setDocument(new batasInput((byte)20).getKata(NIP));
         NoSurat.setDocument(new batasInput((byte)20).getKata(NoSurat));
         KdDokter.setDocument(new batasInput((byte)20).getKata(KdDokter));
         TCari.setDocument(new batasInput((int)100).getKata(TCari));
         NamaPJ.setDocument(new batasInput((byte)100).getKata(NamaPJ));
         AlamatPJ.setDocument(new batasInput((int)200).getFilter(AlamatPJ));

         if(koneksiDB.CARICEPAT().equals("aktif")){
             TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                 @Override public void insertUpdate(DocumentEvent e) { if(TCari.getText().length()>2){ tampil(); } }
                 @Override public void removeUpdate(DocumentEvent e) { if(TCari.getText().length()>2){ tampil(); } }
                 @Override public void changedUpdate(DocumentEvent e) { if(TCari.getText().length()>2){ tampil(); } }
             });
         }

         // Listener Petugas (same)
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

         // Listener Dokter (same)
         dokter.addWindowListener(new WindowListener() {
             @Override public void windowOpened(WindowEvent e) {}
             @Override public void windowClosing(WindowEvent e) {}
             @Override public void windowClosed(WindowEvent e) {
                 if(dokter.getTable().getSelectedRow()!= -1){
                     KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                     NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                     KdDokter.requestFocus();
                 }
             }
             @Override public void windowIconified(WindowEvent e) {}
             @Override public void windowDeiconified(WindowEvent e) {}
             @Override public void windowActivated(WindowEvent e) {}
             @Override public void windowDeactivated(WindowEvent e) {}
         });

         // ****** START: Added ActionListener for BertindakAtas ******
         BertindakAtas.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 isPasien(); // Panggil method isPasien setiap kali pilihan berubah
             }
         });
         // ****** END: Added ActionListener for BertindakAtas ******

         ChkInput.setSelected(false);
         isForm();

         // Restore photo panel setup
         ChkAccor.setSelected(false);
         isPhoto();

         HTMLEditorKit kit = new HTMLEditorKit();
         LoadHTML2.setEditable(true);
         LoadHTML2.setEditorKit(kit);
         StyleSheet styleSheet = kit.getStyleSheet();
         styleSheet.addRule( // Same CSS rules as original
                 ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                 ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                 ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                 ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                 ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                 ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                 ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                 ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                 ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
         );
         Document doc = kit.createDefaultDocument();
         LoadHTML2.setDocument(doc);

         // Pastikan isPasien dipanggil setelah komponen diinisialisasi
         isPasien();
     }


     /** This method is called from within the constructor to
      * initialize the form.
      * WARNING: Do NOT modify this code. The content of this method is
      * always regenerated by the Form Editor.
      *
      * NOTE: You need to add the PanelAccor back in the GUI builder.
      *       It should contain ChkAccor, FormPhoto (with Scroll5/LoadHTML2 and FormPass3/buttons).
      *       Position it appropriately (e.g., BorderLayout.EAST).
      */
     @SuppressWarnings("unchecked")
     // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
     private void initComponents() {
 
         JK = new widget.TextBox();
         LahirPasien = new widget.TextBox();
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
         Tanggal = new widget.Tanggal();
         jLabel3 = new widget.Label();
         NoSurat = new widget.TextBox();
         jLabel10 = new widget.Label();
         NamaPJ = new widget.TextBox();
         jLabel8 = new widget.Label();
         BertindakAtas = new widget.ComboBox();
         jLabel22 = new widget.Label();
         AlamatPJ = new widget.TextBox();
         jLabel18 = new widget.Label();
         NIP = new widget.TextBox();
         NamaPetugas = new widget.TextBox();
         btnPetugas = new widget.Button();
         jLabel23 = new widget.Label();
         KdDokter = new widget.TextBox();
         NmDokter = new widget.TextBox();
         btnDokter = new widget.Button();
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
 
         JK.setEditable(false);
         JK.setHighlighter(null);
         JK.setName("JK"); // NOI18N
 
         LahirPasien.setEditable(false);
         LahirPasien.setHighlighter(null);
         LahirPasien.setName("LahirPasien"); // NOI18N
 
         setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
         setUndecorated(true);
         setResizable(false);
 
         internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Surat Pemilihan DPJP ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
 
         jLabel19.setText("Tgl. Surat :");
         jLabel19.setName("jLabel19"); // NOI18N
         jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
         panelGlass9.add(jLabel19);
 
         DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
         DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-05-2024" }));
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
         DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-05-2024" }));
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
         PanelInput.setPreferredSize(new java.awt.Dimension(192, 185));
         PanelInput.setLayout(new java.awt.BorderLayout(1, 1));
 
         FormInput.setName("FormInput"); // NOI18N
         FormInput.setPreferredSize(new java.awt.Dimension(100, 165));
         FormInput.setLayout(null);
 
         jLabel4.setText("No.Rawat :");
         jLabel4.setName("jLabel4"); // NOI18N
         FormInput.add(jLabel4);
         jLabel4.setBounds(0, 10, 70, 23);
 
         TNoRw.setHighlighter(null);
         TNoRw.setName("TNoRw"); // NOI18N
         TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 TNoRwKeyPressed(evt);
             }
         });
         FormInput.add(TNoRw);
         TNoRw.setBounds(74, 10, 136, 23);
 
         TPasien.setEditable(false);
         TPasien.setHighlighter(null);
         TPasien.setName("TPasien"); // NOI18N
         FormInput.add(TPasien);
         TPasien.setBounds(325, 10, 255, 23);
 
         TNoRM.setEditable(false);
         TNoRM.setHighlighter(null);
         TNoRM.setName("TNoRM"); // NOI18N
         FormInput.add(TNoRM);
         TNoRM.setBounds(212, 10, 111, 23);
 
         jLabel17.setText("Pasien :");
         jLabel17.setName("jLabel17"); // NOI18N
         FormInput.add(jLabel17);
         jLabel17.setBounds(580, 10, 50, 23);
 
         jLabel16.setText("Tgl. Surat :");
         jLabel16.setName("jLabel16"); // NOI18N
         jLabel16.setVerifyInputWhenFocusTarget(false);
         FormInput.add(jLabel16);
         jLabel16.setBounds(0, 40, 70, 23);
 
         Tanggal.setForeground(new java.awt.Color(50, 70, 50));
         Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-05-2024" }));
         Tanggal.setDisplayFormat("dd-MM-yyyy");
         Tanggal.setName("Tanggal"); // NOI18N
         Tanggal.setOpaque(false);
         Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 TanggalKeyPressed(evt);
             }
         });
         FormInput.add(Tanggal);
         Tanggal.setBounds(74, 40, 90, 23);
 
         jLabel3.setText("No. Surat :");
         jLabel3.setName("jLabel3"); // NOI18N
         FormInput.add(jLabel3);
         jLabel3.setBounds(530, 40, 90, 23);
 
         NoSurat.setHighlighter(null);
         NoSurat.setName("NoSurat"); // NOI18N
         NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 NoSuratKeyPressed(evt);
             }
         });
         FormInput.add(NoSurat);
         NoSurat.setBounds(624, 40, 119, 23);
 
         jLabel10.setText("Penandatangan :");
         jLabel10.setName("jLabel10"); // NOI18N
         FormInput.add(jLabel10);
         jLabel10.setBounds(0, 70, 85, 23);
 
         NamaPJ.setName("NamaPJ"); // NOI18N
         NamaPJ.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 NamaPJKeyPressed(evt);
             }
         });
         FormInput.add(NamaPJ);
         NamaPJ.setBounds(89, 70, 370, 23);
 
         jLabel8.setText("Bertindak Untuk/Atas Nama :");
         jLabel8.setName("jLabel8"); // NOI18N
         FormInput.add(jLabel8);
         jLabel8.setBounds(469, 70, 160, 23);
 
         BertindakAtas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Diri Sendiri", "Suami", "Istri", "Anak", "Ayah", "Ibu", "Kakak", "Adik", "Wali", "Saudara", "Lainnya" }));
         BertindakAtas.setName("BertindakAtas"); // NOI18N
         BertindakAtas.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 BertindakAtasKeyPressed(evt);
             }
         });
         FormInput.add(BertindakAtas);
         BertindakAtas.setBounds(633, 70, 110, 23);
 
         jLabel22.setText("Alamat P.J. :");
         jLabel22.setName("jLabel22"); // NOI18N
         FormInput.add(jLabel22);
         jLabel22.setBounds(0, 100, 85, 23);
 
         AlamatPJ.setHighlighter(null);
         AlamatPJ.setName("AlamatPJ"); // NOI18N
         AlamatPJ.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 AlamatPJKeyPressed(evt);
             }
         });
         FormInput.add(AlamatPJ);
         AlamatPJ.setBounds(89, 100, 654, 23);
 
         jLabel18.setText("Petugas :");
         jLabel18.setName("jLabel18"); // NOI18N
         FormInput.add(jLabel18);
         jLabel18.setBounds(0, 130, 85, 23);
 
         NIP.setEditable(false);
         NIP.setHighlighter(null);
         NIP.setName("NIP"); // NOI18N
         FormInput.add(NIP);
         NIP.setBounds(89, 130, 100, 23);
 
         NamaPetugas.setEditable(false);
         NamaPetugas.setName("NamaPetugas"); // NOI18N
         FormInput.add(NamaPetugas);
         NamaPetugas.setBounds(191, 130, 185, 23);
 
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
         btnPetugas.setBounds(378, 130, 28, 23);
 
         jLabel23.setText("DPJP Pilihan:");
         jLabel23.setName("jLabel23"); // NOI18N
         FormInput.add(jLabel23);
         jLabel23.setBounds(410, 130, 80, 23);
 
         KdDokter.setEditable(false);
         KdDokter.setHighlighter(null);
         KdDokter.setName("KdDokter"); // NOI18N
         FormInput.add(KdDokter);
         KdDokter.setBounds(494, 130, 80, 23);
 
         NmDokter.setEditable(false);
         NmDokter.setName("NmDokter"); // NOI18N
         FormInput.add(NmDokter);
         NmDokter.setBounds(576, 130, 140, 23);
 
         btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
         btnDokter.setMnemonic('1');
         btnDokter.setToolTipText("Alt+1");
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
         btnDokter.setBounds(718, 130, 28, 23);
 
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
         PanelAccor.setPreferredSize(new java.awt.Dimension(430, 43));
         PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));
 
         ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
         ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
         ChkAccor.setSelected(true);
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
         FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Bukti/Scan Surat Pemilihan DPJP : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
 
     private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
          // Validation remains the same as previous DPJP version
         if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
             Valid.textKosong(TNoRw,"Pasien");
         }else if(NamaPJ.getText().trim().equals("")){
             Valid.textKosong(NamaPJ,"Nama Penandatangan");
         }else if(AlamatPJ.getText().trim().equals("")){
             Valid.textKosong(AlamatPJ,"Alamat Penandatangan");
         }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
             Valid.textKosong(btnDokter,"Dokter DPJP Pilihan");
         }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
             Valid.textKosong(btnPetugas,"Petugas");
         }else if(NoSurat.getText().trim().equals("")){
             Valid.textKosong(NoSurat,"No. Surat");
         }else{
             // INSERT remains the same as previous DPJP version
             if(Sequel.menyimpantf("surat_pemilihan_dpjp","?,?,?,?,?,?,?,?,?","Data",9,new String[]{
                     NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),TNoRM.getText(),
                     NamaPJ.getText(),AlamatPJ.getText(),BertindakAtas.getSelectedItem().toString(),
                     KdDokter.getText(),NIP.getText()
                 })==true){
                 // AddRow remains the same as previous DPJP version
                  tabMode.addRow(new String[]{
                     NoSurat.getText(), TNoRw.getText(), TNoRM.getText(), TPasien.getText(), LahirPasien.getText(),
                     Valid.SetTgl(Tanggal.getSelectedItem()+""), NamaPJ.getText(), AlamatPJ.getText(),
                     BertindakAtas.getSelectedItem().toString(), KdDokter.getText(), NmDokter.getText(),
                     NIP.getText(), NamaPetugas.getText()
                 });
                  LCount.setText(""+tabMode.getRowCount());
                  emptTeks();
             }
         }
     }//GEN-LAST:event_BtnSimpanActionPerformed
 
     private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
         // Navigation remains the same
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
             BtnSimpanActionPerformed(null);
         }else{
             Valid.pindah(evt,btnDokter,BtnBatal);
         }
     }//GEN-LAST:event_BtnSimpanKeyPressed
 
     private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
         // Logic remains the same
         emptTeks();
         ChkInput.setSelected(true);
         isForm();
     }//GEN-LAST:event_BtnBatalActionPerformed
 
     private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
          // Logic remains the same
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
             emptTeks();
         }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
     }//GEN-LAST:event_BtnBatalKeyPressed
 
     private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
         // Logic remains the same (using index 11 for NIP)
         if(tbSurat.getSelectedRow()>-1){
             if(akses.getkode().equals("Admin Utama")){
                 hapus();
             }else{
                 // Cek apakah NIP di tabel (indeks 11) sama dengan NIP di form
                 if(NIP.getText().equals(tbSurat.getValueAt(tbSurat.getSelectedRow(), 11).toString())){
                     hapus();
                 }else{
                     JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas ("+tbSurat.getValueAt(tbSurat.getSelectedRow(), 12).toString()+") yang membuat surat ini!");
                 }
             }
         }else{
             JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
         }
     }//GEN-LAST:event_BtnHapusActionPerformed
 
     private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
         // Logic remains the same
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
             BtnHapusActionPerformed(null);
         }else{
             Valid.pindah(evt, BtnBatal, BtnEdit);
         }
     }//GEN-LAST:event_BtnHapusKeyPressed
 
     private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
         // Validation and ganti() call remain the same (using index 11 for NIP check)
         if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
             Valid.textKosong(TNoRw,"Pasien");
         }else if(NamaPJ.getText().trim().equals("")){
             Valid.textKosong(NamaPJ,"Nama Penandatangan");
         }else if(AlamatPJ.getText().trim().equals("")){
             Valid.textKosong(AlamatPJ,"Alamat Penandatangan");
         }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
             Valid.textKosong(btnDokter,"Dokter DPJP Pilihan");
         }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
             Valid.textKosong(btnPetugas,"Petugas");
         }else if(NoSurat.getText().trim().equals("")){
             Valid.textKosong(NoSurat,"No. Surat");
         }else{
             if(tbSurat.getSelectedRow()>-1){
                 if(akses.getkode().equals("Admin Utama")){
                     ganti();
                 }else{
                     // Cek apakah NIP di tabel (indeks 11) sama dengan NIP di form
                     if(NIP.getText().equals(tbSurat.getValueAt(tbSurat.getSelectedRow(),11).toString())){
                         ganti();
                     }else{
                         JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas ("+tbSurat.getValueAt(tbSurat.getSelectedRow(), 12).toString()+") yang membuat surat ini!");
                     }
                 }
             }else{
                 JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
             }
         }
     }//GEN-LAST:event_BtnEditActionPerformed
 
     private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
         // Logic remains the same
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
             BtnEditActionPerformed(null);
         }else{
             Valid.pindah(evt, BtnHapus, BtnPrint);
         }
     }//GEN-LAST:event_BtnEditKeyPressed
 
     private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
         // Logic remains the same
         petugas.dispose();
         dokter.dispose();
         dispose();
     }//GEN-LAST:event_BtnKeluarActionPerformed
 
     private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
         // Logic remains the same
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
             BtnKeluarActionPerformed(null);
         }else{Valid.pindah(evt,BtnPrint,TCari);}
     }//GEN-LAST:event_BtnKeluarKeyPressed
 
     private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        // Logic remains the same as previous DPJP version (needs specific report)
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
 
             // Filter selection based on current state (one row selected or all based on date range)
             String filter = " WHERE s.tanggal BETWEEN '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' AND '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
             if (!TCari.getText().trim().equals("")) {
                 filter += " AND (s.no_surat LIKE '%"+TCari.getText().trim()+"%' OR s.no_rawat LIKE '%"+TCari.getText().trim()+"%' OR s.no_rkm_medis LIKE '%"+TCari.getText().trim()+"%' OR p.nm_pasien LIKE '%"+TCari.getText().trim()+"%' OR s.nama_pj LIKE '%"+TCari.getText().trim()+"%' OR d.nm_dokter LIKE '%"+TCari.getText().trim()+"%' OR pt.nama LIKE '%"+TCari.getText().trim()+"%') ";
             }
 
             // Jika sebuah baris di tabel dipilih, utamakan cetak data dari baris itu
             if (tbSurat.getSelectedRow() > -1 && !NoSurat.getText().isEmpty() && NoSurat.getText().equals(tbSurat.getValueAt(tbSurat.getSelectedRow(), 0).toString()) ) {
                  filter = " WHERE s.no_surat = '"+tbSurat.getValueAt(tbSurat.getSelectedRow(), 0).toString()+"' ";
             }
 
             String reportQuery = "SELECT s.no_surat, s.no_rawat, s.tanggal AS tgl_surat, s.no_rkm_medis, p.nm_pasien, " +
                                  "p.tgl_lahir, p.jk, s.nama_pj, s.alamat_pj, s.bertindak_atas, s.kd_dokter, d.nm_dokter, " +
                                  "s.nip, pt.nama AS nama_petugas " +
                                  "FROM surat_pemilihan_dpjp s " +
                                  "INNER JOIN reg_periksa rp ON s.no_rawat = rp.no_rawat " +
                                  "INNER JOIN pasien p ON s.no_rkm_medis = p.no_rkm_medis " +
                                  "INNER JOIN dokter d ON s.kd_dokter = d.kd_dokter " +
                                  "INNER JOIN petugas pt ON s.nip = pt.nip " +
                                  filter +
                                  "ORDER BY s.tanggal, s.no_surat";
 
             // *** Replace "rptSuratPemilihanDPJP.jasper" with your actual report file name ***
             Valid.MyReportqry("rptSuratPemilihanDPJP.jasper", "report", "::[ Data Surat Pemilihan DPJP ]::", reportQuery, param);
         }
         this.setCursor(Cursor.getDefaultCursor());
     }//GEN-LAST:event_BtnPrintActionPerformed
 
     private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        // Logic remains the same
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
             BtnPrintActionPerformed(null);
         }else{
             Valid.pindah(evt, BtnEdit, BtnKeluar);
         }
     }//GEN-LAST:event_BtnPrintKeyPressed
 
     private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        // Logic remains the same
         if(evt.getKeyCode()==KeyEvent.VK_ENTER){
             BtnCariActionPerformed(null);
         }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
             BtnCari.requestFocus();
         }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
             BtnKeluar.requestFocus();
         }
     }//GEN-LAST:event_TCariKeyPressed
 
     private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        // Logic remains the same
         tampil();
     }//GEN-LAST:event_BtnCariActionPerformed
 
     private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        // Logic remains the same
         if(evt.getKeyCode()==KeyEvent.VK_SPACE){
             BtnCariActionPerformed(null);
         }else{
             Valid.pindah(evt, TCari, BtnAll);
         }
     }//GEN-LAST:event_BtnCariKeyPressed
 
     private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
         // Logic remains the same
         TCari.setText("");
         tampil();
     }//GEN-LAST:event_BtnAllActionPerformed
 
     private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
         // Logic remains the same
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
                 if (ChkAccor.isSelected()) { // Hanya panggil jika panel terbuka
                    panggilPhoto();
                 }
             } catch (java.lang.NullPointerException e) { }
         }
     }//GEN-LAST:event_tbSuratMouseClicked
 
     private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        // Logic remains the same
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
                     if (ChkAccor.isSelected()) { // Hanya panggil jika panel terbuka
                        panggilPhoto();
                     }
                 } catch (java.lang.NullPointerException e) { }
             } else if (evt.getKeyCode() == KeyEvent.VK_SPACE) { // Tambahkan event space untuk view photo
                 try {
                     getData();
                     ChkAccor.setSelected(true); // Otomatis buka panel photo
                     isPhoto(); // Update tampilan panel
                     panggilPhoto(); // Panggil photo setelah panel dibuka
                 } catch (java.lang.NullPointerException e) { }
             }
         }
     }//GEN-LAST:event_tbSuratKeyReleased
 
     private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        // Pindah ke DPJP Pilihan (btnDokter) setelah petugas dipilih
         Valid.pindah(evt,AlamatPJ,btnDokter);
     }//GEN-LAST:event_btnPetugasKeyPressed
 
     private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
         // Logic remains the same
         petugas.emptTeks();
         petugas.isCek();
         petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
         petugas.setLocationRelativeTo(internalFrame1);
         petugas.setVisible(true);
     }//GEN-LAST:event_btnPetugasActionPerformed
 
     private void BertindakAtasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BertindakAtasKeyPressed
         // Pindah ke AlamatPJ jika bisa diedit, atau langsung ke btnPetugas jika tidak
         if (AlamatPJ.isEditable()) {
             Valid.pindah(evt, NamaPJ, AlamatPJ); // Pindah normal ke AlamatPJ
         } else {
             Valid.pindah(evt, NamaPJ, btnPetugas); // Langsung ke petugas karena NamaPJ & AlamatPJ disable
         }
     }//GEN-LAST:event_BertindakAtasKeyPressed
 
     private void NamaPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaPJKeyPressed
         // Pindah ke Bertindak Atas jika field NamaPJ bisa diedit
         Valid.pindah(evt,NoSurat,BertindakAtas);
     }//GEN-LAST:event_NamaPJKeyPressed
 
     private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
         // Logic remains the same
         if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
             isRawat();
             isPasien(); // Panggil isPasien setelah data pasien didapat
         }else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
             isRawat();
             isPasien(); // Panggil isPasien setelah data pasien didapat
             Tanggal.requestFocus(); // Pindah fokus ke Tanggal setelah Enter di No Rawat
         } else{
             Valid.pindah(evt,TCari,Tanggal);
         }
     }//GEN-LAST:event_TNoRwKeyPressed
 
     private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
         // Logic remains the same
         Valid.pindah(evt,TNoRw,NoSurat);
     }//GEN-LAST:event_TanggalKeyPressed
 
     private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
         // Pindah ke NamaPJ jika NamaPJ bisa diedit, atau langsung ke BertindakAtas
         if (NamaPJ.isEditable()) {
            Valid.pindah(evt,Tanggal,NamaPJ);
         } else {
            Valid.pindah(evt,Tanggal,BertindakAtas);
         }
     }//GEN-LAST:event_NoSuratKeyPressed
 
     private void AlamatPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatPJKeyPressed
        // Pindah ke tombol Petugas setelah mengisi Alamat PJ
         Valid.pindah(evt, BertindakAtas, btnPetugas);
     }//GEN-LAST:event_AlamatPJKeyPressed
 
     private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        // Logic remains the same
         dokter.emptTeks();
         dokter.isCek();
         dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
         dokter.setLocationRelativeTo(internalFrame1);
         dokter.setVisible(true);
     }//GEN-LAST:event_btnDokterActionPerformed
 
     private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed
        // Logic remains the same
         Valid.pindah(evt, btnPetugas, BtnSimpan);
     }//GEN-LAST:event_btnDokterKeyPressed
 
     //<editor-fold defaultstate="collapsed" desc="Photo Panel Actions (Restored)">
     private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
         if(tbSurat.getSelectedRow()!= -1){
             isPhoto();
             if (ChkAccor.isSelected()) { // Hanya panggil photo jika panel dibuka
                 panggilPhoto(); // Call panggilPhoto after toggling
             } else {
                 LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Photo/Scan Belum Tersedia</font></center></body></html>");
                 lokasifile = "";
             }
         }else{
             ChkAccor.setSelected(false); // Keep it closed if no row selected
             isPhoto(); // Update panel view even if closed
             JOptionPane.showMessageDialog(null,"Silahkan pilih data surat pada tabel terlebih dahulu..!!!");
         }
     }//GEN-LAST:event_ChkAccorActionPerformed
 
     private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
                                       
    if(tabMode.getRowCount()==0){
        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        TCari.requestFocus();
    }else{
        if(tbSurat.getSelectedRow()>-1){
            Sequel.queryu("delete from antripemilihandpjp");
            Sequel.queryu("insert into antripemilihandpjp values('"+tbSurat.getValueAt(tbSurat.getSelectedRow(),0).toString()+"','"+tbSurat.getValueAt(tbSurat.getSelectedRow(),1).toString()+"')");
            Sequel.queryu("delete from surat_pemilihan_dpjp_bukti where no_surat='"+tbSurat.getValueAt(tbSurat.getSelectedRow(),0).toString()+"'");
            
            // Opsional: Tambahkan pesan konfirmasi
            JOptionPane.showMessageDialog(rootPane,"Nomor surat "+tbSurat.getValueAt(tbSurat.getSelectedRow(),0).toString()+" siap untuk pengambilan bukti/scan.");
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data surat pada tabel terlebih dahulu..!!");
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
      if(tbSurat.getSelectedRow()>-1){
    if(lokasifile.equals("")){
        JOptionPane.showMessageDialog(null,"Maaf, Silahkan ambil photo bukti pemilihan DPJP terlebih dahulu..!!!!");
    }else{
        Map<String, Object> param = new HashMap<>();
        param.put("namars",akses.getnamars());
        param.put("alamatrs",akses.getalamatrs());
        param.put("kotars",akses.getkabupatenrs());
        param.put("propinsirs",akses.getpropinsirs());
        param.put("kontakrs",akses.getkontakrs());
        param.put("emailrs",akses.getemailrs());
        param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
        param.put("photo","http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/pemilihanDPJP/"+lokasifile);
        
        // Karena tabel hanya memiliki 13 kolom, kita perlu mengambil NIP langsung dari tabel database
        // daripada mengakses indeks 16 dan 17 yang tidak ada
        String noSurat = tbSurat.getValueAt(tbSurat.getSelectedRow(),0).toString();
        String tanggalSurat = tbSurat.getValueAt(tbSurat.getSelectedRow(),2).toString(); // Perkiraan indeks untuk tanggal
        
        // Ambil NIP dan nama petugas langsung dari database
        String nip = Sequel.cariIsi("SELECT nip FROM surat_pemilihan_dpjp WHERE no_surat='"+noSurat+"'");
        String namaPetugas = Sequel.cariIsi("SELECT petugas.nama FROM surat_pemilihan_dpjp INNER JOIN petugas ON surat_pemilihan_dpjp.nip=petugas.nip WHERE surat_pemilihan_dpjp.no_surat='"+noSurat+"'");
        
        // Ambil sidik jari berdasarkan NIP yang didapat
        String finger = Sequel.cariIsi("SELECT sha1(sidikjari.sidikjari) FROM sidikjari INNER JOIN pegawai ON pegawai.id=sidikjari.id WHERE pegawai.nik=?", nip);
        
        //param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+namaPetugas+"\nID "+(finger.equals("")?nip:finger)+"\n"+Valid.SetTgl3(tanggalSurat));
        param.put("finger_petugas","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+namaPetugas+"\nID "+(finger.equals("")?nip:finger)+"\n"+Valid.SetTgl3(tanggalSurat));
        Valid.MyReportqry("rptSuratPemilihanDPJP.jasper","report","::[ Surat Pemilihan DPJP ]::",
            "SELECT " +
            "rp.no_rawat, " +
            "p.no_rkm_medis, " +
            "p.nm_pasien, " +
            "IF(p.jk='L','LAKI-LAKI','PEREMPUAN') AS jk, " +
            "p.umur, " +
            "DATE_FORMAT(p.tgl_lahir,'%d-%m-%Y') AS tgl_lahir, " +
            "CONCAT(p.alamat,', ',kel.nm_kel,', ',kec.nm_kec,', ',kab.nm_kab) AS alamat_pasien, " +
            "p.no_tlp, " +
            "DATE_FORMAT(spdj.tanggal,'%d-%m-%Y') AS tanggal_surat, " +
            "spdj.tanggal AS tanggal_surat_orig, " +
            "spdj.no_surat, " +
            "spdj.nama_pj, " +
            "spdj.alamat_pj, " +
            "spdj.bertindak_atas, " +
            "spdj.kd_dokter, " +
            "d.nm_dokter, " +
            "spdj.nip, " +
            "ptgs.nama AS nama_petugas " +
            "FROM reg_periksa rp " +
            "INNER JOIN pasien p ON rp.no_rkm_medis = p.no_rkm_medis " +
            "INNER JOIN kelurahan kel ON p.kd_kel = kel.kd_kel " +
            "INNER JOIN kecamatan kec ON p.kd_kec = kec.kd_kec " +
            "INNER JOIN kabupaten kab ON p.kd_kab = kab.kd_kab " +
            "INNER JOIN surat_pemilihan_dpjp spdj ON rp.no_rawat = spdj.no_rawat " +
            "INNER JOIN dokter d ON spdj.kd_dokter = d.kd_dokter " +
            "INNER JOIN petugas ptgs ON spdj.nip = ptgs.nip " +
            "WHERE spdj.no_surat='"+noSurat+"'",param);
    }
}else{
    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
      }
     }//GEN-LAST:event_BtnPrint1ActionPerformed
     //</editor-fold>
 
     /**
     * @param args the command line arguments
     */
     public static void main(String args[]) {
         java.awt.EventQueue.invokeLater(() -> {
             SuratPemilihanDPJP dialog = new SuratPemilihanDPJP(new javax.swing.JFrame(), true); // Instantiate new class
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
     private widget.TextBox AlamatPJ;
     private widget.ComboBox BertindakAtas;
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
     private widget.TextBox JK;
     private widget.TextBox KdDokter;
     private widget.Label LCount;
     private widget.TextBox LahirPasien;
     private widget.editorpane LoadHTML2;
     private widget.TextBox NIP;
     private widget.TextBox NamaPJ;
     private widget.TextBox NamaPetugas;
     private widget.TextBox NmDokter;
     private widget.TextBox NoSurat;
     private widget.PanelBiasa PanelAccor;
     private javax.swing.JPanel PanelInput;
     private widget.ScrollPane Scroll;
     private widget.ScrollPane Scroll5;
     private widget.TextBox TCari;
     private widget.TextBox TNoRM;
     private widget.TextBox TNoRw;
     private widget.TextBox TPasien;
     private widget.Tanggal Tanggal;
     private widget.Button btnAmbil;
     private widget.Button btnDokter;
     private widget.Button btnPetugas;
     private widget.InternalFrame internalFrame1;
     private widget.Label jLabel10;
     private widget.Label jLabel16;
     private widget.Label jLabel17;
     private widget.Label jLabel18;
     private widget.Label jLabel19;
     private widget.Label jLabel21;
     private widget.Label jLabel22;
     private widget.Label jLabel23;
     private widget.Label jLabel3;
     private widget.Label jLabel4;
     private widget.Label jLabel6;
     private widget.Label jLabel7;
     private widget.Label jLabel8;
     private javax.swing.JPanel jPanel3;
     private widget.panelisi panelGlass8;
     private widget.panelisi panelGlass9;
     private widget.Table tbSurat;
     // End of variables declaration//GEN-END:variables
 
     public void tampil() {
         Valid.tabelKosong(tabMode);
         try{
              // Updated SELECT query for surat_pemilihan_dpjp and joins
             String sql = "SELECT s.no_surat, s.no_rawat, s.tanggal AS tgl_surat, s.no_rkm_medis, p.nm_pasien, " +
                          "DATE_FORMAT(p.tgl_lahir,'%d-%m-%Y') as tgl_lahir, " + // Format tanggal lahir
                          "s.nama_pj, s.alamat_pj, s.bertindak_atas, s.kd_dokter, d.nm_dokter, " +
                          "s.nip, pt.nama AS nama_petugas " +
                          "FROM surat_pemilihan_dpjp s " +
                          "INNER JOIN reg_periksa rp ON s.no_rawat = rp.no_rawat " +
                          "INNER JOIN pasien p ON s.no_rkm_medis = p.no_rkm_medis " +
                          "INNER JOIN dokter d ON s.kd_dokter = d.kd_dokter " + // Join with dokter
                          "INNER JOIN petugas pt ON s.nip = pt.nip " +
                          "WHERE s.tanggal BETWEEN ? AND ? ";
 
             if(!TCari.getText().trim().equals("")){
                 sql += "AND (s.no_surat LIKE ? OR s.no_rawat LIKE ? OR s.no_rkm_medis LIKE ? OR p.nm_pasien LIKE ? " +
                        "OR s.nama_pj LIKE ? OR d.nm_dokter LIKE ? OR pt.nama LIKE ?) "; // Updated search fields
             }
             sql += "ORDER BY s.tanggal DESC, s.no_surat DESC"; // Use s.tanggal, order descending for recent first
 
             ps=koneksi.prepareStatement(sql);
             try {
                  ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                  ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                 if(!TCari.getText().trim().equals("")){
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
                     // Updated tabMode.addRow to match new columns
                     tabMode.addRow(new String[]{
                         rs.getString("no_surat"), rs.getString("no_rawat"), rs.getString("no_rkm_medis"),
                         rs.getString("nm_pasien"), rs.getString("tgl_lahir"), Valid.SetTgl(rs.getString("tgl_surat")), // Format tgl_surat
                         rs.getString("nama_pj"), rs.getString("alamat_pj"), rs.getString("bertindak_atas"),
                         rs.getString("kd_dokter"), rs.getString("nm_dokter"), rs.getString("nip"),
                         rs.getString("nama_petugas")
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
         }catch(Exception e){
             System.out.println("Notifikasi : "+e);
         }
         LCount.setText(""+tabMode.getRowCount());
     }
 
     public void emptTeks() {
         TNoRw.setText("");
         TNoRM.setText("");
         TPasien.setText("");
         LahirPasien.setText("");
         JK.setText("");
         NamaPJ.setText("");
         AlamatPJ.setText("");
         BertindakAtas.setSelectedIndex(0); // Default to "Diri Sendiri"
         KdDokter.setText("");
         NmDokter.setText("");
         Tanggal.setDate(new Date());
         Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,4),signed)),0) from surat_pemilihan_dpjp where tanggal='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
                 "SDPJP"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),4,NoSurat);
         LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>"); // Clear photo preview
         lokasifile = ""; // Reset location file
 
         // Panggil isPasien setelah mengosongkan field dan set default combobox
         isPasien(); // This will handle the initial state (NamaPJ & AlamatPJ should be editable as TNoRw is empty)
 
         TNoRw.requestFocus();
     }
 
 
     private void getData() {
          if(tbSurat.getSelectedRow()!= -1){
             NoSurat.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),0).toString());
             TNoRw.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),1).toString());
             TNoRM.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),2).toString());
             TPasien.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),3).toString());
             LahirPasien.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),4).toString());
             Valid.SetTgl(Tanggal,tbSurat.getValueAt(tbSurat.getSelectedRow(),5).toString());
             NamaPJ.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),6).toString());
             AlamatPJ.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),7).toString());
             BertindakAtas.setSelectedItem(tbSurat.getValueAt(tbSurat.getSelectedRow(),8).toString()); // Load first
             KdDokter.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),9).toString());
             NmDokter.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),10).toString());
             NIP.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),11).toString());
             NamaPetugas.setText(tbSurat.getValueAt(tbSurat.getSelectedRow(),12).toString());
             // Load patient JK separately if needed for display
             JK.setText(Sequel.cariIsi("select jk from pasien where no_rkm_medis=?", TNoRM.getText()));
 
             isPasien(); // ****** Panggil isPasien SETELAH BertindakAtas di-set ******
         }
     }
 
     private void isRawat() {
         try {
             ps=koneksi.prepareStatement(
                     "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk, " +
                     "DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as tgl_lahir, reg_periksa.tgl_registrasi " + // Format tgl lahir
                     "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "where reg_periksa.no_rawat=?");
             try {
                 ps.setString(1,TNoRw.getText());
                 rs=ps.executeQuery();
                 if(rs.next()){
                     TNoRM.setText(rs.getString("no_rkm_medis"));
                     TPasien.setText(rs.getString("nm_pasien"));
                     JK.setText(rs.getString("jk"));
                     LahirPasien.setText(rs.getString("tgl_lahir"));
                 } else {
                     TNoRM.setText("");
                     TPasien.setText("");
                     JK.setText("");
                     LahirPasien.setText("");
                     JOptionPane.showMessageDialog(null, "Data Pasien dengan No. Rawat tersebut tidak ditemukan.");
                 }
             } catch (Exception e) {
                 System.out.println("Notif (isRawat): "+e);
             } finally{
                 if(rs!=null){ rs.close(); }
                 if(ps!=null){ ps.close(); }
             }
         } catch (Exception e) {
             System.out.println("Notif (isRawat): "+e);
         }
     }
 
     // ****** START: Modified isPasien Method ******
     private void isPasien() {
         if (BertindakAtas.getSelectedItem() == null) return; // Hindari NullPointerException saat inisialisasi awal
 
         if (BertindakAtas.getSelectedItem().toString().equals("Diri Sendiri")) {
             // Hanya isi jika No Rawat & Nama Pasien sudah terisi
             if (!TNoRw.getText().trim().isEmpty() && !TPasien.getText().trim().isEmpty()) {
                 NamaPJ.setText(TPasien.getText());
                 AlamatPJ.setText(Sequel.cariIsi("select alamat from pasien where no_rkm_medis=?", TNoRM.getText()));
                 NamaPJ.setEditable(false); // Non-aktifkan field
                 AlamatPJ.setEditable(false); // Non-aktifkan field
             } else {
                 // Jika data pasien belum ada tapi dipilih "Diri Sendiri", kosongkan dan enable
                 NamaPJ.setText("");
                 AlamatPJ.setText("");
                 NamaPJ.setEditable(true);
                 AlamatPJ.setEditable(true);
             }
         } else {
             // Jika bukan "Diri Sendiri", kosongkan dan enable field
             // Jangan kosongkan jika NamaPJ sudah diisi manual sebelumnya dan hanya ganti pilihan lain
             // if (NamaPJ.getText().isEmpty() || BertindakAtas.getSelectedIndex() != 0) { // Kosongkan jika memang kosong atau baru ganti dari diri sendiri
                 NamaPJ.setText("");
                 AlamatPJ.setText("");
             //}
             NamaPJ.setEditable(true);
             AlamatPJ.setEditable(true);
             // Jika diperlukan, set fokus ke NamaPJ saat pilihan selain "Diri Sendiri"
             // if (!NamaPJ.isFocusOwner() && ChkInput.isSelected()) { // Cek jika form input aktif
             //     NamaPJ.requestFocusInWindow();
             // }
         }
     }
     // ****** END: Modified isPasien Method ******
 
 
     public void setNoRm(String norwt,Date tgl2) {
         TNoRw.setText(norwt);
         TCari.setText(norwt);
         DTPCari2.setDate(tgl2);
         isRawat();
         // isPasien() dipanggil setelah isRawat() dan setelah BertindakAtas di-set jika getData() dipanggil
         // Tidak perlu dipanggil di sini secara eksplisit sebelum tampil()
 
         ChkInput.setSelected(true); // Mungkin ingin buka form input secara default
         isForm();
         tampil(); // Load data for this norwt
 
         if (tabMode.getRowCount() == 0) { // <--- PERBAIKAN DI SINI (Menggunakan == 0)
             // Jika TIDAK ADA data surat ditemukan untuk No Rawat tsb
             emptTeks(); // Kosongkan form input
             TNoRw.setText(norwt); // Tetapkan No Rawat lagi (karena emptTeks() mengosongkannya)
             isRawat(); // Ambil data pasien lagi (karena emptTeks() mengosongkannya)
             isPasien(); // Atur NamaPJ/AlamatPJ lagi sesuai kondisi (mungkin "Diri Sendiri")
             setDefaultDPJP(norwt); // Set default DPJP bahkan jika surat belum ada
             // Mungkin beri pesan bahwa surat belum dibuat?
             // JOptionPane.showMessageDialog(rootPane, "Belum ada Surat Pemilihan DPJP untuk pasien ini.");
             Tanggal.requestFocus(); // Fokus ke tanggal untuk input baru
         } else {
             // Jika ADA data surat ditemukan
             tbSurat.setRowSelectionInterval(0, 0); // Pilih baris pertama
             getData(); // Tampilkan data dari baris yang dipilih
             try {
                 isPhoto(); // Siapkan panel foto
                 if (ChkAccor.isSelected()) { // Hanya panggil jika panel terbuka
                     panggilPhoto(); // Coba tampilkan foto jika ada
                 }
             } catch (Exception e) {
                 System.out.println("Error saat menampilkan foto di setNoRm: "+e);
             }
         }
     }
 
     private void setDefaultDPJP(String norwt) {
        String kd_dokter_ranap = Sequel.cariIsi("select kd_dokter from kamar_inap where no_rawat=? order by tgl_masuk desc, jam_masuk desc limit 1", norwt);
        if (!kd_dokter_ranap.isEmpty()) {
            KdDokter.setText(kd_dokter_ranap);
            NmDokter.setText(dokter.tampil3(kd_dokter_ranap));
        } else {
             // Jika tidak ada di ranap, coba cari di reg_periksa
             String kd_dokter_reg = Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?", norwt);
             if (!kd_dokter_reg.isEmpty()) {
                 KdDokter.setText(kd_dokter_reg);
                 NmDokter.setText(dokter.tampil3(kd_dokter_reg));
             } else {
                 KdDokter.setText(""); // Kosongkan jika tidak ketemu
                 NmDokter.setText("");
             }
        }
     }
 
     private void isForm(){
         if(ChkInput.isSelected()==true){
             ChkInput.setVisible(false);
             PanelInput.setPreferredSize(new Dimension(WIDTH,185)); // Sesuaikan tinggi panel input
             FormInput.setVisible(true);
             ChkInput.setVisible(true);
             BtnBatal.requestFocus(); // Fokus ke tombol batal saat form input dibuka
         }else if(ChkInput.isSelected()==false){
             ChkInput.setVisible(false);
             PanelInput.setPreferredSize(new Dimension(WIDTH,20));
             FormInput.setVisible(false);
             ChkInput.setVisible(true);
         }
     }
 
 
     public void isCek(){
          // ****** Ganti Hak Akses Jika Diperlukan ******
         // Sesuaikan dengan nama hak akses yang benar di database Anda untuk modul ini
         // Misalnya, jika hak aksesnya adalah 'surat_pemilihan_dpjp'
         boolean aksesSuratDPJP = akses.getadmin() || akses.getsurat_persetujuan_umum(); // Contoh penggunaan hak akses 'surat_pemilihan_dpjp'
 
         BtnSimpan.setEnabled(aksesSuratDPJP);
         BtnHapus.setEnabled(aksesSuratDPJP);
         BtnEdit.setEnabled(aksesSuratDPJP);
         BtnPrint.setEnabled(aksesSuratDPJP);
         btnAmbil.setEnabled(aksesSuratDPJP); // Sesuaikan jika perlu hak akses berbeda
         BtnPrint1.setEnabled(aksesSuratDPJP); // Sesuaikan jika perlu hak akses berbeda
         // ****** Ganti Hak Akses Jika Diperlukan ******
 
         if(akses.getjml2()>=1){
             NIP.setEditable(false);
             btnPetugas.setEnabled(false);
             NIP.setText(akses.getkode());
             NamaPetugas.setText(petugas.tampil3(NIP.getText()));
             if(NamaPetugas.getText().equals("")){
                 NIP.setText("");
                 JOptionPane.showMessageDialog(null,"User login tidak terdaftar sebagai petugas!");
             }
         }
     }
 
     private void ganti() {
         if(Sequel.mengedittf("surat_pemilihan_dpjp","no_surat=?","no_rawat=?, tanggal=?, no_rkm_medis=?, nama_pj=?, alamat_pj=?, bertindak_atas=?, kd_dokter=?, nip=?",9,new String[]{
             TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem()+""), TNoRM.getText(), NamaPJ.getText(),
             AlamatPJ.getText(), BertindakAtas.getSelectedItem().toString(), KdDokter.getText(), NIP.getText(),
             tbSurat.getValueAt(tbSurat.getSelectedRow(),0).toString() // WHERE clause uses original NoSurat
         })==true){
             // Update table model remains same as previous DPJP version
             int row = tbSurat.getSelectedRow();
             tbSurat.setValueAt(NoSurat.getText(),row,0);
             tbSurat.setValueAt(TNoRw.getText(),row,1);
             tbSurat.setValueAt(TNoRM.getText(),row,2);
             tbSurat.setValueAt(TPasien.getText(),row,3);
             tbSurat.setValueAt(LahirPasien.getText(),row,4);
             tbSurat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+""),row,5);
             tbSurat.setValueAt(NamaPJ.getText(),row,6);
             tbSurat.setValueAt(AlamatPJ.getText(),row,7);
             tbSurat.setValueAt(BertindakAtas.getSelectedItem().toString(),row,8);
             tbSurat.setValueAt(KdDokter.getText(),row,9);
             tbSurat.setValueAt(NmDokter.getText(),row,10);
             tbSurat.setValueAt(NIP.getText(),row,11);
             tbSurat.setValueAt(NamaPetugas.getText(),row,12);
             emptTeks();
         }
     }
 
     private void hapus() {
         // Fetch the no_surat before potentially deleting the photo link
         String noSuratToDelete = tbSurat.getValueAt(tbSurat.getSelectedRow(), 0).toString();
 
         // Konfirmasi sebelum hapus
         int reply = JOptionPane.showConfirmDialog(rootPane,"Yakin ingin menghapus data surat "+noSuratToDelete+"?","Konfirmasi Hapus",JOptionPane.YES_NO_OPTION);
         if (reply == JOptionPane.YES_OPTION) {
             // Delete from the main table first
             if(Sequel.queryu2tf("delete from surat_pemilihan_dpjp where no_surat=?",1,new String[]{
                 noSuratToDelete
             })==true){
                  // Hapus juga bukti jika ada (gunakan tabel yang benar)
                 Sequel.queryu2("delete from surat_pemilihan_dpjp_bukti where no_surat=?", 1, new String[]{noSuratToDelete});
 
                 tabMode.removeRow(tbSurat.getSelectedRow());
                 LCount.setText(""+tabMode.getRowCount());
                 emptTeks();
             }else{
                 JOptionPane.showMessageDialog(null,"Gagal menghapus data surat pemilihan DPJP..!!");
             }
         }
     }
 
     //<editor-fold defaultstate="collapsed" desc="Photo Panel Methods (Restored)">
     private void isPhoto(){
         if(ChkAccor.isSelected()==true){
             ChkAccor.setVisible(false);
             PanelAccor.setPreferredSize(new Dimension(480,HEIGHT)); // Adjust width as needed
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
    
    if(FormPhoto.isVisible()==true && tbSurat.getSelectedRow() > -1){
        lokasifile="";
        try {
            ps=koneksi.prepareStatement("select photo from surat_pemilihan_dpjp_bukti where no_surat=?");
            try {
                ps.setString(1,tbSurat.getValueAt(tbSurat.getSelectedRow(),0).toString());
                rs=ps.executeQuery();
                if(rs.next()){
                    if(rs.getString("photo")==null || rs.getString("photo").equals("") || rs.getString("photo").equals("-")){
                        lokasifile="";
                        LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Bukti/Scan Belum Tersedia</font></center></body></html>");
                    }else{
                        lokasifile=rs.getString("photo");
                        LoadHTML2.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/pemilihanDPJP/"+lokasifile+"' alt='photo' width='450' height='450'/></center></body></html>");
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
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
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
 
 }