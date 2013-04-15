package compiler;

public class SymbolDriver {
    public static void main(String args[]){
        SymbolTable table = new SymbolTable("test", 0);
        Symbol sym1 = new Symbol("some_name_here", SymbolKind.MP_SYMBOL_VAR);
        Symbol sym2 = new Symbol("aaa_I_should_go_first", SymbolKind.MP_SYMBOL_VAR);
        Symbol sym3 = new Symbol("zzz_I_should_go_last", SymbolKind.MP_SYMBOL_VAR);

        table.insert(sym1);
        table.insert(sym2);
        table.insert(sym3);
  
        table.dump();

        System.out.println(table.lookup("zzz_I_should_go_last"));

        System.out.println(table.lookup("I don't exist"));

    }

	
}
