package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.control.*;

public class Main extends Application {
	
	Button doctor,dentist,back , enter,restore;	 // sharable buttons
	static Connection connect = null;
	static Statement statement = null;
	static PreparedStatement pstatement = null;
	static ResultSet res = null;	
	String url = "jdbc:mysql://localhost:3306/clinicproject";
	String user_name = "root";
	String pass_word = "root1234";
		
	public void connectDB() { // Connect the dataBase with javaFX
		try {
			connect = DriverManager.getConnection(url, user_name, pass_word);
			System.out.println("Connect with DataBase");
			
		} catch (SQLException e) {
			e.getMessage();
			System.out.println("Failed Connection");
		}	
	}	
	@Override
	public void start(Stage primaryStage) {
		try {
		
			new FirstStage();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
