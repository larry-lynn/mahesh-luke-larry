package compiler;

import java.util.*;

public class Function extends SymbolWithType{

    String jumpLabel;
    ArrayList<Args> args;

    // Constructor
    public Function(String lex, SymbolType rtype, ArrayList<Args> argList, String newLabel){
	    super(lex, rtype);
	    this.kind = SymbolKind.MP_SYMBOL_FUNCTION;
        args = argList;
        jumpLabel = newLabel;
    }
   
    // XXX this method might be unnecessary
    public SymbolType getReturnType(){
    	return(this.getType());
    }

    public ArrayList<Args> getArgs(){
	    return(this.args);
    }
	
    public String getJumpLabel(){
        return(jumpLabel);
    }
    
    public int getArgCount(){
        int argCount = args.size();
        return(argCount);
    }
    
}
