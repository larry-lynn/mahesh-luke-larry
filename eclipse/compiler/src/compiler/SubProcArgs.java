package compiler;


// do we need this class?  When we indicate sub-proc-args, we need
// lexeme, type AND rev/val - which makes them just like Args
public class SubProcArgs {
    private String name;
    private ParserSymbol data_type;

    // Constructor when the name is known
    public SubProcArgs(String input) {
	this.name = input;
    }
    // Getter methods
    public String getName() {
	return this.name;
    }
    public ParserSymbol getType() {
	return this.data_type;
    }
    // Set method
    public void setName(String input) {
	this.name = input;
    }
    public void setType(ParserSymbol input) {
	this.data_type = input;
    }
}