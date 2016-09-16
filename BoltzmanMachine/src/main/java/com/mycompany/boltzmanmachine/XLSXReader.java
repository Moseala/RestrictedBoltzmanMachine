/*
 * ‘******************************************************
 * ‘***  XLSXReader
 * ‘***  Author: Erik Clary
 * ‘******************************************************
 * ‘*** Purpose: This is the file reader class for the K-Means algorithm, it handles XLSX files.
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

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Erik
 * methods in this file were derived from examples found at http://java67.blogspot.com/2014/09/how-to-read-write-xlsx-file-in-java-apache-poi-example.html
 */
public class XLSXReader {
    
    private XSSFWorkbook inWorkbook;
    private XSSFSheet workingSheet;
    private String filePath;
    
    /*
    ‘******************************************************
    ‘***  XLSXReader
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This is the constructor for the XLSXReader class. It requests the file to be used through a FileDialog, and creates an XLSXWorkbook and sheet from it.
    ‘*** Method Inputs:
    ‘*** int sheet: the sheetnumber where the data is found
    ‘*** Return value:
    ‘*** N/A
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    public XLSXReader(int sheet) throws FileNotFoundException{
        Frame frm = new Frame();
        FileDialog fileD = new FileDialog(frm, "Please choose the data file:", FileDialog.LOAD);
        fileD.setVisible(true);
        filePath = fileD.getDirectory()+ File.separator + fileD.getFile();
        InputStream inFile = new FileInputStream(filePath);
        
        try {
            inWorkbook = new XSSFWorkbook(inFile);
            workingSheet = inWorkbook.getSheetAt(sheet); 
            inFile.close();
        } catch (IOException ex) {
            Logger.getLogger(XLSXReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /*
    ‘******************************************************
    ‘***  getData
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This method retuns the data read from the XLSXSheet
    ‘*** Method Inputs:
    ‘*** int[] columns: columns to be read from the sheet in this format: [1,2,3] will read the 2nd, 3rd, and 4th columns on the sheet
    ‘*** int records: the number of records to be read (rows)
    ‘*** Return value:
    ‘*** double: the value of the distance between the two vectors
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    public ArrayList<DataContainer> getData(int[] columns, int records){
        
        ArrayList<DataContainer> outData = new ArrayList<>();
        DataContainer data;
        for(int x = 0; x< records; x++){
            String[] preData = new String[columns.length];
            XSSFRow row = workingSheet.getRow(x+1); //gets the row that the data is on (+1 to account for 0 row is column identifiers.
            for(int y = 0; y<columns.length; y++){
                if(row.getCell(columns[y]).getCellType() == Cell.CELL_TYPE_STRING)
                    preData[y] = row.getCell(columns[y]).getStringCellValue(); //pulls data only from the columns specified by the argument
                if(row.getCell(columns[y]).getCellType() == Cell.CELL_TYPE_NUMERIC) //This could be done better, and elminate the bottom loop to convert.
                    preData[y] = String.valueOf(row.getCell(columns[y]).getNumericCellValue());
            }
            double[] valueData = new double[preData.length];
            for(int y = 0; y< preData.length; y++){
                valueData[y] = Double.valueOf(preData[y]);
            }
            data = new DataContainer(valueData);
            outData.add(data);
        }
        return outData;
    }
    
    /*
    ‘******************************************************
    ‘***  writeData
    ‘***  Author: Erik Clary
    ‘******************************************************
    ‘*** Purpose: This method writes the data back into the XLSXWorkbook.
    ‘*** Method Inputs:
    ‘*** ArrayList<DataContainer>[] groupList: The array of arraylists containing the data to be written back into the workbook.
    ‘*** Return value:
    ‘*** N/A
    ‘******************************************************
    ‘*** June 12, 2016
    ‘******************************************************
    */
    public void writeData(ArrayList<DataContainer>[] groupList){
        
        XSSFSheet outSheet = inWorkbook.getSheet("myGroupedData");
        if(outSheet == null)
            outSheet = inWorkbook.createSheet("myGroupedData");
        
        int rowNum = 0;
        for(int x = 0; x<groupList.length; x++){
            for(int groupNum = 0; groupNum < groupList[x].size(); groupNum++){
                Row row = outSheet.createRow(rowNum++);
                Cell cell = row.createCell(0);
                cell.setCellValue(x);
                cell = row.createCell(1);
                cell.setCellValue(groupList[x].get(groupNum).getName());
                for(int internal = 2; internal < groupList[x].get(groupNum).getData().length+2; internal++){ //+2 for the dataContainer's group and name in addition to the data length
                    cell = row.createCell(internal);
                    cell.setCellValue(groupList[x].get(groupNum).getData()[internal-2]);
                }
            }
        }
        
        try {
            OutputStream outStream = new FileOutputStream(filePath);
            inWorkbook.write(outStream);
            outStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XLSXReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XLSXReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
