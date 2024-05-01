package application;

import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class FirstStage extends Main {

	// Layouts
	BorderPane bd = new BorderPane();
	HBox hbox1 = new HBox(15);
	HBox hbox2 = new HBox(15);
	VBox vbox = new VBox(15);
	Stage stage = new Stage();
	Stage stage2 = new Stage();
	Scene scene, scene1, scene2, scene3, scene4, scene5, scene6, scene7, scene8, scene9, scene10, scene11, scene12 , scene13 , newScene;

	// Labels && TextFields
	TextField usernameTxt = new TextField();
	TextField passwordTxt = new TextField();
	Label usernameLabel = new Label("User Name : ");
	Label passwordLabel = new Label("Password :  ");

	// Tables
	TableView<Patient> tableOne = new TableView<>();
	TableView<Appointment> tableTwo = new TableView<>();
	TableView<Appointment> tableThree = new TableView<>();
	TableView<Appointment> tableFour = new TableView<>();
	TableView<Dentists> tableFive = new TableView<>();
	TableView<Payments> tableSix = new TableView<>();
	TableView<Payments> tableSeven = new TableView<>();
	TableView<Appointment> tableEight = new TableView<>();

	// Sharable***************************
	static boolean flag = true, flag2 = true, flag3 = true, flag4 = true, flag5 = true, flag6 = true, flag7 = true , flag8 = true;
	static ImageView imv = new ImageView(
			new Image("C:\\Users\\user\\eclipse-workspace\\DataBaseFinalProject\\Image.png"));
	static ImageView imv2 = imv;
	Circle clip;
	FileChooser chosen = new FileChooser();
	File test = new File("C:\\Users\\user\\eclipse-workspace\\DataBaseFinalProject\\a.txt");
	File file, imageFile;
	byte[] imageData = Files.readAllBytes(test.toPath());
	Patient p;
	Appointment app;
	Payments pa;
	Dentists d;
	// *************************************

	// DataBase
	ResultSet res2 = null;

	public FirstStage() throws Exception { // Constructor

		connectDB(); // Connect with DataBase

		// Buttons
		doctor = new Button("Doctor");
		dentist = new Button("Dentist");
		back = new Button("Back");
		enter = new Button("Enter");
		restore = new Button("Restore");

		// events
		eventsForBasicButtons();
		events();

		// Editing
		bd.setPadding(new Insets(20, 20, 20, 20));
		bd.setStyle("-fx-background-image:url('file:img.png');-fx-background-size:cover");
		back.setStyle(
				"-fx-background-radius: 50 50 50 50;-fx-background-color: #808080; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 20px 40px;");
		enter.setStyle(
				"-fx-background-radius: 50 50 50 50;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 20px 40px;");
		doctor.setStyle(
				"-fx-background-radius: 25 25 25 25;-fx-background-color: d8d9e0; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 30px;");
		dentist.setStyle(
				"-fx-background-radius: 25 25 25 25;-fx-background-color: d8d9e0; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 30px;");
		restore.setStyle(
				"-fx-background-radius: 25 25 25 25;-fx-background-color: d8d9e0; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 30px;");

		usernameLabel.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 25));
		usernameLabel.setTextFill(Color.BLUE);
		passwordLabel.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 25));
		passwordLabel.setTextFill(Color.BLUE);
		hbox1.setAlignment(Pos.CENTER);
		hbox1.getChildren().addAll(dentist, doctor);
		hbox2.setAlignment(Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(hbox1, hbox2);
		bd.setBottom(vbox);

//		imageFile = new File("C:\\Users\\user\\eclipse-workspace\\DataBaseFinalProject\\Image.png");
//		pstatement = connect.prepareStatement("insert into adminstrator values (?,?,?,?,?,?)");
//		byte[] imageDate = Files.readAllBytes(imageFile.toPath());
//		pstatement.setInt(1, 1);
//		pstatement.setString(2, "Taqi");
//		pstatement.setString(3, "100");
//		pstatement.setString(4, "Bassem");
//		pstatement.setString(5, "0569");
//		pstatement.setBytes(6, imageDate);
//		pstatement.executeUpdate();

		// ForDataBase********************************
		patientRegistrationTableView(); // Initialization
		appointmentSchedulingTableView();// Initialization
		paymentsPatientTableView();// Initialization
		paymentsDentistTableView();// Initialization
		dentistSceneTableView();//    Initialization
		ResultSet set = null;
		try {
			pstatement = connect.prepareStatement("select * from patients");
			set = pstatement.executeQuery();
			while (set.next()) {
				tableOne.getItems().add(new Patient(set.getInt(1), set.getInt(3), set.getString(2), set.getString(4),
						set.getString(5)));
				tableOne.refresh();
			}
			set.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// **

		// DataBase***********************************************
		try {
			pstatement = connect.prepareStatement("select * from appointments");
			set = pstatement.executeQuery();
			while (set.next()) {
				tableTwo.getItems().add(new Appointment(set.getInt(1), set.getInt(2), set.getInt(3), set.getString(4),
						set.getString(5), set.getString(6), set.getString(7), set.getString(8), set.getString(9)));
				tableTwo.refresh();
			}
			set.close();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// DataBase***********************************************
		try {
			pstatement = connect.prepareStatement("select dentistId,dentistName,userName,pass from dentists");
			set = pstatement.executeQuery();
			while (set.next()) {
				tableFive.getItems()
						.add(new Dentists(set.getInt(1), set.getString(2), set.getString(3), set.getString(4)));
				tableFive.refresh();
			}
			set.close();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// Stage && Scene
		scene = new Scene(bd, 700, 700);
		stage.setScene(scene);
		stage.setTitle("Dental clinic");
		stage.show();

	}

	private TableView<Appointment> tableForAvailableDentists() { // Create TableView #Four

		// Editing
		tableFour.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-text-fill: #000000;"
				+ "-fx-background-color: #e0e0e0;");
		tableFour.setStyle("-fx-background-color: lightblue;");
		tableFour.setMaxHeight(150);

		// Columns
		if (flag4) {

			TableColumn<Appointment, Integer> dentistIdColumn = new TableColumn<>("Dentist ID");
			dentistIdColumn.setMinWidth(100);
			dentistIdColumn.setCellValueFactory(new PropertyValueFactory<>("dentistId"));

			TableColumn<Appointment, String> dentistColumn = new TableColumn<>("Dentist");
			dentistColumn.setMinWidth(100);
			dentistColumn.setCellValueFactory(new PropertyValueFactory<>("dentist"));

			tableFour.getColumns().addAll(dentistIdColumn, dentistColumn);

			flag4 = false;

		}

		return tableFour;

	}

	private TableView<Appointment> tableForShowAllAppointments() { // Create TableView #Three

		// Editing
		tableThree.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-text-fill: #000000;"
				+ "-fx-background-color: #e0e0e0;");
		tableThree.setStyle("-fx-background-color: lightblue;");

		// Columns
		if (flag3) {

			TableColumn<Appointment, String> appointmentDateColumn = new TableColumn<>("Appointment Date");
			appointmentDateColumn.setMinWidth(120);
			appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

			TableColumn<Appointment, String> treatmentColumn = new TableColumn<>("Treatment");
			treatmentColumn.setMinWidth(80);
			treatmentColumn.setCellValueFactory(new PropertyValueFactory<>("treatments"));

			TableColumn<Appointment, String> startTimeColumn = new TableColumn<>("Start Time");
			startTimeColumn.setMinWidth(80);
			startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));

			TableColumn<Appointment, String> endTimeColumn = new TableColumn<>("End Time");
			endTimeColumn.setMinWidth(80);
			endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));

			TableColumn<Appointment, String> dentistColumn = new TableColumn<>("Dentist");
			dentistColumn.setMinWidth(80);
			dentistColumn.setCellValueFactory(new PropertyValueFactory<>("dentist"));

			tableThree.getColumns().addAll(appointmentDateColumn, startTimeColumn, endTimeColumn, dentistColumn,
					treatmentColumn);

			flag3 = false;

		}

		return tableThree;

	}

	private TableView<Appointment> appointmentSchedulingTableView() { // Create TableView #Two

		// Editing
		tableTwo.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-text-fill: #000000;"
				+ "-fx-background-color: #e0e0e0;");
		tableTwo.setStyle("-fx-background-color: lightblue;");
		// Columns

		if (flag2) {
			TableColumn<Appointment, Integer> appointmentIdColumn = new TableColumn<>("Appointment ID");
			appointmentIdColumn.setMinWidth(50);
			appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));

			TableColumn<Appointment, Integer> patientIdColumn = new TableColumn<>("Patient ID");
			patientIdColumn.setMinWidth(50);
			patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));

			TableColumn<Appointment, String> patientNameColumn = new TableColumn<>("Patient Name");
			patientNameColumn.setMinWidth(50);
			patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));

			TableColumn<Appointment, String> appointmentDateColumn = new TableColumn<>("Appointment Date");
			appointmentDateColumn.setMinWidth(50);
			appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

			TableColumn<Appointment, String> dentistColumn = new TableColumn<>("Dentist");
			dentistColumn.setMinWidth(50);
			dentistColumn.setCellValueFactory(new PropertyValueFactory<>("dentist"));

			TableColumn<Appointment, String> treatmentColumn = new TableColumn<>("Treatment");
			treatmentColumn.setMinWidth(50);
			treatmentColumn.setCellValueFactory(new PropertyValueFactory<>("treatments"));

			TableColumn<Appointment, String> startTimeColumn = new TableColumn<>("Start Time");
			startTimeColumn.setMinWidth(50);
			startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));

			TableColumn<Appointment, String> endTimeColumn = new TableColumn<>("End Time");
			endTimeColumn.setMinWidth(50);
			endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));

			tableTwo.getColumns().addAll(appointmentIdColumn, patientIdColumn, patientNameColumn, appointmentDateColumn,
					startTimeColumn, endTimeColumn, dentistColumn, treatmentColumn);

			flag2 = false;
		}
		return tableTwo;
	}

	private TableView<Patient> patientRegistrationTableView() { // Create tableView #One

		// Editing
		tableOne.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-text-fill: #000000;"
				+ "-fx-background-color: #e0e0e0;");
		tableOne.setStyle("-fx-background-color: lightblue;" /* Set the desired color */
		);

		tableOne.setEditable(true);

		// Columns
		if (flag) {
			TableColumn<Patient, Integer> idColumn = new TableColumn<Patient, Integer>("Patient ID");
			idColumn.setMinWidth(80);
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

			TableColumn<Patient, Integer> ageColumn = new TableColumn<Patient, Integer>("Patient Age");
			ageColumn.setMinWidth(100);
			ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

			TableColumn<Patient, String> nameColumn = new TableColumn<Patient, String>("Patient Name");
			nameColumn.setMinWidth(120);
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

			TableColumn<Patient, String> phoneColumn = new TableColumn<Patient, String>("Patient Phone");
			phoneColumn.setMinWidth(120);
			phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

			TableColumn<Patient, String> emailColumn = new TableColumn<Patient, String>("Patient Email");
			emailColumn.setMinWidth(160);
			emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

			tableOne.getColumns().addAll(idColumn, nameColumn, ageColumn, phoneColumn, emailColumn);
			flag = false;
		}

		return tableOne;
	}

	private TableView<Payments> paymentsPatientTableView() { // Create tableView #Six

		// Editing
		tableSix.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-text-fill: #000000;"
				+ "-fx-background-color: #e0e0e0;");
		tableSix.setStyle("-fx-background-color: lightblue;" /* Set the desired color */
		);

		if (flag6) {
			TableColumn<Payments, Integer> idColumn = new TableColumn<Payments, Integer>("Payment ID");
			idColumn.setMinWidth(60);
			idColumn.setCellValueFactory(new PropertyValueFactory<>("paymentId"));

			TableColumn<Payments, String> payMethodColumn = new TableColumn<Payments, String>("Payment Method");
			payMethodColumn.setMinWidth(120);
			payMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

			TableColumn<Payments, String> payAmountColumn = new TableColumn<Payments, String>("Payment Amount");
			payAmountColumn.setMinWidth(120);
			payAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));

			TableColumn<Payments, String> payDateColumn = new TableColumn<Payments, String>("Payment Date");
			payDateColumn.setMinWidth(110);
			payDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

			TableColumn<Payments, String> treatmentColumn = new TableColumn<Payments, String>("Treatment");
			treatmentColumn.setMinWidth(90);
			treatmentColumn.setCellValueFactory(new PropertyValueFactory<>("treatment"));

			TableColumn<Payments, String> isPaidColumn = new TableColumn<Payments, String>("Is Paid ?");
			isPaidColumn.setMinWidth(90);
			isPaidColumn.setCellValueFactory(new PropertyValueFactory<>("isPaid"));

			tableSix.getColumns().addAll(idColumn, payAmountColumn, payMethodColumn, payDateColumn, treatmentColumn,
					isPaidColumn);

			flag6 = false;
		}

		return tableSix;
	}

	private TableView<Payments> paymentsDentistTableView() { // Table View #Seven

		// Editing
		tableSeven.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-text-fill: #000000;"
				+ "-fx-background-color: #e0e0e0;");
		tableSeven.setStyle("-fx-background-color: lightblue;");

		if (flag7) {
			TableColumn<Payments, String> nameColumn = new TableColumn<Payments, String>("Patient Name");
			nameColumn.setMinWidth(60);
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));

			TableColumn<Payments, String> payAmountColumn = new TableColumn<Payments, String>("Payment Amount");
			payAmountColumn.setMinWidth(120);
			payAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));

			TableColumn<Payments, String> payDateColumn = new TableColumn<Payments, String>("Payment Date");
			payDateColumn.setMinWidth(110);
			payDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

			TableColumn<Payments, String> treatmentColumn = new TableColumn<Payments, String>("Treatment");
			treatmentColumn.setMinWidth(90);
			treatmentColumn.setCellValueFactory(new PropertyValueFactory<>("treatment"));

			TableColumn<Payments, String> isPaidColumn = new TableColumn<Payments, String>("Is Paid ?");
			isPaidColumn.setMinWidth(90);
			isPaidColumn.setCellValueFactory(new PropertyValueFactory<>("isPaid"));

			tableSeven.getColumns().addAll(nameColumn, payAmountColumn, payDateColumn, treatmentColumn, isPaidColumn);

			flag7 = false;
		}

		return tableSeven;

	}

	private TableView<Dentists> dentistProfileTableView() { // Create TableView #Five

		// Edits

		// Editing
		tableFive.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-text-fill: #000000;"
				+ "-fx-background-color: #e0e0e0;");
		tableFive.setStyle("-fx-background-color: lightblue;");

		tableFive.setEditable(true);
		if (flag5) {
			TableColumn<Dentists, Integer> idColumn = new TableColumn<Dentists, Integer>("Dentist ID");
			idColumn.setMinWidth(120);
			idColumn.setCellValueFactory(new PropertyValueFactory<>("dentistId"));

			TableColumn<Dentists, String> nameColumn = new TableColumn<Dentists, String>("Dentist Name");
			nameColumn.setMinWidth(140);
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("dentistName"));

			TableColumn<Dentists, String> userColumn = new TableColumn<Dentists, String>("User Name");
			userColumn.setMinWidth(151);
			userColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

			TableColumn<Dentists, String> passColumn = new TableColumn<Dentists, String>("Password");
			passColumn.setMinWidth(151);
			passColumn.setCellValueFactory(new PropertyValueFactory<>("pass"));

			tableFive.getColumns().addAll(idColumn, nameColumn, userColumn, passColumn);

			flag5 = false;
		}

		return tableFive;

	}

	private TableView<Appointment> dentistSceneTableView() { // Create tableView #eight


		// Editing
		tableEight.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-text-fill: #000000;"
				+ "-fx-background-color: #e0e0e0;");
		tableEight.setStyle("-fx-background-color: lightblue;");

		// Columns

		if (flag8) {

			TableColumn<Appointment, String> patientNameColumn = new TableColumn<>("Patient Name");
			patientNameColumn.setMinWidth(150);
			patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));

			TableColumn<Appointment, String> appointmentDateColumn = new TableColumn<>("Appointment Date");
			appointmentDateColumn.setMinWidth(150);
			appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

			TableColumn<Appointment, String> treatmentColumn = new TableColumn<>("Treatment");
			treatmentColumn.setMinWidth(150);
			treatmentColumn.setCellValueFactory(new PropertyValueFactory<>("treatments"));

			TableColumn<Appointment, String> startTimeColumn = new TableColumn<>("Start Time");
			startTimeColumn.setMinWidth(150);
			startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));

			TableColumn<Appointment, String> endTimeColumn = new TableColumn<>("End Time");
			endTimeColumn.setMinWidth(150);
			endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));

			tableEight.getColumns().addAll(patientNameColumn, appointmentDateColumn, startTimeColumn, endTimeColumn,
					treatmentColumn);

			flag8 = false;
		}
		return tableEight;

	}
	
	private SplitPane DentistManagementInterface() { // Dentist Management

		// ComboBox

		ComboBox<String> cbox = new ComboBox<String>();
		ObservableList<String> list = FXCollections.observableArrayList("Male", "Female");
		cbox.setItems(list);

		// Layouts
		SplitPane split = new SplitPane();
		GridPane gp = new GridPane();
		VBox vbox = new VBox(40);
		VBox vbox1 = new VBox(30);
		VBox vbox2 = new VBox(15);
		HBox hbox1 = new HBox(30);
		HBox hbox2 = new HBox(30);
		HBox hbox3 = new HBox(30);
		BorderPane border = new BorderPane();
		Text dentistReg = new Text("Dentist Registration");
		Text dentistProfile = new Text("Dentist Profile");
		Text appointScheduling = new Text("Appointment Scheduling");
		Text prev = new Text("Back");
		Label introAdd = new Label("Add a Dentist");
		Label dName = new Label("Dentist Name:");
		Label dAge = new Label("Dentist Age:");
		Label dPhone = new Label("Dentist Phone:");
		Label dEmail = new Label("Dentist Email:");
		Label dAddress = new Label("Dentist Address");
		Label dGraduate = new Label("Graduate From:");
		Label dSex = new Label("Dentist Sex:");
		Label dExp = new Label("Work Experience");
		Label userName = new Label("User Name:");
		Label pass = new Label("Password:");
		Label intro = new Label("Dentists Shown");
		Label intro2 = new Label("Appointments Info");
		TextField dNametxt = new TextField();
		TextField dAgetxt = new TextField();
		TextField dPhonetxt = new TextField();
		TextField dEmailtxt = new TextField();
		TextField dExptxt = new TextField();
		TextField dGraduatetxt = new TextField();
		TextField dAddresstxt = new TextField();
		TextField userNametxt = new TextField();
		TextField passtxt = new TextField();
		Button save = new Button("Save");
		Button update = new Button("Update");
		Button delete = new Button("Delete");

		// Edits
		gp.setVgap(40);
		gp.setHgap(40);
		vbox1.setAlignment(Pos.CENTER);
		vbox2.setAlignment(Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		hbox3.setAlignment(Pos.CENTER);
		vbox1.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, null, null)));
		split.setDividerPositions(0.24);
		border.setPadding(new Insets(20, 20, 20, 20));
		vbox.setPadding(new Insets(30, 30, 30, 30));
		vbox2.setPadding(new Insets(60, 60, 60, 60));
		save.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 20px;");
		update.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 20px;");

		delete.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 20px;");

		border.setStyle("-fx-background-image:url('file:basicPage.jpg');-fx-background-size:cover");
		vbox.setStyle("-fx-background-image:url('file:basicPage.jpg');-fx-background-size:cover");
		border.setCenter(
				new ImageView(new Image("C:\\Users\\user\\eclipse-workspace\\DataBaseFinalProject\\logo2.png")));
		Font font = (Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 15));
		introAdd.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 20));
		intro2.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 20));
		dentistProfile.setFont(font);
		dentistReg.setFont(font);
		appointScheduling.setFont(font);
		prev.setFont(font);
		dName.setFont(font);
		dAge.setFont(font);
		dPhone.setFont(font);
		dEmail.setFont(font);
		dAddress.setFont(font);
		dGraduate.setFont(font);
		dExp.setFont(font);
		userName.setFont(font);
		pass.setFont(font);
		dSex.setFont(font);
		intro.setFont(font);
		dNametxt.setPromptText("Name");
		dAgetxt.setPromptText("Age");
		dPhonetxt.setPromptText("Phone");
		dEmailtxt.setPromptText("Email");
		dAddresstxt.setPromptText("Address");
		dGraduatetxt.setPromptText("University");
		dExptxt.setPromptText("Experience");
		passtxt.setPromptText("Password");
		userNametxt.setPromptText("User Name");
		hbox2.setVisible(false);

		// Positions
		gp.add(dName, 0, 0);
		gp.add(dAge, 0, 1);
		gp.add(dNametxt, 1, 0);
		gp.add(dAgetxt, 1, 1);
		gp.add(dGraduate, 0, 2);
		gp.add(dGraduatetxt, 1, 2);
		gp.add(dSex, 0, 3);
		gp.add(cbox, 1, 3);
		gp.add(dPhone, 2, 0);
		gp.add(dEmail, 2, 1);
		gp.add(dPhonetxt, 3, 0);
		gp.add(dEmailtxt, 3, 1);
		gp.add(dExp, 2, 2);
		gp.add(dExptxt, 3, 2);
		gp.add(dAddress, 2, 3);
		gp.add(dAddresstxt, 3, 3);
		gp.add(userName, 0, 4);
		gp.add(userNametxt, 1, 4);
		gp.add(pass, 2, 4);
		gp.add(passtxt, 3, 4);
		hbox2.getChildren().addAll(delete, update);
		gp.add(save, 1, 6);
		GridPane.setHalignment(save, HPos.RIGHT);
		vbox.getChildren().addAll(introAdd, gp, hbox2);
		vbox1.getChildren().addAll(dentistReg, dentistProfile, appointScheduling, prev);
		vbox2.getChildren().addAll(intro, dentistProfileTableView());
		split.getItems().addAll(vbox1, border);

		// Events
		dentistReg.setOnMouseMoved(e -> {
			dentistReg.setStyle("-fx-underline: true;");
		});
		dentistReg.setOnMouseExited(e -> {
			dentistReg.setStyle("-fx-underline: false;");
		});
		// ***
		dentistProfile.setOnMouseMoved(e -> {
			dentistProfile.setStyle("-fx-underline: true;");
		});
		dentistProfile.setOnMouseExited(e -> {
			dentistProfile.setStyle("-fx-underline: false;");
		});
		// ***
		appointScheduling.setOnMouseMoved(e -> {
			appointScheduling.setStyle("-fx-underline: true;");
		});
		appointScheduling.setOnMouseExited(e -> {
			appointScheduling.setStyle("-fx-underline: false;");
		});
		// ***
		prev.setOnMouseMoved(e -> {
			prev.setStyle("-fx-underline: true;");
		});
		prev.setOnMouseExited(e -> {
			prev.setStyle("-fx-underline: false;");
		});
		// ***

		prev.setOnMouseClicked(e -> {

			stage.setScene(scene6);
			stage.setTitle("Clinic System");
			stage.show();
		});

		dentistReg.setOnMouseClicked(e -> {

			split.getItems().remove(1);
			split.getItems().add(1, vbox);
			split.setDividerPositions(0.22);
			dNametxt.clear();
			dAgetxt.clear();
			dPhonetxt.clear();
			dEmailtxt.clear();
			dAddresstxt.clear();
			dExptxt.clear();
			dGraduatetxt.clear();
			passtxt.clear();
			userNametxt.clear();
			cbox.getSelectionModel().clearSelection();
			save.setVisible(true);
			hbox2.setVisible(false);

		});

		save.setOnAction(e -> {

			// DataBase*******************************************
			try {
				pstatement = connect.prepareStatement(
						"insert into dentists (dentistName,dentistPhone,dentistEmail,dentistAge,dentistSex,dentistAddress,dentistGradguate,dentistExp,userName,pass) values (?,?,?,?,?,?,?,?,?,?)");

				pstatement.setString(1, dNametxt.getText());
				pstatement.setString(2, dPhonetxt.getText());
				pstatement.setString(3, dEmailtxt.getText());
				pstatement.setInt(4, Integer.parseInt(dAgetxt.getText()));
				pstatement.setString(5, cbox.getSelectionModel().getSelectedItem().toString());
				pstatement.setString(6, dAddresstxt.getText());
				pstatement.setString(7, dGraduatetxt.getText());
				pstatement.setString(8, dExptxt.getText());
				pstatement.setString(9, userNametxt.getText());
				pstatement.setString(10, passtxt.getText());
				pstatement.executeUpdate();

				ResultSet set = null;
				int max = 0;
				pstatement = connect.prepareStatement("select Max(dentistId) from dentists");
				set = pstatement.executeQuery();
				if (set.next()) {
					max = set.getInt(1);
				}

				tableFive.getItems()
						.add(new Dentists(max, dNametxt.getText(), userNametxt.getText(), passtxt.getText()));
				tableFive.refresh();
				// *******************************************************
				dNametxt.clear();
				dAgetxt.clear();
				dPhonetxt.clear();
				dEmailtxt.clear();
				dAddresstxt.clear();
				dExptxt.clear();
				dGraduatetxt.clear();
				passtxt.clear();
				userNametxt.clear();
				cbox.getSelectionModel().clearSelection();

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Successfully inserted");
				alert.show();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		});

		dentistProfile.setOnMouseClicked(e -> {

			split.getItems().remove(1);
			split.getItems().add(1, border);
			split.setDividerPositions(0.22);
			border.setCenter(null);
			border.setCenter(vbox2);

		});

		tableFive.setOnMouseClicked(e -> {

			// DataBase**************************************************
			if (e.getClickCount() == 2) {

				d = tableFive.getSelectionModel().getSelectedItem();
				int dId = d.getDentistId();
				try {
					ResultSet set = null;
					pstatement = connect.prepareStatement("select * from dentists where dentistId = ?");
					pstatement.setInt(1, dId);
					set = pstatement.executeQuery();

					if (set.next()) {
						dNametxt.setText(set.getString("dentistName"));
						dAgetxt.setText(set.getInt("dentistAge") + "");
						dPhonetxt.setText(set.getString("dentistPhone"));
						dEmailtxt.setText(set.getString("dentistEmail"));
						dAddresstxt.setText(set.getString("dentistAddress"));
						dExptxt.setText(set.getInt("dentistExp") + "");
						dGraduatetxt.setText(set.getString("dentistGradguate"));
						userNametxt.setText(set.getString("userName"));
						passtxt.setText(set.getString("pass"));
						cbox.getSelectionModel().select(set.getString("dentistSex"));
					}
					set.close();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				introAdd.setVisible(false);
				hbox2.setVisible(true);
				split.getItems().remove(1);
				split.getItems().add(1, vbox);
				split.setDividerPositions(0.22);
				save.setVisible(false);

			}

		});

		update.setOnAction(e -> {

			// DataBase*****************************************************
			try {
				pstatement = connect.prepareStatement(
						"update dentists set dentistName=?,dentistPhone=?,dentistEmail=?,dentistAge=?,dentistSex=?,dentistAddress=?,dentistGradguate=?,dentistExp=?,userName=?,pass=? where dentistId=?");

				pstatement.setString(1, dNametxt.getText());
				pstatement.setString(2, dPhonetxt.getText());
				pstatement.setString(3, dEmailtxt.getText());
				pstatement.setInt(4, Integer.parseInt(dAgetxt.getText()));
				pstatement.setString(5, cbox.getSelectionModel().getSelectedItem().toString());
				pstatement.setString(6, dAddresstxt.getText());
				pstatement.setString(7, dGraduatetxt.getText());
				pstatement.setString(8, dExptxt.getText());
				pstatement.setString(9, userNametxt.getText());
				pstatement.setString(10, passtxt.getText());
				pstatement.setInt(11, d.getDentistId());
				
				pstatement.executeUpdate();
				d.setDentistName(dNametxt.getText());
				d.setUserName(userNametxt.getText());
				d.setPass(passtxt.getText());
				tableFive.refresh();
				
//				pstatement = connect.prepareStatement(
//						"update appointments set dentistName=?");
//				pstatement.setString(1,dNametxt.getText());
//				tableTwo.refresh();
//				tableThree.refresh();
//				tableFour.refresh();
				

				split.getItems().remove(1);
				split.getItems().add(1, border);
				split.setDividerPositions(0.22);
				border.setCenter(null);
				border.setCenter(vbox2);

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Successfully updated");
				alert.show();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		});

		delete.setOnAction(e -> {

			try {

				// DataBase*********************************************************
				pstatement = connect.prepareStatement("delete from dentists where dentistId = ?");
				pstatement.setInt(1, d.getDentistId());
				pstatement.executeUpdate();
				tableFive.refresh();

				pstatement = connect
						.prepareStatement("update dentists set dentistId = dentistId - 1 where dentistId > ?");
				pstatement.setInt(1, d.getDentistId());
				pstatement.execute();

				// For Auto_Increment
				ResultSet set = null;
				int maxId;
				pstatement = connect.prepareStatement("select Max(dentistId) from dentists");
				set = pstatement.executeQuery();
				if (set.next()) {
					maxId = set.getInt(1);
				} else {
					maxId = 0;
				}
				set.close();
				pstatement = connect.prepareStatement("alter table dentists auto_increment = ?");
				pstatement.setInt(1, maxId + 1);
				pstatement.executeUpdate();

			} catch (SQLException ex) {
				System.out.println("###########3");
			}

			// delete From tableView
			ResultSet set = null;
			try {
				pstatement = connect.prepareStatement("select * from dentists");
				set = pstatement.executeQuery();

				tableFive.getItems().clear();

				while (set.next()) {
					tableFive.getItems()
							.add(new Dentists(set.getInt(1), set.getString(2), set.getString(3), set.getString(4)));
					tableFive.refresh();
				}
				set.close();
			} catch (Exception ex) {

			}

			split.getItems().remove(1);
			split.getItems().add(1, border);
			split.setDividerPositions(0.22);
			border.setCenter(null);
			border.setCenter(vbox2);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Successfully updated");
			alert.show();

		});

		appointScheduling.setOnMouseClicked(e -> {

			split.getItems().remove(1);
			split.getItems().add(1, border);
			split.setDividerPositions(0.22);
			border.setCenter(null);
			border.setTop(null);
			border.setTop(intro2);
			border.setPadding(new Insets(30, 30, 30, 30));
			border.setCenter(appointmentSchedulingTableView());

		});

		return split;
	}

	private SplitPane PatientManagementInterface() { // Patient Management


		// Layouts
		SplitPane split = new SplitPane();
		BorderPane border = new BorderPane();
		VBox vbox = new VBox(50);
		VBox vbox2 = new VBox(15);
		VBox vbox3 = new VBox(10);
		VBox vbox4 = new VBox(15);
		VBox vbox5 = new VBox(15);
		HBox hbox = new HBox(15);
		HBox hbox2 = new HBox(10);
		HBox hbox3 = new HBox(15);
		HBox hbox6 = new HBox(15);
		HBox hboxnew = new HBox(15);
		HBox hbx = new HBox(15);
		Text insert = new Text("Patient Registration");
		Text appoint = new Text("Appointment Scheduling");
		Text prev = new Text("Back");
		TextField searchPApp = new TextField();
		TextField searchDApp = new TextField();
		TextField nametxt = new TextField();
		TextField agetxt = new TextField();
		TextField phonetxt = new TextField();
		TextField emailtxt = new TextField();
		TextField pnametxt = new TextField();
		TextField datetxt = new TextField();
		TextField sTimetxt = new TextField();
		TextField eTimetxt = new TextField();
		TextField dentisttxt = new TextField();
		TextField treatmenttxt = new TextField();
		TextField searchtxt = new TextField();
		Label intro = new Label("Patients Info");
		Label introApp = new Label("Appointments Info");
		Button add = new Button("Add");
		Button delete = new Button("Delete");
		Button update = new Button("Update");
		Button addAppoint = new Button("Add Appointment");
		Button saveUpdate = new Button("Save update");
		Button done = new Button("Done");
		Button deleteApp = new Button("Delete");
		Button updateApp = new Button("Update");
		Button doneApp = new Button("Done");

		// Editing
		border.setStyle("-fx-background-image:url('file:basicPage.jpg');-fx-background-size:cover");
		add.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 30px;");
		addAppoint.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 30px;");
		delete.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 30px;");
		update.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 30px;");
		saveUpdate.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 30px;");
		done.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 5px 15px;");
		deleteApp.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 30px;");
		updateApp.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 30px;");
		doneApp.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 5px 15px;");

		border.setPadding(new Insets(20, 20, 20, 20));
		hbox.setPadding(new Insets(10, 10, 10, 10));
		hbox6.setPadding(new Insets(10, 10, 10, 10));
		vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
		split.setDividerPositions(0.22);
		Font font = (Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 15));
		intro.setFont(font);
		intro.setTextFill(Color.RED);
		introApp.setFont(font);
		introApp.setTextFill(Color.RED);
		insert.setFont(font);
		appoint.setFont(font);
		prev.setFont(font);
		border.setCenter(
				new ImageView(new Image("C:\\Users\\user\\eclipse-workspace\\DataBaseFinalProject\\logo.png")));
		vbox.setAlignment(Pos.CENTER);
		hboxnew.setAlignment(Pos.CENTER);
		vbox5.setAlignment(Pos.CENTER);
		hbox.setAlignment(Pos.CENTER);
		vbox2.setAlignment(Pos.CENTER);
		vbox3.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		vbox4.setAlignment(Pos.CENTER);
		hbox3.setAlignment(Pos.CENTER);
		hbox6.setAlignment(Pos.CENTER);
		hbx.setAlignment(Pos.CENTER);
		searchPApp.setPromptText("Search by patient");
		searchDApp.setPromptText("Search by dentist");
		agetxt.setPromptText("Age");
		nametxt.setPromptText("Name");
		phonetxt.setPromptText("Phone");
		emailtxt.setPromptText("Email");
		searchtxt.setPromptText("Search By Name");
		pnametxt.setPromptText("Name");
		datetxt.setPromptText("Y/M/D");
		dentisttxt.setPromptText("D_Name");
		treatmenttxt.setPromptText("treatment");
		sTimetxt.setPromptText("00:00");
		eTimetxt.setPromptText("00:00");

		// Initial Values for textFields
		nametxt.setText("");
		agetxt.setText("");
		phonetxt.setText("");
		emailtxt.setText("");
		searchtxt.setText("");
		searchPApp.setText("");
		searchDApp.setText("");
		// ****************************

		// Positions For Layouts
		hbx.getChildren().addAll(searchPApp, searchDApp);
		hboxnew.getChildren().addAll(searchtxt, intro);
		hbox.getChildren().addAll(add, delete, update, addAppoint);
		vbox.getChildren().addAll(insert, appoint, prev);
		vbox2.getChildren().addAll(hboxnew, patientRegistrationTableView(), hbox);
		hbox2.getChildren().addAll(nametxt, agetxt, phonetxt, emailtxt);
		vbox3.getChildren().addAll(hbox2, done);
		hbox3.getChildren().addAll(pnametxt, datetxt, sTimetxt, eTimetxt, dentisttxt, treatmenttxt);
		vbox5.getChildren().addAll(hbox3, doneApp);
		hbox6.getChildren().addAll(deleteApp, updateApp);
		vbox4.getChildren().addAll(hbx, appointmentSchedulingTableView(), hbox6);
		split.getItems().addAll(vbox, border);

		// Events inside this interface
		insert.setOnMouseMoved(e -> {
			insert.setStyle("-fx-underline: true;");
		});
		insert.setOnMouseExited(e -> {
			insert.setStyle("-fx-underline: false;");
		});
		// ***
		appoint.setOnMouseMoved(e -> {
			appoint.setStyle("-fx-underline: true;");
		});
		appoint.setOnMouseExited(e -> {
			appoint.setStyle("-fx-underline: false;");
		});
		// ***
		prev.setOnMouseMoved(e -> {
			prev.setStyle("-fx-underline: true;");
		});
		prev.setOnMouseExited(e -> {
			prev.setStyle("-fx-underline: false;");
		});
		// ***

		prev.setOnMouseClicked(e -> { // Back
			stage.setScene(scene6);
			stage.setTitle("Clinic System");
			stage.show();
		});

		insert.setOnMouseClicked(e -> { // Patient Registration
			border.setTop(null);
			border.setCenter(null);
			border.setBottom(null);
			border.setCenter(vbox2);
			insert.setDisable(true);
			appoint.setDisable(false);
		});

		appoint.setOnMouseClicked(e -> { // Appointments

			border.setPadding(new Insets(30, 30, 30, 30));
			border.setTop(introApp);
			BorderPane.setAlignment(introApp, Pos.CENTER);
			border.setCenter(null);
			border.setBottom(null);
			border.setCenter(vbox4);
			appoint.setDisable(true);
			insert.setDisable(false);

		});

		// ************************************************************
		add.setOnAction(e -> { // Add patient
			border.setBorder(null);
			vbox3.setVisible(true);
			border.setBottom(vbox3);

		});

		done.setOnAction(e -> { // Insertion Completed

			// DataBase ***********************************
			ResultSet set = null;
			try {
				if (!nametxt.getText().equals("") && !agetxt.getText().equals("") && !phonetxt.getText().equals("")
						&& !emailtxt.getText().equals("")) {
					pstatement = connect.prepareStatement(
							"insert into patients (patientName , patientAge,patientPhone,patientEmail) values (?,?,?,?)");
					pstatement.setString(1, nametxt.getText());
					pstatement.setString(2, agetxt.getText());
					pstatement.setString(3, phonetxt.getText());
					pstatement.setString(4, emailtxt.getText());
					pstatement.executeUpdate();
					pstatement = connect.prepareStatement(
							"select * from patients where patientId = (select Max(patientId) from patients)");
					set = pstatement.executeQuery();
					if (set.next()) {
						tableOne.getItems().add(new Patient(set.getInt(1), set.getInt(3), set.getString(2),
								set.getString(4), set.getString(5)));
						tableOne.refresh();
					}
					set.close();

					nametxt.clear();
					agetxt.clear();
					phonetxt.clear();
					emailtxt.clear();
					vbox3.setVisible(false);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Successfully inserted ! ");
					alert.show();
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Incorrect entered");
					alert.show();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			// ********************************************

		});

		delete.setOnAction(e -> { // Delete Patient

			int index = tableOne.getSelectionModel().getSelectedIndex();

			if (index >= 0) {
				Patient p = tableOne.getSelectionModel().getSelectedItem();

				// delete From DataBase
				int id = p.getId();
				int count = 0;
				try {

					ResultSet set2 = null;
					pstatement = connect
							.prepareStatement("select Min(appointId) from appointments where patientId = ?");
					pstatement.setInt(1, id);
					set2 = pstatement.executeQuery();
					int min;
					if (set2.next()) {
						min = set2.getInt(1);

					} else {
						min = 0;
					}
					set2.close();

					ResultSet setC = null;
					pstatement = connect
							.prepareStatement("select count(AppointId) from appointments where patientId = ?");
					pstatement.setInt(1, id);
					setC = pstatement.executeQuery();
					if (setC.next()) {
						count = setC.getInt(1);
					}

					pstatement = connect.prepareStatement("delete from patients where patientId = ?");
					pstatement.setInt(1, id);
					pstatement.executeUpdate();
					tableOne.refresh();
					tableTwo.refresh();

					pstatement = connect
							.prepareStatement("update patients set patientId = patientId - 1 where patientId > ?");
					pstatement.setInt(1, id);
					pstatement.execute();

					pstatement = connect
							.prepareStatement("update appointments set appointId = appointId - ? where appointId > ?");
					pstatement.setInt(1, count);
					pstatement.setInt(2, min - 1);
					pstatement.executeUpdate();
					// **************************************

					// For Auto_Increment
					ResultSet set = null;
					int maxId;
					pstatement = connect.prepareStatement("select Max(patientId) from patients");
					set = pstatement.executeQuery();
					if (set.next()) {
						maxId = set.getInt(1);
					} else {
						maxId = 0;
					}
					set.close();
					pstatement = connect.prepareStatement("alter table patients auto_increment = ?");
					pstatement.setInt(1, maxId + 1);
					pstatement.executeUpdate();

					ResultSet set3 = null;
					int maxIdAppoint;
					pstatement = connect.prepareStatement("select Max(appointId) from appointments");
					set3 = pstatement.executeQuery();
					if (set3.next()) {
						maxIdAppoint = set3.getInt(1);
					} else {
						maxIdAppoint = 0;
					}
					pstatement = connect.prepareStatement("alter table appointments auto_increment = ?");
					pstatement.setInt(1, maxIdAppoint + 1);
					pstatement.executeUpdate();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				// ***************

				// delete From tableView
				ResultSet set = null;
				try {
					pstatement = connect.prepareStatement("select * from patients");
					set = pstatement.executeQuery();

					tableOne.getItems().clear();

					while (set.next()) {
						tableOne.getItems().add(new Patient(set.getInt(1), set.getInt(3), set.getString(2),
								set.getString(4), set.getString(5)));
						tableOne.refresh();
					}
					set.close();

					pstatement = connect.prepareStatement("select * from appointments");
					set = pstatement.executeQuery();
					tableTwo.getItems().clear();
					while (set.next()) {
						tableTwo.getItems()
								.add(new Appointment(set.getInt(1), set.getInt(2), set.getInt(3), set.getString(4),
										set.getString(5), set.getString(6), set.getString(7), set.getString(8),
										set.getString(9)));
						tableTwo.refresh();
					}
					set.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Please select an item");
				alert.show();

			}

		});

		searchtxt.setOnAction(e -> { // Search On patient by his name

			if (searchtxt.getText().equals("")) {
				tableOne.getItems().clear();
				ResultSet set = null;
				try {
					pstatement = connect.prepareStatement("select * from patients");
					set = pstatement.executeQuery();
					while (set.next()) {
						tableOne.getItems().add(new Patient(set.getInt(1), set.getInt(3), set.getString(2),
								set.getString(4), set.getString(5)));
						tableOne.refresh();
					}
					set.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else {

				try {
					tableOne.getItems().clear();
					ResultSet set = null;
					pstatement = connect.prepareStatement("select * from patients where patientName = ?");
					pstatement.setString(1, searchtxt.getText());
					set = pstatement.executeQuery();
					while (set.next()) {
						tableOne.getItems().add(new Patient(set.getInt(1), set.getInt(3), set.getString(2),
								set.getString(4), set.getString(5)));
						tableOne.refresh();
					}
					set.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}

		});

		update.setOnAction(e -> { // Update Patient info

			int index = tableOne.getSelectionModel().getSelectedIndex();

			if (index >= 0) {
				vbox3.setVisible(true);
				border.setBottom(null);
				border.setBottom(vbox3);
				Patient p = tableOne.getSelectionModel().getSelectedItem();
				nametxt.setText(p.getName());
				agetxt.setText(String.valueOf(p.getAge()));
				phonetxt.setText(p.getPhone());
				emailtxt.setText(p.getEmail());

				done.setOnAction(ex -> {

					// OnDataBase
					try {
						pstatement = connect.prepareStatement(
								"update patients SET patientName = ?, patientAge = ?, patientPhone = ?, patientEmail = ? where patientId = ?");
						pstatement.setString(1, nametxt.getText());
						pstatement.setInt(2, Integer.parseInt(agetxt.getText()));
						pstatement.setString(3, phonetxt.getText());
						pstatement.setString(4, emailtxt.getText());
						pstatement.setInt(5, p.getId());
						pstatement.execute();
						tableOne.refresh();

						pstatement = connect
								.prepareStatement("update appointments SET patientName =? where patientId = ?");
						pstatement.setString(1, nametxt.getText());
						pstatement.setInt(2, p.getId());
						pstatement.executeUpdate();
						ResultSet sets = null;
						pstatement = connect.prepareStatement("select * from appointments");
						sets = pstatement.executeQuery();
						tableTwo.getItems().clear();
						while (sets.next()) {
							tableTwo.getItems()
									.add(new Appointment(sets.getInt(1), sets.getInt(2), sets.getInt(3),
											sets.getString(4), sets.getString(5), sets.getString(6), sets.getString(7),
											sets.getString(8), sets.getString(9)));
							tableTwo.refresh();
						}
						sets.close();
						tableOne.getItems().clear();
						ResultSet set = null;
						pstatement = connect.prepareStatement("select * from patients");
						set = pstatement.executeQuery();
						while (set.next()) {
							tableOne.getItems().add(new Patient(set.getInt(1), set.getInt(3), set.getString(2),
									set.getString(4), set.getString(5)));
							tableOne.refresh();
						}
						set.close();

						tableOne.refresh();
						border.setBottom(null);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("Successfully updated");
						alert.show();

					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				});

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Please select an item");
				alert.show();
			}

		});

		deleteApp.setOnAction(e -> { // Delete appointment

			int index = tableTwo.getSelectionModel().getSelectedIndex();

			if (index >= 0) {

				Appointment ap = tableTwo.getSelectionModel().getSelectedItem();

				try {
					pstatement = connect.prepareStatement("delete from appointments where appointId = ?");
					pstatement.setInt(1, ap.getAppointmentId());
					pstatement.executeUpdate();

					pstatement = connect
							.prepareStatement("update appointments set appointId = appointId - 1 where appointId > ?");
					pstatement.setInt(1, ap.getAppointmentId());
					pstatement.executeUpdate();

					ResultSet set = null;
					int maxId;
					pstatement = connect.prepareStatement("select Max(appointId) from appointments");
					set = pstatement.executeQuery();
					if (set.next()) {
						maxId = set.getInt(1);
					} else {
						maxId = 0;
					}
					set.close();
					pstatement = connect.prepareStatement("alter table appointments auto_increment = ?");
					pstatement.setInt(1, maxId + 1);
					pstatement.executeUpdate();

					// **************************************

					ResultSet sett = null;
					tableTwo.getItems().clear();
					pstatement = connect.prepareStatement("select * from appointments");
					sett = pstatement.executeQuery();
					while (sett.next()) {
						tableTwo.getItems()
								.add(new Appointment(sett.getInt(1), sett.getInt(2), sett.getInt(3), sett.getString(4),
										sett.getString(5), sett.getString(6), sett.getString(7), sett.getString(8),
										sett.getString(9)));
						tableTwo.refresh();
					}
					sett.close();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			} else {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Please select an item");
				alert.show();

			}

		});

		updateApp.setOnAction(e -> { // Update appointments

			int index = tableTwo.getSelectionModel().getSelectedIndex();

			if (index >= 0) {

				vbox5.setVisible(true);
				border.setBottom(null);
				border.setBottom(vbox5);

				app = tableTwo.getSelectionModel().getSelectedItem();
				pnametxt.setText(app.getPatientName());
				dentisttxt.setText(app.getDentist());
				datetxt.setText(app.getDate());
				sTimetxt.setText(app.getStartTime());
				eTimetxt.setText(app.getEndTime());
				treatmenttxt.setText(app.getTreatments());

				doneApp.setOnAction(ex -> {

					try {
						ResultSet set = null;
						pstatement = connect.prepareStatement(
								"update appointments set patientName =?,dentistName=?,appointDate=?,appointStart=?,appointEnd=?,treatment=? where appointId=? AND patientId = ?");
						pstatement.setString(1, pnametxt.getText());
						pstatement.setString(2, dentisttxt.getText());
						pstatement.setString(3, datetxt.getText());
						pstatement.setString(4, sTimetxt.getText());
						pstatement.setString(5, eTimetxt.getText());
						pstatement.setString(6, treatmenttxt.getText());
						pstatement.setInt(7, app.getAppointmentId());
						pstatement.setInt(8, app.getPatientId());
						pstatement.executeUpdate();

						pstatement = connect
								.prepareStatement("update patients set patientName = ? where patientId = ?");
						pstatement.setString(1, pnametxt.getText());
						pstatement.setInt(2, app.getPatientId());
						pstatement.executeUpdate();

						tableOne.getSelectionModel().select(app.getPatientId() - 1);
						Patient p = tableOne.getSelectionModel().getSelectedItem();
						p.setName(pnametxt.getText());
						tableOne.refresh();

						pstatement = connect.prepareStatement("select * from appointments");
						set = pstatement.executeQuery();
						tableTwo.getItems().clear();

						while (set.next()) {
							tableTwo.getItems()
									.add(new Appointment(set.getInt(1), set.getInt(2), set.getInt(3), set.getString(4),
											set.getString(5), set.getString(6), set.getString(7), set.getString(8),
											set.getString(9)));
							tableTwo.refresh();
						}
						set.close();

						tableTwo.refresh();

						border.setBottom(null);

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("Successfully Updated");
						alert.show();

					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				});

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Please select an item");
				alert.show();
			}

		});

		addAppoint.setOnAction(e -> { // Add appointment

			int index = tableOne.getSelectionModel().getSelectedIndex();

			if (index >= 0) {
				p = tableOne.getSelectionModel().getSelectedItem();
				scene10 = new Scene(forReserveAppointment(), 900, 600);
				stage.setScene(scene10);
				stage.setTitle("Reserve Appointment");
				stage.show();

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Please select an item");
				alert.show();
			}

		});

		searchPApp.setOnAction(e -> { // Search by patient name
			if (searchPApp.getText().equals("")) {
				tableTwo.getItems().clear();
				ResultSet set = null;
				try {
					pstatement = connect.prepareStatement("select * from appointments");
					set = pstatement.executeQuery();
					while (set.next()) {
						tableTwo.getItems()
								.add(new Appointment(set.getInt(1), set.getInt(2), set.getInt(3), set.getString(4),
										set.getString(5), set.getString(6), set.getString(7), set.getString(8),
										set.getString(9)));
						tableTwo.refresh();
					}
					set.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else {

				try {
					tableTwo.getItems().clear();
					ResultSet set = null;
					pstatement = connect.prepareStatement("select * from appointments where patientName = ?");
					pstatement.setString(1, searchPApp.getText());
					set = pstatement.executeQuery();
					while (set.next()) {
						tableTwo.getItems()
								.add(new Appointment(set.getInt(1), set.getInt(2), set.getInt(3), set.getString(4),
										set.getString(5), set.getString(6), set.getString(7), set.getString(8),
										set.getString(9)));
						tableTwo.refresh();
					}
					set.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		searchDApp.setOnAction(e -> { // Search by dentist name.
			if (searchDApp.getText().equals("")) {
				tableTwo.getItems().clear();
				ResultSet set = null;
				try {
					pstatement = connect.prepareStatement("select * from appointments");
					set = pstatement.executeQuery();
					while (set.next()) {
						tableTwo.getItems()
								.add(new Appointment(set.getInt(1), set.getInt(2), set.getInt(3), set.getString(4),
										set.getString(5), set.getString(6), set.getString(7), set.getString(8),
										set.getString(9)));
						tableTwo.refresh();
					}
					set.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else {

				try {
					tableTwo.getItems().clear();
					ResultSet set = null;
					pstatement = connect.prepareStatement("select * from appointments where dentistName = ?");
					pstatement.setString(1, searchDApp.getText());
					set = pstatement.executeQuery();
					while (set.next()) {
						tableTwo.getItems()
								.add(new Appointment(set.getInt(1), set.getInt(2), set.getInt(3), set.getString(4),
										set.getString(5), set.getString(6), set.getString(7), set.getString(8),
										set.getString(9)));
						tableTwo.refresh();
					}
					set.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		return split;

	}

	private SplitPane paymentManagementInterface() { // Payment Interface

		// Layouts
		SplitPane split = new SplitPane();
		BorderPane bd = new BorderPane();
		GridPane gp = new GridPane();
		VBox vbox = new VBox(30);
		VBox v1 = new VBox(30);
		VBox v2 = new VBox(20);
		VBox v3 = new VBox(15);
		VBox v4 = new VBox(15);
		VBox v5 = new VBox(15);
		VBox v6 = new VBox(15);
		HBox h1 = new HBox(15);
		HBox h2 = new HBox(15);
		HBox h3 = new HBox(15);
		HBox h4 = new HBox(15);
		Text patient = new Text("Patient's Payment");
		Text dentist = new Text("Dentist's Payment");
		Text prev = new Text("Back");
		TextField pnametxt = new TextField();
		// TextField pphonetxt = new TextField();
		TextField dnametxt = new TextField();
		// TextField dphonetxt = new TextField();
		TextField payMethodtxt = new TextField();
		TextField payAmounttxt = new TextField();
		TextField payDatetxt = new TextField();
		TextField treattxt = new TextField();
		TextField isPaidtxt = new TextField();
//		TextField dPaytxt = new TextField();
//		TextField dNotPaytxt = new TextField();
//		TextField Dpaytxt = new TextField();
//		TextField DNotPaytxt = new TextField();
//		Label dPayLabel = new Label("Dentist Salary Of The Paid:");
//		Label dNotPayLabel = new Label("Dentist Salary Of The Not Paid:");
//		Label DPayLabel = new Label("Doctor Salary Of The Paid:");
//		Label DNotPayLabel = new Label("Doctor Salary Of Not The Paid:");
		Label introP = new Label("Payments of patients");
		Label introd = new Label("Payments of dentists");
		Label intro = new Label("All Payments In Clinic");
		Button add = new Button("Add");
		Button delete = new Button("Delete");
		Button update = new Button("Update");
		Button done = new Button("Done");
		Button saveUpdates = new Button("Save Updates");

		// Edits
		vbox.setPadding(new Insets(20, 5, 20, 5));
		v5.setPadding(new Insets(20, 5, 20, 5));
		bd.setPadding(new Insets(40, 40, 40, 40));
		split.setDividerPositions(0.22);
		bd.setCenter(intro);
		bd.setStyle("-fx-background-image:url('file:basicPage.jpg');-fx-background-size:cover");
		add.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 30px;");
		delete.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 30px;");
		update.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 30px;");
		done.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 30px;");

		saveUpdates.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 10px 30px;");

		v1.setAlignment(Pos.CENTER);
		v4.setAlignment(Pos.CENTER);
		h1.setAlignment(Pos.CENTER);
		h2.setAlignment(Pos.CENTER);
		h3.setAlignment(Pos.CENTER);
		v2.setAlignment(Pos.CENTER);
		v3.setAlignment(Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		v5.setAlignment(Pos.CENTER);
		h4.setAlignment(Pos.CENTER);
		v6.setAlignment(Pos.CENTER);
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(10);
		gp.setHgap(10);
		v1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
		Font font = Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 18);
		intro.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 25));
//		dPayLabel.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 16));
//		dNotPayLabel.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 16));
//		DPayLabel.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 16));
//		DNotPayLabel.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 16));

		introP.setFont(font);
		introd.setFont(font);
		introP.setTextFill(Color.RED);
		introd.setTextFill(Color.RED);
		patient.setFont(font);
		dentist.setFont(font);
		prev.setFont(font);
		pnametxt.setPromptText("Patient name");
		// pphonetxt.setPromptText("Patient phone");
		dnametxt.setPromptText("Dentist name");
		// dphonetxt.setPromptText("Dentist phone");
		payAmounttxt.setPromptText("Amount");
		payDatetxt.setPromptText("Payment date");
		payMethodtxt.setPromptText("Payment Method");
		isPaidtxt.setPromptText("Yes / No");
		treattxt.setPromptText("Treatment");
		saveUpdates.setVisible(false);
		isPaidtxt.setVisible(false);

		// Positions
//		gp.add(dPayLabel, 0, 0);
//		gp.add(dNotPayLabel, 0, 1);
//		gp.add(DPayLabel, 0, 2);
//		gp.add(DNotPayLabel, 0, 3);
//		gp.add(dPaytxt, 1, 0);
//		gp.add(dNotPaytxt, 1, 1);
//		gp.add(Dpaytxt, 1, 2);
//		gp.add(DNotPaytxt, 1, 3);
		v6.getChildren().add(gp);
		v5.getChildren().addAll(tableSeven);
		h4.getChildren().addAll(dnametxt);
		v4.getChildren().addAll(introd, h4);
		h3.getChildren().addAll(payMethodtxt, payAmounttxt, payDatetxt, treattxt, isPaidtxt);
		v3.getChildren().addAll(h3, done, saveUpdates);
		h2.getChildren().addAll(add, delete, update);
		vbox.getChildren().addAll(tableSix, h2);
		h1.getChildren().addAll(pnametxt);
		v2.getChildren().addAll(introP, h1);
		v1.getChildren().addAll(patient, dentist, prev);
		split.getItems().addAll(v1, bd);

		// Events
		patient.setOnMouseMoved(e -> {
			patient.setStyle("-fx-underline: true; ");
		});

		patient.setOnMouseExited(e -> {
			patient.setStyle("-fx-underline: false; ");
		});

		// ***
		dentist.setOnMouseMoved(e -> {
			dentist.setStyle("-fx-underline: true; ");
		});

		dentist.setOnMouseExited(e -> {
			dentist.setStyle("-fx-underline: false; ");
		});

		// ***
		prev.setOnMouseMoved(e -> {
			prev.setStyle("-fx-underline: true; ");
		});

		prev.setOnMouseExited(e -> {
			prev.setStyle("-fx-underline: false; ");
		});

		// ***

		prev.setOnMouseClicked(e -> {
			stage.setTitle("Clinic System");
			stage.setScene(scene6);
			stage.show();
		});

		patient.setOnMouseClicked(e -> {
			bd.setCenter(null);
			bd.setTop(null);
			bd.setBottom(null);
			bd.setTop(v2);

		});

		pnametxt.setOnAction(e -> {

			if (!pnametxt.getText().isBlank()) {

				// DataBase *************************
				try {
					bd.setCenter(null);
					ResultSet set = null;
					pstatement = connect.prepareStatement(
							"select paymentId,paymentAmount,paymentMethod,paymentDate,treatment,isPaid from payments where patientName = ?");
					System.out.println("YEs");
					pstatement.setString(1, pnametxt.getText());
					// pstatement.setString(2, pphonetxt.getText());
					set = pstatement.executeQuery();
					tableSix.getItems().clear();
					while (set.next()) {

						bd.setCenter(vbox);
						tableSix.getItems().add(new Payments(set.getInt(1), set.getString(2), set.getString(3),
								set.getString(4), set.getString(5), set.getString(6)));
						tableSix.refresh();

					}
					set.close();
					pstatement = connect.prepareStatement("select patientName from patients where patientName = ?");
					pstatement.setString(1, pnametxt.getText());
					set = pstatement.executeQuery();
					if (!set.next()) {
						System.out.println("##");
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("Unknown Patient");
						alert.show();
					} else if (tableSix.getItems().isEmpty()) {
						bd.setCenter(vbox);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText(" No payments!!");
						alert.show();
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			} else {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Please enter the name");
				alert.show();

			}

		});

		add.setOnAction(e -> {

			bd.setBottom(null);
			bd.setBottom(v3);

		});

		done.setOnAction(e -> {

			if (!payAmounttxt.getText().isBlank() && !payMethodtxt.getText().isBlank()
					&& !payDatetxt.getText().isBlank() && !treattxt.getText().isBlank()) {

				try {

					// DataBase***********
					ResultSet set = null;
					int patId = 0;

					pstatement = connect.prepareStatement("select patientId from patients where patientName = ?");
					pstatement.setString(1, pnametxt.getText());
					set = pstatement.executeQuery();
					if (set.next()) {
						patId = set.getInt("patientId");
					}
					pstatement = connect.prepareStatement(
							"insert into payments (patientId,patientName,paymentMethod,paymentAmount,paymentDate,treatment,isPaid) values (?,?,?,?,?,?,?)");
					pstatement.setInt(1, patId);
					pstatement.setString(2, pnametxt.getText());
					pstatement.setString(3, payMethodtxt.getText());
					pstatement.setString(4, payAmounttxt.getText());
					pstatement.setString(5, payDatetxt.getText());
					pstatement.setString(6, treattxt.getText());
					pstatement.setString(7, "No");
					pstatement.execute();

					pstatement = connect.prepareStatement(
							"select paymentId from payments where paymentId = (select Max(paymentId) from payments)");
					set = pstatement.executeQuery();
					int appId = 0;
					if (set.next()) {
						appId = set.getInt(1);
					}
					tableSix.refresh();
					tableSix.getItems().add(new Payments(appId, payMethodtxt.getText(), payAmounttxt.getText(),
							payDatetxt.getText(), treattxt.getText(), "NO"));
					tableSix.refresh();

					bd.setBottom(null);
					payAmounttxt.clear();
					payMethodtxt.clear();
					payDatetxt.clear();
					treattxt.clear();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Successfully added !");
					alert.show();

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Incorrect insertion");
				alert.show();
			}

		});

		delete.setOnAction(e -> {

			int index = tableSix.getSelectionModel().getSelectedIndex();
			if (index >= 0) {

				try {

					pstatement = connect.prepareStatement("delete from payments  where paymentId = ?");
					pstatement.setInt(1, index + 1);
					pstatement.execute();
					tableSix.getItems().remove(index);

					pstatement = connect
							.prepareStatement("update payments SET paymentId = paymentId - 1 where paymentId > ?");
					pstatement.setInt(1, index + 1);
					pstatement.executeUpdate();

					ResultSet set = null;
					pstatement = connect.prepareStatement(
							"select paymentId from payments where paymentId = (select Max(paymentId) from payments)");
					set = pstatement.executeQuery();
					int max = 0;
					if (set.next()) {

						max = set.getInt(1);
					} else {
						max = 0;
					}
					set.close();
					pstatement = connect.prepareStatement("alter table payments auto_increment = ?");
					pstatement.setInt(1, max + 1);
					pstatement.execute();

					// For tableView
					pstatement = connect.prepareStatement(
							"select paymentId,paymentMethod,paymentAmount,paymentDate,treatment,isPaid from payments");
					set = pstatement.executeQuery();
					tableSix.getItems().clear();
					while (set.next()) {

						tableSix.getItems().add(new Payments(set.getInt(1), set.getString(2), set.getString(3),
								set.getString(4), set.getString(5), set.getString(6)));
						tableSix.refresh();

					}
					set.close();

				} catch (Exception ex) {

					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Faild delete !");
					alert.show();

				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Please select an item");
				alert.show();
			}

		});

		update.setOnAction(e -> {

			int index = tableSix.getSelectionModel().getSelectedIndex();

			if (index >= 0) {
				pa = tableSix.getSelectionModel().getSelectedItem();

				try {

					bd.setBottom(null);
					bd.setBottom(v3);
					isPaidtxt.setVisible(true);
					payMethodtxt.setText(pa.getPaymentMethod());
					payAmounttxt.setText(pa.getPaymentAmount());
					payDatetxt.setText(pa.getPaymentDate());
					treattxt.setText(pa.getTreatment());
					isPaidtxt.setText(pa.getIsPaid());
					saveUpdates.setVisible(true);
					done.setVisible(false);
				} catch (Exception ex) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Faild Update");
					alert.show();
				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Please select an item");
				alert.show();
			}

			saveUpdates.setOnAction(e2 -> {

				// DataBase*********************
				try {

					pstatement = connect.prepareStatement(
							"update payments SET paymentMethod = ?,paymentAmount = ?,paymentDate = ?, treatment = ? , isPaid = ? where paymentId = ?");
					pstatement.setString(1, payMethodtxt.getText());
					pstatement.setString(2, payAmounttxt.getText());
					pstatement.setString(3, payDatetxt.getText());
					pstatement.setString(4, treattxt.getText());
					pstatement.setString(5, isPaidtxt.getText());
					pstatement.setInt(6, index + 1);
					pstatement.executeUpdate();

					pa.setIsPaid(isPaidtxt.getText());
					pa.setTreatment(treattxt.getText());
					pa.setPaymentAmount(payAmounttxt.getText());
					pa.setPaymentDate(payDatetxt.getText());
					pa.setPaymentMethod(payMethodtxt.getText());
					tableSix.refresh();

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Successfully Updated ! ");
					alert.show();

					bd.setBottom(null);
					saveUpdates.setVisible(false);
					done.setVisible(true);
				} catch (Exception ex) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Faild Update !!");
					alert.show();
				}

			});
		});

		// Dentist

		dentist.setOnMouseClicked(e -> {
			bd.setTop(null);
			bd.setCenter(null);
			bd.setBottom(null);
			bd.setTop(v4);

		});

		dnametxt.setOnAction(e -> {

			if (!dnametxt.getText().isBlank()) {

				try {

					ResultSet set = null;
					pstatement = connect.prepareStatement("select dentistId from dentists where dentistName = ?");
					pstatement.setString(1, dnametxt.getText());
					set = pstatement.executeQuery();
					int id = 0;
					if (set.next()) {
						id = set.getInt(1);
					} else {
						id = 0;
					}

					if (id == 0) {
						bd.setCenter(null);
						bd.setBottom(null);
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Unknown dentist");
						alert.show();

					} else {
						bd.setCenter(null);
						bd.setBottom(null);
						bd.setCenter(v5);
						bd.setBottom(v6);
						ResultSet set2 = null;
						pstatement = connect
								.prepareStatement("select patientId from appointments where dentistName = ?");
						pstatement.setString(1, dnametxt.getText());
						set = pstatement.executeQuery();
						tableSeven.getItems().clear();
						int Pay = 0, NotPay = 0;
						while (set.next()) {
							int pId = set.getInt(1);
							pstatement = connect.prepareStatement(
									"select p.patientName,p.paymentAmount,p.paymentDate,p.treatment,p.isPaid from payments p where patientId = ? ");
							pstatement.setInt(1, pId);
							set2 = pstatement.executeQuery();
							while (set2.next()) {

								tableSeven.getItems().add(new Payments(set2.getString(1), set2.getString(2),
										set2.getString(3), set2.getString(4), set2.getString(5)));
								tableSeven.refresh();
							}
						}

						set.close();
						set2.close();

//						double dPay = Pay * 0.40;
//						double dNotPay = NotPay * 0.40;
//						double DPay = Pay * 0.60;
//						double DNotPay = NotPay * 0.60;

//						dPaytxt.setText(dPay + "");
//						dNotPaytxt.setText(dNotPay + "");
//						Dpaytxt.setText(DPay + "");
//						DNotPaytxt.setText(DNotPay + "");

					}

				} catch (Exception ex) {

				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Enter the name");
				alert.show();
			}

		});

		return split;

	}

	private SplitPane forReserveAppointment() { // Scheduling appointments

		// Layouts
		SplitPane split = new SplitPane();
		GridPane gp = new GridPane();
		VBox vbox = new VBox(15);
		VBox vbox2 = new VBox(15);
		VBox v = new VBox(10);
		BorderPane bd = new BorderPane();
		Label searchResult = new Label("Sorry !\n There's no available dentists\n at this Appointment");
		Label noAppoint = new Label("Sorry !\n There's no booked appointments \n for this patient!");
		Label showLabel = new Label("Patient's Appointments");
		Label dateLabel = new Label("Date:");
		Label sTimeLabel = new Label("Start Time:");
		Label eTimeLabel = new Label("End Time:");
		Label treatmentLabel = new Label("Treatment");
		Label info = new Label("Available Denitist");
		Label intro = new Label("Fill Patient's information of his appointment");
		Label intro2 = new Label("Scheduling appointments");
		TextField datetxt = new TextField();
		TextField sTimetxt = new TextField();
		TextField eTimetxt = new TextField();
		TextField treatmenttxt = new TextField();
		Button showAll = new Button("Show his Appointments");
		Button fillInfo = new Button("Appointment Info");
		Button prev = new Button("Back");
		Button search = new Button("Search For Dentists");
		Button book = new Button("Book");

		// Edits
		bd.setPadding(new Insets(50, 50, 50, 50));
		gp.setVgap(10);
		gp.setHgap(10);
		gp.setAlignment(Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		vbox2.setAlignment(Pos.CENTER);
		v.setAlignment(Pos.CENTER);
		vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
		split.setDividerPositions(0.29);
		showLabel.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 20));
		dateLabel.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 13));
		sTimeLabel.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 13));
		eTimeLabel.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 13));
		treatmentLabel.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 13));
		searchResult.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 13));
		noAppoint.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 17));
		intro.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 17));
		intro2.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 17));
		info.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 13));
		searchResult.setTextFill(Color.BLACK);
		noAppoint.setTextFill(Color.BLACK);
		showLabel.setTextFill(Color.BLACK);
		intro2.setTextFill(Color.BLACK);
		info.setTextFill(Color.BLACK);
		info.setTextFill(Color.BLACK);
		showAll.setMinWidth(240);
		fillInfo.setMinWidth(240);
		prev.setMinWidth(240);
		datetxt.setPromptText("YYYY/MM/DD");
		sTimetxt.setPromptText("HH:MM");
		eTimetxt.setPromptText("HH:MM");
		treatmenttxt.setPromptText("Treatment");
		book.setVisible(false);
		bd.setStyle("-fx-background-image:url('file:basicPage.jpg');-fx-background-size:cover");
		search.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: WHITE; -fx-font-size: 16px; -fx-padding: 3px 8px;");
		book.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: WHITE; -fx-font-size: 16px; -fx-padding: 3px 8px;");
		showAll.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 20;\n"
				+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: red;\n"
				+ "-fx-background-color: transparent;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
				+ "-fx-background-radius: 25 25 25 25");
		fillInfo.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 20;\n"
				+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: red;\n"
				+ "-fx-background-color: transparent;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
				+ "-fx-background-radius: 25 25 25 25");

		prev.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 20;\n"
				+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: red;\n"
				+ "-fx-background-color: transparent;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
				+ "-fx-background-radius: 25 25 25 25");

		showAll.setOnMouseMoved(e -> {
			showAll.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 20;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: red;\n"
					+ "-fx-background-color: #d8d9e0;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
					+ "-fx-background-radius: 25 25 25 25");
		});
		showAll.setOnMouseExited(e -> {
			showAll.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 20;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: red;\n"
					+ "-fx-background-color: transparent;\n" + "-fx-border-color: #d8d9e0;\n"
					+ "-fx-border-width:  3.5;" + "-fx-background-radius: 25 25 25 25");
		});

		fillInfo.setOnMouseMoved(e -> {
			fillInfo.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 20;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: red;\n"
					+ "-fx-background-color: #d8d9e0;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
					+ "-fx-background-radius: 25 25 25 25");
		});
		fillInfo.setOnMouseExited(e -> {
			fillInfo.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 20;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: red;\n"
					+ "-fx-background-color: transparent;\n" + "-fx-border-color: #d8d9e0;\n"
					+ "-fx-border-width:  3.5;" + "-fx-background-radius: 25 25 25 25");
		});
		prev.setOnMouseMoved(e -> {
			prev.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 20;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: red;\n"
					+ "-fx-background-color: #d8d9e0;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
					+ "-fx-background-radius: 25 25 25 25");
		});
		prev.setOnMouseExited(e -> {
			prev.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 20;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: red;\n"
					+ "-fx-background-color: transparent;\n" + "-fx-border-color: #d8d9e0;\n"
					+ "-fx-border-width:  3.5;" + "-fx-background-radius: 25 25 25 25");
		});

		// Positions
		gp.add(dateLabel, 0, 0);
		gp.add(sTimeLabel, 0, 1);
		gp.add(eTimeLabel, 0, 2);
		gp.add(treatmentLabel, 0, 3);
		gp.add(datetxt, 1, 0);
		gp.add(sTimetxt, 1, 1);
		gp.add(eTimetxt, 1, 2);
		gp.add(treatmenttxt, 1, 3);
		gp.add(search, 1, 4);
		GridPane.setHalignment(search, HPos.CENTER);
		bd.setBottom(book);
		BorderPane.setAlignment(book, Pos.CENTER);
		bd.setCenter(intro2);
		v.getChildren().addAll(info, tableForAvailableDentists());
		vbox.getChildren().addAll(showAll, fillInfo, prev);
		vbox2.getChildren().addAll(showLabel, tableForShowAllAppointments());
		split.getItems().addAll(vbox, bd);

		// Events
		prev.setOnAction(e -> {

			stage.setScene(scene7);
			stage.setTitle("Patient Management");
			stage.show();
		});

		showAll.setOnAction(e -> { // Show all appointments for this patient.

			bd.setStyle("-fx-background-image:url('file:basicPage.jpg');-fx-background-size:cover");

			try {
				ResultSet set = null;
				pstatement = connect.prepareStatement(
						"select appointDate,appointStart,appointEnd,dentistName,treatment from appointments where patientId = ?");
				pstatement.setInt(1, p.getId());
				set = pstatement.executeQuery();
				tableThree.getItems().clear();
				while (set.next()) {
					tableThree.getItems().add(new Appointment(set.getString(1), set.getString(2), set.getString(3),
							set.getString(4), set.getString(5)));
					tableThree.refresh();
				}
				set.close();

				if (!tableThree.getItems().isEmpty()) {
					bd.setRight(null);
					bd.setCenter(null);
					bd.setLeft(null);
					bd.setTop(null);
					bd.setCenter(vbox2);
					book.setVisible(false);
				} else {

					bd.setRight(null);
					bd.setCenter(null);
					bd.setLeft(null);
					bd.setTop(null);
					bd.setCenter(noAppoint);

				}

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		});

		fillInfo.setOnAction(e -> { // Fill info for appointment

			bd.setStyle("-fx-background-image:url('file:basicPage.jpg');-fx-background-size:cover");
			bd.setCenter(null);
			bd.setLeft(null);
			bd.setTop(null);
			bd.setTop(intro);
			bd.setLeft(gp);
			BorderPane.setAlignment(intro, Pos.CENTER);
			BorderPane.setAlignment(gp, Pos.CENTER);

		});

		search.setOnAction(e -> { // Search For available dentists

			try {
				ResultSet set = null;
				pstatement = connect.prepareStatement(
						"select dentistId,dentistName from dentists where dentistId NOT IN (select dentistId from appointments where appointDate = ? AND appointStart = ? AND appointEnd = ? or appointDate = ? AND appointStart >= ? AND appointStart <= ? or appointDate = ? AND appointEnd <= ? and appointEnd >= ?)");
				pstatement.setString(1, datetxt.getText());
				pstatement.setString(2, sTimetxt.getText());
				pstatement.setString(3, eTimetxt.getText());
				pstatement.setString(4, datetxt.getText());
				pstatement.setString(5, sTimetxt.getText());
				pstatement.setString(6, eTimetxt.getText());
				pstatement.setString(7, datetxt.getText());
				pstatement.setString(8, eTimetxt.getText());
				pstatement.setString(9, sTimetxt.getText());
				set = pstatement.executeQuery();
				tableFour.getItems().clear();
				while (set.next()) {
					tableFour.getItems().add(new Appointment(set.getInt("dentistId"), set.getString("dentistName")));
					tableFour.refresh();
				}
				set.close();

				if (tableFour.getItems().isEmpty()) {
					bd.setRight(null);
					bd.setRight(searchResult);
					BorderPane.setAlignment(searchResult, Pos.CENTER);
				} else {
					bd.setRight(null);
					bd.setRight(v);
					BorderPane.setAlignment(v, Pos.CENTER);
					book.setVisible(true);
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		});

		book.setOnAction(e -> {

			try {

				int index = tableFour.getSelectionModel().getSelectedIndex();
				Appointment a = tableFour.getSelectionModel().getSelectedItem();
				ResultSet set = null;

				if (index >= 0) {
					pstatement = connect.prepareStatement(
							"insert into appointments (patientId,dentistId,patientName,dentistname,appointDate,appointStart,appointEnd,treatment) values (?,?,?,?,?,?,?,?)");
					pstatement.setInt(1, p.getId());
					pstatement.setInt(2, a.getDentistId());
					pstatement.setString(3, p.getName());
					pstatement.setString(4, a.getDentist());
					pstatement.setString(5, datetxt.getText());
					pstatement.setString(6, sTimetxt.getText());
					pstatement.setString(7, eTimetxt.getText());
					pstatement.setString(8, treatmenttxt.getText());
					pstatement.executeUpdate();
					pstatement = connect.prepareStatement(
							"select * from appointments where appointId = (select Max(appointId) from appointments)");
					set = pstatement.executeQuery();

					if (set.next()) {
						tableTwo.getItems()
								.add(new Appointment(set.getInt(1), set.getInt(2), set.getInt(3), set.getString(4),
										set.getString(5), set.getString(6), set.getString(7), set.getString(8),
										set.getString(9)));
						tableTwo.refresh();
					}

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Successfully booked");
					alert.show();
					datetxt.clear();
					sTimetxt.clear();
					eTimetxt.clear();
					book.setVisible(false);
					bd.setRight(null);

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Please choose a dentist");
					alert.show();
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		});

		return split;

	}

	private VBox DoctorAccount() throws SQLException, Exception { // My Account interface (Settings).

		// Layouts
		VBox vbox = new VBox(10);
		GridPane gp = new GridPane();
		Text changePhoto = new Text("Change Photo");
		Text changeInfo = new Text("Change Info");
		Label userLabel = new Label("User Name:");
		Label passLabel = new Label("Password:");
		Label emailLabel = new Label("Email:");
		Label phoneLabel = new Label("Phone:");
		TextField usertxt = new TextField();
		PasswordField passtxt = new PasswordField();
		TextField emailtxt = new TextField();
		TextField phonetxt = new TextField();
		Button save = new Button("Save");

		// Editing
		gp.setVgap(10);
		gp.setHgap(10);
		gp.setAlignment(Pos.CENTER);
		gp.setVisible(false);
		vbox.setAlignment(Pos.CENTER);
		changePhoto.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 13));
		changePhoto.setFill(Color.BLACK);
		changeInfo.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 13));
		changeInfo.setFill(Color.BLACK);
		save.setStyle(
				"-fx-background-radius: 40 40 40 40;-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 3px 8px;");
		userLabel.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 13));
		userLabel.setTextFill(Color.BLACK);
		passLabel.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 13));
		passLabel.setTextFill(Color.BLACK);
		emailLabel.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 13));
		emailLabel.setTextFill(Color.BLACK);
		phoneLabel.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 13));
		phoneLabel.setTextFill(Color.BLACK);

		// Positions
		gp.add(changePhoto, 0, 0);
		GridPane.setHalignment(changePhoto, HPos.CENTER);
		gp.add(userLabel, 0, 1);
		gp.add(passLabel, 0, 2);
		gp.add(emailLabel, 0, 3);
		gp.add(phoneLabel, 0, 4);
		gp.add(usertxt, 1, 1);
		gp.add(passtxt, 1, 2);
		gp.add(emailtxt, 1, 3);
		gp.add(phonetxt, 1, 4);
		gp.add(save, 1, 5);
		GridPane.setHalignment(save, HPos.CENTER);
		vbox.getChildren().addAll(imv2, changeInfo, gp);

		// DataBase*********************************************************
		ResultSet sett = null;
		pstatement = connect.prepareStatement(
				"SELECT adminName,adminPass,adminEmail,adminPhone,adminPhoto FROM adminstrator where id = (select Max(id) from adminstrator)");
		sett = pstatement.executeQuery();
		if (sett.next()) {
			usertxt.setText(sett.getString(1));
			passtxt.setText(sett.getString(2));
			emailtxt.setText(sett.getString(3));
			phonetxt.setText(sett.getString(4));
			imageData = sett.getBytes("adminPhoto");//edit by Ayham
		}
		sett.close();
		// *********************************************************************

		// Events
		changePhoto.setOnMouseMoved(e -> {

			changePhoto.setStyle("-fx-underline: true; ");
		});

		changePhoto.setOnMouseExited(e -> {

			changePhoto.setStyle("-fx-underline: false; ");
		});

		changeInfo.setOnMouseMoved(e -> {
			changeInfo.setStyle("-fx-underline: true; ");
		});

		changeInfo.setOnMouseExited(e -> {
			changeInfo.setStyle("-fx-underline: false; ");
		});

		changeInfo.setOnMouseClicked(e -> {
			gp.setVisible(true);
		});

		changePhoto.setOnMouseClicked(e -> {
			file = chosen.showOpenDialog(stage);
			// DataBase **********************************************************
			try {
				imageData = Files.readAllBytes(file.toPath());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// *****************************************************************
		});

		save.setOnAction(e -> {

			// DataBase******************************************************
			try {
				ResultSet set = null;
				pstatement = connect.prepareStatement(
						"insert into adminstrator (adminName,adminPass,adminEmail,adminPhone,adminPhoto) values (?,?,?,?,?)");
				pstatement.setString(1, usertxt.getText());
				pstatement.setString(2, passtxt.getText());
				pstatement.setString(3, emailtxt.getText());
				pstatement.setString(4, phonetxt.getText());
				pstatement.setBytes(5, imageData);
				pstatement.executeUpdate();
				pstatement = connect.prepareStatement(
						"select adminPhoto from adminstrator where id = (select Max(id) from adminstrator)");
				set = pstatement.executeQuery();
				if (set.next()) {
					imageData = set.getBytes("adminPhoto");
					InputStream inp = new ByteArrayInputStream(imageData);
					imv2.setImage(new Image(inp));
				}

				gp.setVisible(false);

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// ****************************************************************
		});

		return vbox;
	}

	private SplitPane DoctorBasicScreen() { // The First Interface shown

		// Layouts
		SplitPane split = new SplitPane();
		BorderPane border = new BorderPane();
		VBox vbox = new VBox(50);
		// Text dash = new Text("DashBoard");
		Text account = new Text("My Account");
		Text patientManag = new Text("Patient Management");
		Text paymentManag = new Text("Payment Management");
		Text dentistManag = new Text("Dentist Management");
		Text logOut = new Text("Log out");
		Label intro = new Label("Welcome in Your Account");
		Label intro2 = new Label("Welcome in the Dental Clinic");

		// Editing
		vbox.setAlignment(Pos.CENTER);
		border.setStyle("-fx-background-image:url('file:basicPage.jpg');-fx-background-size:cover");
		border.setPadding(new Insets(90, 30, 40, 30));
		vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
		Font font = (Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 15));
		intro.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 20));
		intro2.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 20));
		account.setFont(font);
		patientManag.setFont(font);
		dentistManag.setFont(font);
		paymentManag.setFont(font);
		logOut.setFont(font);
		// dash.setFont(font);
		split.setDividerPositions(0.22);
		border.setCenter(intro2);

		// Positions
		vbox.getChildren().addAll(account, patientManag, dentistManag, paymentManag, logOut);
		split.getItems().addAll(vbox, border);

		// Events

		logOut.setOnMouseMoved((MouseEvent event) -> {
			logOut.setStyle("-fx-underline: true; ");
		});
		logOut.setOnMouseExited((MouseEvent event) -> {
			logOut.setStyle("-fx-underline: false; ");
		});
		// ********
		/*
		 * dash.setOnMouseMoved((MouseEvent event) -> {
		 * dash.setStyle("-fx-underline: true; "); }); dash.setOnMouseExited((MouseEvent
		 * event) -> { dash.setStyle("-fx-underline: false; "); }); // ********
		 * 
		 */
		patientManag.setOnMouseMoved((MouseEvent event) -> {
			patientManag.setStyle("-fx-underline: true; ");
		});
		patientManag.setOnMouseExited((MouseEvent event) -> {
			patientManag.setStyle("-fx-underline: false; ");
		});
		// ********
		dentistManag.setOnMouseMoved((MouseEvent event) -> {
			dentistManag.setStyle("-fx-underline: true; ");
		});
		dentistManag.setOnMouseExited((MouseEvent event) -> {
			dentistManag.setStyle("-fx-underline: false; ");
		});
		// ********
		account.setOnMouseMoved((MouseEvent event) -> {
			account.setStyle("-fx-underline: true; ");
		});
		account.setOnMouseExited((MouseEvent event) -> {
			account.setStyle("-fx-underline: false; ");
		});
		// ********
		paymentManag.setOnMouseMoved((MouseEvent event) -> {
			paymentManag.setStyle("-fx-underline: true; ");
		});
		paymentManag.setOnMouseExited((MouseEvent event) -> {
			paymentManag.setStyle("-fx-underline: false; ");
		});
		// ********

		logOut.setOnMouseClicked(e -> {
			stage.setScene(scene);
			stage.setTitle("Dental Cinic");
			stage.show();
		});

		patientManag.setOnMouseClicked(e -> {
			scene7 = new Scene(PatientManagementInterface(), 900, 600);
			stage.setScene(scene7);
			stage.setTitle("Patient Management");
			stage.show();
		});

		dentistManag.setOnMouseClicked(e -> {
			scene11 = new Scene(DentistManagementInterface(), 930, 600);
			stage.setScene(scene11);
			stage.setTitle("Dentist Management");
			stage.show();
		});

		paymentManag.setOnMouseClicked(e -> {
			scene12 = new Scene(paymentManagementInterface(), 900, 600);
			stage.setTitle("Payment Management");
			stage.setScene(scene12);
			stage.show();
		});

		account.setOnMouseClicked(e -> {
			border.setCenter(null);
			border.setTop(intro);
			BorderPane.setAlignment(intro, Pos.CENTER);
			try {
				try {
					border.setCenter(DoctorAccount());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		return split;
	}

	private BorderPane DoctorLogIn() throws Exception { // Log in interface for (Doctor).

		// Layouts
		Label userName = new Label("Admin");
		BorderPane border = new BorderPane();
		VBox vbox = new VBox(15);
		PasswordField passtxt = new PasswordField();
		Label forget = new Label("Forget Password?");
		Label invalid = new Label("Invalid password! try again");

		// DataBase **************************************************************
		ResultSet set = null;
		pstatement = connect
				.prepareStatement("SELECT adminName FROM adminstrator where id = (select Max(id) from adminstrator)");
		set = pstatement.executeQuery();
		if (set.next())
			userName.setText(set.getString("adminName"));
		set.close();
		// -------------
		pstatement = connect
				.prepareStatement("SELECT adminPhoto FROM adminstrator where id = (select Max(id) from adminstrator)");
		set = pstatement.executeQuery();
		byte[] imageData;
		if (set.next()) {
			imageData = set.getBytes("adminPhoto");
			InputStream inp = new ByteArrayInputStream(imageData);
			imv2.setImage(new Image(inp));
		}
		// ************************************************************************
		// Editing
		invalid.setTextFill(Color.RED);
		invalid.setVisible(false);
		invalid.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 10));
		imv.setFitWidth(150);
		imv.setFitHeight(150);
		clip = new Circle(imv.getFitWidth() / 2, imv.getFitHeight() / 2, imv.getFitWidth() / 2);
		clip.setStroke(Color.RED);
		clip.setStrokeWidth(10);
		imv.setClip(clip);
		userName.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 20));
		userName.setTextFill(Color.RED);
		passtxt.setPromptText("Enter Password");
		passtxt.setMaxWidth(130);
		forget.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 15));
		forget.setTextFill(Color.RED);

		// Positions
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(imv, userName, passtxt, invalid);
		vbox.setPadding(new Insets(20, 20, 90, 20));
		border.setPadding(new Insets(20, 20, 20, 20));
		border.setStyle("-fx-background-image:url('file:login.jpg');-fx-background-size:cover");
		border.setTop(back);
		border.setCenter(vbox);
		border.setBottom(forget);

		// Events
		passtxt.setOnAction(e -> {

			// DataBase *****************************************************
			String passEnterd = passtxt.getText();
			String userEnterd = userName.getText();
			String sql = "select adminPass from adminstrator where id = (select Max(id) from adminstrator) AND adminName = ?";

			try {
				pstatement = connect.prepareStatement(sql);
				pstatement.setString(1, userEnterd);
				res = pstatement.executeQuery();

				if (res.next()) {
					if (res.getString("adminPass").equals(passEnterd)) {

						scene6 = new Scene(DoctorBasicScreen(), 900, 600);
						stage.setTitle("Clinic System");
						stage.setScene(scene6);
						stage.show();

					} else {
						System.out.println("InCorrect");
						passtxt.setStyle("-fx-border-color: red; -fx-border-width: 2px; ");
						invalid.setVisible(true);
					}
				}
			} catch (SQLException e1) {
				System.out.println("Failed One");
			}

			// *******************************************************************
		});

		forget.setOnMouseMoved(e -> {

			forget.setStyle("-fx-underline: true; ");
		});
		forget.setOnMouseExited(e -> {

			forget.setStyle("-fx-underline: false;");
		});

		forget.setOnMouseClicked(e -> {
			restoringForAdminstrator();
		});

		return border;

	}

	private void restoringForAdminstrator() { // Restore the admin's password by his email

		// Layouts
		Stage stageSpecial = new Stage();
		Scene sceneSpecial;
		BorderPane border = new BorderPane();
		VBox vbox = new VBox(5);
		HBox hbox = new HBox(15);
		Label label1 = new Label("Forget Password?");
		Label label2 = new Label("Please enter  your email address and we will send you password");
		Label emailLabel = new Label("Email: ");
		TextField emailtxt = new TextField();
		Label restore = new Label("");

		// Editing
		border.setPadding(new Insets(40, 40, 40, 40));
		vbox.setAlignment(Pos.CENTER);
		hbox.setAlignment(Pos.CENTER);
		border.setStyle("-fx-background-image:url('file:restore.jpg');-fx-background-size:cover");
		label1.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 20));
		label1.setTextFill(Color.FLORALWHITE);
		label2.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 10));
		label2.setTextFill(Color.FLORALWHITE);
		emailLabel.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 20));
		emailLabel.setTextFill(Color.FLORALWHITE);
		restore.setFont(Font.font(null, FontWeight.BLACK, FontPosture.ITALIC, 20));
		restore.setTextFill(Color.GHOSTWHITE);
		restore.setVisible(false);
		emailtxt.setPrefColumnCount(15);
		// Positions
		hbox.getChildren().addAll(emailLabel, emailtxt);
		vbox.getChildren().addAll(label1, label2, hbox);
		border.setCenter(vbox);
		border.setBottom(restore);
		BorderPane.setAlignment(restore, Pos.CENTER);

		// Stages && Scene
		sceneSpecial = new Scene(border, 500, 500);
		stageSpecial.setTitle("Restore Password");
		stageSpecial.setScene(sceneSpecial);
		stageSpecial.show();

		// Events
		emailtxt.setOnAction(e -> {

			// DataBase **************************************************
			String email = emailtxt.getText();
			String sql = "select adminPass from adminstrator where id = (select Max(id) from adminstrator) AND adminEmail = ?";

			try {
				pstatement = connect.prepareStatement(sql);
				pstatement.setString(1, email);
				res = pstatement.executeQuery();

				if (res.next()) {
					restore.setVisible(true);
					restore.setText("Your password is : " + res.getString("adminPass"));
				} else {
					restore.setVisible(true);
					restore.setText("Invalid Email ");
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// *****************************************************************
		});

	}

	private BorderPane Dentist_Scene() {// Dentist Interface.

		// layouts
				BorderPane border = new BorderPane();
				GridPane gp = new GridPane();
				// Editing
				gp.setVgap(15);
				gp.setHgap(15);
				gp.setAlignment(Pos.CENTER);
				border.setPadding(new Insets(20, 20, 20, 20));
				border.setStyle("-fx-background-image:url('file:img3.jpg');-fx-background-size:cover");

				// Positions
				gp.add(usernameLabel, 0, 0);
				gp.add(passwordLabel, 0, 1);
				gp.add(usernameTxt, 1, 0);
				gp.add(passwordTxt, 1, 1);
				gp.add(enter, 1, 3);
				GridPane.setHalignment(enter, HPos.LEFT);
				border.setTop(back);
				border.setCenter(gp);

				// Events
				enter.setOnAction(e -> {

					if (!usernameTxt.getText().isBlank() && !passwordTxt.getText().isBlank()) {

						// DataBase***************************************
						try {
							// For Scene
							newScene = new Scene(tableEight, 900, 600);
							stage2.setScene(newScene);
							stage2.show();

							ResultSet set = null;
							pstatement = connect
									.prepareStatement("select dentistId from dentists where userName = ? AND pass = ?");
							pstatement.setString(1, usernameTxt.getText());
							pstatement.setString(2, passwordTxt.getText());
							set = pstatement.executeQuery();

							if (set.next()) {

								int id = set.getInt(1);
								pstatement = connect.prepareStatement(
										"select patientName , appointDate,appointStart,appointEnd,treatment from appointments where dentistId = ?");
								pstatement.setInt(1, id);
								set = pstatement.executeQuery();

								tableEight.getItems().clear();

								while (set.next()) {

									tableEight.getItems().add(new Appointment(set.getString(1), set.getString(2),
											set.getString(3), set.getString(4), set.getString(5), "()"));
									tableEight.refresh();

								}
								set.close();

							} else {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setContentText("UnKnown Dentist");
								alert.show();
							}

						} catch (Exception ex) {
//							ex.printStackTrace();
							Alert alert = new Alert(AlertType.ERROR);
							alert.setContentText("Faild");
							alert.show();
						}

					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Enter User Name and password !! ");
						alert.show();
					}

				});

				return border;
	}

	public void eventsForBasicButtons() { // For handling the styles events
		doctor.setOnMouseMoved(e -> {
			doctor.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 20;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: #CE2029;\n"
					+ "-fx-background-color: #d8d9e0;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
					+ "-fx-background-radius: 25 25 25 25");
		});
		doctor.setOnMouseExited(e -> {
			doctor.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 20;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: #f2f3f4;\n"
					+ "-fx-background-color: transparent;\n" + "-fx-border-color: #d8d9e0;\n"
					+ "-fx-border-width:  3.5;" + "-fx-background-radius: 25 25 25 25");
		});

		// *************
		dentist.setOnMouseMoved(e -> {
			dentist.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 20;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: #CE2029;\n"
					+ "-fx-background-color: #d8d9e0;\n" + "-fx-border-color: #d8d9e0;\n" + "-fx-border-width:  3.5;"
					+ "-fx-background-radius: 25 25 25 25");
		});
		dentist.setOnMouseExited(e -> {
			dentist.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 20;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: #f2f3f4;\n"
					+ "-fx-background-color: transparent;\n" + "-fx-border-color: #d8d9e0;\n"
					+ "-fx-border-width:  3.5;" + "-fx-background-radius: 25 25 25 25");
		});

		// ******
	}

	public void events() { // For handling the Basic buttons

		doctor.setOnAction(e -> {
			try {
				scene1 = new Scene(DoctorLogIn(), 700, 700);
				stage.setScene(scene1);
				stage.setTitle("Authentication Page");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		});
		dentist.setOnAction(e -> {
			scene4 = new Scene(Dentist_Scene(), 700, 700);
			stage.setScene(scene4);
			stage.setTitle("Dentist Page");
		});

		back.setOnAction(e -> {
			stage.setScene(scene);
			stage.setTitle("Dental clinic");
		});

	}

	
	
}
