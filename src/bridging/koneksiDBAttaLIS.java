package bridging;

import AESsecurity.EnkripsiAES;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;
import javax.swing.JOptionPane;

public class koneksiDBAttaLIS {
    private static Connection connection = null;
    private static final Properties prop = new Properties();
    private static final MysqlDataSource dataSource = new MysqlDataSource();

    public koneksiDBAttaLIS() {}

    public static Connection condb() {
        if (connection == null) {
            try {
                prop.loadFromXML(new FileInputStream("setting/database.xml"));
                dataSource.setURL("jdbc:mysql://"
                        + EnkripsiAES.decrypt(prop.getProperty("HOSTATTALIS")) + ":"
                        + EnkripsiAES.decrypt(prop.getProperty("PORTATTALIS")) + "/"
                        + EnkripsiAES.decrypt(prop.getProperty("DATABASEATTALIS"))
                        + "?zeroDateTimeBehavior=convertToNull&autoReconnect=true&useCompression=true");
                dataSource.setUser(EnkripsiAES.decrypt(prop.getProperty("USERATTALIS")));
                dataSource.setPassword(EnkripsiAES.decrypt(prop.getProperty("PASSATTALIS")));
                dataSource.setCachePreparedStatements(true);
                dataSource.setUseCompression(true);
                dataSource.setUseLocalSessionState(true);
                dataSource.setUseLocalTransactionState(true);
                connection = dataSource.getConnection();
                System.out.println("  Koneksi Berhasil. Menyambungkan ke database Atta-LIS...!!!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server Atta-LIS terputus : " + e);
            }
        }
        return connection;
    }
}
