import java.io.PrintWriter;

public class Service {

	private int serviceCode;
	private float fee;
	private String serviceName;
	
	public Service()
	{
		serviceCode = -999;
		fee = -9.99f;
		serviceName = "Default";
	}
	public Service(String passedServiceName, float passedFee, int passedServiceCode)
	{
		this.serviceCode = passedServiceCode;
		this.fee = passedFee;
		this.serviceName = passedServiceName;
	}
	
	public Service(Service copyTo) 
	{
		this.serviceName = copyTo.serviceName;
		this.serviceCode = copyTo.serviceCode;
		this.fee = copyTo.fee;
		
	}
	public int getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(int serviceCode) {
		this.serviceCode = serviceCode;
	}

	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public void display()
	{
		System.out.println("Service name: " + serviceName + "\nFee: " + fee + "\nService Code: " + serviceCode + '\n');
	}
	
	public void saveOutForEmail(PrintWriter out)
	{
		out.write("Service: " + serviceName + "\n" + "Fee: " + fee + "\n" + "Service Code: " + serviceCode + "\n");
	}
	
	public void saveOutForSystem(PrintWriter out)
	{
		out.write(serviceName + ";" + fee + ";" + serviceCode + ";");
	}
}
