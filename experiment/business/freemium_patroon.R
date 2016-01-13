#Freemium patroon formule uit Business model generatie bladzijde 103

#Aantal gebruikers in gebruikersbestand
gebruikers = 1:3000
#Percentage van premium gebruikers in gebruikersbestand
premium_gebruikers = 0.2
#Percentage van freemium gebruikers in gebruikersbestand
freemium_gebruikers = 0.8
#Toetreden tot het gebruikersbestand ratio
groei_ratio = 1.01
#Uitstappen uit het gebruikersbestand ratio
churn_ratio = 0.99

#Financiele aspect dienst, unit uitgedrukt in "bedrag in euro per jaar"
prijs_premium = 30
kosten_freemium = 5
kosten_premium = 5
vaste_kosten = 100
aquisitie_kosten = 300

#Inkomsten van de dienst
inkomsten = (gebruikers * premium_gebruikers * prijs_premium) * groei_ratio * churn_ratio

#Kosten van de dienst
kosten = (gebruikers * freemium_gebruikers * kosten_freemium) + (gebruikers * premium_gebruikers * kosten_premium)

#Operationele winst aan de dienst
winst = inkomsten - kosten - vaste_kosten - aquisitie_kosten

#Plot de winst per gebruikers aantal
plot(winst,
     main="Freemium/premium model voor KSART",
     xlab="Aantal KSART freemium/premium members",
     ylab="Winst in euro per jaar",
     type="l",
     lwd=2,
     col="green")