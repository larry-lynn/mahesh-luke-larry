package compiler;

import java.util.*;

public class Function extends Symbol{

    public enum Return_type {
	Integer, Float, Boolean
    }

    Return_type return_type;
    List<SubProcArgs> args = new ArrayList<SubProcArgs>();

    // Constructor
    public Function(String lex, String rtype, String[] arrayOfArgs){
	super(lex, "function_identifier");

	switch(rtype){
	case "Integer":
	    return_type = Return_type.Integer;
	    break;

	case "Float":
	    return_type = Return_type.Float;
	    break;

	case "Boolean":
	    return_type = Return_type.Boolean;
	    break;
	}

	args = Arrays.asList(arrayOfArgs);
    }
    
    
    public Enum getReturnType(){
    	return(this.return_type);
    }

    public List getArgs(){
	return(this.args);
    }
	
}
