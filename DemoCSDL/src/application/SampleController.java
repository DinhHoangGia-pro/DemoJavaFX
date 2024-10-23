package application;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import javafx.fxml.FXML;

public class SampleController 

{
	@FXML
	private TextField txtUser,txtPassword, txtTest;
	
	@FXML
	private Label lblInfo;
	
	@FXML
	public void dangnhap_clicked()
	{
		
		Dangnhap_caitien1(txtUser.getText(), txtPassword.getText());
				
		
	}
	
	
	public void Dangnhap(String username, String password)
	{
		try {
			Connection conn=DataConnection.getConnection();
			
			String query = "SELECT id, username FROM	tbl_User "
					+ "where username='"+username+"' and password='"+password+"'";
			
			txtTest.setText(query);
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) 
			{
				lblInfo.setText(rs.getString("username")+ " đã đăng nhập");
			}
			else
			{
				lblInfo.setText("Đăng nhập không thành công");
			}
			
			
		}
		
		catch(Exception ex)
		{
			lblInfo.setText("Lỗi đăng nhập:"+ex.getMessage());
			txtUser.setText("Lỗi đăng nhập:"+ex.getMessage());
		}
		
		
	}
	
	public void Dangnhap_caitien1(String username, String password)
	{
		try {
			Connection conn=DataConnection.getConnection();
			
			String query = "DECLARE @thamso1 nvarchar(100), @thamso2 nvarchar(100);"
					+ "set @thamso1='"+ username+"'; "
					+ "set @thamso2='"+password+"';"
					+ "SELECT id, username FROM tbl_User "
					+ "where username=@thamso1 and password=@thamso2";
			
			txtTest.setText(query);
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) 
			{
				lblInfo.setText(rs.getString("username")+ " đã đăng nhập");
			}
			else
			{
				lblInfo.setText("Đăng nhập không thành công");
			}
			
			
		}
		
		catch(Exception ex)
		{
			lblInfo.setText("Lỗi đăng nhập:"+ex.getMessage());
			//txtUser.setText("Lỗi đăng nhập:"+ex.getMessage());
		}
		
		
	}
	
	
	
	
}
