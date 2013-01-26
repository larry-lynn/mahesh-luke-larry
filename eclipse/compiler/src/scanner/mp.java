package scanner;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;

public class mp {
	
    private static String readFile(String path) throws IOException {
    	/*copied from
              http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file */
    	FileInputStream stream = new FileInputStream(new File(path));
    	try {
    	    FileChannel fc = stream.getChannel();
    	    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
    	    /* Instead of using default, pass in a decoder. */
    	    return Charset.defaultCharset().decode(bb).toString();
    	}
    	finally {
    	    stream.close();
    	}
    }  //end readFile()

    public static void main(String [] args) throws Exception{
        String infile = "../../data/test1.pas";
        String file_as_string;
        char[] file_as_array;
        scanner.Dispatcher disp;
        scanner.Token tok;

        
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        
        
        
        try{
            file_as_string = readFile(infile);
        }
        catch (IOException e){
            throw e;
        }

        file_as_array = file_as_string.toCharArray();

        disp = new Dispatcher(file_as_array);

        
        while( true ){
            tok = disp.getToken();
            if(tok == null){
                break;
            }
            System.out.println(tok.token_name + "\t" + tok.line_number + "\t" + tok.column_number + "\t" + tok.lexeme);
        }


    } // end main()    
    
}
