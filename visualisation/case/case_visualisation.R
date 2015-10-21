#Deadline October 30th 11:20 am

ds <- read.csv("lemming_data_21.csv")
library(plyr)
ds <- rename(ds,c(
  "x" = "sightings", #Lemming sightings per hour in Rotterdam
  "y" = "weights", #Lemming weight in kg
  "z" = "lifetimes" #Lemming lifetime in years
  ))
pairs(ds)

#1. Plot the distribution of the variable x. What is the mean, standard deviation
# and error on the mean? Show your plot and state your answers.
plot(ds$sightings, main="Lemming sightings in Rotterdam", xlab="Hours", ylab="Amount of Lemmings")
abline(h = mean(ds$sightings), col="red")
legend(
  "bottomleft",
  "Mean",
  lty=1,
  col="red"
  )

#Taken from: http://stackoverflow.com/questions/2676554/in-r-how-to-find-the-standard-error-of-the-mean
sem <- function(x) sd(x)/sqrt(length(x)) #Function to find the "standard error on the mean" of a sample

#25.47741
mean_sightings <- mean(ds$sightings)
#8.373527
sd_sightings <- sd(ds$sightings)
#0.8373527
sem_sightings <- sem(ds$sightings)

#52.98623
mean_weights <- mean(ds$weights)
#30.25457
sd_weights <- sd(ds$weights)
#3.025457
sem_weights <- sem(ds$weights)

#89.51
mean_lifetimes <- mean(ds$lifetimes)
#50.30623
sd_lifetimes <- sd(ds$lifetimes)
#5.030623
sem_lifetimes <- sem(ds$lifetimes)

#2. What relationship is there between the variables y and z? Show your plot.
plot(ds$weights, ds$lifetimes, main="Lemming weights vs lifetimes", xlab="Weights (kg)", ylab="Lifetime (years)")
#Strong linear relationship
#A Lemming can become on average 2 years old and maximum 4 years old: http://www.grenswetenschap.nl/permalink.asp?i=4384
#Lemming weight from 30 to 110 or up to 130 grams: http://www.helsinki.fi/science/metapop/species/lemmings.html

#3. Make a linear fit to the relationship between y and z. Show your fit on another
#plot. What are the best fit parameters and parameter errors? Are you happy with your fit?
model <- lm(ds$lifetimes ~ ds$weights)
#(Intercept)   ds$weights  
#2.769        1.637
plot(ds$weights, ds$lifetimes, main="Lemming weights vs lifetimes", xlab="Weights (kg)", ylab="Lifetime (years)")
abline(model, col="red")
legend(
  "topleft",
  "Linear fit",
  lty=1,
  col="red"
)

#4. Research lemmings. Do your results make sense? Discuss.
