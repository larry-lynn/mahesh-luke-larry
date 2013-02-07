package compiler;

import java.io.*;

public class mp {

    public static void main(String [] args) throws Exception{
    	
    	if( (args.length == 0) || (args.length > 1) ){
    		System.out.println("Usage: " + mp.class.getClass().getName() + "<source-code-file>");
    		System.exit(-3);
    	}
    	
        //String infile = "./data/test1.pas";
    	String infile = args[0];
        PrintWriter writeFileHandle = new PrintWriter(infile + ".tok");
        String outputLine;
    	
        compiler.Dispatcher disp;
        compiler.Token tok;

        
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        
        disp = new Dispatcher();
        disp.openFile(infile);
 
        
        
        while( true ){
            tok = disp.getToken();
            //System.out.println(tok.token_name + "\t" + tok.getLineNumber() + "\t" + tok.getColumnNumber() + "\t" + tok.getLexeme() );
            //System.out.format("%-20s%-7s%-7s%s\n", tok.token_name, tok.getLineNumber(), tok.getColumnNumber(), tok.getLexeme());
            //writeFileHandle.format("%-20s%-7s%-7s%s\n", tok.token_name, tok.getLineNumber(), tok.getColumnNumber(), tok.getLexeme());
            outputLine = String.format("%-20s%-7s%-7s%s\n", tok.token_name, tok.getLineNumber(), tok.getColumnNumber(), tok.getLexeme());
            // Write output to file to meet spec
            writeFileHandle.format("%s", outputLine);
            // Print to stdout for ease of debugging;
            System.out.print(outputLine);
            
            if(tok.token_name.toString() == "MP_EOF"){;
                break;
            }
        }
        
        writeFileHandle.close();


    } // end main()    
    
}
