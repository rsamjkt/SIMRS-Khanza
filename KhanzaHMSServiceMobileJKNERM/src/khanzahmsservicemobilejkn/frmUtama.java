/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanzahmsservicemobilejkn;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.ApiMobileJKN;
import fungsi.koneksiDB;
import fungsi.sekuel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.Timer;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 *
 * @author windiartonugroho
 */
public class frmUtama extends javax.swing.JFrame {
    private final Connection koneksi=koneksiDB.condb();
    private final sekuel Sequel=new sekuel();
    private String requestJson,URL="",utc="",link="",
              jam="",menit="",detik="",hari="",
              kodebpjs=Sequel.cariIsi("select password_asuransi.kd_pj from password_asuransi");
    private final ApiMobileJKN api=new ApiMobileJKN();
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private final ObjectMapper mapper= new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final SimpleDateFormat tanggalFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date date = new Date();  

    /**
     * Creates new form frmUtama
     */
    public frmUtama() {
        initComponents();
        try {
            link=koneksiDB.URLAPIMOBILEJKN();
        } catch (Exception e) {
            System.out.println("E : "+e);
            e.printStackTrace();
        }
        
        this.setSize(390,340);
        
        date = new Date();  
        Tanggal1.setText(tanggalFormat.format(date)); 
        Tanggal2.setText(tanggalFormat.format(date)); 
        jam();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TeksArea = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Tanggal1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Tanggal2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        BtnSapuManual = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Service Mobile JKN SAPU BERSIH By. Randy Mandala [Smart Timeline Builder]");

        TeksArea.setColumns(20);
        TeksArea.setRows(5);
        jScrollPane1.setViewportView(TeksArea);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Tanggal :");
        jLabel1.setPreferredSize(new java.awt.Dimension(70, 23));
        jPanel1.add(jLabel1);

        Tanggal1.setPreferredSize(new java.awt.Dimension(100, 23));
        jPanel1.add(Tanggal1);

        jLabel3.setText("s.d.");
        jLabel3.setPreferredSize(new java.awt.Dimension(28, 23));
        jPanel1.add(jLabel3);

        Tanggal2.setPreferredSize(new java.awt.Dimension(100, 23));
        jPanel1.add(Tanggal2);

        jLabel2.setPreferredSize(new java.awt.Dimension(30, 23));
        jPanel1.add(jLabel2);

        BtnSapuManual.setText("Sapu Manual"); // <-- APAKAH BARIS INI ADA?
BtnSapuManual.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        BtnSapuManualActionPerformed(evt);
    }
});
jPanel1.add(BtnSapuManual); // <-- DAN YANG PALING PENTING, APAKAH BARIS INI ADA?



        jButton1.setText("Keluar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void BtnSapuManualActionPerformed(java.awt.event.ActionEvent evt) {
        TeksArea.append("\n--- [MANUAL] Proses Sapu Bersih Dimulai Untuk Tanggal " + Tanggal1.getText() + " s.d. " + Tanggal2.getText() + " ---\n");
        prosesSapuBersihAkhirHari();
        TeksArea.append("--- [MANUAL] Proses Sapu Bersih Selesai ---\n");
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(frmUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmUtama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnSapuManual;
    private javax.swing.JTextField Tanggal1;
    private javax.swing.JTextField Tanggal2;
    private javax.swing.JTextArea TeksArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Date now = Calendar.getInstance().getTime();
                jam = new SimpleDateFormat("HH").format(now);
                menit = new SimpleDateFormat("mm").format(now);
                detik = new SimpleDateFormat("ss").format(now);
                
                if(jam.equals("01")&&menit.equals("01")&&detik.equals("01")){
                    TeksArea.setText("");
                    date = new Date();  
                    Tanggal1.setText(tanggalFormat.format(date)); 
                    Tanggal2.setText(tanggalFormat.format(date)); 
                }
                
                if(detik.equals("01") && (Integer.parseInt(menit) % 5 == 0)){
                    aturHari();
                    TeksArea.append("\n--- Proses Periodik Dimulai ("+dateFormat.format(new Date())+") ---\n");
                    prosesBatalAntrean();
                    prosesTimelinePasien();
                    TeksArea.append("--- Proses Periodik Selesai ---\n");
                }
                
                if(jam.equals("23") && menit.equals("00") && detik.equals("01")) {
                    aturHari();
                    TeksArea.append("\n--- [AKHIR HARI] Proses Sapu Bersih Dimulai ---\n");
                    prosesSapuBersihAkhirHari();
                    TeksArea.append("--- [AKHIR HARI] Proses Sapu Bersih Selesai ---\n");
                }
            }
        };
        new Timer(1000, taskPerformer).start();
    }
    
    private void aturHari() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1: hari="AKHAD"; break;
            case 2: hari="SENIN"; break;
            case 3: hari="SELASA"; break;
            case 4: hari="RABU"; break;
            case 5: hari="KAMIS"; break;
            case 6: hari="JUMAT"; break;
            case 7: hari="SABTU"; break;
            default: break;
        }
    }
    
    private void prosesTimelinePasien() {
        TeksArea.append("Fase 1 & 2: Memproses Pasien Aktif & Deteksi Macet...\n");
        String query = "SELECT rp.no_reg, rp.no_rawat, rp.tgl_registrasi, rp.jam_reg, rp.kd_dokter, d.nm_dokter, " +
                       "rp.kd_poli, p.nm_poli, rp.stts_daftar, rp.no_rkm_medis, rp.kd_pj, rp.stts, " +
                       "rmjb.nobooking, rmjb.status as status_jkn, rmjb.validasi as waktu_checkin_jkn " +
                       "FROM reg_periksa rp " +
                       "INNER JOIN dokter d ON rp.kd_dokter = d.kd_dokter " +
                       "INNER JOIN poliklinik p ON rp.kd_poli = p.kd_poli " +
                       "LEFT JOIN referensi_mobilejkn_bpjs rmjb ON rp.no_rawat = rmjb.no_rawat " +
                       "WHERE rp.tgl_registrasi BETWEEN ? AND ? AND rp.stts <> 'Batal'";
        
        try (PreparedStatement ps = koneksi.prepareStatement(query)) {
            ps.setString(1, Tanggal1.getText());
            ps.setString(2, Tanggal2.getText());
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    String noRawat = rs.getString("no_rawat");
                    String kodeBooking = rs.getString("nobooking") != null ? rs.getString("nobooking") : noRawat;

                    // Dapatkan riwayat task yang sudah terkirim
                    Map<Integer, Date> taskTerkirim = getTaskTerkirimDenganWaktu(noRawat);
                    int taskTerakhir = taskTerkirim.keySet().stream().max(Integer::compare).orElse(0);
                    
                    // Jika timeline sudah selesai, lewati pasien ini
                    if (taskTerakhir == 5 || taskTerakhir == 7 || taskTerakhir == 99) {
                        continue;
                    }

                    // Daftarkan pasien onsite jika ini adalah interaksi pertama (belum ada task terkirim)
                    if (rs.getString("nobooking") == null && taskTerakhir == 0) {
                        daftarkanPasienOnsite(rs);
                    }
                    
                    if (rs.getString("status_jkn") != null && !rs.getString("status_jkn").equals("Checkin")) {
                        continue;
                    }

                    // Bangun sisa timeline dari titik terakhir yang sukses
                    Map<Integer, Date> timelineIdeal = buildPerfectTimeline(rs, false, taskTerkirim);
                    kirimTaskDariTimeline(kodeBooking, noRawat, timelineIdeal);
                    
                    // Ambil ulang riwayat setelah kemungkinan ada pengiriman baru
                    Map<Integer, Date> taskTerkirimUpdate = getTaskTerkirimDenganWaktu(noRawat);
                    if (taskTerkirimUpdate.isEmpty()) continue;
                    
                    int taskTerakhirUpdate = taskTerkirimUpdate.keySet().stream().max(Integer::compare).get();
                    
                    if (taskTerakhirUpdate == 5 || taskTerakhirUpdate == 7 || taskTerakhirUpdate == 99) continue;
                    
                    Date waktuTaskTerakhir = taskTerkirimUpdate.get(taskTerakhirUpdate);
                    long selisihWaktu = new Date().getTime() - waktuTaskTerakhir.getTime();
                    long timeout = 90 * 60 * 1000;

                    if (selisihWaktu > timeout) {
                        TeksArea.append("!!! PASIEN MACET: " + noRawat + ". Memaksa penyelesaian timeline.\n");
                        Map<Integer, Date> timelineLengkap = buildPerfectTimeline(rs, true, taskTerkirimUpdate);
                        kirimTaskDariTimeline(kodeBooking, noRawat, timelineLengkap);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Notif Proses Timeline: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void prosesSapuBersihAkhirHari() {
    // Kueri dengan kolom yang sudah diperbaiki (rp.tgl_registrasi)
    String query = "SELECT rp.no_reg, rp.no_rawat, rp.tgl_registrasi, rp.jam_reg, rp.kd_dokter, d.nm_dokter, " + // <-- BARIS INI TELAH DIPERBAIKI
                   "rp.kd_poli, p.nm_poli, rp.stts_daftar, rp.no_rkm_medis, rp.kd_pj, rp.stts, " +
                   "rmjb.nobooking, rmjb.status as status_jkn, rmjb.validasi as waktu_checkin_jkn " +
                   "FROM reg_periksa rp " +
                   "INNER JOIN dokter d ON rp.kd_dokter = d.kd_dokter " +
                   "INNER JOIN poliklinik p ON rp.kd_poli = p.kd_poli " +
                   "LEFT JOIN referensi_mobilejkn_bpjs rmjb ON rp.no_rawat = rmjb.no_rawat " +
                   "WHERE rp.tgl_registrasi BETWEEN ? AND ? AND rp.stts <> 'Batal' AND NOT EXISTS " +
                   "(SELECT 1 FROM referensi_mobilejkn_bpjs_taskid t WHERE t.no_rawat = rp.no_rawat AND t.taskid IN (5, 7, 99))";

    try (PreparedStatement ps = koneksi.prepareStatement(query)) {
        ps.setString(1, Tanggal1.getText());
        ps.setString(2, Tanggal2.getText());
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String noRawat = rs.getString("no_rawat");
                String kodeBooking = rs.getString("nobooking") != null ? rs.getString("nobooking") : noRawat;
                TeksArea.append("SAPU BERSIH: Menemukan timeline belum selesai untuk " + noRawat + ". Memaksa penyelesaian.\n");
                Map<Integer, Date> taskTerkirim = getTaskTerkirimDenganWaktu(noRawat);
                Map<Integer, Date> timelineLengkap = buildPerfectTimeline(rs, true, taskTerkirim);
                kirimTaskDariTimeline(kodeBooking, noRawat, timelineLengkap);
            }
        }
    } catch (Exception e) {
        System.out.println("Notif Sapu Bersih: " + e.getMessage());
        e.printStackTrace();
    }
}

    private Map<Integer, Date> buildPerfectTimeline(ResultSet rs, boolean forceComplete, Map<Integer, Date> taskTerkirim) throws Exception {
        Map<Integer, Date> timeline = new HashMap<>();
        int taskTerakhir = taskTerkirim.keySet().stream().max(Integer::compare).orElse(0);
        String noRawat = rs.getString("no_rawat");
        
        String tanggalRegistrasi = rs.getString("tgl_registrasi");
        String waktuCheckinJKN = rs.getString("waktu_checkin_jkn");
        String waktuReg = tanggalRegistrasi + " " + rs.getString("jam_reg");
        
        String waktuTask4Raw = Sequel.cariIsi("select concat(p.tgl_perawatan,' ',p.jam_rawat) from pemeriksaan_ralan p where p.no_rawat=?", noRawat);
        if (waktuTask4Raw.isEmpty()) waktuTask4Raw = Sequel.cariIsi("select if(diterima='0000-00-00 00:00:00','',diterima) from mutasi_berkas where no_rawat=?", noRawat);
        String waktuTask5Raw = Sequel.cariIsi("select if(kembali='0000-00-00 00:00:00','',kembali) from mutasi_berkas where no_rawat=?", noRawat);
        boolean statusSudah = rs.getString("stts").equals("Sudah");
        
        boolean adaResep = Sequel.cariIsi("select no_resep from resep_obat where no_rawat=?", noRawat).length() > 0;
        String waktuTask6Raw = "", waktuTask7Raw = "";
        if (adaResep) {
            waktuTask6Raw = Sequel.cariIsi("select concat(tgl_perawatan,' ',jam) from resep_obat where tgl_perawatan<>'0000-00-00' and no_rawat=?", noRawat);
            waktuTask7Raw = Sequel.cariIsi("select concat(tgl_penyerahan,' ',jam_penyerahan) from resep_obat where concat(tgl_penyerahan,' ',jam_penyerahan)<>'0000-00-00 00:00:00' and no_rawat=?", noRawat);
        }

        Calendar waktuPatokan = Calendar.getInstance();
        
        if (taskTerakhir > 0) {
            waktuPatokan.setTime(taskTerkirim.get(taskTerakhir));
        } else {
            try {
                String waktuAwalStr = (waktuCheckinJKN != null && !waktuCheckinJKN.isEmpty() && !waktuCheckinJKN.contains("0000-00-00")) 
                                    ? tanggalRegistrasi + " " + waktuCheckinJKN.substring(11) 
                                    : waktuReg;
                waktuPatokan.setTime(dateFormat.parse(waktuAwalStr));
                timeline.put(3, waktuPatokan.getTime());
            } catch (Exception e) { return new HashMap<>(); }
        }
        
        if (taskTerakhir < 4) {
            Date waktuTask4 = parseDateOrNull(waktuTask4Raw);
            if (waktuTask4 != null && waktuTask4.after(waktuPatokan.getTime())) {
                waktuPatokan.setTime(waktuTask4);
            } else if (forceComplete || (waktuTask4 != null && !waktuTask4.after(waktuPatokan.getTime()))) {
                waktuPatokan.setTime(generateRandomTimestamp(waktuPatokan, 10, 15));
            } else { return timeline; }
            timeline.put(4, waktuPatokan.getTime());
        }

        if (taskTerakhir < 5) {
            Date waktuTask5 = parseDateOrNull(waktuTask5Raw);
            if (waktuTask5 != null && waktuTask5.after(waktuPatokan.getTime())) {
                waktuPatokan.setTime(waktuTask5);
            } else if (statusSudah || forceComplete || (waktuTask5 != null && !waktuTask5.after(waktuPatokan.getTime()))) {
                waktuPatokan.setTime(generateRandomTimestamp(waktuPatokan, 10, 15));
            } else { return timeline; }
            timeline.put(5, waktuPatokan.getTime());
        }

        if (adaResep && taskTerakhir < 6) {
            Date waktuTask6 = parseDateOrNull(waktuTask6Raw);
            if (waktuTask6 != null && waktuTask6.after(waktuPatokan.getTime())) {
                waktuPatokan.setTime(waktuTask6);
            } else if (forceComplete || (waktuTask6 != null && !waktuTask6.after(waktuPatokan.getTime()))) {
                waktuPatokan.setTime(generateRandomTimestamp(waktuPatokan, 2, 5));
            } else { return timeline; }
            timeline.put(6, waktuPatokan.getTime());
        }
            
        if (adaResep && taskTerakhir < 7) {
            Date waktuTask7 = parseDateOrNull(waktuTask7Raw);
            if (waktuTask7 != null && waktuTask7.after(waktuPatokan.getTime())) {
                 waktuPatokan.setTime(waktuTask7);
            } else if (forceComplete || (waktuTask7 != null && !waktuTask7.after(waktuPatokan.getTime()))) {
                 waktuPatokan.setTime(generateRandomTimestamp(waktuPatokan, 5, 10));
            } else { return timeline; }
            timeline.put(7, waktuPatokan.getTime());
        }
        
        return timeline;
    }
    
    private void kirimTaskDariTimeline(String kodeBooking, String noRawat, Map<Integer, Date> timeline) {
        if (timeline.isEmpty()) return;
        Map<Integer, Boolean> taskSudahKirim = getTaskTerkirim(noRawat);
        
        for (Map.Entry<Integer, Date> entry : timeline.entrySet()) {
            int taskId = entry.getKey();
            Date waktuTask = entry.getValue();

            if (!taskSudahKirim.getOrDefault(taskId, false)) {
                if (taskId == 6) {
                    kirimAntreanFarmasi(kodeBooking, noRawat);
                }
                kirimWaktuUpdate(kodeBooking, noRawat, taskId, waktuTask.getTime());
            }
        }
    }

    private void daftarkanPasienOnsite(ResultSet rs) {
        try {
            if (Sequel.cariInteger("select count(*) from referensi_mobilejkn_bpjs_taskid where no_rawat=? and taskid > 0", rs.getString("no_rawat")) > 0) {
                return;
            }
            TeksArea.append("Mendaftarkan pasien onsite " + rs.getString("no_rawat") + " ke antrean BPJS...\n");
            
            String kodedokterBpjs = Sequel.cariIsi("select kd_dokter_bpjs from maping_dokter_dpjpvclaim where kd_dokter=?", rs.getString("kd_dokter"));
            String kodepoliBpjs = Sequel.cariIsi("select kd_poli_bpjs from maping_poli_bpjs where kd_poli_rs=?", rs.getString("kd_poli"));
            if (kodedokterBpjs.isEmpty() || kodepoliBpjs.isEmpty()) {
                TeksArea.append("   Gagal: Mapping dokter/poli BPJS tidak ditemukan.\n");
                return;
            }
            
            String jamMulai = "";
            String jamSelesai = "";
            try (PreparedStatement psJadwal = koneksi.prepareStatement("select jam_mulai, jam_selesai from jadwal where kd_dokter=? and kd_poli=? and hari_kerja=?")) {
                psJadwal.setString(1, rs.getString("kd_dokter"));
                psJadwal.setString(2, rs.getString("kd_poli"));
                psJadwal.setString(3, hari);
                try (ResultSet rsJadwal = psJadwal.executeQuery()) {
                    if (rsJadwal.next()) {
                        jamMulai = rsJadwal.getString("jam_mulai");
                        jamSelesai = rsJadwal.getString("jam_selesai");
                    }
                }
            }
            if (jamMulai.isEmpty() || jamSelesai.isEmpty()) {
                TeksArea.append("   Gagal: Jadwal dokter tidak ditemukan.\n");
                return;
            }
            String jampraktek = jamMulai.substring(0, 5) + "-" + jamSelesai.substring(0, 5);
            
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-cons-id", koneksiDB.CONSIDAPIMOBILEJKN());
            utc = String.valueOf(api.GetUTCdatetimeAsString());
            headers.add("x-timestamp", utc);
            headers.add("x-signature", api.getHmac(utc));
            headers.add("user_key", koneksiDB.USERKEYAPIMOBILEJKN());
            
            requestJson = "{" +
                "\"kodebooking\": \"" + rs.getString("no_rawat") + "\"," +
                "\"jenispasien\": \"" + (rs.getString("kd_pj").equals(kodebpjs) ? "JKN" : "NON JKN") + "\"," +
                "\"nomorkartu\": \"" + (rs.getString("kd_pj").equals(kodebpjs) ? Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis=?", rs.getString("no_rkm_medis")) : "-") + "\"," +
                "\"nik\": \"" + Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?", rs.getString("no_rkm_medis")) + "\"," +
                "\"nohp\": \"" + Sequel.cariIsi("select no_tlp from pasien where no_rkm_medis=?", rs.getString("no_rkm_medis")) + "\"," +
                "\"kodepoli\": \"" + kodepoliBpjs + "\"," +
                "\"namapoli\": \"" + rs.getString("nm_poli") + "\"," +
                "\"pasienbaru\": " + (rs.getString("stts_daftar").equalsIgnoreCase("Baru") ? 1 : 0) + "," +
                "\"norm\": \"" + rs.getString("no_rkm_medis") + "\"," +
                "\"tanggalperiksa\": \"" + rs.getString("tgl_registrasi") + "\"," +
                "\"kodedokter\": " + kodedokterBpjs + "," +
                "\"namadokter\": \"" + rs.getString("nm_dokter") + "\"," +
                "\"jampraktek\": \"" + jampraktek + "\"," +
                "\"jeniskunjungan\": 3," +
                "\"nomorreferensi\": \"-\"," +
                "\"nomorantrean\": \"" + rs.getString("no_reg") + "\"," +
                "\"angkaantrean\": " + Integer.parseInt(rs.getString("no_reg")) + "," +
                "\"estimasidilayani\": " + (dateFormat.parse(rs.getString("tgl_registrasi") + " " + rs.getString("jam_reg")).getTime() + (5 * 60000)) + "," +
                "\"sisakuotajkn\": 0,\"kuotajkn\": 0,\"sisakuotanonjkn\": 0,\"kuotanonjkn\": 0," +
                "\"keterangan\": \"Pasien mendaftar di tempat.\"" +
            "}";

            requestEntity = new HttpEntity(requestJson, headers);
            URL = link + "/antrean/add";
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            nameNode = root.path("metadata");
            TeksArea.append("   Respon Onsite Add: " + nameNode.path("code").asText() + " " + nameNode.path("message").asText() + "\n");
        } catch(Exception e) {
            System.out.println("Notif Pendaftaran Onsite: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void kirimWaktuUpdate(String kodebooking, String noRawat, int taskid, long waktu) {
        try {
            TeksArea.append("-> Mengirim Task ID " + taskid + " untuk booking " + kodebooking + "...\n");
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-cons-id", koneksiDB.CONSIDAPIMOBILEJKN());
            utc = String.valueOf(api.GetUTCdatetimeAsString());
            headers.add("x-timestamp", utc);
            headers.add("x-signature", api.getHmac(utc));
            headers.add("user_key", koneksiDB.USERKEYAPIMOBILEJKN());
            requestJson = "{\"kodebooking\": \"" + kodebooking + "\",\"taskid\": \"" + taskid + "\",\"waktu\": \"" + waktu + "\"}";
            
            requestEntity = new HttpEntity(requestJson, headers);
            URL = link + "/antrean/updatewaktu";
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            nameNode = root.path("metadata");
            TeksArea.append("   Respon: " + nameNode.path("code").asText() + " " + nameNode.path("message").asText() + "\n");
            if (nameNode.path("code").asText().equals("200")) {
                Sequel.menyimpantf2("referensi_mobilejkn_bpjs_taskid", "?,?,?", "task id", 3, new String[]{
                    noRawat, String.valueOf(taskid), dateFormat.format(new Date(waktu))
                });
            }
        } catch (Exception ex) { 
            System.out.println("Notif Kirim Waktu: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void kirimAntreanFarmasi(String kodebooking, String noRawat) {
        String noResep = Sequel.cariIsi("select no_resep from resep_obat where no_rawat=?", noRawat);
        if (noResep.isEmpty()) return;
        if(Sequel.cariInteger("select count(*) from referensi_mobilejkn_bpjs_taskid where no_rawat=? and taskid='6'", noRawat) > 0) return;
        TeksArea.append("-> Menambah antrean farmasi untuk booking " + kodebooking + "...\n");
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-cons-id", koneksiDB.CONSIDAPIMOBILEJKN());
            utc = String.valueOf(api.GetUTCdatetimeAsString());
            headers.add("x-timestamp", utc);
            headers.add("x-signature", api.getHmac(utc));
            headers.add("user_key", koneksiDB.USERKEYAPIMOBILEJKN());
            String jenisResep = Sequel.cariInteger("select count(no_resep) from resep_dokter_racikan where no_resep=?", noResep) > 0 ? "Racikan" : "Non Racikan";
            int nomorAntrean = Integer.parseInt(StringUtils.right(noResep, 4));
            requestJson = "{\"kodebooking\": \"" + kodebooking + "\",\"jenisresep\": \"" + jenisResep + "\",\"nomorantrean\": " + nomorAntrean + ",\"keterangan\": \"Resep dibuat secara elektronik di poli\"}";
            requestEntity = new HttpEntity(requestJson, headers);
            URL = link + "/antrean/farmasi/add";
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            nameNode = root.path("metadata");
            TeksArea.append("   Respon: " + nameNode.path("code").asText() + " " + nameNode.path("message").asText() + "\n");
        } catch (Exception e) { 
            System.out.println("Notif Farmasi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void prosesBatalAntrean() {
        TeksArea.append("Memproses Antrean Batal...\n");
        String sql = "SELECT * FROM referensi_mobilejkn_bpjs_batal where statuskirim='Belum' and tanggalbatal BETWEEN ? AND ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, Tanggal1.getText());
            ps.setString(2, Tanggal2.getText());
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    try {
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                        utc=String.valueOf(api.GetUTCdatetimeAsString());
                        headers.add("x-timestamp",utc);
                        headers.add("x-signature",api.getHmac(utc));
                        headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                        requestJson ="{\"kodebooking\": \""+rs.getString("nobooking")+"\",\"keterangan\": \""+rs.getString("keterangan")+"\"}";

                        requestEntity = new HttpEntity(requestJson, headers);
                        URL = link + "/antrean/batal";
                        root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                        nameNode = root.path("metadata");

                        if (nameNode.path("code").asText().equals("200")) {
                            Sequel.queryu2("update referensi_mobilejkn_bpjs_batal set statuskirim='Sudah' where nomorreferensi=?", 1, new String[]{rs.getString("nomorreferensi")});
                            long waktuBatal = dateFormat.parse(rs.getString("tanggalbatal")).getTime();
                            kirimWaktuUpdate(rs.getString("nobooking"), rs.getString("no_rawat_batal"), 99, waktuBatal);
                        }
                        TeksArea.append("   Respon Batal: " + nameNode.path("code").asText() + " " + nameNode.path("message").asText() + "\n");
                    } catch (Exception ex) {
                        System.out.println("Notifikasi Bridging Batal: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception e) { 
            System.out.println("Notif SQL Batal: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private Map<Integer, Boolean> getTaskTerkirim(String noRawat) {
        Map<Integer, Boolean> tasks = new HashMap<>();
        try (PreparedStatement ps = koneksi.prepareStatement("select taskid from referensi_mobilejkn_bpjs_taskid where no_rawat=?")) {
            ps.setString(1, noRawat);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) tasks.put(rs.getInt("taskid"), true);
            }
        } catch (Exception e) { 
            System.out.println("Notif Get Task: " + e.getMessage());
            e.printStackTrace();
        }
        return tasks;
    }
    
    private Map<Integer, Date> getTaskTerkirimDenganWaktu(String noRawat) {
        Map<Integer, Date> tasks = new HashMap<>();
        try (PreparedStatement ps = koneksi.prepareStatement("select taskid, waktu from referensi_mobilejkn_bpjs_taskid where no_rawat=? ORDER BY taskid")) {
            ps.setString(1, noRawat);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) tasks.put(rs.getInt("taskid"), dateFormat.parse(rs.getString("waktu")));
            }
        } catch (Exception e) { 
            System.out.println("Notif Get Task Waktu: " + e.getMessage());
            e.printStackTrace();
        }
        return tasks;
    }

    private Date parseDateOrNull(String dateStr) {
        if (dateStr == null || dateStr.isEmpty() || dateStr.contains("0000-00-00")) return null;
        try { return dateFormat.parse(dateStr); } catch (Exception e) { return null; }
    }
    
    private Date generateRandomTimestamp(Calendar baseTime, int minMinutes, int maxMinutes) {
        Calendar newTime = Calendar.getInstance();
        newTime.setTime(baseTime.getTime());

        int minutesToAdd = ThreadLocalRandom.current().nextInt(minMinutes, maxMinutes + 1);
        int secondsToAdd = ThreadLocalRandom.current().nextInt(0, 60);
        
        newTime.add(Calendar.MINUTE, minutesToAdd);
        newTime.add(Calendar.SECOND, secondsToAdd);
        
        if (newTime.get(Calendar.DAY_OF_YEAR) != baseTime.get(Calendar.DAY_OF_YEAR) || newTime.get(Calendar.YEAR) != baseTime.get(Calendar.YEAR)) {
            newTime.setTime(baseTime.getTime()); 
            newTime.set(Calendar.HOUR_OF_DAY, 23);
            newTime.set(Calendar.MINUTE, 59);
            newTime.set(Calendar.SECOND, 59);
        }
        
        if(newTime.getTime().before(baseTime.getTime())){
             newTime.setTime(baseTime.getTime());
             newTime.add(Calendar.SECOND, 1);
        }
        
        return newTime.getTime();
    }
}