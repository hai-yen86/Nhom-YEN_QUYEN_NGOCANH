package view;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class BillFrame extends JFrame {

    public BillFrame(String tenMon, int soLuong, int donGia, int thanhTien) {

        setTitle("Hóa đơn thanh toán");
        setSize(400,300);
        setLocationRelativeTo(null);

        JTextArea bill = new JTextArea();
        bill.setFont(new Font("Arial", Font.PLAIN, 16));

        DecimalFormat df = new DecimalFormat("#,###");

bill.setText(
        "=========== BILL ==========\n\n" +
        "Tên món: " + tenMon + "\n" +
        "Số lượng: " + soLuong + "\n" +
        "Đơn giá: " + df.format(donGia) + " đ\n" +
        "-------------------------\n" +
        "Tổng tiền: " + df.format(thanhTien) + " đ\n\n" +
        "Cảm ơn quý khách!"
);

        bill.setEditable(false);

        add(new JScrollPane(bill));

        setVisible(true);
    }
}