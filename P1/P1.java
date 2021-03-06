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
	
	/**
	 * Tests the Sym class
	 */
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
	
	/**
	 * Tests the addDecl() method
	 */
	private static void test_addDecl()
	{
		SymTable temp = new SymTable();
		Sym sym_1 = new Sym("int");
		Sym sym_2 = null;
		
		try
		{
			temp.addDecl("x", sym_1);
			try
			{
				temp.addDecl("x", sym_1);
			}
			
			catch(DuplicateSymException e)
			{
				System.out.println("DuplicateSymException for addDecl() "
						+ "was caught properly");
			}
			
			try
			{
				temp.addDecl("x", sym_2);
			}
			
			catch (NullPointerException e)
			{
				System.out.println("NullPointerException handled when sym is null");
			}
			
			try
			{
				temp.addDecl(null, sym_1);
			}
			
			catch (NullPointerException e)
			{
				System.out.println("NullPointerException handled when name is null");
			}
		}
		
		catch (Exception e)
		{
			System.out.println("addDecl() did not work");
		}
	}
	
	/**
	 * Tests the addScope() method
	 */
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
	
	/**
	 * Tests the lookupLocal() method
	 */
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
			
			try
			{
				temp.removeScope();
				if(temp.lookupLocal("x") == sym_1)
				{
					System.out.println("This statement should not appear as"
							+ "lookupLocal should fail.");
				}
				
			}
			
			catch (EmptySymTableException e)
			{
				System.out.println("EmptySymTableExecption for lookupLocal() "
						+ "was caught properly.");
			}
		}
		
		catch (Exception e)
		{
			System.out.println("lookupLocal() did not work");
		}
	}
	
	/**
	 * Tests the lookupGlobal() method
	 */
	private static void test_lookupGlobal()
	{
		SymTable temp = new SymTable();
		Sym sym_1 = new Sym("int");
		
		try
		{
			temp.addDecl("x", sym_1);
			if(temp.lookupGlobal("x") == sym_1)
			{
				System.out.println("lookupGlobal() did work");
			}
			
			try
			{
				temp.removeScope();
				if(temp.lookupGlobal("x") == sym_1)
				{
					System.out.println("This statement should not appear as"
							+ "lookupGlobal should fail.");
				}
				
			}
			
			catch (EmptySymTableException e)
			{
				System.out.println("EmptySymTableExecption for lookupGlobal() "
						+ "was caught properly.");
			}
		}
		
		catch (Exception e)
		{
			System.out.println("lookupGlobal() did not work");
		}
	}
	
	/**
	 * Tests the removeScope() method
	 */
	private static void test_removeScope()
	{
		SymTable temp = new SymTable();
		
		try
		{
			temp.removeScope();
			try
			{
				temp.removeScope();
			}
			
			catch (EmptySymTableException e)
			{
				System.out.println("EmptySymTableExecption for removeScope() "
						+ "was caught properly.");
			}
		}
		
		catch (Exception e)
		{
			System.out.println("removeScope() did not work");
		}
	}
	
	/**
	 * Tests the print() method
	 */
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