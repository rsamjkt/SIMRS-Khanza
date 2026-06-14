---
name: khanza-upstream-reconcile
description: Workflow membandingkan & mengadopsi fitur baru dari upstream mas-elkhanza/SIMRS-Khanza ke fork RS ini TANPA menimpa kustomisasi RS. Gunakan saat user minta "cek upstream", "ada fitur baru apa", "adopsi/ambil dari mas-elkhanza", "samakan dengan upstream", atau reconcile/merge perbedaan.
---

# SIMRS Khanza — Upstream Reconcile

Mengadopsi fitur baru dari upstream `mas-elkhanza/SIMRS-Khanza` ke fork RS (`rsamjkt/Khanza-26-Jan2025`) dengan **aturan mutlak: ambil yang BARU, JANGAN timpa kode kustomisasi RS.**

## Prinsip
- "jgn timpa" — kerjaan kustomisasi RS tidak boleh hilang. Kalau ragu, STOP & tanya, jangan main timpa.
- Sebelum eksekusi file dua-arah, tampilkan diff & jelaskan mana "punya kita" vs "baru upstream", lalu yakinkan user.

## Langkah

### 1. Siapkan remote upstream
```bash
git remote add elkhanza https://github.com/mas-elkhanza/SIMRS-Khanza.git 2>/dev/null
git fetch --depth 1 --filter=blob:none elkhanza master
```
Repo tidak punya common ancestor → diff tree langsung.

### 2. Cari perbedaan NYATA (abaikan CRLF)
```bash
git diff --no-renames --ignore-cr-at-eol --numstat HEAD elkhanza/master -- src/
```
- Banyak "beda" hanya CRLF (upstream CRLF, kita LF) → abaikan.
- Kolom: `+` = ditambah upstream, `−` = punya kita yang upstream tak punya.
- Hitung baris `−` non-kosmetik per file (filter tanggal default, `{`/`}`, whitespace) = indikator kustomisasi kita.

### 3. Klasifikasi tiap file .java
- **A (hanya upstream)** = file baru → salin (lihat #4a).
- **D (hanya kita)** = file custom kita → JANGAN sentuh.
- **M, DEL\*≈0** = murni additive → boleh **ambil utuh** (#4a).
- **M, dua arah** = kita & upstream sama-sama beda → **BEDAH-SISIP** (#4b), jangan timpa.
- Hanya CRLF / kosmetik / preferensi kita → SKIP.

### 4a. Ambil utuh (wholesale, normalisasi ke LF)
```bash
git show elkhanza/master:<path>.java | tr -d '\r' > <path>.java
git show elkhanza/master:<path>.form | tr -d '\r' > <path>.form
```
File biner (.png/.jasper/.jpg/webapp): `git checkout elkhanza/master -- <path>` atau `git show ... > file` (tanpa tr).
Aman dipakai HANYA bila sudah dipastikan tidak ada baris `−` kustomisasi kita.

### 4b. Bedah-sisip (file kita jadi dasar, sisip blok baru) — pakai Python anchor-based
```python
ours = open('<path>').read().split('\n')
up   = open('/tmp/upstream_file').read().split('\n')   # git show elkhanza/master:path|tr -d '\r' > /tmp/...
def after(L,anc,b):  # sisip b setelah baris berisi anc
    for i,l in enumerate(L):
        if anc in l: return L[:i+1]+b+L[i+1:]
    raise SystemExit("NOT FOUND: "+anc)
def before(L,anc,b): ...
def repl(L,f,r): ...
# contoh menu surat: import, handler (before handler kita), decl extend, setEnabled, instantiation, menu-add
```
**Jebakan indeks kolom:** kalau blok upstream merujuk `getValueAt(row, N)`, cek apakah layout kolom tabel kita beda. Mis. DlgKasirRalan: upstream pakai 17/18/11, kita 20/21/14 (ada kolom "Stts Asesmen" ekstra). PERTAHANKAN indeks kita; jangan ambil indeks upstream → bug.
Handler yang pakai textfield (TNoRw/DTPCari2) bukan indeks tabel = aman, portable.

### 5. Verifikasi (WAJIB tiap file)
- Blok kustomisasi kita IDENTIK byte-for-byte vs backup:
  `diff <(grep <blok> /tmp/backup) <(grep <blok> <path>)`
- Compile-check Java 8 (lihat memory `khanza-build-compile`):
  `javac --release 8 -encoding UTF-8 -cp "$CP" -d /tmp/out <files>`
  CP harus exclude `*-src.jar` & taruh `commons-codec-1.12.jar` di depan.

### 6. DB & config
- DDL tabel baru dari `git show elkhanza/master:sik.sql` (byte-safe `LC_ALL=C`; ada blob biner).
- Buat CREATE TABLE **tanpa FK** bila tipe `no_rawat` induk bisa beda (hindari error). User yang jalankan SQL.
- Report .jasper di-load dari `report/`, `dist/report/`, `build/report/` → sebar ke tiga lokasi.

### 7. Rilis
- Bump label versi frmUtama (memory `version-label-bump`).
- Commit per unit logis → `git push origin main`.
- Tag: `git tag -a vYYYY.MM.DD -m "<catatan>"; git push origin vYYYY.MM.DD` (memory `khanza-release-process`).

## Referensi cepat
- Kustomisasi yang dijaga: lihat memory `khanza-preserve-customizations`.
- Contoh bedah-sisip menu surat sudah dilakukan di: frmUtama, DlgIGD, DlgKamarInap, DlgKasirRalan.
- Fitur sudah diadopsi (v2026.06.14): 3 surat baru, tab Pengembalian Deposit, upload DICOM Orthanc (radiologi + 12 form hasil), update SmartKlaim FHIR.
