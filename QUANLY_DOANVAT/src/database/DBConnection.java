package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        Connection conn = null;

        try {
            String url = "jdbc:mysql://localhost:3306/quanly_doanvat";
            String user = "root";
            String password = "";

            conn = DriverManager.getConnection(url, user, password);

            System.out.println("Kết nối MySQL thành công!");

        } catch (Exception e) {
            System.out.println("Lỗi kết nối!");
            e.printStackTrace();
        }

        return conn;
    }

}