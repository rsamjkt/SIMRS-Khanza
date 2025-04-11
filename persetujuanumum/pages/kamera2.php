<?php
if(strpos($_SERVER['REQUEST_URI'], "pages")){
    exit(header("Location:../index.php"));
}

$namars       = getOne("select setting.nama_instansi from setting");
$nosurat      = "";
$norawat      = "";

$_sql         = "select * from antripersetujuanumum" ;  
$hasil        = bukaquery2($_sql);
while ($data = mysqli_fetch_array ($hasil)){
    $nosurat  = $data['no_surat'];
    $norawat  = $data['no_rawat'];
}

$no_rkm_medis = "";
$nm_pasien    = "";
$jk           = "";
$umur         = "";
$tgl_lahir    = "";
$alamat       = "";
$no_tlp       = "";

$_sql2  = "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','LAKI-LAKI','PEREMPUAN') as jk,
           pasien.umur,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as tgl_lahir,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, 
           pasien.no_tlp from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
           inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel
           inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec 
           inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab
           where reg_periksa.no_rawat='".$norawat."'" ;  
$hasil2 = bukaquery2($_sql2);
while ($data2  = mysqli_fetch_array ($hasil2)){
    $no_rkm_medis = $data2['no_rkm_medis'];
    $nm_pasien    = $data2['nm_pasien'];
    $jk           = $data2['jk'];
    $umur         = $data2['umur'];
    $tgl_lahir    = $data2['tgl_lahir'];
    $alamat       = $data2['alamat'];
    $no_tlp       = $data2['no_tlp'];
}

$tanggal        = "";
$nama_pj        = "";
$umur_pj        = "";
$no_ktppj       = "";
$jkpj           = "";
$bertindak_atas = "";
$no_telp        = "";
$_sql2  = "select DATE_FORMAT(surat_persetujuan_umum.tanggal,'%d-%m-%Y') as tanggal,surat_persetujuan_umum.nama_pj,surat_persetujuan_umum.umur_pj,
           surat_persetujuan_umum.no_ktppj,if(surat_persetujuan_umum.jkpj='L','LAKI-LAKI','PEREMPUAN') as jkpj,surat_persetujuan_umum.bertindak_atas,
           surat_persetujuan_umum.no_telp from surat_persetujuan_umum where surat_persetujuan_umum.no_surat='$nosurat'" ;  
