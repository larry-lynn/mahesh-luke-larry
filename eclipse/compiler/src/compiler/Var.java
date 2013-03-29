package compiler;

public class Var extends Symbol {
    private SymbolType var_data_type;
       
       // Constructor if the input is known
       public Var(SymbolType input){
	   super("", SymbolKind.MP_SYMBOL_VAR);
       	   this.var_data_type = input;
       }
       
       // Constructor if name & type are known
       public Var(String lexeme, SymbolType input){
	   super(lexeme, SymbolKind.MP_SYMBOL_VAR);
       	   this.setType(input);
       }

       // Set the var_data_type
       public void setType(SymbolType input){
       	      this.var_data_type = input;
       }
}
