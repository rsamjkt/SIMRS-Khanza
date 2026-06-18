# Changelog — SIMRS Khanza RSAM

Semua perubahan penting dicatat di sini. Format: per **rilis (tag tanggal)** dengan label versi aplikasi.
Kategori: **Ditambah** (fitur baru), **Diubah**, **Diperbaiki** (fix), **Catatan DB/Deploy**.

---

## [v2026.06.18e] — 2026-06-18 · `V.18.06.2026-34 (Atta)`

### Diubah
- **CPPT tab — redesign layout AruniHealth** (style mirip hasil cetak, ada QR code per entri):
  - Tab menampilkan card per entri dengan layout identik hasil cetak CPPT.
  - **QR code** di setiap card (pojok kanan atas) menggunakan ZXing — payload: `SIMRS-CPPT|{no_rawat}|{tgl} {jam}|{nip}|{nama_petugas}`.
  - SOAP tampil **1 kolom vertikal** (S → O → A → P) dengan strip warna kiri AruniHealth-style: Subyektif (#2563EB biru), Obyektif (#DC2626 merah), Asesmen (#D97706 oranye), Plan (#10B981 hijau).
  - Instruksi + Evaluasi dalam kotak abu-abu (#F8FAFC).
  - Verif DPJP: hijau (`#F0FFF4`) bila sudah terverifikasi; kuning (`#FFFBEB`) + "TBAK — Menunggu verifikasi DPJP" bila belum.
- **Report cetak CPPT** (`rptCPPT.jasper`) — redesign lengkap:
  - Ubah dari **landscape A4 → portrait A4** (595×842).
  - **QR Code per entri** menggunakan `jr:QRCode` (barcode4j component JasperReports 6.8) — payload sama dengan tab.
  - Layout card per baris: header (profesi/nama, tgl/jam) → konten SOAP+TTV+instruksi+verif (1 kolom markup HTML, auto-stretch) → QR di kanan.
  - Field baru: `no_rawat`, `nip` (diambil dari SQL CPPT).
- SQL `tampilCPPT` dan `cetakCPPT` ditambah `pr.nip AS nip`, `pr.no_rawat AS no_rawat`, `COALESCE(pr.evaluasi,'') AS evaluasi`.

### Catatan Deploy
- Copy `rptCPPT.jasper` ke `/SynologyDrive/dist/dist/report/` (sudah dilakukan).

---

## [v2026.06.18d] — 2026-06-18 · `V.18.06.2026-33 (Atta)`

### Diubah
- **CPPT tab — redesign layout** menjadi card view (HTML) seperti AruniHealth:
  - Setiap entri ditampilkan sebagai card terpisah, bukan tabel horizontal.
  - SOAP dalam **2-kolom** per card: S (Subjektif) + O (Objektif) di baris atas, A (Asesmen) + P (Plan/RTL) di baris bawah — persis seperti DokterRanap di web AruniHealth.
  - TTV tampil sebagai baris chip di atas grid SOAP.
  - Instruksi dan Verifikasi DPJP sebagai baris bawah dengan warna berbeda.
  - Report cetak (`rptCPPT.jasper`) diperbarui: kolom SOAP sekarang 1 kolom lebar (320px) berisi S/O/A/P stacked dengan label.

## [v2026.06.18c] — 2026-06-18 · `V.18.06.2026-32 (Atta)`

### Ditambah
- **Tab CPPT (Catatan Perkembangan Pasien Terintegrasi)** di `RMRiwayatPerawatan`:
  - Tab baru "CPPT" di JTabbedPane riwayat perawatan.
  - Tombol **Cetak CPPT** → laporan `rptCPPT.jasper` (A4 landscape) dengan kop RS, info pasien.
  - Data diambil otomatis saat tab dipilih berdasarkan kunjungan yang dipilih di tab Riwayat Kunjungan.

### Catatan Deploy
- Copy `rptCPPT.jasper` ke `/SynologyDrive/dist/dist/report/` (wajib agar cetak CPPT bisa jalan).

---

## [v2026.06.18b] — 2026-06-18 · `V.18.06.2026-31 (Atta)`

### Ditambah — dari upstream mas-elkhanza
- **Report `rptCetakPenilaianAwalKebidananRanap`** — form penilaian awal kebidanan ranap (jrxml + jasper).
- **13 upload handler Orthanc PACS** (`webapps/hasilpemeriksaan*/pages/upload/service.php`): Echo, Echo Pediatrik, EKG, Endoskopi (Faring/Hidung/Telinga), OCT, Slit Lamp, Treadmill, USG, USG Gynecologi, USG Neonatus, USG Urologi.
- 3 icon baru di `src/48x48/`.

### Catatan DB (jalankan saat deploy — WAJIB sebelum pakai Catatan Observasi Ruang OK)
```sql
-- Tabel data catatan observasi ruang operasi
CREATE TABLE IF NOT EXISTS `catatan_observasi_ruang_ok` (
  `no_rawat` varchar(17) NOT NULL,
  `tgl_perawatan` date NOT NULL,
  `jam_rawat` time NOT NULL,
  `gcs` varchar(10) DEFAULT NULL,
  `td` varchar(8) NOT NULL,
  `hr` varchar(5) DEFAULT NULL,
  `rr` varchar(5) DEFAULT NULL,
  `suhu` varchar(5) DEFAULT NULL,
  `spo2` varchar(3) NOT NULL,
  `keterangan` varchar(100) NOT NULL,
  `nip` varchar(20) NOT NULL,
  PRIMARY KEY (`no_rawat`,`tgl_perawatan`,`jam_rawat`),
  KEY `no_rawat` (`no_rawat`),
  KEY `nip` (`nip`),
  CONSTRAINT `catatan_observasi_ruang_ok_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `catatan_observasi_ruang_ok_ibfk_2` FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Hak akses (dari v2026.06.18 — kalau belum dijalankan)
ALTER TABLE user ADD COLUMN IF NOT EXISTS catatan_observasi_ruang_ok tinyint(1) DEFAULT 0;
```

### Catatan Deploy
- Copy `rptCetakPenilaianAwalKebidananRanap.jasper` ke `/SynologyDrive/dist/dist/report/` (sudah dilakukan).

---

## [v2026.06.18] — 2026-06-18 · `V.18.06.2026-30`

### Ditambah — Fitur dari upstream mas-elkhanza
- **Catatan Observasi Ruang Operasi** (`RMDataCatatanObservasiRuangOperasi`): form baru + report. Menu "Catatan Observasi Ruang Operasi" ditambahkan di **IGD, Kamar Inap, Kasir Ralan, Registrasi** (submenu Rekam Medik Operasi).
- **Hak akses** `catatan_observasi_ruang_ok` di `akses.java` + `DlgUser` / `DlgUpdateUser` (kolom index 1202).
- **Cache perujuk** (`cache/perujuk.iyem`) + pencarian berbasis cache di `DlgRujukMasuk`.
- **`DlgPoli`**: inlining `prosesCari()` — selaraskan dengan upstream.
- **`SmartKlaimBPJSKirimFHIR`**: fix loop FHIR resource array (`while` → `if` + `do-while`).

### Catatan DB (jalankan saat deploy)
```sql
ALTER TABLE user ADD COLUMN catatan_observasi_ruang_ok tinyint(1) DEFAULT 0;
```

---

## [v2026.06.17d] — 2026-06-17 · `V.17.06.2026-29`

### Diubah
- **Form Permintaan Radiologi** (UI only, database tidak diubah):
  - Label "Informasi Tambahan Permintaan Foto" → **"Indikasi Klinis"**
  - Label "Indikasi Pemeriksaan / Diagnosis Klinis" → **"Diagnosis Klinis"**

---

## [v2026.06.17c] — 2026-06-17 · `V.17.06.2026-28`

### Diubah
- **Form Permintaan Laboratorium** (UI only, database tidak diubah):
  - Label "Indikasi/Klinis" → **"Diagnosa"**
  - Label "Informasi Tambahan" → **"Indikasi Klinis"**
  - Pesan validasi ikut diperbarui.

---

## [v2026.06.17b] — 2026-06-17 · `V.17.06.2026-27`

### Diperbaiki
- **Tanggal Kunjungan di cetakan asesmen awal** — sebelumnya hanya tampil tanggal, sekarang tampil tanggal + jam (`dd/MM/yyyy HH:mm`). Berlaku untuk 8 laporan: Penilaian Awal Kebidanan Ralan, Keperawatan Gigi, IGD, Ralan, Ralan Anak, Ralan Geriatri, Ranap, Ranap Neonantus. Field `tgl_lahir` dan tanggal obstetri tidak disentuh.

### Catatan Deploy
- Copy `.jasper` yang berubah ke `/SynologyDrive/dist/dist/report/` (sudah dilakukan).
- Skill `khanza-deploy-synology` diupdate: otomatis sync report ke Synology tiap rilis.

---

## [v2026.06.17] — 2026-06-17 · `V.17.06.2026-26`

### Diubah
- **`DlgBookingPeriksa`**: optimasi query `select *` → explicit columns (hemat memory); `while` → `if` untuk tabel config 1-row (`set_kelengkapan_data_pasien`, `set_urut_no_rkm_medis`); fix race condition — `runBackground(tampil)` dipindah setelah inisialisasi variabel DB selesai. (Adopt upstream "Optimasi query & memory" — tanpa kustomisasi RS di file ini.)

---

## [v2026.06.16] — 2026-06-16 · `V.16.06.2026-25`

### Diperbaiki
- **NPE production di `widget.Tanggal.setMinDate`** (`popupEditor` null). `JDateTimePicker` hanya set `popupEditor` untuk L&F Metal/Motif/Windows; di L&F production (Nimbus) = null → crash saat klik baris di **Surat Kontrol BPJS** (`BPJSSuratKontrol.updateMinDateKontrol` via itemListener `TanggalSurat`). Fix: null-guard di `setMinDate`/`getMinDate`.
  - Catatan: validasi "blok kontrol < 8 hari" tetap jalan (murni kode di tombol Simpan, tidak terpengaruh bug ini). Pengecualian: Admin Utama, poli Mata/Bedah.

### Ditambah (Dev/Deploy)
- Skill **`khanza-deploy-synology`**: copy jar rilis ke `/SynologyDrive/dist/dist/khanza-<versi>.jar` (lowercase) + update launcher. **Jar lama tidak dihapus** (rollback).
- **`Aplikasi.command`** — launcher macOS (padanan `Aplikasi.bat`, double-klik di Finder). Tanpa `-XX:PermSize/MaxPermSize` (usang sejak Java 8). Skill deploy mengelola `.bat` & `.command` sekaligus.

---

## [v2026.06.15] — 2026-06-15 · `V.15.06.2026-23` → `-24`

### Ditambah
- **Keterangan BUD (Beyond Use Date) di cetakan resep** (etiket/aturan pakai obat): `rptItemResep`, `rptItemResep2/3/5/6`. Field `bud` + tampilan BUD (sumber `bud_obat.bud` / `bud_racikan.bud`). Diterapkan ke **versi etiket milik RS**, bukan versi default.
- **Fix keuangan dari upstream**: `KeuanganBayarBebanHutangLain` — field No.Bukti `getOnlyAngka` → `getKata` (boleh huruf).
- **File fitur Wearable e-Pasien** diambil untuk implementasi nanti (`epasien/...wearable...`). Belum diaktifkan (butuh tabel `pasien_wearable` & modul e-Pasien — masih aktif dirombak upstream).

### Diperbaiki
- **Bug laten**: query `rptItemResep2` kedua di `DlgResepObat` (~baris 1788) tanpa `bud` → ditambah `bud_racikan.bud` + `left join` (cegah error "Unknown column" setelah jrxml punya field `bud`).

### Catatan DB
- BUD butuh tabel `bud_obat` & `bud_racikan` (sudah ada di skema RS).

---

## [v2026.06.14] — 2026-06-14 · `V.14.06.2026-21` → `-22`

Rilis besar: **reconciliation dengan upstream mas-elkhanza** (ambil fitur baru tanpa menimpa kustomisasi RS) + dokumentasi.

### Ditambah — 3 Surat Baru (end-to-end)
- Form **Surat Keterangan Berobat**, **Surat Penolakan Resusitasi**, **Surat Permintaan Second Opinion** (`.java` + `.form` + report + icon + webapp PHP).
- Hak akses (`akses.java`) + checkbox di **DlgUser/DlgUpdateUser**.
- Menu cetak surat di **frmUtama, IGD (DlgIGD), Kamar Inap (DlgKamarInap), Kasir Ralan (DlgKasirRalan)** — disisip **bedah** (kustomisasi RS dijaga byte-for-byte: neonatus di KamarInap, asesmen/parsial/penilaian di KasirRalan).

### Ditambah — Fitur lain dari upstream
- **Tab "Pengembalian Deposit"** di `DlgPengeluaranPengeluaran` + report (tabel `pengembalian_deposit` sudah ada → tanpa DB baru).
- **Upload DICOM ke Orthanc PACS**: `DlgCariPeriksaRadiologi` + 12 form `RMHasil*` (EKG, Echo, USG, Endoskopi, OCT, dll) + `ApiOrthanc` + webapp `radiologi/service.php`.
- `RMDataCatatanKeseimbanganCairan` (auto-hitung keseimbangan cairan), `RMDataResumePasienRanap` (minor).

### Diubah
- **`SmartKlaimBPJSKirimFHIR`** di-update ke versi upstream terbaru (FHIR mapping diperluas; tanpa kustomisasi RS).
- **`DlgReg`**: Tanggal Lahir di bukti registrasi (`rptBuktiRegister`) — query + jrxml + recompile Java 8.

### Diperbaiki
- Bugfix BPJS: cek status `.equals("AKTIF")` → `.startsWith("AKTIF")` (handle "AKTIF (xxx)") di `BPJSDataSEP` & `PCareDataPendaftaran`.

### Ditambah — Dev Workflow
- **7 skill** pengembangan di `.claude/skills/`: `khanza-upstream-reconcile`, `khanza-jasper-report`, `khanza-add-fitur`, `khanza-deploy-release`, `khanza-crud-form`, `khanza-build-doctor`, `khanza-bridging`.

### Catatan DB (jalankan saat deploy)
- 3 surat: `surat_keterangan_berobat`, `surat_penolakan_resusitasi` (+`bukti_*`, +`*_saksikeluarga`), `surat_permintaan_second_opinion` (+`bukti_*`, +`*_saksikeluarga`) + `ALTER TABLE user` (3 kolom hak akses).
- Orthanc: `gambar_radiologi` + 12 tabel `hasil_*_gambar` (tanpa FK agar aman).

---

## Konvensi

- **Label versi:** `V.DD.MM.YYYY-build` di `frmUtama.java` — build naik terus tiap perubahan.
- **Rilis:** git tag `vYYYY.MM.DD`. Jar: `khanza-<DD.MM.YYYY-build>.jar` di folder deploy.
- **Aturan emas:** ambil fitur baru dari upstream, **jangan timpa** kustomisasi RS.
