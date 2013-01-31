package scanner;

import java.lang.StringBuilder;

public class TokenMethods {
	
	//Variables that will be replaced
	public enum State {
		q0, q1, q2
	}
	char[] source_to_scan;
	int row;
	int column;
	int file_pointer;
	//q0 = normal scanning state
	//q1 = found the token/valid state
	//q2 = searching for additional parts in the ID call
	
	//Method:		Determine Identifier
	//Input: 		Takes the current pointer of the file 
	//Output:		Returns a Token containing the information
	//Procedure:	Determines whether or not the string obtained is a valid identifier following
	//				The syntax of (letter | "_"(letter | digit)) {["_"](letter | digit)}
	
	public Token MPIdentifierFSM()
	{
		//String built for the identifier
		StringBuilder id = new StringBuilder();
		//Token coin that will be returned
		Token coin = new Token("MP_Identifier",id.toString());
		//State object for the ID fsm
		State id_fsm = State.q0;
		//int peek to look ahead of the file pointer
		int peek = 0;
		
		
		//while checker for first part of the identifier
		while(id_fsm != State.q1)
		{
			char ch = source_to_scan[file_pointer + peek];
			//check to see if we have found a letter using ASCII value for A-Z,a-z at the start
			if( (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122))
			{
				id.append(ch);
				id_fsm = State.q1;
			}
			//check to see if we have more to the variable
			peek += 1;
			ch = source_to_scan[file_pointer + peek];
			if( (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) || (ch >= 48 && ch <= 57) || (ch == '_') )
			{
				id_fsm = State.q2;
				id.append(ch);
				//continue to scan the rest of the ID
				while(id_fsm != State.q1)
				{
					peek += 1;
					ch = source_to_scan[file_pointer + peek];
					//check to see if we have a letter A-Z,a-z or a digit 0-9
					if( (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) || (ch >= 48 && ch <= 57) )
					{
						//add the letter or digit to it
						id.append(ch);
					}
					else if(ch == '_')
					{
						//check to see if we have a letter or digit that is following the underscore
						peek += 1;
						ch = source_to_scan[file_pointer + peek];
						//check to see if we have a letter A-Z,a-z or a digit 0-9, or an underscore
						if( (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) || (ch >= 48 && ch <= 57) )
						{
							id.append('_');
							id.append(ch);
						}
						else
						{
							//we have an invalid variable here?
						}
					}
					//else we scanned something that is either the end or not what we want
					else
					{
						id_fsm = State.q1;
					}
				}
			}
		}
		
		//update the coin with information
		coin.column_number = column + peek;
		coin.line_number = row;
		
		//update column & file pointer
		column += peek;
		file_pointer += peek;
		
		return coin;
	}
	
	//Method: 		Determine Period
	//Input:  		Takes the current location of file pointer 
	//Output: 		Returns a Token containing the information
	//Procedure:	Determines whether or not the token is a period?
	public Token MPPeriodFSM()
	{
		//Token coin that will be returned
		Token coin = new Token("MP_Period",".");
		//State object to determine the "."
		State p_fsm = State.q0;
		//int peek to look ahead of the file pointer
		int peek = 0;
		
		//While check
		while(p_fsm != State.q1)
		{
			switch(source_to_scan[file_pointer + peek])
			{
				case '.':
					p_fsm = State.q1;
					break;
				case ' ':
					peek+=1;
					break;
				default:
					System.exit(-1);
					
			}
		}
		
		//update the coin with information
		coin.column_number = column + peek;
		coin.line_number = row;
		
		//update column & file pointer
		column += peek;
		file_pointer += peek;
		
		return coin;
	}
	
	//Method:		Determine Left Paren
	//Input:		Takes the current location of the file pointer
	//Output: 		Returns a Token containing the information
	//Procedure:	Determines whether the current pointer of the file is looking at a "("
	public Token MPLeftParenFSM()
	{
		//Token coin that will be returned with the information
		Token coin = new Token("MP_Left_Paren","(");
		//State object for the left paren
		State lp_fsm = State.q0;
		//Int peek to look ahead of the file pointer
		int peek = 0;
		
		//While checker
		while(lp_fsm != State.q1)
		{
			switch(source_to_scan[file_pointer + peek])
			{
				case '(':
					lp_fsm = State.q1;
					break;
				case ' ':
					peek += 1;
					break;
				default:
					System.exit(-1);
			}
		}
		
		//update coin information
		coin.column_number = column + peek;
		coin.line_number = row;
		
		//update the column and file_pointer information
		column += peek;
		file_pointer += peek;
		
		return coin;
	}
	
	//Method:		Determine Right Paren
	//Input: 		Takes the current location of the file pointer
	//Output:		Returns a Token containing the information
	//Procedure:	Determines whether the current pointer of the file is looking at a ")"
	public Token MPRightParenFSM()
	{
		//Token coin that will be returned with the information
		Token coin = new Token("MP_Right_Paren",")");
		//State object to determine the state
		State rp_fsm = State.q0;
		//Int object peek to look ahead of the file pointer
		int peek = 0;
		
		while(rp_fsm != State.q1)
		{
			switch(source_to_scan[file_pointer + peek])
			{
				case ')':
					rp_fsm = State.q1;
					break;
				case ' ':
					peek += 1;
					break;
				default:
					System.exit(-1);
			}
		}
		
		//update coin information
		coin.column_number = column + peek;
		coin.line_number = row;
		
		//update the file_pointer & column
		file_pointer += peek;
		column += peek;
		return coin;
	}
	
	//Method:		Determine Times
	//Input:		Takes the current location of the file pointer
	//Output:		Returns a Token containing the information
	//Procedure:	Determines whether the current pointer of the file is looking at a "*"
	public Token MPTimesFSM()
	{
		//Token coin containing the information
		Token coin = new Token("MP_Times","*");
		//State object for the times fsm
		State t_fsm = State.q0;
		//Int object peek to look ahead of the file pointer
		int peek = 0;
		
		//while checker
		while(t_fsm != State.q1)
		{
			switch(source_to_scan[file_pointer + peek])
			{
				case '*':
					t_fsm = State.q1;
					break;
				case ' ':
					peek += 1;
					break;
				default:
					System.exit(-1);
			}
		}
		
		//update the coin information
		coin.column_number = column + peek;
		coin.line_number = row;
		//update the file_pointer & column
		column += peek;
		file_pointer += peek;
		return coin;
	}
	
	

}
