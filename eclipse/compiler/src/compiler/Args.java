package compiler;

public class Args extends Symbol {
    
    public enum Call_Method{MP_SYMBOL_REFERENCE, MP_SYMBOL_VALUE}
    private Call_Method args_call_method;
    private ParserSymbol type;

    // Public constructor when one input is known, the args type
    public Args(ParserSymbol input) {
	super("", input);
	this.type = input;
    }
    public Call_Method getCall() {
	return this.args_call_method;
    }
    // Public set method
    public void setType(ParserSymbol input) {
	this.type = input;
    }
    public void setCall(Call_Method input) {
	this.args_call_method = input;
    }
}