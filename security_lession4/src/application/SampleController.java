package application;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.security.MessageDigest;
import java.util.Base64;


public class SampleController 
{
	
	@FXML
	private TextField inputText;
	@FXML
	private TextField outputText;
	
	@FXML
	public void hashText() throws Exception {
		String plainText = inputText.getText();
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hashedBytes = digest.digest(plainText.getBytes());
		outputText.setText(Base64.getEncoder().encodeToString(hashedBytes));
	}
	
}
