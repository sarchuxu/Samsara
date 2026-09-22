import java.util.ArrayList; 

public class Player 
{
    private int maxHp; 
    private int currHp; 
    private int atk; 
    private int atkMult;
    private int def;
    private static int currStep;
    private static int featherSpace;
    private static double accuracy;
    private static double heal;
    private static boolean red;
    private static boolean orange;
    private static boolean yellow;
    private static boolean green;
    private static boolean blue;
    private static boolean indigo;
    private static boolean pink;
    private static boolean revive;
    private static String[] image;
    private Location location;
    private final ArrayList<Item> inv;
    private final ArrayList<Item> arrowInv;
    private Enemy currEnemy;

    public Player(int maxHp, Location location)
    {
        this.maxHp = maxHp; 
        currHp = maxHp; 
        atk = 1; 
        atkMult = 1;
        def = 1;
        currStep = 0;
        featherSpace = 3;
        accuracy = 1.0;
        heal = 0.0;
        red = orange = yellow = green = blue = indigo = pink = false;
        revive = false;
        image = new String[] 
        {
            "`.        ___     .’",
            " ‘. ` . /     \\ . ’ ", 
            "    ‘   )  v  | .  ",
            "        \\    / ",
            "         w  w"
        };
        this.location = location; 
        inv = new ArrayList<>();
        arrowInv = new ArrayList<>();
        currEnemy = null;
    }

