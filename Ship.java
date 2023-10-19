/**
 * 
 */
public class Ship {
    String name;
    int length;
    public Ship (int size, String called) //this is the closest i could get to struct
    {
        name = called;
        length = size;
    }

    
    /** 
     * @return int
     */
    public int getSize () //this doesn't actually have to be changed. so.
    {
        return length;
    }

    
    /** 
     * @return String
     */
    
     public String getName () //getters instead
    {
        return name;
    }
}
