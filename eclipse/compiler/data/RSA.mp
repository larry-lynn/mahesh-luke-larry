Program RSA;
var MESSAGE, CYPHERTEXT, CLEARTEXT, p, q, n, phi_n, e, d, a, i : integer;

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

FUNCTION exponent( base, power: integer) : INTEGER;
    BEGIN
        if( power < 2 )
        then
            exponent := base
         else
             exponent := base * exponent(base, power - 1)
    END;

FUNCTION modular_exponentiation(base, power, modulus: integer) : integer;
    BEGIN
        if( power < 2 )
        then
            modular_exponentiation := base mod modulus
        else
            modular_exponentiation := ( (base * modular_exponentiation( base, power - 1, modulus)) mod modulus)
    END;

{main program begins here}
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

{calculate d, multiplicative inverse modulo of e}
i := 0;
d := 0;
while( i <> 1 ) DO
BEGIN
    d := d + 1;
    i := (d * e) mod phi_n; 
END;

writeln('RSA CRYPTOSYSTEM');
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
write('d = ');
writeln(d);
writeln('Encrypting message  . . .');
CYPHERTEXT := ( (exponent(MESSAGE, e)) mod n);
write('encrypted message is ');
writeln(CYPHERTEXT);
writeln('Decrypting message  . . .');
{ must use modular exponentiation here - straight exponent overflows the value of the largest integer}
CLEARTEXT := modular_exponentiation(CYPHERTEXT, d, n);
write('decrypted message is ');
writeln(CLEARTEXT);

END
.
