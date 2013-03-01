package compiler;

public class Args extends Symbol {
    private enum args_data_type;
    private enum call_method;
    

    // Public constructor
    public Args() {
	
    }
    // Public constructor when one input is known, the args type
    public Args(enum input) {
	this.args_data_type = input;
    }
    // Public constructor when all input is known
    public Args(enum type, enum caller) {
	this.args_data_type = type;
	this.call_method = caller;
    }
    // Public get methods
    public enum getType() {
	return this.args_data_type;
    }
    public enum getCall() {
	return this.call_method;
    }
    // Public set method
    public void setType(enum input) {
	this.args_data_type = input;
    }
    public void setCall(enum input) {
	this.call_method = input;
    }
}