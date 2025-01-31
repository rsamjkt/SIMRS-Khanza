package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import fungsi.sekuel;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author windiartonugroho
 */
public class ApiOrthanc {
    private HttpHeaders headers ;
    private JsonNode root;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private sekuel Sequel =new sekuel();
    private SSLContext sslContext;
    private SSLSocketFactory sslFactory;
    private Scheme scheme;
    private HttpComponentsClientHttpRequestFactory factory;
    private String auth,authEncrypt,requestJson;
    private SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");
    private byte[] encodedBytes;
    private int i=1;
    
    public ApiOrthanc(){
        try {
            auth=koneksiDB.USERORTHANC()+":"+koneksiDB.PASSORTHANC();
            encodedBytes = Base64.encodeBase64(auth.getBytes());
            authEncrypt= new String(encodedBytes);
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }
    
    public String Auth(){
        return authEncrypt;
    }
    
    public JsonNode AmbilSeries(String Norm,String Tanggal1,String Tanggal2){
        System.out.println("Percobaan Mengambil Photo Pasien : "+Norm);
        try{
            headers = new HttpHeaders();
            System.out.println("Auth : "+authEncrypt);
            headers.add("Authorization", "Basic "+authEncrypt);
            requestJson = "{"+
                              "\"Level\": \"Study\","+
                              "\"Expand\": true,"+
                              "\"Query\": {"+
                                   "\"StudyDate\": \""+Tanggal1+"-"+Tanggal2+"\","+
                                   "\"PatientID\": \""+Norm+"\""+
                              "}"+
                          "}";
            System.out.println("Request JSON : "+requestJson);
            requestEntity = new HttpEntity(requestJson,headers);
            System.out.println("URL : "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/tools/find");
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/tools/find", HttpMethod.POST, requestEntity, String.class).getBody();
            System.out.println("Result JSON : "+requestJson);
            root = mapper.readTree(requestJson);
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
            JOptionPane.showMessageDialog(null,"Gagal mengambil data dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    
    public JsonNode AmbilPng(String NoRawat,String Series,String norawatslash){
        System.out.println("Percobaan Mengambil Gambar PNG : "+NoRawat+", Series : "+Series);
        try{
            headers = new HttpHeaders();
            System.out.println("Auth : "+authEncrypt);
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series, HttpMethod.GET, requestEntity, String.class).getBody();
            System.out.println("Result JSON : "+requestJson);
            root = mapper.readTree(requestJson);
            i=1;
            for(JsonNode list:root.path("Instances")){
                 System.out.println("Mengambil Gambar PNG "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview");
                 headers = new HttpHeaders();
                 headers.add("Authorization", "Basic "+authEncrypt);
                 headers.add("Accept","image/png");
                 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                 headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                 HttpEntity<String> entity = new HttpEntity<>(headers);
                 ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview", HttpMethod.GET, entity, byte[].class);
                 Files.write(Paths.get("./gambarradiologi/"+NoRawat+i+".png"),response.getBody());
                 
                 uploadImage(NoRawat+i+".png","pages/upload");
                    Sequel.menyimpantf("gambar_radiologi","?,?,?,?","No.Rawat",4,new String[]{
                        norawatslash,tanggalNow.format(new Date()),jamNow.format(new Date()),"pages/upload/"+NoRawat+i+".png"
                    });
                 i++;
            }
            JOptionPane.showMessageDialog(null,"Pengambilan Gambar PNG dari Orthanc berhasil, silahkan lihat di dalam folder Aplikasi..!!");
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
            JOptionPane.showMessageDialog(null,"Gagal mengambil Gambar PNG dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    
    public JsonNode AmbilJpg(String NoRawat,String Series){
        System.out.println("Percobaan Mengambil Gambar JPG : "+NoRawat+", Series : "+Series);
        try{
            headers = new HttpHeaders();
            System.out.println("Auth : "+authEncrypt);
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series, HttpMethod.GET, requestEntity, String.class).getBody();
            System.out.println("Result JSON : "+requestJson);
            root = mapper.readTree(requestJson);
            i=1;
            for(JsonNode list:root.path("Instances")){
                 System.out.println("Mengambil Gambar JPG "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview");
                 headers = new HttpHeaders();
                 headers.add("Authorization", "Basic "+authEncrypt);
                 headers.add("Accept","image/jpeg");
                 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                 headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                 HttpEntity<String> entity = new HttpEntity<>(headers);
                 ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview", HttpMethod.GET, entity, byte[].class);
                 Files.write(Paths.get("./gambarradiologi/"+NoRawat+i+".jpg"),response.getBody());
                 i++;
            }
            JOptionPane.showMessageDialog(null,"Pengambilan Gambar JPG dari Orthanc berhasil, silahkan lihat di dalam folder Aplikasi..!!");
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
            JOptionPane.showMessageDialog(null,"Gagal mengambil Gambar JPG dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    
    public JsonNode AmbilBmp(String NoRawat,String Series){
        System.out.println("Percobaan Mengambil Gambar BMP : "+NoRawat+", Series : "+Series);
        try{
            headers = new HttpHeaders();
            System.out.println("Auth : "+authEncrypt);
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series, HttpMethod.GET, requestEntity, String.class).getBody();
            System.out.println("Result JSON : "+requestJson);
            root = mapper.readTree(requestJson);
            i=1;
            for(JsonNode list:root.path("Instances")){
                 System.out.println("Mengambil Gambar BMP "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview");
                 headers = new HttpHeaders();
                 headers.add("Authorization", "Basic "+authEncrypt);
                 headers.add("Accept","image/bmp");
                 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                 headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                 HttpEntity<String> entity = new HttpEntity<>(headers);
                 ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview", HttpMethod.GET, entity, byte[].class);
                 Files.write(Paths.get("./gambarradiologi/"+NoRawat+i+".bmp"),response.getBody());
                 i++;
            }
            JOptionPane.showMessageDialog(null,"Pengambilan Gambar BMP dari Orthanc berhasil, silahkan lihat di dalam folder Aplikasi..!!");
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
            JOptionPane.showMessageDialog(null,"Gagal mengambil Gambar BMP dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    
    public JsonNode AmbilDcm(String NoRawat,String Series){
        System.out.println("Percobaan Mengambil Gambar DCM : "+NoRawat+", Series : "+Series);
        try{
            headers = new HttpHeaders();
            System.out.println("Auth : "+authEncrypt);
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series, HttpMethod.GET, requestEntity, String.class).getBody();
            System.out.println("Result JSON : "+requestJson);
            root = mapper.readTree(requestJson);
            i=1;
            for(JsonNode list:root.path("Instances")){
                 System.out.println("Mengambil Gambar DCM "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/file");
                 headers = new HttpHeaders();
                 headers.add("Authorization", "Basic "+authEncrypt);
                 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                 headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                 HttpEntity<String> entity = new HttpEntity<>(headers);
                 ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/file", HttpMethod.GET, entity, byte[].class);
                 Files.write(Paths.get("./gambarradiologi/"+NoRawat+i+".dcm"),response.getBody());
                 i++;
            }
            JOptionPane.showMessageDialog(null,"Pengambilan Gambar DCM dari Orthanc berhasil, silahkan lihat di dalam folder Aplikasi..!!");
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
            JOptionPane.showMessageDialog(null,"Gagal mengambil Gambar DCM dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    
    public RestTemplate getRest() throws NoSuchAlgorithmException, KeyManagementException {
        sslContext = SSLContext.getInstance("SSL");
        TrustManager[] trustManagers= {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {return null;}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
            }
        };
        sslContext.init(null,trustManagers , new SecureRandom());
        sslFactory=new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        scheme=new Scheme("https",443,sslFactory);
        factory=new HttpComponentsClientHttpRequestFactory();
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        return new RestTemplate(factory);
    }
    
void uploadImage(String fileName, String docPath) {
    // Ganti dengan kredensial dan info Cloudflare R2 Anda
    final String CLOUDFLARE_R2_ACCESS_KEY = koneksiDB.CLOUDFLARER2ACCESSKEY();
    final String CLOUDFLARE_R2_SECRET_KEY = koneksiDB.CLOUDFLARER2SECRETKEY();
    final String CLOUDFLARE_R2_ACCOUNT_ID = koneksiDB.CLOUDFLARER2ACCOUNTID(); 
    final String BUCKET_NAME = koneksiDB.CLOUDFLAREBUCKETNAME();
    // Region di Cloudflare R2 umumnya "auto" atau "us-east-1". 
    // Boleh pakai "auto", tapi beberapa integrasi kadang perlu "us-east-1".
    final String REGION = koneksiDB.CLOUDFLAREREGION(); 
    // Contoh: "docPath" = "pages/upload"

    try {
        // Baca file lokal yg mau di-upload
        File file = new File("gambarradiologi/" + fileName);
        byte[] fileBytes = FileUtils.readFileToByteArray(file);

        // ----- 1. Siapkan parameter waktu -----
        // x-amz-date format: "yyyyMMdd'T'HHmmss'Z'"
        // dateStamp format: "yyyyMMdd" (untuk pembuatan signing key)
        SimpleDateFormat amzDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        amzDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        String xAmzDate = amzDateFormat.format(new Date());

        SimpleDateFormat dateStampFormat = new SimpleDateFormat("yyyyMMdd");
        dateStampFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        String dateStamp = dateStampFormat.format(new Date());

        // ----- 2. Tentukan host & URL object -----
        // Format host R2: <BUCKET_NAME>.<ACCOUNT_ID>.r2.cloudflarestorage.com
        String host = BUCKET_NAME + "." + CLOUDFLARE_R2_ACCOUNT_ID + ".r2.cloudflarestorage.com";
        // Lokasi penyimpanan object di dalam bucket, misal: /pages/upload/NamaFile.png
        String canonicalUri = "/" + docPath + "/" + fileName;
        // Endpoint lengkap
        String endpoint = "https://" + host + canonicalUri;

        // ----- 3. Hitung payload hash (SHA-256 atas body) -----
        String payloadHash = sha256Hex(fileBytes);

        // ----- 4. Bangun Canonical Request -----
        // Contoh HEADERS minimal: host, x-amz-content-sha256, x-amz-date
        String canonicalHeaders = 
                "host:" + host + "\n" +
                "x-amz-content-sha256:" + payloadHash + "\n" +
                "x-amz-date:" + xAmzDate + "\n";

        // SignedHeaders: daftar header yang ikut ditanda tangani
        String signedHeaders = "host;x-amz-content-sha256;x-amz-date";

        // Metode = PUT, QueryString = "" (kosong), lalu payloadHash di bagian terakhir
        String canonicalRequest = 
                "PUT\n" + 
                canonicalUri + "\n" + 
                "" + "\n" + 
                canonicalHeaders + "\n" + 
                signedHeaders + "\n" +
                payloadHash;

        // ----- 5. String to Sign -----
        String algorithm = "AWS4-HMAC-SHA256";
        String credentialScope = dateStamp + "/" + REGION + "/s3/aws4_request";
        String hashCanonicalRequest = sha256Hex(canonicalRequest.getBytes("UTF-8"));
        String stringToSign = 
                algorithm + "\n" +
                xAmzDate + "\n" +
                credentialScope + "\n" +
                hashCanonicalRequest;

        // ----- 6. Hitung Signature -----
        byte[] signingKey = getSignatureKey(CLOUDFLARE_R2_SECRET_KEY, dateStamp, REGION, "s3");
        String signature = bytesToHex(hmacSHA256(stringToSign, signingKey));

        // ----- 7. Bangun Header Authorization -----
        String authorizationHeader = 
                algorithm + " " +
                "Credential=" + CLOUDFLARE_R2_ACCESS_KEY + "/" + credentialScope + ", " +
                "SignedHeaders=" + signedHeaders + ", " +
                "Signature=" + signature;

        // ----- 8. Lakukan koneksi HTTP (PUT) -----
        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setUseCaches(false);

        // Set header
        conn.setRequestProperty("Host", host);
        conn.setRequestProperty("x-amz-date", xAmzDate);
        conn.setRequestProperty("x-amz-content-sha256", payloadHash);
        // Jika ingin objek bisa diakses publik:
        conn.setRequestProperty("x-amz-acl", "public-read");
        // Konten MIME type (contoh PNG, bisa diganti sesuai fileName)
        conn.setRequestProperty("Content-Type", "image/png");
        // Header Authorization
        conn.setRequestProperty("Authorization", authorizationHeader);

        // Tulis data file ke output stream
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(fileBytes);
            wr.flush();
        }

        // ----- 9. Cek response -----
        int responseCode = conn.getResponseCode();
        if (responseCode >= 200 && responseCode < 300) {
            System.out.println("File berhasil diupload ke Cloudflare R2: " + fileName);
            // Jika ingin menghapus file lokal setelah upload
            deleteFile();
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getErrorStream() != null ? conn.getErrorStream() : conn.getInputStream()
            ));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Upload Cloudflare R2 gagal. Kode: " + responseCode + 
                               ", Respon: " + response.toString());
        }

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Terjadi error upload ke R2: " + e.getMessage());
    }
}

private String calculateHmacSHA1(String data, String key) throws Exception {
    SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
    Mac mac = Mac.getInstance("HmacSHA1");
    mac.init(signingKey);
    byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
    return Base64.encodeBase64String(rawHmac);
}
    // Add this method for creating authentication header
private String createAuthHeader(String stringToSign, String dateStamp, String secretKey) {
    try {
        byte[] kSecret = ("AWS4" + secretKey).getBytes("UTF-8");
        byte[] kDate = hmacSHA256(dateStamp, kSecret);
        byte[] kRegion = hmacSHA256("auto", kDate);
        byte[] kService = hmacSHA256("s3", kRegion);
        byte[] kSigning = hmacSHA256("aws4_request", kService);
        return bytesToHex(hmacSHA256(stringToSign, kSigning));
    } catch (Exception e) {
        return "";
    }
}

// Menghasilkan hash SHA-256 (hex string) dari sebuah byte[]
private String sha256Hex(byte[] data) throws Exception {
    java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
    byte[] digest = md.digest(data);
    return bytesToHex(digest);
}

// HMAC-SHA256 untuk data & key
private byte[] hmacSHA256(String data, byte[] key) throws Exception {
    String algorithm = "HmacSHA256";
    Mac mac = Mac.getInstance(algorithm);
    mac.init(new SecretKeySpec(key, algorithm));
    return mac.doFinal(data.getBytes("UTF-8"));
}

// Konversi bytes ke format heksadesimal
private String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
        sb.append(String.format("%02x", b));
    }
    return sb.toString();
}

// Proses pembentukan key turunan (signature key) untuk V4
private byte[] getSignatureKey(String key, String dateStamp, String regionName, String serviceName) throws Exception {
    byte[] kSecret = ("AWS4" + key).getBytes("UTF-8");
    byte[] kDate = hmacSHA256(dateStamp, kSecret);
    byte[] kRegion = hmacSHA256(regionName, kDate);
    byte[] kService = hmacSHA256(serviceName, kRegion);
    return hmacSHA256("aws4_request", kService);
}

    
    void deleteFile() {
        File file = new File("gambarradiologi");
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }
    } 
}
