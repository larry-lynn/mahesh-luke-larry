package compiler;

import java.util.*;

public class Procedure extends Symbol{
    
    String jumpLabel;
    ArrayList<Args> args;

    // Constructor
    public Procedure(String lex, ArrayList<Args> argList, String newLabel){
        super(lex, SymbolKind.MP_SYMBOL_PROCEDURE);
        args = argList;
        jumpLabel = newLabel;
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
