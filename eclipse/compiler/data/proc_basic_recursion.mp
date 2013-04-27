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
writeln('Expected results: 5, 6, 7, 8, 9');

X := 5;

p(x);

END
.
