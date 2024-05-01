package application;

public class Dentists {

	private int dentistId;
	private String dentistName;
	private String userName;
	private String pass;

	public Dentists(int dentistId, String dentistName, String userName,String pass) {
		super();
		this.dentistId = dentistId;
		this.dentistName = dentistName;
		this.userName = userName;
		this.pass = pass;
	}

	public int getDentistId() {
		return dentistId;
	}

	public void setDentistId(int dentistId) {
		this.dentistId = dentistId;
	}

	public String getDentistName() {
		return dentistName;
	}

	public void setDentistName(String dentistName) {
		this.dentistName = dentistName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	
	
}
