import java.time.*;

public class Transaction {
	
	private LocalDate transactionDate;
	private LocalDateTime dateTimeRecieved;
	private int providerNumber;
	private int memberNumber;
	private int serviceCode;
	private String comment;
	
	public Transaction(){
		/* set all members to their zero equivalent values */
		transactionDate = LocalDate.MIN;
		dateTimeRecieved = LocalDateTime.MIN;
		providerNumber = 0;
		memberNumber = 0;
		serviceCode = 0;
		comment = "";
	}
	
	public Transaction(Transaction toCopy){
		this.transactionDate = LocalDate.from(toCopy.getTransactionDate());
		this.dateTimeRecieved = LocalDateTime.from(toCopy.getDateTimeRecieved());
		this.providerNumber = toCopy.getProviderNumber();
		this.memberNumber = toCopy.getMemberNumber();
		this.serviceCode = toCopy.getServiceCode();
		this.comment = toCopy.getComment();
	}
	
	public Boolean isWithinPastWeek()
	{
		long week = 7;
		LocalDate todaysDate = LocalDate.now();
		//if the transaction date is before a week ago
		if (transactionDate.isBefore(todaysDate.minusDays(week)))
			return false;
		else
			return true;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(LocalDate date) {
		this.transactionDate = LocalDate.from(date);
	}
	
	public LocalDateTime getDateTimeRecieved() {
		return dateTimeRecieved;
	}

	public void setDateTimeRecieved(LocalDateTime dateTime) {
		this.dateTimeRecieved = LocalDateTime.from(dateTime);
	}

	public int getProviderNumber() {
		return providerNumber;
	}

	public void setProviderNumber(int number) {
		this.providerNumber = number;
	}

	public int getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(int number) {
		this.memberNumber = number;
	}

	public int getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(int serviceNumber) {
		this.serviceCode = serviceNumber;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String commentString) {
		return this.comment;
	}
}
