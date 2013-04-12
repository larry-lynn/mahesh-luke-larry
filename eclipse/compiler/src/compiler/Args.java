package compiler;

public class Args extends SymbolWithType {
    
    private SymbolMode mode;
    //private SymbolType type;

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
    
    /*
    public Call_Method getCall() {
	    return this.args_call_method;
    }
    */
    
    public SymbolMode getMode(){
    	return(mode);	
    }
    
    public void setMode(SymbolMode sMode){
    	this.mode = sMode;
    }
    
    // Public set method
    // setType now inherited from parent class
    /*
    public void setType(SymbolType input) {
	    this.type = input;
    }
    */
    
}
