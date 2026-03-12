package FoodManager;

import database.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FoodManager extends JPanel {

    JTable table;
    DefaultTableModel model;

    JTextField txtTenMon, txtGia, txtSoLuong, txtSearch;
    JComboBox<String> cbLoai;

    JLabel lblImageName;
    String selectedImage = "monan1.jpg";

    public FoodManager(){

        setLayout(new BorderLayout());
        setBackground(new Color(250,240,220));

        // ===== HEADER =====
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(240,220,190));

        JLabel lbSearch = new JLabel("Tìm kiếm món:");

        txtSearch = new JTextField(20);

        JButton btnSearch = new JButton("Tìm");
        btnSearch.setBackground(new Color(100,180,90));
        btnSearch.setForeground(Color.white);

        topPanel.add(lbSearch);
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);

        add(topPanel,BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new Object[]{"ID","Hình","Tên món","Giá","Số lượng","Loại"},0){

            @Override
            public Class getColumnClass(int column){
                if(column==1) return Icon.class;
                return Object.class;
            }
        };

        table = new JTable(model);
        table.setRowHeight(60);

        JScrollPane scroll = new JScrollPane(table);



 // ===== MAIN SPLIT (TABLE + FORM) =====
JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
splitPane.setResizeWeight(0.6);

splitPane.setTopComponent(scroll);

// ===== FORM PANEL =====
JPanel formPanel = new JPanel();
formPanel.setLayout(new GridBagLayout());
formPanel.setBackground(new Color(240,220,190));

GridBagConstraints gbc = new GridBagConstraints();
gbc.insets = new Insets(8,8,8,8);
gbc.fill = GridBagConstraints.HORIZONTAL;

txtTenMon = new JTextField(15);
txtGia = new JTextField(15);
txtSoLuong = new JTextField(15);

cbLoai = new JComboBox<>();
cbLoai.addItem("Đồ uống");
cbLoai.addItem("Ăn vặt");

JButton btnChooseImage = new JButton("Chọn ảnh");

lblImageName = new JLabel("monan1.jpg");

// ROW 1
gbc.gridx=0; gbc.gridy=0;
formPanel.add(new JLabel("Tên món"),gbc);

gbc.gridx=1;
formPanel.add(txtTenMon,gbc);

gbc.gridx=2;
formPanel.add(new JLabel("Giá"),gbc);

gbc.gridx=3;
formPanel.add(txtGia,gbc);

// ROW 2
gbc.gridx=0; gbc.gridy=1;
formPanel.add(new JLabel("Số lượng"),gbc);

gbc.gridx=1;
formPanel.add(txtSoLuong,gbc);

gbc.gridx=2;
formPanel.add(new JLabel("Loại"),gbc);

gbc.gridx=3;
formPanel.add(cbLoai,gbc);

// ROW 3
gbc.gridx=0; gbc.gridy=2;
formPanel.add(new JLabel("Hình ảnh"),gbc);

gbc.gridx=1;
formPanel.add(btnChooseImage,gbc);

gbc.gridx=2;
formPanel.add(new JLabel("Tên file"),gbc);

gbc.gridx=3;
formPanel.add(lblImageName,gbc);

// ===== BUTTON PANEL =====
JPanel buttonPanel = new JPanel();

JButton btnAdd = new JButton("Thêm");
btnAdd.setBackground(new Color(90,180,120));

JButton btnUpdate = new JButton("Cập nhật");
btnUpdate.setBackground(new Color(255,180,60));

JButton btnDelete = new JButton("Xóa");
btnDelete.setBackground(new Color(255,80,80));

JButton btnRefresh = new JButton("Làm mới");
btnRefresh.setBackground(new Color(100,160,200));

buttonPanel.add(btnAdd);
buttonPanel.add(btnUpdate);
buttonPanel.add(btnDelete);
buttonPanel.add(btnRefresh);

gbc.gridx=0;
gbc.gridy=3;
gbc.gridwidth=4;

formPanel.add(buttonPanel,gbc);

splitPane.setBottomComponent(formPanel);

