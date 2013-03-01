package compiler;

import java.util.*;

public class Symbol {

    public enum Data_type {
	variable_identifier, arguments, function_identifier, procedure_identifier
    }

    String lexeme;
    int offset;
    Data_type data_type;
    
    // Constructor
    public Symbol(String lex, String type ){
        lexeme = lex;

	switch(type){
	case "variable_identifier":
	    data_type = Data_type.variable_identifier;
	    break;

	case "arguments":
	    data_type = Data_type.arguments;
	    break;

	case "function_identifier":
	    data_type = Data_type.function_identifier;
	    break;

	case "procedure_identifier":
	    data_type = Data_type.procedure_identifier;
	    break;	 
	}

    }
    
    
    public String getLexeme(){
    	return(this.lexeme);
    }
    
    public Enum getDataType(){
    	return(this.data_type);
    }
	
}
