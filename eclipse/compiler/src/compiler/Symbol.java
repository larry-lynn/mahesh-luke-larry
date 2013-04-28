package compiler;

public class Symbol {

    String lexeme;
    String offset;
    SymbolKind kind;
    
    // Constructor
    public Symbol(String lex){
    	lexeme = lex;
    }
    
    
    public Symbol(String lex, SymbolKind symKind ){
        lexeme = lex;
        kind = symKind;
    }
    
    // Constructor
    public Symbol(String lex, SymbolKind symKind, SymbolType symType ){
        lexeme = lex;
        kind = symKind;
    }
     
    public String getLexeme(){
    	return(this.lexeme);
    }
    
    public SymbolKind getKind(){
    	return(this.kind);
    }
    
    public String getOffset(){
        return offset;
    }
	
}
