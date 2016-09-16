/*
 * ‘******************************************************
 * ‘***  NodeInput
 * ‘***  Author: Erik Clary
 * ‘******************************************************
 * ‘*** Purpose: This is an input node for the neural network, allows for the recursive weight calculation to stop
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
public class NodeInput implements NodeInterface{

    double value;
    boolean bias;
    
    /*
    ‘******************************************************
    ‘***  NodeInput
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This is the constructor for NodeInput
    ‘*** Method Inputs:
    ‘*** double value: The value of the input
    ‘*** boolean bias: Whether this node is a bias node or not.
    ‘*** Return value:
    ‘*** N/A
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    public NodeInput(double value, boolean bias){
        this.value = value;
        this.bias = bias;
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
    public double getOutput() {
        return value;
    }

    /*
    ‘******************************************************
    ‘***  propTrain
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This is the end of the recursive method of the same name called from the root. This simply ends the recursion.
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
        //if(!bias){
        //    this.value = BoltzMath.activationFunction(value);
        //}
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
        return value;
    }

    /*
    ‘******************************************************
    ‘***  getError
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This is the end of the recursive method called from the root, as NodeInputs are the terminal nodes for the tree.
    ‘*** Method Inputs:
    ‘*** double expected: the expected output for the tree/branch
    ‘*** Return value:
    ‘*** double: the error of the branch, which is 0 because this is an input.
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    @Override
    public double getError(double expected) {
        return 0.0;
    }
    
    /*
    ‘******************************************************
    ‘***  toString
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This is the end of the recursive method called from the root, as NodeInputs are the terminal nodes for the tree.
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
        if(bias)
            return "BiasNode";
        return "InputNode";
    }

    @Override
    public void setParents(NodeInterface[] newParents) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
