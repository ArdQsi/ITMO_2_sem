package data;

import exceptions.FileException;

/**
 * interface for storable object
 */
public interface Collectionable{
    /**
     * get element id
     * @return
     */
    public long getId();

    /**
     * setId useful for replacing object in collection or set id for object
     * @param ID
     * @throws FileException
     */
    public void setId(long ID) throws FileException;

    /**
     * get name of element
     * @return
     */
    public String getName();

    /**
     * get price of element
     * @return
     */
    public Integer getPrice();

    /**
     * compare two object
     * @param product
     * @return
     */
    public int compareTo(Collectionable product);

    /**
     * compare two object by id
     * @param ID
     * @return
     */
    public long compareToId(String ID);
}
