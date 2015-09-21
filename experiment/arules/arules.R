#http://www.public.iastate.edu/~vardeman/stat502x/A%20Priori%20Algorithm%20R%20Example.pdf
#http://www.salemmarafi.com/code/market-basket-analysis-with-r/comment-page-1/
library(arules)

#Make a empty List
ls <- list()
#Find the Data file
con  <- file("breien.txt", open = "r")

#oneLine <- "Breien is een werkwijze om lappen textiel te maken van een doorlopend garen met behulp van twee of meer naalden."

#Loop all Lines from the Data file
while (length(oneLine <- readLines(con, n = 1, warn = FALSE)) > 0) {
  #oneLine <- gsub(".", "", oneLine)
  
  #Split every word; result is Vector
  oneLine <- strsplit(oneLine, " ")
  #Remove duplicates from the "Line"-vector
  #oneLine <- oneLine[!duplicated(oneLine)]
  oneLine <- unique(oneLine[[1]])
  
  #Save the new "Line"-vector in List
  ls <- c(ls,c(oneLine[[1]]))
}

#Close Data file
close(con)

ls <- list(
  c("tomaat","kaas", "ui"),
   c("kaas", "ui"),
   c("kaas")
)

#Set transaction names
names(ls) <- paste("tr", c(1:length(ls)), sep = "") 

#Coerce into transactions 
#trans <- as(ls[!duplicated(ls)], "transactions")
trans <- as(ls, "transactions")

#Analyze transactions
summary(trans)
inspect(trans)

rules <- apriori(trans, parameter=list(
    support=0.01,
    confidence=0.01,
    target="rules")
  )
#Show support, confidence and lift
inspect(head(sort(rules,by="lift"),n=length(ls)))
inspect(
  sort(rules,by="lift"),
  n=length(ls)
  )

