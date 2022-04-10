package file;

import exceptions.FileException;


public interface ReaderWriter {
    public String readCSV() throws FileException;

    //public void writeToCSV(String data) throws FileException;
}

