package compiler;

public class ParserDriver {

	public static void main(String[] args) throws Exception{
    	if( (args.length == 0) || (args.length > 1) ){
    		System.out.println("Usage: java compiler.ParserDriver <source-code-file>");
    		System.exit(-3);
    	}
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        
        //String infile = "./data/test1.pas";
    	String infile = args[0];
    	
        Parser parse;
        parse = new Parser(infile);
        
        switch(parse.lookahead.token_name){
        // Test 1 -- use parse_input1.mp
        case MP_SCOLON:
        	parse.StatementTail();
        	break;
        // Test 3 -- use parse_input2.mp
        /* not needed anymore - test1 hits ReadParameter Tail now
        case MP_COMMA:
        	parse.ReadParameterTail();
        	break;
        */
        // Test 4 -- use parse_input3.mp
        case MP_WRITE:
        	parse.WriteStatement();
        	break;

        // Test 5 -- use parse_input4.mp
        /*
        case MP_COMMA:
    	    parse.WriteParameterTail();
    	    break;
        */
        // Test 6 -- use parse_input5.mp
        case MP_IDENTIFIER:
	        parse.AssignmentStatement();
	        break;
        // Test 7 -- use parse_input6.mp
        case MP_IF:
        	parse.IfStatement();
        	break;    
        // Test 8 - use parse_input7.mp
        case MP_ELSE:
        	parse.OptionalElsePart("DummyString");
        	break;
        // Test 9 - use parse_input8.mp
        case MP_WHILE:
        	parse.WhileStatement();
        	break;
        // Test 10 - use parse_input9.mp
        case MP_FOR:
            parse.ForStatement();
            break;
        // Test 11 - use parse_input10.mp   
        case MP_PLUS:
        	parse.InitialValue();
        	break;
        // Test 12 - use parse_input11.mp
        case MP_DOWNTO:
            parse.StepValue();
            parse.FinalValue();
            break;
        // Test 13 - use parse_input5.mp -- again
        /*
        case MP_IDENTIFIER:
        	parse.ProcedureStatement();
        	break;
        */
        // Test 14 - use parse_input12.mp
        case MP_LPAREN:
        	parse.OptionalActualParameterList();
        	break;
        default:
        	System.out.println("More sleeping chickens");
        	break;
        }
        // XXX I think we'll get to this
        //parse.SystemGoal();
        
        // Test 1 - use parse_input1.mp
        //parse.StatementTail();
        
        // Test 2 - use parse_input1.mp -- again
        //parse.EmptyStatement();
        
        // Test 3 - use parse_input2.mp
        //parse.ReadParameterTail();
        
        // Test 4 - use parse_input3.mp
        // parse.WriteStatement();
        
        // Test 5 - use parse_input4.mp
        // parse.WriteParameterTail();
        
        // Test 6 - use parse_input5.mp
        //parse.AssignmentStatement();
        
        // Test 7 - use parse_input6.mp
        // parse.IfStatement();
        
        // Test 8 - use parse_input7.mp
        //parse.OptionalElsePart();
        
        // Test 9 - use parse_input8.mp
        //parse.WhileStatement();
        
        // Test 10 - use parse_input9.mp
        // parse.ForStatement();
        
        // Test 11 - use parse_input10.mp
        //parse.InitialValue();
        
        // Test 12 - use parse_input11.mp
        //parse.StepValue();
        //parse.FinalValue();
        
        // Test 13 - use parse_input5.mp -- again
        //parse.ProcedureStatement();
        
        // Test 14 - use parse_input12.mp
        //parse.OptionalActualParameterList();

    } // end main()    
	
}
