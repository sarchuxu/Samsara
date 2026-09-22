public class Enemy 
{
    private final String name;
    private final String description; 
    private final String[] image;
    private final int maxHp; 
    private int currHp;
    private final int atk;

    public Enemy(String name, String description, String[] image, int maxHp, int atk)
    {
        this.name = name;
        this.description = description;
        this.image = image;
        this.maxHp = maxHp; 
        currHp = maxHp;
        this.atk = atk;
    }

    public void changeCurrHp(int change)
    {
        currHp += change;
    }

    public String getName()
    {
        return name;
    }
    public String getDescription()
    {
        return description;
    }
    public String printImage()
    {
        String out = "";
        for (String s: image)
        {
            out += s;
            out += "\n";
        }
        return out;
    }
    public int getMaxHp()
    {
        return maxHp;
    }
    public int getCurrHp()
    {
        return currHp;
    }
    public int getAtk()
    {
        return atk;
    }
}
