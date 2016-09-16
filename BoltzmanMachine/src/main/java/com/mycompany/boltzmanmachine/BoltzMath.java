/*
 * ‘******************************************************
 * ‘***  BoltzMath
 * ‘***  Author: Erik Clary
 * ‘******************************************************
 * ‘*** Purpose: This is the math class for this program, and handles most mathematical operations.
 * ‘******************************************************
 * ‘*** June 12, 2016
 * ‘******************************************************
 * ‘*** Jun 12: Initial code written
 * ‘******************************************************
 * ‘*** Look at this!
 * ‘*** 
 * ‘*******************************************************
 */


package com.mycompany.boltzmanmachine;

/**
 *
 * @author Erik
 */
public class BoltzMath {
    private static final double LEARNING_RATE = 0.1; //This is the learning rate for the program, set to .1 for xor example
    private static final double MOMENTUM_RATE = .4; //this is the momentum rate for the program, set to .4 for xor example
    private static final double ACTIVE_THRESHOLD = 0.5; //This is the activation threshold, should be left at .5 but can be adjusted.
    
    /*
    ‘******************************************************
    ‘***  activationFunction
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This is the activation function for the program. It is the REMU function described in AIFH Vol.3
    ‘*** Method Inputs:
    ‘*** double summedInput: the value to be activated (x)
    ‘*** Return value:
    ‘*** double: the acitvated value
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    public static double activationFunction(double summedInput){
        double out = 0;
        
        out = 1/(1+Math.exp(-summedInput));
        //System.out.println("DEBUG: " +out);
        
        return out;
    }
    
    /*
    ‘******************************************************
    ‘***  getMSESum
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This method returns the means squared error for the two input terms
    ‘*** Method Inputs:
    ‘*** double expected: the expected value
    ‘*** double calculated: the calculated value
    ‘*** Return value:
    ‘*** double: the MSE for the input terms
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    public static double getMSESum(double expected, double calculated){
        return Math.pow((expected-calculated), 2)/2;
    }
    
    /*
    ‘******************************************************
    ‘***  backPropagationWeight
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This method returns the revised weight based on the backpropagation equation 6.12 from AIFH Vol. 3
    ‘*** Method Inputs:
    ‘*** double preWeight: the previous weight
    ‘*** double lastWeightChange: the delta(change) of the last iteration for this branch
    ‘*** double error: the MSE of the edge
    ‘*** Return value:
    ‘*** double: the new weight for the edge
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    public static double backPropagationWeight(double prevWeight, double lastWeightChange, double error){
        double newWeight = 0;
        
        newWeight = prevWeight - (LEARNING_RATE)*(error/prevWeight) + (MOMENTUM_RATE * lastWeightChange); //Equation 6.12 from Jeff Heaton Vol. 3
                
        return newWeight;
    }
    
}
