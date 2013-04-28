Program RSA;
var MESSAGE, p, q, n, phi_n, e, d, a, i : integer;

FUNCTION gcd( a, b: integer ) : integer;
    {find the greatest commond denominator of 2 numbers}
    BEGIN
        if( b = 0 )
        then
           BEGIN
               gcd := a;
           END
        ELSE
            BEGIN
                gcd :=  gcd(b, (a mod b));
            END
    END;

BEGIN
MESSAGE := 42;
{p and q are chosen primes}
p := 23;
q := 13;
{n is a product of primes}
n := p * q;
{special case of Eulers totient when both numbers are prime}
phi_n := (p - 1) * (q - 1);

{calculate e, relatively prime to phi_n}
a := 3;
i := a;
while( i < phi_n) DO
BEGIN
    if( gcd( i, phi_n ) = 1)
    THEN
    BEGIN
        a := i;
        i := phi_n;
    END; {leaving off the ; here throws a VM memory error.  Find out why}
    i := i + 2;
END;
e := a;

write('original message is: ');
writeln(MESSAGE);
write('p = ');
writeln(p);
write('q = ');
writeln(q);
write('n = ');
writeln(n);
write('phi_n = ');
writeln(phi_n);
write('e = ');
writeln(e);



END
.
