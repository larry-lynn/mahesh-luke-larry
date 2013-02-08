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
        
        // XXX I think we'll get to this
        //parse.SystemGoal();
        parse.StatementTail();
        

    } // end main()    
	
}
