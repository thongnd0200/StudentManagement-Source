package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class StudentManager {
	 public StudentManager(){
	    }

	    private Connection connection;

	    public Connection getConn() {
		return connection;
	    }

	    public void setConn(Connection conn) {
		this.connection = conn;
	    }

	    public Connection getConnect() throws ClassNotFoundException, SQLException {
		Class.forName(DatabaseInfo.driverName);
		connection = DriverManager.getConnection(DatabaseInfo.dbURL);
		System.out.println("CONNECTTED!");
		return connection;
	    }
	    public boolean checkStudent(String Mssv) throws SQLException, ClassNotFoundException {
	    	// Kết nối database
	    	connection = getConnect();

	    	// Kiểm tra sinh viên có trong database hay chưa
	    	String sql = "SELECT * FROM Students WHERE Mssv = '" + Mssv + "'";
	    	Statement stm1 = connection.createStatement();
	    	ResultSet rs = stm1.executeQuery(sql);

	    	// Trả về kết quả
	    	if (!rs.next()) {
	    	    connection.close();
	    	    return false;
	    	}
	    	connection.close();
	    	return true;
	        }
	    public Vector<Vector<String>> getAll() throws ClassNotFoundException, SQLException {
	    	Vector<Vector<String>> data = new Vector<>();

	    	// Kết nối database
	    	connection = getConnect();

	    	// Tạo câu lệnh SQL
	    	Statement stmt = connection.createStatement();
	    	ResultSet rs = stmt.executeQuery("Select * from Students");
	    	while (rs.next()) {

	    	    // Lấy dữ liệu từ ResultSet
	    	    String Mssv = rs.getString(1);
	    	    String Ten = rs.getString(2);
	    	    String Lop = rs.getString(3);
	    	    String GioiTinh = rs.getString(4);
	    	    String DiaChi = rs.getString(5);
	    	    String Email = rs.getString(6);

	    	    // Ghi vào vector
	    	    Vector<String> temp = new Vector<>();
	    	    temp.add(Mssv);
	    	    temp.add(Ten);
	    	    temp.add(Lop);
	    	    temp.add(GioiTinh);
	    	    temp.add(DiaChi);
	    	    temp.add(Email);

	    	    // Thêm dữ liệu vào data vector chính
	    	    data.add(temp);
	    	}
	    	connection.close();
	    	return data;
	        }
	    public int update(String Mssv, String Ten, String GioiTinh, String Lop, String DiaChi, String Email)
                throws ClassNotFoundException, SQLException {
	    	int updateStatus = 0;
	    	// Kết nối database
	    	Connection conn = getConnect();
	    	// Tạo câu lệnh SQL
	    	String sql = "UPDATE Students SET Ten ='" + Ten + "',GioiTinh ='" + GioiTinh + "',Lop ='" + Lop
	    			+ "', Email ='" + Email + "', DiaChi='" + DiaChi + "' WHERE Mssv='" + Mssv + "'";
	    	Statement stm1 = conn.createStatement();
	    	updateStatus = stm1.executeUpdate(sql);
	    	conn.close();
	    	return updateStatus;
	    }
	    public void addNew(String Mssv, String Ten, String Lop, String GioiTinh, String DiaChi, String Email)
                    throws ClassNotFoundException, SQLException {
	    	// Kết nối database
	    	connection = getConnect();

	    	// Tạo câu lệnh SQL 
	    	String sql = "INSERT INTO Students(Mssv, Ten, Lop, GioiTinh, DiaChi, Email) VALUES(?,?,?,?,?,?)";
	    	PreparedStatement stmt = connection.prepareStatement(sql);
	    	stmt.setString(1, Mssv);
	    	stmt.setString(2, Ten);
	    	stmt.setString(3, Lop);
	    	stmt.setString(4, GioiTinh);
	    	stmt.setString(5, DiaChi);
	    	stmt.setString(6, Email);

	    	// Thực hiện lệnh SQL
	    	stmt.executeUpdate();

	    	// Đóng kết nối
	    	connection.close();
	    	}
	    public int delete(String Mssv) throws SQLException, ClassNotFoundException {
	    	int deleteStatus = 0;

	    	// Kết nối database
	    	connection = getConnect();

	    	// Xóa sinh viên
	    	String sql = "DELETE FROM Students WHERE Mssv='" + Mssv + "'";
	    	Statement stm1 = connection.createStatement();
	    	deleteStatus = stm1.executeUpdate(sql);

	    	// Trả về kết quả int (có xóa thành công hay không)
	    	connection.close();
	    	return deleteStatus;
	        }
}

