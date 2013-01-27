package scanner;

public class Token {

    String token_name;
    int line_number;
    int column_number;
    String lexeme;

    // Main constructor used by FSMs
    public Token(String tok_nam, String lex){
        token_name = tok_nam;
        lexeme = lex;
    }
    
    // Alternate constructor; used when all inputs are known
    public Token(String tok_nam, int row, int column, String lex){
        token_name = tok_nam;
        line_number = row;
        column_number = column;
        lexeme = lex;
    }
	
}
