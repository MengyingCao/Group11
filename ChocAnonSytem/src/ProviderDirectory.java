import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class ProviderDirectory
{
	private static TreeMap<String, Person> directory;		//tree based on provider name(might change to number)
	private static TreeMap<Integer, Service> directory2;		//tree based on service number
	private static TreeMap<Integer, Person>	directory3;		//tree based on provider number
	
	public ProviderDirectory()
	{
		directory = new TreeMap<String, Person>();
		directory2 = new TreeMap<Integer, Service>();
		directory3 = new TreeMap<Integer, Person>();
	}
	
	//adds new provider to maps
	public int addPro(Person temp)
	{
		directory.put(temp.NameGetter(), temp);
		directory3.put(temp.IDGetter(), temp);
					
		if(directory.containsKey(temp.NameGetter()))
			if(directory3.containsKey(temp.IDGetter()))
				return 1;
	
		return 0;
	}
	
	//remove provider from maps based on name
	public boolean removePro(String key)
	{
		if(directory.containsKey(key))
		{	
			if(directory3.containsKey(directory.remove(key).IDGetter()))
				return true;
		}
		return false;
	}
	
	//display list of provider in alphabetical order	
	public void displayAll()
	{	
		for(Entry<String, Person> entry : directory.entrySet())
			entry.getValue().DisplayInformation();
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

	}
}
