package view;

import database.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class UserManager extends JFrame {

    JTable table;
    DefaultTableModel model;

    JTextField txtUsername;
    JTextField txtPassword;
    JTextField txtFullname;

    public UserManager(){

        setTitle("Quản lý người dùng");
        setSize(900,600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color pinkHeader = new Color(255,105,180);
        Color blueSoft = new Color(210,235,255);
        Color pinkSoft = new Color(255,230,240);

        // ===== TITLE =====
        JLabel title = new JLabel("QUẢN LÝ NGƯỜI DÙNG",SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI",Font.BOLD,26));
        title.setOpaque(true);
        title.setBackground(pinkHeader);
        title.setForeground(Color.WHITE);
        title.setPreferredSize(new Dimension(900,70));

        add(title,BorderLayout.NORTH);

        // ===== CENTER PANEL =====
        JPanel centerPanel = new JPanel(new GridLayout(2,1));

        add(centerPanel,BorderLayout.CENTER);

        // ================= FORM PANEL (40%) =================
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(pinkSoft);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        txtUsername = new JTextField(20);
        txtPassword = new JTextField(20);
        txtFullname = new JTextField(20);

        gbc.gridx=0; gbc.gridy=0;
        formPanel.add(new JLabel("Username:"),gbc);

        gbc.gridx=1;
        formPanel.add(txtUsername,gbc);

        gbc.gridx=0; gbc.gridy=1;
        formPanel.add(new JLabel("Password:"),gbc);

        gbc.gridx=1;
        formPanel.add(txtPassword,gbc);

        gbc.gridx=0; gbc.gridy=2;
        formPanel.add(new JLabel("Họ tên:"),gbc);

        gbc.gridx=1;
        formPanel.add(txtFullname,gbc);

        // ===== BUTTON =====
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(pinkSoft);

        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnRefresh = new JButton("Làm mới");

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnRefresh);

        gbc.gridx=0;
        gbc.gridy=3;
        gbc.gridwidth=2;

        formPanel.add(btnPanel,gbc);

        centerPanel.add(formPanel);

        // ================= TABLE PANEL (60%) =================
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(blueSoft);

        model = new DefaultTableModel(
                new Object[]{"ID","Username","Password","Họ tên"},0
        );

        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI",Font.PLAIN,14));

        JScrollPane scroll = new JScrollPane(table);

        tablePanel.add(scroll,BorderLayout.CENTER);

        centerPanel.add(tablePanel);

        loadData();

        // ===== EVENT =====
        btnAdd.addActionListener(e -> addUser());
        btnUpdate.addActionListener(e -> updateUser());
        btnDelete.addActionListener(e -> deleteUser());
        btnRefresh.addActionListener(e -> loadData());

        table.getSelectionModel().addListSelectionListener(e -> showData());
    }

    // ===== LOAD DATA =====
    void loadData(){

        model.setRowCount(0);

        try{

            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM nguoidung";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("ten_dang_nhap"),
                        rs.getString("mat_khau"),
                        rs.getString("ho_ten")
                });

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // ===== ADD =====
    void addUser(){

        try{

            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO nguoidung(ten_dang_nhap,mat_khau,ho_ten) VALUES(?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,txtUsername.getText());
            ps.setString(2,txtPassword.getText());
            ps.setString(3,txtFullname.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"Thêm người dùng thành công");

            loadData();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // ===== UPDATE =====
    void updateUser(){

        int row = table.getSelectedRow();

        if(row==-1) return;

        int id = (int) model.getValueAt(row,0);

        try{

            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE nguoidung SET ten_dang_nhap=?,mat_khau=?,ho_ten=? WHERE id=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,txtUsername.getText());
            ps.setString(2,txtPassword.getText());
            ps.setString(3,txtFullname.getText());
            ps.setInt(4,id);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"Cập nhật thành công");

            loadData();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // ===== DELETE =====
    void deleteUser(){

        int row = table.getSelectedRow();

        if(row==-1) return;

        int id = (int) model.getValueAt(row,0);

        try{

            Connection conn = DBConnection.getConnection();

            String sql = "DELETE FROM nguoidung WHERE id=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,id);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"Xóa thành công");

            loadData();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // ===== SHOW DATA =====
    void showData(){

        int row = table.getSelectedRow();

        if(row==-1) return;

        Object username = model.getValueAt(row,1);
        Object password = model.getValueAt(row,2);
        Object fullname = model.getValueAt(row,3);

        txtUsername.setText(username == null ? "" : username.toString());
        txtPassword.setText(password == null ? "" : password.toString());
        txtFullname.setText(fullname == null ? "" : fullname.toString());

    }

}