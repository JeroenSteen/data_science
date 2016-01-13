package pkgcase;

import java.util.*;
import java.util.Map;

public class Genetic {

    public HashMap population;
    public int bitLength;
    
    public Genetic(CsvObject csvr) {
        //Produce an initial population of individuals
        population              = population(csvr);
        System.out.println("individuals in population: "+population.size());
        
        //Evaluate fitness of all individual
        HashMap fitnesses       = fitness(population);
        System.out.println("fitnesses of individuals: "+fitnesses);
        
        //Select filter individuals for reproduction
        ArrayList selections    = selection(fitnesses);
        System.out.println("selections from individuals: "+selections);
        
        ArrayList crossovers    = crossover(selections);
        System.out.println("crossovers from selected individuals: "+crossovers);
        
        ArrayList mutations     = mutation(crossovers);
        System.out.println("mutations from crossed individuals: "+mutations);
        
    }
    
    /*
    * Generate initial population
    * - make bitstring per individual
    */
    public HashMap population(CsvObject csvr) {
        String[] headers = csvr.headers;
        
        //Construct a population
        HashMap population = new HashMap();
        //Iterator for current individual in population
        int populationIterator = 0;
        for(Map r:csvr.records) {
            //Construct a binary string
            String bit = new String();
            //Find bits for string
            for(String header: headers) {
                String h = (String)r.get(header);
                //Concat current binary string with bit
                bit = h+""+bit;
            }
            //Place binary string to population
            population.put(populationIterator, bit);
            
            ++populationIterator;
        }
        return population;
    }
    
    /*
    * Fitness evaluation:
    * - find fitness per individual
    */
    public HashMap fitness(HashMap population) {
        //Construct fitness values per individual
        HashMap fitnesses = new HashMap();
        
        //Loop all individuals
        for(Object individualIdentifier: population.keySet()) {
            //Iterator for strong attributes of individual
            int individualIterator = 0;
            
            //Find individual in population based on identifier
            String individual = (String)population.get(individualIdentifier);
            for(char bit : individual.toCharArray()) {
                //Strong attribute is found, increment fitness value
                if(bit == '1') ++individualIterator;
            }
            
            fitnesses.put(individualIdentifier, individualIterator);

        }
        return fitnesses;
    }
    
    /*
    * Genetic operator for selection, select:
    * - random individual
    * - individual with best fitness
    */
    public ArrayList selection(HashMap fitnesses) {
        //Random select a parent from the population
        Random randomGenerator          = new Random();
        Object[] individualFitnesses    = fitnesses.values().toArray();
        int randomIdentifier            = randomGenerator.nextInt(individualFitnesses.length);
        Object randomFitness            = individualFitnesses[randomIdentifier];
        String randomBitstring          = (String)this.population.get(randomIdentifier);
        
        System.out.println(
                "random individual identifier: "+randomIdentifier+
                ", has fitness: "+randomFitness+
                ", with bitstring: "+randomBitstring
        );
        
        //Find individual with best fitness
        Object bestFitness              = Collections.max(fitnesses.values());
        String bestBitstring = null;
        int bestIdentifier = 0;
        for (Object fitnessIdentifier : fitnesses.keySet()) {
            //Find a individual with the best fitness
            if(bestFitness.equals(fitnesses.get(fitnessIdentifier))) {
                bestBitstring   = (String)this.population.get(fitnessIdentifier);
                bestIdentifier  = (int)fitnessIdentifier;

                break;
            }
        }
        
        //Found random and best bit string
        if(randomBitstring != null && bestBitstring != null) {
            System.out.println(
                    "best individual identifier: "+bestIdentifier+
                    ", has fitness: "+bestFitness+
                    ", with bitstring: "+bestBitstring
                );
            
            //Elitism (keep the best ones) should be used..
        }
        
        ArrayList selectedIndividuals = new ArrayList();
        selectedIndividuals.add(randomBitstring);
        selectedIndividuals.add(bestBitstring);
        return selectedIndividuals;
        
    }
    //Genetic operator for crossover, should be ∈[0.65,0.85]: One-point crossover
    public ArrayList crossover(ArrayList selections) {
        //Recombine between (two) individuals
        String firstBitstring       = (String)selections.get(0);
        String secondBitstring      = (String)selections.get(1);
        
        //Pick up a cutoff point (random position), first find bit length
        int bitLength               = firstBitstring.length();
        //Make it available for the total algorithm
        this.bitLength = bitLength;
        //Construct a random cutoff point, between range 0 and bit length
        int randomNumber            = randomNumberInRange(0, bitLength);
        
        //Cut begin part, from first and second bit string
        String beginFirstString     = firstBitstring.substring(0, randomNumber);
        String beginSecondString    = secondBitstring.substring(0, randomNumber);
        //Cut end part, from first and second bit string
        String endFirstString       = firstBitstring.substring(randomNumber, bitLength);
        String endSecondString      = secondBitstring.substring(randomNumber, bitLength);
        
        //Exchange the branches to yield two new individuals
        String newFirstString       = beginFirstString+endSecondString;
        String newSecondString      = beginSecondString+endFirstString;
        
        ArrayList crossedIndividuals = new ArrayList();
        crossedIndividuals.add(newFirstString);
        crossedIndividuals.add(newSecondString);
        return crossedIndividuals;
    }
    //Genetic operator for mutation, should be very low : 0.01% - 0.1 %
    public ArrayList mutation(ArrayList crossovers) {
        String firstBitstring       = (String)crossovers.get(0);
        String secondBitstring      = (String)crossovers.get(1);
        
        //Construct a random mutationable point, between range 0 and bit length
        int randomNumber            = randomNumberInRange(0, this.bitLength);
        
        String newFirstString       = changeBitInPosition(randomNumber, firstBitstring);
        String newSecondString      = changeBitInPosition(randomNumber, secondBitstring);
        
        ArrayList mutatedIndividuals = new ArrayList();
        mutatedIndividuals.add(newFirstString);
        mutatedIndividuals.add(newSecondString);
        return mutatedIndividuals;
    }
    
    //Helper functions for the algorithm
    private static int randomNumberInRange(int min, int max) {
        if (min >= max) throw new IllegalArgumentException("max must be greater than min");

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    private static String changeBitInPosition(int position, String bitstring){
        char[] bitArray        = bitstring.toCharArray();
        
        if(bitArray[position] == '0') {
            bitArray[position] = '1';
        } else {
            bitArray[position] = '0';
        }
        return new String(bitArray);
    }
    
    
    
}
