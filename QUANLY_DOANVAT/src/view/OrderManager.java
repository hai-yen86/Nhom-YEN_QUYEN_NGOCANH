package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderManager extends JFrame {

    JTable table;
    DefaultTableModel model;

    JSpinner dateSpinner;

    JTextField txtTenMon;
    JTextField txtSoLuong;
    JTextField txtTongTien;

    JButton btnThem;
    JButton btnXoa;
    JButton btnLamMoi;

    public OrderManager(){

        setTitle("Quản lý đơn hàng");
        setSize(950,560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color pink = new Color(255,230,240);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10,10));
        mainPanel.setBackground(pink);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(mainPanel);

        // ===== TITLE =====
        JLabel title = new JLabel("🍜 QUẢN LÝ ĐƠN HÀNG",SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI",Font.BOLD,24));
        title.setForeground(new Color(255,80,120));

        mainPanel.add(title,BorderLayout.NORTH);

        // ===== CENTER =====
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(10,10));
        centerPanel.setBackground(pink);

        mainPanel.add(centerPanel,BorderLayout.CENTER);

        // ===== PANEL THÔNG TIN =====
        JPanel infoPanel = new JPanel(new GridLayout(4,2,10,10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin đơn hàng"));
        infoPanel.setBackground(pink);

        JLabel lbNgay = new JLabel("Ngày:");

        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner,"dd/MM/yyyy");
        dateSpinner.setEditor(editor);

        JLabel lblTenMon = new JLabel("Tên món:");
        txtTenMon = new JTextField();

        JLabel lblSoLuong = new JLabel("Số lượng:");
        txtSoLuong = new JTextField();

        JLabel lbTongTien = new JLabel("Tổng tiền:");
        txtTongTien = new JTextField();

        infoPanel.add(lbNgay);
        infoPanel.add(dateSpinner);

        infoPanel.add(lblTenMon);
        infoPanel.add(txtTenMon);

        infoPanel.add(lblSoLuong);
        infoPanel.add(txtSoLuong);

        infoPanel.add(lbTongTien);
        infoPanel.add(txtTongTien);

        centerPanel.add(infoPanel,BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Ngày");
        model.addColumn("Tên món");
        model.addColumn("Số lượng");
        model.addColumn("Tổng tiền");

        table = new JTable(model);

        // ===== CLICK VÀO TỔNG TIỀN MỞ CHI TIẾT =====
        table.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();

                if(row >= 0 && col == 4){

                    int id = Integer.parseInt(table.getValueAt(row,0).toString());
                    String tenMon = table.getValueAt(row,2).toString();
                    int soLuong = Integer.parseInt(table.getValueAt(row,3).toString());
                    int tongTien = Integer.parseInt(table.getValueAt(row,4).toString());

                    new OrderDetail(id,tenMon,soLuong,tongTien).setVisible(true);

                }

            }

        });

        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI",Font.PLAIN,14));

        table.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,14));
        table.getTableHeader().setBackground(new Color(255,170,200));

        JScrollPane scroll = new JScrollPane(table);

        centerPanel.add(scroll,BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(pink);

        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");

        styleButton(btnThem,new Color(70,190,120));
        styleButton(btnXoa,new Color(255,90,90));
        styleButton(btnLamMoi,new Color(90,140,230));

        btnPanel.add(btnThem);
        btnPanel.add(btnXoa);
        btnPanel.add(btnLamMoi);

        mainPanel.add(btnPanel,BorderLayout.SOUTH);

        // ===== EVENTS =====
        btnThem.addActionListener(e -> themDonHang());
        btnXoa.addActionListener(e -> xoaDonHang());
        btnLamMoi.addActionListener(e -> lamMoi());

    }

    void styleButton(JButton btn,Color color){

        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI",Font.BOLD,14));
        btn.setPreferredSize(new Dimension(120,40));

    }

    void themDonHang(){

    Date date = (Date) dateSpinner.getValue();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    String ngay = sdf.format(date);
    String tenMon = txtTenMon.getText();

    int soLuong = Integer.parseInt(txtSoLuong.getText());
    int donGia = Integer.parseInt(txtTongTien.getText());

    int tongTien = soLuong * donGia;

    int id = model.getRowCount()+1;

    model.addRow(new Object[]{
            id,
            ngay,
            tenMon,
            soLuong,
            tongTien
    });
    }

    void xoaDonHang(){

        int row = table.getSelectedRow();

        if(row >= 0){
            model.removeRow(row);
        }
        else{
            JOptionPane.showMessageDialog(this,"Chọn đơn hàng cần xóa");
        }

    }

    void lamMoi(){

        txtTenMon.setText("");
        txtSoLuong.setText("");
        txtTongTien.setText("");

    }

}