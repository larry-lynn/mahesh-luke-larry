package compiler;

import java.util.*;

public class Function extends Symbol{

    ParserSymbol return_type;
    List<SubProcArgs> args = new ArrayList<SubProcArgs>();

    // Constructor
    public Function(String lex, ParserSymbol rtype, SubProcArgs[] arrayOfArgs){
	super(lex, ParserSymbol.MP_SYMBOL_FUNCTION);

	return_type = rtype;
	args = Arrays.asList(arrayOfArgs);
    }
    
    
    public Enum getReturnType(){
    	return(this.return_type);
    }

    public List getArgs(){
	return(this.args);
    }
	
}
