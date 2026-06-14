---
name: khanza-bridging
description: Pola integrasi eksternal di SIMRS Khanza (BPJS VClaim/Antrean, SatuSehat, PCare, Apotek BPJS, dukcapil). Gunakan saat menambah/men-debug bridging, signature/header API BPJS, baca config terenkripsi, atau "kirim/ambil data ke BPJS/SatuSehat".
---

# Khanza — Bridging / Integrasi

Modul `src/bridging/` (~262 file). Config di `setting/database.xml` (sebagian AES-encrypted, dibaca via getter `koneksiDB`).

## Config (getter di koneksiDB)
- BPJS VClaim: `URLAPIBPJS`, `CONSIDAPIBPJS`, `SECRETKEYAPIBPJS`, `USERKEYAPIBPJS`.
- SatuSehat: `CLIENTIDSATUSEHAT`, `SECRETKEYSATUSEHAT`, `URLAUTHSATUSEHAT`, `URLFHIRSATUSEHAT`, `IDSATUSEHAT`.
- PCare: `URLAPIPCARE`, `CONSIDAPIPCARE`, `SECRETKEYAPIPCARE`, `USERKEYAPIPCARE`, `USERPCARE`, `PASSPCARE`.
- Apotek BPJS, iCare, Aplicare, Smart Klaim, Mobile JKN, Orthanc, dukcapil — pola getter serupa (lihat koneksiDB.java).
Nilai sensitif disimpan ter-AES (`AESsecurity.EnkripsiAES.decrypt`), URL/flag plain.

## Header BPJS (pola: timestamp + HMAC-SHA256 + decrypt response)
```
X-cons-id   : <CONSID>
X-timestamp : <epoch detik UTC> (System.currentTimeMillis()/1000)
X-signature : Base64( HmacSHA256( CONSID + "&" + timestamp , SECRETKEY ) )
user_key    : <USERKEY> (di query/header tergantung endpoint)
```
Response BPJS ter-enkripsi → decrypt pakai key `SHA-256(CONSID + SECRETKEY + timestamp)` + LZString decompress (lihat `ApiBPJSEnc`, `ApiBPJSLZString`, `ApiBPJSAesKeySpec`).

## SatuSehat (OAuth2 + FHIR)
- Ambil token: POST `URLAUTHSATUSEHAT` (client_credentials, CLIENTID/SECRET) → bearer token.
- Kirim resource FHIR (Encounter, Condition, Observation, dll) ke `URLFHIRSATUSEHAT`. Lihat `bridging/SatuSehat*`.

## HTTP & JSON
- HttpClient (apache) / Spring `HttpHeaders`+`HttpEntity`/`RestTemplate`. Parse JSON: Jackson `ObjectMapper` / `response.path("field").asText()`.
- **Cek status aktif BPJS**: gunakan `.startsWith("AKTIF")` BUKAN `.equals("AKTIF")` (status bisa "AKTIF (xxx)") — bugfix yang sudah diterapkan.

## Catatan
- File bridging umumnya BUKAN kustomisasi RS (kode integrasi standar) → relatif aman di-update dari upstream bila ada perbaikan API (lihat skill `khanza-upstream-reconcile`). Pengecualian: cek dulu kalau ada konfig/kode khusus RS.
- Smart Klaim FHIR (`SmartKlaimBPJSKirimFHIR`) sudah di-update ke versi upstream terbaru di v2026.06.14.
- Sub-app service terpisah di root: `KhanzaHMSServiceSatuSehat`, `KhanzaHMSServicePCare`, `KhanzaHMSServiceMobileJKN*`, dll.
