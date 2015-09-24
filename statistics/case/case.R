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
hist(train$Age, freq=F, col="lightgrey", xlab="Ages", ylab="Occurences", main="Titanic passengers by Age")
#curve(
#  expr=dnorm(train$Age, mean=mAge, sd=sdAge),
#  col="red",
#  add=T)
lines(density(train$Age, na.rm=T), col="red")

#c2: Q-Q plot for the Age variable
qqnorm(train$Age, datax=T, ylab = "Years", xlab="Occurences", main="Titanic passengers by Age")

#d: Computements
ages <- na.omit(train$Age)
ageN <- length(ages)

#probability that a passenger is younger than 12; 7.034117e-30
#pbinom(1,ageN,
#       length(ages[ages < 12]) / ageN
#       )
( pnorm(12,mAge,sdAge) - pnorm(0,mAge,sdAge) ) * 100 #0.09108244, 9 percentage

#probability that a passenger is between 20 and 50 years old
pnorm(50,mAge,sdAge) - pnorm(20,mAge,sdAge)

#probability that a passenger is older than 65
pnorm(max(train$Age, na.rm=T), mAge,sdAge) - pnorm(65,mAge,sdAge)