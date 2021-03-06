#a: Import the dataset
train <- read.csv("train.csv", header=T)
train <- as.data.frame(train)

#b: Find all survivors; 342
survivors <- train[train$Survived==1,]
#Find the Passenger Classes; 1,2,3
levels(factor(survivors$Pclass))

#b1: survivors per passanger class 
barplot(
  height = c(
    sum(survivors$Pclass==1),
    sum(survivors$Pclass==2),
    sum(survivors$Pclass==3)
  ),
  names.arg=c(
    "1st","2nd","3rd"
  ),
  xlab="Passenger Classes",ylab="Amount of survivors",
  main="Survivors Titanic from each Passenger Class"
)

#Find all deceased; 549
deceased <- train[train$Survived==0,]
#b2: deceased per passanger class 
barplot(
  height = c(
    sum(deceased$Pclass==1),
    sum(deceased$Pclass==2),
    sum(deceased$Pclass==3)
  ),
  names.arg=c(
    "1st","2nd","3rd"
  ),
  xlab="Passenger Classes",ylab="Amount of deceased",
  main="Deceased Titanic passengers per Passenger Class"
)


#b3:
female_survivors <- survivors[survivors$Sex == "female",]
male_survivors <- survivors[survivors$Sex == "male",]
female_deceased <- deceased[deceased$Sex == "female",]
male_deceased <- deceased[deceased$Sex == "male",]

library(plyr)
female_survivors <- count(female_survivors, c("Survived"))$freq
male_survivors <- count(male_survivors, c("Survived"))$freq
female_deceased <- count(female_deceased, c("Survived"))$freq
male_deceased <- count(male_deceased, c("Survived"))$freq

barplot(
  c(female_survivors, male_survivors, female_deceased, male_deceased),
  names.arg=c("Female survivors", "Male survivors", "Female deceased", "Male deceased"),
  main="Survivors and Deceased based on Sex",
  xlab="Survivors and Deceased by Sex",
  ylab="Amount of passengers",
  col=c("pink", "lightblue", "pink", "lightblue")
)

#b4:
embarkations <- train[train$Embarked != "",]
embarkations <- count(embarkations$Embarked)
barplot(embarkations$freq,
        names.arg=c("Cherbourg", "Queenstown", "Southampton"),
        main="Passangers per Port of Embarkation",
        xlab="Ports of Embarkations",
        ylab="Amount of passengers"
        )


#c: Find the mean and ds of Age
mAge <- mean(train$Age, na.rm=T)
sdAge <- sd(train$Age, na.rm=T)
#http://www.r-bloggers.com/normal-distribution-functions/
dnAge <- dnorm(train$Age,
      mean=mAge,
      sd=sdAge)
#c1: Plot normal distribution and histogram of Age; one plot..
hist(train$Age, freq=F, col="lightgrey", xlab="Ages", ylab="Occurences", main="Titanic passengers by Age", border="blue")
lines(density(train$Age, na.rm=T), col="darkblue")
lines(dnorm(0:80,mAge,sdAge), col="red", lwd=2)
legend("topleft", c("Normal", "Density"), col=c("red", "darkblue"), lwd=5)
axis(side = 4, col = "red", at=c(0,.01,.02,.03), labels=c(0,50,100,200))

#c2: Q-Q plot for the Age variable
qqnorm(train$Age, datax=T, ylab = "Years", xlab="Occurences", main="Titanic passengers by Age")

install.packages("car")
library(car)
qqPlot(train$Age, main="Titanic passengers by Age", ylab="Ages", xlab="Normal quantiles")

#d: Computements
ages <- na.omit(train$Age)
ageN <- length(ages)

#probability that a passenger is younger than 12; 7.034117e-30
#pbinom(1,ageN,
#       length(ages[ages < 12]) / ageN
#       )
pnorm(12,mAge,sdAge) * 100 #11.15356 percentage

#probability that a passenger is between 20 and 50 years old
pnorm(50,mAge,sdAge) - pnorm(20,mAge,sdAge)

#probability that a passenger is older than 65
1 - pnorm(65,mAge,sdAge)

#e: question 1
females <- train[train$Sex == "female",]
males <- train[train$Sex == "male",]
t.test(females$Survived, males$Survived, alternative="two.sided")

#question 2
criticals <- train[train$Age <= 8 | train$Age >= 65,]
noncriticals <- train[train$Age >= 8 | train$Age <= 65,]
t.test(noncriticals$Survived, criticals$Survived, alternative="greater")

#question 3
ps1 <- train[train$Pclass == 1,]
ps2 <- train[train$Pclass == 2,]
ps3 <- train[train$Pclass == 3,]

sum(ps3$Survived == T) #119
sum(ps2$Survived == T) #87
sum(ps1$Survived == T) #136

#Alternative: Is a/Higher class survivors greather then b/Opposite
t.test(ps1$Survived, ps2$Survived, alternative="greater") #Rejected null hypothesis, 0 is not in conf
t.test(ps1$Survived, ps3$Survived, alternative="greater") #Rejected null hypothesis, 0 is not in conf
t.test(ps2$Survived, ps1$Survived, alternative="greater") #Accepted null hypothesis, 0 is in conf
#The higher class only haves a better chance of survival two times

#f:
fares <- train$Fare 
ages <- train$Age

#Linear model: Multiple R-Squared is Low (Bad)
model <- lm(ages ~ fares)

plot(ages,fares, xlab="Ages", ylab="Fares", main="Fare per age")
abline(model, col="red")

#Summary: 0.8716
summary(model)

#predict.lm(model, 15); 15 hours is..
353.16 + 25.33 * 15 #733.11; intercept + slope * <hours>