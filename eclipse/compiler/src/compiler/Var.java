package compiler;

public class Var extends SymbolWithType {
    //private SymbolType var_data_type;
       
       // Constructor if the input is known
       public Var(SymbolType sType){
 	       super("", sType);
           this.kind = SymbolKind.MP_SYMBOL_VAR;
        }
	   /*
       public Var(SymbolType input){
	   super("", SymbolKind.MP_SYMBOL_VAR);
       	   this.var_data_type = input;
       }
       */
       
       // Constructor if name & type are known
       public Var(String lexeme, SymbolType sType){
	       super(lexeme, sType);
	       this.kind = SymbolKind.MP_SYMBOL_VAR;
       }
       /*
       // Constructor if name & type are known
       public Var(String lexeme, SymbolType input){
	   super(lexeme, SymbolKind.MP_SYMBOL_VAR);
       	   this.setType(input);
       }
       */
       
       // getType & setType inherited from SymbolWithType

       // Set the var_data_type - now inherited from SymbolWithType
       /*
       public void setType(SymbolType input){
       	      this.var_data_type = input;
       }
       */
}
