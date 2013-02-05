package scanner;

// research on simple hash functions foundhere
// http://www.cs.hmc.edu/~geoff/classes/hmc.cs070.200101/homework10/hashfuncs.html
public class ReservedWords {
	public static final int a_prime = 137;
	
	public static final String [][] preComputedTable = {
		{"", "FALSE"}, 					// index 0 
		{"read", "MP_READ"}, 			// index 1 
		{"", "FALSE"}, 					// index 2 
		{"", "FALSE"}, 					// index 3 
		{"", "FALSE"}, 					// index 4 
		{"", "FALSE"}, 					// index 5 
		{"", "FALSE"}, 					// index 6 
		{"write", "MP_WRITE"}, 			// index 7 
		{"until", "MP_UNTIL"}, 			// index 8 
		{"", "FALSE"}, 					// index 9 
		{"procedure", "MP_PROCEDURE"}, 	// index 10 
		{"", "FALSE"}, 					// index 11 
		{"", "FALSE"}, 					// index 12 
		{"", "FALSE"}, 					// index 13 
		{"else", "MP_ELSE"}, 			// index 14 
		{"", "FALSE"}, 					// index 15 
		{"", "FALSE"}, // index 16 
		{"", "FALSE"}, // index 17 
		{"", "FALSE"}, // index 18 
		{"", "FALSE"}, // index 19 
		{"then", "MP_THEN"}, // index 20 
		{"", "FALSE"}, // index 21 
		{"", "FALSE"}, // index 22 
		{"", "FALSE"}, // index 23 
		{"", "FALSE"}, // index 24 
		{"", "FALSE"}, // index 25 
		{"", "FALSE"}, // index 26 
		{"", "FALSE"}, // index 27 
		{"", "FALSE"}, // index 28 
		{"", "FALSE"}, // index 29 
		{"", "FALSE"}, // index 30 
		{"", "FALSE"}, // index 31 
		{"", "FALSE"}, // index 32 
		{"and", "MP_AND"}, // index 33 
		{"", "FALSE"}, // index 34 
		{"", "FALSE"}, // index 35 
		{"", "FALSE"}, // index 36 
		{"end", "MP_END"}, // index 37 
		{"", "FALSE"}, // index 38 
		{"", "FALSE"}, // index 39 
		{"", "FALSE"}, // index 40 
		{"", "FALSE"}, // index 41 
		{"", "FALSE"}, // index 42 
		{"", "FALSE"}, // index 43 
		{"", "FALSE"}, // index 44 
		{"", "FALSE"}, // index 45 
		{"mod", "MP_MOD"}, // index 46 
		{"", "FALSE"}, // index 47 
		{"function", "MP_FUNCTION"}, // index 48 
		{"div", "MP_DIV"}, // index 49 
		{"", "FALSE"}, // index 50 
		{"", "FALSE"}, // index 51 
		{"", "FALSE"}, // index 52 
		{"for", "MP_FOR"}, // index 53 
		{"", "FALSE"}, // index 54 
		{"var", "MP_VAR"}, // index 55 
		{"", "FALSE"}, // index 56 
		{"", "FALSE"}, // index 57 
		{"", "FALSE"}, // index 58 
		{"", "FALSE"}, // index 59 
		{"", "FALSE"}, // index 60 
		{"", "FALSE"}, // index 61 
		{"", "FALSE"}, // index 62 
		{"not", "MP_NOT"}, // index 63 
		{"", "FALSE"}, // index 64 
		{"integer", "MP_INTEGER"}, // index 65 
		{"", "FALSE"}, // index 66 
		{"", "FALSE"}, // index 67 
		{"", "FALSE"}, // index 68 
		{"", "FALSE"}, // index 69 
		{"if", "MP_IF"}, // index 70 
		{"", "FALSE"}, // index 71 
		{"", "FALSE"}, // index 72 
		{"", "FALSE"}, // index 73 
		{"do", "MP_DO"}, // index 74 
		{"program", "MP_PROGRAM"}, // index 75 
		{"", "FALSE"}, // index 76 
		{"", "FALSE"}, // index 77 
		{"", "FALSE"}, // index 78 
		{"", "FALSE"}, // index 79 
		{"", "FALSE"}, // index 80 
		{"", "FALSE"}, // index 81 
		{"", "FALSE"}, // index 82 
		{"", "FALSE"}, // index 83 
		{"", "FALSE"}, // index 84 
		{"", "FALSE"}, // index 85 
		{"", "FALSE"}, // index 86 
		{"", "FALSE"}, // index 87 
		{"or", "MP_OR"}, // index 88 
		{"", "FALSE"}, // index 89 
		{"to", "MP_TO"}, // index 90 
		{"", "FALSE"}, // index 91 
		{"", "FALSE"}, // index 92 
		{"repeat", "MP_REPEAT"}, // index 93 
		{"", "FALSE"}, // index 94 
		{"", "FALSE"}, // index 95 
		{"", "FALSE"}, // index 96 
		{"", "FALSE"}, // index 97 
		{"", "FALSE"}, // index 98 
		{"", "FALSE"}, // index 99 
		{"", "FALSE"}, // index 100 
		{"", "FALSE"}, // index 101 
		{"", "FALSE"}, // index 102 
		{"", "FALSE"}, // index 103 
		{"", "FALSE"}, // index 104 
		{"", "FALSE"}, // index 105 
		{"begin", "MP_BEGIN"}, // index 106 
		{"", "FALSE"}, // index 107 
		{"", "FALSE"}, // index 108 
		{"", "FALSE"}, // index 109 
		{"", "FALSE"}, // index 110 
		{"", "FALSE"}, // index 111 
		{"", "FALSE"}, // index 112 
		{"", "FALSE"}, // index 113 
		{"", "FALSE"}, // index 114 
		{"", "FALSE"}, // index 115 
		{"", "FALSE"}, // index 116 
		{"fixed", "MP_FIXED"}, // index 117 
		{"", "FALSE"}, // index 118 
		{"downto", "MP_DOWNTO"}, // index 119 
		{"", "FALSE"}, // index 120 
		{"", "FALSE"}, // index 121 
		{"", "FALSE"}, // index 122 
		{"float", "MP_FLOAT"}, // index 123 
		{"", "FALSE"}, // index 124 
		{"", "FALSE"}, // index 125 
		{"while", "MP_WHILE"}, // index 126 
		{"", "FALSE"}, // index 127 
		{"", "FALSE"}, // index 128 
		{"", "FALSE"}, // index 129 
		{"", "FALSE"}, // index 130 
		{"", "FALSE"}, // index 131 
		{"", "FALSE"}, // index 132 
		{"", "FALSE"}, // index 133 
		{"", "FALSE"}, // index 134 
		{"", "FALSE"}, // index 135 
		{"", "FALSE"} // index 136 
	};
	
	public static int checkSum(String input){
		int sum = 0;
		int i;
		for(i=0; i< input.length(); ++i){
			sum = sum + input.charAt(i);
		}
		return(sum);	
	}

	public static int simpleHash(String input){
        int cs = checkSum(input);
		int hash_val = (cs % a_prime);
		return(hash_val);
	}
	
	public static String checkReserved(String input){
		int hashVal = simpleHash(input.toLowerCase());
		String output = "FALSE";
		// filter step
		String possibleMatch = preComputedTable[hashVal][0];
		// refine step - resolve possible collisions
		if(possibleMatch.length() > 0 ){
			if(input.toLowerCase().equals(possibleMatch)){
				output = preComputedTable[hashVal][1];
			}
		}
		return(output);
	}
	
}  // end class

