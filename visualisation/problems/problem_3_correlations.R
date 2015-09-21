#ds <- read.table("wk3_correlations.txt", fill=T,head=T)

#Use delim for the use of "," in numbers
ds <- read.delim("wk3_correlations.txt")
#Shows following correlation;
#linear correlation: afstand, gewicht + bonus, maandloon
plot(ds)

plot(ds$Afstand..m., ds$Gewicht..kg., type="o")

#Linear correlation
plot(2*1:10+1)

#Exponential correlation
plot ( 1:10 * 2 ^ 0:10 )
plot ( 1:10 * (2 ^ 0:10) )
plot ( 1:10 * (2 ^ 0:10) * (2 ^ 0:10) )

#2
x = -10:10
y = (1.5 ^ 1.5) ^ x
plot(x, y)

#3: You want 10, step of 5
x = 1:10 
y = log(x,2)
plot(x,y)

