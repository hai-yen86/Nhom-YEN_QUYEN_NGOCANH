package view;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.DecimalFormat;

public class ThongKeForm extends JFrame {

    JLabel lblTongDon, lblDoanhThu, lblTongMon;
    JTable tableDoanhThu, tableMon;

    public ThongKeForm() {

        setTitle("Thống kê");
        setSize(950, 620);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🌈 nền xanh da trời nhạt
        getContentPane().setBackground(new Color(210, 235, 255));

        // ===== TITLE =====
        JLabel title = new JLabel("THỐNG KÊ DOANH THU", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBorder(new EmptyBorder(15, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // ===== MAIN =====
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(new Color(210, 235, 255));

        // ===== CARD PANEL =====
        JPanel cardPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        cardPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        cardPanel.setBackground(new Color(210, 235, 255));

        lblTongDon = createCard("🧾 Tổng đơn");
        lblDoanhThu = createCard("💰 Doanh thu");
        lblTongMon = createCard("🍟 Món bán");

        cardPanel.add(lblTongDon);
        cardPanel.add(lblDoanhThu);
        cardPanel.add(lblTongMon);

        // ===== TABLE PANEL =====
        JPanel tablePanel = new JPanel(new GridLayout(1, 2, 20, 20));
        tablePanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        tablePanel.setBackground(new Color(210, 235, 255));

        tableDoanhThu = new JTable();
        JScrollPane sp1 = new JScrollPane(tableDoanhThu);
        sp1.setBorder(BorderFactory.createTitledBorder("📅 Doanh thu theo ngày"));

        tableMon = new JTable();
        JScrollPane sp2 = new JScrollPane(tableMon);
        sp2.setBorder(BorderFactory.createTitledBorder("🍔 Món bán chạy"));

        tablePanel.add(sp1);
        tablePanel.add(sp2);

        main.add(cardPanel);
        main.add(tablePanel);

        add(main, BorderLayout.CENTER);

        loadData();
    }

    // ===== CARD UI =====
    private JLabel createCard(String text) {
        JLabel lb = new JLabel(text + "\n0", JLabel.CENTER);
        lb.setOpaque(true);
        lb.setBackground(Color.WHITE);
        lb.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lb.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        return lb;
    }

    // ===== FORMAT TIỀN =====
    private String formatTien(int tien) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(tien) + " đ";
    }

    // ===== LOAD DATA =====
    private void loadData() {
        try {
            Connection conn = DBConnection.getConnection();

            // ===== Tổng đơn =====
            ResultSet rs1 = conn.createStatement().executeQuery("SELECT COUNT(*) FROM donhang");
            if (rs1.next()) {
                lblTongDon.setText("🧾 Tổng đơn\n" + rs1.getInt(1));
            }

            // ===== Doanh thu =====
            ResultSet rs2 = conn.createStatement().executeQuery("SELECT SUM(tong_tien) FROM donhang");
            if (rs2.next()) {
                lblDoanhThu.setText("💰 Doanh thu\n" + formatTien(rs2.getInt(1)));
            }

            // ===== Tổng món =====
            ResultSet rs3 = conn.createStatement().executeQuery("SELECT SUM(so_luong) FROM donhang");
            if (rs3.next()) {
                lblTongMon.setText("🍟 Món bán\n" + rs3.getInt(1));
            }

            // ===== Doanh thu theo ngày =====
            DefaultTableModel m1 = new DefaultTableModel(new String[]{"Ngày", "Doanh thu"}, 0);
            ResultSet rs4 = conn.createStatement().executeQuery(
                    "SELECT ngay, SUM(tong_tien) FROM donhang GROUP BY ngay"
            );
            while (rs4.next()) {
                m1.addRow(new Object[]{
                        rs4.getString(1),
                        formatTien(rs4.getInt(2))
                });
            }
            tableDoanhThu.setModel(m1);

            // ===== Món bán chạy =====
            DefaultTableModel m2 = new DefaultTableModel(new String[]{"Tên món", "Số lượng"}, 0);
            ResultSet rs5 = conn.createStatement().executeQuery(
                    "SELECT ten_mon, SUM(so_luong) FROM donhang GROUP BY ten_mon ORDER BY SUM(so_luong) DESC"
            );
            while (rs5.next()) {
                m2.addRow(new Object[]{
                        rs5.getString(1),
                        rs5.getInt(2)
                });
            }
            tableMon.setModel(m2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}