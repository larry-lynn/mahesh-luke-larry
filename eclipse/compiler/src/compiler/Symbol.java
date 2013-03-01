package compiler;

public class Symbol {
    String lexeme;
    
    public Symbol(String name){
        lexeme = name;
    }

    public String getLexeme(){
        return lexeme;
    }

}
