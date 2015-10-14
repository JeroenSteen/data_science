hours = c(4,9,10,14,4,7,12,22,1,3,8,11,5,6,10,11,16,13,13,10) 
score = c(390,580,650,730,410,530,600,790,350,400,590,640,450,520,690,690,770,700,730,640)

#0.9336055: Yes (Good)
cor(hours,score)

#Linear model: Multiple R-Squared is High (Good)
model = lm(score ~ hours)

plot(hours,score)
abline(model, col="red")

#Summary: 0.8716
summary(model)

#predict.lm(model, 15); 15 hours is..
353.16 + 25.33 * 15 #733.11; intercept + slope * <hours>
