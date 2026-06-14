---
name: khanza-jasper-report
description: Bikin/edit & recompile Jasper report di SIMRS Khanza dengan benar (bytecode Java 8, compilerClass, deploy 3 folder). Gunakan saat user minta tambah/ubah report, kolom di cetakan, "kenapa report X tidak muncul", recompile .jrxml/.jasper, atau error "Report compiler class not found"/UnsupportedClassVersionError.
---

# Khanza — Jasper Report

Report di SIMRS Khanza: ~1286 `.jasper` di folder `report/`. Di-load **runtime dari folder eksternal** (`./report/`, `./dist/report/`, `./build/report/`), BUKAN dari jar.

## Cara report dipanggil (2 pola)
- `Valid.MyReportqry("rptX.jasper","report","::[judul]::","<SQL>",param)` → datasource = **ResultSet SQL** (`JRResultSetDataSource`). Field di jrxml HARUS cocok nama kolom SQL. Kalau jrxml punya field yang tidak ada di SQL → error "Unknown column" / kosong.
- `Valid.MyReport("rptX.jasper",param,"::[judul]::")` → report pakai `<queryString>` internal-nya sendiri terhadap koneksi DB; data dari `$P{param}`.

## Menambah kolom ke report (mis. Tanggal Lahir)
1. **jrxml**: tambah `<field name="x" class="...">` + `<textField>` + `<staticText>` label. Geser elemen lain bila perlu (tambah tinggi band).
2. **Bila MyReportqry**: tambah kolom `x` ke SQL di file `.java` pemanggil (sinkron nama field).
3. **Recompile** jrxml → jasper (lihat bawah).
4. **Sebar** ke `report/`, `dist/report/`, `build/report/`.

## Recompile .jrxml → .jasper (KRITIS — harus benar)
JDK lokal = Java 15, tapi app jalan di JRE 8 → bytecode ekspresi WAJIB **major 52 (Java 8)**, dan `compilerClass` tersimpan harus kelas STANDAR (bukan subclass custom).

Compiler helper (subclass JRJavacCompiler, `--release 8`, lalu set compilerClass via refleksi):
```java
// CompileRpt8.java — jalankan dgn: javac/java -cp "<CP>" CompileRpt8 in.jrxml out.jasper
import net.sf.jasperreports.engine.design.*; import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader; import net.sf.jasperreports.engine.util.JRSaver;
import javax.tools.*; import java.io.*; import java.util.*;
public class CompileRpt8 {
  static class Javac8 extends JRJavacCompiler { Javac8(JasperReportsContext c){super(c);}
    @Override public String compileClasses(File[] src,String cp) throws JRException {
      JavaCompiler jc=ToolProvider.getSystemJavaCompiler(); StringWriter sw=new StringWriter();
      try(StandardJavaFileManager fm=jc.getStandardFileManager(null,null,null)){
        fm.setLocation(StandardLocation.CLASS_OUTPUT,Collections.singletonList(src[0].getParentFile()));
        boolean ok=jc.getTask(new PrintWriter(sw),fm,null,Arrays.asList("--release","8","-classpath",cp),
          null,fm.getJavaFileObjects(src)).call(); return ok?null:sw.toString(); }catch(Exception e){return ""+e;} } }
  public static void main(String[] a) throws Exception {
    JasperDesign jd=JRXmlLoader.load(a[0]);
    JRCompiler c=new Javac8(DefaultJasperReportsContext.getInstance());
    JasperReport jr=c.compileReport(jd);
    java.lang.reflect.Field f=net.sf.jasperreports.engine.base.JRBaseReport.class.getDeclaredField("compilerClass");
    f.setAccessible(true); f.set(jr,"net.sf.jasperreports.engine.design.JRJavacCompiler"); // WAJIB: kelas standar
    JRSaver.saveObject(jr,a[1]); System.out.println("OK "+a[1]); }
}
```
Classpath (CP): `build/classes` + `dist/lib/*.jar` TAPI **exclude `jasperreports-1.3.1.jar`** (konflik Spring) dan `*-src.jar`.

**Jebakan:**
- Kalau pakai subclass tanpa override compilerClass → app error "Report compiler class not found: CompileRpt8$Javac8".
- Kalau pakai compiler stok tanpa `--release 8` → bytecode Java 15 → `UnsupportedClassVersionError` di JRE 8.
- Verifikasi: major version 52 & field `tgl_lahir`/dll ada (load ulang `.jasper`).

## Cek
- `JRLoader.loadObjectFromFile(jasper)` harus sukses; cek `jr.getCompilerClass()` = JRJavacCompiler.
- Lihat memory `khanza-build-compile` untuk detail classpath.
