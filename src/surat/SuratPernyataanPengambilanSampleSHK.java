package surat;

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
import java.sql.SQLException;
// import java.sql.Time; // Tidak terpakai langsung, LocalTime lebih modern
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;
import javax.swing.JFileChooser;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class SuratPernyataanPengambilanSampleSHK extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, psPasienBayi, psFoto, psBukti;
    private ResultSet rs, rsPasienBayi, rsFoto, rsBukti;
    private int i = 0;
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private String finger = "";
    private String TglLahirBayiDB = ""; // Simpan tanggal lahir dari DB (format YYYY-MM-DD)
    private String JamLahirBayiDB = ""; // Simpan jam lahir dari DB (format HH:MM:SS)
    private String lokasifile = "";

    private final String TABEL_PERNYATAAN = "surat_pernyataan_shk";
    private final String TABEL_BUKTI = "surat_pernyataan_shk_bukti";
    private final String PRIMARY_KEY_COLUMN = "no_pernyataan";
    private final String PHOTO_COLUMN = "photo";
    private final String NO_PERNYATAAN_PREFIX = "SHK";
    private final String DIREKTORI_FOTO_SHK_WEB = "pernyataan_shk";
    private final String LOKASI_UPLOAD_LOKAL = "bukti_shk/";

    public SuratPernyataanPengambilanSampleSHK(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents(); // Memanggil komponen dari file .form
        this.setLocation(8, 1);
        setSize(1150, 700);

        // --- Definisi Kolom Tabel ---
        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Pernyataan", "No.Rawat", "No.R.M.", "Nama Bayi", "Tgl.Lahir", "Jam Lahir",
            "Nama Ibu", "Nama Ayah",
            "Tgl.Ambil Sample", "Jam Ambil Sample", "Tempat Ambil Sample", "Alamat Ortu", "No.Telp Ortu",
            "NIP", "Nama Petugas", "Tgl.Pernyataan", "Photo"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbPernyataanSHK.setModel(tabMode);

        // --- Pengaturan Tabel ---
        tbPernyataanSHK.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPernyataanSHK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // --- Set Lebar Kolom Tabel ---
        TableColumn column;
        int[] columnWidths = {
            120, 105, 70, 170, 75, 65,
            170, 170,
            100, 100, 150, 250, 100,
            90, 150, 85, 150
        };
        if (columnWidths.length != tbPernyataanSHK.getColumnCount()) {
             System.out.println("Peringatan: Jumlah lebar kolom tidak sesuai dengan jumlah kolom tabel!");
        } else {
             for (i = 0; i < columnWidths.length; i++) {
                  column = tbPernyataanSHK.getColumnModel().getColumn(i);
                  column.setPreferredWidth(columnWidths[i]);
             }
        }
        tbPernyataanSHK.setDefaultRenderer(Object.class, new WarnaTable());

        // --- Set Batas Input ---
        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        NIP.setDocument(new batasInput((byte) 20).getKata(NIP));
        NoPernyataanSHK.setDocument(new batasInput((byte) 25).getKata(NoPernyataanSHK));
        TCari.setDocument(new batasInput((int) 100).getKata(TCari));
        NamaIbu.setDocument(new batasInput((byte) 50).getKata(NamaIbu));
        NamaAyah.setDocument(new batasInput((byte) 50).getKata(NamaAyah));
        AlamatOrtu.setDocument(new batasInput((int) 200).getKata(AlamatOrtu));
        NoTelp.setDocument(new batasInput((byte) 30).getKata(NoTelp));
        TempatPengambilan.setDocument(new batasInput((byte) 50).getKata(TempatPengambilan));

        // --- Listener Cari Cepat ---
        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { if (TCari.getText().length() > 2) tampil(); }
                @Override public void removeUpdate(DocumentEvent e) { if (TCari.getText().length() > 2) tampil(); }
                @Override public void changedUpdate(DocumentEvent e) { if (TCari.getText().length() > 2) tampil(); }
            });
        }

        // --- Listener DlgCariPetugas ---
        petugas.addWindowListener(new WindowListener() {
            @Override public void windowOpened(WindowEvent e) {}
            @Override public void windowClosing(WindowEvent e) {}
            @Override public void windowClosed(WindowEvent e) {
                if (petugas.getTable().getSelectedRow() != -1) {
                    NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                    NIP.requestFocus();
                } else {
                    btnPetugas.requestFocus();
                }
            }
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });

        // --- Inisialisasi ComboBox Jam/Menit/Detik (Ambil Sample) ---
        inisialisasiJam(cmbJam);
        inisialisasiMenit(cmbMnt);
        inisialisasiDetik(cmbDtk);
        setWaktuToComboBox(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        // --- Inisialisasi ComboBox Jam/Menit/Detik (Jam Lahir) ---
        // Model sudah di-set di .form, tidak perlu setEnabled(false) lagi
        inisialisasiJam(cmbJamLahir);
        inisialisasiMenit(cmbMntLahir);
        inisialisasiDetik(cmbDtkLahir);
        setWaktuLahirToComboBox("00:00:00"); // Set awal ke 00:00:00

        // --- Pengaturan Awal Form ---
        ChkInput.setSelected(true);
        isForm();
        ChkAccor.setSelected(false);
        isPhoto();

        // --- Pengaturan HTML Editor (Untuk Panel Foto) ---
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML2.setEditable(false);
        LoadHTML2.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        Document doc = kit.createDefaultDocument();
        LoadHTML2.setDocument(doc);
        LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Silahkan pilih data pada tabel</font></center></body></html>");

        // --- Buat direktori upload lokal jika belum ada ---
        File uploadDir = new File(LOKASI_UPLOAD_LOKAL);
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if(created) {
                System.out.println("Direktori upload lokal dibuat: " + LOKASI_UPLOAD_LOKAL);
            } else {
                System.out.println("Gagal membuat direktori upload lokal: " + LOKASI_UPLOAD_LOKAL);
            }
        }
    }

    // --- Method inisialisasi ComboBox Waktu ---
    private void inisialisasiJam(JComboBox<String> cmb) { String[] jam = new String[24]; for (int i = 0; i <= 23; i++) { jam[i] = String.format("%02d", i); } cmb.setModel(new DefaultComboBoxModel<>(jam)); }
    private void inisialisasiMenit(JComboBox<String> cmb) { String[] menit = new String[60]; for (int i = 0; i <= 59; i++) { menit[i] = String.format("%02d", i); } cmb.setModel(new DefaultComboBoxModel<>(menit)); }
    private void inisialisasiDetik(JComboBox<String> cmb) { String[] detik = new String[60]; for (int i = 0; i <= 59; i++) { detik[i] = String.format("%02d", i); } cmb.setModel(new DefaultComboBoxModel<>(detik)); }

    // --- Helper untuk Jam Ambil Sample ---
    private String getWaktuFromComboBox() { if (cmbJam != null && cmbMnt != null && cmbDtk != null && cmbJam.getSelectedItem() != null && cmbMnt.getSelectedItem() != null && cmbDtk.getSelectedItem() != null) { return cmbJam.getSelectedItem().toString() + ":" + cmbMnt.getSelectedItem().toString() + ":" + cmbDtk.getSelectedItem().toString(); } else { System.out.println("Error: ComboBox waktu null atau item belum dipilih."); return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")); } }
    private void setWaktuToComboBox(String waktu) { if (cmbJam == null || cmbMnt == null || cmbDtk == null) { System.out.println("Error: ComboBox waktu null."); return; } if (waktu != null && waktu.matches("\\d{2}:\\d{2}:\\d{2}")) { String[] parts = waktu.split(":"); cmbJam.setSelectedItem(parts[0]); cmbMnt.setSelectedItem(parts[1]); cmbDtk.setSelectedItem(parts[2]); } else { LocalTime now = LocalTime.now(); cmbJam.setSelectedItem(String.format("%02d", now.getHour())); cmbMnt.setSelectedItem(String.format("%02d", now.getMinute())); cmbDtk.setSelectedItem(String.format("%02d", now.getSecond())); } }

    // --- Helper untuk Jam Lahir ---
    private String getWaktuLahirFromComboBox() {
        if (cmbJamLahir != null && cmbMntLahir != null && cmbDtkLahir != null &&
            cmbJamLahir.getSelectedItem() != null && cmbMntLahir.getSelectedItem() != null && cmbDtkLahir.getSelectedItem() != null) {
            return cmbJamLahir.getSelectedItem().toString() + ":" + cmbMntLahir.getSelectedItem().toString() + ":" + cmbDtkLahir.getSelectedItem().toString();
        } else {
            System.out.println("Error: ComboBox waktu lahir null atau item belum dipilih.");
            return "00:00:00"; // Default jika ada masalah
        }
    }
    private void setWaktuLahirToComboBox(String waktu) {
        if (cmbJamLahir == null || cmbMntLahir == null || cmbDtkLahir == null) {
            System.out.println("Error: ComboBox waktu lahir null.");
            return;
        }
        if (waktu != null && waktu.matches("\\d{2}:\\d{2}:\\d{2}")) {
            try {
                String[] parts = waktu.split(":");
                if (cmbJamLahir.getModel().getSize() == 0) inisialisasiJam(cmbJamLahir);
                if (cmbMntLahir.getModel().getSize() == 0) inisialisasiMenit(cmbMntLahir);
                if (cmbDtkLahir.getModel().getSize() == 0) inisialisasiDetik(cmbDtkLahir);

                cmbJamLahir.setSelectedItem(parts[0]);
                cmbMntLahir.setSelectedItem(parts[1]);
                cmbDtkLahir.setSelectedItem(parts[2]);
            } catch (Exception e) {
                 System.err.println("Error setting waktu lahir: " + e.getMessage());
                 cmbJamLahir.setSelectedItem("00");
                 cmbMntLahir.setSelectedItem("00");
                 cmbDtkLahir.setSelectedItem("00");
            }
        } else {
            cmbJamLahir.setSelectedItem("00");
            cmbMntLahir.setSelectedItem("00");
            cmbDtkLahir.setSelectedItem("00");
            if (waktu != null && !waktu.isEmpty()) {
                 System.out.println("Peringatan: Format waktu lahir tidak valid ('" + waktu + "'), direset ke 00:00:00.");
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPernyataanSHK = new widget.Table();
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
        jLabel10 = new widget.Label();
        NamaIbu = new widget.TextBox();
        jLabel11 = new widget.Label();
        AlamatOrtu = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel16 = new widget.Label();
        TglPernyataan = new widget.Tanggal();
        jLabel14 = new widget.Label();
        jLabel3 = new widget.Label();
        NoPernyataanSHK = new widget.TextBox();
        jLabel20 = new widget.Label();
        NoTelp = new widget.TextBox();
        jLabel5 = new widget.Label();
        NamaAyah = new widget.TextBox();
        jLabel22 = new widget.Label();
        cmbJamLahir = new widget.ComboBox();
        cmbMntLahir = new widget.ComboBox();
        cmbDtkLahir = new widget.ComboBox();
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        TglAmbilSample = new widget.Tanggal();
        jLabel15 = new widget.Label();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        jLabel23 = new widget.Label();
        TempatPengambilan = new widget.TextBox();
        TglLahirBayi = new widget.Tanggal();
        ChkInput = new widget.CekBox();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        BtnPrint1 = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Surat Pernyataan Pengambilan Sample SHK ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbPernyataanSHK.setToolTipText("Silahkan klik untuk memilih data yang mau diedit, dihapus, atau dicetak");
        tbPernyataanSHK.setName("tbPernyataanSHK"); // NOI18N
        tbPernyataanSHK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPernyataanSHKMouseClicked(evt);
            }
        });
        tbPernyataanSHK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPernyataanSHKKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPernyataanSHKKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbPernyataanSHK);

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
        BtnPrint.setText("Cetak Daftar");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(120, 30));
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
        jLabel19.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-05-2024" }));
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

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-05-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(210, 23));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(1150, 245));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(1150, 225));
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
        TNoRw.setBounds(104, 10, 130, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(330, 10, 270, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(237, 10, 90, 23);

        jLabel10.setText("Nama Ibu Bayi :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 70, 100, 23);

        NamaIbu.setName("NamaIbu"); // NOI18N
        NamaIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaIbuKeyPressed(evt);
            }
        });
        FormInput.add(NamaIbu);
        NamaIbu.setBounds(104, 70, 260, 23);

        jLabel11.setText("Alamat Ortu :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 130, 100, 23);

        AlamatOrtu.setName("AlamatOrtu"); // NOI18N
        AlamatOrtu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatOrtuKeyPressed(evt);
            }
        });
        FormInput.add(AlamatOrtu);
        AlamatOrtu.setBounds(104, 130, 400, 23);

        jLabel17.setText("Tgl.Lahir Bayi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(605, 10, 80, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 190, 100, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        FormInput.add(NIP);
        NIP.setBounds(104, 190, 120, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(226, 190, 245, 23);

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
        btnPetugas.setBounds(474, 190, 28, 23);

        jLabel16.setText("Tgl. Pernyataan :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 100, 23);

        TglPernyataan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-05-2024" }));
        TglPernyataan.setDisplayFormat("dd-MM-yyyy");
        TglPernyataan.setName("TglPernyataan"); // NOI18N
        TglPernyataan.setOpaque(false);
        TglPernyataan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglPernyataanItemStateChanged(evt);
            }
        });
        TglPernyataan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglPernyataanKeyPressed(evt);
            }
        });
        FormInput.add(TglPernyataan);
        TglPernyataan.setBounds(104, 40, 95, 23);

        jLabel14.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(10, 70, 0, 0);

        jLabel3.setText("No. Pernyataan :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(210, 40, 100, 23);

        NoPernyataanSHK.setHighlighter(null);
        NoPernyataanSHK.setName("NoPernyataanSHK"); // NOI18N
        NoPernyataanSHK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPernyataanSHKKeyPressed(evt);
            }
        });
        FormInput.add(NoPernyataanSHK);
        NoPernyataanSHK.setBounds(314, 40, 188, 23);

        jLabel20.setText("No. Telp / HP :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(510, 130, 90, 23);

        NoTelp.setName("NoTelp"); // NOI18N
        NoTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoTelpKeyPressed(evt);
            }
        });
        FormInput.add(NoTelp);
        NoTelp.setBounds(604, 130, 174, 23);

        jLabel5.setText("Nama Ayah Bayi :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(370, 70, 100, 23);

        NamaAyah.setName("NamaAyah"); // NOI18N
        NamaAyah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaAyahKeyPressed(evt);
            }
        });
        FormInput.add(NamaAyah);
        NamaAyah.setBounds(474, 70, 304, 23);

        jLabel22.setText("Jam Lahir Bayi :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(605, 40, 80, 23);

        cmbJamLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJamLahir.setName("cmbJamLahir"); // NOI18N
        cmbJamLahir.setPreferredSize(new java.awt.Dimension(55, 23));
        cmbJamLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamLahirKeyPressed(evt);
            }
        });
        FormInput.add(cmbJamLahir);
        cmbJamLahir.setBounds(688, 40, 55, 23);

        cmbMntLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMntLahir.setName("cmbMntLahir"); // NOI18N
        cmbMntLahir.setPreferredSize(new java.awt.Dimension(55, 23));
        cmbMntLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntLahirKeyPressed(evt);
            }
        });
        FormInput.add(cmbMntLahir);
        cmbMntLahir.setBounds(747, 40, 55, 23);

        cmbDtkLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtkLahir.setName("cmbDtkLahir"); // NOI18N
        cmbDtkLahir.setPreferredSize(new java.awt.Dimension(55, 23));
        cmbDtkLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkLahirKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtkLahir);
        cmbDtkLahir.setBounds(806, 40, 55, 23);

        jLabel12.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(10, 100, 0, 0);

        jLabel13.setText("Tgl. Ambil :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 100, 100, 23);

        TglAmbilSample.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-05-2024" }));
        TglAmbilSample.setDisplayFormat("dd-MM-yyyy");
        TglAmbilSample.setName("TglAmbilSample"); // NOI18N
        TglAmbilSample.setOpaque(false);
        TglAmbilSample.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAmbilSampleKeyPressed(evt);
            }
        });
        FormInput.add(TglAmbilSample);
        TglAmbilSample.setBounds(104, 100, 95, 23);

        jLabel15.setText("Jam Ambil :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(210, 100, 100, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setPreferredSize(new java.awt.Dimension(55, 23));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(314, 100, 55, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setPreferredSize(new java.awt.Dimension(55, 23));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(373, 100, 55, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setPreferredSize(new java.awt.Dimension(55, 23));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(432, 100, 55, 23);

        jLabel23.setText("Tempat Ambil :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(495, 100, 105, 23);

        TempatPengambilan.setName("TempatPengambilan"); // NOI18N
        TempatPengambilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TempatPengambilanKeyPressed(evt);
            }
        });
        FormInput.add(TempatPengambilan);
        TempatPengambilan.setBounds(604, 100, 174, 23);

        TglLahirBayi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-05-2024" }));
        TglLahirBayi.setDisplayFormat("dd-MM-yyyy");
        TglLahirBayi.setName("TglLahirBayi"); // NOI18N
        TglLahirBayi.setOpaque(false);
        TglLahirBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglLahirBayiKeyPressed(evt);
            }
        });
        FormInput.add(TglLahirBayi);
        TglLahirBayi.setBounds(688, 10, 90, 23);

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
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Bukti Pernyataan : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        FormPhoto.add(Scroll5, java.awt.BorderLayout.CENTER);

        FormPass3.setBackground(new java.awt.Color(255, 255, 255));
        FormPass3.setBorder(null);
        FormPass3.setName("FormPass3"); // NOI18N
        FormPass3.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbil.setMnemonic('U');
        btnAmbil.setText("Ambil/Upload");
        btnAmbil.setToolTipText("Alt+U (Upload bukti foto)");
        btnAmbil.setName("btnAmbil"); // NOI18N
        btnAmbil.setPreferredSize(new java.awt.Dimension(130, 30));
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
        BtnPrint1.setMnemonic('C');
        BtnPrint1.setText("Cetak Surat");
        BtnPrint1.setToolTipText("Alt+C (Cetak surat pernyataan tunggal)");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnPrint1);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        // Validasi Input
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) { Valid.textKosong(TNoRw, "Pasien/Bayi"); return; }
        if (NamaIbu.getText().trim().equals("")) { Valid.textKosong(NamaIbu, "Nama Ibu Bayi"); return; }
        if (NamaAyah.getText().trim().equals("")) { Valid.textKosong(NamaAyah, "Nama Ayah Bayi"); return; }
        if (AlamatOrtu.getText().trim().equals("")) { Valid.textKosong(AlamatOrtu, "Alamat Orang Tua"); return; }
        if (NoTelp.getText().trim().equals("")) { Valid.textKosong(NoTelp, "Nomor Telepon Orang Tua"); return; }
        if (TempatPengambilan.getText().trim().equals("")) { Valid.textKosong(TempatPengambilan, "Tempat Pengambilan Sampel"); return; }
        if (NamaPetugas.getText().trim().equals("")) { Valid.textKosong(btnPetugas, "Petugas"); return; }
        if (NoPernyataanSHK.getText().trim().equals("")) { Valid.textKosong(NoPernyataanSHK, "No.Pernyataan"); return; }

        // !!! PERHATIAN: Kode ini BELUM menyimpan TglLahirBayi dan Jam Lahir Bayi yang diedit user !!!
        // Simpan ke tabel utama TANPA kolom photo
        if (Sequel.menyimpantf(TABEL_PERNYATAAN, "?,?,?,?,?,?,?,?,?,?,?", "Data", 11, new String[]{ // 11 parameter
            NoPernyataanSHK.getText(), TNoRw.getText(), Valid.SetTgl(TglPernyataan.getSelectedItem() + ""),
            Valid.SetTgl(TglAmbilSample.getSelectedItem() + ""), getWaktuFromComboBox(), // Jam Ambil Sample
            TempatPengambilan.getText(), NIP.getText(), NamaIbu.getText(), NamaAyah.getText(),
            AlamatOrtu.getText(), NoTelp.getText()
        }) == true) {
            // Tambah ke tabel UI
            tabMode.addRow(new Object[]{
                NoPernyataanSHK.getText(), TNoRw.getText(), TNoRM.getText(), TPasien.getText(),
                Valid.SetTgl(TglLahirBayi.getSelectedItem() + ""), // Ambil dari widget.Tanggal
                getWaktuLahirFromComboBox(), // Ambil dari ComboBox Jam Lahir
                NamaIbu.getText(), NamaAyah.getText(),
                Valid.SetTgl(TglAmbilSample.getSelectedItem() + ""), getWaktuFromComboBox(), // Jam Ambil Sample
                TempatPengambilan.getText(), AlamatOrtu.getText(), NoTelp.getText(),
                NIP.getText(), NamaPetugas.getText(), Valid.SetTgl(TglPernyataan.getSelectedItem() + ""),
                "" // Tampilkan string kosong untuk photo di tabel UI
            });
            LCount.setText("" + tabMode.getRowCount());
            emptTeks();
        } else {
             JOptionPane.showMessageDialog(rootPane,"Gagal menyimpan data!");
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tbPernyataanSHK.getSelectedRow() > -1) {
            String noPernyataan = tbPernyataanSHK.getValueAt(tbPernyataanSHK.getSelectedRow(), 0).toString();
            String nipPembuat = tbPernyataanSHK.getValueAt(tbPernyataanSHK.getSelectedRow(), 13).toString();

            if (akses.getkode().equals("Admin Utama") || NIP.getText().equals(nipPembuat)) {
                int reply = JOptionPane.showConfirmDialog(rootPane, "Yakin data mau dihapus? Ini akan menghapus data utama dan bukti fotonya (jika ada).", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    String namaFileFoto = Sequel.cariIsi("SELECT " + PHOTO_COLUMN + " FROM " + TABEL_BUKTI + " WHERE " + PRIMARY_KEY_COLUMN + "=?", noPernyataan);
                    boolean fileDeleted = true;
                    if (namaFileFoto != null && !namaFileFoto.isEmpty() && !namaFileFoto.equals("-")) {
                        fileDeleted = hapusFileFisik(LOKASI_UPLOAD_LOKAL + namaFileFoto);
                        if(!fileDeleted) {
                            JOptionPane.showMessageDialog(rootPane,"Gagal menghapus file fisik bukti!\n"+ LOKASI_UPLOAD_LOKAL + namaFileFoto);
                        }
                    }

                    if (fileDeleted) {
                        hapus(noPernyataan);
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Gagal menghapus file fisik bukti. Penghapusan data dibatalkan.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh petugas yang membuat atau Admin Utama..!!");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data pada tabel terlebih dahulu..!!");
            tbPernyataanSHK.requestFocus();
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        // Validasi Input
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) { Valid.textKosong(TNoRw, "Pasien/Bayi"); return; }
        if (NamaIbu.getText().trim().equals("")) { Valid.textKosong(NamaIbu, "Nama Ibu Bayi"); return; }
        if (NamaAyah.getText().trim().equals("")) { Valid.textKosong(NamaAyah, "Nama Ayah Bayi"); return; }
        if (AlamatOrtu.getText().trim().equals("")) { Valid.textKosong(AlamatOrtu, "Alamat Orang Tua"); return; }
        if (NoTelp.getText().trim().equals("")) { Valid.textKosong(NoTelp, "Nomor Telepon Orang Tua"); return; }
        if (TempatPengambilan.getText().trim().equals("")) { Valid.textKosong(TempatPengambilan, "Tempat Pengambilan Sampel"); return; }
        if (NamaPetugas.getText().trim().equals("")) { Valid.textKosong(btnPetugas, "Petugas"); return; }
        if (NoPernyataanSHK.getText().trim().equals("")) { Valid.textKosong(NoPernyataanSHK, "No.Pernyataan"); return; }

        if (tbPernyataanSHK.getSelectedRow() > -1) {
            String noPernyataanDipilih = tbPernyataanSHK.getValueAt(tbPernyataanSHK.getSelectedRow(), 0).toString();
            String nipPembuat = tbPernyataanSHK.getValueAt(tbPernyataanSHK.getSelectedRow(), 13).toString();

            if (akses.getkode().equals("Admin Utama") || NIP.getText().equals(nipPembuat)) {
                // !!! PERHATIAN: Kode ini BELUM menyimpan TglLahirBayi dan Jam Lahir Bayi yang diedit user !!!
                ganti(noPernyataanDipilih);
            } else {
                JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh petugas yang membuat atau Admin Utama..!!");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data pada tabel terlebih dahulu..!!");
            tbPernyataanSHK.requestFocus();
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));

            // Query untuk report daftar
            String sql = "SELECT s.no_pernyataan, s.no_rawat, p.no_rkm_medis, p.nm_pasien, "
                    + "p.tgl_lahir, pb.jam_lahir, s.nama_ibu, s.nama_ayah, "
                    + "s.tgl_pengambilan_sample, s.jam_pengambilan_sample, s.tempat_pengambilan, "
                    + "s.alamat_orangtua, s.no_telp_orangtua, s.nip_petugas, pt.nama AS nama_petugas, s.tgl_pernyataan, "
                    + "sb." + PHOTO_COLUMN + " "
                    + "FROM " + TABEL_PERNYATAAN + " s "
                    + "INNER JOIN reg_periksa rp ON s.no_rawat = rp.no_rawat "
                    + "INNER JOIN pasien p ON rp.no_rkm_medis = p.no_rkm_medis "
                    + "INNER JOIN petugas pt ON s.nip_petugas = pt.nip "
                    + "LEFT JOIN pasien_bayi pb ON p.no_rkm_medis = pb.no_rkm_medis "
                    + "LEFT JOIN " + TABEL_BUKTI + " sb ON s." + PRIMARY_KEY_COLUMN + " = sb." + PRIMARY_KEY_COLUMN + " "
                    + "WHERE s.tgl_pernyataan BETWEEN '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' AND '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' ";

            if (!TCari.getText().trim().equals("")) {
                String keyword = "%" + TCari.getText().trim() + "%";
                sql += "AND (s.no_pernyataan LIKE '" + keyword + "' OR s.no_rawat LIKE '" + keyword + "' OR p.no_rkm_medis LIKE '" + keyword + "' "
                     + "OR p.nm_pasien LIKE '" + keyword + "' OR s.nama_ibu LIKE '" + keyword + "' OR s.nama_ayah LIKE '" + keyword + "' "
                     + "OR pt.nama LIKE '" + keyword + "') ";
            }
            sql += "ORDER BY s.tgl_pernyataan DESC, s.no_pernyataan DESC";

            Valid.MyReportqry("rptDataSuratPernyataanSHK.jasper", "report", "::[ Data Surat Pernyataan SHK ]::", sql, param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void tbPernyataanSHKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPernyataanSHKMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (Exception e) {
                System.out.println("Error klik tabel (getData): " + e);
            }
            try {
                 if (!ChkAccor.isSelected()) {
                      ChkAccor.setSelected(true);
                      isPhoto();
                 }
                 panggilPhoto();
            } catch (Exception e) {
                 System.out.println("Error klik tabel (panggilPhoto): " + e);
            }
            if (evt.getClickCount() == 2) {
                BtnEditActionPerformed(null);
            }
        }
    }//GEN-LAST:event_tbPernyataanSHKMouseClicked

    private void tbPernyataanSHKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPernyataanSHKKeyReleased
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                    panggilPhoto();
                } catch (Exception e) {
                    System.out.println("Error navigasi tabel: " + e);
                }
            }
        }
    }//GEN-LAST:event_tbPernyataanSHKKeyReleased

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
            TglPernyataan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            isRawat();
            TglPernyataan.requestFocus();
        } else {
            Valid.pindah(evt, TCari, TglPernyataan);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TglPernyataanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPernyataanKeyPressed
        Valid.pindah(evt, TNoRw, NoPernyataanSHK);
    }//GEN-LAST:event_TglPernyataanKeyPressed

    private void NoPernyataanSHKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPernyataanSHKKeyPressed
        Valid.pindah(evt, TglPernyataan, TglLahirBayi); // Pindah ke Tgl Lahir Bayi
    }//GEN-LAST:event_NoPernyataanSHKKeyPressed

    private void TglLahirBayiKeyPressed(java.awt.event.KeyEvent evt) { // Handler baru
        Valid.pindah(evt, NoPernyataanSHK, cmbJamLahir);
    }

    private void cmbJamLahirKeyPressed(java.awt.event.KeyEvent evt) { // Handler baru
         Valid.pindah(evt, TglLahirBayi, cmbMntLahir);
    }

    private void cmbMntLahirKeyPressed(java.awt.event.KeyEvent evt) { // Handler baru
         Valid.pindah(evt, cmbJamLahir, cmbDtkLahir);
    }

    private void cmbDtkLahirKeyPressed(java.awt.event.KeyEvent evt) { // Handler baru
         Valid.pindah(evt, cmbMntLahir, NamaIbu);
    }

    private void NamaIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaIbuKeyPressed
        Valid.pindah(evt, cmbDtkLahir, NamaAyah); // Pindah dari Jam Lahir
    }//GEN-LAST:event_NamaIbuKeyPressed

    private void NamaAyahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaAyahKeyPressed
        Valid.pindah(evt, NamaIbu, TglAmbilSample);
    }//GEN-LAST:event_NamaAyahKeyPressed

    private void TglAmbilSampleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAmbilSampleKeyPressed
        Valid.pindah(evt, NamaAyah, cmbJam);
    }//GEN-LAST:event_TglAmbilSampleKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt, TglAmbilSample, cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt, cmbJam, cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt, cmbMnt, TempatPengambilan);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void TempatPengambilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TempatPengambilanKeyPressed
        Valid.pindah(evt, cmbDtk, AlamatOrtu);
    }//GEN-LAST:event_TempatPengambilanKeyPressed

    private void AlamatOrtuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatOrtuKeyPressed
        Valid.pindah(evt, TempatPengambilan, NoTelp);
    }//GEN-LAST:event_AlamatOrtuKeyPressed

    private void NoTelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoTelpKeyPressed
        Valid.pindah(evt, AlamatOrtu, btnPetugas);
    }//GEN-LAST:event_NoTelpKeyPressed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt, NoTelp, BtnSimpan);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 40, internalFrame1.getHeight() - 40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void TglPernyataanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglPernyataanItemStateChanged
        if (BtnSimpan.isEnabled() && !BtnEdit.isEnabled()) {
            autoNomorPernyataan();
        }
    }//GEN-LAST:event_TglPernyataanItemStateChanged

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tbPernyataanSHKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPernyataanSHKKeyPressed
        if (tabMode.getRowCount() != 0) {
            int row = tbPernyataanSHK.getSelectedRow();
            if (row < 0) return;
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                     getData(); // Panggil getData sebelum fokus ke edit
                     BtnEdit.requestFocus();
                } catch (Exception e) {
                    System.out.println("Error spasi tabel: " + e);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
                BtnHapusActionPerformed(null);
            } else if (evt.getKeyCode() == KeyEvent.VK_P && evt.isAltDown()) {
                BtnCetakSuratActionPerformed(null);
            } else if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
                // Penanganan keyReleased sudah cukup
            }
        }
    }//GEN-LAST:event_tbPernyataanSHKKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        isPhoto();
        if (ChkAccor.isSelected() && tbPernyataanSHK.getSelectedRow() != -1) {
            panggilPhoto();
        } else if (!ChkAccor.isSelected()) {
            LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Panel Foto Ditutup</font></center></body></html>");
        } else {
            ChkAccor.setSelected(false);
            isPhoto();
            JOptionPane.showMessageDialog(null, "Silahkan pilih data pernyataan pada tabel terlebih dahulu..!!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        if (tbPernyataanSHK.getSelectedRow() < 0 || NoPernyataanSHK.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data pernyataan pada tabel atau pastikan No. Pernyataan terisi..!!");
            tbPernyataanSHK.requestFocus();
            return;
        }

        String noPernyataan = NoPernyataanSHK.getText();
        String namaFileServer = "";
        File fileDipilih = null;

        // 1. Pilih File
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pilih File Bukti Foto (.jpg, .png, .gif)");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Gambar (*.jpg, *.png, *.gif)", "jpg", "png", "gif"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            fileDipilih = fileChooser.getSelectedFile();
            String ekstensi = "";
            int i = fileDipilih.getName().lastIndexOf('.');
            if (i > 0) {
                ekstensi = fileDipilih.getName().substring(i + 1);
            }
            namaFileServer = noPernyataan + "_" + System.currentTimeMillis() + "." + ekstensi;
        } else {
            System.out.println("Pemilihan file dibatalkan.");
            return;
        }

        // 2. Upload/Copy File
        try {
            File destFile = new File(LOKASI_UPLOAD_LOKAL + namaFileServer);
            Files.copy(fileDipilih.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File berhasil dicopy ke: " + destFile.getAbsolutePath());

            // 3. Simpan referensi ke database
            if (Sequel.queryu2tf("REPLACE INTO " + TABEL_BUKTI + " (" + PRIMARY_KEY_COLUMN + ", " + PHOTO_COLUMN + ") VALUES (?, ?)",
                                2, new String[]{noPernyataan, namaFileServer})) {

                int row = tbPernyataanSHK.getSelectedRow();
                if (row > -1 && tbPernyataanSHK.getValueAt(row, 0).toString().equals(noPernyataan)) {
                    tabMode.setValueAt(namaFileServer, row, 16); // Index kolom photo di UI
                }
                lokasifile = namaFileServer;
                panggilPhoto();
                JOptionPane.showMessageDialog(rootPane, "Upload dan simpan data foto berhasil!");

            } else {
                JOptionPane.showMessageDialog(rootPane, "Gagal menyimpan referensi foto ke database!");
                destFile.delete(); // Hapus file yang sudah diupload jika DB gagal
            }

        } catch (Exception e) {
            System.out.println("Error saat upload/copy file atau simpan ke DB: " + e);
            JOptionPane.showMessageDialog(rootPane, "Gagal mengupload/menyimpan foto: " + e.getMessage());
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        if (tbPernyataanSHK.getSelectedRow() > -1) {
            panggilPhoto();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data pernyataan pada tabel terlebih dahulu..!!");
            tbPernyataanSHK.requestFocus();
        }
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        BtnCetakSuratActionPerformed(evt);
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, btnPetugas, BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampil();
            TCari.setText("");
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnAll, TCari);
        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed
    // --- Akhir Event handlers ---

    // --- Fungsi Cetak Surat Tunggal ---
    private void BtnCetakSuratActionPerformed(java.awt.event.ActionEvent evt) {
        if (tbPernyataanSHK.getSelectedRow() > -1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));

            String noPernyataanCetak = tbPernyataanSHK.getValueAt(tbPernyataanSHK.getSelectedRow(), 0).toString();
            String nipPetugasCetak = tbPernyataanSHK.getValueAt(tbPernyataanSHK.getSelectedRow(), 13).toString();
            String namaPetugasCetak = tbPernyataanSHK.getValueAt(tbPernyataanSHK.getSelectedRow(), 14).toString();
            String tglPernyataanCetak = tbPernyataanSHK.getValueAt(tbPernyataanSHK.getSelectedRow(), 15).toString();
            String namaFileFotoCetak = tbPernyataanSHK.getValueAt(tbPernyataanSHK.getSelectedRow(), 16) != null ? tbPernyataanSHK.getValueAt(tbPernyataanSHK.getSelectedRow(), 16).toString() : "";

            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", nipPetugasCetak);
            param.put("finger", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + namaPetugasCetak + "\nID " + (finger.equals("") ? nipPetugasCetak : finger) + "\n" + Valid.SetTgl3(tglPernyataanCetak));

            String urlFoto = "";
            if (!namaFileFotoCetak.isEmpty() && !namaFileFotoCetak.equals("-")) {
                urlFoto = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/" + DIREKTORI_FOTO_SHK_WEB + "/" + namaFileFotoCetak;
            }
            param.put("photo_url", urlFoto);

            // Query Cetak Surat
            String sql = "SELECT s.no_pernyataan, s.no_rawat, p.no_rkm_medis, p.nm_pasien AS nama_bayi, "
                    + "p.jk AS jk_bayi, p.tgl_lahir AS tgl_lahir_bayi, pb.jam_lahir AS jam_lahir_bayi, "
                    + "p.nm_ibu, pb.umur_ibu, s.nama_ayah, pb.umur_ayah, "
                    + "CONCAT(p.alamat, IFNULL(CONCAT(', ', kl.nm_kel), ''), IFNULL(CONCAT(', ', kc.nm_kec), ''), IFNULL(CONCAT(', ', kb.nm_kab), ''), IFNULL(CONCAT(', ', prop.nm_prop), '')) AS alamat_pasien, "
                    + "s.alamat_orangtua, s.no_telp_orangtua, "
                    + "s.tgl_pernyataan, s.tgl_pengambilan_sample, s.jam_pengambilan_sample, s.tempat_pengambilan, "
                    + "s.nip_petugas, pt.nama AS nama_petugas, "
                    + "sb." + PHOTO_COLUMN + " "
                    + "FROM " + TABEL_PERNYATAAN + " s "
                    + "INNER JOIN reg_periksa rp ON s.no_rawat = rp.no_rawat "
                    + "INNER JOIN pasien p ON rp.no_rkm_medis = p.no_rkm_medis "
                    + "INNER JOIN petugas pt ON s.nip_petugas = pt.nip "
                    + "LEFT JOIN pasien_bayi pb ON p.no_rkm_medis = pb.no_rkm_medis "
                    + "LEFT JOIN kelurahan kl ON p.kd_kel = kl.kd_kel "
                    + "LEFT JOIN kecamatan kc ON p.kd_kec = kc.kd_kec "
                    + "LEFT JOIN kabupaten kb ON p.kd_kab = kb.kd_kab "
                    + "LEFT JOIN propinsi prop ON p.kd_prop = prop.kd_prop "
                    + "LEFT JOIN " + TABEL_BUKTI + " sb ON s." + PRIMARY_KEY_COLUMN + " = sb." + PRIMARY_KEY_COLUMN + " "
                    + "WHERE s.no_pernyataan = '" + noPernyataanCetak + "'";

            Valid.MyReportqry("rptSuratPernyataanSHK.jasper", "report", "::[ Surat Pernyataan Pengambilan Sampel SHK ]::", sql, param);
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data pernyataan pada tabel terlebih dahulu..!!!!");
            tbPernyataanSHK.requestFocus();
        }
    }

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
    private widget.TextBox NoPernyataanSHK;
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
    private widget.Tanggal TglAmbilSample;
    private widget.Tanggal TglLahirBayi;
    private widget.Tanggal TglPernyataan;
    private widget.Button btnAmbil;
    private widget.Button btnPetugas;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbDtkLahir;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbJamLahir;
    private widget.ComboBox cmbMnt;
    private widget.ComboBox cmbMntLahir;
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
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbPernyataanSHK;
    // End of variables declaration//GEN-END:variables

    // --- Method tampil() ---
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            String sqlSelect = "SELECT s.no_pernyataan, s.no_rawat, p.no_rkm_medis, p.nm_pasien, "
                    + "p.tgl_lahir, pb.jam_lahir, s.nama_ibu, s.nama_ayah, "
                    + "s.tgl_pengambilan_sample, s.jam_pengambilan_sample, s.tempat_pengambilan, "
                    + "s.alamat_orangtua, s.no_telp_orangtua, s.nip_petugas, pt.nama AS nama_petugas, s.tgl_pernyataan, "
                    + "sb." + PHOTO_COLUMN + " "
                    + "FROM " + TABEL_PERNYATAAN + " s "
                    + "INNER JOIN reg_periksa rp ON s.no_rawat = rp.no_rawat "
                    + "INNER JOIN pasien p ON rp.no_rkm_medis = p.no_rkm_medis "
                    + "INNER JOIN petugas pt ON s.nip_petugas = pt.nip "
                    + "LEFT JOIN pasien_bayi pb ON p.no_rkm_medis = pb.no_rkm_medis "
                    + "LEFT JOIN " + TABEL_BUKTI + " sb ON s." + PRIMARY_KEY_COLUMN + " = sb." + PRIMARY_KEY_COLUMN + " ";

            String sqlWhere = "WHERE s.tgl_pernyataan BETWEEN ? AND ? ";
            String sqlOrder = "ORDER BY s.tgl_pernyataan DESC, s.no_pernyataan DESC";

            String keyword = TCari.getText().trim();
            if (!keyword.equals("")) {
                sqlWhere += "AND (s.no_pernyataan LIKE ? OR s.no_rawat LIKE ? OR p.no_rkm_medis LIKE ? OR p.nm_pasien LIKE ? OR s.nama_ibu LIKE ? OR s.nama_ayah LIKE ? OR pt.nama LIKE ?) ";
            }

            ps = koneksi.prepareStatement(sqlSelect + sqlWhere + sqlOrder);
            try {
                int parameterIndex = 1;
                ps.setString(parameterIndex++, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(parameterIndex++, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

                if (!keyword.equals("")) {
                    String keywordLike = "%" + keyword + "%";
                    ps.setString(parameterIndex++, keywordLike);
                    ps.setString(parameterIndex++, keywordLike);
                    ps.setString(parameterIndex++, keywordLike);
                    ps.setString(parameterIndex++, keywordLike);
                    ps.setString(parameterIndex++, keywordLike);
                    ps.setString(parameterIndex++, keywordLike);
                    ps.setString(parameterIndex++, keywordLike);
                }

                rs = ps.executeQuery();
                while (rs.next()) {
                    String namaFoto = rs.getString(PHOTO_COLUMN);
                    tabMode.addRow(new Object[]{
                        rs.getString("no_pernyataan"), rs.getString("no_rawat"), rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"), Valid.SetTgl(rs.getDate("tgl_lahir") != null ? rs.getDate("tgl_lahir").toString() : ""),
                        rs.getString("jam_lahir"),
                        rs.getString("nama_ibu"), rs.getString("nama_ayah"),
                        Valid.SetTgl(rs.getDate("tgl_pengambilan_sample") != null ? rs.getDate("tgl_pengambilan_sample").toString() : ""),
                        rs.getString("jam_pengambilan_sample"),
                        rs.getString("tempat_pengambilan"), rs.getString("alamat_orangtua"),
                        rs.getString("no_telp_orangtua"), rs.getString("nip_petugas"),
                        rs.getString("nama_petugas"), Valid.SetTgl(rs.getDate("tgl_pernyataan") != null ? rs.getDate("tgl_pernyataan").toString() : ""),
                        namaFoto != null ? namaFoto : ""
                    });
                }
            } catch (SQLException e) {
                System.out.println("Notif Tampil : " + e);
            } finally {
                if (rs != null) { try { rs.close(); } catch (SQLException ignore) {} }
                if (ps != null) { try { ps.close(); } catch (SQLException ignore) {} }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi Tampil Error : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    // --- Method emptTeks() --- (Dimodifikasi)
    public void emptTeks() {
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        TglLahirBayi.setDate(new Date()); // Reset TglLahirBayi ke tanggal hari ini
        setWaktuLahirToComboBox("00:00:00"); // Reset ComboBox Jam Lahir
        TglLahirBayiDB = "";
        JamLahirBayiDB = "";
        NamaIbu.setText("");
        NamaAyah.setText("");
        AlamatOrtu.setText("");
        NoTelp.setText("");
        TglAmbilSample.setDate(new Date());
        setWaktuToComboBox(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))); // Reset Jam Ambil Sample
        TempatPengambilan.setText(akses.getnamars());
        TglPernyataan.setDate(new Date());

        lokasifile = "";
        LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
        ChkAccor.setSelected(false);
        isPhoto();

        autoNomorPernyataan();
        TNoRw.requestFocus();

        BtnSimpan.setEnabled(true);
        BtnEdit.setEnabled(false);
        BtnHapus.setEnabled(false);

        isCek(); // Panggil isCek di akhir
    }

    // --- Method autoNomorPernyataan() ---
    private void autoNomorPernyataan() {
        Valid.autoNomer3("SELECT IFNULL(MAX(CONVERT(RIGHT(" + PRIMARY_KEY_COLUMN + ",4),SIGNED)),0) FROM " + TABEL_PERNYATAAN + " WHERE "
                + "DATE_FORMAT(tgl_pernyataan,'%Y-%m-%d')='" + Valid.SetTgl(TglPernyataan.getSelectedItem() + "") + "' ",
                NO_PERNYATAAN_PREFIX + TglPernyataan.getSelectedItem().toString().substring(6, 10) + TglPernyataan.getSelectedItem().toString().substring(3, 5) + TglPernyataan.getSelectedItem().toString().substring(0, 2), 4, NoPernyataanSHK);
    }

        // --- Method getData() --- (Dimodifikasi untuk memastikan data tabel tampil)
        private void getData() {
            if (tbPernyataanSHK.getSelectedRow() != -1) {
                int row = tbPernyataanSHK.getSelectedRow();
                NoPernyataanSHK.setText(tabMode.getValueAt(row, 0).toString());
                TNoRw.setText(tabMode.getValueAt(row, 1).toString());
                TNoRM.setText(tabMode.getValueAt(row, 2).toString());
                TPasien.setText(tabMode.getValueAt(row, 3).toString());
    
                // ---- Bagian Kunci untuk Tanggal Lahir ----
                String tglLahirDariTabel = tabMode.getValueAt(row, 4) != null ? tabMode.getValueAt(row, 4).toString() : "";
                if (!tglLahirDariTabel.isEmpty()) {
                    // Set komponen widget.Tanggal menggunakan format dari tabel (dd-MM-yyyy)
                    Valid.SetTgl(TglLahirBayi, tglLahirDariTabel);
                     System.out.println("getData: Tgl Lahir Tabel '" + tglLahirDariTabel + "' di set ke TglLahirBayi."); // Debug
                } else {
                    TglLahirBayi.setDate(new Date()); // Set default jika kosong di tabel
                    System.out.println("getData: Tgl Lahir kosong di tabel, set ke default."); // Debug
                }
                // ------------------------------------------
    
                // ---- Bagian Kunci untuk Jam Lahir ----
                String jamLahirDariTabel = tabMode.getValueAt(row, 5) != null ? tabMode.getValueAt(row, 5).toString() : "";
                setWaktuLahirToComboBox(jamLahirDariTabel); // Set ComboBox jam, mnt, dtk
                System.out.println("getData: Jam Lahir Tabel '" + jamLahirDariTabel + "' di set ke ComboBox."); // Debug
                // --------------------------------------
    
                // Isi field lain
                NamaIbu.setText(tabMode.getValueAt(row, 6).toString());
                NamaAyah.setText(tabMode.getValueAt(row, 7) != null ? tabMode.getValueAt(row, 7).toString() : "");
                Valid.SetTgl(TglAmbilSample, tabMode.getValueAt(row, 8).toString());
                setWaktuToComboBox(tabMode.getValueAt(row, 9).toString());
                TempatPengambilan.setText(tabMode.getValueAt(row, 10).toString());
                AlamatOrtu.setText(tabMode.getValueAt(row, 11).toString());
                NoTelp.setText(tabMode.getValueAt(row, 12).toString());
                NIP.setText(tabMode.getValueAt(row, 13).toString());
                NamaPetugas.setText(tabMode.getValueAt(row, 14).toString());
                Valid.SetTgl(TglPernyataan, tabMode.getValueAt(row, 15).toString());
                lokasifile = tabMode.getValueAt(row, 16) != null ? tabMode.getValueAt(row, 16).toString() : "";
    
                BtnSimpan.setEnabled(false);
                BtnEdit.setEnabled(true);
                BtnHapus.setEnabled(true);
            }
        }

        // --- Method isRawat() --- (Dimodifikasi untuk memastikan data DB tampil)
        private void isRawat() {
            // Reset fields dulu
            TNoRM.setText("");
            TPasien.setText("");
            TglLahirBayi.setDate(new Date()); // Default ke hari ini jika tidak ada data
            setWaktuLahirToComboBox("00:00:00"); // Default ke 00:00:00
            TglLahirBayiDB = "";
            JamLahirBayiDB = "";
            NamaIbu.setText("");
            NamaAyah.setText("");
            AlamatOrtu.setText("");
            NoTelp.setText("");
    
            if (TNoRw.getText() == null || TNoRw.getText().trim().isEmpty()) {
                // System.out.println("isRawat: No Rawat kosong, tidak bisa query.");
                return; // Jangan lakukan query jika No Rawat kosong
            }
    
            // Query untuk mengambil data pasien dan bayi
            String sql = "SELECT rp.no_rkm_medis, p.nm_pasien, p.jk, p.tgl_lahir, " // Ambil tgl_lahir dari pasien
                    + "pb.jam_lahir, p.nm_ibu, pb.nama_ayah, " // Ambil jam_lahir dari pasien_bayi
                    + "COALESCE(p.alamat, '') AS alamat_pasien_saja, "
                    + "CONCAT(p.alamat, IFNULL(CONCAT(', ', kl.nm_kel), ''), IFNULL(CONCAT(', ', kc.nm_kec), ''), IFNULL(CONCAT(', ', kb.nm_kab), ''), IFNULL(CONCAT(', ', prop.nm_prop), '')) AS alamat_lengkap, "
                    + "p.no_tlp "
                    + "FROM reg_periksa rp "
                    + "INNER JOIN pasien p ON rp.no_rkm_medis = p.no_rkm_medis "
                    + "LEFT JOIN pasien_bayi pb ON p.no_rkm_medis = pb.no_rkm_medis " // LEFT JOIN jika data bayi mungkin tidak ada
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
    
                        // ---- Bagian Kunci untuk Tanggal Lahir ----
                        TglLahirBayiDB = rsPasienBayi.getString("tgl_lahir"); // Ambil dari DB (format YYYY-MM-DD)
                        if (TglLahirBayiDB != null && !TglLahirBayiDB.isEmpty()) {
                            // Set komponen widget.Tanggal menggunakan format YYYY-MM-DD
                            Valid.SetTgl2(TglLahirBayi, TglLahirBayiDB);
                            System.out.println("isRawat: Tgl Lahir DB '" + TglLahirBayiDB + "' di set ke TglLahirBayi."); // Debug
                        } else {
                            TglLahirBayi.setDate(new Date()); // Set default jika null/kosong
                            System.out.println("isRawat: Tgl Lahir null/kosong dari DB, set ke default."); // Debug
                        }
                        // ------------------------------------------
    
                        // ---- Bagian Kunci untuk Jam Lahir ----
                        JamLahirBayiDB = rsPasienBayi.getString("jam_lahir"); // Ambil dari DB (format HH:MM:SS)
                        setWaktuLahirToComboBox(JamLahirBayiDB); // Set ComboBox jam, mnt, dtk
                        System.out.println("isRawat: Jam Lahir DB '" + JamLahirBayiDB + "' di set ke ComboBox."); // Debug
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
    
                    } else { // Jika data tidak ditemukan
                        JOptionPane.showMessageDialog(null, "Data Pasien/Bayi dengan No. Rawat '" + TNoRw.getText() + "' tidak ditemukan.");
                        // Reset fields (sudah dilakukan di awal method)
                        TNoRw.requestFocus();
                    }
                } catch (SQLException e) {
                    System.out.println("Notif SQL Exception isRawat : " + e);
                    // Reset fields jika error query
                    TglLahirBayi.setDate(new Date());
                    setWaktuLahirToComboBox("00:00:00");
                } catch (Exception e){
                    System.out.println("Notif General Exception isRawat : " + e);
                    // Reset fields jika error lain
                    TglLahirBayi.setDate(new Date());
                    setWaktuLahirToComboBox("00:00:00");
                } finally {
                    if (rsPasienBayi != null) { try { rsPasienBayi.close(); } catch (SQLException ignore) {} }
                    if (psPasienBayi != null) { try { psPasienBayi.close(); } catch (SQLException ignore) {} }
                }
            } catch (SQLException e) {
                System.out.println("Notif PrepareStatement isRawat : " + e);
                 // Reset fields jika error prepare statement
                 TglLahirBayi.setDate(new Date());
                 setWaktuLahirToComboBox("00:00:00");
            }
        }

    // --- Method setNoRm() ---
    public void setNoRm(String norwt, Date tglReg) {
        System.out.println("setNoRm dipanggil dengan NoRawat: " + norwt); // DEBUG
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        if (tglReg != null) {
           DTPCari1.setDate(tglReg);
           DTPCari2.setDate(tglReg);
        } else {
           DTPCari1.setDate(new Date());
           DTPCari2.setDate(new Date());
        }
        isRawat(); // Panggil isRawat() untuk mengisi data
        ChkInput.setSelected(true);
        isForm();
        TglPernyataan.requestFocus(); // Fokus ke field input pertama setelah No Rawat
    }

    // --- Method isForm() ---
    private void isForm() {
        if (ChkInput.isSelected()) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 245));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    // --- Method isCek() ---
    public void isCek() {
        boolean canAccess = akses.getsurat_persetujuan_umum(); // Ganti ke hak akses yang sesuai jika ada, jika tidak pakai persetujuan umum/admin
        if (!canAccess) {
            canAccess = akses.getsurat_persetujuan_umum();
            if (!canAccess) {
                 canAccess = akses.getadmin();
            }
        }


        BtnSimpan.setEnabled(canAccess);
        BtnHapus.setEnabled(canAccess);
        BtnEdit.setEnabled(canAccess);
        BtnPrint.setEnabled(canAccess); // Cetak Daftar
        BtnPrint1.setEnabled(canAccess); // Cetak Surat Tunggal
        btnAmbil.setEnabled(canAccess); // Upload Foto
        BtnRefreshPhoto1.setEnabled(canAccess); // Refresh Foto

        // Set Petugas Otomatis
        if (akses.getjml2() >= 1) {
            NIP.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(NIP.getText()));
            if (NamaPetugas.getText().trim().equals("")) {
                NIP.setText("");
                NamaPetugas.setText("");
                btnPetugas.setEnabled(true); // Aktifkan tombol jika user login bukan petugas
                // JOptionPane.showMessageDialog(null, "User login bukan Petugas Medis/Paramedis. Silahkan pilih petugas.");
                // btnPetugas.requestFocus(); // Fokuskan jika perlu
            } else {
                btnPetugas.setEnabled(!canAccess); // Nonaktifkan jika petugas sudah terisi & punya akses
            }
        } else {
            NIP.setText("");
            NamaPetugas.setText("");
            btnPetugas.setEnabled(true); // Aktifkan jika tidak ada info user login
            // btnPetugas.requestFocus(); // Fokuskan jika perlu
        }

        // Jika Simpan aktif, otomatis generate nomor jika mode tambah
        if (BtnSimpan.isEnabled() && !BtnEdit.isEnabled()) {
            autoNomorPernyataan();
        }
    }


    // --- Method ganti() ---
    private void ganti(String noPernyataanLama) {
        // !!! PERHATIAN: Kode ini BELUM menyimpan TglLahirBayi dan Jam Lahir Bayi yang diedit user !!!
        // Update DB
        if (Sequel.mengedittf(TABEL_PERNYATAAN, PRIMARY_KEY_COLUMN + "=?",
                PRIMARY_KEY_COLUMN + "=?, no_rawat=?, tgl_pernyataan=?, tgl_pengambilan_sample=?, jam_pengambilan_sample=?, tempat_pengambilan=?, nip_petugas=?, nama_ibu=?, nama_ayah=?, alamat_orangtua=?, no_telp_orangtua=?",
                12, new String[]{
                    NoPernyataanSHK.getText(), TNoRw.getText(), Valid.SetTgl(TglPernyataan.getSelectedItem() + ""),
                    Valid.SetTgl(TglAmbilSample.getSelectedItem() + ""), getWaktuFromComboBox(), // Jam Ambil Sample
                    TempatPengambilan.getText(), NIP.getText(), NamaIbu.getText(), NamaAyah.getText(),
                    AlamatOrtu.getText(), NoTelp.getText(),
                    noPernyataanLama
                }) == true) {
            // Update tabel UI
            int row = tbPernyataanSHK.getSelectedRow();
            tabMode.setValueAt(NoPernyataanSHK.getText(), row, 0);
            tabMode.setValueAt(TNoRw.getText(), row, 1);
            tabMode.setValueAt(TNoRM.getText(), row, 2);
            tabMode.setValueAt(TPasien.getText(), row, 3);
            tabMode.setValueAt(Valid.SetTgl(TglLahirBayi.getSelectedItem() + ""), row, 4); // Ambil dari widget.Tanggal
            tabMode.setValueAt(getWaktuLahirFromComboBox(), row, 5); // Update dari ComboBox Jam Lahir
            tabMode.setValueAt(NamaIbu.getText(), row, 6);
            tabMode.setValueAt(NamaAyah.getText(), row, 7);
            tabMode.setValueAt(Valid.SetTgl(TglAmbilSample.getSelectedItem() + ""), row, 8);
            tabMode.setValueAt(getWaktuFromComboBox(), row, 9); // Jam Ambil Sample
            tabMode.setValueAt(TempatPengambilan.getText(), row, 10);
            tabMode.setValueAt(AlamatOrtu.getText(), row, 11);
            tabMode.setValueAt(NoTelp.getText(), row, 12);
            tabMode.setValueAt(NIP.getText(), row, 13);
            tabMode.setValueAt(NamaPetugas.getText(), row, 14);
            tabMode.setValueAt(Valid.SetTgl(TglPernyataan.getSelectedItem() + ""), row, 15);
            // Kolom foto (index 16) tidak perlu diubah di sini, dihandle saat upload

            emptTeks();
            JOptionPane.showMessageDialog(rootPane, "Data berhasil diubah!");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Gagal mengubah data!");
        }
    }

    // --- Method hapus() ---
    private void hapus(String noPernyataan) {
        // Hapus juga dari tabel bukti jika ada
        Sequel.queryu2tf("DELETE FROM " + TABEL_BUKTI + " WHERE " + PRIMARY_KEY_COLUMN + "=?", 1, new String[]{noPernyataan});

        // Hapus dari tabel utama
        if (Sequel.queryu2tf("DELETE FROM " + TABEL_PERNYATAAN + " WHERE " + PRIMARY_KEY_COLUMN + "=?", 1, new String[]{noPernyataan}) == true) {
            tabMode.removeRow(tbPernyataanSHK.getSelectedRow());
            LCount.setText("" + tabMode.getRowCount());
            emptTeks();
            JOptionPane.showMessageDialog(rootPane, "Data dan bukti foto (jika ada) berhasil dihapus!");
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data dari database!");
        }
    }

    // --- Method isPhoto() ---
    private void isPhoto() {
        if (ChkAccor.isSelected()) {
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(430, getHeight()));
            FormPhoto.setVisible(true);
            ChkAccor.setVisible(true);
        } else {
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15, getHeight()));
            FormPhoto.setVisible(false);
            ChkAccor.setVisible(true);
        }
    }

    // --- Method panggilPhoto() ---
    private void panggilPhoto() {
        if (FormPhoto.isVisible()) {
            if (tbPernyataanSHK.getSelectedRow() > -1) {
                 if (lokasifile == null || lokasifile.trim().isEmpty() || lokasifile.equals("-")) {
                      LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Bukti Foto Belum Diupload</font></center></body></html>");
                 } else {
                      try {
                           // Coba load dari lokal dulu, fallback ke web
                           File fileLokal = new File(LOKASI_UPLOAD_LOKAL + lokasifile);
                           String urlGambar;
                           if (fileLokal.exists()) {
                               // Gunakan file: URI untuk file lokal
                               urlGambar = fileLokal.toURI().toString();
                               System.out.println("Memuat foto dari lokal: " + urlGambar);
                           } else {
                               // Fallback ke URL web
                               urlGambar = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/" + DIREKTORI_FOTO_SHK_WEB + "/" + lokasifile;
                               System.out.println("Memuat foto dari web: " + urlGambar);
                           }
                           LoadHTML2.setText("<html><body><center><img src='" + urlGambar + "' alt='photo' width='400' height='400'/></center></body></html>");
                      } catch (Exception e) {
                           System.out.println("Error membangun URL/URI foto: " + e);
                           LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#AA0000'>Gagal Memuat Foto</font></center></body></html>");
                      }
                 }
            } else {
                 LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Silahkan pilih data pada tabel</font></center></body></html>");
                 lokasifile = "";
            }
        }
    }
    // --- End Method Terkait Foto ---

    // --- Method hapusFileFisik() ---
    private boolean hapusFileFisik(String pathLengkapFile) {
        System.out.println("Mencoba menghapus file fisik: " + pathLengkapFile);
        try {
            File file = new File(pathLengkapFile);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("File fisik berhasil dihapus.");
                    return true;
                } else {
                    System.out.println("Gagal menghapus file fisik.");
                    return false;
                }
            } else {
                System.out.println("File fisik tidak ditemukan, dianggap berhasil dihapus dari sisi file.");
                return true; // Jika file sudah tidak ada, anggap sukses
            }
        } catch (SecurityException se) {
            System.out.println("Error keamanan saat menghapus file fisik: " + se.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Error lain saat menghapus file fisik: " + e.getMessage());
            return false;
        }
    }
}