add(splitPane,BorderLayout.CENTER);

        loadData();

        // ===== SỰ KIỆN =====
        btnAdd.addActionListener(e->addFood());
        btnDelete.addActionListener(e->deleteFood());
        btnUpdate.addActionListener(e->updateFood());
        btnRefresh.addActionListener(e->loadData());
        btnSearch.addActionListener(e->searchFood());
        btnChooseImage.addActionListener(e->chooseImage());

        table.getSelectionModel().addListSelectionListener(e -> showData());
    }

    // ===== CHỌN ẢNH =====
    void chooseImage(){

        JFileChooser chooser = new JFileChooser();

        int result = chooser.showOpenDialog(this);

        if(result == JFileChooser.APPROVE_OPTION){

            try{

                File file = chooser.getSelectedFile();

                selectedImage = file.getName();

                lblImageName.setText(selectedImage);

                File dest = new File("src/images/" + selectedImage);

                Files.copy(file.toPath(),dest.toPath(),StandardCopyOption.REPLACE_EXISTING);

            }catch(Exception e){
                e.printStackTrace();
            }

        }

    }

    // ===== LOAD DATA =====
    void loadData(){

        model.setRowCount(0);

        try{

            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM monan";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                String tenAnh = rs.getString("hinh_anh");

                ImageIcon resizedIcon = null;

                try{

                    java.net.URL imgURL = getClass().getResource("/images/" + tenAnh);

                    if(imgURL != null){

                        ImageIcon icon = new ImageIcon(imgURL);

                        Image img = icon.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);

                        resizedIcon = new ImageIcon(img);
                    }

                }catch(Exception ex){
                    resizedIcon = null;
                }

                double gia = rs.getDouble("gia");
DecimalFormat df = new DecimalFormat("#,###");

model.addRow(new Object[]{
        rs.getInt("id"),
        resizedIcon,
        rs.getString("ten_mon"),
        df.format(gia) + " đ",
        rs.getInt("so_luong"),
        rs.getInt("id_loai")==1?"Đồ uống":"Ăn vặt"
});

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // ===== THÊM =====
    void addFood(){

        try{

            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO monan(ten_mon,gia,so_luong,id_loai,hinh_anh) VALUES(?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,txtTenMon.getText());
            String giaText = txtGia.getText().replace("đ","").replace(",","").trim();
ps.setDouble(2,Double.parseDouble(giaText));
            ps.setInt(3,Integer.parseInt(txtSoLuong.getText()));
            ps.setInt(4,cbLoai.getSelectedIndex()+1);
            ps.setString(5,selectedImage);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"Thêm món thành công 🎉");

            loadData();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // ===== DELETE =====
    void deleteFood(){

        int row = table.getSelectedRow();

        if(row==-1) return;

        int id = (int)model.getValueAt(row,0);

        try{

            Connection conn = DBConnection.getConnection();

            String sql = "DELETE FROM monan WHERE id=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,id);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"Xóa thành công");

            loadData();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // ===== UPDATE =====
    void updateFood(){

        int row = table.getSelectedRow();

        if(row==-1) return;

        int id = (int)model.getValueAt(row,0);

        try{

            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE monan SET ten_mon=?,gia=?,so_luong=?,id_loai=?,hinh_anh=? WHERE id=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,txtTenMon.getText());
            String giaText = txtGia.getText().replace("đ","").replace(",","").trim();
ps.setDouble(2,Double.parseDouble(giaText));
            ps.setInt(3,Integer.parseInt(txtSoLuong.getText()));
            ps.setInt(4,cbLoai.getSelectedIndex()+1);
            ps.setString(5,selectedImage);
            ps.setInt(6,id);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"Cập nhật thành công");

            loadData();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // ===== SEARCH =====
    void searchFood(){

        model.setRowCount(0);

        try{

            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM monan WHERE ten_mon LIKE ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,"%"+txtSearch.getText()+"%");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                String tenAnh = rs.getString("hinh_anh");

                java.net.URL imgURL = getClass().getResource("/images/" + tenAnh);

                ImageIcon icon = null;

                if(imgURL != null){

                    ImageIcon i = new ImageIcon(imgURL);

                    Image img = i.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);

                    icon = new ImageIcon(img);

                }

                double gia = rs.getDouble("gia");
DecimalFormat df = new DecimalFormat("#,###");

model.addRow(new Object[]{
        rs.getInt("id"),
        icon,
        rs.getString("ten_mon"),
        df.format(gia) + " đ",
        rs.getInt("so_luong"),
        rs.getInt("id_loai")==1?"Đồ uống":"Ăn vặt"
});

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // ===== SHOW DATA =====
    void showData(){

        int row = table.getSelectedRow();

        if(row==-1) return;

        txtTenMon.setText(model.getValueAt(row,2).toString());
        String gia = model.getValueAt(row,3).toString();

gia = gia.replace("đ","").replace(",","").trim();

txtGia.setText(gia);
        txtSoLuong.setText(model.getValueAt(row,4).toString());

        if(model.getValueAt(row,5).toString().equals("Đồ uống")){
            cbLoai.setSelectedIndex(0);
        }else{
            cbLoai.setSelectedIndex(1);
        }

    }

}