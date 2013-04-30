Program func_by_ref;
var X, Y : integer;

FUNCTION double_it(var a: integer ) : integer;
    BEGIN
        a := a * 2;
        double_it := a;
    END;

BEGIN
writeln('Program: func_by_ref');
X := 64;
writeln(X);
Y := double_it(X); 
writeln(X);
writeln(Y);


END
.
