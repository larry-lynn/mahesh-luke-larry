package compiler;

public class Token {

    TokenType token_name;
    int line_number;
    int column_number;
    String lexeme;

    // Main constructor used by FSMs
    public Token(TokenType tok_nam, String lex){
        token_name = tok_nam;
        lexeme = lex;
    }
    
    // Alternate constructor; used when all inputs are known
    public Token(TokenType tok_nam, int row, int column, String lex){
        token_name = tok_nam;
        line_number = row;
        column_number = column;
        lexeme = lex;
    }
    
    public String getLexeme(){
    	return(this.lexeme);
    }
    
    public int getLineNumber(){
    	return(this.line_number);
    }
    
    public int getColumnNumber(){
        return(this.column_number);	
    }
	
}
