package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderDetail extends JFrame {

    JTable table;
    DefaultTableModel model;
    JLabel lblTotal;

    int soLuong;
    int tongTien;
    String tenMon;

    public OrderDetail(int orderId, String tenMon, int soLuong, int tongTien){

        this.tenMon = tenMon;
        this.soLuong = soLuong;
        this.tongTien = tongTien;

        setTitle("Chi tiết đơn hàng");
        setSize(720,500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color pink = new Color(255,230,240);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(pink);
        add(mainPanel);

        // ===== TITLE =====
        JLabel title = new JLabel("🍜 CHI TIẾT ĐƠN HÀNG - ID: " + orderId,SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI",Font.BOLD,22));
        title.setForeground(new Color(255,70,120));
        title.setBorder(BorderFactory.createEmptyBorder(15,10,10,10));

        mainPanel.add(title,BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel();

        model.addColumn("ID món");
        model.addColumn("Tên món");
        model.addColumn("Số lượng");
        model.addColumn("Đơn giá");
        model.addColumn("Thành tiền");

        table = new JTable(model);

        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI",Font.PLAIN,14));

        table.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,15));
        table.getTableHeader().setBackground(new Color(255,150,190));

        JScrollPane scroll = new JScrollPane(table);

        mainPanel.add(scroll,BorderLayout.CENTER);

        // ===== BOTTOM =====
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(pink);
        bottom.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));

        lblTotal = new JLabel("Tổng tiền: " + tongTien + " VNĐ");
        lblTotal.setFont(new Font("Segoe UI",Font.BOLD,18));
        lblTotal.setForeground(new Color(200,0,90));

        bottom.add(lblTotal,BorderLayout.WEST);

        // panel chứa nút
        JPanel right = new JPanel();
        right.setBackground(pink);

        // ===== NÚT THANH TOÁN =====
        JButton btnPay = new JButton("Thanh toán");
        btnPay.setFont(new Font("Segoe UI",Font.BOLD,14));
        btnPay.setBackground(new Color(0,170,90));
        btnPay.setForeground(Color.white);
        btnPay.setFocusPainted(false);

        // sự kiện thanh toán
btnPay.addActionListener(e -> {

    JOptionPane.showMessageDialog(this,"Thanh toán thành công!");

    showBill(); // mở hóa đơn

        });

        // ===== NÚT ĐÓNG =====
        JButton btnClose = new JButton("Đóng");
        btnClose.setFont(new Font("Segoe UI",Font.BOLD,14));
        btnClose.setBackground(new Color(255,90,130));
        btnClose.setForeground(Color.white);
        btnClose.setFocusPainted(false);

        btnClose.addActionListener(e -> dispose());

        right.add(btnPay);
        right.add(btnClose);

        bottom.add(right,BorderLayout.EAST);

        mainPanel.add(bottom,BorderLayout.SOUTH);

        // ===== HIỂN THỊ MÓN =====
        int donGia = tongTien / soLuong;

        addFood(1, tenMon, soLuong, donGia);

    }

    void addFood(int id,String name,int qty,int price){

        int total = qty*price;

        model.addRow(new Object[]{
                id,name,qty,price,total
        });

    }

    // ===== HÀM HIỂN BILL =====
    void showBill(){

    JDialog billDialog = new JDialog(this,"Hóa đơn thanh toán",true);
    billDialog.setSize(500,450);
    billDialog.setLocationRelativeTo(this);
    billDialog.setLayout(new BorderLayout());

    Color pink = new Color(255,230,240);

    // ===== TITLE =====
    JLabel title = new JLabel("🍜 HÓA ĐƠN THANH TOÁN",SwingConstants.CENTER);
    title.setFont(new Font("Segoe UI",Font.BOLD,22));
    title.setForeground(new Color(255,70,120));
    title.setBorder(BorderFactory.createEmptyBorder(15,10,10,10));

    billDialog.add(title,BorderLayout.NORTH);

    // ===== LẤY THỜI GIAN =====
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    String time = now.format(formatter);

    // ===== MÃ ĐƠN HÀNG =====
    int orderCode = (int)(Math.random()*9000)+1000;

    // ===== TABLE BILL =====
    String[] column = {"Món ăn","Số lượng","Đơn giá","Thành tiền"};

    int donGia = tongTien/soLuong;

    Object[][] data = {
            {tenMon,soLuong,donGia,tongTien}
    };

    JTable billTable = new JTable(data,column);

    billTable.setRowHeight(35);
    billTable.setFont(new Font("Segoe UI",Font.PLAIN,14));
    billTable.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,14));
    billTable.getTableHeader().setBackground(new Color(255,150,190));

    JScrollPane scroll = new JScrollPane(billTable);

    // ===== INFO PANEL =====
    JPanel info = new JPanel(new GridLayout(3,1));
    info.setBackground(pink);
    info.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

    JLabel code = new JLabel("Mã đơn hàng: "+orderCode);
    JLabel date = new JLabel("Thời gian: "+time);
    JLabel total = new JLabel("Tổng tiền: "+tongTien+" VNĐ");

    code.setFont(new Font("Segoe UI",Font.BOLD,14));
    date.setFont(new Font("Segoe UI",Font.PLAIN,14));
    total.setFont(new Font("Segoe UI",Font.BOLD,16));

    total.setForeground(new Color(200,0,90));

    info.add(code);
    info.add(date);
    info.add(total);

    JPanel center = new JPanel(new BorderLayout());
    center.add(info,BorderLayout.NORTH);
    center.add(scroll,BorderLayout.CENTER);

    billDialog.add(center,BorderLayout.CENTER);

    // ===== BUTTON =====
    JButton btnClose = new JButton("Đóng");
    btnClose.setFont(new Font("Segoe UI",Font.BOLD,14));
    btnClose.setBackground(new Color(255,90,130));
    btnClose.setForeground(Color.white);

    btnClose.addActionListener(e -> billDialog.dispose());

    JPanel bottom = new JPanel();
    bottom.setBackground(pink);
    bottom.add(btnClose);

    billDialog.add(bottom,BorderLayout.SOUTH);

    billDialog.setVisible(true);
}

    }
