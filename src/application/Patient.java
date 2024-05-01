package application;

public class Patient {

	private int id;
	private int age;
	private String name;
	private String phone;
	private String email;
	
	
	public Patient() {
		// TODO Auto-generated constructor stub
	}

	public Patient(int id, int age, String name, String phone, String email) {
		super();
		this.id = id;
		this.age = age;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
