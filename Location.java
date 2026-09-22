import java.util.ArrayList; 

public class Location 
{
    private int step;
    private int revStep;
    private String name; 
    private String description; 
    private String help;
    private final String itemHolder;
    private Location nextLoc;
    private Location oppLoc;
    private final ArrayList<Item> items;
    private String[] path;
    private int[] enemyStep; 
    private Enemy[] enemies; 
    private boolean[] enemyDefeated;

    public Location(String name, String description, String help, String itemHolder, ArrayList<Item> items, String[] path, int[] enemyStep, Enemy[] enemies)
    {
        this.name = name; 
        this.description = description; 
        this.help = help; 
        this.itemHolder = itemHolder;
        this.items = new ArrayList<>(items);
        this.path = path.clone();
        step = 0;
        revStep = 0;
        this.enemyStep = enemyStep;
        this.enemies = enemies;
        enemyDefeated = new boolean[enemyStep.length];
    }

    public void removeItem(Item item)
    {
        if (item == null) return;
        items.removeIf(i -> i.getId() == item.getId());
    }
    public void addItem(Item item)
    {
        items.add(item);
    }
    public boolean isInLoc(Item item)
    {
        if (item == null) return false;
        for (Item i: items)
        {
            if (item.getId() == i.getId())
            {
                return true;
            }
        }
        return false;
    }
    public Item itemFromKey(String s)
    {
        for (Item i: items)
        {
            if (i.haveSameKeyword(s))
            {
                return i;
            }
        }
        return null;
    }
    public String printItems()
    {
        StringBuilder out = new StringBuilder("\n"); 
        for (Item i: items)
        {
            out.append(i.getName()).append("\n");
        }
        return out.toString();
    }
    public int getStepNum()
    {
        return Math.max(step, revStep);
    }
    public Location travelPath(String dir, Player p)
    {
       if (step < path.length && dir.equals(path[step]))
       {
           step++;
           if (step == path.length)
           {
               step = 0;
               revStep = 0;
               return nextLoc;
           }
           else
           {
                p.setEnemy(getPlayerEnemy(step, p));
           }
       }
       else
       {
           step = 0;
       }
       if (revStep < path.length && dir.equals(oppositeDir(path[revStep])))
       {
           revStep++;
           if (revStep == path.length)
           {
                step = 0;   
                revStep = 0;
                return oppLoc;
           }
           else
           {
                p.setEnemy(getPlayerEnemy(revStep, p));
           }
       }
       else
       {
           revStep = 0;
       }
       return null;
    }
    public Enemy getPlayerEnemy(int step, Player p)
    {
        for (int i: enemyStep)
        {
            if (step == i)
            {
                for (int j = 0; j < enemyDefeated.length; j++)
                {
                    if (enemyDefeated[j] == false)
                    {
                        return enemies[j];
                    }
                }
            }
        }
        return null;
    }
    public static String oppositeDir(String s)
    {
        String out;
        out = switch (s)
        {
            case "n" -> "s";
            case "ne" -> "sw";
            case "e" -> "w";
            case "se" -> "nw";
            case "s" -> "n";
            case "sw" -> "ne";
            case "w" -> "e";
            case "nw" -> "se";
            default -> "";
        };
        return out;
    }
    public void incEnemyDefeated() 
    {
        for (int i = 0; i < enemyDefeated.length; i++)
            {
                if (enemyDefeated[i] == false)
                {
                    enemyDefeated[i] = true;
                    return;
                }
            }
    }
    public void initLoc()
    {
        System.out.println(name.toUpperCase() + "\n" + description);
        if (!items.isEmpty())
        {
            System.out.println("\n" + itemHolder + printItems());
        }
    }
    public void clearItems()
    {
        items.clear();
    }

    public void setName(String newName)
    {
        name = newName;
    }
    public void setDescription(String newDescript)
    {
        description = newDescript;
    }
    public void setHelp(String newHelp)
    {
        help = newHelp;
    }
    public void setNextLoc(Location loc)
    {
        nextLoc = loc;
    }
    public void setOppLoc(Location loc)
    {
        oppLoc = loc;
    }
    public void setPath(String[] path)
    {
        this.path = path.clone();
    }
    public void setEnemyStep(int[] enemyStep)
    {
        this.enemyStep = enemyStep.clone();
    }
    public void setEnemies(Enemy[] enemies)
    {
        this.enemies = enemies.clone();
    }
    public void setEnemyDefeated(boolean[] enemyDefeated)
    {
        this.enemyDefeated = enemyDefeated.clone();
    }
    
    public String getName()
    {
        return name;
    }
    public String getDescription()
    {
        return description;
    }
    public String getHelp()
    {
        return help;
    }
    public String getItemHolder()
    {
        return itemHolder;
    }
    public Location getNextLoc()
    {
        return nextLoc; 
    }
    public Location getOppLoc()
    {
        return oppLoc;
    }
    public ArrayList<Item> getItems()
    {
        return new ArrayList<>(items);
    }
    public String[] getPath()
    {
        return path.clone();
    }
    public int[] getEnemyStep()
    {
        return enemyStep;
    }
    public Enemy[] getEnemies()
    {
        return enemies;
    }
    public boolean[] getEnemyDefeated()
    {
        return enemyDefeated;
    }
    public String printPath()
    {
        StringBuilder out = new StringBuilder(); 
        for (String s: path)
        {
            out.append(s).append(" ");
        }
        return out.length() == 0
            ? "move in any direction (n, ne, e, se, s, sw, w, or nw)"
            : out.toString();
    }
}
import java.util.ArrayList; 

