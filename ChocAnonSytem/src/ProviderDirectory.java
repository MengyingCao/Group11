import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.TreeMap;
import java.util.Map.Entry;

public class ProviderDirectory 
{
	private TreeMap<Integer, Service> directory;
	
	public ProviderDirectory()
	{
		directory = new TreeMap<Integer, Service>();
	}
	
	//copy constructor
	public ProviderDirectory(ProviderDirectory toCopy)
	{
		for(Entry<Integer, Service> entry : toCopy.directory.entrySet())
			directory.put(entry.getKey(), entry.getValue());
	}
	
	//adds new service to map returns 1 if successful 0 if failure
	public int addService(Service toAdd)
	{
		directory.put(toAdd.getServiceCode(), toAdd);
		
		if(directory.containsKey(toAdd.getServiceCode()))
			return 1;
			
		return 0;
	}
	
	//removes service from map, 1 if successful 0 if failure
	public int removeService(int key)
	{
		if(directory.containsKey(key))
			directory.remove(key);
		if(!directory.containsKey(key))
			return 1;
		
		return 0;
	}
	
	//search and display
	public int seacrhByNumAndDisplay(int key)
	{
		if(directory.containsKey(key))
		{
			directory.get(key).display();
			return 1;
		}
		
		return 0;
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
		
		for(Entry<Integer, Service> entry : directory.entrySet())
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
		
		for(Entry<Integer, Service> entry : directory.entrySet())
		{
			entry.getValue().saveOutForSystem(writer);
			writer.write("\n");
		}

		writer.close();
	}
}
