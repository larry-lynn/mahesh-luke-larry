package compiler;

import java.util.*;

public class Symbol {

    String lexeme;
    int offset;
    ParserSymbol data_type;
    
    // Constructor
    public Symbol(String lex, ParserSymbol type ){
        lexeme = lex;
	data_type = type;
    }
    
    
    public String getLexeme(){
    	return(this.lexeme);
    }
    
    public Enum getDataType(){
    	return(this.data_type);
    }

    public void setOffset(int lastOffset){
	offset = lastOffset + 1;
    }

    public int getOffset(){
	return offset;
    }
	
}
