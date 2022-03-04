package collection;

import data.*;
import file.FileManager;
import java.time.LocalDateTime;
import java.util.*;
import static io.OutPutManager.print;
import static io.OutPutManager.printErr;
import static utils.DateConvertor.dateToString;

/**
 * Class works with collection
 */
public class ProductCollectionManager{
    private LinkedList<Product> collection;
    private HashSet<Long> uniqueIds;
    private LocalDateTime initDate;

    /**
     * Constructor, set start values
     */
    public ProductCollectionManager() {
        uniqueIds = new HashSet<>();
        FileManager fileManager = new FileManager();
        collection = fileManager.readCSVS();
        initDate = java.time.LocalDateTime.now();
    }

    /**
     * Method that gets information about the collection
     * @return info about the collection
     */
    public String getInfo() {
        return "LinkedList of Product, size: " + Integer.toString(collection.size()) + ", initialization date: " + dateToString(initDate);
    }

    /**
     * Method that generates a unique id
     * @return unique id
     */
    public long generateId() {
        if (collection.isEmpty())
            return 1;
        else {
            long id = collection.getLast().getId() + 1;
            if (uniqueIds.contains(id)) {
                while (uniqueIds.contains(id)) id +=1;
            }
            uniqueIds.add(id);
            return id;
        }
    }

    /**
     * Get collection
     * @return collection
     */
    public LinkedList<Product> getCollection() {
        return collection;
    }

    /**
     * Add new element to collection
     * @param product
     */
    public void add(Product product) {
        product.setId(generateId());
        collection.add(product);
        print("Added element");
        print(product.toString());
    }

    /**
     * Check id in collection
     * @param id
     * @return true/false
     */
    public boolean chekID(long id){
        for (Product product: collection){
            if (product.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove element by id
     * @param id
     */
    public void removeById(long id) {
        for (Product product : collection) {
            if (product.getId() == id) {
                collection.remove(product);
                uniqueIds.remove(id);
                print("element with id = " + Long.toString(id) + " deleted");
                return;
            }
        }
    }

    /**
     * update element with id
     * @param id
     * @param newProduct
     */
    public void updateId(long id, Product newProduct) {
        int idx = 0;
        for (Product product : collection) {
            if (product.getId() == id) {
                newProduct.setId(id);
                collection.set(idx,newProduct);
                print("element with id: " + Long.toString(id) + " updated");
                return;
            }
            idx += 1;
        }
    }

    /**
     * Clear all elements of collection
     */
    public void clear() {
        collection.clear();
        uniqueIds.clear();
        print("collection cleared");
    }

    /**
     * Add element if the price is less
     * @param product
     */
    public void addIfMin(Product product) {
        for (Product Product : collection) {
            if (product.compareTo(Product)==1) {
                printErr("unable to add");
                return;
            }
        }
        add(product);
    }

    /**
     * Displays elements with less manufacture cost
     * @param cost
     */
    public void printLessThanManufactureCost(float cost) {
        LinkedList<Product> list = new LinkedList<>();
        for (Product product: collection) {
            if (product.getManufactureCost()<cost) {
                list.add(product);
            }
        }
        if(list.isEmpty()) print("нет таких элементов, у которых стоимость прозводства меньше "+cost);
        else {
            print(list);
        }
    }

    /**
     * Displays element with unique owner
     */
    public void printUniqueOwner() {
        LinkedList<String> owner = new LinkedList<>();
        print("unique owners: ");
        for (Product product: collection) {
            if(!owner.contains(product.getOwner())) {
                print(product.getOwner());
                owner.add(product.getOwner());
            }
        }
    }
}
