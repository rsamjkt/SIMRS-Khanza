/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */
public class WarnaTableKasirRalan extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        // 1. Warna Default (Ganjil Genap)
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
            component.setForeground(new Color(50,50,50));
        }else{
            component.setBackground(new Color(255,255,255));
            component.setForeground(new Color(50,50,50));
        } 
        
        // 2. Logika Status Periksa 
        // (Index sekarang 13 karena urutan: 9.JnsBayar, 10.NoSEP, 11.PRB, 12.TTV, 13.Status)
        if(table.getValueAt(row,13).toString().equals("Sudah")){
            component.setBackground(new Color(200,0,0)); // Merah
            component.setForeground(new Color(255,230,230));
        }else if(table.getValueAt(row,13).toString().equals("Batal")){
            component.setBackground(new Color(255,243,109)); // Kuning
            component.setForeground(new Color(120,110,50));
        }else if(table.getValueAt(row,13).toString().equals("Dirujuk")||table.getValueAt(row,13).toString().equals("Meninggal")||table.getValueAt(row,13).toString().equals("Pulang Paksa")){
            component.setBackground(new Color(152,152,156)); // Abu-abu
            component.setForeground(new Color(245,245,255));
        }else if(table.getValueAt(row,13).toString().equals("Dirawat")){
            component.setBackground(new Color(119,221,119)); // Hijau Muda
            component.setForeground(new Color(245,255,245));
        }
        
        // 3. Logika Status Bayar (Index bergeser jadi 18)
        if(table.getValueAt(row,18).toString().equals("Sudah Bayar")){
            component.setBackground(new Color(50,50,50)); // Hitam/Gelap
            component.setForeground(new Color(255,255,255));
        }
        
        // 4. Warna Pink jika Assessment/TTV (Index 12) sudah diisi
        if(table.getValueAt(row,12).toString().equals("Sudah")){
            component.setBackground(Color.PINK);
            component.setForeground(Color.BLACK);
        }

        // 5. Warna Hijau jika ada SEP (Index 10)
        // PERBAIKAN UTAMA DISINI: Cek agar "Belum Ada SEP" TIDAK HIJAU
        if(table.getColumnCount() > 10 && table.getValueAt(row,10) != null){
             String noSep = table.getValueAt(row,10).toString();
             
             // Syarat Hijau: Tidak Kosong, Bukan Strip, Bukan X, DAN BUKAN "Belum Ada SEP"
             if(!noSep.equals("") && !noSep.equals("-") && !noSep.equals("X") && !noSep.equals("Belum Ada SEP")){
                component.setBackground(new Color(0, 166, 81)); // Hijau BPJS
                component.setForeground(new Color(255,255,255)); // Tulisan Putih
             }
        }
        
        // (Opsional) Jika kolom dipilih/diklik warnanya biar jelas (Biru default Java)
        if (isSelected) {
            component.setBackground(new Color(184, 207, 229));
            component.setForeground(new Color(51, 51, 51));
        }

        return component;
    }
}