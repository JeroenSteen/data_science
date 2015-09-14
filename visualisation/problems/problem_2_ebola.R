ds <- read.csv("ds_ebola_2014.csv")
ds <- as.data.frame(ds)

#Find all Ebola "cases"
dcases <- ds[ds$category==" cases",]

#Set the first 10 days per Country
dguinea <- dcases[dcases$country=="Guinea",]
dguinea <- dguinea[1:10,]
dguinea$days <- 1:10

dliberia <- dcases[dcases$country=="Liberia",]
dliberia <- dliberia[1:10,]
dliberia$days <- 1:10

dmali <- dcases[dcases$country=="Mali",]
dmali <- dmali[1:10,]
dmali$days <- 1:10

dnigeria <- dcases[dcases$country=="Nigeria",]
dnigeria <- dnigeria[1:10,]
dnigeria$days <- 1:10

dsenegal <- dcases[dcases$country=="Senegal",]
dsenegal <- dsenegal[1:10,]
dsenegal$days <- 1:10

dsierra <- dcases[dcases$country=="Sierra Leone",]
dsierra <- dsierra[1:10,]
dsierra$days <- 1:10

dspain <- dcases[dcases$country=="Spain",]
dspain <- dspain[1:10,]
dspain$days <- 1:10

duk <- dcases[dcases$country=="UK",]
duk <- duk[1:10,]
duk$days <- 1:10

dusa <- dcases[dcases$country=="USA",]
dusa <- dusa[1:10,]
dusa$days <- 1:10

#Merge the frames
drcases <- Reduce(function(x, y) merge(x, y, all=TRUE), list(dguinea,dliberia,dmali,dnigeria,dsenegal,dsierra,dspain,duk,dusa))
drcases <- drcases[!is.na(drcases)]
library(ggplot2)
#1: Show the rise of Ebola; most rapid is Sierra Leone
gg <- ggplot2::qplot(drcases$day, drcases$confirmed, color=drcases$country, main="Rise of Ebola",xlab="Days",ylab="Amount of cases (persons confirmed with Ebola)", na.rm = TRUE)
gg <- gg + labs(color = "Countries") + geom_line()
gg

#Tryout with Date range
#dmin <- min( as.Date(dcases$date) )
#dmax <- max( as.Date(dcases$date) )
#dmindays <- as.numeric(dmin) #* 86400000
#dmaxdays <- as.numeric(dmax) #* 86400000
#ddays <- dmaxdays - dmindays #276
#sp <- data.frame(date=rep(seq.POSIXt(as.POSIXct("2014-01-01"), by="month", length.out=276), each=1), var=rnorm(276))
#dcases <- split(sp, as.Date(dcases$date))
#data < 10-5-2000


#2: Total numbers of Ebola deaths
ddeaths <- ds[ds$category==" deaths",]
ddeaths$sum <- ddeaths$confirmed + ddeaths$probable + ddeaths$total
#dsum <- aggregate(ddeaths$date, by=list(ddeaths$country), FUN=sum)
#ggplot2::qplot(dsum$Group.1,dsum$x)
library(scales)
ddeaths$date <- as.Date(ddeaths$date)
gg2 <- ggplot2::qplot(ddeaths$date,ddeaths$sum, color=ddeaths$country, main="Total Ebola deaths over time",xlab="Months in 2014",ylab="Amount of deaths (persons confirmed with Ebola)")
gg2 <- gg2 + scale_x_date(label = date_format("%b"),
                    breaks = seq(min(ddeaths$date), max(ddeaths$date), "month")
                    )
gg2 <- gg2 + labs(color = "Countries") + geom_line()
gg2

#3: New infections occur; date
dcases <- ds[ds$category==" cases",]
dcountries <- split(dcases, dcases$country, drop=T)
for (i in 1:length(dcountries)) {
  dcountry <- dcountries[[i]]
  
  print( as.character(dcountry[["country"]]) )
  dcountrymin <- min(as.Date(dcountry$date))
  
  print( dcountrymin )
}

#4: Per country confirmed Cases totals per month
dcases = ds[ds$category==" cases",]
library(scales)
dcases$date <- as.Date(dcases$date)
gg4 <- ggplot2::qplot(dcases$date,dcases$confirmed, color=dcases$country, main="Total persons confirmed with Ebola over time",xlab="Months in 2014",ylab="Amount of confirmed cases")
gg4 <- gg4 + scale_x_date(label = date_format("%b"),
                          breaks = seq(min(dcases$date), max(dcases$date), "month")
)
gg4 <- gg4 + labs(color = "Countries") + geom_line()
gg4
