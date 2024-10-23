package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SampleController {
	
	@FXML
	private TableView<Product> tableView;
	@FXML
	private TableColumn<Product, Integer> columnProductId;
	@FXML
	private TableColumn<Product, String> columnProductName;
	@FXML
	private TableColumn<Product, Double> columnPrice;
	
	@FXML
	private TextField txtProductName,txtPrice;
	
	ObservableList<Product> productList =
			FXCollections.observableArrayList();
	
	
	@FXML
	public void initialize() {
		
		columnProductId.setCellValueFactory(new
		PropertyValueFactory<>("ProductID"));
		
		columnProductName.setCellValueFactory(new
		PropertyValueFactory<>("ProductName"));
		
		columnPrice.setCellValueFactory(new
		PropertyValueFactory<>("UnitPrice"));
		tableView.setItems(productList);
		
		loadProducts();
	
	
	
	}
	
	@FXML
	public void loadProducts() 
		{
		String query = "SELECT ProductID, ProductName, UnitPrice FROM Products";
		try (Connection conn = DataConnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery()) 
		{
				
				while (rs.next()) {
					Product product = new Product();
					product.setProductID(rs.getInt("ProductID"));					
					product.setProductName(rs.getString("ProductName"));
					product.setUnitPrice(rs.getDouble("UnitPrice"));
										
					
					
					productList.add(product);
				}
				tableView.setItems(productList);
		}
		catch (SQLException e) {
		e.printStackTrace();
		}
	}
	
	//--------------------------
	@FXML
		public void addProduct() 
		{
			String query = "INSERT INTO Products (ProductName, UnitPrice) VALUES (?, ?)";
			try (Connection conn = DataConnection.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(query)) 
			{
					pstmt.setString(1, txtProductName.getText());
					pstmt.setDouble(2, Double.parseDouble(txtPrice.getText()));
					pstmt.executeUpdate();
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	
}
