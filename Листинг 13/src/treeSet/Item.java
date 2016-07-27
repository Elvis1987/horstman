package treeSet;
import java.util.*;

/**Описание изделия и его номер по каталогу
 * Created by Elvis on 19.07.2016.
 */
public class Item implements Comparable<Item> {
    private String description;
    private int partNumber;

    /**Конструирует объект изделия
     * @param aDescription Описывает изделия
     * @param aPartNumber Номер изделия по каталогу
     */
    public Item(String aDescription, int aPartNumber){
        description = aDescription;
        partNumber = aPartNumber;

    }
    /**Получает писание данного изделия
     * @return Возвращает описание данногоизделия
     */
    public String getDescription(){
        return description;
    }
    public String toString(){
        return "[description = " + description + ", partNumber=" + partNumber + "]";
    }
    public boolean eguals (Object otherObject){
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Item other = (Item) otherObject;
        return  Objects.equals(description, other.description) && partNumber == other.partNumber;
    }

    public int hashCode(){
        return Objects.hash(description, partNumber);
    }

    public int compareTo(Item other){
        return Integer.compare(partNumber, other.partNumber);
    }
}
