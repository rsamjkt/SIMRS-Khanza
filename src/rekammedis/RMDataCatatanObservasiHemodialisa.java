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
import javax.swing.JPanel;
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
 public final class RMDataCatatanObservasiHemodialisa extends javax.swing.JDialog {
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
     public RMDataCatatanObservasiHemodialisa(java.awt.Frame parent, boolean modal) {
         super(parent, modal);
         initComponents();
         this.setLocation(8, 1);
         setSize(628, 674);
 
         tabMode = new DefaultTableModel(null, new Object[]{
             "No.Rawat", "No.R.M.", "Nama Pasien", "Umur", "JK", "Tgl.Lahir", "Tgl.Obser", "Jam Obser", "QB", "QD",
             "Tekanan Arteri", "Tekanan Vena", "TMP", "UFR", "Tensi", "Nadi", "Suhu", "SpO2", "Tindakan", "UFG", "NIP", "Nama Petugas"
         }) {
             @Override 
             public boolean isCellEditable(int rowIndex, int colIndex) {
                 return false;
             }
         };
         tbObat.setModel(tabMode);
 
         tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
         tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
 
         for (i = 0; i < 22; i++) {
             TableColumn column = tbObat.getColumnModel().getColumn(i);
             if (i == 0) {
                 column.setPreferredWidth(105);
             } else if (i == 1) {
                 column.setPreferredWidth(65);
             } else if (i == 2) {
                 column.setPreferredWidth(160);
             } else if (i == 3) {
                 column.setPreferredWidth(35);
             } else if (i == 4) {
                 column.setPreferredWidth(20);
             } else if (i == 5) {
                 column.setPreferredWidth(65);
             } else if (i == 6) {
                 column.setPreferredWidth(65);
             } else if (i == 7) {
                 column.setPreferredWidth(60);
             } else if (i == 8) {
                 column.setPreferredWidth(50);
             } else if (i == 9) {
                 column.setPreferredWidth(50);
             } else if (i == 10) {
                 column.setPreferredWidth(90);
             } else if (i == 11) {
                 column.setPreferredWidth(90);
             } else if (i == 12) {
                 column.setPreferredWidth(50);
             } else if (i == 13) {
                 column.setPreferredWidth(50);
             } else if (i == 14) {
                 column.setPreferredWidth(50);
             } else if (i == 15) {
                 column.setPreferredWidth(50);
             } else if (i == 16) {
                 column.setPreferredWidth(50);
             } else if (i == 17) {
                 column.setPreferredWidth(50);
             } else if (i == 18) {
                 column.setPreferredWidth(150);
             } else if (i == 19) {
                 column.setPreferredWidth(50);
             } else if (i == 20) {
                 column.setPreferredWidth(90);
             } else if (i == 21) {
                 column.setPreferredWidth(160);
             }
         }
         tbObat.setDefaultRenderer(Object.class, new WarnaTable());
 
         TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
         NIP.setDocument(new batasInput((byte)20).getKata(NIP));
         QB.setDocument(new batasInput((byte)10).getKata(QB));
         QD.setDocument(new batasInput((byte)8).getKata(QD));
         TekananArteri.setDocument(new batasInput((byte)5).getKata(TekananArteri));
         TekananVena.setDocument(new batasInput((byte)5).getKata(TekananVena));
         TMP.setDocument(new batasInput((byte)5).getKata(TMP));
         UFR.setDocument(new batasInput((byte)5).getKata(UFR));
         Tensi.setDocument(new batasInput((byte)5).getKata(Tensi));
         Nadi.setDocument(new batasInput((byte)6).getKata(Nadi));
         Suhu.setDocument(new batasInput((byte)20).getKata(Suhu));
         SPO.setDocument(new batasInput((byte)5).getKata(SPO));
         Tindakan.setDocument(new batasInput((int)1000).getKata(Tindakan));
         UFG.setDocument(new batasInput((byte)10).getKata(UFG));
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
 
     /** This method is called from within the constructor to
      * initialize the form.
      * WARNING: Do NOT modify this code. The content of this method is
      * always regenerated by the Form Editor.
      */
     @SuppressWarnings("unchecked")
     // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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
         TekananArteri = new widget.TextBox();
         jLabel15 = new widget.Label();
         TekananVena = new widget.TextBox();
         jLabel17 = new widget.Label();
         TMP = new widget.TextBox();
         jLabel20 = new widget.Label();
         UFR = new widget.TextBox();
         jLabel23 = new widget.Label();
         Tensi = new widget.TextBox();
         jLabel24 = new widget.Label();
         Nadi = new widget.TextBox();
         jLabel26 = new widget.Label();
         Suhu = new widget.TextBox();
         jLabel29 = new widget.Label();
         SPO = new widget.TextBox();
         jLabel30 = new widget.Label();
         Tindakan = new widget.TextBox();
         jLabel31 = new widget.Label();
         UFG = new widget.TextBox();
         ChkInput = new widget.CekBox();
 
         jPopupMenu1.setName("jPopupMenu1"); // NOI18N
 
         MnCatatanObservasiHemodialisa.setBackground(new java.awt.Color(255, 255, 254));
         MnCatatanObservasiHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
         MnCatatanObservasiHemodialisa.setForeground(new java.awt.Color(50, 50, 50));
         MnCatatanObservasiHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
         MnCatatanObservasiHemodialisa.setText("Formulir Catatan Observasi Hemodialisa");
         MnCatatanObservasiHemodialisa.setName("MnCatatanObservasiHemodialisa"); // NOI18N
         MnCatatanObservasiHemodialisa.setPreferredSize(new java.awt.Dimension(260, 26));
         MnCatatanObservasiHemodialisa.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 MnCatatanObservasiHemodialisaActionPerformed(evt);
             }
         });
         jPopupMenu1.add(MnCatatanObservasiHemodialisa);
 
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
         DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21-03-2025" }));
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
         DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21-03-2025" }));
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
         PanelInput.setPreferredSize(new java.awt.Dimension(192, 184));
         PanelInput.setLayout(new java.awt.BorderLayout(1, 1));
 
         FormInput.setBackground(new java.awt.Color(250, 255, 245));
         FormInput.setName("FormInput"); // NOI18N
         FormInput.setPreferredSize(new java.awt.Dimension(100, 225));
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
         Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21-03-2025" }));
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
 
         Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
         Jam.setName("Jam"); // NOI18N
         Jam.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 JamKeyPressed(evt);
             }
         });
         FormInput.add(Jam);
         Jam.setBounds(178, 40, 62, 23);
 
         Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
         Menit.setName("Menit"); // NOI18N
         Menit.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 MenitKeyPressed(evt);
             }
         });
         FormInput.add(Menit);
         Menit.setBounds(243, 40, 62, 23);
 
         Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
         Detik.setName("Detik"); // NOI18N
         Detik.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 DetikKeyPressed(evt);
             }
         });
         FormInput.add(Detik);
         Detik.setBounds(308, 40, 62, 23);
 
         ChkKejadian.setBorder(null);
         ChkKejadian.setSelected(true);
         ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
         ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
         ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
         ChkKejadian.setName("ChkKejadian"); // NOI18N
         FormInput.add(ChkKejadian);
         ChkKejadian.setBounds(373, 40, 23, 23);
 
         jLabel18.setText("Petugas :");
         jLabel18.setName("jLabel18"); // NOI18N
         FormInput.add(jLabel18);
         jLabel18.setBounds(400, 40, 70, 23);
 
         NIP.setEditable(false);
         NIP.setHighlighter(null);
         NIP.setName("NIP"); // NOI18N
         NIP.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 NIPKeyPressed(evt);
             }
         });
         FormInput.add(NIP);
         NIP.setBounds(474, 40, 94, 23);
 
         NamaPetugas.setEditable(false);
         NamaPetugas.setName("NamaPetugas"); // NOI18N
         FormInput.add(NamaPetugas);
         NamaPetugas.setBounds(570, 40, 187, 23);
 
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
         btnPetugas.setBounds(761, 40, 28, 23);
 
         jLabel8.setText("Tgl.Lahir :");
         jLabel8.setName("jLabel8"); // NOI18N
         FormInput.add(jLabel8);
         jLabel8.setBounds(625, 10, 60, 23);
 
         TglLahir.setHighlighter(null);
         TglLahir.setName("TglLahir"); // NOI18N
         FormInput.add(TglLahir);
         TglLahir.setBounds(689, 10, 100, 23);
 
         jLabel12.setText("QB :");
         jLabel12.setName("jLabel12"); // NOI18N
         FormInput.add(jLabel12);
         jLabel12.setBounds(0, 70, 80, 23);
 
         QB.setFocusTraversalPolicyProvider(true);
         QB.setName("QB"); // NOI18N
         QB.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 QBKeyPressed(evt);
             }
         });
         FormInput.add(QB);
         QB.setBounds(84, 70, 50, 23);
 
         jLabel13.setText("QD :");
         jLabel13.setName("jLabel13"); // NOI18N
         FormInput.add(jLabel13);
         jLabel13.setBounds(138, 70, 40, 23);
 
         QD.setFocusTraversalPolicyProvider(true);
         QD.setName("QD"); // NOI18N
         QD.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 QDKeyPressed(evt);
             }
         });
         FormInput.add(QD);
         QD.setBounds(182, 70, 50, 23);
 
         jLabel14.setText("T. Arteri :");
         jLabel14.setName("jLabel14"); // NOI18N
         FormInput.add(jLabel14);
         jLabel14.setBounds(236, 70, 60, 23);
 
         TekananArteri.setFocusTraversalPolicyProvider(true);
         TekananArteri.setName("TekananArteri"); // NOI18N
         TekananArteri.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 TekananArteriKeyPressed(evt);
             }
         });
         FormInput.add(TekananArteri);
         TekananArteri.setBounds(300, 70, 50, 23);
 
         jLabel15.setText("T. Vena :");
         jLabel15.setName("jLabel15"); // NOI18N
         FormInput.add(jLabel15);
         jLabel15.setBounds(354, 70, 60, 23);
 
         TekananVena.setFocusTraversalPolicyProvider(true);
         TekananVena.setName("TekananVena"); // NOI18N
         TekananVena.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 TekananVenaKeyPressed(evt);
             }
         });
         FormInput.add(TekananVena);
         TekananVena.setBounds(418, 70, 50, 23);
 
         jLabel17.setText("TMP :");
         jLabel17.setName("jLabel17"); // NOI18N
         FormInput.add(jLabel17);
         jLabel17.setBounds(472, 70, 40, 23);
 
         TMP.setFocusTraversalPolicyProvider(true);
         TMP.setName("TMP"); // NOI18N
         TMP.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 TMPKeyPressed(evt);
             }
         });
         FormInput.add(TMP);
         TMP.setBounds(516, 70, 50, 23);
 
         jLabel20.setText("UFR :");
         jLabel20.setName("jLabel20"); // NOI18N
         FormInput.add(jLabel20);
         jLabel20.setBounds(570, 70, 40, 23);
 
         UFR.setFocusTraversalPolicyProvider(true);
         UFR.setName("UFR"); // NOI18N
         UFR.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 UFRKeyPressed(evt);
             }
         });
         FormInput.add(UFR);
         UFR.setBounds(614, 70, 50, 23);
 
         jLabel23.setText("Tensi :");
         jLabel23.setName("jLabel23"); // NOI18N
         FormInput.add(jLabel23);
         jLabel23.setBounds(0, 100, 80, 23);
 
         Tensi.setFocusTraversalPolicyProvider(true);
         Tensi.setName("Tensi"); // NOI18N
         Tensi.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 TensiKeyPressed(evt);
             }
         });
         FormInput.add(Tensi);
         Tensi.setBounds(84, 100, 50, 23);
 
         jLabel24.setText("Nadi :");
         jLabel24.setName("jLabel24"); // NOI18N
         FormInput.add(jLabel24);
         jLabel24.setBounds(138, 100, 40, 23);
 
         Nadi.setFocusTraversalPolicyProvider(true);
         Nadi.setName("Nadi"); // NOI18N
         Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 NadiKeyPressed(evt);
             }
         });
         FormInput.add(Nadi);
         Nadi.setBounds(182, 100, 50, 23);
 
         jLabel26.setText("Suhu :");
         jLabel26.setName("jLabel26"); // NOI18N
         FormInput.add(jLabel26);
         jLabel26.setBounds(236, 100, 60, 23);
 
         Suhu.setFocusTraversalPolicyProvider(true);
         Suhu.setName("Suhu"); // NOI18N
         Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 SuhuKeyPressed(evt);
             }
         });
         FormInput.add(Suhu);
         Suhu.setBounds(300, 100, 50, 23);
 
         jLabel29.setText("SpO2 :");
         jLabel29.setName("jLabel29"); // NOI18N
         FormInput.add(jLabel29);
         jLabel29.setBounds(354, 100, 60, 23);
 
         SPO.setFocusTraversalPolicyProvider(true);
         SPO.setName("SPO"); // NOI18N
         SPO.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 SPOKeyPressed(evt);
             }
         });
         FormInput.add(SPO);
         SPO.setBounds(418, 100, 50, 23);
 
         jLabel30.setText("Tindakan :");
         jLabel30.setName("jLabel30"); // NOI18N
         FormInput.add(jLabel30);
         jLabel30.setBounds(0, 130, 80, 23);
 
         Tindakan.setFocusTraversalPolicyProvider(true);
         Tindakan.setName("Tindakan"); // NOI18N
         Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 TindakanKeyPressed(evt);
             }
         });
         FormInput.add(Tindakan);
         Tindakan.setBounds(84, 130, 400, 23);
 
         jLabel31.setText("UFG :");
         jLabel31.setName("jLabel31"); // NOI18N
         FormInput.add(jLabel31);
         jLabel31.setBounds(488, 130, 40, 23);
 
         UFG.setFocusTraversalPolicyProvider(true);
         UFG.setName("UFG"); // NOI18N
         UFG.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyPressed(java.awt.event.KeyEvent evt) {
                 UFGKeyPressed(evt);
             }
         });
         FormInput.add(UFG);
         UFG.setBounds(532, 130, 50, 23);
 
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
             Valid.pindah(evt, UFG, BtnBatal);
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
                 if (NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString())) {
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
                     if (NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString())) {
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
                 Valid.MyReportqry("rptDataCatatanObservasiHemodialisa.jasper", "report", "::~[ Data Catatan Observasi Hemodialisa ]::~",
                     "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur," +
                     "pasien.jk,pasien.tgl_lahir,catatan_observasi_hemodialisa.tgl_perawatan,catatan_observasi_hemodialisa.jam_rawat,catatan_observasi_hemodialisa.qb," +
                     "catatan_observasi_hemodialisa.qd,catatan_observasi_hemodialisa.tekanan_arteri,catatan_observasi_hemodialisa.tekanan_vena,catatan_observasi_hemodialisa.tmp," +
                     "catatan_observasi_hemodialisa.ufr,catatan_observasi_hemodialisa.tensi,catatan_observasi_hemodialisa.nadi,catatan_observasi_hemodialisa.suhu," +
                     "catatan_observasi_hemodialisa.spo2,catatan_observasi_hemodialisa.tindakan,catatan_observasi_hemodialisa.ufg,catatan_observasi_hemodialisa.nip,petugas.nama " +
                     "from catatan_observasi_hemodialisa inner join reg_periksa on catatan_observasi_hemodialisa.no_rawat=reg_periksa.no_rawat " +
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                     "inner join petugas on catatan_observasi_hemodialisa.nip=petugas.nip where " +
                     "catatan_observasi_hemodialisa.tgl_perawatan between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " +
                     "order by catatan_observasi_hemodialisa.tgl_perawatan,catatan_observasi_hemodialisa.jam_rawat", param);
             } else {
                 Valid.MyReportqry("rptDataCatatanObservasiHemodialisa.jasper", "report", "::~[ Data Catatan Observasi Hemodialisa ]::~",
                     "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur," +
                     "pasien.jk,pasien.tgl_lahir,catatan_observasi_hemodialisa.tgl_perawatan,catatan_observasi_hemodialisa.jam_rawat,catatan_observasi_hemodialisa.qb," +
                     "catatan_observasi_hemodialisa.qd,catatan_observasi_hemodialisa.tekanan_arteri,catatan_observasi_hemodialisa.tekanan_vena,catatan_observasi_hemodialisa.tmp," +
                     "catatan_observasi_hemodialisa.ufr,catatan_observasi_hemodialisa.tensi,catatan_observasi_hemodialisa.nadi,catatan_observasi_hemodialisa.suhu," +
                     "catatan_observasi_hemodialisa.spo2,catatan_observasi_hemodialisa.tindakan,catatan_observasi_hemodialisa.ufg,catatan_observasi_hemodialisa.nip,petugas.nama " +
                     "from catatan_observasi_hemodialisa inner join reg_periksa on catatan_observasi_hemodialisa.no_rawat=reg_periksa.no_rawat " +
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                     "inner join petugas on catatan_observasi_hemodialisa.nip=petugas.nip where " +
                     "catatan_observasi_hemodialisa.tgl_perawatan between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and " +
                     "(reg_periksa.no_rawat like '%" + TCari.getText().trim() + "%' or pasien.no_rkm_medis like '%" + TCari.getText().trim() + "%' or " +
                     "pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or catatan_observasi_hemodialisa.nip like '%" + TCari.getText().trim() + "%' or petugas.nama like '%" + TCari.getText().trim() + "%') " +
                     "order by catatan_observasi_hemodialisa.tgl_perawatan,catatan_observasi_hemodialisa.jam_rawat ", param);
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
             Valid.MyReportqry("rptFormulirCatatanObservasiHemodialisa.jasper", "report", "::~[ Formulir Catatan Observasi Hemodialisa ]::~",
                     "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,reg_periksa.tgl_registrasi,reg_periksa.jam_reg," +
                     "pasien.jk,pasien.tgl_lahir,catatan_observasi_hemodialisa.tgl_perawatan,catatan_observasi_hemodialisa.jam_rawat,catatan_observasi_hemodialisa.qb," +
                     "catatan_observasi_hemodialisa.qd,catatan_observasi_hemodialisa.tekanan_arteri,catatan_observasi_hemodialisa.tekanan_vena,catatan_observasi_hemodialisa.tmp," +
                     "catatan_observasi_hemodialisa.ufr,catatan_observasi_hemodialisa.tensi,catatan_observasi_hemodialisa.nadi,catatan_observasi_hemodialisa.suhu," +
                     "catatan_observasi_hemodialisa.spo2,catatan_observasi_hemodialisa.tindakan,catatan_observasi_hemodialisa.ufg,petugas.nama " +
                     "from catatan_observasi_hemodialisa inner join reg_periksa on catatan_observasi_hemodialisa.no_rawat=reg_periksa.no_rawat " +
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                     "inner join petugas on catatan_observasi_hemodialisa.nip=petugas.nip where reg_periksa.no_rawat='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "' " +
                     "order by catatan_observasi_hemodialisa.tgl_perawatan,catatan_observasi_hemodialisa.jam_rawat", param);
         }
     }                                                     
 
     private void QBKeyPressed(java.awt.event.KeyEvent evt) {                              
         Valid.pindah(evt, btnPetugas, QD);
     }                             
 
     private void QDKeyPressed(java.awt.event.KeyEvent evt) {                              
         Valid.pindah(evt, QB, TekananArteri);
     }                             
 
     private void TekananArteriKeyPressed(java.awt.event.KeyEvent evt) {                                         
         Valid.pindah(evt, QD, TekananVena);
     }                                        
 
     private void TekananVenaKeyPressed(java.awt.event.KeyEvent evt) {                                       
         Valid.pindah(evt, TekananArteri, TMP);
     }                                      
 
     private void TMPKeyPressed(java.awt.event.KeyEvent evt) {                               
         Valid.pindah(evt, TekananVena, UFR);
     }                              
 
     private void UFRKeyPressed(java.awt.event.KeyEvent evt) {                               
         Valid.pindah(evt, TMP, Tensi);
     }                              
 
     private void TensiKeyPressed(java.awt.event.KeyEvent evt) {                                 
         Valid.pindah(evt, UFR, Nadi);
     }                                
 
     private void NadiKeyPressed(java.awt.event.KeyEvent evt) {                                
         Valid.pindah(evt, Tensi, Suhu);
     }                               
 
     private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {                                
         Valid.pindah(evt, Nadi, SPO);
     }                               
 
     private void SPOKeyPressed(java.awt.event.KeyEvent evt) {                               
         Valid.pindah(evt, Suhu, Tindakan);
     }                              
 
     private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {                                    
         Valid.pindah(evt, SPO, UFG);
     }                                   
 
     private void UFGKeyPressed(java.awt.event.KeyEvent evt) {                               
         Valid.pindah(evt, Tindakan, BtnSimpan);
     }                              
 
     /**
      * @param args the command line arguments
      */
     public static void main(String args[]) {
         java.awt.EventQueue.invokeLater(() -> {
             RMDataCatatanObservasiHemodialisa dialog = new RMDataCatatanObservasiHemodialisa(new javax.swing.JFrame(), true);
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
     private widget.TextBox Nadi;
     private widget.TextBox NamaPetugas;
     private javax.swing.JPanel PanelInput;
     private widget.TextBox QB;
     private widget.TextBox QD;
     private widget.TextBox SPO;
     private widget.ScrollPane Scroll;
     private widget.TextBox Suhu;
     private widget.TextBox TCari;
     private widget.TextBox TMP;
     private widget.TextBox TNoRM;
     private widget.TextBox TNoRw;
     private widget.TextBox TPasien;
     private widget.Tanggal Tanggal;
     private widget.TextBox TanggalRegistrasi;
     private widget.TextBox TekananArteri;
     private widget.TextBox TekananVena;
     private widget.TextBox Tensi;
     private widget.TextBox TglLahir;
     private widget.TextBox Tindakan;
     private widget.TextBox UFG;
     private widget.TextBox UFR;
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
     private widget.Label jLabel4;
     private widget.Label jLabel6;
     private widget.Label jLabel7;
     private widget.Label jLabel8;
     private javax.swing.JPanel jPanel3;
     private javax.swing.JPopupMenu jPopupMenu1;
     private widget.panelisi panelGlass8;
     private widget.panelisi panelGlass9;
     private widget.Table tbObat;
     // End of variables declaration                   
     
     public void tampil() {
    Valid.tabelKosong(tabMode);
    try {
        if (TCari.getText().trim().equals("")) {
            ps = koneksi.prepareStatement(
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur," +
                "pasien.jk,pasien.tgl_lahir,catatan_observasi_hemodialisa.tgl_perawatan,catatan_observasi_hemodialisa.jam_rawat,catatan_observasi_hemodialisa.qb," +
                "catatan_observasi_hemodialisa.qd,catatan_observasi_hemodialisa.tekanan_arteri,catatan_observasi_hemodialisa.tekanan_vena,catatan_observasi_hemodialisa.tmp," +
                "catatan_observasi_hemodialisa.ufr,catatan_observasi_hemodialisa.tensi,catatan_observasi_hemodialisa.nadi,catatan_observasi_hemodialisa.suhu," +
                "catatan_observasi_hemodialisa.spo2,catatan_observasi_hemodialisa.tindakan,catatan_observasi_hemodialisa.ufg,catatan_observasi_hemodialisa.nip,petugas.nama " +
                "from catatan_observasi_hemodialisa inner join reg_periksa on catatan_observasi_hemodialisa.no_rawat=reg_periksa.no_rawat " +
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "inner join petugas on catatan_observasi_hemodialisa.nip=petugas.nip where " +
                "catatan_observasi_hemodialisa.tgl_perawatan between ? and ? order by catatan_observasi_hemodialisa.tgl_perawatan,catatan_observasi_hemodialisa.jam_rawat");
        } else {
            ps = koneksi.prepareStatement(
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur," +
                "pasien.jk,pasien.tgl_lahir,catatan_observasi_hemodialisa.tgl_perawatan,catatan_observasi_hemodialisa.jam_rawat,catatan_observasi_hemodialisa.qb," +
                "catatan_observasi_hemodialisa.qd,catatan_observasi_hemodialisa.tekanan_arteri,catatan_observasi_hemodialisa.tekanan_vena,catatan_observasi_hemodialisa.tmp," +
                "catatan_observasi_hemodialisa.ufr,catatan_observasi_hemodialisa.tensi,catatan_observasi_hemodialisa.nadi,catatan_observasi_hemodialisa.suhu," +
                "catatan_observasi_hemodialisa.spo2,catatan_observasi_hemodialisa.tindakan,catatan_observasi_hemodialisa.ufg,catatan_observasi_hemodialisa.nip,petugas.nama " +
                "from catatan_observasi_hemodialisa inner join reg_periksa on catatan_observasi_hemodialisa.no_rawat=reg_periksa.no_rawat " +
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "inner join petugas on catatan_observasi_hemodialisa.nip=petugas.nip where " +
                "catatan_observasi_hemodialisa.tgl_perawatan between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or catatan_observasi_hemodialisa.nip like ? or petugas.nama like ?) " +
                "order by catatan_observasi_hemodialisa.tgl_perawatan,catatan_observasi_hemodialisa.jam_rawat");
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
                String[] rowData = new String[22];
                rowData[0] = rs.getString("no_rawat");
                rowData[1] = rs.getString("no_rkm_medis");
                rowData[2] = rs.getString("nm_pasien");
                rowData[3] = rs.getString("umurdaftar") + " " + rs.getString("sttsumur");
                rowData[4] = rs.getString("jk");
                rowData[5] = rs.getString("tgl_lahir");
                rowData[6] = rs.getString("tgl_perawatan");
                rowData[7] = rs.getString("jam_rawat");
                rowData[8] = rs.getString("qb");
                rowData[9] = rs.getString("qd");
                rowData[10] = rs.getString("tekanan_arteri");
                rowData[11] = rs.getString("tekanan_vena");
                rowData[12] = rs.getString("tmp");
                rowData[13] = rs.getString("ufr");
                rowData[14] = rs.getString("tensi");
                rowData[15] = rs.getString("nadi");
                rowData[16] = rs.getString("suhu");
                rowData[17] = rs.getString("spo2");
                rowData[18] = rs.getString("tindakan");
                rowData[19] = rs.getString("ufg");
                rowData[20] = rs.getString("nip");
                rowData[21] = rs.getString("nama");
                tabMode.addRow(rowData);
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
         TekananArteri.setText("");
         TekananVena.setText("");
         TMP.setText("");
         UFR.setText("");
         Tensi.setText("");
         Nadi.setText("");
         Suhu.setText("");
         SPO.setText("");
         Tindakan.setText("");
         UFG.setText("");
         NIP.setText("");
         NamaPetugas.setText("");
         Tanggal.setDate(new Date());
         Tanggal.requestFocus();
     }
 
     private void getData() {
    if (tbObat.getSelectedRow() != -1 && tbObat.getColumnCount() == 22) {
        TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
        TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
        TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
        Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString());
        JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString());
        TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
        Valid.SetTgl(Tanggal, tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
        Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString().substring(0, 2));
        Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString().substring(3, 5));
        Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString().substring(6, 8));
        QB.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
        QD.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());
        TekananArteri.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
        TekananVena.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
        TMP.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
        UFR.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString());
        Tensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString());
        Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());
        Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
        SPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString());
        Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString());
        UFG.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 19).toString());
        NIP.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString());
        NamaPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 21).toString());
    } else {
        System.out.println("Jumlah kolom tidak sesuai: " + tbObat.getColumnCount());
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
                 if (rs != null) {
                     rs.close();
                 }
                 if (ps != null) {
                     ps.close();
                 }
             }
         } catch (Exception e) {
             System.out.println("Notifikasi : " + e);
         }
     }
 
     private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,200));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
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
 
     private void jam() {
         ActionListener taskPerformer = new ActionListener() {
             private int nilai_jam;
             private int nilai_menit;
             private int nilai_detik;
 
             @Override
             public void actionPerformed(ActionEvent e) {
                 String nol_jam = "";
                 String nol_menit = "";
                 String nol_detik = "";
                 Date now = Calendar.getInstance().getTime();
                 nilai_jam = now.getHours();
                 nilai_menit = now.getMinutes();
                 nilai_detik = now.getSeconds();
                 if (nilai_jam <= 9) {
                     nol_jam = "0";
                 }
                 if (nilai_menit <= 9) {
                     nol_menit = "0";
                 }
                 if (nilai_detik <= 9) {
                     nol_detik = "0";
                 }
                 String jam = nol_jam + Integer.toString(nilai_jam);
                 String menit = nol_menit + Integer.toString(nilai_menit);
                 String detik = nol_detik + Integer.toString(nilai_detik);
                 Jam.setSelectedItem(jam);
                 Menit.setSelectedItem(menit);
                 Detik.setSelectedItem(detik);
             }
         };
         new Timer(1000, taskPerformer).start();
     }
 
     private void simpan() {
    if (Sequel.menyimpantf("catatan_observasi_hemodialisa", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 16, new String[]{
        TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
        QB.getText(), QD.getText(), TekananArteri.getText(), TekananVena.getText(), TMP.getText(), UFR.getText(),
        Tensi.getText(), Nadi.getText(), Suhu.getText(), SPO.getText(), Tindakan.getText(), UFG.getText(), NIP.getText()
    }) == true) {
        // kode lainnya tidak perlu diubah
        tabMode.addRow(new String[]{
            TNoRw.getText(), TNoRM.getText(), TPasien.getText(), Umur.getText(), JK.getText(), TglLahir.getText(),
            Valid.SetTgl(Tanggal.getSelectedItem() + ""), Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
            QB.getText(), QD.getText(), TekananArteri.getText(), TekananVena.getText(), TMP.getText(), UFR.getText(),
            Tensi.getText(), Nadi.getText(), Suhu.getText(), SPO.getText(), Tindakan.getText(), UFG.getText(),
            NIP.getText(), NamaPetugas.getText()
        });
        emptTeks();
        LCount.setText("" + tabMode.getRowCount());
    }
}
 
     private void hapus() {
         if (Sequel.queryu2tf("delete from catatan_observasi_hemodialisa where no_rawat=? and tgl_perawatan=? and jam_rawat=?", 3, new String[]{
             tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString(),
             tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString()
         }) == true) {
             tabMode.removeRow(tbObat.getSelectedRow());
             LCount.setText("" + tabMode.getRowCount());
             emptTeks();
         }
     }
 
     private void ganti() {
         if (Sequel.mengedittf("catatan_observasi_hemodialisa", "no_rawat=? and tgl_perawatan=? and jam_rawat=?", "no_rawat=?,tgl_perawatan=?,jam_rawat=?,qb=?,qd=?,tekanan_arteri=?,tekanan_vena=?,tmp=?,ufr=?,tensi=?,nadi=?,suhu=?,spo2=?,tindakan=?,ufg=?,nip=?", 19, new String[]{
             TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
             QB.getText(), QD.getText(), TekananArteri.getText(), TekananVena.getText(), TMP.getText(), UFR.getText(),
             Tensi.getText(), Nadi.getText(), Suhu.getText(), SPO.getText(), Tindakan.getText(), UFG.getText(), NIP.getText(),
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
             tbObat.setValueAt(TekananArteri.getText(), tbObat.getSelectedRow(), 10);
             tbObat.setValueAt(TekananVena.getText(), tbObat.getSelectedRow(), 11);
             tbObat.setValueAt(TMP.getText(), tbObat.getSelectedRow(), 12);
             tbObat.setValueAt(UFR.getText(), tbObat.getSelectedRow(), 13);
             tbObat.setValueAt(Tensi.getText(), tbObat.getSelectedRow(), 14);
             tbObat.setValueAt(Nadi.getText(), tbObat.getSelectedRow(), 15);
             tbObat.setValueAt(Suhu.getText(), tbObat.getSelectedRow(), 16);
             tbObat.setValueAt(SPO.getText(), tbObat.getSelectedRow(), 17);
             tbObat.setValueAt(Tindakan.getText(), tbObat.getSelectedRow(), 18);
             tbObat.setValueAt(UFG.getText(), tbObat.getSelectedRow(), 19);
             tbObat.setValueAt(NIP.getText(), tbObat.getSelectedRow(), 20);
             tbObat.setValueAt(NamaPetugas.getText(), tbObat.getSelectedRow(), 21);
             emptTeks();
         }
     }

    public void isCek(){
        BtnSimpan.setEnabled(akses.getcatatan_observasi_hemodialisa());
        BtnHapus.setEnabled(akses.getcatatan_observasi_hemodialisa());
        BtnEdit.setEnabled(akses.getcatatan_observasi_hemodialisa());
        BtnPrint.setEnabled(akses.getcatatan_observasi_hemodialisa()); 
        if(akses.getjml2()>=1){
            NIP.setEditable(false);
            btnPetugas.setEnabled(false);
            NIP.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(NIP.getText()));
            if(NamaPetugas.getText().equals("")){
                NIP.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }  
        
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
                ChkKejadian.setEnabled(false);
                Jam.setEnabled(false);
                Menit.setEnabled(false);
                Detik.setEnabled(false);
            }
        }
    }
    
 }