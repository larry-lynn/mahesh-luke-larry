package compiler;

import java.util.*;

public class Procedure extends Symbol{

    /* XXX fixme - Larry tinkering
    List<SubProcArgs> args = new ArrayList<SubProcArgs>();

    // Constructor
    public Procedure(String lex, SubProcArgs[] arrayOfArgs){
	    super(lex, ParserSymbol.MP_SYMBOL_PROCEDURE);
	    args = Arrays.asList(arrayOfArgs);
    }
    
    public List getArgs(){
	    return(this.args);
    }
    end XXX */
    
    ArrayList<Args> args;

    // Constructor
    public Procedure(String lex, ArrayList<Args> argList){
        super(lex, ParserSymbol.MP_SYMBOL_PROCEDURE);
        args = argList;
    }
    
    public ArrayList getArgs(){
        return(this.args);
    }
	
}
