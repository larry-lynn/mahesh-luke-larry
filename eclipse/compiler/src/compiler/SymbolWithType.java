package compiler;

public class SymbolWithType extends Symbol{
	SymbolType dataType;
	
	public SymbolWithType(){
		super("");
	}
	
	public SymbolWithType(SymbolType sType){
		super("");
		dataType = sType;
	}
	
	public SymbolWithType(String lexeme, SymbolType sType){
		super(lexeme);
		dataType = sType;
	}
	
    
    // Set the var_data_type
    public void setType(SymbolType input){
    	      this.dataType = input;
    }
    
    public SymbolType getType(){
    	return(this.dataType);
    }

}
