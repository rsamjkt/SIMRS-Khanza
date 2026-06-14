---
name: khanza-deploy-release
description: Build, deploy & rilis SIMRS Khanza (bump versi, jar, sinkron report, SQL, git tag). Gunakan saat user minta build/deploy/rilis, "naikkan versi", "push & release", atau menyiapkan paket update untuk RS.
---

# Khanza — Deploy & Release

## 1. Bump label versi (WAJIB tiap rilis)
`src/simrskhanza/frmUtama.java` baris ~7456:
```java
jLabel7.setText("Randy Mandala - V.DD.MM.YYYY-build");
```
Tanggal = tanggal rilis; build = counter terus naik (mis. ...-20 → -21). Lihat memory `version-label-bump`.

## 2. Compile / rebuild
- Project NetBeans ant (main.class `simrskhanza.SIMRSKhanza`, target Java 1.8). `ant` tidak di PATH; bisa via `dist/lib/ant-*.jar` atau NetBeans.
- **Surgical** (1-2 file): compile ke `build/classes` lalu inject ke jar:
  ```
  javac --release 8 -encoding UTF-8 -cp "<CP>" -d build/classes <File.java>
  jar uf dist/<nama>.jar -C build/classes <paket>/<File>.class <paket>/<File>$*.class
  ```
  CP: exclude `*-src.jar`, taruh `commons-codec-1.12.jar` di depan (lihat memory `khanza-build-compile`).
- Class .class harus **major 52 (Java 8)** — JRE produksi bisa Java 8.

## 3. Report (di-load dari folder eksternal, BUKAN jar)
Sinkron .jasper/.jrxml ke **3 folder**: `report/`, `dist/report/`, `build/report/`.

## 4. Aset & config
- Icon (`/picture/`, `/48x48/`), webapp PHP (`webapps/`), folder runtime (mis. `gambarradiologi/`) ikut ter-deploy.
- Config terenkripsi/plain di `setting/database.xml` (Orthanc, BPJS, Smart Klaim, R2, dll) — user yang isi.

## 5. Database
Kumpulkan semua `CREATE TABLE` / `ALTER TABLE` perubahan rilis ke satu script SQL; **user yang jalankan di server** SEBELUM deploy build baru (kalau kode baca kolom yang belum ada → error login/runtime).

## 6. Git & rilis
```
git add -A && git commit -m "..."        # commit per unit logis
git push origin main
git tag -a vYYYY.MM.DD -m "<catatan>"     # pola tanggal; geser bila perlu: git tag -f ... + push --force
git push origin vYYYY.MM.DD
```
Repo TIDAK pakai GitHub Release object; jar didistribusi terpisah (KhanzaUpdater / Cloudflare R2 `Update-S3-r2`). Lihat memory `khanza-release-process`.

## Checklist rilis
- [ ] Label versi di-bump
- [ ] Compile bersih Java 8
- [ ] Report disebar 3 folder
- [ ] Script SQL disiapkan untuk user
- [ ] Commit + push + tag
