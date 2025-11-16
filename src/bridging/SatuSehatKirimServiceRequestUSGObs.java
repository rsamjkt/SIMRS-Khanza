/*
  by Mas Elkhanza
  Modified by Gemini adapting PHP logic & fixing DateTime exception
 */

package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZoneId; // <<< [FIX] IMPORT TAMBAHAN
import java.time.ZonedDateTime; // <<< [FIX] IMPORT TAMBAHAN
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 *
 * @author dosen
 */
public final class SatuSehatKirimServiceRequestUSGObs extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;   
    private int i=0;
    private String link="",json="",iddokter="",idpasien="";
    private ApiSatuSehat api=new ApiSatuSehat();
    private HttpHeaders headers ;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private SatuSehatCekNIK cekViaSatuSehat=new SatuSehatCekNIK();  
    private StringBuilder htmlContent;    
    
    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public SatuSehatKirimServiceRequestUSGObs(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new String[]{
                "P","No.Rawat","No.RM","Nama Pasien","No.KTP Pasien","Tgl. USG","Nama Dokter",
                "No.KTP Dokter","ID Encounter","ID Lokasi","Nama Poli","Kesimpulan",
                "ID Service Request", "Diagnosa Klinis", "Presentasi", "Jml Air Ketuban",
                "Jumlah Janin", "Lokasi Janin", "Status Janin"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 19; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(110);
            }else if(i==8){
                column.setPreferredWidth(210);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(250);
            }else if(i==12){
                column.setPreferredWidth(210);
            } else {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        
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
        
        try {
            link=koneksiDB.URLFHIRSATUSEHAT();
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }  
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
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
    }
    
    private class IcdCode {
        String code;
        String display;

        public IcdCode(String code, String display) {
            this.code = code;
            this.display = display;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            IcdCode icdCode = (IcdCode) obj;
            return code.equals(icdCode.code);
        }

        @Override
        public int hashCode() {
            return code.hashCode();
        }
    }

    private String buildReasonCodeJson(int row) {
        List<IcdCode> codes = new ArrayList<>();
        String kesimpulan = tbObat.getValueAt(row, 11).toString().toLowerCase().trim();
        String diagnosaKlinis = tbObat.getValueAt(row, 13).toString().trim();
        String presentasi = tbObat.getValueAt(row, 14).toString();
        String jumlahAirKetuban = tbObat.getValueAt(row, 15).toString();
        String jumlahJanin = tbObat.getValueAt(row, 16).toString();
        String lokasiJanin = tbObat.getValueAt(row, 17).toString();
        String statusJanin = tbObat.getValueAt(row, 18).toString();

        java.util.function.Consumer<IcdCode> addCode = (icd) -> {
            if (!codes.contains(icd)) {
                codes.add(icd);
            }
        };

        if (!kesimpulan.isEmpty()) {
            if (kesimpulan.matches(".*(skrining|screening).*")) addCode.accept(new IcdCode("Z36", "Antenatal screening"));
            if (kesimpulan.matches(".*(iugr|fgr|intrauterine growth restriction|pertumbuhan.*(terhambat|kurang)).*")) addCode.accept(new IcdCode("O36.5", "Maternal care for poor fetal growth (suspected IUGR)"));
            if (kesimpulan.matches(".*(oligo|oligohidramnion|afi\\s*(rendah|<\\s*\\d+(\\.\\d+)?)).*")) addCode.accept(new IcdCode("O41.0", "Oligohydramnios"));
            if (kesimpulan.matches(".*(poli|polihidramnion|afi\\s*(tinggi|>\\s*\\d+(\\.\\d+)?)).*")) addCode.accept(new IcdCode("O40", "Polyhydramnios"));
            if (kesimpulan.matches(".*(previa|low[- ]lying|menutup.*ostium).*")) {
                if (kesimpulan.contains("perdarah")) {
                    addCode.accept(new IcdCode("O44.1", "Placenta praevia with hemorrhage"));
                } else {
                    addCode.accept(new IcdCode("O44.0", "Placenta praevia without hemorrhage"));
                }
            }
            if (kesimpulan.matches(".*(perdarah(an)?( antepartum)?).*")) addCode.accept(new IcdCode("O46.9", "Antepartum hemorrhage, unspecified"));
            if (kesimpulan.matches(".*(solusio|abrupsi|abruptio).*")) addCode.accept(new IcdCode("O45.9", "Premature separation of placenta, unspecified"));
            if (kesimpulan.matches(".*(ektopik|ekstrauterin|extra[- ]uterine).*")) addCode.accept(new IcdCode("O00.9", "Ectopic pregnancy, unspecified"));
            if (kesimpulan.matches(".*(sungsang|melintang|bokong|oblique).*")) addCode.accept(new IcdCode("O32.9", "Maternal care for malpresentation of fetus, unspecified"));
            if (kesimpulan.matches(".*(gemeli|kembar|multip(ara|el)?).*")) addCode.accept(new IcdCode("O30.0", "Twin pregnancy"));
            if (kesimpulan.matches(".*(iufd|intrauterine fetal death|janin.*(meninggal|tidak hidup)).*")) addCode.accept(new IcdCode("O36.4", "Maternal care for intrauterine death"));
            if (kesimpulan.matches(".*(anomali|kelainan kongenital|defek|defect).*")) addCode.accept(new IcdCode("O35.9", "Maternal care for (suspected) fetal abnormality, unspecified"));
            if (kesimpulan.matches(".*(kontrol rutin|anc|antenatal|kunjungan rutin|trimester\\s*[1-3]).*")) addCode.accept(new IcdCode("Z34.9", "Supervision of normal pregnancy, unspecified"));
        }
        
        if (jumlahAirKetuban.equals("Berkurang")) addCode.accept(new IcdCode("O41.0", "Oligohydramnios"));
        if (jumlahAirKetuban.equals("Lebih")) addCode.accept(new IcdCode("O40", "Polyhydramnios"));
        if (!presentasi.isEmpty() && !presentasi.equals("Kepala") && !presentasi.equals("-")) addCode.accept(new IcdCode("O32.9", "Maternal care for malpresentation of fetus, unspecified"));
        if (jumlahJanin.equals("Gemeli")) addCode.accept(new IcdCode("O30.0", "Twin pregnancy"));
        if (lokasiJanin.equals("Ekstrauterine")) addCode.accept(new IcdCode("O00.9", "Ectopic pregnancy, unspecified"));
        if (statusJanin.equals("Tidak hidup")) addCode.accept(new IcdCode("O36.4", "Maternal care for intrauterine death"));
        
        if (codes.isEmpty() && (!diagnosaKlinis.isEmpty() || !kesimpulan.isEmpty())) {
             addCode.accept(new IcdCode("Z34.9", "Supervision of normal pregnancy, unspecified"));
        }

        if (!codes.isEmpty()) {
            StringBuilder jsonArray = new StringBuilder();
            jsonArray.append("[");
            for (int i = 0; i < codes.size(); i++) {
                IcdCode c = codes.get(i);
                jsonArray.append("{")
                         .append("\"coding\": [{")
                         .append("\"system\": \"http://hl7.org/fhir/sid/icd-10\",")
                         .append("\"code\": \"").append(c.code).append("\",")
                         .append("\"display\": \"").append(c.display).append("\"")
                         .append("}],")
                         .append("\"text\": \"").append(c.display).append("\"")
                         .append("}");
                if (i < codes.size() - 1) {
                    jsonArray.append(",");
                }
            }
            jsonArray.append("]");
            return jsonArray.toString();
        } else {
            String text = !diagnosaKlinis.isEmpty() ? diagnosaKlinis : (!kesimpulan.isEmpty() ? kesimpulan : "Kontrol antenatal dengan USG");
            return "[{\"text\": \"" + text.replace("\"", "\\\"") + "\"}]";
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppPilihSemua = new javax.swing.JMenuItem();
        ppBersihkan = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnAll = new widget.Button();
        BtnKirim = new widget.Button();
        BtnUpdate = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel16 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppPilihSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppPilihSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPilihSemua.setForeground(new java.awt.Color(50, 50, 50));
        ppPilihSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPilihSemua.setText("Pilih Semua");
        ppPilihSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPilihSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPilihSemua.setName("ppPilihSemua"); // NOI18N
        ppPilihSemua.setPreferredSize(new java.awt.Dimension(150, 26));
        ppPilihSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPilihSemuaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPilihSemua);

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Hilangkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(150, 26));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBersihkan);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengiriman Data Service Request USG Obstetri Satu Sehat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(53, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass8.add(LCount);

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

        BtnKirim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/34.png"))); // NOI18N
        BtnKirim.setMnemonic('K');
        BtnKirim.setText("Kirim");
        BtnKirim.setToolTipText("Alt+K");
        BtnKirim.setName("BtnKirim"); // NOI18N
        BtnKirim.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnKirim);

        BtnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        BtnUpdate.setMnemonic('U');
        BtnUpdate.setText("Update");
        BtnUpdate.setToolTipText("Alt+U");
        BtnUpdate.setName("BtnUpdate"); // NOI18N
        BtnUpdate.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUpdateActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnUpdate);

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
        panelGlass8.add(BtnPrint);

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

        jLabel15.setText("Tgl.Registrasi :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass9.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-10-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d.");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass9.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-10-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel16.setText("Key Word :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel16);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(210, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('6');
        BtnCari.setToolTipText("Alt+6");
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

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>                        

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        dispose();
    }                                         

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {                                     
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,BtnKeluar);}
    }                                    

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(                             
                "<tr class='isi'>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.KTP Pasien</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl. USG</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.KTP Dokter</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ID Encounter</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Poli</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesimpulan</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ID Service Request</b></td>"+
                "</tr>"
            );
            for (i = 0; i < tabMode.getRowCount(); i++) {
                htmlContent.append(
                    "<tr class='isi'>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,4).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,5).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,7).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,8).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,10).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,11).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
                    "</tr>");
            }
            LoadHTML.setText(
                "<html>"+
                  "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                   htmlContent.toString()+
                  "</table>"+
                "</html>"
            );
            htmlContent=null;

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

            File f = new File("DataSatuSehatServiceRequestUSG.html");            
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                            "<tr class='isi2'>"+
                                "<td valign='top' align='center'>"+
                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                    "<font size='2' face='Tahoma'>DATA PENGIRIMAN SATU SEHAT SERVICE REQUEST USG OBSTETRI<br><br></font>"+        
                                "</td>"+
                           "</tr>"+
                        "</table>")
            );
            bw.close();                         
            Desktop.getDesktop().browse(f.toURI());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());       
    }                                        

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {                                 
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbObat.requestFocus();
        }
    }                                

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {                                        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
        this.setCursor(Cursor.getDefaultCursor());
    }                                       

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {                                   
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt,TCari,BtnPrint);
        }
    }                                  

    private void BtnKirimActionPerformed(java.awt.event.ActionEvent evt) {                                         
        for(i=0;i<tbObat.getRowCount();i++){
            if(tbObat.getValueAt(i,0).toString().equals("true") && 
               !tbObat.getValueAt(i,4).toString().equals("") && // KTP Pasien
               !tbObat.getValueAt(i,7).toString().equals("") && // KTP Dokter
               tbObat.getValueAt(i,12).toString().equals("")){  // ID Service Request
                try {
                    String no_rawat = tbObat.getValueAt(i, 1).toString();
                    String ktpPasien = tbObat.getValueAt(i, 4).toString();
                    String ktpDokter = tbObat.getValueAt(i, 7).toString();
                    String idEncounter = tbObat.getValueAt(i, 8).toString();
                    
                    idpasien = cekViaSatuSehat.tampilIDPasien(ktpPasien);
                    iddokter = cekViaSatuSehat.tampilIDParktisi(ktpDokter);
                    
                    if (idpasien.isEmpty() || iddokter.isEmpty() || idEncounter.isEmpty()) {
                        System.out.println("Data NIK/Encounter tidak lengkap untuk No. Rawat: " + no_rawat);
                        continue;
                    }
                    
                    String accessionNumber = no_rawat.replaceAll("/", "");
                    String serviceRequestIdentifierSystem = "http://sys-ids.kemkes.go.id/servicerequest/" + koneksiDB.IDSATUSEHAT();
                    String serviceRequestIdentifierValue = "SR-USG-" + accessionNumber;
                    String accessionNumberSystem = "http://sys-ids.kemkes.go.id/acsn/" + koneksiDB.IDSATUSEHAT();
                    
                    // <<< [START FIX] Konversi Tanggal dengan Zona Waktu >>>
                    String tglUSG = tbObat.getValueAt(i, 5).toString();
                    LocalDateTime localDateTime = LocalDateTime.parse(tglUSG, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
                    ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Jakarta")); // Asumsikan WIB
                    String occurrenceDateTime = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
                    // <<< [END FIX] >>>

                    String reasonCodeJson = buildReasonCodeJson(i);
                    
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        
                        json = "{" +
                                "\"resourceType\": \"ServiceRequest\"," +
                                "\"identifier\": [" +
                                    "{" +
                                        "\"system\": \"" + serviceRequestIdentifierSystem + "\"," +
                                        "\"value\": \"" + serviceRequestIdentifierValue + "\"" +
                                    "}," +
                                    "{" +
                                        "\"use\": \"official\"," +
                                        "\"type\": {" +
                                            "\"coding\": [{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/v2-0203\"," +
                                                "\"code\": \"ACSN\"," +
                                                "\"display\": \"Accession ID\"" +
                                            "}]" +
                                        "}," +
                                        "\"system\": \"" + accessionNumberSystem + "\"," +
                                        "\"value\": \"" + accessionNumber + "\"" +
                                    "}" +
                                "]," +
                                "\"status\": \"active\"," +
                                "\"intent\": \"original-order\"," +
                                "\"priority\": \"routine\"," +
                                "\"category\": [{" +
                                    "\"coding\": [{" +
                                        "\"system\": \"http://snomed.info/sct\"," +
                                        "\"code\": \"363679005\"," +
                                        "\"display\": \"Imaging\"" +
                                    "}]," +
                                    "\"text\": \"Pemeriksaan pencitraan (Imaging)\"" +
                                "}]," +
                                "\"code\": {" +
                                    "\"coding\": [{" +
                                        "\"system\": \"http://snomed.info/sct\"," +
                                        "\"code\": \"168731009\"," +
                                        "\"display\": \"Obstetric ultrasound scan (procedure)\"" +
                                    "}]," +
                                    "\"text\": \"USG Obstetri\"" +
                                "}," +
                                "\"subject\": {" +
                                    "\"reference\": \"Patient/" + idpasien + "\"," +
                                    "\"display\": \"" + tbObat.getValueAt(i, 3).toString() + "\"" +
                                "}," +
                                "\"encounter\": {" +
                                    "\"reference\": \"Encounter/" + idEncounter + "\"," +
                                    "\"display\": \"Kunjungan obstetri\"" +
                                "}," +
                                "\"occurrenceDateTime\": \"" + occurrenceDateTime + "\"," +
                                "\"authoredOn\": \"" + occurrenceDateTime + "\"," +
                                "\"requester\": {" +
                                    "\"reference\": \"Practitioner/" + iddokter + "\"," +
                                    "\"display\": \"" + tbObat.getValueAt(i, 6).toString() + "\"" +
                                "}," +
                                "\"performer\": [{" +
                                    "\"reference\": \"Practitioner/" + iddokter + "\"," +
                                    "\"display\": \"" + tbObat.getValueAt(i, 6).toString() + "\"" +
                                "}]," +
                                "\"reasonCode\": " + reasonCodeJson + "," +
                                "\"locationReference\": [{" +
                                    "\"reference\": \"Location/" + tbObat.getValueAt(i, 9).toString() + "\"," +
                                    "\"display\": \"" + tbObat.getValueAt(i, 10).toString() + "\"" +
                                "}]" +
                            "}";
                        
                        System.out.println("URL : "+link+"/ServiceRequest");
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/ServiceRequest", HttpMethod.POST, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        
                        root = mapper.readTree(json);
                        response = root.path("id");
                        
                        if(!response.asText().equals("")){
                            Sequel.mengedit("satu_sehat_servicerequest_usg", "no_rawat=?", "id_servicerequest=?, accession_number=?, response=?, status='success'", 4, new String[]{
                                response.asText(), accessionNumber, json, no_rawat
                            });
                            
                            tbObat.setValueAt(response.asText(), i, 12);
                            tbObat.setValueAt(false, i, 0);
                        }
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        Sequel.menyimpan("satu_sehat_servicerequest_usg", "?, '', ?, 'failed', ?", "Log Gagal", 4, new String[]{
                            no_rawat, accessionNumber, e.getMessage()
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
            }
        }
    }                                        

    private void ppPilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {                                             
        for(i=0;i<tbObat.getRowCount();i++){
            tbObat.setValueAt(true,i,0);
        }
    }                                            

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {                                            
        for(i=0;i<tbObat.getRowCount();i++){
            tbObat.setValueAt(false,i,0);
        }
    }                                           

    private void BtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {                                          
        for(i=0;i<tbObat.getRowCount();i++){
            if(tbObat.getValueAt(i,0).toString().equals("true") && 
               !tbObat.getValueAt(i,12).toString().equals("")){ 
                try {
                    String no_rawat = tbObat.getValueAt(i, 1).toString();
                    String idServiceRequest = tbObat.getValueAt(i, 12).toString();
                    String ktpPasien = tbObat.getValueAt(i, 4).toString();
                    String ktpDokter = tbObat.getValueAt(i, 7).toString();
                    String idEncounter = tbObat.getValueAt(i, 8).toString();
                    
                    idpasien = cekViaSatuSehat.tampilIDPasien(ktpPasien);
                    iddokter = cekViaSatuSehat.tampilIDParktisi(ktpDokter);
                    
                    if (idpasien.isEmpty() || iddokter.isEmpty() || idEncounter.isEmpty()) {
                        System.out.println("Data NIK/Encounter tidak lengkap untuk No. Rawat: " + no_rawat);
                        continue;
                    }
                    
                    String accessionNumber = no_rawat.replaceAll("/", "");
                    String serviceRequestIdentifierSystem = "http://sys-ids.kemkes.go.id/servicerequest/" + koneksiDB.IDSATUSEHAT();
                    String serviceRequestIdentifierValue = "SR-USG-" + accessionNumber;
                    String accessionNumberSystem = "http://sys-ids.kemkes.go.id/acsn/" + koneksiDB.IDSATUSEHAT();
                    
                    // <<< [START FIX] Konversi Tanggal dengan Zona Waktu >>>
                    String tglUSG = tbObat.getValueAt(i, 5).toString();
                    LocalDateTime localDateTime = LocalDateTime.parse(tglUSG, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
                    ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Jakarta")); // Asumsikan WIB
                    String occurrenceDateTime = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
                    // <<< [END FIX] >>>

                    String reasonCodeJson = buildReasonCodeJson(i);
                    
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        
                        json = "{" +
                                "\"resourceType\": \"ServiceRequest\"," +
                                "\"id\": \"" + idServiceRequest + "\"," +
                                "\"identifier\": [" +
                                    "{" +
                                        "\"system\": \"" + serviceRequestIdentifierSystem + "\"," +
                                        "\"value\": \"" + serviceRequestIdentifierValue + "\"" +
                                    "}," +
                                    "{" +
                                        "\"use\": \"official\"," +
                                        "\"type\": {" +
                                            "\"coding\": [{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/v2-0203\"," +
                                                "\"code\": \"ACSN\"," +
                                                "\"display\": \"Accession ID\"" +
                                            "}]" +
                                        "}," +
                                        "\"system\": \"" + accessionNumberSystem + "\"," +
                                        "\"value\": \"" + accessionNumber + "\"" +
                                    "}" +
                                "]," +
                                "\"status\": \"active\"," +
                                "\"intent\": \"original-order\"," +
                                "\"priority\": \"routine\"," +
                                "\"category\": [{" +
                                    "\"coding\": [{" +
                                        "\"system\": \"http://snomed.info/sct\"," +
                                        "\"code\": \"363679005\"," +
                                        "\"display\": \"Imaging\"" +
                                    "}]," +
                                    "\"text\": \"Pemeriksaan pencitraan (Imaging)\"" +
                                "}]," +
                                "\"code\": {" +
                                    "\"coding\": [{" +
                                        "\"system\": \"http://snomed.info/sct\"," +
                                        "\"code\": \"168731009\"," +
                                        "\"display\": \"Obstetric ultrasound scan (procedure)\"" +
                                    "}]," +
                                    "\"text\": \"USG Obstetri\"" +
                                "}," +
                                "\"subject\": {" +
                                    "\"reference\": \"Patient/" + idpasien + "\"," +
                                    "\"display\": \"" + tbObat.getValueAt(i, 3).toString() + "\"" +
                                "}," +
                                "\"encounter\": {" +
                                    "\"reference\": \"Encounter/" + idEncounter + "\"," +
                                    "\"display\": \"Kunjungan obstetri\"" +
                                "}," +
                                "\"occurrenceDateTime\": \"" + occurrenceDateTime + "\"," +
                                "\"authoredOn\": \"" + occurrenceDateTime + "\"," +
                                "\"requester\": {" +
                                    "\"reference\": \"Practitioner/" + iddokter + "\"," +
                                    "\"display\": \"" + tbObat.getValueAt(i, 6).toString() + "\"" +
                                "}," +
                                "\"performer\": [{" +
                                    "\"reference\": \"Practitioner/" + iddokter + "\"," +
                                    "\"display\": \"" + tbObat.getValueAt(i, 6).toString() + "\"" +
                                "}]," +
                                "\"reasonCode\": " + reasonCodeJson + "," +
                                "\"locationReference\": [{" +
                                    "\"reference\": \"Location/" + tbObat.getValueAt(i, 9).toString() + "\"," +
                                    "\"display\": \"" + tbObat.getValueAt(i, 10).toString() + "\"" +
                                "}]" +
                            "}";
                        
                        System.out.println("URL : "+link+"/ServiceRequest/"+idServiceRequest);
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/ServiceRequest/"+idServiceRequest, HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        
                         Sequel.mengedit("satu_sehat_servicerequest_usg", "no_rawat=?", "response=?, status='success'", 2, new String[]{
                            json, no_rawat
                        });
                        
                        tbObat.setValueAt(false,i,0);
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        Sequel.mengedit("satu_sehat_servicerequest_usg", "no_rawat=?", "response=?, status='failed'", 2, new String[]{
                            e.getMessage(), no_rawat
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
            }
        }
    }                                         

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {                                       
        TCari.setText("");
        tampil();
    }                                      

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {                                  
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }                                 

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SatuSehatKirimServiceRequestUSGObs dialog = new SatuSehatKirimServiceRequestUSGObs(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnKirim;
    private widget.Button BtnPrint;
    private widget.Button BtnUpdate;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppPilihSemua;
    private widget.Table tbObat;
    // End of variables declaration                   
    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                   "SELECT rp.no_rawat, rp.no_rkm_medis, p.nm_pasien, p.no_ktp AS ktp_pasien, " +
                   "hpu.tanggal AS tgl_usg, d.nm_dokter, peg.no_ktp AS ktp_dokter, " +
                   "IFNULL(enc.id_encounter, '') AS id_encounter, loc.id_lokasi_satusehat, pol.nm_poli, " +
                   "hpu.kesimpulan, IFNULL(usg.id_servicerequest, '') as id_servicerequest, " +
                   "hpu.diagnosa_klinis, hpu.presentasi, hpu.jumlah_air_ketuban, hpu.jumlahjanin, " +
                   "hpu.lokasi, hpu.janin " +
                   "FROM hasil_pemeriksaan_usg hpu " +
                   "INNER JOIN reg_periksa rp ON hpu.no_rawat = rp.no_rawat " +
                   "INNER JOIN pasien p ON rp.no_rkm_medis = p.no_rkm_medis " +
                   "INNER JOIN dokter d ON hpu.kd_dokter = d.kd_dokter " +
                   "INNER JOIN pegawai peg ON d.kd_dokter = peg.nik " +
                   "INNER JOIN poliklinik pol ON rp.kd_poli = pol.kd_poli " +
                   "INNER JOIN satu_sehat_mapping_lokasi_ralan loc ON pol.kd_poli = loc.kd_poli " +
                   "LEFT JOIN satu_sehat_encounter enc ON rp.no_rawat = enc.no_rawat " +
                   "LEFT JOIN satu_sehat_servicerequest_usg usg ON rp.no_rawat = usg.no_rawat " +
                   "WHERE rp.tgl_registrasi BETWEEN ? AND ? " +
                   (TCari.getText().equals("") ? "" : "AND (rp.no_rawat LIKE ? OR rp.no_rkm_medis LIKE ? OR " +
                   "p.nm_pasien LIKE ? OR p.no_ktp LIKE ? OR d.nm_dokter LIKE ? OR hpu.kesimpulan LIKE ?)") +
                   "ORDER BY hpu.tanggal DESC");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,
                        rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),
                        rs.getString("ktp_pasien"),
                        rs.getString("tgl_usg"),
                        rs.getString("nm_dokter"),
                        rs.getString("ktp_dokter"),
                        rs.getString("id_encounter"),
                        rs.getString("id_lokasi_satusehat"),
                        rs.getString("nm_poli"),
                        rs.getString("kesimpulan"),
                        rs.getString("id_servicerequest"),
                        rs.getString("diagnosa_klinis"),
                        rs.getString("presentasi"),
                        rs.getString("jumlah_air_ketuban"),
                        rs.getString("jumlahjanin"),
                        rs.getString("lokasi"),
                        rs.getString("janin")
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

    public void isCek(){
        BtnKirim.setEnabled(akses.getsatu_sehat_kirim_servicerequest_radiologi());
        BtnUpdate.setEnabled(akses.getsatu_sehat_kirim_servicerequest_radiologi());
        BtnPrint.setEnabled(akses.getsatu_sehat_kirim_servicerequest_radiologi());
    }
    
    public JTable getTable(){
        return tbObat;
    }
}