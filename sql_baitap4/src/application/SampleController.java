package application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;

public class SampleController 
{
	
	@FXML
	TextField txtSearch, txtProductID,txtProductName,txtQuantityPerUnit,txtUnitPrice;
	
	@FXML
	TableView<Product> tblProducts;
	
	
	@FXML
	private TableColumn<Product, Integer> clProductID;
	
	
	@FXML
	private TableColumn<Product, String> clProductName;
	
	@FXML
	private TableColumn<Product, String> clQuantityPerUnit;
	@FXML
	private TableColumn<Product, Double> clUnitPrice;
	
	
	@FXML
	private Button btnAdd, btnEdit, btnDel, btnSave, btnSearch, btnCancel;
	
	private ObservableList<Product> productList =
			FXCollections.observableArrayList();
			
	private Product selectedProduct;
	
	
	private String event="";
	
	@FXML
	public void initialize()
	{
		//Hàm initialize: Khởi tạo dữ liệu cho form
		
		clProductID.setCellValueFactory(new
				PropertyValueFactory<>("ProductID"));
				
		clProductName.setCellValueFactory(new
				PropertyValueFactory<>("ProductName"));
		
		clQuantityPerUnit.setCellValueFactory(new
				PropertyValueFactory<>("QuantityPerUnit"));
				
		clUnitPrice.setCellValueFactory(new
				PropertyValueFactory<>("UnitPrice"));
		tblProducts.setItems(productList);				
				//Load dữ liệu từ CSDL, đổ vào giao diện
				loadProducts();	
	
	}
	
	@FXML
	public void loadProducts() 
		{
		//Xóa sạch dữ liệu của giao diện trước khi add
		tblProducts.getSelectionModel().clearSelection();
		tblProducts.getItems().clear();
		
		setEnable(false);/*Khong cho sua*/
		
		String query = "SELECT ProductID, ProductName,QuantityPerUnit, UnitPrice FROM Products";
		try (Connection conn = DataConnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		
		ResultSet rs = pstmt.executeQuery()) 
		{
				
				while (rs.next()) {
					Product product = new Product();
					product.setProductID(rs.getInt("ProductID"));					
					product.setProductName(rs.getString("ProductName"));
					product.setQuantityPerUnit(rs.getString("QuantityPerUnit"));
					product.setUnitPrice(rs.getDouble("UnitPrice"));								
										
					productList.add(product);
				}
				tblProducts.setItems(productList);
		}
		catch (SQLException e) {
		e.printStackTrace();
		}
	}
	
	//---------------THU TUC DO DU LIEU VAO CAC O EDIT KHI CLICK VÀO 1 HÀNG
	
	@FXML
	public void selectProduct() {
	selectedProduct = tblProducts.getSelectionModel().getSelectedItem();
	if (selectedProduct != null) {
		txtProductName.setText(selectedProduct.getProductName());
		txtUnitPrice.setText(String.valueOf(selectedProduct.getUnitPrice()));
		txtQuantityPerUnit.setText(selectedProduct.getQuantityPerUnit());
		txtProductID.setText(String.valueOf(selectedProduct.getProductID()));
	
	}
	}
	
	
	public void setEnable(boolean dk)
	{
		txtProductID.setEditable(dk);
		txtProductName.setEditable(dk);
		txtQuantityPerUnit.setEditable(dk);
		txtUnitPrice.setEditable(dk);
		btnAdd.setDisable(false);
		btnEdit.setDisable(false);
		btnDel.setDisable(false);
		
	}
	//-----------------------------------
	@FXML
	public void addProduct_event()
	{
		setEnable(true);
		txtProductID.setEditable(false);
		txtProductID.setText("");
		txtProductName.setText("");
		txtQuantityPerUnit.setText("");
		txtUnitPrice.setText("");
		event="INSERT";
		btnAdd.setDisable(true);
		btnEdit.setDisable(true);
		btnDel.setDisable(true);
		
	}
	
