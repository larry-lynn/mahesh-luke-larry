package compiler;

import java.util.*;

public class Procedure extends Symbol{

    List<SubProcArgs> args = new ArrayList<SubProcArgs>();

    // Constructor
    public Procedure(String lex, SubProcArgs[] arrayOfArgs){
	super(lex, ParserSymbol.MP_SYMBOL_PROCEDURE);
	args = Arrays.asList(arrayOfArgs);
    }
    
    public List getArgs(){
	return(this.args);
    }
	
}
