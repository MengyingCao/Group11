import java.util.*;
import java.util.Map.Entry;

public class ProviderDirectory
{
	private static TreeMap<String, Person> directory;
	
	public ProviderDirectory()
	{
		directory = new TreeMap<String, Person>();
	}
	
	public void addPro(Person temp)
	{
		directory.put(temp.getName(), temp);	
	}
	
	public void display()
	{
		for(Entry<String, Person> entry : directory.entrySet())
		{
			entry.getValue().display();
		}
	}
	
	public int searchByProNum(int found)
	{
		for(Entry<String, Person> entry : directory.entrySet())
		{
			if(entry.getValue().getNum() == found)
				return 1;
		}
		return 0;
	}
	
	public static void main(String[] args)
	{
		ProviderDirectory map = new ProviderDirectory();
		Service temp1 = new Service(1234);
		Person	temp = new Person("Jorden", 56, temp1);
		
		map.addPro(temp);
		temp = new Person("Tay", 123, temp1);
		map.addPro(temp);
		temp = new Person("Butt", 321, temp1);
		map.addPro(temp);


		System.out.println(directory.size());
		map.display();
	}
}
