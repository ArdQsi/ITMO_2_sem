package data;

import java.time.LocalDateTime;

/**
 * Person class
 */
public class Person{
    private String personName; //Поле не может быть null, Строка не может быть пустой
    private LocalDateTime birthday; //Поле может быть null
    private float weight; //Значение поля должно быть больше 0
    private String passportID; //Длина строки должна быть не меньше 7, Поле не может быть null

    /**
     * New person
     * @param personName
     * @param birthday
     * @param weight
     * @param passportID
     */
    public Person(String personName, LocalDateTime birthday, float weight, String passportID) {
        this.personName = personName;
        this.birthday = birthday;
        this.weight = weight;
        this.passportID = passportID;
    }
    public Person() {}

    /**
     * Method to get personName
     * @return personName
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * Method to get birthday
     * @return birthday
     */
    public LocalDateTime getBirthday() {
        return birthday;
    }

    /**
     * Method to get weight
     * @return weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Method to get passport id
     * @return passportID
     */
    public String getPassportID() {
        return passportID;
    }

    /**
     * Method to set person name
     * @param personName
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    /**
     * Method to set birthday
     * @param birthday
     */
    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    /**
     * Method to set weight
     * @param weight
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Method to set passport id
     * @param passportID
     */
    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    /**
     * Get all data of person in string
     * @return String with all fields of city
     */
    @Override
    public String toString() {
        String s ="";
        s += "{";
        if (personName != null) s+= "personName : \"" + getPersonName() +"\",";
        s+= "birthday : \"" + birthday + "\",";
        s+= "weight : \"" + getWeight() + "\",";
        if (passportID != null) s+= "passportID : \"" + getPassportID() + "\"}";
        return s;
    }

}
