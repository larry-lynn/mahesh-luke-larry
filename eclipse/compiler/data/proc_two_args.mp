Program PROC_two_args;
var X, Y : integer;
Procedure p(a,b : Integer);
    BEGIN
        writeln(a + 100);
        writeln(b + 100);
    END;

BEGIN
writeln('Program: PROC_two_args');
writeln('Expected results: 10,20,120,110,10,20');

X := 10;
Y := 20;
writeln(X);
writeln(Y);
p(X,Y);
writeln(X);
writeln(Y);



END
.
