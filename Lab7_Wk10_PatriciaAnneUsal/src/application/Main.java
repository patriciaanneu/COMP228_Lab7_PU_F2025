package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.sql.*;
import java.time.LocalDate;

public class Main extends Application {
	
	//database connection
	private static final String DB_URL = "jdbc:mysql://localhost:3306/employment_app";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "November2025";
	
	
	//form fields
	 private TextField fullNameField;
	 private TextField currentAddressField;
	 private TextField contactNumberField;
	 private TextField emailField;
	 private TextField educAttainField;
	 private RadioButton maleRadio;
	 private RadioButton femaleRadio;
	 private RadioButton otherRadio;
	 private DatePicker dateAvailablePicker;
	 private TextField desiredPositionField;
	 private TextField desiredSalaryField;
	 private RadioButton authorizedYesRadio;
	 private RadioButton authorizedNoRadio;
	 private RadioButton relativesYesRadio;
	 private RadioButton relativesNoRadio;
	 private TextArea relativesExplainArea;
	 private Button submitButton;
	
	
	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Employment Application");
		
		Label personalInfoLabel = new Label("Personal Information");
		personalInfoLabel.setStyle("-fx-font-weight: bold; -fx-background-color: black; -fx-text-fill: white; -fx-padding: 5;");
		
		fullNameField = new TextField();
        currentAddressField = new TextField();
        contactNumberField = new TextField();
        emailField = new TextField();
        educAttainField = new TextField();
		
