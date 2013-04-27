Program PROC_by_val;
var X : integer;
Procedure p(a : Integer);
    BEGIN
        a := a + 1;
        writeln(a);
    END;

BEGIN
writeln('Program: PROC_by_val');
writeln('Expected results: 5, 6, 5');
X := 5;
writeln(X);
p(x);
writeln(X);



END
.
