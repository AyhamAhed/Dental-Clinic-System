package application;

public class Appointment {

	private int appointmentId;
	private int patientId;
	private int dentistId;
	private String patientName;
	private String dentist;
	private String date;
	private String startTime;
	private String endTime;
	private String treatments;
	
	public Appointment() {
		// TODO Auto-generated constructor stub
	}
	
	public Appointment(String patientName, String date, String startTime, String endTime, String treatment, String a) {

		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.treatments = treatment;
		this.patientName = patientName;

	}
	

	public Appointment(int appointmentId, int patientId, int dentistId, String patientName, String dentist, String date,
			String startTime, String endTime, String treatments) {
		super();
		this.appointmentId = appointmentId;
		this.patientId = patientId;
		this.dentistId = dentistId;
		this.patientName = patientName;
		this.dentist = dentist;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.treatments = treatments;
	}




	public Appointment(int dentistId , String dentis) {
		this.dentist = dentis;
		this.dentistId = dentistId;
		
	}
	public Appointment( String date, String startTime,String endTime, String dentist,String treatments) {
		super();
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.treatments = treatments;
		this.dentist = dentist;
	}
	
	
	public Appointment(int appointmentId, int patientId, String patientName, String date, String startTime,
			String endTime, String treatments, String dentist) {
		super();
		this.appointmentId = appointmentId;
		this.patientId = patientId;
		this.patientName = patientName;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.treatments = treatments;
		this.dentist = dentist;
	}

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTreatments() {
		return treatments;
	}

	public void setTreatments(String treatments) {
		this.treatments = treatments;
	}

	public String getDentist() {
		return dentist;
	}

	public void setDentist(String dentist) {
		this.dentist = dentist;
	}

	public int getDentistId() {
		return dentistId;
	}

	public void setDentistId(int dentistId) {
		this.dentistId = dentistId;
	}
	
	

}
