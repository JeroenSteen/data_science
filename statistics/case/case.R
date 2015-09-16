#Import the dataset
train <- read.csv("train.csv", header=T)
train <- as.data.frame(train)

#Find all survivors; 342
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


#Survivors and deceased per Age
levels(factor(survivors$Age))
#b3:
hist(
  survivors$Age,
  breaks=80,
  xlab="Ages (year)",ylab="Amount of survivors",
  main="Survivors Titanic per Age"
  )
