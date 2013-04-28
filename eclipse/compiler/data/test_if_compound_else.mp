program test_if_compound_else;
VAR num1, num2 : Integer; bool1,bool2 : Boolean;

BEGIN
  bool1 := True;
  bool2 := True;
  writeln('Program: Test If Compound Else');
  if(bool1 <>  bool2)
    writeln('In the IF portion');
    BEGIN
      num1 := 50;
    END
      BEGIN
      num2 := 100;
    END
    write('The value of num1: ');
    writeln(num1);
    write('The value of num2: ');
    writeln(num2);
  else
    writeln('In the ELSE portion');
    num1 := 100;
    num2 := 50
    write('The value of num1: ');
    writeln(num1);
    write('The value of num2: ');
    writeln(num2);
END.