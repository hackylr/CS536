import java.util.*;

public class SemSym {
    private String type;
    private boolean function;
    private boolean struct;
    private List <String> formalsListVals;
    private HashMap <String, SemSym> structMems;
	
    public SemSym(String type, boolean function, boolean struct, List <String> 
		formalsListVals, HashMap <String, SemSym> structMems) throws Exception{
		if (type == null) {
			throw new Exception("Type is null");
		}
		/*if (formalsListVals.isEmpty()) {
			throw new Exception("FormalsListVals is empty");
		}
		if (structMems.isEmpty()) {
			throw new Exception("structMems is empty");
		}*/
        	this.type = type;
		this.function = function;
		this.struct = struct;
		this.formalsListVals = formalsListVals;
		this.structMems = structMems;
    }
    
    public String getType() {
        return type;
    }
	
	public boolean isFunction() {
		return function;
	}
	
	public boolean isStruct() {
		return struct;
	}
    
	public List<String> getFormalsListVals() {
		return formalsListVals;
	}
	
	public HashMap<String, SemSym> getStructMems() {
		return structMems;
	}
	
    public String toString() {
        return type;
    }
}
