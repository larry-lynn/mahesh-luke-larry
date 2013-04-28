package compiler;

import java.util.ArrayList;

public class Args extends SymbolWithType {
    
    private SymbolMode mode;
    int positionInCallSignature;

    // Public constructor when one input is known, the args type
    public Args(SymbolType sType) {
	    //super("", SymbolKind.MP_SYMBOL_PARAMETER);
    	super("", sType);
	    this.kind = SymbolKind.MP_SYMBOL_PARAMETER;
    }
    // Constructor if name & type are known
    public Args(String lexeme, SymbolType sType){
        super(lexeme , sType);
        this.kind = SymbolKind.MP_SYMBOL_PARAMETER;
    }
    // Constructor if name, type & call method are known
    public Args(String lexeme, SymbolType sType, SymbolMode sMode){
    	super(lexeme , sType);
    	this.kind = SymbolKind.MP_SYMBOL_PARAMETER;
        this.mode = sMode;
    }

    public Args(String lexeme, SymbolType sType, SymbolMode sMode, int position){
    	super(lexeme , sType);
    	this.kind = SymbolKind.MP_SYMBOL_PARAMETER;
        this.mode = sMode;
        this.positionInCallSignature = position;
    }
    
    public void setPosition(int position){
        this.positionInCallSignature = position;        
    }

    public int getPosition(){
        return(positionInCallSignature);
    }

    // helper function to ge used when loading data into local
    // memory for paramters.  Kind of a hack.  Consider refactoring
    public static Args getArgAtPosition(ArrayList<Symbol> topTableAsList, int position){
        Args a;
        for(Symbol s : topTableAsList){
            if(s.getKind() == SymbolKind.MP_SYMBOL_PARAMETER){
                a = (Args) s;
                if(a.getPosition() == position){
                    return(a);
                }
            }
        }
        return(null);
    }

    public SymbolMode getMode(){
    	return(mode);	
    }
    
    public void setMode(SymbolMode sMode){
    	this.mode = sMode;
    }
    
    public static String formatArglist(Symbol payload){
        ArrayList<Args> argList;
        StringBuilder argListStringB = null;
        Procedure proc = null;
        Function func = null;
        Boolean first = true;
        argList = null;
        
        switch(payload.getKind()){
        case MP_SYMBOL_PROCEDURE:
            proc = (Procedure) payload;
            argList = proc.getArgs();
            break;
        case MP_SYMBOL_FUNCTION:
            func = (Function) payload;
            argList = func.getArgs();
            break;
        default:
            System.out.println("case in formatArglist should not be reachable.  Error!");
            System.exit(-9);
        }

        argListStringB = new StringBuilder();
        //proc = (Procedure) payload;
        argListStringB.append("(");
        for(Args a : argList ){
            if(first){first = false;}
            else{argListStringB.append(", ");}
            argListStringB.append("[");
            argListStringB.append(a.getLexeme());
            argListStringB.append(":");
            switch(a.getType()){
            case MP_SYMBOL_INTEGER:
                argListStringB.append("int");
                break;
            case MP_SYMBOL_BOOLEAN:
                argListStringB.append("bool");
                break;
            case MP_SYMBOL_FLOAT:
                argListStringB.append("flt");
                break;
            case MP_SYMBOL_STRING:
                argListStringB.append("str");
                break;
            default:
                System.out.println("code in arglist formatting that should be unreachable.  Error!");
                System.exit(-10);
            }
            argListStringB.append(":");
            switch(a.getMode() ){
            case MP_SYMBOL_VALUE:
                argListStringB.append("val");
                break;
            case MP_SYMBOL_REFERENCE:
                argListStringB.append("ref");
                break;
            default:
                System.out.println("code in arglist formatting that should be unreachable.  Error!");
                System.exit(-10);              
            }
            argListStringB.append("]");
            
        }
        argListStringB.append(")");
        return(argListStringB.toString());
    }

    
}