        ToggleGroup genderGroup = new ToggleGroup();
        maleRadio = new RadioButton("Male");
        femaleRadio = new RadioButton("Female");
        otherRadio = new RadioButton("Other");
        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);
        otherRadio.setToggleGroup(genderGroup);
		
        Label employmentLabel = new Label("Employment Eligibility");
        employmentLabel.setStyle("-fx-font-weight: bold; -fx-background-color: black; -fx-text-fill: white; -fx-padding: 5;");
        
        dateAvailablePicker = new DatePicker();
        desiredPositionField = new TextField();
        desiredSalaryField = new TextField();
        
        ToggleGroup authorizedGroup = new ToggleGroup();
        authorizedYesRadio = new RadioButton("Yes");
        authorizedNoRadio = new RadioButton("No");
        authorizedYesRadio.setToggleGroup(authorizedGroup);
        authorizedNoRadio.setToggleGroup(authorizedGroup);
        
        ToggleGroup relativesGroup = new ToggleGroup();
        relativesYesRadio = new RadioButton("Yes");
        relativesNoRadio = new RadioButton("No");
        relativesYesRadio.setToggleGroup(relativesGroup);
        relativesNoRadio.setToggleGroup(relativesGroup);
        
        relativesExplainArea = new TextArea();
        relativesExplainArea.setPrefRowCount(3);
        
        
        //submit button
        submitButton = new Button("Submit");
        submitButton.setOnAction(e -> handleSubmit());
        
        //layout using GridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);
        
        //personal information layout
        int row = 0;
        grid.add(personalInfoLabel, 0, row, 4,1);
        row++;
        grid.add(new Label("Full Name"), 0, row);
        grid.add(fullNameField, 1, row, 3,1);
        row++;
        grid.add(new Label("Current Address"), 0, row);
        grid.add(currentAddressField, 1, row, 3,1);
        row++;
        grid.add(new Label("Contact Number"), 0, row);
        grid.add(contactNumberField, 1, row);
        grid.add(new Label("Email Address"), 2, row);
        grid.add(emailField, 3, row);
        row++;
        grid.add(new Label("Highest Educational Attainment"), 0, row);
        grid.add(educAttainField, 1, row);
        grid.add(new Label("Gender"), 2, row);
        HBox genderBox = new HBox(10, maleRadio, femaleRadio, otherRadio);
        grid.add(genderBox, 3, row);
        
        //employment eligibility layout
        row++;
        grid.add(employmentLabel, 0, row, 4,1);
        row++;
        grid.add(new Label("Date Available"), 0, row);
        grid.add(dateAvailablePicker, 1, row);
        grid.add(new Label("Desired Position"), 2, row);
        grid.add(desiredPositionField, 3, row);
        row++;
        grid.add(new Label("Desired Salary"), 0, row);
        grid.add(desiredSalaryField, 1, row);
        row++;
        grid.add(new Label("Are you legally authorized to work in the country?"), 0, row, 3, 1);
        HBox authorizedBox = new HBox(10, authorizedYesRadio, authorizedNoRadio);
        grid.add(authorizedBox, 3, row);
        row++;
        grid.add(new Label("Do you have relatives working for our company?"), 0, row, 3,1);
        HBox relativesBox = new HBox(10, relativesYesRadio, relativesNoRadio);
        grid.add(relativesBox, 3, row);
        row++;
        grid.add(new Label("If yes, please explain further"), 0, row);
        grid.add(relativesExplainArea, 1, row, 3,1);
        row++;
        grid.add(submitButton, 3, row);
        Scene scene = new Scene(grid, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
	}
	
	private void handleSubmit() {
		String fullName = fullNameField.getText();
        String currentAddress = currentAddressField.getText();
        String contactNumber = contactNumberField.getText();
        String email = emailField.getText();
        String educAttain = educAttainField.getText();
        
        String gender = "";
        if (maleRadio.isSelected()) gender = "Male";
        else if (femaleRadio.isSelected()) gender = "Female";
        else if (otherRadio.isSelected()) gender = "Other";
        
        LocalDate dateAvailable = dateAvailablePicker.getValue();
        String desiredPosition = desiredPositionField.getText();
        String desiredSalary = desiredSalaryField.getText();
        
        Boolean authorized = null;
        if (authorizedYesRadio.isSelected()) authorized = true;
        else if (authorizedNoRadio.isSelected()) authorized = false;

        Boolean haveRelatives = null;
        if (relativesYesRadio.isSelected()) haveRelatives = true;
        else if (relativesNoRadio.isSelected()) haveRelatives = false;
        
        String relativesExplain = relativesExplainArea.getText();
        
        if(fullName.isEmpty() || currentAddress.isEmpty() || contactNumber.isEmpty() || email.isEmpty()
                || educAttain.isEmpty() || gender.isEmpty() || dateAvailable == null
                || desiredPosition.isEmpty() || desiredSalary.isEmpty()
                || authorized == null || haveRelatives == null) {
            showAlert(Alert.AlertType.ERROR, "Please fill in all required fields.");
            return;
        }

        String sql = "INSERT INTO applications (" + "full_name, current_address, contact_number, email, educ_attain, gender, " + "date_available, desired_position, desired_salary, authorized_to_work, have_relatives, relatives_explain) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        	     PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

        	    preparedStatement.setString(1, fullName);
        	    preparedStatement.setString(2, currentAddress);
        	    preparedStatement.setString(3, contactNumber);
        	    preparedStatement.setString(4, email);
        	    preparedStatement.setString(5, educAttain);
        	    preparedStatement.setString(6, gender);
        	    preparedStatement.setDate(7, java.sql.Date.valueOf(dateAvailable));
        	    preparedStatement.setString(8, desiredPosition);
        	    preparedStatement.setString(9, desiredSalary);
        	    preparedStatement.setBoolean(10, authorized);
        	    preparedStatement.setBoolean(11, haveRelatives);
        	    preparedStatement.setString(12, relativesExplain);

        	    int rowsInserted = preparedStatement.executeUpdate();

        	    if (rowsInserted > 0) {
        	        showAlert(Alert.AlertType.INFORMATION, "Application submitted successfully!");
        	        clearForm();
        	    } else {
        	        showAlert(Alert.AlertType.ERROR, "Failed to submit application.");
        	    }

        	} catch (SQLException ex) {
        	    ex.printStackTrace();
        	    showAlert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage());
        	}	
     }
        		
        
        
	private void clearForm() {
		
		fullNameField.clear();
        currentAddressField.clear();
        contactNumberField.clear();
        emailField.clear();
        educAttainField.clear();
        maleRadio.setSelected(false);
        femaleRadio.setSelected(false);
        otherRadio.setSelected(false);
        dateAvailablePicker.setValue(null);
        desiredPositionField.clear();
        desiredSalaryField.clear();
        authorizedYesRadio.setSelected(false);
        authorizedNoRadio.setSelected(false);
        relativesYesRadio.setSelected(false);
        relativesNoRadio.setSelected(false);
        relativesExplainArea.clear();
		
	}

	private void showAlert(Alert.AlertType alertType, String msg) {
		Alert alert = new Alert(alertType);
        alert.setTitle("Notification");
        alert.setContentText(msg);
        alert.showAndWait();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
