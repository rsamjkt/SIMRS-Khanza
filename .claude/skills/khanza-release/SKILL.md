---
name: khanza-release
description: Proses rilis penuh SIMRS Khanza — bump versi, build jar, commit, push, tag, deploy Synology. Gunakan saat user minta "buat release", "rilis", "release", "push release". WAJIB pakai identitas git rsamjkt dan TANPA co-author Claude.
---

# Khanza — Rilis Penuh

Orkestrasi rilis dari awal sampai deploy. Rangkai skill lain: `khanza-deploy-release` (build jar), `khanza-deploy-synology` (deploy).

## 🚫 ATURAN GIT MUTLAK (jangan dilanggar)
- **Author commit = `rsamjkt` / `randy@rsanggrekmas.com`.** Pastikan sekali:
  ```bash
  git config user.name "rsamjkt"
  git config user.email "randy@rsanggrekmas.com"
  ```
- **JANGAN PERNAH** menambahkan trailer `Co-Authored-By: Claude ...` di pesan commit. Pesan commit bersih, tanpa atribusi AI. (Berlaku untuk SEMUA commit di repo ini, bukan cuma rilis.)
- Repo GitHub: `rsamjkt/Khanza-26-Jan2025`, branch `main`.

## Langkah rilis
1. **Bump versi label** di `src/simrskhanza/frmUtama.java` (`jLabel7`): `V.DD.MM.YYYY-build`. Tanggal = hari rilis, build naik terus (lihat versi terakhir).
2. **Update CHANGELOG.md** — tambah entri rilis baru (Ditambah/Diubah/Diperbaiki + catatan DB/deploy).
3. **Build jar** (kalau ada perubahan kode): compile `--release 8` ke `build/classes` lalu inject ke `dist/Khanza-<versi>.jar` (lihat `khanza-deploy-release` & `khanza-build-doctor` utk classpath). Untuk docs-only, jar tidak perlu di-rebuild.
4. **Commit** (identitas rsamjkt, TANPA co-author):
   ```bash
   git add -A
   git commit -m "<judul>

   <detail opsional>"
   ```
5. **Push**: `git push origin main`.
6. **Tag** pola tanggal `vYYYY.MM.DD`:
   ```bash
   git tag -a vYYYY.MM.DD -m "Release vYYYY.MM.DD (V.DD.MM.YYYY-build) - <ringkas>"
   git push origin vYYYY.MM.DD
   ```
   - Kalau tag tanggal itu sudah ada (rilis ke-2 di hari sama): geser `git tag -f ... && git push origin <tag> --force`, ATAU pakai sufiks huruf `vYYYY.MM.DDb` (ada presedennya).
7. **Deploy Synology** (kalau diminta): jalankan skill `khanza-deploy-synology` — copy `khanza-<versi>.jar` + update `Aplikasi.bat` & `Aplikasi.command`, jar lama jangan dihapus.

## Verifikasi
```bash
git log -1 --pretty="%an <%ae>"          # harus rsamjkt <randy@rsanggrekmas.com>
git log -1 --pretty="%b" | grep -i "co-authored" && echo "BAHAYA: ada co-author!" || echo "OK bersih"
[ "$(git rev-parse HEAD)" = "$(git ls-remote origin refs/heads/main | cut -f1)" ] && echo "main sinkron"
```

## Catatan
- Repo TIDAK pakai GitHub Release object (gh belum login); rilis = git tag.
- Jar didistribusi via Synology/updater, bukan dilampirkan ke GitHub.
- Lihat memory `git-commit-identity` & `khanza-release-process`.
