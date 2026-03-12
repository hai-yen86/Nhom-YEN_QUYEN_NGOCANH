package view;

import javax.swing.*;
import java.awt.*;

public class BillFrame extends JFrame {

    public BillFrame(String tenMon, int soLuong, int donGia, int thanhTien) {

        setTitle("Hóa đơn thanh toán");
        setSize(400,300);
        setLocationRelativeTo(null);

        JTextArea bill = new JTextArea();
        bill.setFont(new Font("Arial", Font.PLAIN, 16));

        bill.setText(
                "=========== BILL ==========\n\n" +
                "Tên món: " + tenMon + "\n" +
                "Số lượng: " + soLuong + "\n" +
                "Đơn giá: " + donGia + " VND\n" +
                "-------------------------\n" +
                "Tổng tiền: " + thanhTien + " VND\n\n" +
                "Cảm ơn quý khách!"
        );

        bill.setEditable(false);

        add(new JScrollPane(bill));

        setVisible(true);
    }
}