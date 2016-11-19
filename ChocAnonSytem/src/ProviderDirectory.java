import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class ProviderDirectory
{
	private static TreeMap<String, Person> directory;		//tree based on provider name(might change to number)
	private static TreeMap<Integer, Person> directory2;		//tree based on service number
	private static TreeMap<Integer, Person>	directory3;		//tree based on provider number
	
	public ProviderDirectory()
	{
		directory = new TreeMap<String, Person>();
		directory2 = new TreeMap<Integer, Person>();
		directory3 = new TreeMap<Integer, Person>();
	}
	
	//adds new provider to maps
	public int addPro(Person temp)
	{
		directory.put(temp.getName(), temp);
		directory2.put(temp.getServiceNum(), temp);
		directory3.put(temp.getNum(), temp);
					
		if(directory.containsKey(temp.getName()))
			if(directory2.containsKey(temp.getServiceNum()))
				if(directory3.containsKey(temp.getNum()))
					return 1;
	
		return 0;
	}
	
	//remove provider from maps based on name
	public boolean removePro(String key)
	{
		if(directory.containsKey(key))
		{	
			directory3.remove(directory2.remove(directory.remove(key).getServiceNum()).getNum());
					return true;
		}
		return false;
	}
	
	//display list of provider in alphabetical order	
	public void displayAll()
	{	
		for(Entry<String, Person> entry : directory.entrySet())
			entry.getValue().display();
	}
	
	//search by provider number
	public int searchByProNum(int found)
	{
		//check that the key exist if so respond positively
		if(directory3.containsKey(found))
			return 1;
		
		return 0;
	}
	
	//searches map for service number
	public int searchByServiceNum(int found)
	{
		if(directory2.containsKey(found))
		{
			directory2.get(found).display();
			return 1;
		}
		return 0;
	}
	
	public void saveOutForEmail()
	{
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter("Directory Email.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Entry<String, Person> entry : directory.entrySet())
		{
			entry.getValue().saveOutForEmail(writer);
			writer.write("\n");
		}

		writer.close();
	}
	
	public void saveOutForSystem()
	{
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter("Provider Directory.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Entry<String, Person> entry : directory.entrySet())
		{
			entry.getValue().saveOutForSystem(writer);
			writer.write("\n");
		}

		writer.close();
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
		//map.displayAll();
		if(map.searchByProNum(123) == 1)
			System.out.println("Logged in.\n");
		else
			System.out.println("Number Not found");
			
		map.searchByServiceNum(1234);
		map.searchByServiceNum(1);
		map.removePro("Tay");
		map.searchByServiceNum(1);
		map.searchByServiceNum(2);
		map.saveOutForEmail();
		map.saveOutForSystem();
	}
}
