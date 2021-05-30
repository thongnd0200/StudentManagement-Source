package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import	java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Vector;

import Database.DatabaseInfo;
import Database.StudentManager;

public class GUI extends JFrame {
	JLabel lbMssv, lbClass, lbName, lbAddress, lbGioiTinh, lbEmail;
	JTextField tfMssv, tfClass, tfName, tfAddress, tfGioiTinh, tfEmail;
	JButton btAdd, btUpdate, btDelete, btShow;
	JScrollPane scrollPane;
	JTable dataTable;
	JPanel contentPane;
	StudentManager manager = new StudentManager();
	Vector<Vector<String>> data = new Vector<Vector<String>>();
	Vector<String> header = new Vector<String>();
	public GUI() {
		this.setTitle("Quan Ly Thong Tin Sinh Vien");
		this.setSize(1200, 700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbMssv = new JLabel("MSSV");
		lbMssv.setBounds(50, 50, 100, 50);
		
		
		lbName = new JLabel("Ten");
		lbName.setBounds(50, 125, 100, 50);
		
		lbClass = new JLabel("Lop");
		lbClass.setBounds(50, 200, 100, 50);
		
		lbAddress = new JLabel("Dia Chi");
		lbAddress.setBounds(650, 50, 100, 50);
		
		lbGioiTinh = new JLabel("Gioi Tinh (M-F)");
		lbGioiTinh.setBounds(650, 125, 100, 50);
		
		lbEmail = new JLabel("Email");
		lbEmail.setBounds(650, 200, 100, 50);
		
		tfMssv = new JTextField();
		tfMssv.setBounds(150, 60, 300, 30);

		tfClass = new JTextField();
		tfClass.setBounds(150, 210, 300, 30);

		tfName = new JTextField();
		tfName.setBounds(150, 135, 300, 30);

		tfAddress = new JTextField();
		tfAddress.setBounds(750, 60, 300, 30);

		tfGioiTinh = new JTextField();
		tfGioiTinh.setBounds(750, 135, 300, 30);

		tfEmail = new JTextField();
		tfEmail.setBounds(750, 210, 300, 30);

		contentPane.add(lbMssv);
		contentPane.add(tfMssv);
		contentPane.add(lbName);
		contentPane.add(tfName);
		contentPane.add(lbClass);
		contentPane.add(tfClass);
		contentPane.add(lbGioiTinh);
		contentPane.add(tfGioiTinh);
		contentPane.add(lbAddress);
		contentPane.add(tfAddress);
		contentPane.add(lbEmail);
		contentPane.add(tfEmail);
		
		dataTable = new JTable(new DefaultTableModel(data, header));
		dataTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	tblStudentMouseClicked(evt);
            }
        });
		dataTable.setRowSelectionAllowed(true);
		dataTable.setDefaultEditor(Object.class, null);
	    dataTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
	    scrollPane = new JScrollPane(dataTable);
		scrollPane.setBounds(50, 375, 1100, 250);
		contentPane.add(scrollPane);
		header.add("MSSV");
		header.add("Ten");
		header.add("Lop");
		header.add("Gioi Tinh");
		header.add("Dia Chi");
		header.add("Email");
		
		btAdd = new JButton("ADD");
		btAdd.setBounds(50, 300, 200, 50);
		btAdd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
				    btnAddActionPerformed(arg0);
				}
			    });
		btDelete = new JButton("DELETE");
		btDelete.setBounds(350, 300, 200, 50);
		btDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			    btnDeleteActionPerformed(e);
			}
		    });
		btUpdate = new JButton("UPDATE");
		btUpdate.setBounds(650, 300, 200, 50);
		btUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnUpdateActionPerformed(arg0); 
			}
		    });
		btShow = new JButton("SHOW");
		btShow.setBounds(950, 300, 200, 50);
		btShow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			    try {

				// Tải dữ liệu từ database vào phần mềm
				data = manager.getAll();

				// Thiết kế bảng dữ liệu để hiển thị
				((DefaultTableModel) (dataTable.getModel())).setDataVector(data, header);

				// Kích hoạt các chức năng Add, Delete
				btAdd.setEnabled(true);
				btDelete.setEnabled(true);
				btUpdate.setEnabled(true);
				// Thông báo thành công
				JOptionPane.showMessageDialog(contentPane, "Load sucess!");

			    } catch (Exception e) {
				JOptionPane.showMessageDialog(contentPane, "Load failure!\nDetails: " + e);
			    }
			}
		    });
		contentPane.add(btAdd);
		contentPane.add(btDelete);
		contentPane.add(btUpdate);
		contentPane.add(btShow);		
		
		
		
		this.setVisible(true);
	}
	protected void btnAddActionPerformed(ActionEvent arg0) {
		try {
		    String Mssv = tfMssv.getText();
		    String Ten = tfName.getText();
		    String Lop = tfClass.getText().toUpperCase();
		    String GioiTinh = tfGioiTinh.getText().toUpperCase();
		    String DiaChi = tfAddress.getText();
		    String Email = tfEmail.getText();
		    //Kiểm tra tính hợp lệ của các thông tin đã nhập
		    if (Mssv.matches("[A-Za-z0-9]+") && Ten.matches("[A-Za-z ]+") && Lop.matches("[A-Za-z0-9]+") && GioiTinh.matches("[M|F]"))
		    {
		    // Thêm dữ liệu vào database
		    manager.addNew(Mssv, Ten, Lop, GioiTinh, DiaChi, Email);
		    
		    // Cập nhật hiển thị database cho phần mềm
		    data = manager.getAll();
		    ((DefaultTableModel) (dataTable.getModel())).setDataVector(data, header);

		    // Thông báo thành công
		    JOptionPane.showMessageDialog(contentPane, "Add Success!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
		    }
		    else JOptionPane.showMessageDialog(contentPane, "Add new failure", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e1) {
		    JOptionPane.showMessageDialog(contentPane, "Add new failure\nDetails: " + e1, "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	
	protected void btnDeleteActionPerformed(ActionEvent e) {
		try {
		    String Mssv = tfMssv.getText();

		    // Kiểm tra sinh viên có trong database hay không
		    if (!manager.checkStudent(Mssv)) throw new Exception("This student ID is not exits in database, So can delete!");
		    manager.delete(Mssv);

		    // Cập nhật lại dữ liệu hiển thị trên phần mềm
		    data = manager.getAll();
		    ((DefaultTableModel) (dataTable.getModel())).setDataVector(data, header);

		    // Thông báo xóa thành công
		    JOptionPane.showMessageDialog(contentPane, "Delete Success!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e1) {
		    JOptionPane.showMessageDialog(contentPane, "Delete failure\nDetails:" + e1, "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	protected void btnUpdateActionPerformed(ActionEvent arg0) {
		try {
		    String Mssv = tfMssv.getText();
		    String Ten = tfName.getText();
		    String Lop = tfClass.getText().toUpperCase();
		    String GioiTinh = tfGioiTinh.getText().toUpperCase();
		    String DiaChi = tfAddress.getText();
		    String Email = tfEmail.getText();
		    //Kiểm tra sự tồn tại của sinh viên trước khi Update
		    if (!manager.checkStudent(Mssv)) throw new Exception("This student ID is not exits in database, So can update!");
		    //Kiểm tra tính hợp lệ của các thông tin đã nhập
		    if (Mssv.matches("[A-Za-z0-9]+") && Ten.matches("[A-Za-z ]+") && Lop.matches("[A-Za-z0-9]+") && GioiTinh.matches("[M|F]"))
		    {
		    // Cập nhật dữ liệu vào database
		    manager.update(Mssv, Ten, GioiTinh, Lop, DiaChi, Email);
		    
		    // Cập nhật hiển thị database cho phần mềm
		    data = manager.getAll();
		    ((DefaultTableModel) (dataTable.getModel())).setDataVector(data, header);

		    // Thông báo thành công
		    JOptionPane.showMessageDialog(contentPane, "Update Success!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
		    }
		    else JOptionPane.showMessageDialog(contentPane, "Update failure", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e1) {
		    JOptionPane.showMessageDialog(contentPane, "Update failure\nDetails: " + e1, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	protected void tblStudentMouseClicked(MouseEvent evt) {
		DefaultTableModel model = (DefaultTableModel)dataTable.getModel();
		int selectedRow = dataTable.getSelectedRow();
		//Trả về dữ liệu trên dòng được chọn 
		tfMssv.setText(model.getValueAt(selectedRow, 0).toString());
		tfName.setText(model.getValueAt(selectedRow, 1).toString());
		tfClass.setText(model.getValueAt(selectedRow, 2).toString());
		tfGioiTinh.setText(model.getValueAt(selectedRow, 3).toString());
		tfAddress.setText(model.getValueAt(selectedRow, 4).toString());
		tfEmail.setText(model.getValueAt(selectedRow, 5).toString());
	}
	public static void main(String[] args) {
		new GUI();
	}
}
