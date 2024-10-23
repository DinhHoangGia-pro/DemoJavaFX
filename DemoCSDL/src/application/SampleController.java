package application;

import javafx.scene.control.Label;
import java.security.*;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Base64;
import java.security.spec.NamedParameterSpec;
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
		
		Dangnhap(txtUser.getText(), txtPassword.getText());
				
		
	}
	
	@FXML
	public void dangnhap1_clicked()
	{
		
		Dangnhap_caitien1(txtUser.getText(), txtPassword.getText());
				
		
	}
	
	@FXML
	public void dangnhap2_clicked()
	{
		
		Dangnhap_caitien2(txtUser.getText(), txtPassword.getText());
				
		
	}
	
	@FXML
	public void dangnhap_hasing_clicked()
	{
		
		Dangnhap_with_Hasing(txtUser.getText(), txtPassword.getText());
				
		
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
	
	public void Dangnhap_caitien2(String username, String password)
	{
		try {
			Connection conn=DataConnection.getConnection();
			
			String query = "SELECT id, username FROM	tbl_User "
					+ "where username=? and password=?";
			
			txtTest.setText(query);
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
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
	
	public String hashText(String text) throws Exception {
		String plainText = text;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hashedBytes = digest.digest(plainText.getBytes());
		String str=Base64.getEncoder().encodeToString(hashedBytes);
		return str;
	}
	
	public void Dangnhap_with_Hasing(String username, String password)
	{
		try {
			Connection conn=DataConnection.getConnection();
			
			String query = "SELECT id, username FROM	tbl_User "
					+ "where username=? and password=?";
			
			txtTest.setText(query);
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, hashText(password));
			
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
	
	
	
	
	
}
