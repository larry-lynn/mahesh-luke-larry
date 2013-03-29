package compiler;

public class Args extends Symbol {
    
    public enum Call_Method{MP_SYMBOL_REFERENCE, MP_SYMBOL_VALUE}
    private Call_Method args_call_method;
    private SymbolType type;

    // Public constructor when one input is known, the args type
    public Args(SymbolType input) {
	    super("", SymbolKind.MP_SYMBOL_PARAMETER);
	    this.type = input;
    }
    // Constructor if name & type are known
    public Args(String lexeme, SymbolType input){
    super(lexeme , SymbolKind.MP_SYMBOL_PARAMETER);
        this.setType(input);
    }
    // Constructor if name, type & call method are known
    public Args(String lexeme, SymbolType input, Call_Method passType){
    super(lexeme , SymbolKind.MP_SYMBOL_PARAMETER);
        this.setType(input);
        this.setCall(passType);
    }
    
    public Call_Method getCall() {
	    return this.args_call_method;
    }
    // Public set method
    public void setType(SymbolType input) {
	    this.type = input;
    }
    public void setCall(Call_Method input) {
	    this.args_call_method = input;
    }
}
