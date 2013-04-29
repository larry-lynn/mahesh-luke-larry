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
	
    // XXX wrong way to do it - register & offset relative to register should
    // have been stored separately - too late now to change representation
    public int getPartialNumericAddress(){
        int parenPos;
        String prefix;
        int address;
        parenPos = offset.indexOf('(');
        prefix = offset.substring(0,parenPos);
        address = Integer.parseInt(prefix);
        System.out.println(address);
        return(address);
    }
    
    public String getRegister(){
        int parenPos;
        String register;
        parenPos = offset.indexOf('(');
        register = offset.substring(parenPos + 1, parenPos + 3);
        System.out.println(register);
        return(register);
    }
    
}
