import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class ProviderDirectory
{
	private static TreeMap<String, Person> directory;		//tree based on provider name
	private static TreeMap<Integer, Person>	directory1;		//tree based on provider number
	
	public ProviderDirectory()
	{
		directory = new TreeMap<String, Person>();
		directory1 = new TreeMap<Integer, Person>();
	}
	
	//adds new provider to both maps
	public int addPro(Person temp)
	{
		directory.put(temp.NameGetter(), temp);
		directory1.put(temp.IDGetter(), temp);
					
		if(directory.containsKey(temp.NameGetter()))
			if(directory1.containsKey(temp.IDGetter()))
				return 1;
	
		return 0;
	}
	
	//remove provider from both maps based on name
	public boolean removeProByName(String key)
	{
		if(directory.containsKey(key))
			return directory1.remove(key, directory.remove(key).IDGetter());
		
		return false;
	}
	
	//removes by from both maps based on provider number
	public boolean removeByProNum(int key)
	{
		if(directory1.containsKey(key))	
			return directory1.remove(key, directory.remove(key).IDGetter());
		
		return false;
	}
	
	//display list of provider in alphabetical order	
	public void displayAllByName()
	{	
		for(Entry<String, Person> entry : directory.entrySet())
			entry.getValue().DisplayInformation();
	}
	
	//searches map by provider number
	public int searchByProNum(int found)
	{
		//check that the key exist if so respond positively
		if(directory1.containsKey(found))
			return 1;
		
		return 0;
	}
	
	//search by name and copies to person brought in
	public Person searchByNameAndCopy(String key, Person temp)
	{
		if(directory.containsKey(key))
			return temp = new Providers(directory.get(key));
		
		return null;
	}
	
	//search by number and copies to a person brought in
	public Person searchByNumAndCopy(int key, Person temp)
	{
		if(directory1.containsKey(key))
			return temp = new Providers(directory1.get(key));
		
		return null;
	}
	
	//searches by number and displays
	public int searchByNumAndDisplay(int key)
	{
		if(directory1.containsKey(key))
		{	
			directory1.get(key).DisplayInformation();
			return 1;
		}
		return 0;
	}
	
	//searches map by provider name, then displays
	public int searchProNameAndDisplay(String found)
	{
		if(directory.containsKey(found))
		{
			directory.get(found).DisplayInformation();
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
		Person temp = new Providers();
		ProviderDirectory directory = new ProviderDirectory();
		
		temp.setname("Jo");
		temp.SetIDnum();
		temp.setlocation("AAAAA", "CCCC", "ZZZZZZ", 123);
		directory.addPro(temp);
		
		Person temp1 = null;
		
		temp1 = directory.searchByNumAndCopy(temp.IDGetter(), temp1);
		
		if(temp1 != null)
			temp1.DisplayInformation();
		
		
		
		
	}
	
}
