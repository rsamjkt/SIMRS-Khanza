---
name: khanza-add-fitur
description: Tambah fitur/form/surat baru end-to-end di SIMRS Khanza (form + hak akses + menu + checkbox user + tabel DB). Gunakan saat user minta tambah form/surat/menu/modul baru, atau "tambahkan fitur X dengan hak aksesnya".
---

# Khanza — Tambah Fitur End-to-End

Satu fitur baru nyentuh ~6 tempat di file berbeda. Kalau ada yang kelupaan → menu tidak muncul / error login. Ikuti checklist ini.

## A. Form (UI)
- `src/<paket>/NamaForm.java` + `NamaForm.form` (pasangan NetBeans).
- Komponen pakai `widget.*` (Button, ButtonBig, TextBox, Table, Tanggal, dll). Koneksi `koneksiDB.condb()`. CRUD via `Sequel` (lihat skill `khanza-crud-form`).

## B. Hak akses — `src/fungsi/akses.java` (5 TITIK, anchor pakai flag yang sudah ada mis. `surat_permohonan_privasi`)
1. Deklarasi field boolean: `...,nama_flag=false;`
2. Blok admin (set semua true): `akses.nama_flag=true;`
3. Blok baca user (rs2): `akses.nama_flag=rs2.getBoolean("nama_flag");`
4. Blok reset/setLogOut: `akses.nama_flag=false;`
5. Getter: `public static boolean getnama_flag(){return akses.nama_flag;}`
Permission dibaca dari tabel **`user`** (admin dapat semua otomatis lewat kode).

## C. Menu di frmUtama / Dlg (bedah-sisip, JANGAN timpa file)
Tombol/menu item: deklarasi + instantiation (`new widget.ButtonBig()`/`new JMenuItem()` set icon `/picture/...`/`/48x48/...`, text, name, size, addActionListener) + handler `XxxActionPerformed` (buka form) + `Panelmenu.add`/`MnX.add` dijaga `if(akses.getnama_flag())`. Untuk file yang dikustomisasi RS → graft Python anchor-based (lihat skill `khanza-upstream-reconcile`).

## D. Checkbox hak akses — DlgUser.java & DlgUpdateUser.java
- Header kolom permission (array String) → tambah label `"[P]Nama"`.
- Naikkan jumlah kolom (`for i<NNN`) + width case + default `false` saat tambah user.
- Query SELECT user + `rs.getBoolean(...)` + UPDATE simpan + copy hak akses.
- DlgUpdateUser: blok `setTampil()` (addRow filter) + blok simpan (mengedit per checkbox).

## E. Database (user yang jalankan SQL)
- `CREATE TABLE` untuk data form (struktur dari kode `Sequel.menyimpan(...)` atau dari `git show elkhanza/master:sik.sql`).
- `ALTER TABLE user ADD COLUMN nama_flag tinyint(1) NOT NULL DEFAULT 0;`
- Buat tanpa FK bila ragu tipe kolom induk (hindari error).

## Verifikasi
- Compile-check Java 8 SEMUA file yang disentuh bareng (akses + frmUtama + form + DlgUser/DlgUpdateUser). Lihat memory `khanza-build-compile`.
- Pastikan blok kustomisasi RS yang disentuh tetap identik byte-for-byte.

## Contoh nyata
Adopsi 3 surat (Keterangan Berobat, Penolakan Resusitasi, Second Opinion) di v2026.06.14 mengikuti pola ini persis.
