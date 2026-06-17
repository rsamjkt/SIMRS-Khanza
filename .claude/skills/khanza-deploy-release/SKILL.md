---
name: khanza-deploy-release
description: Build, deploy & rilis SIMRS Khanza (bump versi, jar, sinkron report, SQL, git tag, GitHub Release). Gunakan saat user minta build/deploy/rilis, "naikkan versi", "push & release", atau menyiapkan paket update untuk RS.
---

# Khanza — Deploy & Release

## 1. Bump label versi (WAJIB tiap rilis)
`src/simrskhanza/frmUtama.java` baris ~7456:
```java
jLabel7.setText("Randy Mandala - V.DD.MM.YYYY-build (Codename)");
```
- Tanggal = tanggal rilis; build = counter terus naik (mis. ...-30 → -31)
- Codename opsional — kalau ada, format: `(NamaCodename)` di akhir
- Lihat memory `version-label-bump`

## 2. Compile / rebuild
- Project NetBeans ant (main.class `simrskhanza.SIMRSKhanza`, target Java 1.8). `ant` tidak di PATH; bisa via `dist/lib/ant-*.jar` atau NetBeans.
- **Surgical** (1-2 file): compile ke `build/classes` lalu inject ke jar:
  ```
  javac --release 8 -encoding UTF-8 -cp "<CP>" -d build/classes <File.java>
  jar uf dist/<nama>.jar -C build/classes <paket>/<File>.class <paket>/<File>$*.class
  ```
  CP: exclude `*-src.jar`, taruh `commons-codec-1.12.jar` + `jasperreports-6.8.0.jar` di depan (lihat memory `khanza-build-compile`).
- Class .class harus **major 52 (Java 8)** — JRE produksi bisa Java 8.

## 3. Report (di-load dari folder eksternal, BUKAN jar)
Sinkron .jasper/.jrxml ke **3 folder**: `report/`, `dist/report/`, `build/report/`.
Setelah commit, cek report yang berubah dan copy ke Synology:
```bash
git diff HEAD~1 HEAD --name-only -- 'report/*.jasper' | xargs -I{} cp {} /Users/randymandala/SynologyDrive/dist/dist/report/
```

## 4. Aset & config
- Icon (`/picture/`, `/48x48/`), webapp PHP (`webapps/`), folder runtime (mis. `gambarradiologi/`) ikut ter-deploy.
- Config terenkripsi/plain di `setting/database.xml` (Orthanc, BPJS, Smart Klaim, R2, dll) — user yang isi.

## 5. Database
Kumpulkan semua `CREATE TABLE` / `ALTER TABLE` perubahan rilis ke satu script SQL; **user yang jalankan di server** SEBELUM deploy build baru.

## 6. Git commit + tag
```bash
git add <files>
git -c user.name="rsamjkt" -c user.email="randy@rsanggrekmas.com" commit -m "..."
git push origin main
git tag vYYYY.MM.DD        # multi-rilis sehari: b, c, d
git push origin vYYYY.MM.DD
```

## 7. GitHub Release (WAJIB setelah tag)
```bash
export GH_TOKEN="<token>"

# Rilis biasa
gh release create vYYYY.MM.DD \
  --repo rsamjkt/Khanza-26-Jan2025 \
  --title "vYYYY.MM.DD · V.DD.MM.YYYY-build" \
  --latest \
  --notes "$(cat <<'EOF'
### Ditambah
- ...

### Diperbaiki
- ...

### Catatan DB
\`\`\`sql
ALTER TABLE ...
\`\`\`
EOF
)"

# Rilis dengan codename
gh release create vYYYY.MM.DD \
  --repo rsamjkt/Khanza-26-Jan2025 \
  --title "Codename — vYYYY.MM.DD · V.DD.MM.YYYY-build" \
  --latest \
  --notes "..."
```
- `--latest` hanya untuk rilis terbaru
- Suffix b/c/d → JANGAN `--latest` (kecuali memang yang terbaru)

## 8. Deploy Synology
```bash
SYNDIR="/Users/randymandala/SynologyDrive/dist/dist"
cp dist/Khanza-DD.MM.YYYY-build.jar "$SYNDIR/khanza-DD.MM.YYYY-build.jar"
sed -i '' 's/khanza-OLD.jar/khanza-NEW.jar/g' "$SYNDIR/Aplikasi.bat" "$SYNDIR/Aplikasi.command"
```

## Checklist rilis
- [ ] Label versi di-bump (+ codename kalau ada)
- [ ] Compile bersih Java 8
- [ ] Jar diupdate (termasuk inner classes `$*.class`)
- [ ] Report disinkron + copy ke Synology
- [ ] Script SQL disiapkan untuk user
- [ ] Commit + push + tag
- [ ] **GitHub Release dibuat** (`gh release create`)
- [ ] Launcher Synology diupdate ke jar baru
