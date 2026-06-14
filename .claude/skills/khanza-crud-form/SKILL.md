---
name: khanza-crud-form
description: Pola form Swing & CRUD database di SIMRS Khanza (widget, Sequel, koneksiDB). Gunakan saat scaffold/ubah form, simpan/edit/hapus data, query tabel, atau menulis interaksi DB di form.
---

# Khanza — Form Swing & CRUD

## Komponen UI (paket `widget.*`)
`Button`, `ButtonBig`, `TextBox`, `TextArea`, `Table`, `ComboBox`, `CekBox`, `RadioButton`, `Tanggal`, `Label`, `Panel`, `ScrollPane`, `TabPane`. Form = NetBeans `JDialog`/`JInternalFrame` dengan pasangan `.java` + `.form`.

## Koneksi & helper
- `koneksiDB.condb()` → `Connection` (singleton, auto-reconnect). Config dari `setting/database.xml` (AES).
- Helper SQL: `fungsi.sekuel` (biasanya `private sekuel Sequel=new sekuel();`).
- Validasi/format/report: `fungsi.validasi` (`private validasi Valid=new validasi();`).

## CRUD via Sequel (sekuel.java)
**Simpan (INSERT):**
- `Sequel.menyimpan("tabel","?,?,?",3,new String[]{a,b,c})` — values berurutan.
- `Sequel.menyimpantf(...)` → return boolean (true=sukses). `menyimpantf2` varian.
- `menyimpanignore(...)` → INSERT IGNORE.

**Edit (UPDATE):**
- `Sequel.mengedit("tabel","key=?","kolom=?,kolom2=?",n,new String[]{...})`.
- `mengedittf(...)` → boolean. `mengedit2/3` varian (mis. commit beda).

**Hapus (DELETE):**
- `Sequel.meghapus("tabel","field",nilai)` / `meghapus2` / `meghapus3` / `meghapustf`.

**Query langsung:** `Sequel.query(qry)`, `queryu(qry)` (update), `queryu2(qry,n,String[])`, `queryutf(...)` (boolean).

**Baca nilai tunggal:** `Sequel.cariIsi("select x from t where y=?",val)` (String), `cariInteger(...)`, `cariIsiAngka(...)` (double), `cariGambar(...)` (BLOB).

**Auto nomor:** `Valid.autoNomer3("select ifnull(max(...),0) from t where ...","PREFIX"+tgl,4,FieldNomor)`.

## Pola tabel + pencarian (lazimnya)
- `DefaultTableModel tabMode` dengan header array + `isCellEditable=false` + `getColumnClass`.
- Set lebar kolom via loop `for(i...) getColumnModel().getColumn(i).setPreferredWidth(...)`; kolom kunci (kd_pj/kd_poli) sering di-hide `setMaxWidth(0)`.
- `tampil()` isi tabel dari ResultSet; tombol Cari filter `where ... like '%"+TCari+"%'`.
- Pewarnaan baris: renderer `fungsi.WarnaTable*`.

## Transaksi
Operasi multi-tabel: `Sequel.AutoComitFalse()` → batch → commit/rollback. Cek pola di form existing (DlgPemberianObat, DlgKasirRalan).

## ⚠️ Keamanan
Banyak SQL pakai string-concat → rawan SQL injection. Untuk query baru, **pakai PreparedStatement / Sequel dengan `?`**, jangan concat input mentah.

## Verifikasi
Compile-check Java 8 (memory `khanza-build-compile`). Jangan timpa form yang dikustomisasi RS (memory `khanza-preserve-customizations`).
