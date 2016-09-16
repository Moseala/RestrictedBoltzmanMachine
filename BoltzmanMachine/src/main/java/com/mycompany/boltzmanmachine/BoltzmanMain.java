/*
 * ‘******************************************************
 * ‘***  BoltzmanMain
 * ‘***  Author: Erik Clary
 * ‘******************************************************
 * ‘*** Purpose: This is the main class for the program.
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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Erik
 */
public class BoltzmanMain {
    

    private static final int NUMRECORDS = 4; //number of rows to read from the excel document.
    private static final int NUMSHEET = 1;  //the sheet number to read from (this is zero-indexed, so the first sheet will be '0'
    private static final int[] COLUMNS = {0,1,3}; //the last column should be solution for the training
    private static final int NUMINPUT = COLUMNS.length -1; //this is the number of input columns, and should always be one less than the column count.
    private static final int OVERFLOW = 25; //this is the overflow/train counter, be sure to set this to a low number if using the xor example.
    private static final int NUMHIDDEN = 2; //the num of hidden 
    
    
    public static void main(String args[]){
        //Read in file
        ArrayList<DataContainer> data = null;
        try{
            XLSXReader inData = new XLSXReader(NUMSHEET);
            data = inData.getData(COLUMNS, NUMRECORDS);
        }
        catch(FileNotFoundException e){
        }
        
        //Creates input node array to be utilized later (starts with first point)
        NodeInput[] inputLayer = new NodeInput[NUMINPUT+1];
        for(int x = 0; x<inputLayer.length; x++){
            inputLayer[x] = new NodeInput(data.get(0).getData()[x],false);
        }
        inputLayer[NUMINPUT] = new NodeInput(1,true);//bias node for the hidden
        NodeInterface[] hiddenLayer = new NodeInterface[NUMHIDDEN+1];//Create hidden layer with random weights between -1.0 and 1.0 (Gaussian distr.)
        for(int x = 0; x<hiddenLayer.length-1; x++){
            hiddenLayer[x] = new Node(inputLayer.clone()); //all biases initially set to 1; the .clone should prevent the recursive deeplearning from messing through iterations, 
        }
        hiddenLayer[NUMHIDDEN] = new NodeInput(1,true); //bias node for the output
        
        Node output = new Node(hiddenLayer);
        
        //Train the machine
        
        
        int overflow =0;
        double calcValueBefore = 0;
        double calcValueAfter = 0;
        while(overflow++<OVERFLOW){
            for(int outer = 0; outer < data.size(); outer++){ //for the values in the training set
                for(int x = 0; x<inputLayer.length; x++){
                    inputLayer[x] = new NodeInput(data.get(outer).getData()[x],false); //set input nodes to training value[outer]
                }
                inputLayer[NUMINPUT] = new NodeInput(1,true);//bias node for the hidden
                for(int x = 0; x<hiddenLayer.length; x++){
                    hiddenLayer[x] = new Node(inputLayer.clone()); //gives new parents to the hidden layer, this is needed so .copy will work and recusion doesnt mess with each hidden's loop for the pos/neg calcs
                }
                hiddenLayer[NUMHIDDEN] = new NodeInput(1,true); //bias node for the output
                calcValueBefore = output.getValue();
                //begin back propagation training
                //if(Math.abs(BoltzMath.getError(data.get(outer).getData()[COLUMNS.length-1], calcValueBefore, NUMRECORDS)) > .1) //if you dont want to train the array when its error for this value is low.
                output.propTrain(data.get(outer).getData()[COLUMNS.length-1]);
                //finish back propagation training
                calcValueAfter = output.getValue();
                System.out.printf("Expected Value: %.4f | Before CalcValue: %.4f | After CalcValue: %.4f | Error: %.5f %n", data.get(outer).getData()[COLUMNS.length-1], calcValueBefore, calcValueAfter, BoltzMath.getMSESum(data.get(outer).getData()[COLUMNS.length-1], calcValueAfter));
                
            }
            
        }
        System.out.println("\nTrained Tree:");
        System.out.println(output.toString());
        while(true){
            Scanner in = new Scanner(System.in);
            System.out.print("Enter two binary values to XOR: ");
            String input = in.nextLine();

            inputLayer = new NodeInput[NUMINPUT];

            if(input.length() != NUMINPUT){
                System.out.println("String is too long, cannot input values to input nodes.");
            }
            else{
                for(int x = 0; x<NUMINPUT; x++){
                    inputLayer[x] = new NodeInput(Integer.parseInt("" + input.charAt(x)),false);
                    hiddenLayer[x].setParents(inputLayer);
                }
                
                
                System.out.printf("BoltzmanMachine predicts %f as the XOR ouput.%n",output.getOutput());

            }
        }
        //System.exit(0);
    }
    
    

}
