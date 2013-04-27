Program PROC_basic_recursion;
var X : integer;
Procedure p(a : Integer);
    BEGIN
        if( a < 10) THEN
            BEGIN
            writeln(a);
            p(a + 1);
            END;
    END;

BEGIN
writeln('Program: PROC_basic_recursion');

X := 5;

p(x);

END
.
