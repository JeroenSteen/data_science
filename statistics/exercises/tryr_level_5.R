chests <- c('gold', 'silver', 'gems', 'gold', 'gems')
types <- factor(chests)

as.integer(types)
levels(types)

weights <- c(300, 200, 100, 250, 150)
prices <- c(9000, 5000, 12000, 7500, 18000)
plot(weights, prices)

plot(weights, prices, pch=as.integer(types))

#Hardcoded
legend("topright",
       c("gems", "gold", "silver"),
       pch=1:3
       )
#Better
legend("topright",
       levels(types), #Label names
       pch=1:length(levels(types)) #Length
       )