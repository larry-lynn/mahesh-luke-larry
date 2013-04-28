Program func_by_val;
var X : integer;

FUNCTION double_it( a: integer ) : integer;
    BEGIN
        double_it := a * 2;
    END;

BEGIN
writeln('Program: func_by_val');
X := 64;
writeln(X);
X := double_it(X); 
writeln(X);


END
.
