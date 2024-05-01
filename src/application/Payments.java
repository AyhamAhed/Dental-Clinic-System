package application;

public class Payments {

	// Columns
	private int paymentId;
	private String paymentMethod;
	private String paymentAmount;
	private String paymentDate;
	private String treatment;
	private String isPaid;

	// ****
	private String patientName;

	public Payments(String patientName,  String paymentAmount, String paymentDate ,String treatment,String isPaid) {

		this.patientName = patientName;
		this.treatment = treatment;
		this.paymentAmount = paymentAmount;
		this.paymentDate = paymentDate;
		this.isPaid = isPaid;

	}

	public Payments(int paymentId, String paymentMethod, String paymentAmount, String paymentDate, String treatment,
			String isPaid) {
		super();
		this.paymentId = paymentId;
		this.paymentMethod = paymentMethod;
		this.paymentAmount = paymentAmount;
		this.paymentDate = paymentDate;
		this.treatment = treatment;
		this.isPaid = isPaid;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(String isPaid) {
		this.isPaid = isPaid;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	

}
