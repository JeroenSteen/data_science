#1: Show help for the Iris dataset
help(iris)

#Load dataset in Variable
irises <- iris

#Attach the dataset
attach(irises)

#Check the Data
summary(irises)

#Get all the Setosa Flowers
setosa <- irises[Species=="setosa",]

#See the Factors of the Petal Widths and Lengths
pwidths <- factor(irises[Species=="setosa",]$Petal.Width)
plengths <- factor(irises[Species=="setosa",]$Petal.Length)
#See the Factors of the Sepal Widths and Lengths
swidths <- factor(irises[Species=="setosa",]$Sepal.Width)
slengths <- factor(irises[Species=="setosa",]$Sepal.Length)

#Count the Factors of the Petal Widths and Lengths
nlevels(pwidths)
nlevels(plengths)
#Count the Factors of the Sepal Widths and Lengths
nlevels(swidths)
nlevels(slengths)

#Show the Widths of Setosas
irises[Species=="setosa",]$Petal.Width

#2: Make a Histogram with: 20 bins of Petal width fot the Iris Setosa
hist(irises[Species=="setosa",]$Petal.Width)
hist(irises[Species=="setosa",]$Petal.Width, main="Irises",xlab="X",ylab="Y")
legend("topright", c("Germany", "Plastic"), col=c("blue", "red"), lwd=10)

install.packages("ggplot2")
library(ggplot2)
#3: Make a scatterplot with: Sepal length vs Petal length of all Irises
ggplot2::qplot(Sepal.Length,Petal.Length, color=Species,main="Iris: sepal length vs petal length",xlab="sepal length (cm.)",ylab="petal length (cm.)")

#Get all the Irises where the Petal width is larger then 1.5
petals <- iris[Petal.Width>1.5,]

#4: Make a scatterplot with: Compare Sepal length vs Sepal width of the "Large petal flowers"
lp <- ggplot2::qplot(petals$Sepal.Length,petals$Sepal.Width, color=petals$Species, main="Large petal flowers: sepal length vs sepal width",xlab="sepal length (cm.)",ylab="sepal width (cm.)")
lp <- lp + scale_fill_discrete(name="legendName")
lp

#5: Inter-species difference
msetosa <- mean(iris[Species=="setosa",]$Sepal.Length)
mversicolor <- mean(iris[Species=="versicolor",]$Sepal.Length)
mvirginica <- mean(iris[Species=="virginica",]$Sepal.Length)
interspecies <- c(msetosa,mversicolor,mvirginica)
names(interspecies) <- c("setosa", "versicolor", "virginica")
plot(c(msetosa,mversicolor,mvirginica))