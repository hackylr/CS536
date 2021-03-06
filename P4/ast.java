///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            P4.java
// Files:            SemSym.java, SymTable.java, ast.java, ErrMsg.java, test.cf, 
//						nameErrors.cf, cimple.cup, cimple.grammar, cimple.jlex,
//						DuplicateSymException.java, EmptySymTableException.java
//						Makefile, test.out, nameErrors.out

// Semester:         CS536 Fall 2017
//
// Author:           Damon Francisco
// Email:            dfrancisco@wisc.edu
// CS Login:         damon
// Lecturer's Name:  Aws Albarghouthi
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Avery Chen
// Email:            agchen@wisc.edu
// CS Login:         avery
// Lecturer's Name:  Aws Albarghouthi
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//
// Online sources:   Piazza, Stack Overflow
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.*;
import java.util.*;

// **********************************************************************
// The ASTnode class defines the nodes of the abstract-syntax tree that
// represents a Moo program.
//
// Internal nodes of the tree contain pointers to children, organized
// either in a list (for nodes that may have a variable number of 
// children) or as a fixed set of fields.
//
// The nodes for literals and ids contain line and character number
// information; for string literals and identifiers, they also contain a
// string; for integer literals, they also contain an integer value.
//
// Here are all the different kinds of AST nodes and what kinds of children
// they have.  All of these kinds of AST nodes are subclasses of "ASTnode".
// Indentation indicates further subclassing:
//
//     Subclass            Kids
//     --------            ----
//     ProgramNode         DeclListNode
//     DeclListNode        linked list of DeclNode
//     DeclNode:
//       VarDeclNode       TypeNode, IdNode, int
//       FnDeclNode        TypeNode, IdNode, FormalsListNode, FnBodyNode
//       FormalDeclNode    TypeNode, IdNode
//       StructDeclNode    IdNode, DeclListNode
//
//     FormalsListNode     linked list of FormalDeclNode
//     FnBodyNode          DeclListNode, StmtListNode
//     StmtListNode        linked list of StmtNode
//     ExpListNode         linked list of ExpNode
//
//     TypeNode:
//       IntNode           -- none --
//       BoolNode          -- none --
//       VoidNode          -- none --
//       StructNode        IdNode
//
//     StmtNode:
//       AssignStmtNode      AssignNode
//       PostIncStmtNode     ExpNode
//       PostDecStmtNode     ExpNode
//       ReadStmtNode        ExpNode
//       WriteStmtNode       ExpNode
//       IfStmtNode          ExpNode, DeclListNode, StmtListNode
//       IfElseStmtNode      ExpNode, DeclListNode, StmtListNode,
//                                    DeclListNode, StmtListNode
//       WhileStmtNode       ExpNode, DeclListNode, StmtListNode
//       CallStmtNode        CallExpNode
//       ReturnStmtNode      ExpNode
//
//     ExpNode:
//       IntLitNode          -- none --
//       StrLitNode          -- none --
//       TrueNode            -- none --
//       FalseNode           -- none --
//       IdNode              -- none --
//       DotAccessNode       ExpNode, IdNode
//       AssignNode          ExpNode, ExpNode
//       CallExpNode         IdNode, ExpListNode
//       UnaryExpNode        ExpNode
//         UnaryMinusNode
//         NotNode
//       BinaryExpNode       ExpNode ExpNode
//         PlusNode     
//         MinusNode
//         TimesNode
//         DivideNode
//         AndNode
//         OrNode
//         EqualsNode
//         NotEqualsNode
//         LessNode
//         GreaterNode
//         LessEqNode
//         GreaterEqNode
//
// Here are the different kinds of AST nodes again, organized according to
// whether they are leaves, internal nodes with linked lists of kids, or
// internal nodes with a fixed number of kids:
//
// (1) Leaf nodes:
//        IntNode,   BoolNode,  VoidNode,  IntLitNode,  StrLitNode,
//        TrueNode,  FalseNode, IdNode
//
// (2) Internal nodes with (possibly empty) linked lists of children:
//        DeclListNode, FormalsListNode, StmtListNode, ExpListNode
//
// (3) Internal nodes with fixed numbers of kids:
//        ProgramNode,     VarDeclNode,     FnDeclNode,     FormalDeclNode,
//        StructDeclNode,  FnBodyNode,      StructNode,     AssignStmtNode,
//        PostIncStmtNode, PostDecStmtNode, ReadStmtNode,   WriteStmtNode   
//        IfStmtNode,      IfElseStmtNode,  WhileStmtNode,  CallStmtNode
//        ReturnStmtNode,  DotAccessNode,   AssignExpNode,  CallExpNode,
//        UnaryExpNode,    BinaryExpNode,   UnaryMinusNode, NotNode,
//        PlusNode,        MinusNode,       TimesNode,      DivideNode,
//        AndNode,         OrNode,          EqualsNode,     NotEqualsNode,
//        LessNode,        GreaterNode,     LessEqNode,     GreaterEqNode
//
// **********************************************************************

