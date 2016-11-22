import java.time.*;

public class Transaction {
	
	private LocalDate transactionDate;
	private LocalDateTime dateTimeRecieved;
	private int providerNumber;
	private int memberNumber;
	int serviceCode;
	String comment;
	
	public Transaction(){
		/* set all members to their zero equivalent values */
		transactionDate = LocalDate.MIN;
		dateTimeRecieved = LocalDateTime.MIN;
		providerNumber = -1;
		memberNumber = -1;
		serviceCode = -1;
		comment = "";
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

	public void setProviderNumber(int providerNumber) {
		this.providerNumber = providerNumber;
	}

	public int getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(int memberNumber) {
		this.memberNumber = memberNumber;
	}

	public int getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(int serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
