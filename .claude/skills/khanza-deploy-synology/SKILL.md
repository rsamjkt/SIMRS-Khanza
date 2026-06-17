---
name: khanza-deploy-synology
description: Deploy jar rilis SIMRS Khanza ke folder Synology Drive & update Aplikasi.bat. Gunakan saat user minta "deploy ke synology", "copy jar release ke synology", "update aplikasi.bat", atau setelah build jar rilis baru. ATURAN MUTLAK: jangan hapus jar lama.
---

# Khanza — Deploy ke Synology Drive

Menyalin jar rilis ke folder deploy Synology + mengarahkan `Aplikasi.bat` ke jar baru. **Jar lama JANGAN dihapus** (untuk rollback).

## Lokasi
- Repo jar (hasil build): `dist/Khanza-<DD.MM.YYYY-build>.jar` (huruf K besar).
- Folder deploy: **`/Users/randymandala/SynologyDrive/dist/dist/`** (nested `dist/dist`, BUKAN `dist/`).
- Launcher: `/Users/randymandala/SynologyDrive/dist/dist/Aplikasi.bat`.
- Pola nama jar deploy: **lowercase** `khanza-<DD.MM.YYYY-build>.jar`.

## Langkah
```bash
REPO="/Users/randymandala/Documents/GitHub/Khanza-26-Jan2025"
DEST="/Users/randymandala/SynologyDrive/dist/dist"
# 1. versi dari label frmUtama (V.DD.MM.YYYY-build -> DD.MM.YYYY-build)
VER=$(grep -oE 'V\.[0-9.]+-[0-9]+' "$REPO/src/simrskhanza/frmUtama.java" | head -1 | sed 's/^V\.//')
SRC="$REPO/dist/Khanza-$VER.jar"      # jar di repo (cek ADA)
NEW="khanza-$VER.jar"                 # nama deploy lowercase

# 2. copy jar baru (TAMBAH, jangan hapus yg lama)
cp "$SRC" "$DEST/$NEW"

# 3. backup + update launcher (Windows .bat DAN macOS .command)
cp "$DEST/Aplikasi.bat" "$DEST/Aplikasi.bat.bak-$(echo $VER | tr -d '.-')"
sed -i '' -E "s#khanza-[A-Za-z0-9._-]*\.jar#$NEW#" "$DEST/Aplikasi.bat"
# macOS launcher (buat kalau belum ada; selalu sinkronkan nama jar)
[ -f "$DEST/Aplikasi.command" ] && cp "$DEST/Aplikasi.command" "$DEST/Aplikasi.command.bak-$(echo $VER | tr -d '.-')"
sed -i '' -E "s#khanza-[A-Za-z0-9._-]*\.jar#$NEW#" "$DEST/Aplikasi.command" 2>/dev/null || \
  printf '#!/bin/bash\ncd "$(dirname "$0")"\njava -jar -Xss2m -Xms32m -Xmx1024m "%s"\n' "$NEW" > "$DEST/Aplikasi.command"
chmod +x "$DEST/Aplikasi.command"

# 4. copy report yang berubah/baru ke Synology report folder
# Cari file .jasper yang berubah sejak commit terakhir sebelum HEAD
CHANGED_REPORTS=$(git -C "$REPO" diff HEAD~1 HEAD --name-only -- 'report/*.jasper' | xargs -I{} basename {})
for f in $CHANGED_REPORTS; do
  cp "$REPO/report/$f" "$DEST/report/$f" && echo "report OK: $f"
done
```
Catatan macOS: `Aplikasi.command` = padanan `.bat` (double-klik di Finder). JANGAN pakai `-XX:PermSize`/`-XX:MaxPermSize` (dihapus sejak Java 8, warning di Java 9+). Isinya: `cd "$(dirname "$0")"` lalu `java -jar -Xss2m -Xms32m -Xmx1024m <jar>`. Pastikan executable (`chmod +x`).

## Verifikasi WAJIB
```bash
ls "$DEST"/khanza-*.jar          # jar LAMA harus masih ada semua + jar baru
JARNAME=$(grep -oE 'khanza-[A-Za-z0-9._-]*\.jar' "$DEST/Aplikasi.bat")
[ -f "$DEST/$JARNAME" ] && echo "OK bat -> $JARNAME valid" || echo "GAGAL: target tidak ada"
ls "$DEST/report/"               # cek report baru sudah ada
```

## Aturan
- ⛔ **JANGAN hapus/timpa jar lama** — hanya menambah jar baru. Jar lama = rollback.
- Selalu backup `Aplikasi.bat` sebelum diubah.
- `Aplikasi.bat` isinya satu baris: `java -jar -Xss2m -Xms32m -Xmx1024m -XX:PermSize=32m -XX:MaxPermSize=4072m <jar>`. Hanya nama jar di akhir yang diganti.
- Jar repo dibuat oleh skill `khanza-deploy-release` (bump versi + inject + nama Khanza-<ver>.jar).

## Riwayat
Pertama dipakai: deploy `khanza-16.06.2026-25.jar` (hotfix NPE Tanggal.setMinDate), Aplikasi.bat diarahkan dari -22 ke -25.
