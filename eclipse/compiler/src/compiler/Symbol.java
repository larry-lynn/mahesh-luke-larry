package compiler;

import java.util.*;

public class Symbol {

    String lexeme;
    int offset;
    SymbolKind kind;
    
    // Constructor
    public Symbol(String lex, SymbolKind symKind ){
        lexeme = lex;
	kind = symKind;
    }
    
    
    public String getLexeme(){
    	return(this.lexeme);
    }
    
    public SymbolKind getKind(){
    	return(this.kind);
    }

    // XXX I don't think this is right -- Larry
    public void setOffset(int lastOffset){
	offset = lastOffset + 1;
    }

    public int getOffset(){
	return offset;
    }
	
}
