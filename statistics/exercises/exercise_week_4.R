#Normal distribution

#1:
mean <- 110
sd <- 11
iqscores <- c(93,105,110,129)

plot(iqscores)

#https://en.wikipedia.org/wiki/Standard_score
#Find standard scores of IQ-scores
z <- (iqscores - mean) / sd
#-1.5454545
#-0.4545455
#0.0000000
#1.7272727

#Compute probabilities
pnorm(90, mean, sd) #Smaller or equal than 90
1 - pnorm(120, mean, sd) #Bigger than 120
pnorm(140, mean, sd) - pnorm(115, mean, sd) #Smaller than 115 and Smaller or equal then 140

#2:
pnorm(501.5, 502, 0.5) * 100 #Less then 501,5 grams
(1 - pnorm(502.4, 502, 0.5)) * 100 #More than 502,4 grams
(pnorm(502.4, 502, 0.5) - pnorm(501.5, 502, 0.5)) * 100 #Between 501,5 and 502,4 grams

#3:
pnorm(6, 5, 1) * 100 #84 percentage
(pnorm(5.8, 5, 1) - pnorm(5, 5, 1)) * 100 #28 percentage
(pnorm(5.9, 5, 1) - pnorm(5.2, 5, 1)) * 100 #23 percentage

#4:
pnorm(312, 450, 60)
#5:
1 - pnorm(20, 20.5, 0.2)
#6:
pnorm(4.5, 3.5, 1) - pnorm(2, 3.5, 1)
#7:
qnorm(0.1, 450, 50) #385,9 seconds
qnorm(0.95, 450, 50) #Between 358,9 and 532,2 seconds
#8:
pnorm(135, 110, 25) #Sarah, 29 years old
135 - 110 #25, from mean
pnorm(120, 90, 25) #Ann, 62 years old
120 - 90 #30, from mean.. Ann wins
#9:
qnorm(1 - 0.985, 12.36895, 0.17)

#Exponentional distribution

#1:
pexp(1.5, 1) #0.7768
1 - pexp(2, 1) #0.135
pexp(2.82, 1) - pexp(1.35, 1) #0.199

#2:
pexp(15/60, 12) #0.95
pexp(5/60, 12) #0.632
pexp(10/60, 12) - pexp(2/60, 12) #0.534
pexp(7.2/60, 12) - pexp(0.1/60, 12) #0.743
1 - pexp(7/60, 12) #0.246

#3:
pexp(5/60, 15)

#4:
pexp(16/8, 1/8) - pexp(12/8, 1/8)

#5:
1 - pexp(15, 3/60) #0.472
pexp(25, 3/60) #0.713
pexp(90, 3/60) - pexp(30, 3/60) #0.212
