import java.util.*;


//Class for the symbol table
public class SymTable 
{
	private List<HashMap<String, Sym>> symList = new LinkedList<HashMap<String, Sym>>();
	
	//Constructor
	public SymTable()
	{
		symList.add(new HashMap<String, Sym>());
	}
	
	//Error checking
	public void addDecl(String name, Sym sym) throws DuplicateSymException, EmptySymTableException
	{
		if (symList.isEmpty())
		{
			throw new EmptySymTableException();
		}
		
		HashMap<String, Sym> currList = symList.get(0);
		if (currList.containsKey(name))
		{
			throw new DuplicateSymException();
		}
		
		currList.put(name, sym);
	}
	
	//Not sure if need to check if symList is empty or not first, just did so as a precaution
	public void addScope()
	{
		//Check if symList is empty or not first
		if (symList.isEmpty())
		{
			symList.add(new HashMap<String, Sym>());
		}
		
		//If symList is not empty, then place new element at index 0
		else
		{
			symList.add(0, new HashMap<String, Sym>());
		}
	}
	
	public Sym lookupLocal(String name) throws EmptySymTableException
	{
		if (symList.isEmpty())
		{
			throw new EmptySymTableException();
		}
		
//		HashMap<String, Sym> currList = symList.get(0);
//		if(currList.containsKey(name))
//		{
//			return symList.get(0).get(name);
//		}
//		else
//		{
//			return null;
//		}
		
		if (symList.get(0).containsKey(name))
		{
			return symList.get(0).get(name);
		}
		
		else
		{
			return null;
		}
	}
	
	public Sym lookupGlobal(String name) throws EmptySymTableException
	{
		if (symList.isEmpty())
		{
			throw new EmptySymTableException();
		}
		
		for (int i = 0; i < symList.size(); i++)
		{
			if (symList.get(i).containsKey(name))
			{
				return symList.get(i).get(name); 
			}
		}
		
		return null;
	}
	
	public void removeScope() throws EmptySymTableException
	{
		if (symList.isEmpty())
		{
			throw new EmptySymTableException();
		}
		
		symList.remove(0);
	}
	
	public void print()
	{
		 System.out.print("\nSym Table\n");
		 
	     for (int i = 0; i < symList.size(); i++) 
	     {
	         System.out.println(symList.get(i).toString());
	     }
	     
	     System.out.print("\n");
	}
	
}
