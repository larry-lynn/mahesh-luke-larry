Program PROC_by_ref;
var X : integer;
Procedure p(var a : Integer);
    BEGIN
        a := a + 1;
        writeln(a);
    END;

BEGIN
writeln('Program: PROC_by_ref');
writeln('Expected results: 5, 6, 6');
X := 5;
writeln(X);
p(x);
writeln(X);

END
.