    public void go(String dir)
    {
        dir = normalizeDir(dir);
        if (location.getName().equals("Temple of Ouroboros"))
        {
            location = location.getNextLoc();
            location.initLoc();
            return;
        }
        Location loc = location.travelPath(dir, this);
        if (loc != null)
        {
            location = loc; 
            location.initLoc();
        }
        else 
        {
            Location prev = location; 
            location = Game.getMaze(); 
            location.setPath(prev.getPath());
            location.setHelp(prev.getHelp());
            location.setOppLoc(prev.getOppLoc());
            location.setNextLoc(prev.getNextLoc());
            location.setEnemyStep(prev.getEnemyStep());
            location.setEnemies(prev.getEnemies());
            location.setEnemyDefeated(prev.getEnemyDefeated());
        }
    }
    private String normalizeDir(String dir)
    {
        return switch(dir)
        {
            case "north" -> "n"; 
            case "northeast" -> "ne"; 
            case "east" -> "e"; 
            case "southeast" -> "se"; 
            case "south" -> "s"; 
            case "southwest" -> "sw"; 
            case "west" -> "w"; 
            case "northwest" -> "nw"; 
            default -> dir;
        };
    }
    public void take(String obj)
    {
        ArrayList<Item> temp = location.getItems();
        if (obj.equals("all"))
        {
            if (!temp.isEmpty())
            {
                for (Item i: temp)
                {
                    inv.add(i);
                    location.removeItem(i);
                    System.out.println(i.getName() + " - taken!");
                }
            }
            else
            {
                System.out.println("There's nothing to take.");
            }
        }
        else
        {
            int count = 0; 
            if (!temp.isEmpty())
            {
                for (Item i: temp)
                {
                    if (i.haveSameKeyword(obj))
                    {
                        count++;
                    }
                }
            }
            switch (count) 
            {
                case 0 -> 
                {
                    if (itemFromKey(obj) != null)
                    {
                        System.out.println("You already have that!");
                    }
                    else
                    {
                        System.out.println("There is no " + obj + " here!");
                    }
                }
                case 1 -> {
                    Item i = location.itemFromKey(obj);
                    inv.add(i);
                    location.removeItem(i);
                    System.out.println("Taken!");
                }
                default -> System.out.println("Please specify which one.");
            }
        }
        if(haveSame())
        {
            dropSame();
        }
    }
    public void drop(String obj)
    {
        ArrayList<Item> temp = new ArrayList<>(inv);
        if (obj.equals("all"))
        {
            if (!temp.isEmpty())
            {
                for (Item i: temp)
                {
                    inv.remove(i);
                    location.addItem(i);
                    System.out.println(i.getName() + " - dropped!");
                }
            }
            else
            {
                System.out.println("Okay, but you didn't have anything to begin with.");
            }
        }
        else
        {
            int count = 0; 
            if (!temp.isEmpty())
            {
                for (Item i: inv)
                {
                    if (i.haveSameKeyword(obj))
                    {
                        count++;
                    }
                }
            }
            switch (count) 
            {
                case 0 -> 
                {
                    System.out.println("You don't have that!");
                }
                case 1 -> 
                {
                    Item i = itemFromKey(obj);
                    inv.remove(i);
                    location.addItem(i);
                    System.out.println("Dropped!");
                }
                default -> 
                {
                    System.out.println("Please specify which one.");
                }
            }
        }
    }
    public void equip(Item i)
    {
        if (featherSpace > 0)
        {
            switch(i.getColor())
            {
                case "red" -> 
                {
                    red = true;
                    atk++; 
                    revive = true;
                }
                case "orange" -> 
                {
                    orange = true;
                    atk++;
                    maxHp++;
                    currHp = maxHp;
                }
                case "yellow" -> 
                {
                    yellow = true; 
                    atk += 2;
                }
                case "green" -> 
                {
                    green = true; 
                    atkMult = 2; 
                    accuracy = 0.5;
                }
                case "blue" -> 
                {
                    blue = true; 
                    atk += 3;
                    heal = 0.5;
                }
                case "indigo" -> 
                {
                    indigo = true; 
                    atk += 6; 
                }
                case "pink" -> 
                {
                    pink = true; 
                    atk += 3; 
                    maxHp += 3;
                    currHp = maxHp;
                }
            }
            System.out.println("Equipped!");
            arrowInv.add(i);
            inv.remove(i);
            featherSpace--;
        }
        else
        {
            System.out.println("No space for another fletching. Please unequip a feather.");
        }
    }
    public void unequip(Item i)
    {
        switch(i.getColor())
        {
            case "red" -> 
                {
                    red = false;
                    atk--; 
                    revive = false;
                }
                case "orange" -> 
                {
                    orange = false;
                    atk--;
                    maxHp--;
                    currHp = maxHp;
                }
                case "yellow" -> 
                {
                    yellow = false; 
                    atk -= 2;
                }
                case "green" -> 
                {
                    green = false; 
                    atkMult = 1; 
                    accuracy = 1;
                }
                case "blue" -> 
                {
                    blue = false; 
                    atk -= 3;
                    heal = 0;
                }
                case "indigo" -> 
                {
                    indigo = false; 
                    atk -= 6; 
                }
                case "pink" -> 
                {
                    pink = false; 
                    atk -= 3; 
                    maxHp -= 3;
                    currHp = maxHp;
                }
        }
        System.out.println("Unequipped!");
        arrowInv.remove(i);
        inv.add(i);
        featherSpace++;
    }
    // Precondition: only one with that keyword 
    public Item itemFromKey(String s)
    {
        for (Item i: inv)
        {
            if (i.haveSameKeyword(s))
            {
                return i;
            }
        }
        for (Item i: arrowInv) 
        {
            if (i.haveSameKeyword(s))
            {
                return i;
            }
        }
        return null;
    }
    public boolean isInInv(Item item)
    {
        for (Item i: inv)
        {
            if (item != null && item.getId() == i.getId())
            {
                return true;
            }
        }
        return false;
    }
    public boolean isGlow()
    {
        return itemFromKey("gred") != null && itemFromKey("gorange") != null && itemFromKey("gyellow") != null && itemFromKey("ggreen") != null && itemFromKey("gblue") != null && itemFromKey("gindigo") != null && itemFromKey("gpink") != null;
    }
    public boolean isThick()
    {
        return itemFromKey("tred") != null && itemFromKey("torange") != null && itemFromKey("tyellow") != null && itemFromKey("tgreen") != null && itemFromKey("tblue") != null && itemFromKey("tindigo") != null && itemFromKey("tpink") != null;
    }
    public void setMaxHp(int newHp)
    {
        maxHp = newHp;
    }
    public void changeCurrHp(int change)
    {
        currHp += change;
    }
    public void changeAtk(int change)
    {
        atk += change;
    }
    public void changeDef(int change)
    {
        def += change;
    }
    public void setCurrStep(int step)
    {
        currStep = step;
    }
    public void setImage(String[] newImage)
    {
        image = newImage;
    }
    public void setLocation(Location newLocation)
    {
        location = newLocation;
    }
    public void setEnemy(Enemy newEnemy)
    {
        currEnemy = newEnemy;
    }

