# Changelog — SIMRS Khanza RSAM

Semua perubahan penting dicatat di sini. Format: per **rilis (tag tanggal)** dengan label versi aplikasi.
Kategori: **Ditambah** (fitur baru), **Diubah**, **Diperbaiki** (fix), **Catatan DB/Deploy**.

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
