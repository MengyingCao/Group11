import java.io.*;

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
		this.serv = temp1;
	}
	
	public int set(String temp, int temp2, Service temp1)
	{
		
		this.name = temp;
		this.num = temp2;
		this.serv = temp1;
		
		if(this.name != null)
			return 1;
		
		return 0;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void saveOutForEmail(PrintWriter out)
	{
		out.write("Name: " + name + "\n");
		serv.saveOutForEmail(out);
	}
	
	public void saveOutForSystem(PrintWriter out)
	{
		out.write(name + ";" + num + ";");
		serv.saveOutForSystem(out);
	}
	
	public int getNum()
	{
		return num;
	}
	
	public int getServiceNum()
	{
		return serv.getServiceCode();
	}

	public void display() 
	{
		System.out.print("Name: ");
		System.out.println(name);
		serv.display();
	}
	
	public void displayServ()
	{
		serv.display();
	}
}
