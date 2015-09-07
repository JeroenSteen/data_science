#http://www.r-bloggers.com/how-to-get-the-frequency-table-of-a-categorical-variable-as-a-data-frame-in-r/
install.packages('plyr')
library(plyr)

lyrics <- read.table("lyrics.txt", sep="\n")

#list
typeof(lyrics)
summary(lyrics)
#419
amount <- length(lyrics[[1]])

lyrics <- count(lyrics)
topwords <- lyrics[lyrics$freq>=10,]


#table(counted)

#Take the 10 Top words
plot(lyrics$V1[0:10],lyrics$freq[0:10])