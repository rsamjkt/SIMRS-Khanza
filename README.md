# SIMRS Khanza — RSAM (Fork Kustom)

Sistem Informasi Manajemen Rumah Sakit (SIMRS) berbasis **SIMRS Khanza**, dikustomisasi untuk kebutuhan RS. Aplikasi **desktop Java Swing + MySQL** dengan laporan **JasperReports** dan integrasi BPJS/SatuSehat/PCare.

> **Versi saat ini:** `V.16.06.2026-25` · **Rilis:** `v2026.06.16`
> Lihat [CHANGELOG.md](CHANGELOG.md) untuk riwayat perubahan lengkap.

---

## ⚠️ Penting: ini fork yang sudah BANYAK dikustomisasi

Repo ini **bukan** SIMRS Khanza standar. Banyak modul sudah disesuaikan untuk RS. **Jangan menimpa buta** dari upstream — kerjaan kustomisasi bisa hilang.

- **Upstream:** [mas-elkhanza/SIMRS-Khanza](https://github.com/mas-elkhanza/SIMRS-Khanza) (branch `master`)
- **Cara adopsi fitur baru:** ambil yang baru saja, **jangan timpa** yang sudah dikustomisasi. Lihat skill [`khanza-upstream-reconcile`](.claude/skills/khanza-upstream-reconcile/).

### Kustomisasi RS yang DIJAGA (jangan ditimpa)
| Area | Keterangan |
|---|---|
| `DlgReg` | Tanggal Lahir di bukti registrasi + kustomisasi registrasi |
| `koneksiDB` | Method Cloudflare R2 (upload dokumen) |
| `sekuel` | Aturan tanggal dilonggarkan (edit data s/d 21 hari) |
| `DlgKasirRalan` | Kolom "Stts Asesmen" + integrasi penilaian keperawatan + indeks parsial/antripoli |
| `DlgKamarInap` | Penilaian Risiko Jatuh Neonatus (R1/R2/R3) |
| `WarnaTableKasirRalan` | Pewarnaan TTV/SEP custom |
| Laporan kunjungan TNI/Polri, RM USG, dll | Versi RS |

---

## 🧱 Teknologi

- **Java 8** (kompilasi `--release 8`, bytecode major 52) — penting: JRE produksi bisa Java 8
- **Swing/AWT** (NetBeans GUI Builder, pasangan `.java` + `.form`)
- **MySQL** via JDBC (koneksi dari `setting/database.xml`, kredensial AES)
- **JasperReports** (~1.286 `.jasper`, di-load dari folder `report/` eksternal)
- Integrasi: **BPJS** (VClaim/Antrean/Apotek/Smart Klaim), **SatuSehat** (FHIR), **PCare**, **Mobile JKN**, **Orthanc** (PACS/DICOM), Dukcapil

---

## 🚀 Build & Jalankan

### Build
Project NetBeans (Ant). Main class: `simrskhanza.SIMRSKhanza`. Target Java 1.8.
Untuk fix kecil, lazimnya compile + inject class ke jar (lihat skill [`khanza-deploy-release`](.claude/skills/khanza-deploy-release/) & [`khanza-build-doctor`](.claude/skills/khanza-build-doctor/)).

> ⚠️ Jebakan classpath: ada beberapa versi `commons-codec` di `dist/lib` — taruh `commons-codec-1.12.jar` paling depan & exclude `*-src.jar` (lihat `khanza-build-doctor`).

### Jalankan
Jar dinamai `khanza-<DD.MM.YYYY-build>.jar`. Launcher:

| OS | File | Cara |
|---|---|---|
| 🪟 Windows | `Aplikasi.bat` | double-klik |
| 🍎 macOS | `Aplikasi.command` | double-klik (pertama kali: klik kanan → Open) |

Isi launcher (intinya): `java -jar -Xss2m -Xms32m -Xmx1024m khanza-<versi>.jar`

---

## 📦 Deploy (Synology Drive)

Folder deploy: `/Users/randymandala/SynologyDrive/dist/dist/`. Tiap rilis: copy jar baru (nama lowercase `khanza-<versi>.jar`) + update `Aplikasi.bat` & `Aplikasi.command`. **Jar lama tidak dihapus** (untuk rollback). Otomatis via skill [`khanza-deploy-synology`](.claude/skills/khanza-deploy-synology/).

---

## ⚙️ Konfigurasi

`setting/database.xml` — host/DB/kredensial (AES), plus konfigurasi integrasi: BPJS, SatuSehat, PCare, Smart Klaim, Orthanc, Cloudflare R2, dll.

Beberapa fitur butuh tabel DB tambahan (dijalankan terpisah saat deploy):
- 3 surat: `surat_keterangan_berobat`, `surat_penolakan_resusitasi` (+bukti), `surat_permintaan_second_opinion` (+bukti) + 3 kolom hak akses di tabel `user`
- Orthanc DICOM: `gambar_radiologi` + 12 tabel `hasil_*_gambar`
- BUD resep: `bud_obat`, `bud_racikan`

---

## 🔢 Versi & Rilis

- **Label versi:** di `frmUtama.java` (`jLabel7`) format `V.DD.MM.YYYY-build`, di-bump tiap update.
- **Rilis:** git tag pola tanggal `vYYYY.MM.DD` (mis. `v2026.06.16`). Tidak pakai GitHub Release object; jar didistribusi terpisah.

---

## 🛠️ Dev Workflow (Claude Code Skills)

Repo punya skill di [`.claude/skills/`](.claude/skills/) yang otomatis aktif:

| Skill | Fungsi |
|---|---|
| `khanza-upstream-reconcile` | Banding & adopsi fitur upstream tanpa timpa kustomisasi |
| `khanza-add-fitur` | Tambah fitur end-to-end (form + hak akses + menu + DB) |
| `khanza-jasper-report` | Bikin/recompile report (Java 8, compilerClass, sebar 3 folder) |
| `khanza-crud-form` | Pola form Swing + CRUD (widget/Sequel/koneksiDB) |
| `khanza-bridging` | Integrasi BPJS/SatuSehat/PCare |
| `khanza-build-doctor` | Diagnosa error compile/classpath |
| `khanza-deploy-release` | Build, inject jar, sinkron report, SQL, bump versi, tag |
| `khanza-deploy-synology` | Deploy jar ke Synology + update launcher (.bat/.command) |

---

## 📁 Struktur Singkat

```
src/                  kode Java (rekammedis, bridging, keuangan, inventory, simrskhanza, ...)
report/               JasperReports (.jrxml/.jasper)
webapps/              modul PHP (e-Pasien, hybrid web)
setting/database.xml  konfigurasi (AES)
dist/                 jar build + lib (gitignored)
.claude/skills/       workflow pengembangan
KhanzaHMSService*/    sub-aplikasi service (SatuSehat, PCare, Mobile JKN, ...)
```

---

## 📜 Lisensi & Atribusi

Berbasis SIMRS Khanza (Khanza.Soft Media) — Aladdin Free Public License. Kustomisasi oleh tim RS.
