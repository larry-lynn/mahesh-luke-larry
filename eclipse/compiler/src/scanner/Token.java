package scanner;

public class Token {

    String token_name;
    int line_number;
    int column_number;
    String lexeme;

    public Token(String tok_nam, String lex){
        token_name = tok_nam;
        lexeme = lex;
    }
	
}
