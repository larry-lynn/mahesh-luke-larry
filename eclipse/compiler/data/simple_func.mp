Program simple_func;
var X : integer;

FUNCTION do_it : integer;
    BEGIN
        writeln( 2 );
        do_it := 5;
    END;

BEGIN
writeln('Simple function test - no arguments');
writeln('Expected results: 1, 2, 5');

writeln( 1 );
X := do_it;
writeln(X);

END
.
