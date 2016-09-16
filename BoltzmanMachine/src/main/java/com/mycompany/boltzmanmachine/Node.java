/*
 * ‘******************************************************
 * ‘***  ClaryProject1
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

import com.sun.media.jfxmedia.logging.Logger;
import java.util.Random;

/**
 *
 * @author Erik
 */
public class Node implements NodeInterface{
    private NodeInterface[] parents;
    private double[] weights;
    private Random rand = new Random();
    private double value;
    private double lastWeightChange[];
    private double error =0;

    /*
    ‘******************************************************
    ‘***  Node
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This is the constructor for Node
    ‘*** Method Inputs:
    ‘*** NodeInterface[] parents: technically this is the array for the children of this node, but considering the backpropagation, it made more sense to label these parents as the tree is initially traversed from from the opposite side of the root/output.
    ‘*** Return value:
    ‘*** N/A
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    public Node(NodeInterface[] parents){
        weights = new double[parents.length];
        
        for(int x= 0; x<weights.length; x++){
            weights[x] = rand.nextGaussian();
        }
        
        this.parents = parents;
        lastWeightChange = new double[parents.length];
    }

    /*
    ‘******************************************************
    ‘***  setParents
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This method allows to set different parents for the node.
    ‘*** Method Inputs:
    ‘*** NodeInterface[] parents: technically this is the array for the children of this node, but considering the backpropagation, it made more sense to label these parents as the tree is initially traversed from from the opposite side of the root/output.
    ‘*** Return value:
    ‘*** N/A
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    public void setParents(NodeInterface[] newParents){
        this.parents = newParents;
    }

    /*
    ‘******************************************************
    ‘***  setWeight
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This method sets the weight for a certain edge for this node.
    ‘*** Method Inputs:
    ‘*** int index: the index of the weight to be set
    ‘*** double weight: the new weight for the edge
    ‘*** Return value:
    ‘*** N/A
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    public void setWeight(int index, double weight) {
        this.weights[index] = weight;
    }


    /*
    ‘******************************************************
    ‘***  getWeight
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This method returns the weight of an edge of this node.
    ‘*** Method Inputs:
    ‘*** int index: the index of the weight to be retuned
    ‘*** Return value:
    ‘*** double: the weight of the edge requested
    ‘******************************************************
    ‘*** June 12, 2016
    ‘****/
    public double getWeight(int index) {
        return weights[index];
    }
    

    /*
    ‘******************************************************
    ‘***  getOutput
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This method gets the output and sets the value of the node.
    ‘*** Method Inputs:
    ‘*** N/A
    ‘*** Return value:
    ‘*** double: the output of the node based on its parents.
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    /**
     * This is functionally: {Sum(inputVector * weight)+ bias} for each input for this node.
     * @return
     */
    public double getOutput() {

        double out = 0;
        for(int x = 0; x<parents.length; x++){
            out += weights[x] * parents[x].getOutput();
        }
        value = BoltzMath.activationFunction(out); //this node is activated (1) if the activation is greater that .5
        
        return value;
    }

    /*
    ‘******************************************************
    ‘***  propTrain
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This is a recursive reverse propagation training. Should ONLY be called from the root of the tree.
    ‘*** Method Inputs:
    ‘*** double expected: the expected output from the tree
    ‘*** Return value:
    ‘*** N/A
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    @Override
    public void propTrain(double expected) {
        getOutput(); //this calculates the maps' values and sets them for use with this back propagation
        error = getError(expected); //this calculates the total error for this map for use with the back propagaiton.
        for(int x = 0; x< weights.length; x++){
            double tempWeightChange = BoltzMath.backPropagationWeight(weights[x],lastWeightChange[x], error);
            lastWeightChange[x] = weights[x] - tempWeightChange;
            weights[x] = tempWeightChange;
            parents[x].propTrain(expected); //this will recurse until it hits the input nodes, then pop back out
            //System.out.printf("%f %n", weights[x]);
        }
    }

    /*
    ‘******************************************************
    ‘***  getValue
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This method returns the value stored in this node
    ‘*** Method Inputs:
    ‘*** N/A
    ‘*** Return value:
    ‘*** double: value stored in this node
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    @Override
    public double getValue() {
        //getOutput();
        return value;
    }

    /*
    ‘******************************************************
    ‘***  getError
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This recursive method gets the cumulative means squared error for the branch 
    ‘*** Method Inputs:
    ‘*** double expected: the expected output for the tree/branch
    ‘*** Return value:
    ‘*** double: the error of the branch.
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    @Override
    public double getError(double expected) {
        double error = 0;
        for(int x= 0; x<parents.length; x++){
            error+= parents[x].getError(expected) + BoltzMath.getMSESum(expected, getValue());
        }
            
        return error;
    }
    
    /*
    ‘******************************************************
    ‘***  toString
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This recursive method returns the string representation for the tree. Should only be called from the root
    ‘*** Method Inputs:
    ‘*** N/A
    ‘*** Return value:
    ‘*** String: Representation of this tree
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    @Override
    public String toString(){
        String outString = "";
        for(int x = 0; x<parents.length; x++){
            outString += "Weight[" +x + "]: " + weights[x] + "\n" + parents[x].toString();
            outString +=  "\n[FinishTree]\n ";
        }
        return outString;
    }

    

}
