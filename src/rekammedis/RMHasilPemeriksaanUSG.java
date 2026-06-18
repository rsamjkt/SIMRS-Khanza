/*
 * By Mas Elkhanza
 */


package rekammedis;

import bridging.ApiOrthanc;
import bridging.OrthancDICOM;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.io.OutputStream;
import org.json.JSONObject;
import java.util.UUID;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.HyperlinkEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author perpustakaan
 */
public final class RMHasilPemeriksaanUSG extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeDicom;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private RMCariPemeriksaan caripemeriksaan=new RMCariPemeriksaan(null,false);    
    private StringBuilder htmlContent;
    private String finger="";
    private JsonNode root;
    private String usg;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMHasilPemeriksaanUSG(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","Kode Dokter","Nama Dokter","Tanggal","Usia Kehamilan",
            "Janin","Jumlah Janin","Lokasi","Letak Janin","Frekuensi HR","Presentasi","GS","CRL","DBP","FL","AC","HC","TBJ","Diagnosa Klinis",
            "Plasenta Berimplatansi","Derajat Maturitas","Air Ketuban","Jenis Kelamin","ICA","Kelainan Kongenital Mayor","Kesimpulan","Taksiran Persalinan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 30; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(115);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setPreferredWidth(70);
            }else if(i==10){
                column.setPreferredWidth(60);
            }else if(i==11){
                column.setPreferredWidth(60);
            }else if(i==12){
                column.setPreferredWidth(60);
            }else if(i==13){
                column.setPreferredWidth(60);
            }else if(i==14){
                column.setPreferredWidth(30);
            }else if(i==15){
                column.setPreferredWidth(30);
            }else if(i==16){
                column.setPreferredWidth(30);
            }else if(i==17){
                column.setPreferredWidth(30);
            }else if(i==18){
                column.setPreferredWidth(30);
            }else if(i==19){
                column.setPreferredWidth(30);
            }else if(i==20){
                column.setPreferredWidth(30);
            }else if(i==21){
                column.setPreferredWidth(60);
            }else if(i==22){
                column.setPreferredWidth(60);
            }else if(i==23){
                column.setPreferredWidth(60);
            }else if(i==24){
                column.setPreferredWidth(70);
            }else if(i==25){
                column.setPreferredWidth(50);
            }else if(i==26){
                column.setPreferredWidth(30);
            }else if(i==27){
                column.setPreferredWidth(147);
            }else if(i==28){
                column.setPreferredWidth(60);
            }else if(i==29){
                column.setPreferredWidth(100);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDicom=new DefaultTableModel(null,new Object[]{
            "UUID Pasien","ID Studies","ID Series"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbListDicom.setModel(tabModeDicom);
        tbListDicom.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbListDicom.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbListDicom.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(270);
            }else if(i==2){
                column.setPreferredWidth(270);
            }
        }
        tbListDicom.setDefaultRenderer(Object.class, new WarnaTable());
        
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        UsiaKehamilanHpht.setDocument(new batasInput((byte)10).getKata(UsiaKehamilanHpht));
        HR.setDocument(new batasInput((int)40).getKata(HR));
        UkuranKantong.setDocument(new batasInput((int)6).getKata(UkuranKantong));
        UkuranBokong.setDocument(new batasInput((int)6).getKata(UkuranBokong));
        DiameterBiparietal.setDocument(new batasInput((int)6).getKata(DiameterBiparietal));
        PanjangFemur.setDocument(new batasInput((int)6).getKata(PanjangFemur));
        LingkarAbdomen.setDocument(new batasInput((int)6).getKata(LingkarAbdomen));
        LingkarKepala.setDocument(new batasInput((int)6).getKata(LingkarKepala));
        TafsiranBerat.setDocument(new batasInput((int)6).getKata(TafsiranBerat));
        DiagnosaKlinis.setDocument(new batasInput((int)50).getKata(DiagnosaKlinis));
        Plasenta.setDocument(new batasInput((int)50).getKata(Plasenta));
        IndexCairan.setDocument(new batasInput((int)40).getKata(IndexCairan));
        Kelainan.setDocument(new batasInput((int)60).getKata(Kelainan));
        Kesimpulan.setDocument(new batasInput((int)200).getKata(Kesimpulan));
        TaksiranPersalinan = new widget.TextBox();
        TaksiranPersalinan.setDocument(new batasInput((byte)10).getKata(TaksiranPersalinan));
        TaksiranPersalinan.setName("TaksiranPersalinan");
        TaksiranPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TaksiranPersalinanKeyPressed(evt);
            }
        });
        jLabelTP = new widget.Label();
        jLabelTP.setText("Taksiran Persalinan (HPL) :");
        jLabelTP.setName("jLabelTP");
        FormInput.add(jLabelTP);
        jLabelTP.setBounds(10, 425, 165, 23);
        FormInput.add(TaksiranPersalinan);
        TaksiranPersalinan.setBounds(180, 425, 120, 23);
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
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KdDokter.requestFocus();
                }
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
             
        
        caripemeriksaan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                for(i=0;i<caripemeriksaan.getTable().getRowCount();i++){
                    if(caripemeriksaan.getTable().getValueAt(i, 0).toString().equals("true")){
                      Kesimpulan.append(caripemeriksaan.getTable().getValueAt(i,3).toString()+", ");
                      Kesimpulan.requestFocus();
                    }
                  } 
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
  
                
        ChkAccor.setSelected(false);
        isPhoto();
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        LoadHTML2.setEditable(false);
        LoadHTML2.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML2.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
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
        LoadHTML.setDocument(doc);
        LoadHTML2.setDocument(doc);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadHTML = new widget.editorpane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianMedis = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel9 = new widget.Label();
        UsiaKehamilanHpht = new widget.TextBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel10 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        label11 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel31 = new widget.Label();
        HR = new widget.TextBox();
        jLabel32 = new widget.Label();
        DiagnosaKlinis = new widget.TextBox();
        jLabel33 = new widget.Label();
        UkuranKantong = new widget.TextBox();
        jLabel34 = new widget.Label();
        UkuranBokong = new widget.TextBox();
        jLabel36 = new widget.Label();
        DiameterBiparietal = new widget.TextBox();
        Presentasi = new widget.ComboBox();
        jLabel46 = new widget.Label();
        jLabel37 = new widget.Label();
        PanjangFemur = new widget.TextBox();
        jLabel38 = new widget.Label();
        LingkarAbdomen = new widget.TextBox();
        LingkarKepala = new widget.TextBox();
        jLabel39 = new widget.Label();
        TafsiranBerat = new widget.TextBox();
        jLabel41 = new widget.Label();
        Plasenta = new widget.TextBox();
        jLabel125 = new widget.Label();
        DerajatMaturitas = new widget.ComboBox();
        JumlahAir = new widget.ComboBox();
        jLabel126 = new widget.Label();
        jLabel42 = new widget.Label();
        IndexCairan = new widget.TextBox();
        Kelainan = new widget.TextBox();
        jLabel43 = new widget.Label();
        jLabel127 = new widget.Label();
        PeluangSex = new widget.ComboBox();
        jLabel44 = new widget.Label();
        scrollPane17 = new widget.ScrollPane();
        Kesimpulan = new widget.TextArea();
        jLabel11 = new widget.Label();
        jLabel14 = new widget.Label();
        Janin = new widget.ComboBox();
        jLabel45 = new widget.Label();
        JumlahJanin = new widget.ComboBox();
        Lokasi = new widget.ComboBox();
        jLabel13 = new widget.Label();
        jLabel12 = new widget.Label();
        Letak = new widget.ComboBox();
        BtnDokter5 = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
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
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        TabData = new javax.swing.JTabbedPane();
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        BtnWAPasien = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();
        FormOrthan = new widget.PanelBiasa();
        Scroll6 = new widget.ScrollPane();
        tbListDicom = new widget.Table();
        panelGlass7 = new widget.panelisi();
        btnDicom = new widget.Button();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMedis.setText("Formulir Hasil Pemeriksaan USG");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Hasil Pemeriksaan USG OBSTETRI ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50)));
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(467, 500));
        internalFrame1.setLayout(new java.awt.BorderLayout());

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.setPreferredSize(new java.awt.Dimension(457, 480));
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 480));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(750, 393));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 40, 110, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(186, 40, 295, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(484, 40, 28, 23);

        jLabel9.setText("Usia Kehamilan Berdasarkan HPHT:");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(20, 80, 170, 23);

        UsiaKehamilanHpht.setHighlighter(null);
        UsiaKehamilanHpht.setName("UsiaKehamilanHpht"); // NOI18N
        UsiaKehamilanHpht.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaKehamilanHphtKeyPressed(evt);
            }
        });
        FormInput.add(UsiaKehamilanHpht);
        UsiaKehamilanHpht.setBounds(200, 80, 70, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 750, 1);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(538, 40, 52, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-12-2023 10:16:37" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(594, 40, 130, 23);

        jLabel31.setText("Frekuensi HR :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(10, 140, 83, 23);

        HR.setFocusTraversalPolicyProvider(true);
        HR.setName("HR"); // NOI18N
        HR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HRKeyPressed(evt);
            }
        });
        FormInput.add(HR);
        HR.setBounds(100, 140, 270, 23);

        jLabel32.setText("Diagnosa Klinis :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(30, 260, 90, 23);

        DiagnosaKlinis.setFocusTraversalPolicyProvider(true);
        DiagnosaKlinis.setName("DiagnosaKlinis"); // NOI18N
        DiagnosaKlinis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKlinisKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaKlinis);
        DiagnosaKlinis.setBounds(130, 260, 190, 23);

        jLabel33.setText("Ukuran Kantong Gestasi (GS) :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(10, 170, 165, 23);

        UkuranKantong.setFocusTraversalPolicyProvider(true);
        UkuranKantong.setName("UkuranKantong"); // NOI18N
        UkuranKantong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UkuranKantongKeyPressed(evt);
            }
        });
        FormInput.add(UkuranKantong);
        UkuranKantong.setBounds(180, 170, 60, 23);

        jLabel34.setText("Ukuran Bokong - Kepala (CRL) :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(250, 170, 180, 23);

        UkuranBokong.setFocusTraversalPolicyProvider(true);
        UkuranBokong.setName("UkuranBokong"); // NOI18N
        UkuranBokong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UkuranBokongKeyPressed(evt);
            }
        });
        FormInput.add(UkuranBokong);
        UkuranBokong.setBounds(440, 170, 60, 23);

        jLabel36.setText("Biparietal Diameter (BPD) :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(520, 170, 150, 23);

        DiameterBiparietal.setFocusTraversalPolicyProvider(true);
        DiameterBiparietal.setName("DiameterBiparietal"); // NOI18N
        DiameterBiparietal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiameterBiparietalKeyPressed(evt);
            }
        });
        FormInput.add(DiameterBiparietal);
        DiameterBiparietal.setBounds(680, 170, 60, 23);

        Presentasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kepala", "Sungsang", "Melintang", "Bokong", "Dinamis" }));
        Presentasi.setName("Presentasi"); // NOI18N
        Presentasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PresentasiActionPerformed(evt);
            }
        });
        Presentasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PresentasiKeyPressed(evt);
            }
        });
        FormInput.add(Presentasi);
        Presentasi.setBounds(460, 140, 120, 20);

        jLabel46.setText("Presentasi:");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(400, 140, 60, 23);

        jLabel37.setText("Panjang Femur (FL) :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(10, 200, 165, 23);

        PanjangFemur.setFocusTraversalPolicyProvider(true);
        PanjangFemur.setName("PanjangFemur"); // NOI18N
        PanjangFemur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangFemurKeyPressed(evt);
            }
        });
        FormInput.add(PanjangFemur);
        PanjangFemur.setBounds(180, 200, 60, 23);

        jLabel38.setText("Lingkar Abdomen (AC) :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(250, 200, 180, 23);

        LingkarAbdomen.setFocusTraversalPolicyProvider(true);
        LingkarAbdomen.setName("LingkarAbdomen"); // NOI18N
        LingkarAbdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LingkarAbdomenKeyPressed(evt);
            }
        });
        FormInput.add(LingkarAbdomen);
        LingkarAbdomen.setBounds(440, 200, 60, 23);

        jLabel39b = new widget.Label();
        jLabel39b.setText("Lingkar Kepala (HC) :");
        jLabel39b.setName("jLabel39b");
        FormInput.add(jLabel39b);
        jLabel39b.setBounds(250, 230, 180, 23);

        LingkarKepala.setFocusTraversalPolicyProvider(true);
        LingkarKepala.setName("LingkarKepala");
        LingkarKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LingkarKepalaKeyPressed(evt);
            }
        });
        FormInput.add(LingkarKepala);
        LingkarKepala.setBounds(440, 230, 60, 23);

        jLabel39.setText("Tafsiran berat Janin (TBJ) :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(520, 200, 150, 23);

        TafsiranBerat.setFocusTraversalPolicyProvider(true);
        TafsiranBerat.setName("TafsiranBerat"); // NOI18N
        TafsiranBerat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TafsiranBeratKeyPressed(evt);
            }
        });
        FormInput.add(TafsiranBerat);
        TafsiranBerat.setBounds(680, 200, 60, 23);

        jLabel41.setText("Plasenta Berimplatansi Di :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(310, 260, 150, 23);

        Plasenta.setFocusTraversalPolicyProvider(true);
        Plasenta.setName("Plasenta"); // NOI18N
        Plasenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PlasentaKeyPressed(evt);
            }
        });
        FormInput.add(Plasenta);
        Plasenta.setBounds(470, 260, 270, 23);

        jLabel125.setText("Derajat Maturitas Plasenta :");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(10, 290, 165, 23);

        DerajatMaturitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3" }));
        DerajatMaturitas.setName("DerajatMaturitas"); // NOI18N
        DerajatMaturitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DerajatMaturitasKeyPressed(evt);
            }
        });
        FormInput.add(DerajatMaturitas);
        DerajatMaturitas.setBounds(180, 290, 60, 23);

        JumlahAir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cukup", "Berkurang", "Lebih" }));
        JumlahAir.setName("JumlahAir"); // NOI18N
        JumlahAir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahAirKeyPressed(evt);
            }
        });
        FormInput.add(JumlahAir);
        JumlahAir.setBounds(370, 290, 100, 23);

        jLabel126.setText("Jumlah Air Ketuban :");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(250, 290, 120, 23);

        jLabel42.setText("Indeks Cairan Amnion (ICA) :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(10, 320, 165, 23);

        IndexCairan.setFocusTraversalPolicyProvider(true);
        IndexCairan.setName("IndexCairan"); // NOI18N
        IndexCairan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IndexCairanKeyPressed(evt);
            }
        });
        FormInput.add(IndexCairan);
        IndexCairan.setBounds(180, 320, 160, 23);

        Kelainan.setFocusTraversalPolicyProvider(true);
        Kelainan.setName("Kelainan"); // NOI18N
        Kelainan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelainanKeyPressed(evt);
            }
        });
        FormInput.add(Kelainan);
        Kelainan.setBounds(500, 320, 240, 23);

        jLabel43.setText("Kelainan Kongenital Mayor :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(330, 320, 160, 23);

        jLabel127.setText("Jenis Kelamin :");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(480, 290, 80, 23);

        PeluangSex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Belum Dapat di Tentukan", "Laki-laki", "Perempuan" }));
        PeluangSex.setName("PeluangSex"); // NOI18N
        PeluangSex.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PeluangSexKeyPressed(evt);
            }
        });
        FormInput.add(PeluangSex);
        PeluangSex.setBounds(570, 290, 160, 23);

        jLabel44.setText("Kesimpulan :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(10, 350, 165, 23);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N

        Kesimpulan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kesimpulan.setColumns(20);
        Kesimpulan.setRows(5);
        Kesimpulan.setName("Kesimpulan"); // NOI18N
        Kesimpulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesimpulanKeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(Kesimpulan);

        FormInput.add(scrollPane17);
        scrollPane17.setBounds(180, 350, 555, 63);

        jLabel11.setText("minggu");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(270, 80, 40, 23);

        jLabel14.setText("Janin:");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(20, 110, 30, 23);

        Janin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hidup", "Tidak hidup" }));
        Janin.setName("Janin"); // NOI18N
        Janin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JaninKeyPressed(evt);
            }
        });
        FormInput.add(Janin);
        Janin.setBounds(70, 110, 80, 20);

        jLabel45.setText("Jumlah Janin:");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(160, 110, 70, 23);

        JumlahJanin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tunggal ", "Gemeli" }));
        JumlahJanin.setName("JumlahJanin"); // NOI18N
        JumlahJanin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JumlahJaninActionPerformed(evt);
            }
        });
        JumlahJanin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahJaninKeyPressed(evt);
            }
        });
        FormInput.add(JumlahJanin);
        JumlahJanin.setBounds(240, 110, 80, 20);

        Lokasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Intrauterine", "Ekstrauterine", "-" }));
        Lokasi.setName("Lokasi"); // NOI18N
        Lokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiKeyPressed(evt);
            }
        });
        FormInput.add(Lokasi);
        Lokasi.setBounds(380, 110, 160, 20);

        jLabel13.setText("Lokasi:");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(330, 110, 40, 23);

        jLabel12.setText("Letak Janin:");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(550, 110, 60, 23);

        Letak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Memanjang", "Sungsang", "Oblique", "Dinamis" }));
        Letak.setName("Letak"); // NOI18N
        Letak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LetakKeyPressed(evt);
            }
        });
        FormInput.add(Letak);
        Letak.setBounds(620, 110, 110, 20);

        BtnDokter5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter5.setMnemonic('2');
        BtnDokter5.setToolTipText("Alt+2");
        BtnDokter5.setName("BtnDokter5"); // NOI18N
        BtnDokter5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter5ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter5);
        BtnDokter5.setBounds(140, 370, 28, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Hasil USG Obstetri", null, internalFrame2, "");

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

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

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-12-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-12-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
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
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(430, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 248));
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

        TabData.setBackground(new java.awt.Color(254, 255, 254));
        TabData.setForeground(new java.awt.Color(50, 50, 50));
        TabData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabData.setName("TabData"); // NOI18N
        TabData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabDataMouseClicked(evt);
            }
        });

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(null);
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
        BtnRefreshPhoto1.setMnemonic('U');
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Alt+U");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnRefreshPhoto1);

        BtnWAPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/whatsapp.png"))); // NOI18N
        BtnWAPasien.setMnemonic('U');
        BtnWAPasien.setText("Pasien");
        BtnWAPasien.setToolTipText("Alt+U");
        BtnWAPasien.setName("BtnWAPasien"); // NOI18N
        BtnWAPasien.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnWAPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnWAPasienActionPerformed(evt);
            }
        });
        FormPass3.add(BtnWAPasien);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        FormPhoto.add(Scroll5, java.awt.BorderLayout.CENTER);

        TabData.addTab("Gambar Pemeriksaan USG", FormPhoto);

        FormOrthan.setBackground(new java.awt.Color(255, 255, 255));
        FormOrthan.setBorder(null);
        FormOrthan.setName("FormOrthan"); // NOI18N
        FormOrthan.setPreferredSize(new java.awt.Dimension(115, 73));
        FormOrthan.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbListDicom.setName("tbListDicom"); // NOI18N
        Scroll6.setViewportView(tbListDicom);

        FormOrthan.add(Scroll6, java.awt.BorderLayout.CENTER);

        panelGlass7.setBorder(null);
        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(115, 40));

        btnDicom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        btnDicom.setMnemonic('T');
        btnDicom.setText("Tampilkan DICOM");
        btnDicom.setToolTipText("Alt+T");
        btnDicom.setName("btnDicom"); // NOI18N
        btnDicom.setPreferredSize(new java.awt.Dimension(150, 30));
        btnDicom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDicomActionPerformed(evt);
            }
        });
        panelGlass7.add(btnDicom);

        FormOrthan.add(panelGlass7, java.awt.BorderLayout.PAGE_END);

        TabData.addTab("Integrasi Orthanc", FormOrthan);

        PanelAccor.add(TabData, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Hasil USG Obstetri", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        internalFrame1.getAccessibleContext().setAccessibleName(""); // NOI18N

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(DiagnosaKlinis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaKlinis,"Diagnosa Klinis");
        }else if(HR.getText().trim().equals("")){
            Valid.textKosong(HR,"Frekuensi HR");
        }else if(Kesimpulan.getText().trim().equals("")){
            Valid.textKosong(Kesimpulan,"Kesimpulan");
        }else{
            if(Sequel.menyimpantf("hasil_pemeriksaan_usg","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",27,new String[]{
        TNoRw.getText(),
        Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
        KdDokter.getText(),
        UsiaKehamilanHpht.getText(),
        Janin.getSelectedItem().toString(),
        JumlahJanin.getSelectedItem().toString(),
        Lokasi.getSelectedItem().toString(),
        Letak.getSelectedItem().toString(),
        HR.getText(),
        Presentasi.getSelectedItem().toString(),
        UkuranKantong.getText(),
        UkuranBokong.getText(),
        DiameterBiparietal.getText(),
        PanjangFemur.getText(),
        LingkarAbdomen.getText(),
        LingkarKepala.getText(),
        TafsiranBerat.getText(),
        DiagnosaKlinis.getText(),
        Plasenta.getText(),
        DerajatMaturitas.getSelectedItem().toString(),
        JumlahAir.getSelectedItem().toString(),
        PeluangSex.getSelectedItem().toString(),
        IndexCairan.getText(),
        Kelainan.getText(),
        Kesimpulan.getText(),
        "",
        TaksiranPersalinan.getText()
    })==true){
        emptTeks();
}
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,TerapiPreOp,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
        hapus();
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(DiagnosaKlinis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaKlinis,"Diagnosa Klinis");
        }else if(HR.getText().trim().equals("")){
            Valid.textKosong(HR,"HR");
        }else if(Kesimpulan.getText().trim().equals("")){
            Valid.textKosong(Kesimpulan,"Kesimpulan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
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
            try{
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,hasil_pemeriksaan_usg.tanggal,"+
                            "hasil_pemeriksaan_usg.kd_dokter,dokter.nm_dokter,hasil_pemeriksaan_usg.diagnosa_klinis,"+
                            "hasil_pemeriksaan_usg.usiakehamilanhpht,hasil_pemeriksaan_usg.usiakehamilanhpht,"+
                            "hasil_pemeriksaan_usg.janin,hasil_pemeriksaan_usg.jumlahjanin,hasil_pemeriksaan_usg.lokasi,hasil_pemeriksaan_usg.letakjanin,"+
                            "hasil_pemeriksaan_usg.frekuensi_hr,hasil_pemeriksaan_usg.presentasi,"+
                            "hasil_pemeriksaan_usg.kantong_gestasi,hasil_pemeriksaan_usg.ukuran_bokongkepala,"+

                            "hasil_pemeriksaan_usg.diameter_biparietal,hasil_pemeriksaan_usg.panjang_femur,"+
                            "hasil_pemeriksaan_usg.lingkar_abdomen,hasil_pemeriksaan_usg.lingkar_kepala,hasil_pemeriksaan_usg.tafsiran_berat_janin,"+
                            "hasil_pemeriksaan_usg.plasenta_berimplatansi,hasil_pemeriksaan_usg.derajat_maturitas,hasil_pemeriksaan_usg.jumlah_air_ketuban,"+
                            "hasil_pemeriksaan_usg.indek_cairan_ketuban,hasil_pemeriksaan_usg.kelainan_kongenital,hasil_pemeriksaan_usg.peluang_sex,"+
                            "hasil_pemeriksaan_usg.kesimpulan,hasil_pemeriksaan_usg.taksiran_persalinan from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join hasil_pemeriksaan_usg on reg_periksa.no_rawat=hasil_pemeriksaan_usg.no_rawat "+
                            "inner join dokter on hasil_pemeriksaan_usg.kd_dokter=dokter.kd_dokter where "+
                            "hasil_pemeriksaan_usg.tanggal between ? and ? order by hasil_pemeriksaan_usg.tanggal");
                }else{
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,hasil_pemeriksaan_usg.tanggal,"+
                            "hasil_pemeriksaan_usg.kd_dokter,dokter.nm_dokter,hasil_pemeriksaan_usg.diagnosa_klinis,"+
                            "hasil_pemeriksaan_usg.usiakehamilanhpht,"+
                            "hasil_pemeriksaan_usg.janin,hasil_pemeriksaan_usg.jumlahjanin,hasil_pemeriksaan_usg.lokasi,hasil_pemeriksaan_usg.letakjanin,"+
                            "hasil_pemeriksaan_usg.frekuensi_hr,hasil_pemeriksaan_usg.presentasi,"+
                            "hasil_pemeriksaan_usg.kantong_gestasi,hasil_pemeriksaan_usg.ukuran_bokongkepala,"+
                            "hasil_pemeriksaan_usg.diameter_biparietal,hasil_pemeriksaan_usg.panjang_femur,"+
                            "hasil_pemeriksaan_usg.lingkar_abdomen,hasil_pemeriksaan_usg.lingkar_kepala,hasil_pemeriksaan_usg.tafsiran_berat_janin,"+
                            "hasil_pemeriksaan_usg.plasenta_berimplatansi,hasil_pemeriksaan_usg.derajat_maturitas,hasil_pemeriksaan_usg.jumlah_air_ketuban,"+
                            "hasil_pemeriksaan_usg.indek_cairan_ketuban,hasil_pemeriksaan_usg.kelainan_kongenital,hasil_pemeriksaan_usg.peluang_sex,"+
                            "hasil_pemeriksaan_usg.kesimpulan,hasil_pemeriksaan_usg.taksiran_persalinan from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join hasil_pemeriksaan_usg on reg_periksa.no_rawat=hasil_pemeriksaan_usg.no_rawat "+
                            "inner join dokter on hasil_pemeriksaan_usg.kd_dokter=dokter.kd_dokter where "+
                            "hasil_pemeriksaan_usg.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                            "hasil_pemeriksaan_usg.kd_dokter like ? or dokter.nm_dokter like ?) order by hasil_pemeriksaan_usg.tanggal");
                }

                try {
                    if(TCari.getText().trim().equals("")){
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
                    } 
                    rs=ps.executeQuery();
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.RM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Dokter</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Dokter</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+  
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Usia Kehamilan</b></td>"+    
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Janin</b></td>"+   
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jumlah Janin</b></td>"+   
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Lokasi</b></td>"+    
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Letak Janin</b></td>"+    
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Frekuensi HR</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Presentasi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>GS</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>CRL</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>DBP</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>FL</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>AC</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>HC</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TBJ</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Klinis</b></td>"+    
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Plasenta Berimplatansi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Derajat Maturitas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Air Ketuban</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jenis Kelamin</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Indeks Cairan Amnion (ICA)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kelainan Kongenital Mayor</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kesimpulan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Taksiran Persalinan (HPL)</b></td>"+
                        "</tr>"
                    );
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                               "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                               "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                               "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                               "<td valign='top'>"+rs.getString("kd_dokter")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_dokter")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal")+"</td>"+                                      
                               "<td valign='top'>"+rs.getString("usiakehamilanhpht")+"</td>"+                                       
                               "<td valign='top'>"+rs.getString("janin")+"</td>"+
                               "<td valign='top'>"+rs.getString("jumlahjanin")+"</td>"+
                               "<td valign='top'>"+rs.getString("lokasi")+"</td>"+
                               "<td valign='top'>"+rs.getString("letakjanin")+"</td>"+
                               "<td valign='top'>"+rs.getString("frekuensi_hr")+"</td>"+
                               "<td valign='top'>"+rs.getString("presentasi")+"</td>"+
                               "<td valign='top'>"+rs.getString("kiriman_dari")+"</td>"+                                       
                               "<td valign='top'>"+rs.getString("kantong_gestasi")+"</td>"+
                               "<td valign='top'>"+rs.getString("ukuran_bokongkepala")+"</td>"+
                               "<td valign='top'>"+rs.getString("diameter_biparietal")+"</td>"+
                               "<td valign='top'>"+rs.getString("panjang_femur")+"</td>"+
                               "<td valign='top'>"+rs.getString("lingkar_abdomen")+"</td>"+
                               "<td valign='top'>"+rs.getString("lingkar_kepala")+"</td>"+
                               "<td valign='top'>"+rs.getString("tafsiran_berat_janin")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosa_klinis")+"</td>"+           
                               "<td valign='top'>"+rs.getString("plasenta_berimplatansi")+"</td>"+
                               "<td valign='top'>"+rs.getString("derajat_maturitas")+"</td>"+
                               "<td valign='top'>"+rs.getString("jumlah_air_ketuban")+"</td>"+
                               "<td valign='top'>"+rs.getString("indek_cairan_ketuban")+"</td>"+
                               "<td valign='top'>"+rs.getString("kelainan_kongenital")+"</td>"+
                               "<td valign='top'>"+rs.getString("peluang_sex")+"</td>"+
                               "<td valign='top'>"+rs.getString("kesimpulan")+"</td>"+
                               "<td valign='top'>"+rs.getString("taksiran_persalinan")+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='2100px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                           htmlContent.toString()+
                          "</table>"+
                        "</html>"
                    );

                    File g = new File("file2.css");            
                    BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                    bg.write(
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
                    bg.close();

                    File f = new File("DataHasilPemeriksaanUSG.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='2100px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA PENILAIAN PRE OPERASI<br><br></font>"+        
                                        "</td>"+
                                   "</tr>"+
                                "</table>")
                    );
                    bw.close();                         
                    Desktop.getDesktop().browse(f.toURI());
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
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                isPhoto();
                panggilPhoto();
                getData();
                tampilOrthanc();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
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
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Edukasi,Hubungan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        //Valid.pindah(evt,Edukasi,Anamnesis);
    }//GEN-LAST:event_TanggalKeyPressed

    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMedisActionPerformed
        // [DIUBAH] Mengambil 'photo' dari 'hasil_pemeriksaan_usg_gambar', LIMIT 1 untuk mengambil satu gambar perwakilan
