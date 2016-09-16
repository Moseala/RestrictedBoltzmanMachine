/*
 * ‘******************************************************
 * ‘***  DataContainer
 * ‘***  Author: Erik Clary
 * ‘******************************************************
 * ‘*** Purpose: This class is a container for the data read from the XLSX Object
 * ‘******************************************************
 * ‘*** June 12, 2016
 * ‘******************************************************
 * ‘*** Jun 12: Initial code written
 * ‘******************************************************
 * ‘*** Look at this!
 * ‘*** List all the places in the code where you did something interesting,
 * ‘*** clever or elegant.  If you did good work in this program and you want
 * ‘*** me to consider it in your grade, point it out here.
 * ‘*******************************************************
 */
package com.mycompany.boltzmanmachine;

/**
 *
 * @author Erik
 */
public class DataContainer {
    private String name;
    private double[] data;
    
    /*
    ‘******************************************************
    ‘***  DataContainer
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This is the constructor for the DataContainer class.
    ‘*** Method Inputs:
    ‘*** String name: name for this DataContainer object.
    ‘*** double[] data: the vector or array of data for this object.
    ‘*** Return value:
    ‘*** N/A
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    public DataContainer(double[] data){
        this.data = data;
    }
    
    /*
    ‘******************************************************
    ‘***  getName
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This method returns the name of this object
    ‘*** Method Inputs:
    ‘*** N/A
    ‘*** Return value:
    ‘*** String: the name of this object
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    public String getName(){
        return name;
    }
    
    /*
    ‘******************************************************
    ‘***  getData
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This method returns the data of this object.
    ‘*** Method Inputs:
    ‘*** N/A
    ‘*** Return value:
    ‘*** double[]: the data/vector of this object
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    public double[] getData(){
        return data;
    }
}