public class Location 
{
    private int step;
    private int revStep;
    private String name; 
    private String description; 
    private String help;
    private final String itemHolder;
    private Location nextLoc;
    private Location oppLoc;
    private final ArrayList<Item> items;
    private String[] path;
    private int[] enemyStep; 
    private Enemy[] enemies; 
    private boolean[] enemyDefeated;

    public Location(String name, String description, String help, String itemHolder, ArrayList<Item> items, String[] path, int[] enemyStep, Enemy[] enemies)
    {
        this.name = name; 
        this.description = description; 
        this.help = help; 
        this.itemHolder = itemHolder;
        this.items = new ArrayList<>(items);
        this.path = path.clone();
        step = 0;
        revStep = 0;
        this.enemyStep = enemyStep;
        this.enemies = enemies;
        enemyDefeated = new boolean[enemyStep.length];
    }

    public void removeItem(Item item)
    {
        if (item == null) return;
        items.removeIf(i -> i.getId() == item.getId());
    }
    public void addItem(Item item)
    {
        items.add(item);
    }
    public boolean isInLoc(Item item)
    {
        if (item == null) return false;
        for (Item i: items)
        {
            if (item.getId() == i.getId())
            {
                return true;
            }
        }
        return false;
    }
    public Item itemFromKey(String s)
    {
        for (Item i: items)
        {
            if (i.haveSameKeyword(s))
            {
                return i;
            }
        }
        return null;
    }
    public String printItems()
    {
        StringBuilder out = new StringBuilder("\n"); 
        for (Item i: items)
        {
            out.append(i.getName()).append("\n");
        }
        return out.toString();
    }
    public int getStepNum()
    {
        return Math.max(step, revStep);
    }
    public Location travelPath(String dir, Player p)
    {
       if (step < path.length && dir.equals(path[step]))
       {
           step++;
           if (step == path.length)
           {
               step = 0;
               revStep = 0;
               return nextLoc;
           }
           else
           {
                p.setEnemy(getPlayerEnemy(step, p));
           }
       }
       else
       {
           step = 0;
       }
       if (revStep < path.length && dir.equals(oppositeDir(path[revStep])))
       {
           revStep++;
           if (revStep == path.length)
           {
                step = 0;   
                revStep = 0;
                return oppLoc;
           }
           else
           {
                p.setEnemy(getPlayerEnemy(revStep, p));
           }
       }
       else
       {
           revStep = 0;
       }
       return null;
    }
    public Enemy getPlayerEnemy(int step, Player p)
    {
        for (int i: enemyStep)
        {
            if (step == i)
            {
                for (int j = 0; j < enemyDefeated.length; j++)
                {
                    if (enemyDefeated[j] == false)
                    {
                        return enemies[j];
                    }
                }
            }
        }
        return null;
    }
    public static String oppositeDir(String s)
    {
        String out;
        out = switch (s)
        {
            case "n" -> "s";
            case "ne" -> "sw";
            case "e" -> "w";
            case "se" -> "nw";
            case "s" -> "n";
            case "sw" -> "ne";
            case "w" -> "e";
            case "nw" -> "se";
            default -> "";
        };
        return out;
    }
    public void incEnemyDefeated() 
    {
        for (int i = 0; i < enemyDefeated.length; i++)
            {
                if (enemyDefeated[i] == false)
                {
                    enemyDefeated[i] = true;
                    return;
                }
            }
    }
    public void initLoc()
    {
        System.out.println(name.toUpperCase() + "\n" + description);
        if (!items.isEmpty())
        {
            System.out.println("\n" + itemHolder + printItems());
        }
    }
    public void clearItems()
    {
        items.clear();
    }

    public void setName(String newName)
    {
        name = newName;
    }
    public void setDescription(String newDescript)
    {
        description = newDescript;
    }
    public void setHelp(String newHelp)
    {
        help = newHelp;
    }
    public void setNextLoc(Location loc)
    {
        nextLoc = loc;
    }
    public void setOppLoc(Location loc)
    {
        oppLoc = loc;
    }
    public void setPath(String[] path)
    {
        this.path = path.clone();
    }
    public void setEnemyStep(int[] enemyStep)
    {
        this.enemyStep = enemyStep.clone();
    }
    public void setEnemies(Enemy[] enemies)
    {
        this.enemies = enemies.clone();
    }
    public void setEnemyDefeated(boolean[] enemyDefeated)
    {
        this.enemyDefeated = enemyDefeated.clone();
    }
    
    public String getName()
    {
        return name;
    }
    public String getDescription()
    {
        return description;
    }
    public String getHelp()
    {
        return help;
    }
    public String getItemHolder()
    {
        return itemHolder;
    }
    public Location getNextLoc()
    {
        return nextLoc; 
    }
    public Location getOppLoc()
    {
        return oppLoc;
    }
    public ArrayList<Item> getItems()
    {
        return new ArrayList<>(items);
    }
    public String[] getPath()
    {
        return path.clone();
    }
    public int[] getEnemyStep()
    {
        return enemyStep;
    }
    public Enemy[] getEnemies()
    {
        return enemies;
    }
    public boolean[] getEnemyDefeated()
    {
        return enemyDefeated;
    }
    public String printPath()
    {
        StringBuilder out = new StringBuilder(); 
        for (String s: path)
        {
            out.append(s).append(" ");
        }
        return out.length() == 0
            ? "move in any direction (n, ne, e, se, s, sw, w, or nw)"
            : out.toString();
    }
}
