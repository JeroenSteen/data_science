survey <- read.table(text =
"V1 V2 V3 V4 V5 V6 V7 V8 V9 V10
5 6 5 5 5 5 5 4 5 5
2 1 2 2 2 2 2 3 2 2",
header = TRUE)
barplot(
  as.matrix(survey),
  col=c("green","red"),
  xlab="Vragen",
  ylab="Respondenten",
  main="Mini enquête"
  )

par(mfrow=c(1, 1), mar=c(5,5,5,5))