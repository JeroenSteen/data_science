wales_males <- c(34274,59906,55749,45987,75247,67052,65683,37793,59823,89837,118087,68450,68789,61522,169893,114635,28791,87701,34325,44382,44922,71380)
names(wales_males) <- c("Isle of Anglesey","Gwynedd","Conwy","Denbighshire","Flintshire","Wrexham","Powys","Ceredigion","Pembrokeshire","Carmarthenshire","Swansea","Neath Port Talbot","Bridgend", 
                        "The Vale of Glamorgan",
                        "Cardiff",
                        "Rhondda Cynon Taf",
                        "Merthyr Tydfil",
                        "Caerphilly",
                        "Blaenau Gwent",
                        "Torfaen",
                        "Monmouthshire",
                        "Newport"
)

wales_females <- c(35477,61968,59479,47747,77259,67792,67293,38129,62616,93940,120936,71362,70389,64814,176197,119775,30011,91105,35489,46693,46401,74356)
names(wales_females) <- c("Isle of Anglesey","Gwynedd","Conwy","Denbighshire","Flintshire","Wrexham","Powys","Ceredigion","Pembrokeshire","Carmarthenshire","Swansea","Neath Port Talbot","Bridgend", 
                        "The Vale of Glamorgan",
                        "Cardiff",
                        "Rhondda Cynon Taf",
                        "Merthyr Tydfil",
                        "Caerphilly",
                        "Blaenau Gwent",
                        "Torfaen",
                        "Monmouthshire",
                        "Newport"
)

#26069148
england_males <- c(1269703, 3464685, 2598078, 2234493, 2763187, 2875807, 4033289, 4239298, 2590608)
names(england_males) <- c("NORTH EAST", "NORTH WEST", "YORKSHIRE AND THE HUMBER", "EAST MIDLANDS", "WEST MIDLANDS", "EAST", "LONDON", "SOUTH EAST", "SOUTH WEST")

#26943308
england_females <- c(1327183, 3587492, 2685655, 2298729, 2838660, 2971158, 4140652, 4395452, 2698327)
names(england_females) <- c("NORTH EAST", "NORTH WEST", "YORKSHIRE AND THE HUMBER", "EAST MIDLANDS", "WEST MIDLANDS", "EAST", "LONDON", "SOUTH EAST", "SOUTH WEST")


#68374
mean(wales_males)
#70874
mean(wales_females)
#2896572
mean(england_males)
#2993701
mean(england_females)

#Taken from: http://stackoverflow.com/questions/2547402/standard-library-function-in-r-for-finding-the-mode
mode <- function(x) { #Function to find the "mode" of a sample
  ux <- unique(x)
  ux[which.max(tabulate(match(x, ux)))] #Find value that occurs most often
}

#34274
mode(wales_males)
#35477
mode(wales_females)
#1269703
mode(england_males)
#1327183
mode(england_females)

#Taken from: http://stackoverflow.com/questions/2676554/in-r-how-to-find-the-standard-error-of-the-mean
sem <- function(x) sd(x)/sqrt(length(x)) #Function to find the "standard error on the mean" of a sample

#6997.111
sem(wales_males)
#7250.485
sem(wales_females)
#305136.1
sem(england_males)
#314022.8
sem(england_females)

wales_balance_males <- c( min(wales_males), mean(wales_males), max(wales_males) )
wales_balance_females <- c( min(wales_females), mean(wales_females), max(wales_females) )
england_balance_males <- c( min(england_males), mean(england_males), max(england_males) )
england_balance_females <- c( min(england_females), mean(england_females), max(england_females) )


