program casting_test_lessEqual:
  VAR intnumber : Integer;
floatnumber: Float;

BEGIN
  intnumber := 500;
  floatnumber := 500.50;
  writeln('Output should be False');
  writeln( floatnumber <= intnumber);
  writeln('Output should be True');
  writeln( intnumber <= floatnumber );
  writeln('Output should be False');
  writeln( floatnumber <= 500.40 );
  writeln('Output should be True');
  writeln( floatnumber <= 500.5 );
  writeln('Output should be True');
  writeln( intnumber <= 500 );
END
.