package com.mycompany.boltzmanmachine;

/**
 *
 * @author Erik
 */
public interface NodeInterface {
        
    public double getOutput();
    
    public void propTrain(double expected);
    
    public double getValue();
    
    public double getError(double expected);
    
    public String toString();
    
    public void setParents(NodeInterface[] newParents);
    
}
