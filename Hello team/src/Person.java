
public class Person {

	private String name;
	private int num;
	private Service serv;
	
	public Person()
	{
		name = null;
		num = 0;
		serv = new Service();
	}
	
	public Person(String temp, int temp2, Service temp1)
	{
		this.name = temp;
		this.num = temp2;
		this.serv = new Service(temp1);
	}
	
	public int set(String temp, int temp2, Service temp1)
	{
		
		this.name = temp;
		this.num = temp2;
		this.serv.set(temp1.getName());
		
		if(this.name != null)
			return 1;
		
		return 0;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getNum()
	{
		return num;
	}
	
	public int getService()
	{
		return serv.getName();
	}

	public void display() 
	{
		System.out.print("Name: ");
		System.out.println(name);
		serv.display();
	}
}