usg = koneksiDB.CLOUDFLARER2HOST()+Sequel.cariIsi("select photo from hasil_pemeriksaan_usg_gambar where no_rawat=? limit 1", TNoRw.getText())+"";
        
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());      
            
            
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            param.put("photousg",usg);
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),4).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString())); 
            
            Valid.MyReportqry("rptCetakHasilPemeriksaanUSGPhoto.jasper","report","::[ Formulir Hasil Pemeriksaan USG ]::",
                "select reg_periksa.no_rawat,reg_periksa.kd_pj,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y')as tgl_registrasi,"+
                 
                 "reg_periksa.jam_reg, pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,"+
                  "pasien.no_ktp,(pasien.jk)as jkpasien,pasien.tmp_lahir,hasil_pemeriksaan_usg.tanggal,"+
                "hasil_pemeriksaan_usg.kd_dokter,dokter.nm_dokter,hasil_pemeriksaan_usg.diagnosa_klinis,hasil_pemeriksaan_usg.usiakehamilanhpht,"+        
                "hasil_pemeriksaan_usg.janin,hasil_pemeriksaan_usg.jumlahjanin,hasil_pemeriksaan_usg.lokasi,hasil_pemeriksaan_usg.letakjanin,"+
                "hasil_pemeriksaan_usg.frekuensi_hr,hasil_pemeriksaan_usg.presentasi,"+
                "hasil_pemeriksaan_usg.kantong_gestasi,hasil_pemeriksaan_usg.ukuran_bokongkepala,"+
                       
                "hasil_pemeriksaan_usg.diameter_biparietal,hasil_pemeriksaan_usg.panjang_femur,"+
                "hasil_pemeriksaan_usg.lingkar_abdomen,hasil_pemeriksaan_usg.lingkar_kepala,hasil_pemeriksaan_usg.tafsiran_berat_janin,"+
                "hasil_pemeriksaan_usg.plasenta_berimplatansi,hasil_pemeriksaan_usg.derajat_maturitas,hasil_pemeriksaan_usg.jumlah_air_ketuban,"+
                "hasil_pemeriksaan_usg.indek_cairan_ketuban,hasil_pemeriksaan_usg.kelainan_kongenital,hasil_pemeriksaan_usg.peluang_sex,"+
                "hasil_pemeriksaan_usg.kesimpulan,hasil_pemeriksaan_usg.taksiran_persalinan from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join hasil_pemeriksaan_usg on reg_periksa.no_rawat=hasil_pemeriksaan_usg.no_rawat "+
                "inner join dokter on hasil_pemeriksaan_usg.kd_dokter=dokter.kd_dokter where hasil_pemeriksaan_usg.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void HRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HRKeyPressed
        Valid.pindah(evt,Letak,Presentasi);
    }//GEN-LAST:event_HRKeyPressed

    private void DiagnosaKlinisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKlinisKeyPressed
        Valid.pindah(evt,TafsiranBerat,Plasenta);
    }//GEN-LAST:event_DiagnosaKlinisKeyPressed

    private void UkuranKantongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UkuranKantongKeyPressed
        Valid.pindah(evt,Presentasi,UkuranBokong);
    }//GEN-LAST:event_UkuranKantongKeyPressed

    private void UkuranBokongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UkuranBokongKeyPressed
        Valid.pindah(evt,UkuranKantong,DiameterBiparietal);
    }//GEN-LAST:event_UkuranBokongKeyPressed

    private void DiameterBiparietalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiameterBiparietalKeyPressed
        Valid.pindah(evt,UkuranBokong,PanjangFemur);
    }//GEN-LAST:event_DiameterBiparietalKeyPressed

    private void PanjangFemurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangFemurKeyPressed
        Valid.pindah(evt,DiameterBiparietal,LingkarAbdomen);
    }//GEN-LAST:event_PanjangFemurKeyPressed

    private void LingkarAbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LingkarAbdomenKeyPressed
        Valid.pindah(evt,PanjangFemur,LingkarKepala);
    }//GEN-LAST:event_LingkarAbdomenKeyPressed

    private void LingkarKepalaKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt,LingkarAbdomen,TafsiranBerat);
    }

    private void TafsiranBeratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TafsiranBeratKeyPressed
        Valid.pindah(evt,LingkarKepala,DiagnosaKlinis);
    }//GEN-LAST:event_TafsiranBeratKeyPressed

    private void PlasentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PlasentaKeyPressed
        Valid.pindah(evt,DiagnosaKlinis,DerajatMaturitas);
    }//GEN-LAST:event_PlasentaKeyPressed

    private void DerajatMaturitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DerajatMaturitasKeyPressed
        Valid.pindah(evt,Plasenta,JumlahAir);
    }//GEN-LAST:event_DerajatMaturitasKeyPressed

    private void JumlahAirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahAirKeyPressed
        Valid.pindah(evt,DerajatMaturitas,PeluangSex);
    }//GEN-LAST:event_JumlahAirKeyPressed

    private void IndexCairanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IndexCairanKeyPressed
        Valid.pindah(evt,PeluangSex,Kelainan);
    }//GEN-LAST:event_IndexCairanKeyPressed

    private void KelainanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelainanKeyPressed
        Valid.pindah(evt,IndexCairan,Kesimpulan);
    }//GEN-LAST:event_KelainanKeyPressed

    private void PeluangSexKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PeluangSexKeyPressed
        Valid.pindah(evt,JumlahAir,IndexCairan);
    }//GEN-LAST:event_PeluangSexKeyPressed

    private void KesimpulanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesimpulanKeyPressed
        Valid.pindah2(evt,Kelainan,TaksiranPersalinan);
    }//GEN-LAST:event_KesimpulanKeyPressed

    private void TaksiranPersalinanKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt,Kesimpulan,BtnSimpan);
    }

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Pernyataan..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Valid.panggilUrl("hasilpemeriksaanusg/login.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                this.setCursor(Cursor.getDefaultCursor()); 
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
            }
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            panggilPhoto();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void btnDicomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDicomActionPerformed
        if(tabModeDicom.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else {
            if(tbListDicom.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                OrthancDICOM orthan=new OrthancDICOM(null,false);
                orthan.setJudul("::[ DICOM Orthanc Pasien "+tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()+" "+tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()+", Series "+tbListDicom.getValueAt(tbListDicom.getSelectedRow(),2).toString()+" ]::",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString().replaceAll("/",""),tbListDicom.getValueAt(tbListDicom.getSelectedRow(),2).toString(),tbListDicom.getValueAt(tbListDicom.getSelectedRow(),1).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());

                try {
                    orthan.loadURL(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/web-viewer/app/viewer.html?series="+tbListDicom.getValueAt(tbListDicom.getSelectedRow(),2).toString());
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                }
                orthan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                orthan.setLocationRelativeTo(internalFrame1);
                orthan.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
            }
        }
    }//GEN-LAST:event_btnDicomActionPerformed

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        tampilOrthanc();
    }//GEN-LAST:event_TabDataMouseClicked

    private void UsiaKehamilanHphtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaKehamilanHphtKeyPressed
        Valid.pindah(evt,Tanggal,Janin);
    }//GEN-LAST:event_UsiaKehamilanHphtKeyPressed

    private void JumlahJaninActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JumlahJaninActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JumlahJaninActionPerformed

    private void PresentasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PresentasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PresentasiActionPerformed

    private void JaninKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JaninKeyPressed
        Valid.pindah(evt,UsiaKehamilanHpht,UsiaKehamilanHpht);
    }//GEN-LAST:event_JaninKeyPressed

    private void JumlahJaninKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahJaninKeyPressed
        Valid.pindah(evt,Janin,Lokasi);
    }//GEN-LAST:event_JumlahJaninKeyPressed

    private void LokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiKeyPressed
        Valid.pindah(evt,JumlahJanin,Letak);
    }//GEN-LAST:event_LokasiKeyPressed

    private void LetakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LetakKeyPressed
        Valid.pindah(evt,Lokasi,HR);
    }//GEN-LAST:event_LetakKeyPressed

    private void PresentasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PresentasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PresentasiKeyPressed

    private void BtnDokter5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter5ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            caripemeriksaan.setNoRawat(TNoRw.getText());
            caripemeriksaan.tampil();
            caripemeriksaan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            caripemeriksaan.setLocationRelativeTo(internalFrame1);
            caripemeriksaan.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter5ActionPerformed

    private void BtnWAPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnWAPasienActionPerformed
        // TODO add your handling code here:

            if (tbObat.getSelectedRow() != -1) {
        try {
            // Disable SSL verification
            TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                }
            };
            
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
            
            ps = koneksi.prepareStatement(
                "SELECT pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_tlp, hpg.photo " +
                "FROM hasil_pemeriksaan_usg_gambar hpg " +
                "INNER JOIN reg_periksa ON hpg.no_rawat = reg_periksa.no_rawat " +
                "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis " +
                "WHERE reg_periksa.no_rawat = ?"
            );

            ps.setString(1, tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                try {
                    String url = "https://api.qirimwa.my.id/send-media";
                    String apiKey = "a2qO6R52PT4WAfJihaARzeSPYcIWQq"; 
                    String sender = "628179999664";
                    String noWa = rs.getString("no_tlp").replaceAll("\\D+", "");
                    if (noWa.startsWith("0")) {
                        noWa = "62" + noWa.substring(1);
                    }
                    
                    // Gunakan generatePresignedUrl yang sudah ada
                    // [DIUBAH] Menggunakan nama kolom 'photo' dari query yang baru
                    String urlHasil = koneksiDB.CLOUDFLARER2HOST() +"/"+ rs.getString("photo");
                    System.out.println("Generated S3 URL: " + urlHasil);

                    JSONObject jsonRequest = new JSONObject();
                    jsonRequest.put("api_key", apiKey);
                    jsonRequest.put("sender", sender);
                    jsonRequest.put("number", noWa);
                    jsonRequest.put("media_type", "image");
                    jsonRequest.put("caption", "Hasil Pemeriksaan USG");
                    jsonRequest.put("url", urlHasil);
           
                    System.out.println("Request body: " + jsonRequest.toString());

                    URL obj = new URL(url);
                    HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "application/json");
                    con.setDoOutput(true);
                    con.setConnectTimeout(10000);
                    con.setReadTimeout(10000);

                    try (OutputStream os = con.getOutputStream()) {
                        byte[] input = jsonRequest.toString().getBytes(StandardCharsets.UTF_8);
                        os.write(input, 0, input.length);
                        os.flush();
                    }

                    int responseCode = con.getResponseCode();
                    System.out.println("Response Code: " + responseCode);
                    System.out.println("URL Image: " + urlHasil);

                    StringBuilder response = new StringBuilder();
                    try (BufferedReader br = responseCode >= 400 ? 
                            new BufferedReader(new InputStreamReader(con.getErrorStream())) :
                            new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                    }
                    
                    System.out.println("Response body: " + response.toString());

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(response.toString());

                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String dateNow = now.format(formatter);
                    String messageId = UUID.randomUUID().toString();

                    if(jsonNode != null && jsonNode.has("status") && jsonNode.get("status").asBoolean()) {
                        Sequel.queryu("INSERT INTO wa_report VALUES ('" + 
                            messageId + "', '" + 
                            TNoRM.getText() + "', '" + 
                            noWa + "', '" + 
                            akses.getkode() + "', " +
                            "'Kirim Hasil USG', '" + 
                            noWa + "', 'Hasil Pemeriksaan USG', '', 'true', '', '" + 
                            dateNow + "', '" + 
                            dateNow + "')");
                        JOptionPane.showMessageDialog(null, "Berhasil Mengirim Pesan!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        String errorMsg = jsonNode != null && jsonNode.has("msg") ? 
                            jsonNode.get("msg").asText() : 
                            "Unknown error";
                        JOptionPane.showMessageDialog(null, "Gagal mengirim pesan: " + errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } else {
        JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
    }

    }//GEN-LAST:event_BtnWAPasienActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMHasilPemeriksaanUSG dialog = new RMHasilPemeriksaanUSG(new javax.swing.JFrame(), true);
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
    private widget.Button BtnDokter;
    private widget.Button BtnDokter5;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.Button BtnWAPasien;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox DerajatMaturitas;
    private widget.TextBox DiagnosaKlinis;
    private widget.TextBox DiameterBiparietal;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormOrthan;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.TextBox HR;
    private widget.TextBox IndexCairan;
    private widget.ComboBox Janin;
    private widget.ComboBox JumlahAir;
    private widget.ComboBox JumlahJanin;
    private widget.TextBox KdDokter;
    private widget.TextBox Kelainan;
    private widget.TextArea Kesimpulan;
    private widget.Label LCount;
    private widget.ComboBox Letak;
    private widget.TextBox LingkarAbdomen;
    private widget.TextBox LingkarKepala;
    private widget.Label jLabel39b;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.ComboBox Lokasi;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox NmDokter;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox PanjangFemur;
    private widget.ComboBox PeluangSex;
    private widget.TextBox Plasenta;
    private widget.ComboBox Presentasi;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabData;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TafsiranBerat;
    private widget.Tanggal Tanggal;
    private widget.TextBox TglLahir;
    private widget.TextBox UkuranBokong;
    private widget.TextBox UkuranKantong;
    private widget.TextBox UsiaKehamilanHpht;
    private widget.Button btnAmbil;
    private widget.Button btnDicom;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel125;
    private widget.Label jLabel126;
    private widget.Label jLabel127;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane17;
    private widget.Table tbListDicom;
    private widget.Table tbObat;
    private widget.TextBox TaksiranPersalinan;
    private widget.Label jLabelTP;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,hasil_pemeriksaan_usg.tanggal,"+
                        "hasil_pemeriksaan_usg.kd_dokter,dokter.nm_dokter,hasil_pemeriksaan_usg.diagnosa_klinis,"+
                        "hasil_pemeriksaan_usg.usiakehamilanhpht,"+
                        "hasil_pemeriksaan_usg.janin,hasil_pemeriksaan_usg.jumlahjanin,hasil_pemeriksaan_usg.lokasi,hasil_pemeriksaan_usg.letakjanin,"+
                        "hasil_pemeriksaan_usg.frekuensi_hr,hasil_pemeriksaan_usg.presentasi,"+
                        "hasil_pemeriksaan_usg.kantong_gestasi,hasil_pemeriksaan_usg.ukuran_bokongkepala,"+
                        "hasil_pemeriksaan_usg.diameter_biparietal,hasil_pemeriksaan_usg.panjang_femur,"+
                        "hasil_pemeriksaan_usg.lingkar_abdomen,hasil_pemeriksaan_usg.lingkar_kepala,hasil_pemeriksaan_usg.tafsiran_berat_janin,"+
                        "hasil_pemeriksaan_usg.plasenta_berimplatansi,hasil_pemeriksaan_usg.derajat_maturitas,hasil_pemeriksaan_usg.jumlah_air_ketuban,"+
                        "hasil_pemeriksaan_usg.indek_cairan_ketuban,hasil_pemeriksaan_usg.kelainan_kongenital,hasil_pemeriksaan_usg.peluang_sex,"+
                        "hasil_pemeriksaan_usg.kesimpulan,hasil_pemeriksaan_usg.taksiran_persalinan from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join hasil_pemeriksaan_usg on reg_periksa.no_rawat=hasil_pemeriksaan_usg.no_rawat "+
                        "inner join dokter on hasil_pemeriksaan_usg.kd_dokter=dokter.kd_dokter where "+
                        "hasil_pemeriksaan_usg.tanggal between ? and ? order by hasil_pemeriksaan_usg.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,hasil_pemeriksaan_usg.tanggal,"+
                        "hasil_pemeriksaan_usg.kd_dokter,dokter.nm_dokter,hasil_pemeriksaan_usg.diagnosa_klinis,"+
                        "hasil_pemeriksaan_usg.usiakehamilanhpht,"+
                        "hasil_pemeriksaan_usg.janin,hasil_pemeriksaan_usg.jumlahjanin,hasil_pemeriksaan_usg.lokasi,hasil_pemeriksaan_usg.letakjanin,"+
                        "hasil_pemeriksaan_usg.frekuensi_hr,hasil_pemeriksaan_usg.presentasi,"+
                        "hasil_pemeriksaan_usg.kantong_gestasi,hasil_pemeriksaan_usg.ukuran_bokongkepala,"+
                        "hasil_pemeriksaan_usg.diameter_biparietal,hasil_pemeriksaan_usg.panjang_femur,"+
                        "hasil_pemeriksaan_usg.lingkar_abdomen,hasil_pemeriksaan_usg.lingkar_kepala,hasil_pemeriksaan_usg.tafsiran_berat_janin,"+
                        "hasil_pemeriksaan_usg.plasenta_berimplatansi,hasil_pemeriksaan_usg.derajat_maturitas,hasil_pemeriksaan_usg.jumlah_air_ketuban,"+
                        "hasil_pemeriksaan_usg.indek_cairan_ketuban,hasil_pemeriksaan_usg.kelainan_kongenital,hasil_pemeriksaan_usg.peluang_sex,"+
                        "hasil_pemeriksaan_usg.kesimpulan,hasil_pemeriksaan_usg.taksiran_persalinan from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join hasil_pemeriksaan_usg on reg_periksa.no_rawat=hasil_pemeriksaan_usg.no_rawat "+
                        "inner join dokter on hasil_pemeriksaan_usg.kd_dokter=dokter.kd_dokter where "+
                        "hasil_pemeriksaan_usg.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "hasil_pemeriksaan_usg.kd_dokter like ? or dokter.nm_dokter like ?) order by hasil_pemeriksaan_usg.tanggal");
            }
                
            try {
                if(TCari.getText().trim().equals("")){
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
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
                        rs.getString("usiakehamilanhpht"),rs.getString("janin"),rs.getString("jumlahjanin"),rs.getString("lokasi"),
                        rs.getString("letakjanin"),rs.getString("frekuensi_hr"),rs.getString("presentasi"),rs.getString("kantong_gestasi"),
                        rs.getString("ukuran_bokongkepala"),rs.getString("diameter_biparietal"),rs.getString("panjang_femur"),rs.getString("lingkar_abdomen"),
                        rs.getString("lingkar_kepala"),rs.getString("tafsiran_berat_janin"),rs.getString("diagnosa_klinis"),rs.getString("plasenta_berimplatansi"),rs.getString("derajat_maturitas"),
                        rs.getString("jumlah_air_ketuban"),rs.getString("peluang_sex"),rs.getString("indek_cairan_ketuban"),rs.getString("kelainan_kongenital"),rs.getString("kesimpulan"),
                        rs.getString("taksiran_persalinan")
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

        UsiaKehamilanHpht.setText("");
        Janin.setSelectedIndex(0);
        JumlahJanin.setSelectedIndex(0);
        Lokasi.setSelectedIndex(0);
        Letak.setSelectedIndex(0);
        HR.setText("");   
        Presentasi.setSelectedIndex(0);  
        UkuranKantong.setText("");
        UkuranBokong.setText("");
        DiameterBiparietal.setText("");
        PanjangFemur.setText("");
        LingkarAbdomen.setText("");
        LingkarKepala.setText("");
        TafsiranBerat.setText("");
        DiagnosaKlinis.setText("");
        Plasenta.setText("");
        IndexCairan.setText("");
        DerajatMaturitas.setSelectedIndex(0);
        JumlahAir.setSelectedIndex(0);
        PeluangSex.setSelectedIndex(0);
        Kelainan.setText("");
        Kesimpulan.setText("");
        TaksiranPersalinan.setText("");
        Tanggal.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        UsiaKehamilanHpht.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());

            UsiaKehamilanHpht.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Janin.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            JumlahJanin.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Lokasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Letak.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            HR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Presentasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            UkuranKantong.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            UkuranBokong.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            DiameterBiparietal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            PanjangFemur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            LingkarAbdomen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            LingkarKepala.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            TafsiranBerat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            DiagnosaKlinis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Plasenta.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            DerajatMaturitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            JumlahAir.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            PeluangSex.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            IndexCairan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Kelainan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Kesimpulan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            TaksiranPersalinan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29)==null?"":tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            
            DiagnosaKlinis.setText(Sequel.cariIsi("SELECT penilaian FROM pemeriksaan_ralan WHERE penilaian<>\"\" AND no_rawat=? ORDER BY jam_rawat ASC LIMIT 1", TNoRw.getText()));               
            
            
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
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
 
    public void setNoRm(String norwt,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gethasil_pemeriksaan_usg());
    BtnHapus.setEnabled(true);
    BtnEdit.setEnabled(akses.gethasil_pemeriksaan_usg());
    BtnEdit.setEnabled(akses.gethasil_pemeriksaan_usg());
    /*if(akses.getjml2()>=1){
        KdDokter.setEditable(false);
        BtnDokter.setEnabled(false);
        KdDokter.setText(akses.getkode());
        Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?", NmDokter,KdDokter.getText());
        if(NmDokter.getText().equals("")){
            KdDokter.setText("");
            JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
        }
        }*/            
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
       tampil();
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from hasil_pemeriksaan_usg where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tampil();
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
    // [PERBAIKAN] Query UPDATE ini sekarang benar dan mencakup semua kolom
    if(Sequel.mengedittf("hasil_pemeriksaan_usg","no_rawat=?","no_rawat=?, tanggal=?, kd_dokter=?, usiakehamilanhpht=?, janin=?, jumlahjanin=?, lokasi=?, letakjanin=?, "+
            "frekuensi_hr=?, presentasi=?, kantong_gestasi=?, ukuran_bokongkepala=?, diameter_biparietal=?, panjang_femur=?, lingkar_abdomen=?, lingkar_kepala=?, tafsiran_berat_janin=?, "+
            "diagnosa_klinis=?, plasenta_berimplatansi=?, derajat_maturitas=?, jumlah_air_ketuban=?, peluang_sex=?, indek_cairan_ketuban=?, kelainan_kongenital=?, kesimpulan=?, taksiran_persalinan=?", 27, new String[]{
            TNoRw.getText(),
            Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
            KdDokter.getText(),
            UsiaKehamilanHpht.getText(),
            Janin.getSelectedItem().toString(),
            JumlahJanin.getSelectedItem().toString(),
            Lokasi.getSelectedItem().toString(),
            Letak.getSelectedItem().toString(),
            HR.getText(),
            Presentasi.getSelectedItem().toString(),
            UkuranKantong.getText(),
            UkuranBokong.getText(),
            DiameterBiparietal.getText(),
            PanjangFemur.getText(),
            LingkarAbdomen.getText(),
            LingkarKepala.getText(),
            TafsiranBerat.getText(),
            DiagnosaKlinis.getText(),
            Plasenta.getText(),
            DerajatMaturitas.getSelectedItem().toString(),
            JumlahAir.getSelectedItem().toString(),
            PeluangSex.getSelectedItem().toString(),
            IndexCairan.getText(),
            Kelainan.getText(),
            Kesimpulan.getText(),
            TaksiranPersalinan.getText(),
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
           tampil();
           emptTeks();
           TabRawat.setSelectedIndex(1);
    }
}
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(530,HEIGHT));
            TabData.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            TabData.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }

    private void panggilPhoto() {
    if (FormPhoto.isVisible() == true) {
        try {
            // [DIUBAH] Mengambil satu gambar perwakilan dari tabel baru untuk ditampilkan di GUI
ps = koneksi.prepareStatement("SELECT photo FROM hasil_pemeriksaan_usg_gambar WHERE no_rawat=? LIMIT 1");
            try {
                ps.setString(1, tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
                rs = ps.executeQuery();
                if (rs.next()) {
                    if (rs.getString("photo").equals("") || rs.getString("photo").equals("-")) {
                        LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                    } else {
                        String objectKey = rs.getString("photo");
                        String presignedUrl = generatePresignedUrl(objectKey);
                        LoadHTML2.setText("<html><body><center><a href='" + presignedUrl + 
                            "'><img src='" + presignedUrl + 
                            "' alt='photo' width='550' height='550'/></a></center></body></html>");
                    }
                } else {
                    LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
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
            System.out.println("Notif : " + e);
        }
    }
}

private String generatePresignedUrl(String objectKey) {
    try {
        // Ganti dengan kredensial Cloudflare R2 Anda
        final String CLOUDFLARE_R2_ACCESS_KEY = koneksiDB.CLOUDFLARER2ACCESSKEY();
        final String CLOUDFLARE_R2_SECRET_KEY = koneksiDB.CLOUDFLARER2SECRETKEY();
        final String CLOUDFLARE_R2_ACCOUNT_ID = koneksiDB.CLOUDFLARER2ACCOUNTID(); 
        final String BUCKET_NAME = koneksiDB.CLOUDFLAREBUCKETNAME();
        final String REGION = koneksiDB.CLOUDFLAREREGION(); 

        // Waktu kedaluwarsa URL (15 menit dari sekarang)
        long expiration = System.currentTimeMillis() + (15 * 60 * 1000);
        String dateStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String amzDate = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'").format(new Date());

        // Canonical Request
        String canonicalUri = "/" + objectKey;
        String canonicalQueryString = "";  // Tidak ada query string
        String canonicalHeaders = 
                "host:" + BUCKET_NAME + "." + CLOUDFLARE_R2_ACCOUNT_ID + ".r2.cloudflarestorage.com\n" + 
                "x-amz-date:" + amzDate + "\n";
        String signedHeaders = "host;x-amz-date";
        String payloadHash = sha256Hex("".getBytes());  // Body kosong
        String canonicalRequest = "GET\n" + 
                                   canonicalUri + "\n" +
                                   canonicalQueryString + "\n" +
                                   canonicalHeaders + "\n" +
                                   signedHeaders + "\n" + 
                                   payloadHash;

        // String untuk menandatangani
        String stringToSign = "AWS4-HMAC-SHA256\n" + 
                              amzDate + "\n" +
                              dateStamp + "/" + REGION + "/s3/aws4_request\n" +
                              sha256Hex(canonicalRequest.getBytes("UTF-8"));

        // Hitung tanda tangan (signature)
        byte[] signingKey = getSignatureKey(CLOUDFLARE_R2_SECRET_KEY, dateStamp, REGION, "s3");
        String signature = bytesToHex(hmacSHA256(stringToSign, signingKey));

        // Bangun URL presigned
        String presignedUrl = koneksiDB.CLOUDFLARER2HOST()+ canonicalUri +
                              "?X-Amz-Signature=" + signature +
                              "&X-Amz-Date=" + amzDate +
                              "&X-Amz-Algorithm=AWS4-HMAC-SHA256" +
                              "&X-Amz-Credential=" + CLOUDFLARE_R2_ACCESS_KEY + "/" + dateStamp + "/" + REGION + "/s3/aws4_request" +
                              "&X-Amz-SignedHeaders=" + signedHeaders +
                              "&Expires=" + expiration;

        return presignedUrl;

    } catch (Exception e) {
        System.out.println("Error generating presigned URL: " + e);
        return "";
    }
}

// Helper method untuk SHA-256 hash
private String sha256Hex(byte[] data) throws Exception {
    java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
    byte[] digest = md.digest(data);
    return bytesToHex(digest);
}

// HMAC-SHA256 helper method
private byte[] hmacSHA256(String data, byte[] key) throws Exception {
    String algorithm = "HmacSHA256";
    Mac mac = Mac.getInstance(algorithm);
    mac.init(new SecretKeySpec(key, algorithm));
    return mac.doFinal(data.getBytes("UTF-8"));
}

// Convert byte array to hex string
private String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
        sb.append(String.format("%02x", b));
    }
    return sb.toString();
}

// Calculate the signing key
private byte[] getSignatureKey(String key, String dateStamp, String regionName, String serviceName) throws Exception {
    byte[] kSecret = ("AWS4" + key).getBytes("UTF-8");
    byte[] kDate = hmacSHA256(dateStamp, kSecret);
    byte[] kRegion = hmacSHA256(regionName, kDate);
    byte[] kService = hmacSHA256(serviceName, kRegion);
    return hmacSHA256("aws4_request", kService);
}
    
    private void tampilOrthanc() {
        if(TabData.isVisible()==true){
            if(tbObat.getSelectedRow()!= -1){
                 if(TabData.getSelectedIndex()==1){
                     try {
                         Valid.tabelKosong(tabModeDicom);
                         ApiOrthanc orthanc=new ApiOrthanc();
                         root=orthanc.AmbilSeries(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),Valid.SetTgl(DTPCari1.getSelectedItem()+"").replaceAll("-",""),Valid.SetTgl(DTPCari2.getSelectedItem()+"").replaceAll("-",""));
                         for(JsonNode list:root){
                             for(JsonNode sublist:list.path("Series")){
                                  tabModeDicom.addRow(new Object[]{
                                       list.path("PatientMainDicomTags").path("PatientID").asText(),list.path("ID").asText(),sublist.asText()
                                  });   
                             }        
                         }
                     } catch (Exception e) {
                         System.out.println("Notif : "+e);
                     }
                 }
            }
        }
    }
}
