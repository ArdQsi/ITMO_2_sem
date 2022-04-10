package collection;

import data.Product;
import file.FileManager;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static io.OutPutManager.print;
import static utils.DateConvertor.dateToString;

public class ProductCollectionManager implements CollectionManager<Product> {
    private LinkedList<Product> collection;
    private Set<Long> uniqueIds;
    private LocalDateTime initDate;

    public ProductCollectionManager()  {
        uniqueIds = new HashSet<>();
        //collection = new LinkedList<>();
        FileManager fileManager = new FileManager();
        collection = fileManager.readCSVS();
        initDate = java.time.LocalDateTime.now();
    }

    public long generateNextId() {
        if (collection.isEmpty())
            return 1;
        else{
            long id = collection.getLast().getId()+1;
            if (uniqueIds.contains(id)) {
                while (uniqueIds.contains(id)) id +=1;
            }
            uniqueIds.add(id);
            return id;
            }
        }

    //нужно сделать метод
    public void sort() {

    }

    public LinkedList<Product> getCollection() {
        return collection;
    }

    public void add(Product product) {
        long id = generateNextId();
        uniqueIds.add(id);
        product.setId(id);
        collection.add(product);
    }

    public String getInfo() {
        return "LinkedList of Product, size: " + Integer.toString(collection.size()) + ", initialization date: " + dateToString(initDate);
    }

    public boolean chekID(long id){
        /*if (uniqueIds.contains(id)) return true;
        return false;*/
        Optional<Product> product = collection.stream()
                .filter(w->w.getId() == id)
                .findFirst();
        if (product.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean removeById(long id) {
        Optional<Product> product = collection.stream()
                .filter(w->w.getId() == id)
                .findFirst();
        if (product.isPresent()) {
            collection.remove(product.get());
            uniqueIds.remove(id);
            return true;
        }
        return false;
    }


    public boolean updateById(long id, Product newProduct) {
        Optional<Product> product = collection.stream()
                .filter(w->w.getId() == id)
                .findFirst();
        if(product.isPresent()) {
            collection.remove(product.get());
            newProduct.setId(id);
            collection.add(newProduct);
            return true;
        }
        return false;
    }

    //нужно чтобы все элементы удалились


    /*public boolean removeGreater(long id) {
        Optional<Product> product = collection.stream()
                .filter(w->w.getId() > id)
                .findAny();
        if (product.isPresent()) {
            collection.remove(product.get());
            return true;
        }
        return false;
    }*/

    public List<Product> removeGreater(long id) {
        LinkedList<Product> productList = collection.stream()
                .filter(w->w.getId() <= id)
                .collect(Collectors.toCollection(LinkedList::new));
        collection = productList;
        return productList;
    }

    public List<Product> removeLower(long id) {
        LinkedList<Product> productList = collection.stream()
                .filter(w->w.getId()>=id)
                .collect(Collectors.toCollection(LinkedList::new));
        collection = productList;
        return productList;
    }

    public List<Product> filterLessThanManufactureCost(Float cost) {
        List<Product> productList = collection.stream()
                .filter(w->w.getManufactureCost()<cost)
                .collect(Collectors.toList());
        return productList;
    }

    public void clear() {
        collection.clear();
        uniqueIds.clear();
    }

    public boolean addIfMin(Product product) {
        if (collection.stream()
                .min(Product::compareTo)
                .filter(w->w.compareTo(product)==-1)
                .isPresent())
        {
            return false;
        }
        add(product);
        return true;
    }




    //printLessThanManufactureCost
    /*public List<Product> printLessThanManufactureCost(Float cost) {
        List<Product> productCost = new LinkedList<>();
        productCost = collection.stream()
                .filter(product -> product.getManufactureCost() < cost)
                .collect(Collectors.toList());
        return productCost;
    }*/

    /*public void printLessThanManufactureCost(float cost) {
        LinkedList<Product> list = new LinkedList<>();
        for (Product product: collection) {
            if (product.getManufactureCost()<cost) {
                list.add(product);
            }
        }
        if(list.isEmpty()) print("нет таких элементов, у которых стоимость производства меньше "+cost);
        else {
            print(list);
        }
    }*/

    public List<String> getUniqueOwner() {
        List<String> owner = new LinkedList<>();
        owner = collection.stream()
                .map(product -> product.getOwner())
                .distinct()
                .collect(Collectors.toList());
        return owner;
    }

   /* public LinkedList<Product> deserializeCollection(LinkedList<Product> data) {

            if (data == null || data.equals("")) {
                collection = new LinkedList<>();
            } else {
                FileManager fileManager = new FileManager();
                collection = fileManager.readCSVS();
            }
        return collection;
    }*/
}

