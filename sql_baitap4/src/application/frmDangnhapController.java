package application;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class frmDangnhapController {
	
	@FXML 
	TextField txtUsername;
	
	@FXML
	PasswordField txtPassword;
	
	@FXML
	public void dangnhap_clicked()
	{
		
		Dangnhap_with_Hasing(txtUsername.getText(), txtPassword.getText());
				
		
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
			
			
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, hashText(password));
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) 
			{
				//Dang nhap thanh cong
				Goto_mainscreen();
			}
			else
			{
				
			}
			
			
		}
		
		catch(Exception ex)
		{
			
		}
		
		
	}
	
	
	private void Goto_mainscreen()
	{
		//Parent root;
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,871,625);
			Stage stage = new Stage();
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
