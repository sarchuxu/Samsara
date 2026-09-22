public class Item 
{
    private final int id;
    private final String color;
    private final String[] keywords; 
    private final String name;
    private String description; 

    public Item(int id, String color, String[] keywords, String name, String description)
    {
        this.id = id;
        this.color = color;
        this.keywords = keywords.clone(); 
        this.name = name;
        this.description = description;
    }

    public void changeDescription(String newDesc)
    {
        description = newDesc;
    }
    public boolean haveSameKeyword(String s)
    {
        for (String str: keywords)
        {
            if (str.equalsIgnoreCase(s))
            {
                return true;
            }
        }
        return false;
    }
    public boolean haveIdenticalKeywords(String s)
    {
        for (String str: keywords)
        {
            if (!str.equalsIgnoreCase(s))
            {
                return false;
            }
        }
        return true;
    }
    private String normalizeDir(String dir)
    {
        return switch(dir)
        {
            case "n" -> "north"; 
            case "ne" -> "northeast"; 
            case "e" -> "east"; 
            case "se" -> "southeast"; 
            case "s" -> "south"; 
            case "sw" -> "southwest"; 
            case "w" -> "west"; 
            case "nw" -> "northwest"; 
            default -> dir;
        };
    }

    public int getId()
    {
        return id;
    }
    public String getColor()
    {
        return color;
    }
    public String[] getKeywords()
    {
        return keywords.clone();
    }
    public String getName()
    {
        return name;
    }
    public String getDescription(Location l)
    {
        String out = description;
        if (id == 2)
        {
            int i = Player.getNumEquipped();
            if (i > 0)
            {
                out += "\nYour current fletching(s): ";
                if (Player.isRedEquipped() && i > 1)
                {
                    out += "red feather, ";
                    i--;
                }
                else if (Player.isRedEquipped() && i == 1)
                {
                    out += "red feather.";
                }
                if (Player.isOrangeEquipped() && i > 1)
                {
                    out += "orange feather, ";
                    i--;
                }
                else if (Player.isOrangeEquipped() && i == 1)
                {
                    out += "orange feather.";
                }
                if (Player.isYellowEquipped() && i > 1)
                {
                    out += "yellow feather, ";
                    i--;
                }
                else if (Player.isYellowEquipped() && i == 1)
                {
                    out += "yellow feather.";
                }
                if (Player.isGreenEquipped() && i > 1)
                {
                    out += "green feather, ";
                    i--;
                }
                else if (Player.isGreenEquipped() && i == 1)
                {
                    out += "green feather.";
                }
                if (Player.isBlueEquipped() && i > 1)
                {
                    out += "blue feather, ";
                    i--;
                }
                else if (Player.isBlueEquipped() && i == 1)
                {
                    out += "blue feather.";
                }
                if (Player.isIndigoEquipped() && i > 1)
                {
                    out += "indigo feather, ";
                    i--;
                }
                else if (Player.isIndigoEquipped() && i == 1)
                {
                    out += "indigo feather.";
                }
                if (Player.isPinkEquipped() && i > 1)
                {
                    out += "pink feather, ";
                    i--;
                }
                else if (Player.isPinkEquipped() && i == 1)
                {
                    out += "pink feather.";
                }
            }
        }
        else if ((id == 4 || id == 7) && l.getStepNum() < l.getPath().length)
        {
            out += "\nThe flames on the feather shift in the wind before pointing to the " + normalizeDir(l.getPath()[l.getStepNum()]) + ".";
        }
        else if ((id == 20 || id == 21) && l.getStepNum() < l.getPath().length)
        {
            out += "\nThe feather is lightly stirred by the breeze, spinning around until it finally stops in the " + normalizeDir(l.getPath()[l.getStepNum()]) + " direction.";
        }
        return out;
    }
}
