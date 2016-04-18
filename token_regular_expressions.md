# Tokens #
## Auxiliary Regular Expressions ##

```
    letter ::= a|b|c|...|z|A|B|C|...|Z
    digit  ::= 0|1|2|3|4|5|6|7|8|9
```

## Identifiers and Literals ##

```
    MP_IDENTIFIER          (letter | "_"(letter | digit)){["_"](letter | digit)}

    MP_INTEGER_LIT         digit{digit}


    MP_FIXED_LIT           digit{digit} "." digit{digit}  

    MP_FLOAT_LIT           (digit{digit} | digit{digit} "." digit{digit}) ("e"|"E")["+"|"-"]digit{digit}
      
    MP_STRING_LIT          "'" {"''" | AnyCharacterExceptApostropheOrEOL} "'"  
```


## Notes ##

> A comment in microPascal starts with a "{" and ends with a "}"