$hasil2 = bukaquery2($_sql2);
while ($data2  = mysqli_fetch_array ($hasil2)){
    $tanggal        = $data2['tanggal'];
    $nama_pj        = $data2['nama_pj'];
    $umur_pj        = $data2['umur_pj'];
    $no_ktppj       = $data2['no_ktppj'];
    $jkpj           = $data2['jkpj'];
    $bertindak_atas = $data2['bertindak_atas'];
    $no_telp        = $data2['no_telp'];
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>General Consent RS Anggrek Mas</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <script src="js/jquery.min.js"></script>
   
    <script type="text/javascript" src="js/signature.js"></script>
   
    <!-- Include SignaturePad Library -->
    <style>
        #results { padding: 0; background: #EEFFEE; width: 490px; height: 390px }
        .container { margin-top: 20px; }
        .text-dark { color: #000; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid black; padding: 8px; }
        .signature-pad {
            border: 1px solid teal;
            width: 100%;
            max-width: 600px;
            height: 200px;
            margin: 0 auto;
        }
        .signature-pad canvas {
            width: 100%;
            height: 100%;
        }
        td input[type='checkbox'] { margin-left: 10px; }
        .signature-pad--footer {
            text-align: center;
            margin-top: 10px;
        }
        .signature-pad--footer .button {
            margin: 5px;
        }
        .button {
            background-color: #008CBA; /* Blue */
            border: none;
            color: white;
            padding: 7px 14px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            cursor: pointer;
        }
        .button.clear {
            background-color: #f44336; /* Red */
        }
        .button.undo {
            background-color: #e7e7e7; /* Gray */
            color: black;
        }
        .description {
            font-size: 12px;
            color: #777;
        }
    </style>
</head>
<body>
    <div class="container">
        <h5 class="text-center text-dark">
            <button class="btn btn-secondary" onclick="window.location.reload();">Refresh</button><br/><br/>
            PERSETUJUAN UMUM/GENERAL CONSENT<br>
            Pasien/keluarga diberikan penjelasan dan menyetujui sesuai dengan penjelasan :<br>
            NO. <?=$nosurat;?>
        </h5>
        <h7 class="text-center text-dark">Tanggal <?=$tanggal;?></h7>
        <br/>
        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype="multipart/form-data">
            <input type="hidden" name="nosurat" value="<?=$nosurat;?>">
            <!-- Hidden input to store signature data -->
            <input type="hidden" name="signature" id="signature">
            <table>
                <thead>
                    <tr>
                        <th>NO</th>
                        <th>MATERI PENJELASAN</th>
                        <th>ISI MATERI</th>
                        <th>TANDA (√)</th>
                    </tr>
                </thead>
                <tbody>
                    <?php
                    $materiPenjelasan = [
                        "PERSETUJUAN UNTUK PENGOBATAN" => "
                        <ul>
                            <li>Mengizinkan dokter dan profesional kesehatan lainnya untuk melakukan prosedur diagnostik dan untuk memberikan pengobatan medis seperti yang diperlukan dalam penilaian profesional mereka.</li>
                            <li>Prosedur diagnostik dan perawatan medis termasuk tetapi tidak terbatas pada electrocardiograms, x-ray, tes darah, terapi fisik, dan pemberian obat.</li>
                        </ul>",
                        "PELEPASAN INFORMASI" => "
                        <ul>
                            <li>Memahami informasi yang ada didalam diri pasien/keluarga, termasuk Diagnosis, hasil laboratorium, dan hasil tes diagnostik yang akan di gunakan untuk perawatan medis, Rumah Sakit akan menjamin kerahasiannya.</li>
                            <li>Memberi wewenang kepada Rumah Sakit untuk memberikan informasi tentang diagnosis, hasil pelayanan, dan pengobatan bila diperlukan untuk memperoleh klaim asuransi/perusahaan dan atau lembaga pemerintah.</li>
                            <li><select name='izin_pelepasan_informasi' class='form-control'>
                                <option value='Ya'>Ya, Memberikan</option>
                                <option value='Tidak'>Tidak, Memberikan</option>
                            </select> wewenang kepada Rumah Sakit untuk memberikan informasi tentang diagnosis, hasil pelayanan, dan pengobatan saya kepada anggota keluarga/teman saya.</li>
                            <li><input name='keluarga_teman1' type='text' id='TxtIsi1' size='40' maxlength='50' pattern='[a-zA-Z0-9, ./@_-]{1,50}' title=' a-zA-Z0-9, ./@_- (Maksimal 50 karakter)' autocomplete='off'></li>
                            <li><input name='keluarga_teman2' type='text' id='TxtIsi2' size='40' maxlength='50' pattern='[a-zA-Z0-9, ./@_-]{1,50}' title=' a-zA-Z0-9, ./@_- (Maksimal 50 karakter)' autocomplete='off'></li>
                        </ul>",
                        "BARANG BERHARGA MILIK PRIBADI" => "
                        <ul>
                            <li>Memahami bahwa Rumah Sakit tidak bertanggung jawab atas semua kehilangan barang-barang milik saya dan saya secara pribadi bertanggung jawab atas barang-barang berharga yang saya miliki termasuk namun tidak terbatas pada uang, perhiasan, buku cek, kartu kredit, handphone atau barang lainnya.</li>
                            <li>Dan apabila saya membutuhkan maka saya dapat menitipkan barang saya kepada rumah sakit.</li>
                            <li>Saya juga mengerti bahwa saya harus memberitahukan/menitipkan pada Rumah Sakit jika saya memiliki gigi palsu, kacamata, lensa kontak, prosthetics atau barang lainnya yang saya butuhkan untuk diamankan.</li>
                        </ul>",
                        "HAK PASIEN DAN KELUARGA" => "
                        <ul>
                            <li>Pasien/keluarga memiliki hak untuk mengajukan pertanyaan tentang pengobatan yang diusulkan (termasuk identitas setiap orang yang memberikan atau mengamati pengobatan) setiap saat.</li>
                            <li>Mengerti dan memahami bahwa memiliki hak untuk persetujuan, atau menolak persetujuan, untuk setiap prosedur/terapi.</li>
                            <li>Jika diperlukan, saya akan berpartisipasi dalam pemilihan dokter yang akan bertanggung jawab untuk perawatan saya selama berada di rumah sakit.</li>
                            <li>Menegerti dan memahami tentang HAK PASIEN DAN KELUARGA sesuai dengan Undang - undang Kesehatan no. 44 tentang Rumah Sakit.</li>
                        </ul>",
                        "PEMELIHARAAN FASILITAS RUMAH SAKIT" => "
                        <ul>
                            <li>Penjelasan jika terjadi kerusakan atau kehilangan yang disebabkan oleh pasien maka menjadi tanggung jawab pasien termasuk fasilitas umum dan fasilitas/alat medis.</li>
                        </ul>",
                        "KEAMANAN" => "
                        <ul>
                            <li>Pihak keluarga pasien berkunjung didalam jam besuk tetapi jika diluar jam besuk harus lapor dan menukarkan kartu identitas dengan kartu tamu.</li>
                        </ul>",
                        "KEINGINAN PRIVACY" => "
                        <ul>
                            <li><select name='privacy'>
                                    <option value='Mengijinkan'>Mengijinkan</option>
                                    <option value='Tidak Mengijinkan'>Tidak Mengijinkan</option>
                                </select> Rumah Sakit memberi akses bagi keluarga dan handai taulan serta orang yang akan menemui/menjenguk saya.</li>
                            <li>Nama: <input type='text' name='nama_akses_privacy' class='form-control' placeholder='Masukkan Nama'></li>
                            <li>Profesi: <input type='text' name='profesi_akses_privacy' class='form-control' placeholder='Masukkan Profesi'></li>
                        </ul>",
                        "BIAYA PELAYANAN RUMAH SAKIT" => "
                        <ul>
                            <li>Saya telah dijelaskan tentang biaya sesuai dengan perencanaan:</li>
                            <li>Kelas perawatan <input type='text' name='kelas_perawatan' class='form-control' placeholder='Masukan Kelas'></li>
                             <input hidden type='text' name='nilai_kepercayaan' class='form-control' value='Tidak Ada' placeholder='Masukan Kelas'></li>
                             <input hidden type='text' name='pengobatan_kepada' class='form-control' value='pasien' placeholder='Masukan Kelas'></li>

                        </ul>"
                    ];

                    $no = 1;
                    $ceklistNames = [
                        'persetujuan_pengobatan', 
                        'pelepasan_informasi', 
                        'barang_berharga', 
                        'hak_pasien_keluarga', 
                        'pemeliharaan_fasilitas', 
                        'keamanan', 
                        'cek_privacy', 
                        'biaya_pelayanan'
                    ];

                    foreach ($materiPenjelasan as $title => $content) {
                        echo "<tr>";
                        echo "<td style='text-align: center;'>$no</td>";
                        echo "<td>$title</td>";
                        echo "<td>$content</td>";
                        echo "<td style='text-align: center;'><input type='checkbox' name='{$ceklistNames[$no - 1]}' value='Ya'></td>";
                        echo "</tr>";
                        $no++;
                    }
                    ?>
                </tbody>
            </table>
            
            <br/>

            <table class="default" width="98%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="100%" colspan='2'>Saya yang bertanda tangan dibawah ini :</td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nama</td>
                    <td width="70%">: <?=$nama_pj;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Umur</td>
                    <td width="75%">: <?=$umur_pj." &nbsp;&nbsp;Jenis Kelamin : ".$jkpj;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nomor Identitas :</td>
                    <td width="75%">: <?=$no_ktppj;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nomor Telp/Nomor HP</td>
                    <td width="75%">: <?=$no_telp;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Bertindak untuk dan atas nama</td>
                    <td width="75%">: <?=$bertindak_atas;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan='2'>&nbsp;</td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan='2'>Dari pasien <?=$namars?> dengan : </td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nama Pasien</td>
                    <td width="70%">: <?=$nm_pasien;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nomor Rekam Medis</td>
                    <td width="75%">: <?=$no_rkm_medis;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Jenis Kelamin</td>
                    <td width="75%">: <?=$jk;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Tanggal Lahir</td>
                    <td width="75%">: <?=$tgl_lahir;?></td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark">
                Dengan ini menyatakan bahwa saya/ keluarga pasien telah menerima informasi sebagaimana diatas yang saya beri tanda/paraf di kolom sebelah kanan serta telah diberi kesempatan untuk bertanya/berdiskusi dan telah memahami
            </h7>
            <br/>
            <br/>
            <h7 class="text-dark"><center>Yang Membuat Pernyataan</center></h7>
            <div class="row">
                <div id="signature-pad">
        <div style="border:solid 1px teal; width:390px;height:150px;padding:3px;position:relative;">
            <div id="note" onmouseover="my_function();">The signature should be inside box</div>
            <canvas id="the_canvas" width="400px" height="150px"></canvas>
           
        </div>
        <br>
        <div style="margin:10px;">
            <input type="hidden" id="signature" name="signature">
            <button type="button" id="clear_btn" class="btn btn-danger" data-action="clear"><span class="glyphicon glyphicon-remove"></span> Hapus Tanda Tangan</button>
            <button type="submit" id="save_btn" class="btn btn-primary" data-action="save-png"><span class="glyphicon glyphicon-ok"></span> Ya, Saya Sebagai Pembuat Pernyataan</button>
        </div>
    </div>

                
            </div>
        </form>
    </div>
   

    <script>
var wrapper = document.getElementById("signature-pad");
var clearButton = wrapper.querySelector("[data-action=clear]");
var savePNGButton = wrapper.querySelector("[data-action=save-png]");
var canvas = wrapper.querySelector("canvas");
var el_note = document.getElementById("note");
var signaturePad;
signaturePad = new SignaturePad(canvas);

clearButton.addEventListener("click", function (event) {
    document.getElementById("note").innerHTML="The signature should be inside box";
    signaturePad.clear();
});
savePNGButton.addEventListener("click", function (event){
    if (signaturePad.isEmpty()){
        alert("Please provide signature first.");
        event.preventDefault();
    }else{
        var canvas  = document.getElementById("the_canvas");
        var dataUrl = canvas.toDataURL();
        document.getElementById("signature").value = dataUrl;
    }
});
function my_function(){
    document.getElementById("note").innerHTML="";
}
</script>
</body>
</html>
