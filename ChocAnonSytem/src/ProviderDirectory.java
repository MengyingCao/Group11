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
	
	public void displayAll()
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
			{
				entry.getValue().display();
				return 1;
			}
		}
		return 0;
	}
	
	public int searchByServiceNum(int found)
	{
		for(Entry<String, Person> entry : directory.entrySet())
		{
			if(entry.getValue().getService() == found)
			{
				entry.getValue().displayServ();
				return 1;
			}
		}
		return 0;
	}
	
	public static void main(String[] args)
	{
		ProviderDirectory map = new ProviderDirectory();
		Service temp1 = new Service(1234, 300, "PHY");
		Person	temp = new Person("Jorden", 56, temp1);
		map.addPro(temp);
		
		temp1 = new Service(1, 300, "HY");
		temp = new Person("Tay", 123, temp1);
		map.addPro(temp);
		
		temp1 = new Service(2, 300, "P");
		temp = new Person("Butt", 321, temp1);
		map.addPro(temp);


		//System.out.println(directory.size());
		//map.display();
		//map.searchByProNum(123);
		map.searchByServiceNum(1234);
		map.searchByServiceNum(1);
		map.searchByServiceNum(2);
	}
}
