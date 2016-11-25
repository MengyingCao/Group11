import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class ProviderDirectory 
{
	private TreeMap<Integer, Service> directory;
	private TreeMap<String, Service> directory2;
	private Scanner in = null;
	
	public ProviderDirectory()
	{
		directory = new TreeMap<Integer, Service>();
		directory2 = new TreeMap<String, Service>();
	}
	
	
	//copy constructor
	public ProviderDirectory(ProviderDirectory toCopy)
	{
		for(Entry<Integer, Service> entry : toCopy.directory.entrySet())
		{
			directory.put(entry.getKey(), entry.getValue());
			directory2.put(entry.getValue().getServiceName(), entry.getValue());
		}
	}
	
	//Creates a new directory with a file name input
	public ProviderDirectory(String fileName) throws FileNotFoundException
	{
		File inFile = new File(fileName);
		try {
			in = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw e;//.printStackTrace();
		}
		
		in.useDelimiter(";");
		directory = new TreeMap<Integer, Service>();
		directory2 = new TreeMap<String, Service>();
		
		while(in.hasNext())
		{	
			Service temp = new Service(in.next(), in.nextFloat(), in.nextInt());
			directory.put(temp.getServiceCode(), temp);
			directory2.put(temp.getServiceName(), temp);
			in.nextLine();
		}
		in.close();
	}
	
	//adds new service to map returns 1 if successful 0 if failure
	public int addService(Service toAdd)
	{
		directory.put(toAdd.getServiceCode(), toAdd);
		directory2.put(toAdd.getServiceName(), toAdd);
		
		if(directory.containsKey(toAdd.getServiceCode()) && directory2.containsKey(toAdd.getServiceName()))
			return 1;
			
		return 0;
	}
	
	//removes service from map, 1 if successful 0 if failure
	public int removeServiceByNum(int key)
	{
		Service temp = null;
		
		if(directory.containsKey(key))
			temp = directory2.remove(directory.remove(key).getServiceName());
			
		if(!directory.containsKey(key) && !directory2.containsValue(temp))
			return 1;
		
		return 0;
	}
	
	///removes service from both maps by name
	public int removeServiceByName(String key)
	{
		Service temp = null;
		
		if(directory2.containsKey(key))
			temp = directory.remove(directory2.remove(key).getServiceName());
		
		if(!directory2.containsKey(key) && directory.containsValue(temp))
			return 1;
		
		return 0;
	}
	
	//search and display by Number
	public int seacrhByNumAndDisplay(int key)
	{
		if(directory.containsKey(key))
		{
			directory.get(key).display();
			return 1;
		}
		
		return 0;
	}
	
	//search and display by name
	public int searchByNameAndDisplay(String key)
	{
		if(directory2.containsKey(key))
		{
			directory2.get(key).display();
			return 1;
		}
		
		return 0;
	}
	
	//search by the name and copy to temp brought in
	public Service searchByNameAndCopy(String key, Service temp)
	{
		if(directory2.containsKey(key))
			temp = new Service(directory2.get(key));
		
		return temp;
			
	}
	
	//bring in temp service to populate and send back
	public Service searchByNumAndCopy(int key, Service temp)
	{
		if(directory.containsKey(key))
			temp = new Service(directory.get(key));
		
		if(temp == null)
			return null;
		
		return temp;
		
	}
	
	//displays all services alphabetically
	public void displayAllByName()
	{
		for(Entry<String, Service> entry : directory2.entrySet())
			entry.getValue().display();
	}
	
	//save DB out to be emailed
	public void saveOutForEmail()
	{
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter("Directory Email.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Entry<String, Service> entry : directory2.entrySet())
		{
			entry.getValue().saveOutForEmail(writer);
			writer.write("\n");
		}

		writer.close();
	}
	
	//save out for later use by the system
	public void saveOutForSystem()
	{
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter("Provider Directory.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Entry<String, Service> entry : directory2.entrySet())
		{
			entry.getValue().saveOutForSystem(writer);
			writer.write("\n");
		}

		writer.close();
	}
	
/*	public static void main(String[] args)
	{
		ProviderDirectory temp = null;
		try {
			temp = new ProviderDirectory("Provider Directory.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		temp.displayAllByName();
		
		
		
		Service temp1 = new Service("Poo", 300, 5);
		Service toAdd = new Service("bleh", 200, 7);
		
		temp.addService(toAdd);
		temp.addService(temp1);
		temp.saveOutForSystem();
		temp.saveOutForEmail();
		
		
	}*/
}
