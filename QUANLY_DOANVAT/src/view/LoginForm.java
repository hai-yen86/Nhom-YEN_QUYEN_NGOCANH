
package view;

import javax.swing.*;
import java.awt.*;

import java.sql.Connection;        // kết nối database
import java.sql.PreparedStatement; // dùng để insert dữ liệu

import database.DBConnection;      // gọi file kết nối MySQL

public class LoginForm extends JFrame {

    JTextField txtUser;
    JPasswordField txtPass;

    public LoginForm() {

        setTitle("Login System");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel background = new JPanel();
        background.setBackground(new Color(255,200,150));
        background.setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(350,250));
        panel.setBackground(Color.white);
        panel.setLayout(null);

        JLabel title = new JLabel("LOGIN SYSTEM");
        title.setFont(new Font("Segoe UI",Font.BOLD,22));
        title.setForeground(new Color(255,120,0));
        title.setBounds(90,20,200,30);

        JLabel lbUser = new JLabel("Username");
        lbUser.setBounds(40,80,100,25);

        txtUser = new JTextField();
        txtUser.setBounds(130,80,170,25);

        JLabel lbPass = new JLabel("Password");
        lbPass.setBounds(40,120,100,25);

        txtPass = new JPasswordField();
        txtPass.setBounds(130,120,170,25);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(255,150,0));
        btnLogin.setForeground(Color.white);
        btnLogin.setBounds(70,180,90,35);
        
        btnLogin.addActionListener(e -> {

    String user = txtUser.getText();
    String pass = new String(txtPass.getPassword());

    if(!user.isEmpty() && !pass.isEmpty()){

        try{

            // ===== KẾT NỐI DATABASE =====
            Connection con = DBConnection.getConnection();

            // ===== CÂU LỆNH SQL =====
            String sql = "INSERT INTO nguoidung(ten_dang_nhap,mat_khau) VALUES(?,?)";

            // ===== CHUẨN BỊ INSERT =====
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,user); // gán username
            ps.setString(2,pass); // gán password

            ps.executeUpdate(); // thực thi lưu vào database

            JOptionPane.showMessageDialog(this,"Đăng nhập thành công!");

            // ===== MỞ TRANG CHÍNH =====
            new MainMenu().setVisible(true);
            dispose();

        }catch(Exception ex){

            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,"Lỗi kết nối database");

        }

    }else{

        JOptionPane.showMessageDialog(this,"Vui lòng nhập tài khoản và mật khẩu");

    }

});

        JButton btnExit = new JButton("Exit");
        btnExit.setBackground(new Color(255,100,0));
        btnExit.setForeground(Color.white);
        btnExit.setBounds(190,180,90,35);

        btnExit.addActionListener(e -> System.exit(0));

        panel.add(title);
        panel.add(lbUser);
        panel.add(lbPass);
        panel.add(txtUser);
        panel.add(txtPass);
        panel.add(btnLogin);
        panel.add(btnExit);

        background.add(panel);

        add(background);

    }

    public static void main(String[] args) {
        new LoginForm().setVisible(true);
    }
}