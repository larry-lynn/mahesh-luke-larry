import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;

// mp will be the driver that drives the dispatcher
class mp{
  
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
    }


    public static void main(String [] args) throws Exception{
        String infile = "test1.pas";
        String file_as_string;
        char[] file_as_array;
        int i;
        dispatcher disp;
        token tok;

        try{
            file_as_string = readFile(infile);
        }
        catch (IOException e){
            throw e;
        }

        file_as_array = file_as_string.toCharArray();

        disp = new dispatcher(file_as_array);

        /*
        for(i=0; i < file_as_array.length ; ++i){
            System.out.println(file_as_array[i]);
        }
        */

        /*
        tok = disp.getToken();

        System.out.println(tok.token_name + "\t" + tok.line_number + "\t" + tok.column_number + "\t" + tok.lexeme);
        */
        while( true ){
            tok = disp.getToken();
            if(tok == null){
                break;
            }
            System.out.println(tok.token_name + "\t" + tok.line_number + "\t" + tok.column_number + "\t" + tok.lexeme);
        }


    }

}