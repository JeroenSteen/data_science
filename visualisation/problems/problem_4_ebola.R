ds <- read.csv("ds_ebola_2014_summary.csv")

plot(ds$outbreak_day, ds$confirmed_cases)
#dssp <- spline(ds$outbreak_day, ds$confirmed_cases)
dssp <- smooth.spline(ds$outbreak_day, ds$confirmed_cases)
#dssp[dssp$x <= 157.0000]
plot(dssp)
#Non Linear model; differentiate
#model1 <- nls(
#  formula = ds$outbreak_day ~ ds$confirmed_cases,
#  start = min(ds$outbreak_day) #First day
#  )
#1:
predict(dssp, 158) #138.5995 cases

#2:
#Linear
plot( 0:344 * max(ds$confirmed_cases) / 344, xlab="Days", ylab="Amount of confirmed cases")

#Exponential
plot( (0:344)^2 , xlab="Days", ylab="Amount of confirmed cases")

#Quadratic
library(pracma)
x <- 0:344
plot( x + 0.039 * x^2 * (1 + erf(x)), xlab="Days", ylab="Amount of confirmed cases")

#3:
par(mar=c(5,5,5,5))
plot(ds$confirmed_cases)
#Exponentional, the line goes up with a curve (not straight)

#4:
plot(ds$confirmed_cases, type="l", col="red", xlab="Days", ylab="Amount of confirmed cases")
lines((0:344)^2,col="green")

#5:
predict(dssp, 365 + sum(31,28,1)) #23194.2



