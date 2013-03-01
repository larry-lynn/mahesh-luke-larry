package compiler;

import java.util.*;

public class Procedure extends Symbol{

    List<SubProcArgs> args = new ArrayList<SubProcArgs>();

    // Constructor
    public Procedure(String lex, String[] arrayOfArgs){
	super(lex, "procedure_identifier");
	args = Arrays.asList(arrayOfArgs);
    }
    
    public List getArgs(){
	return(this.args);
    }
	
}