// **********************************************************************
// ASTnode class (base class for all other kinds of nodes)
// **********************************************************************

abstract class ASTnode { 
    // every subclass must provide an unparse operation
    abstract public void unparse(PrintWriter p, int indent);
	
    abstract public void nameAnalysis(SymTable symTab);

    // this method can be used by the unparse methods to do indenting
    protected void doIndent(PrintWriter p, int indent) {
        for (int k=0; k<indent; k++) p.print(" ");
    }
	
}

// **********************************************************************
// ProgramNode,  DeclListNode, FormalsListNode, FnBodyNode,
// StmtListNode, ExpListNode
// **********************************************************************

class ProgramNode extends ASTnode {
    public ProgramNode(DeclListNode L) {
        myDeclList = L;
    }

    /**
     * Sample name analysis method. 
     * Creates an empty symbol table for the outermost scope, then processes
     * all of the globals, struct defintions, and functions in the program.
     */
    public void nameAnalysis(SymTable symTab) {
        //SymTable symTab = new SymTable();
	myDeclList.nameAnalysis(symTab);
    }

    public void unparse(PrintWriter p, int indent) {
        myDeclList.unparse(p, indent);
    }

    // 1 kid
    private DeclListNode myDeclList;
}

class DeclListNode extends ASTnode {
    public DeclListNode(List<DeclNode> S) {
        myDecls = S;
    }

