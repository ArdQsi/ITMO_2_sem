package data;

public class Coordinates{
    private long x; //Максимальное значение поля: 658
    private Integer y; //Максимальное значение поля: 211, Поле не может быть null

    /**
     * Constructor
     * @param x
     * @param y
     */
    public Coordinates(long x, Integer y){
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
    }

    /**
     * Set new x
     * @param x
     */
    public void setX(long x) {
        this.x = x;
    }

    /**
     * Set new y
     * @param y
     */
    public void setY(Integer y) {
        this.y = y;
    }

    /**
     *
     * @return x coordinate
     */
    public long getX() {
        return this.x;
    }

    /**
     *
     * @return y coordinate
     */
    public Integer getY() {
        return y;
    }

    /**
     * Convert coordinates data to string
     * @return String with coordinates data
     */
    @Override
    public String toString() {
        String s ="";
        s+="{x : \"" + Long.toString(x) + "\",} ";
        s+="{y : \"" + Integer.toString(y) +"\"}";
        return s;
    }

}
