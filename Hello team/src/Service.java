
public class Service {
	private int num;
	
	public Service()
	{
		num = 0;
	}
	
	public Service(Service temp1) {
		num = temp1.num;
	}

	public Service(int temp) {
		num = temp;
	}

	public int set(int temp)
	{
		num = temp;
		return num;

	}
	public int getName()
	{
		return num;
	}
	
	public void display()
	{
		System.out.print("Service: ");
		System.out.println(this.num);
	}
}