    public void nameAnalysis(SymTable symTab){
        Iterator it = myDecls.iterator();
        try {
            while (it.hasNext()) {
                ((DeclNode)it.next()).nameAnalysis(symTab);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in DeclListNode.print");
            System.exit(-1);
        }
    }		
	
	public void unparse(PrintWriter p, int indent) {
        Iterator it = myDecls.iterator();
        try {
            while (it.hasNext()) {
                ((DeclNode)it.next()).unparse(p, indent);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in DeclListNode.print");
            System.exit(-1);
        }
    }

		
    public HashMap<String, SemSym> retStructMems(SymTable symTab) {
        HashMap<String, SemSym> mems = new HashMap<String, SemSym>();
	Iterator iterator = myDecls.iterator();
	SemSym symbol = null;

	symTab.addScope();
	while(iterator.hasNext())
	{
		VarDeclNode tmp = (VarDeclNode)iterator.next();
		tmp.nameAnalysis(symTab);
		String name = tmp.getID().getMyStrVal();
		
		if(tmp.getID().getSemSym().isStruct())
		{
			try
			{
				symbol = new SemSym(tmp.getID().getMyStrVal(), 
						false, true, tmp.getID().getSemSym().getStructMems());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(-1);	
			}
		}
		else
		{
			try
			{
				symbol = new SemSym(tmp.getID().getMyStrVal(), false, false, null);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(-1);	
			}
	
		}
			
		mems.put(tmp.getID().getMyStrVal(), symbol);
	}
	return mems; 
    }


    // list of kids (DeclNodes)
    private List<DeclNode> myDecls;
}

class FormalsListNode extends ASTnode {
    public FormalsListNode(List<FormalDeclNode> S) {
        myFormals = S;
    }

    public void nameAnalysis(SymTable symTab){
        Iterator it = myFormals.iterator();
        try {
            while (it.hasNext()) {
                ((FormalDeclNode)it.next()).nameAnalysis(symTab);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in FormalsListNode.print");
            System.exit(-1);
        }
    }		
		
    public void unparse(PrintWriter p, int indent) {
        Iterator<FormalDeclNode> it = myFormals.iterator();
        if (it.hasNext()) { // if there is at least one element
            it.next().unparse(p, indent);
            while (it.hasNext()) {  // print the rest of the list
                p.print(", ");
                it.next().unparse(p, indent);
            }
        } 
    }

    // list of kids (FormalDeclNodes)
    private List<FormalDeclNode> myFormals;
}

class FnBodyNode extends ASTnode {
    public FnBodyNode(DeclListNode declList, StmtListNode stmtList) {
        myDeclList = declList;
        myStmtList = stmtList;
    }
    
    public void nameAnalysis(SymTable symTab){
	myDeclList.nameAnalysis(symTab);
	myStmtList.nameAnalysis(symTab);
    }	

    public void unparse(PrintWriter p, int indent) {
        myDeclList.unparse(p, indent);
        myStmtList.unparse(p, indent);
    }

    // 2 kids
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class StmtListNode extends ASTnode {
    public StmtListNode(List<StmtNode> S) {
        myStmts = S;
    }

    public void nameAnalysis(SymTable symTab){
        Iterator it = myStmts.iterator();
        try {
            while (it.hasNext()) {
                ((StmtNode)it.next()).nameAnalysis(symTab);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in StmtListNode.print");
            System.exit(-1);
        }
    }
		
    public void unparse(PrintWriter p, int indent) {
        Iterator<StmtNode> it = myStmts.iterator();
        while (it.hasNext()) {
            it.next().unparse(p, indent);
        }
    }

    // list of kids (StmtNodes)
    private List<StmtNode> myStmts;
}

class ExpListNode extends ASTnode {
    public ExpListNode(List<ExpNode> S) {
        myExps = S;
    }

    public void nameAnalysis(SymTable symTab){
        Iterator it = myExps.iterator();
        try {
            while (it.hasNext()) {
                ((ExpNode)it.next()).nameAnalysis(symTab);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in ExpListNode.print");
            System.exit(-1);
        }
    }
    public void unparse(PrintWriter p, int indent) {
        Iterator<ExpNode> it = myExps.iterator();
        if (it.hasNext()) { // if there is at least one element
            it.next().unparse(p, indent);
            while (it.hasNext()) {  // print the rest of the list
                p.print(", ");
                it.next().unparse(p, indent);
            }
        } 
    }

    // list of kids (ExpNodes)
    private List<ExpNode> myExps;
}

// **********************************************************************
// DeclNode and its subclasses
// **********************************************************************

abstract class DeclNode extends ASTnode {
	

}

class VarDeclNode extends DeclNode {
    public VarDeclNode(TypeNode type, IdNode id, int size) {
        myType = type;
        myId = id;
        mySize = size;
    }

    public void nameAnalysis(SymTable symTab){
	//myStrVal
	//myType = myId.getType();
	String tmp = myId.getId();
	//myType.setType(mytype.getID().getType());
	//myType.setType(myType.getID().getType());	
	myType.nameAnalysis(symTab);
	SemSym symbol = symTab.lookupGlobal(myId.getMyStrVal());
	if (symbol != null) {
	   if (symbol.isStruct()) {
	      myId.nameAnalysis(symTab, 2, null, null);
	   }
	   // if type = bool or int
	   else {
	      myId.nameAnalysis(symTab, 1, null, null);   
	   } 
	}
	else {
	   myId.nameAnalysis(symTab, 7, null, null);
	}
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myType.unparse(p, 0);
        p.print(" ");
        myId.unparse(p, 0);
        p.println(";");
    }

    public IdNode getID()
    {
	return myId;
    }

    public TypeNode getType()

    {
	return myType;
    }

    // 3 kids
    private TypeNode myType;
    private IdNode myId;
    private int mySize;  // use value NOT_STRUCT if this is not a struct type

    public static int NOT_STRUCT = -1;
}

class FnDeclNode extends DeclNode {
    public FnDeclNode(TypeNode type,
                      IdNode id,
                      FormalsListNode formalList,
                      FnBodyNode body) {
        myType = type;
        myId = id;
        myFormalsList = formalList;
        myBody = body;
    }

    public void nameAnalysis(SymTable symTab){
	//myId.setType(myId.getType());
	myId.nameAnalysis(symTab, 3, null, null);
	
	symTab.addScope();
	myFormalsList.nameAnalysis(symTab);
	myBody.nameAnalysis(symTab);

	try {
	   symTab.removeScope();
	//TODO: Add message?
	} catch (EmptySymTableException e) { }
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myType.unparse(p, 0);
        p.print(" ");
        myId.unparse(p, 0);
        p.print("(");
        myFormalsList.unparse(p, 0);
        p.println(") {");
        myBody.unparse(p, indent+4);
        p.println("}\n");
    }

    // 4 kids
    private TypeNode myType;
    private IdNode myId;
    private FormalsListNode myFormalsList;
    private FnBodyNode myBody;
}

class FormalDeclNode extends DeclNode {
    public FormalDeclNode(TypeNode type, IdNode id) {
        myType = type;
        myId = id;
    }

    public void nameAnalysis(SymTable symTab){
	//myId.setType(myId.getType());
	myType.nameAnalysis(symTab);
	myId.nameAnalysis(symTab, 1, null, null);
    }

    public void unparse(PrintWriter p, int indent) {
        myType.unparse(p, 0);
        p.print(" ");
        myId.unparse(p, 0);
    }

    // 2 kids
    private TypeNode myType;
    private IdNode myId;
}

class StructDeclNode extends DeclNode {
    public StructDeclNode(IdNode id, DeclListNode declList) {
        myId = id;
        myDeclList = declList;
    }

    public void nameAnalysis(SymTable symTab){
	HashMap <String, SemSym> structMems = myDeclList.retStructMems(symTab);
	//myId.setType(myId.getType());
	myId.nameAnalysis(symTab, 2, structMems, null);
	symTab.addScope();
	myDeclList.nameAnalysis(symTab);
	try {
	   symTab.removeScope();
	//TODO: Add message?
	} catch (EmptySymTableException e) { 
	   e.printStackTrace();
	   System.exit(-1);
	}
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("struct ");
	myId.unparse(p, 0);
	p.println("{");
        myDeclList.unparse(p, indent+4);
        doIndent(p, indent);
        p.println("};\n");

    }

    // 2 kids
    private IdNode myId;
    private DeclListNode myDeclList;
}

// **********************************************************************
// TypeNode and its Subclasses
// **********************************************************************

abstract class TypeNode extends ASTnode {	
}

class IntNode extends TypeNode {
    public IntNode() {
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("int");
    }
}

class BoolNode extends TypeNode {
    public BoolNode() {
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("bool");
    }
}

class VoidNode extends TypeNode {
    public VoidNode() {
    }
    
    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("void");
    }
}

class StructNode extends TypeNode {
    public StructNode(IdNode id) {
		myId = id;
    }
    
    public void nameAnalysis(SymTable symTab){
        myId.nameAnalysis(symTab);
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("struct ");
		myId.unparse(p, 0);
    }

	
	// 1 kid
    private IdNode myId;
}

// **********************************************************************
// StmtNode and its subclasses
// **********************************************************************

abstract class StmtNode extends ASTnode {
}

class AssignStmtNode extends StmtNode {
    public AssignStmtNode(AssignNode assign) {
        myAssign = assign;
    }
    
    public void nameAnalysis(SymTable symTab){
        myAssign.nameAnalysis(symTab);
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myAssign.unparse(p, -1); // no parentheses
        p.println(";");
    }

    // 1 kid
    private AssignNode myAssign;
}

class PostIncStmtNode extends StmtNode {
    public PostIncStmtNode(ExpNode exp) {
        myExp = exp;
    }

    public void nameAnalysis(SymTable symTab){
        myExp.nameAnalysis(symTab);
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myExp.unparse(p, 0);
        p.println("++;");
    }

    // 1 kid
    private ExpNode myExp;
}

class PostDecStmtNode extends StmtNode {
    public PostDecStmtNode(ExpNode exp) {
        myExp = exp;
    }

    public void nameAnalysis(SymTable symTab){
        myExp.nameAnalysis(symTab);
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myExp.unparse(p, 0);
        p.println("--;");
    }

    // 1 kid
    private ExpNode myExp;
}

class ReadStmtNode extends StmtNode {
    public ReadStmtNode(ExpNode e) {
        myExp = e;
    }

    public void nameAnalysis(SymTable symTab){
        myExp.nameAnalysis(symTab);
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("cin >> ");
        myExp.unparse(p, 0);
        p.println(";");
    }

    // 1 kid (actually can only be an IdNode or an ArrayExpNode)
    private ExpNode myExp;
}

class WriteStmtNode extends StmtNode {
    public WriteStmtNode(ExpNode exp) {
        myExp = exp;
    }

    public void nameAnalysis(SymTable symTab){
        myExp.nameAnalysis(symTab);
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("cout << ");
        myExp.unparse(p, 0);
        p.println(";");
    }

    // 1 kid
    private ExpNode myExp;
}

class IfStmtNode extends StmtNode {
    public IfStmtNode(ExpNode exp, DeclListNode dlist, StmtListNode slist) {
        myDeclList = dlist;
        myExp = exp;
        myStmtList = slist;
    }

    public void nameAnalysis(SymTable symTab){
	
        myExp.nameAnalysis(symTab);
	symTab.addScope();
        myDeclList.nameAnalysis(symTab);
	myStmtList.nameAnalysis(symTab);
	try {
	   symTab.removeScope();
	//TODO: Add message?
	} catch (EmptySymTableException e) { 
		e.printStackTrace();
		System.exit(-1);
	}
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("if (");
        myExp.unparse(p, 0);
        p.println(") {");
        myDeclList.unparse(p, indent+4);
        myStmtList.unparse(p, indent+4);
        doIndent(p, indent);
        p.println("}");
    }

    // e kids
    private ExpNode myExp;
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class IfElseStmtNode extends StmtNode {
    public IfElseStmtNode(ExpNode exp, DeclListNode dlist1,
                          StmtListNode slist1, DeclListNode dlist2,
                          StmtListNode slist2) {
        myExp = exp;
        myThenDeclList = dlist1;
        myThenStmtList = slist1;
        myElseDeclList = dlist2;
        myElseStmtList = slist2;
    }

    public void nameAnalysis(SymTable symTab){
        myExp.nameAnalysis(symTab);
        symTab.addScope();
	myThenDeclList.nameAnalysis(symTab);
        myThenStmtList.nameAnalysis(symTab);
	try {
	   symTab.removeScope();
	//TODO: Add message?
	} catch (EmptySymTableException e) {	
		e.printStackTrace();
		System.exit(-1);
	}
       
	symTab.addScope(); 
	myElseDeclList.nameAnalysis(symTab);
        myElseStmtList.nameAnalysis(symTab);
	try {
	   symTab.removeScope();
	//TODO: Add message?
	} catch (EmptySymTableException e) { 	
		e.printStackTrace();
		System.exit(-1);
	}
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("if (");
        myExp.unparse(p, 0);
        p.println(") {");
        myThenDeclList.unparse(p, indent+4);
        myThenStmtList.unparse(p, indent+4);
        doIndent(p, indent);
        p.println("}");
        doIndent(p, indent);
        p.println("else {");
        myElseDeclList.unparse(p, indent+4);
        myElseStmtList.unparse(p, indent+4);
        doIndent(p, indent);
        p.println("}");        
    }

    // 5 kids
    private ExpNode myExp;
    private DeclListNode myThenDeclList;
    private StmtListNode myThenStmtList;
    private StmtListNode myElseStmtList;
    private DeclListNode myElseDeclList;
}

class WhileStmtNode extends StmtNode {
    public WhileStmtNode(ExpNode exp, DeclListNode dlist, StmtListNode slist) {
        myExp = exp;
        myDeclList = dlist;
        myStmtList = slist;
    }
	
    public void nameAnalysis(SymTable symTab){
        myExp.nameAnalysis(symTab);
	symTab.addScope();
        myDeclList.nameAnalysis(symTab);
        myStmtList.nameAnalysis(symTab);
	try {
	   symTab.removeScope();
	//TODO: Add message?
	} catch (EmptySymTableException e) { 
		e.printStackTrace();
		System.exit(-1);

	}
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("while (");
        myExp.unparse(p, 0);
        p.println(") {");
        myDeclList.unparse(p, indent+4);
        myStmtList.unparse(p, indent+4);
        doIndent(p, indent);
        p.println("}");
    }

    // 3 kids
    private ExpNode myExp;
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class CallStmtNode extends StmtNode {
    public CallStmtNode(CallExpNode call) {
        myCall = call;
    }

    public void nameAnalysis(SymTable symTab){
        myCall.nameAnalysis(symTab);
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myCall.unparse(p, indent);
        p.println(";");
    }

    // 1 kid
    private CallExpNode myCall;
}

class ReturnStmtNode extends StmtNode {
    public ReturnStmtNode(ExpNode exp) {
        myExp = exp;
    }

    public void nameAnalysis(SymTable symTab){
        if (myExp != null) {
	    myExp.nameAnalysis(symTab);
	}
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("return");
        if (myExp != null) {
            p.print(" ");
            myExp.unparse(p, 0);
        }
        p.println(";");
    }

    // 1 kid
    private ExpNode myExp; // possibly null
}

// **********************************************************************
// ExpNode and its subclasses
// **********************************************************************

abstract class ExpNode extends ASTnode {
    public ExpNode getMyLoc() {
       return null;
    }
}

class IntLitNode extends ExpNode {
    public IntLitNode(int lineNum, int charNum, int intVal) {
        myLineNum = lineNum;
        myCharNum = charNum;
        myIntVal = intVal;
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
        p.print(myIntVal);
    }

    private int myLineNum;
    private int myCharNum;
    private int myIntVal;
}

class StringLitNode extends ExpNode {
    public StringLitNode(int lineNum, int charNum, String strVal) {
        myLineNum = lineNum;
        myCharNum = charNum;
        myStrVal = strVal;
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
        p.print(myStrVal);
    }

    private int myLineNum;
    private int myCharNum;
    private String myStrVal;
}

class TrueNode extends ExpNode {
    public TrueNode(int lineNum, int charNum) {
        myLineNum = lineNum;
        myCharNum = charNum;
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("true");
    }

    private int myLineNum;
    private int myCharNum;
}

class FalseNode extends ExpNode {
    public FalseNode(int lineNum, int charNum) {
        myLineNum = lineNum;
        myCharNum = charNum;
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("false");
    }

    private int myLineNum;
    private int myCharNum;
}

class IdNode extends ExpNode {
    public IdNode(int lineNum, int charNum, String strVal) {
        myLineNum = lineNum;
        myCharNum = charNum;
        myStrVal = strVal;
    }

    // Ignored
    public void nameAnalysis(SymTable symTab) {
    }    

    public void nameAnalysis(SymTable symTab, int val, HashMap <String, SemSym>
	structMems, ExpNode myLoc) {
	// Check for undeclared identifier
	/*symbol = symTab.lookupGlobal(myStrVal);
	if (symbol == null) {
	   ErrMsg.fatal(myLineNum, myCharNum, "Undeclared identifier");	
	   ErrMsg.error = true;
	}
	else 
	{
	   type = symbol.getType();
	}*/
	addDeclaration(myStrVal, symbol, symTab);
	symbol = symTab.lookupGlobal(myStrVal);
	if (symbol == null) {
	   ErrMsg.fatal(myLineNum, myCharNum, "Undeclared identifier");	
	   ErrMsg.error = true;
	}
	else 
	{
	   type = symbol.getType();
	}

	switch (val) {
	   // For regular SemSym (no functions or structs)
	   case 1:
	      // Check for non-function void
 	      if (type == "void") {
	         ErrMsg.fatal(myLineNum, myCharNum, "Non-function declared void");
	   	 ErrMsg.error = true;
	      }
	      else {
	         try {
	   	    symbol = new SemSym(type, false, false, null);
           	    type = symbol.getType();	

		 }
		 catch (Exception e)
		 {
		    e.printStackTrace();
		    System.exit(-1);
	         }
				
		 if (symTab.lookupLocal(myStrVal) == null)
		 {
	    	    addDeclaration(myStrVal, symbol, symTab);
		 }
	
		 else
		 {
	     	    ErrMsg.fatal(myLineNum, myCharNum, "Multiply declared identifier");
	   	    ErrMsg.error = true;
		 }
	
	     }
	      break;
	  // For struct SemSyms
          case 2:
	      String structName = "struct " + myStrVal; 
	      SemSym structSymbol = symTab.lookupGlobal(structName);
	      
	      type = structSymbol.getType();

	      if (structSymbol == null) {
	         ErrMsg.fatal(myLineNum, myCharNum, "Invalid name of struct type");
	         ErrMsg.error = true;
	      }
	      //TODO: Add in struct members somehow?
	      SemSym instanceOfStruct = null;
	      
	      try {
	        instanceOfStruct = new SemSym(type, false, true, structMems);
	      } catch (Exception e) {
		 e.printStackTrace();
		 System.exit(-1);
	      }
	      addDeclaration(myStrVal, instanceOfStruct, symTab);
	      break;
	   // For function SemSyms
           case 3:
		// Function check for multiply declared name
		String fnName = myStrVal;	
		SemSym fnPrev = symTab.lookupGlobal(fnName);	
		SemSym fn = null;
	
		if (fnPrev == null) {
		   ErrMsg.fatal(myLineNum, myCharNum, "Undeclared identifier");
		}	
		else {
		   try {
	             fn = new SemSym(type, true, false, null);
		   } catch (Exception e) {
		     e.printStackTrace();
		     System.exit(-1);
	           }

		   if (fnPrev != null) {
		     //TODO Comparison of fnPrev and fn?
		     ErrMsg.fatal(myLineNum, myCharNum, "Multiply declared identifier");
	   	     ErrMsg.error = true;
		   }

		   else {
		     addDeclaration(fnName, fn, symTab);
	           }
	        }	
	        break;
	   // Verify that struct is on LHS
	   case 4:
		symbol = symTab.lookupGlobal(myStrVal);
        	if (symbol == null) {
           	   ErrMsg.fatal(myLineNum, myCharNum, "Undeclared identifier");
	   	   ErrMsg.error = true;
                }
	        else {
		   type = symbol.getType();
		   
		   if (!symbol.isStruct()) {
		      ErrMsg.fatal(myLineNum, myCharNum, "Dot-access of non-struct type");
	   	      ErrMsg.error = true;
		   }		   
                }
	       break;
	   // DotAccessNode - Check RHS when loc exists
	   case 5:
		IdNode struct = (IdNode) myLoc;
		symbol = symTab.lookupGlobal(struct.getMyStrVal());      
		  
		if (symbol != null) {
		   HashMap <String, SemSym> structVars = symbol.getStructMems();
	
		   if (structVars.containsKey(myStrVal)) {
		      symbol = structVars.get(myStrVal);	  
		      type = symbol.getType(); 
		   }
		   else {
		      ErrMsg.fatal(myLineNum, myCharNum, "Invalid struct field name");
	   	      ErrMsg.error = true;
		   }
	        }
		else {
		      ErrMsg.fatal(myLineNum, myCharNum, "Undeclared identifier");
	   	      ErrMsg.error = true;
		}
		break;
	   // DotAccessNode - Check RHS when loc doesn't exist
	   case 6:
		IdNode currStruct = (IdNode) myLoc;
		symbol = symTab.lookupGlobal(currStruct.getMyStrVal());
	        
		if (symbol != null) {
		   HashMap <String, SemSym> structVars = symbol.getStructMems();	
		   if (structVars == null) {
		      ErrMsg.fatal(myLineNum, myCharNum, "Dot-access of non-struct type");
	   	      ErrMsg.error = true;
		   }
		   else {
		       if (structVars.containsKey(myStrVal)) {
		      	  symbol = structVars.get(myStrVal);	  
		          type = symbol.getType(); 
		       }
		       else {
			  ErrMsg.fatal(myLineNum, myCharNum, "Invalid struct field name");
	   		  ErrMsg.error = true;
		       }
		   }	
		} 
		else {
		      ErrMsg.fatal(myLineNum, myCharNum, "Undeclared identifier");
	   	      ErrMsg.error = true;
		}
		break;
	}
	}

    public void unparse(PrintWriter p, int indent) {
	p.print(myStrVal);
	p.print("("+type+")");
    }

    public String getMyStrVal() {
        return myStrVal;
    }

    public String getType()
    {
	return type;
    }

    public SemSym getSemSym()
    {
	return symbol;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addDeclaration(String name, SemSym symbol, SymTable symTab) {
	try
	{
	   symTab.addDecl(name, symbol);
	}
	catch (DuplicateSymException e)
	{
	   System.err.println("addDecl failed:" + e);
	   System.exit(-1);
	} 
	catch (EmptySymTableException e)
	{
	   System.err.println("addDecl failed:" + e);
	   System.exit(-1);
	} 
	catch (NullPointerException e)
	{
	   System.err.println("addDecl failed:" + e);
	   System.exit(-1);
	}

    }

    private int myLineNum;
    private int myCharNum;
    private String myStrVal;
    private SemSym symbol;
    private String type;
}

class DotAccessExpNode extends ExpNode {
    public DotAccessExpNode(ExpNode loc, IdNode id) {
        myLoc = loc;	
        myId = id;
    }

    public void nameAnalysis(SymTable symTab){
	myId.nameAnalysis(symTab, 4, null, null);
	
	// RHS is a loc
	if (getMyLoc() != null) {
	   myLoc.nameAnalysis(symTab);
	   myId.nameAnalysis(symTab, 5, null, getMyLoc());
	}
	else {
	   myId.nameAnalysis(symTab, 6, null, getMyLoc());
	}
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
	    myLoc.unparse(p, 0);
	    p.print(").");
	    myId.unparse(p, 0);
    }

    public ExpNode getMyLoc() {
	return myLoc;
    }

    // 2 kids
    private ExpNode myLoc;	
    private IdNode myId;
}

class AssignNode extends ExpNode {
    public AssignNode(ExpNode lhs, ExpNode exp) {
        myLhs = lhs;
        myExp = exp;
    }

    public void nameAnalysis(SymTable symTab){
        myLhs.nameAnalysis(symTab);
        myExp.nameAnalysis(symTab);
    }

    public void unparse(PrintWriter p, int indent) {
		if (indent != -1)  p.print("(");
	    myLhs.unparse(p, 0);
		p.print(" = ");
		myExp.unparse(p, 0);
		if (indent != -1)  p.print(")");
    }

    // 2 kids
    private ExpNode myLhs;
    private ExpNode myExp;
}

class CallExpNode extends ExpNode {
    public CallExpNode(IdNode name, ExpListNode elist) {
        myId = name;
        myExpList = elist;
    }

    public CallExpNode(IdNode name) {
        myId = name;
        myExpList = new ExpListNode(new LinkedList<ExpNode>());
    }

    public void nameAnalysis(SymTable symTab){
        myId.nameAnalysis(symTab);
        if (myExpList != null) {
	   myExpList.nameAnalysis(symTab);
	}
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	    myId.unparse(p, 0);
	    p.print("(");
	    if (myExpList != null) {
		myExpList.unparse(p, 0);
	    }
	    p.print(")");
    }

    // 2 kids
    private IdNode myId;
    private ExpListNode myExpList;  // possibly null
}

abstract class UnaryExpNode extends ExpNode {
    public UnaryExpNode(ExpNode exp) {
        myExp = exp;
    }

    // one child
    protected ExpNode myExp;
}

abstract class BinaryExpNode extends ExpNode {
    public BinaryExpNode(ExpNode exp1, ExpNode exp2) {
        myExp1 = exp1;
        myExp2 = exp2;
    }

    // two kids
    protected ExpNode myExp1;
    protected ExpNode myExp2;
}

// **********************************************************************
// Subclasses of UnaryExpNode
// **********************************************************************

class UnaryMinusNode extends UnaryExpNode {
    public UnaryMinusNode(ExpNode exp) {
        super(exp);
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(-");
		myExp.unparse(p, 0);
		p.print(")");
    }
}

class NotNode extends UnaryExpNode {
    public NotNode(ExpNode exp) {
        super(exp);
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(!");
		myExp.unparse(p, 0);
		p.print(")");
    }
}

// **********************************************************************
// Subclasses of BinaryExpNode
// **********************************************************************

class PlusNode extends BinaryExpNode {
    public PlusNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" + ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class MinusNode extends BinaryExpNode {
    public MinusNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" - ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class TimesNode extends BinaryExpNode {
    public TimesNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }
    
    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" * ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class DivideNode extends BinaryExpNode {
    public DivideNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" / ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class AndNode extends BinaryExpNode {
    public AndNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" && ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class OrNode extends BinaryExpNode {
    public OrNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" || ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class EqualsNode extends BinaryExpNode {
    public EqualsNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" == ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class NotEqualsNode extends BinaryExpNode {
    public NotEqualsNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" != ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class LessNode extends BinaryExpNode {
    public LessNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }
    
    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" < ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class GreaterNode extends BinaryExpNode {
    public GreaterNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" > ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class LessEqNode extends BinaryExpNode {
    public LessEqNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" <= ");  
		myExp2.unparse(p, 0);
		p.print(")");
    }
}

class GreaterEqNode extends BinaryExpNode {
    public GreaterEqNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void nameAnalysis(SymTable symTab){
    }

    public void unparse(PrintWriter p, int indent) {
	    p.print("(");
		myExp1.unparse(p, 0);
		p.print(" >= ");
		myExp2.unparse(p, 0);
		p.print(")");
    }
}
