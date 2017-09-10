
public class P1 
{
	public static void main(String[] args)
	{
		test_Sym();
		test_addDecl();
		test_addScope();
		test_lookupLocal();
		test_lookupGlobal();
		test_removeScope();
		test_print();
	}
	
	//Testing Sym class
	private static void test_Sym()
	{
		String[] tempList = {"int", "boolean", "char"};
		
		for(int i = 0; i < tempList.length; i++)
		{
			Sym tempSym = new Sym(new String(tempList[i]));
			
			String tempType = tempSym.getType();
			if(!tempType.equals(tempList[i]))
			{
				System.out.println("getType() returns wrong value");
			}
			
			tempType = tempSym.toString();
			if(!tempType.equals(tempList[i]))
			{
				System.out.println("toString() returns wrong value");
			}
		}
	}
	
	private static void test_addDecl()
	{
		SymTable temp = new SymTable();
		Sym sym_1 = new Sym("int");
		
		try
		{
			temp.addDecl("x", sym_1);
			try
			{
				temp.addDecl("x", sym_1);
			}
			
			catch(DuplicateSymException e)
			{
				System.out.println("Exception was caught properly");
			}
		}
		
		catch (Exception e)
		{
			System.out.println("addDecl() did not work");
		}
	}
	
	private static void test_addScope()
	{
		SymTable temp = new SymTable();
		
		try
		{
			temp.addScope();
		}
		
		catch (Exception e)
		{
			System.out.println("addScope() did not work");
		}
	}
	
	private static void test_lookupLocal()
	{
		SymTable temp = new SymTable();
		Sym sym_1 = new Sym("int");
		
		try
		{
			temp.addDecl("x", sym_1);
			if(temp.lookupLocal("x") == sym_1)
			{
				System.out.println("lookupLocal() did work");
			}
		}
		
		catch (Exception e)
		{
			System.out.println("lookupLocal() did not work");
		}
	}
	
	private static void test_lookupGlobal()
	{
		SymTable temp = new SymTable();
		Sym sym_1 = new Sym("int");
		
		try
		{
			temp.addDecl("x", sym_1);
			if(temp.lookupLocal("x") == sym_1)
			{
				System.out.println("lookupLocal() did work");
			}
		}
		
		catch (Exception e)
		{
			System.out.println("lookupLocal() did not work");
		}
	}
	
	private static void test_removeScope()
	{
		SymTable temp = new SymTable();
		
		try
		{
			temp.removeScope();
		}
		
		catch (Exception e)
		{
			System.out.println("removeScope() did not work");
		}
	}
	
	private static void test_print()
	{
		SymTable temp = new SymTable();
		
		try
		{
			temp.print();
		}
		
		catch (Exception e)
		{
			System.out.println("print() did not work");
		}
		
	}
}
 