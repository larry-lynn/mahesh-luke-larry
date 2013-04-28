program test_assign_fail2;
VAR myString := String;

BEGIN
  writeln('Program Test Assign Fail 2');
  myString := 'This string';
  writeln('Trying to assign a bool value to a string');
myString := True;
END.