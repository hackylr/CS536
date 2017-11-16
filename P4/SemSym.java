import java.util.*;

public class SemSym {
    private String type;
	private boolean function;
    private boolean struct;
	private List <String> formalsListVals;
	private HashMap <String, SemSym> structMems;
	
    public SemSym(String type, boolean function, boolean struct, List <String> 
		formalsListVals, HashMap <String, SemSym> structMems) {
		if (type == null) {
			throw Exception("Type is null");
		}
		if (formalsListVals == null || formalsListVals.isEmpty()) {
			throw Exception("FormalsListVals is null or empty");
		}
		if (structMems == null || structMems.isEmpty()) {
			throw Exception("structMems is null or empty");
		}
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
