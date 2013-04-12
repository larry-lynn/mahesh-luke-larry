package compiler;

import java.util.*;

public class Function extends SymbolWithType{

    //SymbolType return_type;
    ArrayList<Args> args;
    //List<SubProcArgs> args = new ArrayList<SubProcArgs>();

    // Constructor
    public Function(String lex, SymbolType rtype, ArrayList<Args> argList){
	//super(lex, SymbolKind.MP_SYMBOL_FUNCTION);
	    super(lex, rtype);
	    this.kind = SymbolKind.MP_SYMBOL_FUNCTION;
	    //return_type = rtype;
        args = argList;

    }
    
    
    public SymbolType getReturnType(){
    	return(this.getType());
    }

    public ArrayList<Args> getArgs(){
	return(this.args);
    }
	
}
