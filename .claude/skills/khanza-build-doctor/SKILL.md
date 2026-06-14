---
name: khanza-build-doctor
description: Diagnosa & atasi error compile/classpath di SIMRS Khanza (commons-codec Base64, *-src.jar, target Java 8, zsh). Gunakan saat ada error javac/compile, "cannot find symbol", UnsupportedClassVersionError, atau build gagal.
---

# Khanza — Build Doctor

JDK lokal = Liberica JDK 15; app target Java 1.8. Compile-check pakai `--release 8`.

## Template classpath yang benar
```bash
CP="build/classes"
for j in dist/lib/*.jar; do case "$j" in
  *-src.jar|*js_commons-codec-1.3.jar|*org-apache-commons-codec.jar|*jasperreports-1.3.1.jar) ;;
  *) CP="$CP:$j";; esac; done
CP="dist/lib/commons-codec-1.12.jar:$CP"          # codec baru paling depan
javac --release 8 -encoding UTF-8 -cp "$CP" -d /tmp/out "${files[@]}"
```

## Error umum & penyebab

### "cannot find symbol: method encodeBase64String" (class Base64)
- Ada **4 versi commons-codec** di dist/lib (1.3, 1.10, 1.12, shaded `org-apache-commons-codec`). `encodeBase64String` cuma ada di >=1.4.
- **Fix:** taruh `commons-codec-1.12.jar` di DEPAN classpath; exclude yang 1.3 & shaded. (Ini artefak classpath, BUKAN bug kode — versi lama pun error sama.)

### "cyclic inheritance involving JComponent" / "cannot find symbol JComponent"
- javac salah meng-compile SOURCE dari `calendar-src.jar`.
- **Fix:** exclude semua `*-src.jar` dari classpath.

### "Report compiler class not found: X$Y" (saat fill jasper)
- `.jasper` di-compile pakai compiler class non-standar. **Fix:** lihat skill `khanza-jasper-report` (set compilerClass = JRJavacCompiler via refleksi).

### "UnsupportedClassVersionError" (runtime)
- Bytecode > Java 8 (mis. major 59) jalan di JRE 8. **Fix:** compile `--release 8`; cek major version = 52.

### "cannot find symbol: requestFocus()/isDisplayable()" beruntun
- Biasanya CASCADE dari error classpath di atas (Swing hierarchy gagal resolve). Perbaiki akar (codec/src jar) dulu.

### Error Spring `createConcurrentMapIfPossible` (saat load jasper)
- Konflik `jasperreports-1.3.1.jar` lama. **Fix:** exclude dari classpath.

## Jebakan shell (zsh)
- zsh TIDAK word-split variabel unquoted. Pakai array `files=(a b c)` atau `${=VAR}`. `$SRC` berisi banyak path → javac anggap satu nama file.
- Proses-substitusi `<(...)` kadang kehilangan PATH (`tr`/`awk` not found) → pakai pipe biasa atau flag git (`--ignore-cr-at-eol`).
- `sik.sql`/file dump ada blob biner → set `LC_ALL=C` untuk awk/sed byte-safe.

## Verifikasi cepat major version
```bash
od -An -tu1 -j6 -N2 <Class>.class | awk '{print "major", $1*256+$2}'   # 52=Java8, 59=Java15
```
