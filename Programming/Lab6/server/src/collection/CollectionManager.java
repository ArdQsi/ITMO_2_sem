package collection;

import data.Product;

import java.util.LinkedList;
import java.util.List;

public interface CollectionManager<T>{
    public long generateNextId();
    /*public void sort();
    public LinkedList<T> getCollection();*/
    public void add(T element);
    public String getInfo();
    /*public boolean checkId();
    public boolean removeById();
    public boolean updateById(long id, T newElement);*/
    public LinkedList<Product> getCollection();
    public boolean chekID(long id);
    public boolean updateById(long id, Product newProduct);
    public boolean removeById(long id);
    public void clear();
    public boolean addIfMin(Product product);
    public List<Product> removeGreater(long id);
    public List<Product> filterLessThanManufactureCost(Float cost);
    public List<String> getUniqueOwner();
    public List<Product> removeLower(long id);


}
