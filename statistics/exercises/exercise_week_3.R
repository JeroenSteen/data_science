#Binomial distribution: number of successes in a sequence of n independent yes/no experiments

#formula: X ~ Binom(n,p)

#n = repeated times
#p = probability (same for each time)
#1 - p (to know the failure)
#return success or failure;
#X = amount of "success" returns
#x = amount of "success" probability

#dbinom(x,n,p) = singular value (x)
#pbinom(x,n,p) = smaller or equal then (x)

#1: Twenty-five percent of the contact lenses sold at an optical retailer are tinted.
#If 25 customers buy contact lenses on a particular day, what is the probability
#that at least one customer buys tinted contact lenses?
p1 <- dbinom(1,25,0.25) #0.006271195
p1 * 100 #0.6271195 percentage

#2: A multiple-choice test has 20 questions. Each question has 3 possible answers,
#one of which is correct. If you answer each of the questions randomly,
#what is the probability of answering exactly 12 questions correctly?
p2 <- dbinom(12,20,1/3) #0.009248728
p2 * 100 #0.9248728 percentage

#3: One out of every 100 radon detection test kits sold at a home improvement outlet
#gives inaccurate results. In a sample of 50 kits obtained from the outlet,
#what is the probability that exactly 48 give accurate results?
p3 <- dbinom(48,50,1-(1/100)) #0.07561804
p3 * 100 #7.561804 percentage

#4: A six-sided dice is tossed three times. Success is achieved when a 6 or a 5
#turns up on the dice. Construct the probability distribution for this binomial experiment.
#Plot such probability distribution in R, using a barplot.
barplot( dbinom(0:1,3,1/6) ) #0.5787037 (1 success), 0.3472222 (2 success).. ?

#5: One out of every 5 people buys premium gasoline. If a service station has 500 customers
#on a given day, what is the expected number of people who buy premium gasoline?
1/5*500 #100 people

#u = expected value
#o = standard deviation
#u = n*p
#o = sqrt( n*p*(1-p) )

#6: Find the variance and standard deviation for the following experiments:
#a) 60 trials, p=1/3
u1 <- 60 * 1/3 #20
sd1 <- sqrt( 60*1/3*(1-1/3) ) #3.651484.. ?
#b) 10 trials, p=1/6
u2 <- 10 * 1/6 #1.666667
sd2 <- sqrt( 10*1/6*(1-1/6) ) #1.178511.. ?
#c) 40 trials, p=0.16
u3 <- 40 * 0.16 #6.4
sd3 <- sqrt( 40*0.16*(1-0.16) ) #2.31862.. ?

#7: In a doctor’s office, 80% of the patients are adults.
#If 15 patients are scheduled for an appointment on a given day,
#what is the expected number of adults? What is the standard deviation?
0.8*15 #12 adults
sd7 <- sqrt( c(12,15)*0.8*(1-0.8) ) #1.385641, 1.549193.. ?

#8: At a bowling alley, 75% of the bowlers rent shoes.
#On a given day, the bowling alley had 500 bowlers.
#What is the probability that 400 or more rented shoes?
p8 <- pbinom(400,500,1-0.75) #0.001344302
p8 * 100 #0.1344302 percentage

#9:
#10:
#11:

#12:

#Poisson distribution: number of events occurring in a fixed interval of time/space

#dpois(x,l) = exact value (x)
#ppois(x,l) = smaller or equal then (x)


#1: Suppose the random variable x has a Poisson distribution with parameter l=4.
#Compute the following probabilities:
#P(X=4)
e1 <- dpois(x=4,lambda=4) #0.1953668
#P(X!=0)

#P(X<6)
e3 <- sum( dpois(x=0:6,lambda=4) ) #0.889326
#P(X<=6)
e4 <- ppois(q=6,lambda=4) #0.889326
#P(X>2)
1-ppois(q=1, lambda=4)

#P(X>=2)
#P(1<=X<=5)
#P(2<X<4)
#P(3<=X<=6)

#2:
#3:
#4:
#5:
#6:



