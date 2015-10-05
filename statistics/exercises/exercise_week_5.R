#Parameters estimation and confidence intervals

#1: SD of population is known; 2.8..
sd <- as.double(2.8)
n <- 9
m <- c(8, 9, 10, 13, 14, 16, 17, 20, 21)
#a; mean of m
mmean <- mean(m)
#b; 4.68449
msd <- sd(m)
#c; #95% confidence interval; 21.92752
qnorm(as.double(0.95), mmean, msd)
#e; #95% confidence interval; 25.11998
qnorm(as.double(0.99), mmean, msd)

#2: Same mean, but SD of population is unknown..
mmean2 <- mean(m)
msd2 <- sd(m)
#Use QT in this case
mlen2 <- length(m)
#d: 95% confidence interval of the mean; 14.32325 (lower)
mmean2 + qt(1-0.95/2,mlen2-1) * msd2/sqrt(mlen2)
#14.52724 (upper)
mmean2 + qt(1 - 0.95 + (1 - 0.95/2),mlen2-1) * msd2/sqrt(mlen2)
#e: 99% confidence interval of the mean; 14.24241 (lower)
mmean2 + qt(1-0.99/2,mlen2-1) * msd2/sqrt(mlen2)
#14.28281 (upper)
mmean2 + qt(1 - 0.99 + (1 - 0.99/2),mlen2-1) * msd2/sqrt(mlen2)
#f: Second confidence interval is larger.. greater percentage means bigger interval

#3:
individuals <- 25
imean <- 22
isd <- 2
#a: estimation of the mean of the population; 22
#b: Compute the 92% confidence interval of the mean; 22.0406 (lower)
imean + qt(1 - 0.92 / 2, individuals-1) * isd/sqrt(individuals)
#upper; 22.12359
imean + qt(1 - 0.92 + (1-0.92/2), individuals-1) * isd/sqrt(individuals)
#c: lower; 22.18078
qnorm(1 - 0.92 / 2, imean, 1.8)
#upper; 22.54987
qnorm(1-0.92 + (1-0.92/2), imean, 1.8)

#4:
#Mean height of Boys
mheightboys <- 172
#Amount of Boys
lheightboys <- 12
#Variance of sample
vheightboys <- 62

#Mean height of Girls
mheightgirls <- 166
#Amount of Girls
lheightgirls <- 12
#Variance of sample
vheightgirls <- 65

#a: 95% boys; 25.14587 (lower)
qnorm(1-0.95/2, lheightboys-1) * sqrt(vheightboys)/sqrt(lheightboys)
#25.72761 (upper)
qnorm(1-0.9 + (1-0.95/2), lheightboys-1) * sqrt(vheightboys)/sqrt(lheightboys)
#b: 95% girls; 25.74705 (lower)
qnorm(1-0.95/2, lheightgirls-1) * sqrt(vheightgirls)/sqrt(lheightgirls)
#26.3427 (upper)
qnorm(1-0.9 + (1-0.95/2), lheightgirls-1) * sqrt(vheightgirls)/sqrt(lheightgirls)
#c: 99% boys; 25.03182 (lower)
qnorm(1-0.99/2, lheightboys-1) * sqrt(vheightboys)/sqrt(lheightboys)
#25.08882 (upper)
qnorm(1-0.99 + (1-0.99/2), lheightboys-1) * sqrt(vheightboys)/sqrt(lheightboys)
#d: 99% girls; 25.63028 (lower)
qnorm(1-0.99/2, lheightgirls-1) * sqrt(vheightgirls)/sqrt(lheightgirls)
#25.68864 (upper)
qnorm(1-0.99 + (1-0.99/2), lheightgirls-1) * sqrt(vheightgirls)/sqrt(lheightgirls)
#e: 92% boys; 25.23162 (lower)
qnorm(1-0.92/2, lheightboys-1) * sqrt(vheightboys)/sqrt(lheightboys)
#25.6977 (upper)
qnorm(1-0.92 + (1-0.92/2), lheightboys-1) * sqrt(vheightboys)/sqrt(lheightboys)
#f: 92% girls; 25.83485 (lower)
qnorm(1-0.92/2, lheightgirls-1) * sqrt(vheightgirls)/sqrt(lheightgirls)
#26.3120 (upper)
qnorm(1-0.92 + (1-0.92/2), lheightgirls-1) * sqrt(vheightgirls)/sqrt(lheightgirls)

#5:
temps <- c(98.2,98.5,98.3,98.1,98.3,98.7,98.1,98.4,98.2,98.4,98.3,98.2)
ltemps <- length(temps)
#98.30833
mtemps <- mean(temps)
#0.1729862
stemps <- sd(temps)
intervals <- c(0.95, 0.99)
#Lower; 98.31156 98.30898
mtemps + qt(1 - intervals/2,mlen2-1) * stemps/sqrt(ltemps)
#Upper; 98.31809 98.31027
mtemps + qt(1 - intervals + (1 - intervals/2),mlen2-1) * stemps/sqrt(ltemps)

#0.4790293 (lower)
qnorm(1-0.95/2, ltemps-1) * 0.15 / sqrt(ltemps)
#0.484503 (upper)
qnorm(1-0.95 + (1-0.95/2), ltemps-1) * 0.15 / sqrt(ltemps)





