package scanner;

public class mp {

    public static void main(String [] args) throws Exception{
        String infile = "../../data/test1.pas";

        scanner.Dispatcher disp;
        scanner.Token tok;

        
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        
        disp = new Dispatcher();
        disp.openFile(infile);
 
        while( true ){
            tok = disp.getToken();
            if(tok == null){
                break;
            }
            System.out.println(tok.token_name + "\t" + tok.line_number + "\t" + tok.column_number + "\t" + tok.lexeme);
        }


    } // end main()    
    
}