	public void addProduct() {
		String query = "INSERT INTO Products (ProductName,QuantityPerUnit, UnitPrice) VALUES (?,?, ?)";
		try (Connection conn = DataConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
				pstmt.setString(1, txtProductName.getText());
				pstmt.setString(2, txtQuantityPerUnit.getText());				
				pstmt.setDouble(3, Double.parseDouble(txtUnitPrice.getText()));
				pstmt.executeUpdate();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		loadProducts();
    	tblProducts.refresh();
	
	}
	
	@FXML
	public void updateProduct_event()
	{
		if (txtProductID.getText()=="")
		{
			Thongbao("Bạn chưa chọn bản ghi để sửa");
			return;
		}
		setEnable(true);		
		event="UPDATE";
		btnAdd.setDisable(true);
		btnEdit.setDisable(true);
		btnDel.setDisable(true);
		
	}
	//----------------------------------------------
	
	public void updateProduct() {
		String query = "UPDATE Products SET ProductName = ?, QuantityPerUnit=?,UnitPrice = ? WHERE ProductID = ?";
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, txtProductName.getText());
            pstmt.setString(2, txtQuantityPerUnit.getText());
            pstmt.setDouble(3, Double.parseDouble(txtUnitPrice.getText()));
            pstmt.setInt(4, Integer.parseInt(txtProductID.getText()));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        	loadProducts();
        	tblProducts.refresh(); // Cập nhật lại hiển thị TableView
	}
	
	public void deleteProduct_event()
	{
		if (txtProductID.getText()=="")
		{
			Thongbao("Bạn chưa chọn bản ghi để xóa");
			return;
		}
		if (Question("Bạn có chắc chắn xóa")==true)
			deleteProduct();
	}
	
	public void deleteProduct()
	{
		String query = "DELETE Products WHERE ProductID = ?";
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

     
            pstmt.setInt(1, Integer.parseInt(txtProductID.getText()));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        	loadProducts();
        	tblProducts.refresh(); // Cập nhật lại hiển thị TableView
		
	}
	@FXML
	public void searchProduct()
	{
		tblProducts.getSelectionModel().clearSelection();
		tblProducts.getItems().clear();
		
		setEnable(false);/*Khong cho sua*/
		
		String query = "SELECT ProductID, ProductName,QuantityPerUnit, UnitPrice FROM Products where ProductName LIKE ?";
		try (
				Connection conn = DataConnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query))
		{
				
	     pstmt.setString(1, "%" + txtSearch.getText() + "%");			
				
		 ResultSet rs = pstmt.executeQuery();
		
				
				while (rs.next()) {
					Product product = new Product();
					product.setProductID(rs.getInt("ProductID"));					
					product.setProductName(rs.getString("ProductName"));
					product.setQuantityPerUnit(rs.getString("QuantityPerUnit"));
					product.setUnitPrice(rs.getDouble("UnitPrice"));								
										
					productList.add(product);
				}
				tblProducts.setItems(productList);
		}
		
		catch (SQLException e) {
		e.printStackTrace();
		}
		
	}
	
	
	boolean ok=false;
	public boolean Question(String msg)
	{
		ok=false;
		
		//Thử Message Box
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Xác nhận...");
		alert.setHeaderText("Xác nhận");
		alert.setContentText(msg);
		alert.showAndWait().ifPresent(rs -> {
		if (rs == ButtonType.OK) {
		//System.out.println("Pressed OK.");
			ok=true;
		}
		else if (rs==ButtonType.CANCEL)
		{
			ok=false;
		}
		});
		
		return ok;
		
	}
	
	public void Thongbao(String msg)
	{
		
		
		//Thử Message Box
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("Thông báo");
		alert.setContentText(msg);
		alert.showAndWait().ifPresent(rs -> {
		if (rs == ButtonType.OK) {
		//System.out.println("Pressed OK.");
			
		}
		
		});
		
		
		
	}
	
	
	@FXML
	public void Cancel_event()
	{
		setEnable(true);
	}
	
	@FXML
	public void Save_event()
	{
		if (event=="INSERT")
		{
			addProduct();
			Thongbao("Đã thêm thành công trong CSDL");
		}
		else if (event=="UPDATE")
		{
			updateProduct();
			Thongbao("Đã cập nhật dữ liệu CSDL");
		}
		
			
			
	}
	
	
	
}
