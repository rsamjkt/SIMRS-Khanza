<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $namars       = getOne("select setting.nama_instansi from setting");
    $nosurat      = "";
    $norawat      = "";
    
    $_sql         = "select * from antripersetujuanumum";  
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
               where reg_periksa.no_rawat='".$norawat."'";  
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
               surat_persetujuan_umum.no_telp from surat_persetujuan_umum where surat_persetujuan_umum.no_surat='$nosurat'";  
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
<html>
<head>
    <title>Persetujuan Umum</title>
    <script src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/signature.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style type="text/css">
        body {
            background-color: #f0f8f8; /* Nuansa background yang lembut */
            font-family: Arial, sans-serif;
        }
        .container {
            background-color: white;
            padding: 30px;
            margin-top: 50px;
            margin-bottom: 50px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }
        h5, h7 {
            color: #006666;
        }
        .btn-tosca {
            background-color: #00b3b3;
            color: white;
            border: none;
        }
        .btn-tosca:hover {
            background-color: #008080;
            color: white;
        }
        table {
            background-color: #f9f9f9;
            border-radius: 8px;
            overflow: hidden;
        }
        th {
            background-color: #00b3b3;
            color: white;
            text-align: center;
            padding: 10px;
        }
        td {
            background-color: #ffffff;
            padding: 10px;
        }
        .form-control {
            border-radius: 5px;
        }
        .signature-container {
            border: 2px solid #00b3b3;
            padding: 10px;
            background-color: #f9f9f9;
            border-radius: 5px;
            margin-top: 10px;
        }
        #results {
            background-color: #e0ffff;
            width: 100%;
            height: auto;
            border: 2px solid #00b3b3;
        }
        .signature-pad {
            border: 2px solid #00b3b3;
            border-radius: 5px;
        }
        @media (max-width: 768px) {
            .container {
                padding: 20px;
                margin-top: 20px;
                margin-bottom: 20px;
            }
            h5, h7 {
                font-size: 1.1rem;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h5 class="text-center">
            <button class="btn btn-tosca mb-3" onclick="window.location.reload();">Refresh</button><br>
            PERSETUJUAN UMUM/GENERAL CONSENT<br>
            Pasien/keluarga diberikan penjelasan dan menyetujui sesuai dengan penjelasan :<br>
            NO. <?=$nosurat;?>
        </h5>
        <h7 class="text-center d-block mb-4">Tanggal <?=$tanggal;?></h7>

        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype="multipart/form-data">
            <input type="hidden" name="nosurat" value="<?=$nosurat;?>">
            <input type="hidden" name="no_rawat" value="<?=$norawat;?>">
            <input type="hidden" name="no_rkm_medis" value="<?=$no_rkm_medis;?>">
            <input type="hidden" name="nama_pj" value="<?=$nama_pj;?>">
            <input type="hidden" name="umur_pj" value="<?=$umur_pj;?>">
            <input type="hidden" name="no_ktppj" value="<?=$no_ktppj;?>">
            <input type="hidden" name="jkpj" value="<?=$jkpj;?>">
            <input type="hidden" name="bertindak_atas" value="<?=$bertindak_atas;?>">
            <input type="hidden" name="no_telp" value="<?=$no_telp;?>">

            <table class="table table-bordered mt-4">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Materi Penjelasan</th>
                        <th>Isi Materi</th>
                        <th>Tanda (√)</th>
                    </tr>
                </thead>
                <tbody>
                    <?php
                    $materiPenjelasan = [
                        "PERSETUJUAN UNTUK PENGOBATAN" => "
                        <ul>
                        <li>Saya memberikan persetujuan dan kuasa kepada RS Anggrek Mas untuk memberikan informasi tentang diagnosis, hasil pelayanan kesehatan dan pengobatan kepada <select name='pengobatan_kepada'><option value='Suami'>Suami</option><option value='Istri'>Istri</option><option value='Anak'>Anak</option><option value='Ayah'>Ayah</option><option value='Ibu'>Ibu</option><option value='Saudara'>Saudara</option><option value='Keponakan'>Keponakan</option><option value='Adik'>Adik</option><option value='Kakak'>Kakak</option><option value='Orang Tua'>Orang Tua</option><option value='Diri Sendiri'>Diri Sendiri</option></select></li>
                            <li>Mengizinkan dokter dan profesional kesehatan lainnya untuk melakukan prosedur diagnostik dan untuk memberikan pengobatan medis seperti yang diperlukan dalam penilaian profesional mereka.</li>
                            <li>Prosedur diagnostik dan perawatan medis termasuk tetapi tidak terbatas pada electrocardiograms, x-ray, tes darah, terapi fisik, dan pemberian obat.</li>
                        </ul>",
                        "PELEPASAN INFORMASI" => "
                        <ul>
                            <li>Memahami informasi yang ada didalam diri pasien/keluarga, termasuk Diagnosis, hasil laboratorium, dan hasil tes diagnostik yang akan di gunakan untuk perawatan medis, Rumah Sakit akan menjamin kerahasiannya.</li>
                            <li>Memberi wewenang kepada Rumah Sakit untuk memberikan informasi tentang diagnosis, hasil pelayanan, dan pengobatan bila diperlukan untuk memperoleh klaim asuransi/perusahaan dan atau lembaga pemerintah.</li>
                            <li>
                                <select name='pelepasan_informasi' class='form-control'>
                                    <option value='Ya'>Ya, Memberikan</option>
                                    <option value='Tidak'>Tidak, Memberikan</option>
                                </select> 
                                wewenang kepada Rumah Sakit untuk memberikan informasi tentang diagnosis, hasil pelayanan, dan pengobatan saya kepada anggota keluarga/teman saya.
                            </li>
                            <li><input name='keluarga' type='text' class='form-control' placeholder='Masukkan Nama Keluarga/Teman'></li>
                            <li><input hidden name='persetujuan_pengobatan' type='text' class='form-control' value='Ya'></li>
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
                            <li>
                                <select name='privacy' class='form-control'>
                                    <option value='Mengijinkan'>Mengijinkan</option>
                                    <option value='Tidak Mengijinkan'>Tidak Mengijinkan</option>
                                </select> 
                                Rumah Sakit memberi akses bagi keluarga dan handai taulan serta orang yang akan menemui/menjenguk saya.
                            </li>
                            <li>Nama: <input type='text' name='nama_privacy' class='form-control' placeholder='Masukkan Nama'></li>
                            <li>Profesi: <input type='text' name='profesi' class='form-control' placeholder='Masukkan Profesi'></li>
                        </ul>",
                        "BIAYA PELAYANAN RUMAH SAKIT" => "
                        <ul>
                            <li>Saya telah dijelaskan tentang biaya sesuai dengan perencanaan:</li>
                            <li>Kelas perawatan <input type='text' name='kelas_rawat' class='form-control' placeholder='Masukan Kelas'><input hidden type='text' name='nilai_kepercayaan' class='form-control' value='tidak ada'></li>
                        </ul>"
                    ];

                    $no = 1;
                    $ceklistNames = [
                        '', '', '', '', '', '', '', ''
                    ];

                    foreach ($materiPenjelasan as $title => $content) {
                        echo "<tr>";
                        echo "<td style='text-align: center;'>$no</td>";
                        echo "<td><strong>$title</strong></td>";
                        echo "<td>$content</td>";
                        echo "<td style='text-align: center;'><input type='checkbox' name='{$ceklistNames[$no - 1]}' id='{$ceklistNames[$no - 1]}' value='Ya'></td>";
                        echo "</tr>";
                        $no++;
                    }
                    ?>
                </tbody>
            </table>
            
            <br/>

            <table class="table table-bordered">
                <tr class="text-dark">
                    <td colspan='2'>Saya yang bertanda tangan dibawah ini :</td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nama</td>
                    <td width="75%">: <?=$nama_pj;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Umur</td>
                    <td width="75%">: <?=$umur_pj;?> &nbsp;&nbsp; Jenis Kelamin : <?=$jkpj;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nomor Identitas</td>
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
                    <td colspan='2'>&nbsp;</td>
                </tr>
                <tr class="text-dark">
                    <td colspan='2'>Dari pasien <?=$namars?> dengan :</td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nama Pasien</td>
                    <td width="75%">: <?=$nm_pasien;?></td>
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
                Bahwa saya telah membaca, memahami dan mendapatkan penjelasan tentang ketentuan yang berlaku di <?=$namars?>. Demikian pernyataan ini dibuat dalam keadaan penuh kesadaran dan tanpa paksaan dari pihak manapun, untuk digunakan sebagaimana mestinya.
            </h7>
            <br/><br/>
            <h7 class="text-dark text-center">Yang Membuat Pernyataan</h7>
            
            <div class="row">
                <div id="signature-pad" class="mx-auto">
                    <div style="border:solid 1px teal; width:390px;height:150px;padding:3px;position:relative;">
                        <div id="note" onmouseover="my_function();">The signature should be inside box</div>
                        <canvas id="the_canvas" width="400px" height="150px"></canvas>
                    </div>
                    <br>
                    <div style="margin:10px;" class="text-center">
                        <input type="hidden" id="signature" name="signature">
                        <button type="button" id="clear_btn" class="btn btn-danger" data-action="clear"><span class="glyphicon glyphicon-remove"></span> Hapus Tanda Tangan</button>
                        <button type="submit" id="save_btn" class="btn btn-tosca" data-action="save-png"><span class="glyphicon glyphicon-ok"></span> Ya, Saya Sebagai Pembuat Pernyataan</button>
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

    <script language="JavaScript">
        // Inisialisasi Webcam
        Webcam.set({
            width: 490,
            height: 390,
            image_format: 'jpeg',
            jpeg_quality: 90
        });

        Webcam.attach( '#my_camera' );

        function take_snapshot() {
            Webcam.snap( function(data_uri) {
                $(".image-tag").val(data_uri);
                document.getElementById('results').innerHTML = '<img src="'+data_uri+'"/>';
            } );
        }

        // Fungsi Validasi Formulir
        function validasiIsi() {
            var ceklistNames = [
                'persetujuan_pengobatan', 
                'pelepasan_informasi', 
                'barang_berharga', 
                'hak_pasien_keluarga', 
                'pemeliharaan_fasilitas', 
                'keamanan', 
                'privacy', 
                'biaya_pelayanan'
            ];

            for (var i = 0; i < ceklistNames.length; i++) {
                var checkbox = document.getElementById(ceklistNames[i]);
                if (!checkbox.checked) {
                    alert("Harap centang semua persetujuan sebelum menyimpan.");
                    return false;
                }
            }

            return true;
        }
    </script>
</body>
</html>

