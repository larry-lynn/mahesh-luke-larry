all:
	flex uscan.l
	mv lex.yy.c uscan.c
	g++ -o execute *.c -ll
clean:
	rm uscan.c
	rm execute