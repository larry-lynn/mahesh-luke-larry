program casting_test_assign;
VAR num1: Integer; float1: Float;

BEGIN
  writeln('Program: Casting Test Assign');
  num1 := 5;
  float1 := 10.5;
  writeln('Trying to assign 5 to a float type');
  float1 := num1;
  write('Results are: ');
  writeln(float1);
  writeln('Trying to assign 10.5 to an int type');
  float1 := 10.5;
  num1 := float1;
  writeln(num1);
END.