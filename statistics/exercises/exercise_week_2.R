#Summary statistics#

#Vector with 98 random values between 1 and 30; with sample()
x <- sample(c(1:30),98,replace=T)

#Copy x, add at the end; two values 50 and -5
x2 <- append(x,values = c(50,-5),after = length(x))

#Check the median of both; Both 16,5
xmed <- median(x)
x2med <- median(x2)

#Summary of x
summary(x)

#Find lower Fence; 9
xlower = quantile(x)[2]
#Find upper Fence; 23
xupper = quantile(x)[4]
#Find third Q; 23 - 9 = 14
IQR(x)

#Two plots in one row setting
par(mfrow=c(1,2))
#No outliers
boxplot(x)
#Outlier/dot exists; 50
boxplot(x2)
#Reset
par(mfrow=c(1,1))

#Find the Standard deviation of both
sd(x)
#Bigger because more elements
sd(x2)

#Visualisations#

#Import eba1977 dataset from ISwR package
library(ISwR)

#Mean of cases; 9,3333
mean(eba1977$cases)
#No outliers; value 3th quartile: 11,0
boxplot(eba1977$cases)

eba <- eba1977
#Summary of Dataset; 4 values per age group, 6 values per city
summary(eba1977)

#Make two Histograms
par(mfrow=c(2,1))
#1 for: amount of cases in city Fredericia; 64
ecf <- eba1977[eba1977$city=="Fredericia",]$cases
#2 for: amount of cases in city Vejle; 51
ecv <- eba1977[eba1977$city=="Vejle",]$cases
hist(ecf,main="Cases Fredericia by Age group",xlab="Age groups",ylab="Cases")
hist(ecv,main="Cases Vejle by Age group",xlab="Age groups",ylab="Cases")
#Reset
par(mfrow=c(1,1))

#Compare cases from the Cities
boxplot(eba1977$cases ~ eba1977$city)
#Compare age groups from the Cities; adding class "factor" to an invalid object
boxplot(as.numeric(eba1977$age) ~ eba1977$city)

#Find the average of cases of each city
aggregate(eba1977$cases, by=list(eba1977$city), FUN=mean)
aggregate(eba1977$cases, by=list(eba1977$age), FUN=mean)
#City with highest average; Fredericia
#Age group with highest average; 65-69

#Import tb.dilute dataset from ISwR package
tbd <- tb.dilute
table(tbd$logdose,tbd$animal,tbd$reaction)
table(c(tbd$logdose,tbd$animal,tbd$reaction))