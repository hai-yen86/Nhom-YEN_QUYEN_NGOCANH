package view;

import javax.swing.*;
import java.awt.*;
import FoodManager.FoodManager;

import view.OrderManager;
import view.ThongKeForm;

public class MainMenu extends JFrame {

    JPanel sidebar;
    JPanel header;
    JPanel content;

    public MainMenu(){

        setTitle("Hệ thống quản lý bán đồ ăn vặt");
        setSize(1100,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(200,650));
        sidebar.setBackground(new Color(255,140,40));
        sidebar.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));

        JButton btnHome = new JButton("Trang chủ");
        JButton btnFood = new JButton("Quản lý món ăn");
        btnFood.addActionListener(e -> {

    content.removeAll();

    content.add(new FoodManager(),BorderLayout.CENTER);

    content.revalidate();
    content.repaint();

});
        JButton btnOrder = new JButton("Quản lý đơn hàng");
        btnOrder.addActionListener(e -> {

    new OrderManager().setVisible(true);

});
        JButton btnUser = new JButton("Quản lý người dùng");
       btnUser.addActionListener(e -> {

    new UserManager().setVisible(true);

});
       JButton btnThongKe = new JButton("Thống kê");

btnThongKe.addActionListener(e -> {
    new ThongKeForm().setVisible(true);
});

        JButton btnLogout = new JButton("Đăng xuất");

        styleMenu(btnHome);
        styleMenu(btnFood);
        styleMenu(btnUser);
        styleMenu(btnOrder);
        styleMenu(btnThongKe);
        styleMenu(btnLogout);
        
        // sự kiện mở quản lý món ăn
btnFood.addActionListener(e -> {

    new FoodManager().setVisible(true);

});
        
        sidebar.add(btnHome);
        sidebar.add(btnFood);
        sidebar.add(btnUser);
        sidebar.add(btnOrder);
        sidebar.add(btnThongKe);
        sidebar.add(btnLogout);

        // ===== HEADER =====
        header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(1000,70));
        header.setLayout(new BorderLayout());

        // LOGO
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/CTECH-logo-file-goc-01-Copy.png"));
        Image img = icon.getImage().getScaledInstance(45,45,Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(img));

        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        leftHeader.setBackground(Color.WHITE);

        JLabel title = new JLabel("HỆ THỐNG QUẢN LÝ BÁN ĐỒ ĂN VẶT");
        title.setFont(new Font("Segoe UI",Font.BOLD,20));
        title.setForeground(new Color(255,120,0));

        leftHeader.add(logo);
        leftHeader.add(title);

        JButton btnLogoutTop = new JButton("Đăng xuất");
        btnLogoutTop.setBackground(new Color(255,150,50));
        btnLogoutTop.setForeground(Color.WHITE);
        btnLogoutTop.setFocusPainted(false);

        header.add(leftHeader,BorderLayout.WEST);
        header.add(btnLogoutTop,BorderLayout.EAST);

        // ===== CONTENT =====
        content = new JPanel();
        content.setBackground(new Color(250,240,220));
        content.setLayout(new BorderLayout());

        JLabel welcome = new JLabel("Chào mừng đến hệ thống quản lý",SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI",Font.BOLD,24));
content.add(welcome,BorderLayout.CENTER);

        // ===== ADD =====
        add(sidebar,BorderLayout.WEST);
        add(header,BorderLayout.NORTH);
        add(content,BorderLayout.CENTER);

        // ===== LOGOUT =====
        btnLogout.addActionListener(e->{
            new LoginForm().setVisible(true);
            dispose();
        });

        btnLogoutTop.addActionListener(e->{
            new LoginForm().setVisible(true);
            dispose();
        });

    }

    private void styleMenu(JButton btn){

        btn.setPreferredSize(new Dimension(160,40));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(255,170,70));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI",Font.BOLD,14));

    }

}