    public boolean haveSame()
    {
        for (int i = 0; i < inv.size() - 1; i++)
        {
            Item i1 = inv.get(i);
            for (int j = i + 1; j < inv.size(); j++)
            {
                Item i2 = inv.get(j);
                if (i1.getColor() != null && i2.getColor() != null && i1.getColor().equals(i2.getColor()))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void dropSame()
    {
        for (int i = 0; i < inv.size() - 1; i++)
        {
            Item i1 = inv.get(i);
            for (int j = i + 1; j < inv.size(); j++)
            {
                Item i2 = inv.get(j);
                if (i1.getColor() != null && i2.getColor() != null && i1.getColor().equals(i2.getColor()))
                {
                    location.addItem(i1);
                    location.addItem(i2);
                    inv.remove(i1);
                    inv.remove(i2);
                    System.out.println("\nSomething strange occurs when the feathers draw near one another. You drop them and watch as they seem to repel one another.");
                    return;
                }
            }
        }
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
        int out = atk * atkMult;
        return out;
    }
    public int getDef()
    {
        return def;
    }
    public static int getCurrStep()
    {
        return currStep;
    }
    public static int getFeatherSpace()
    {
        return featherSpace;
    }
    public static double getAccuracy()
    {
        return accuracy;
    }
    public static double getHeal()
    {
        return heal;
    }
    public static boolean isRedEquipped()
    {
        return red;
    }
    public static boolean isOrangeEquipped()
    {
        return orange;
    }
    public static boolean isYellowEquipped()
    {
        return yellow;
    }
    public static boolean isGreenEquipped()
    {
        return green;
    }
    public static boolean isBlueEquipped()
    {
        return blue;
    }
    public static boolean isIndigoEquipped()
    {
        return indigo;
    }
    public static boolean isPinkEquipped()
    {
        return pink;
    }
    public boolean isFeatherEquipped(Item i)
    {
        if (i.getColor() != null)
        {
            switch(i.getColor())
                    {
                        case "red" -> 
                            {
                                if (red) 
                                {
                                    return true;
                                }
                            }
                            case "orange" -> 
                            {
                                if (orange) 
                                {
                                    return true;
                                }
                            }
                            case "yellow" -> 
                            {
                                if (yellow) 
                                {
                                    return true;
                                }
                            }
                            case "green" -> 
                            {
                                if (green) 
                                {
                                    return true;
                                }
                            }
                            case "blue" -> 
                            {
                                if (blue) 
                                {
                                    return true;
                                }
                            }
                            case "indigo" -> 
                            {
                                if (indigo) 
                                {
                                    return true;
                                }
                            }
                            case "pink" -> 
                            {
                                if (pink) 
                                {
                                    return true;
                                }
                            }
                    }
        }
        return false;
    }
    public static int getNumEquipped()
    {
        int out = 0;
        if (red)
        {
            out++;
        }
        if (orange)
        {
            out++;
        }
        if (yellow)
        {
            out++;
        }
        if (green)
        {
            out++;
        }
        if (blue)
        {
            out++;
        }
        if (indigo)
        {
            out++;
        }

        if (pink)
        {
            out++;
        }
        return out;
    }
    public static boolean getRevive()
    {
        return revive;
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
    public Location getLoc()
    {
        return location;
    }
    public ArrayList<Item> getInv()
    {
        return new ArrayList<>(inv);
    }
    public Enemy getCurrEnemy()
    {
        return currEnemy;
    }
    public String printStats()
    {
        return "HP: " + currHp + "/" + maxHp + "\nATK: " + getAtk();
    }
    public String printInv()
    {
        StringBuilder out = new StringBuilder(); 
        for (Item i: inv)
        {
            out.append(i.getName()).append("\n");
        }
        return out.length() == 0
            ? "Your inventory is as empty as your mind."
            : out.toString();
    }
}
