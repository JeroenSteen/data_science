#http://www.public.iastate.edu/~vardeman/stat502x/A%20Priori%20Algorithm%20R%20Example.pdf
library(arules)

ls <- list(
  c("kaas","tomaat"),
  c("kaas", "tomaat", "komkommer"),
  c("tomaat")
)

#Set transaction names
names(ls) <- paste("tr", c(1:length(ls)), sep = "") 

#Coerce into transactions 
trans <- as(ls, "transactions") 

#Analyze transactions
summary(trans)
inspect(trans)

rules <- apriori(trans, parameter=list(
    support=0.01,
    confidence=0.5,
    target="rules")
  )

#Show support, confidence and lift
inspect(head(sort(rules,by="lift"),n=length(ls)))