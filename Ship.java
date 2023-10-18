public class Ship {
    String name;
    int length;
    public Ship (int size, String called)
    {
        name = called;
        length = size;
    }

    public int getSize ()
    {
        return length;
    }

    public String getName ()
    {
        return name;
    }
}
