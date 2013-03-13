package compiler;

public class Var extends Symbol {
    private ParserSymbol var_data_type;
       
       // Constructor if the input is known
       public Var(ParserSymbol input){
	   super("",input);
       	   this.var_data_type = input;
       }
       
       // Constructor if name & type are known
       public Var(String lexeme, ParserSymbol input){
	   super(lexeme ,input);
       	   this.setType(input);
       }

       // Set the var_data_type
       public void setType(ParserSymbol input){
       	      this.var_data_type = input;
       }
}