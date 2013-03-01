package compiler;

public class SubProcArgs {
    private String name;
    private enum data_type;

    public SubProcArgs() {
	
    }
    
    // Constructor when the name is known
    public SubProcArgs(String input) {
	this.name = input;
    }

    // Constructor when the both input is known
    public SubProcArgs(String input, enum input2) {
	this.name = input;
	this.data_type = input2;
    }

    // Getter methods
    public String getName() {
	return this.name;
    }
    public enum getType() {
	return this.data_type;
    }
    // Set method
    public void setName(String input) {
	this.name = input;
    }
    public void setType(enum input) {
	this.data_type = input;
    }
}