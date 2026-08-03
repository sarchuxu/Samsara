import java.util.*;

public class Game 
{
    String[] bowKeywords, arrowKeywords, mapKeywords, tRedKeywords, compassKeywords, penKeywords, gRedKeywords, gOrangeKeywords, tOrangeKeywords, gYellowKeywords, tYellowKeywords, gGreenKeywords, tGreenKeywords, gBlueKeywords, tBlueKeywords, musicKeywords, musicKeyKeywords, gIndigoKeywords, tIndigoKeywords, gPinkKeywords, tPinkKeywords, scrollKeywords;
    Item bow, arrow, map, tRed, compass, pen, gRed, gOrange, tOrange, gYellow, tYellow, gGreen, tGreen, gBlue, tBlue, music, musicKey, gIndigo, tIndigo, gPink, tPink, scroll;
    ArrayList<Item> allItems;

    String[] templePath, gRedRoomPath, gOrangeRoomPath, tOrangeRoomPath, gYellowRoomPath, tYellowRoomPath, gGreenRoomPath, tGreenRoomPath, gBlueRoomPath, tBlueRoomPath, gIndigoRoomPath, tIndigoRoomPath, gPinkRoomPath, tPinkRoomPath;
    ArrayList<Item> templeItems, gRedRoomItems, gOrangeRoomItems, tOrangeRoomItems, gYellowRoomItems, tYellowRoomItems, gGreenRoomItems, tGreenRoomItems, gBlueRoomItems, tBlueRoomItems, gIndigoRoomItems, tIndigoRoomItems, gPinkRoomItems, tPinkRoomItems;
    Location temple, gRedRoom, gOrangeRoom, tOrangeRoom, gYellowRoom, tYellowRoom, gGreenRoom, tGreenRoom, gBlueRoom, tBlueRoom, gIndigoRoom, tIndigoRoom, gPinkRoom, tPinkRoom;
    public static Location maze;

    Player player;
    Enemy currEnemy;

    public Game() 
    {
        initialize();
    }
    public static void main (String[]args)
    {
        Game game = new Game(); 
        game.startGame();
    }

    private void startGame()
    {
        GameState state = GameState.DEFAULT;
        populate();
        player = new Player(10, temple);
        String input = "";
        System.out.println("Welcome to Samsara!\nType 'list commands' for options.\n");
        player.getLoc().initLoc();
        try (Scanner sc = new Scanner(System.in)) 
        {
            while(!input.equals("quit") && !input.equals("exit"))
            {
                System.out.print("> ");
                input = sc.nextLine().toLowerCase();
                System.out.println();
                switch (state)
                {
                    case DEFAULT -> processCommand(input);
                    case FIGHT -> fightCommand(input);
                }
            }
        }
    }

    public enum GameState
    {
        DEFAULT, 
        FIGHT
    }

    private void processCommand(String input)
    {
        if (input.isBlank()) return;
        String[] command = input.split(" ");
        String verb = command[0]; 
        String obj = "";
        if (command.length > 1)
        {
            obj = input.substring(verb.length() + 1); 
        }
        switch (verb)
        {
            case "list" -> 
            {
                if (obj.equals("commands") || obj.equals("command"))
                System.out.println("Available commands:\nGo: north/northeast/n/ne\nExamine: room/item\nTake: object\nInventory\nHelp");
            }
            case "inventory", "inv" -> 
            {
                System.out.println("Your inventory: \n" + player.printInv());
            }
            case "help" -> 
            {
                System.out.println(player.getLoc().getHelp());
            }
            case "solution" -> 
            {
                System.out.println("The path to the next room is: " + player.getLoc().printPath());
            }
            case "go", "travel" -> 
            {
                if (command.length < 2)
                {
                    System.out.println("You must specify a direction!");
                }
                else
                {
                    player.go(command[1]);
                }
            }
            case "n", "ne", "e", "se", "s", "sw", "w", "nw", "north", "northeast", "east", "southeast", "south", "southwest", "west", "northwest" -> player.go(verb);
            case "take", "get", "grab" -> 
            { 
                if (command.length < 2)
                {
                    System.out.println("You must specify what to take!");
                }
                else
                {
                    player.take(obj);
                }
            }
            case "drop", "throw", "discard" -> 
            {
                if (command.length < 2)
                {
                    System.out.println("You must specify what to drop!");
                }
                else
                {
                    player.drop(obj);
                }
            }
            case "x", "examine", "look" -> 
            {
                if (command.length < 2)
                {
                    Location l = player.getLoc();
                    System.out.println(l.getName().toUpperCase() + "\n" + l.getDescription());
                    if (!l.getItems().isEmpty())
                    {
                        System.out.println("\n" + l.getItemHolder() + l.printItems());
                    }
                }
                else
                {
                    switch (obj)
                    {
                        case "me", "myself", "i" -> 
                        {
                            if(player.getInv().contains(itemFromKey("yellow feather")))
                            {
                                player.printStats();
                            }
                            else
                            {
                                if (player.getInv().contains(itemFromKey("green feather")))
                                {
                                    System.out.println("You are a beautiful traveler, lost in this infinite world. If you collect enough feathers, will they become wings once more?");
                                }
                                else
                                {
                                    System.out.println("You are a traveler, lost in this infinite world.");
                                }
                            }
                            
                        }
                        default -> 
                            look(obj);
                    }
                }
            }
            case "eat", "lick", "kick", "break" -> 
            {
                String[] notRecogMsg = {"Pardon?", "That would not be advisable", "Excuse me?", "I'm not sure why you would do that", "I wouldn't do that if I were you", "I don't think that's a wise course of action", "Why?!"};
                int rand = (int) (Math.random() * notRecogMsg.length);
                System.out.println(notRecogMsg[rand]);
            }
            case "equip" -> 
            {
                if (command.length < 2)
                {
                    System.out.println("You must specify what to equip!");
                }
                else
                {
                    Item i = itemFromKey(obj);
                    if (i != null && player.isInInv(i) && i.getColor() != null)
                    {
                        switch (i.getColor())
                        {
                            case "red" -> 
                            {
                                if (!Player.isRedEquipped())
                                {
                                    player.equip(i);
                                }
                                else
                                {
                                    System.out.println("The " + i.getColor() + " feather is already equipped!");
                                }
                            }
                            case "orange" -> 
                            {
                                if (!Player.isOrangeEquipped())
                                {
                                    player.equip(i);
                                }
                                else
                                {
                                    System.out.println("The " + i.getColor() + " feather is already equipped!");
                                }
                            }
                            case "yellow" -> 
                            {
                                if (!Player.isYellowEquipped())
                                {
                                    player.equip(i);
                                }
                                else
                                {
                                    System.out.println("The " + i.getColor() + " feather is already equipped!");
                                }
                            }
                            case "green" -> 
                            {
                                if (!Player.isGreenEquipped())
                                {
                                    player.equip(i);
                                }
                                else
                                {
                                    System.out.println("The " + i.getColor() + " feather is already equipped!");
                                }
                            }
                            case "blue" -> 
                            {
                                if (!Player.isBlueEquipped())
                                {
                                    player.equip(i);
                                }
                                else
                                {
                                    System.out.println("The " + i.getColor() + " feather is already equipped!");
                                }
                            }
                            case "indigo" -> 
                            {
                                if (!Player.isIndigoEquipped())
                                {
                                    player.equip(i);
                                }
                                else
                                {
                                    System.out.println("The " + i.getColor() + " feather is already equipped!");
                                }
                            }
                            case "pink" -> 
                            {
                                if (!Player.isPinkEquipped())
                                {
                                    player.equip(i);
                                }
                                else
                                {
                                    System.out.println("The " + i.getColor() + " feather is already equipped!");
                                }
                            }
                        }
                    }
                    else
                    {
                        System.out.println("You can't equip that!");
                    }
                }
            }
            case "unequip" -> 
            {
                if (command.length < 2)
                {
                    System.out.println("You must specify what to unequip!");
                }
                else
                {
                    Item i = itemFromKey(obj);
                    if (i != null && !player.isInInv(i) && i.getColor() != null)
                    {
                        switch (i.getColor())
                        {
                            case "red" -> 
                            {
                                if (Player.isRedEquipped())
                                {
                                    player.unequip(i);
                                }
                                else
                                {
                                    System.out.println("Okay, but it wasn't equipped to start with.");
                                }
                            }
                            case "orange" -> 
                            {
                                if (Player.isOrangeEquipped())
                                {
                                    player.unequip(i);
                                }
                                else
                                {
                                    System.out.println("Okay, but it wasn't equipped to start with.");
                                }
                            }
                            case "yellow" -> 
                            {
                                if (Player.isYellowEquipped())
                                {
                                    player.unequip(i);
                                }
                                else
                                {
                                    System.out.println("Okay, but it wasn't equipped to start with.");
                                }
                            }
                            case "green" -> 
                            {
                                if (Player.isGreenEquipped())
                                {
                                    player.unequip(i);
                                }
                                else
                                {
                                    System.out.println("Okay, but it wasn't equipped to start with.");
                                }
                            }
                            case "blue" -> 
                            {
                                if (Player.isBlueEquipped())
                                {
                                    player.unequip(i);
                                }
                                else
                                {
                                    System.out.println("Okay, but it wasn't equipped to start with.");
                                }
                            }
                            case "indigo" -> 
                            {
                                if (Player.isIndigoEquipped())
                                {
                                    player.unequip(i);
                                }
                                else
                                {
                                    System.out.println("Okay, but it wasn't equipped to start with.");
                                }
                            }
                            case "pink" -> 
                            {
                                if (Player.isPinkEquipped())
                                {
                                    player.unequip(i);
                                }
                                else
                                {
                                    System.out.println("Okay, but it wasn't equipped to start with.");
                                }
                            }
                        }
                    }
                    else
                    {
                        System.out.println("You can't equip that!");
                    }
                }
            }
            default -> 
            { 
                String[] notRecogMsg = {"What do you mean?", "I don't know what that means", "Unknown command. For a list of possible commands, type 'list commands'", "Come again?", "I'm not sure that's possible"};
                int rand = (int) (Math.random() * notRecogMsg.length);
                System.out.println(notRecogMsg[rand]);
            }
        }
    }
    private void fightCommand(String input)
    {
        Combat combat = new Combat(player, currEnemy);
        if (input.isBlank()) return;
        switch (input)
        {
            case "1", "attack" -> combat.attack();
        }
    }

    private void look(String obj)
    {
        Item i = player.itemFromKey(obj);
        if (player.isInInv(i))
        {
            System.out.println(i.getDescription(player.getLoc()));
        }
        else if (player.getLoc().isInLoc(player.getLoc().itemFromKey(obj)))
        {
            System.out.println(player.getLoc().itemFromKey(obj).getDescription(player.getLoc()));
        }
        else  
        {
            System.out.println("There is nothing remarkable to see.");
        }
    }
    private void populate()
    {
        temple.addItem(scroll);
        temple.addItem(bow);
        temple.addItem(arrow);
        temple.addItem(map); 
        temple.addItem(tRed);
        temple.setNextLoc(gRedRoom);

        gRedRoom.addItem(compass);
        gRedRoom.addItem(pen); 
        gRedRoom.addItem(gRed);
        gRedRoom.setNextLoc(gOrangeRoom);
        gRedRoom.setOppLoc(tOrangeRoom);

        gOrangeRoom.addItem(gOrange);
        gOrangeRoom.setNextLoc(gYellowRoom);
        gOrangeRoom.setOppLoc(tYellowRoom);

        tOrangeRoom.addItem(tOrange);
        tOrangeRoom.setNextLoc(gYellowRoom);
        tOrangeRoom.setOppLoc(tYellowRoom);

        gYellowRoom.addItem(gYellow);
        gYellowRoom.setNextLoc(gGreenRoom);
        gYellowRoom.setOppLoc(tGreenRoom);

        tYellowRoom.addItem(tYellow);
        tYellowRoom.setNextLoc(gGreenRoom);
        tYellowRoom.setOppLoc(tGreenRoom);

        gGreenRoom.addItem(gGreen);
        gGreenRoom.setNextLoc(gBlueRoom);
        gGreenRoom.setOppLoc(tBlueRoom);

        tGreenRoom.addItem(tGreen);
        tGreenRoom.setNextLoc(gBlueRoom);
        tGreenRoom.setOppLoc(tBlueRoom);

        gBlueRoom.addItem(gBlue);
        gBlueRoom.setNextLoc(gIndigoRoom);
        gBlueRoom.setOppLoc(tIndigoRoom);

        tBlueRoom.addItem(tBlue);
        tBlueRoom.setNextLoc(gIndigoRoom);
        tBlueRoom.setOppLoc(tIndigoRoom);

        gIndigoRoom.addItem(music);
        gIndigoRoom.addItem(musicKey);
        gIndigoRoom.addItem(gIndigo);
        gIndigoRoom.setNextLoc(gPinkRoom);
        gIndigoRoom.setOppLoc(tPinkRoom);

        tIndigoRoom.addItem(music);
        tIndigoRoom.addItem(musicKey);
        tIndigoRoom.addItem(tIndigo);
        tIndigoRoom.setNextLoc(gPinkRoom);
        tIndigoRoom.setOppLoc(tPinkRoom);

        gPinkRoom.addItem(gPink);
        gPinkRoom.setNextLoc(temple);

        tPinkRoom.addItem(tPink);
        tPinkRoom.setOppLoc(temple);
    }
    private void initialize()
    {
        /* Items */
        bowKeywords = new String[]{"bow", "wooden bow", "sturdy bow"};
        arrowKeywords = new String[]{"arrow", "arrowshaft", "straight arrow", "straight arrowshaft"};
        mapKeywords = new String[]{"map"};
        tRedKeywords = new String[]{"feather", "red feather", "thick red feather", "trf", "tred"};
        compassKeywords = new String[]{"compass"};
        penKeywords = new String[]{"pen", "stylus"};
        gRedKeywords = new String[]{"feather", "red feather", "glowing red feather", "grf", "gred"};
        gOrangeKeywords = new String[]{"feather", "orange feather", "glowing orange feather", "gof", "gorange"};
        tOrangeKeywords = new String[]{"feather", "orange feather", "thick orange feather", "tof", "torange"};
        gYellowKeywords = new String[]{"feather", "yellow feather", "glowing yellow feather", "gyf", "gyellow"};
        tYellowKeywords = new String[]{"feather", "yellow feather", "thick yellow feather", "tyf", "tyellow"};
        gGreenKeywords = new String[]{"feather", "green feather", "glowing green feather", "ggf", "ggreen"};
        tGreenKeywords = new String[]{"feather", "green feather", "thick green feather", "tgf", "tgreen"};
        gBlueKeywords = new String[]{"feather", "blue feather", "glowing blue feather", "gbf", "gblue"};
        tBlueKeywords = new String[]{"feather", "blue feather", "thick blue feather", "tbf", "tblue"};
        musicKeywords = new String[]{"music", "score", "music score"};
        musicKeyKeywords = new String[]{"music key", "paper", "paper slip", "slip of paper"};
        gIndigoKeywords = new String[]{"feather", "indigo feather", "glowing indigo feather", "gif", "gindigo"};
        tIndigoKeywords = new String[]{"feather", "indigo feather", "thick indigo feather", "tif", "tindigo"};
        gPinkKeywords = new String[]{"feather", "pink feather", "glowing pink feather", "gpf", "gpink"};
        tPinkKeywords = new String[]{"feather", "pink feather", "thick pink feather", "tpf", "tpink"};
        scrollKeywords = new String[]{"scroll"};

        bow = new Item(1, null, bowKeywords, "A sturdy bow", "A wooden bow. Sturdy and faintly familiar in your hands.");
        arrow = new Item(2, null, arrowKeywords, "An arrowshaft", "A straight arrowshaft. There are three spaces for fletchings.");
        map = new Item(3, null, mapKeywords, "A map", "A map. It's more like a blank page. Not very useful without a writing implement.");
        tRed = new Item(4, "red", tRedKeywords, "A thick red feather", "A thick phoenix feather. Using this to fletch an arrow would grant ATK +1 and one revive.");
        compass = new Item(5, null, compassKeywords, "A compass", "A circular compass. It fits comfortably in the palm of your hand. The needle spins erratically, as lost as you are.");
        pen = new Item(6, null, penKeywords, "A stylus", "A simple stylus. You are tempted to doodle on your map. Too bad there's no eraser.");
        gRed = new Item(7, "red", gRedKeywords, "A glowing red feather", "A glowing phoenix feather. Using this to fletch an arrow would grant ATK +1 and one revive.");
        gOrange = new Item(8, "orange", gOrangeKeywords, "A glowing orange feather", "A glowing parrot feather. Using this to fletch an arrow would grant ATK +1 and HP +1.");
        tOrange = new Item(9, "orange", tOrangeKeywords, "A thick orange feather", "A thick parrot feather. Using this to fletch an arrow would grant ATK +1 and HP +1.");
        gYellow = new Item(10, "yellow", gYellowKeywords, "A glowing yellow feather", "A glowing canary feather. Using this to fletch an arrow would grant ATK +2 and allow the user to see useful information during battle.");
        tYellow = new Item(11, "yellow", tYellowKeywords, "A thick yellow feather", "A thick canary feather. Using this to fletch an arrow would grant ATK +2 and allow the user to see useful information during battle.");
        gGreen = new Item(12, "green", gGreenKeywords, "A glowing green feather", "A glowing peacock feather. Using this to fletch an arrow would double one's attack but add a 50% chance for each shot to miss.");
        tGreen = new Item(13, "green", tGreenKeywords, "A thick green feather", "A thick peacock feather. Using this to fletch an arrow would double one's attack but add a 50% chance for each shot to miss.");
        gBlue = new Item(14, "blue", gBlueKeywords, "A glowing blue feather", "A glowing owl feather. Using this to fletch an arrow would grant ATK +3 and heal one by half of the damage one does.");
        tBlue = new Item(15, "blue", tBlueKeywords, "A thick blue feather", "A thick owl feather. Using this to fletch an arrow would grant ATK +3 and heal one by half of the damage one does.");
        music = new Item(16, null, musicKeywords, "A sheet of music score", "A musical score. The notes on the page are A, A, G, A, rest, C, A, G.");
        musicKey = new Item(17, null, musicKeyKeywords, "A slip of paper covered with musical notation", "A slip of paper with an A major scale and a rest on it. There is a tiny line of words: 'it starts with a'. A what, exactly?");
        gIndigo = new Item(18, "indigo", gIndigoKeywords, "A glowing indigo feather", "A glowing crow feather. Using this to fletch an arrow would grant ATK +6.");
        tIndigo = new Item(19, "indigo", tIndigoKeywords, "A thick indigo feather", "A thick crow feather. Using this to fletch an arrow would grant ATK +6.");
        gPink = new Item(20, "pink", gPinkKeywords, "A glowing pink feather", "A glowing flamingo feather. Using this to fletch an arrow would grant ATK +3 and HP +3.");
        tPink = new Item(21, "pink", tPinkKeywords, "A thick pink feather", "A thick flamingo feather. Using this to fletch an arrow would grant ATK +3 and HP +3. ");
        scroll = new Item(22, null, scrollKeywords, "An ancient scroll", "You are in SAMSARA, a shifting maze of rooms. In order to progress to the next room, you must go in a certain pattern of directions. Once you leave a room, there is no guarantee you can find your way back, even if you retrace your steps. Collect 7 feathers and return to the start.");
        
        allItems = new ArrayList<> (Arrays.asList(bow, arrow, map, tRed, compass, pen, gRed, gOrange, tOrange, gYellow, tYellow, gGreen, tGreen, gBlue, tBlue, music, musicKey, gIndigo, tIndigo, gPink, tPink, scroll));
    
        /* Rooms */
        templePath = new String[]{};
        gRedRoomPath = new String[]{"n", "ne", "e", "se", "s", "sw", "w", "nw"};
        gOrangeRoomPath = new String[]{"n", "n", "e", "se", "sw", "w", "e", "se", "sw", "w"};
        tOrangeRoomPath = new String[]{"n", "n", "e", "se", "sw", "w", "e", "se", "sw", "w"};
        gYellowRoomPath = new String[]{"s", "s", "s"};
        tYellowRoomPath = new String[]{"s", "s", "s"};
        gGreenRoomPath = new String[]{"n", "ne", "ne", "nw", "nw", "ne", "e", "sw"};
        tGreenRoomPath = new String[]{"n", "ne", "ne", "nw", "nw", "ne", "e", "sw"};
        gBlueRoomPath = new String[]{"ne", "se", "se", "ne", "nw", "sw", "sw", "nw"};
        tBlueRoomPath = new String[]{"ne", "se", "se", "ne", "nw", "sw", "sw", "nw"};
        gIndigoRoomPath = new String[]{"n", "n", "w", "n", "nw", "e", "n", "w"};
        tIndigoRoomPath = new String[]{"n", "n", "w", "n", "nw", "e", "n", "w"};
        gPinkRoomPath = new String[]{"s", "sw", "w", "nw", "n", "ne", "e", "se"};
        tPinkRoomPath = new String[]{"s", "sw", "w", "nw", "n", "ne", "e", "se"};
    
        templeItems = new ArrayList<>();
        gRedRoomItems = new ArrayList<>();
        gOrangeRoomItems = new ArrayList<>();
        tOrangeRoomItems = new ArrayList<>();
        gYellowRoomItems = new ArrayList<>();
        tYellowRoomItems = new ArrayList<>();
        gGreenRoomItems = new ArrayList<>();
        tGreenRoomItems = new ArrayList<>();
        gBlueRoomItems = new ArrayList<>();
        tBlueRoomItems = new ArrayList<>();
        gIndigoRoomItems = new ArrayList<>();
        tIndigoRoomItems = new ArrayList<>();
        gPinkRoomItems = new ArrayList<>();
        tPinkRoomItems = new ArrayList<>();

        temple = new Location("Temple of Ouroboros", "You are in an ancient room of crumbling rock. You have the faint sense that you've been here before.", "Maybe you should look at the world outside this room?", "On a stone slab, you see: ", templeItems, templePath);
        gRedRoom = new Location("Vipers' Cove", "You see a giant hall of glittering green rock. Your reflections surround you, tinted in an emerald hue. Behind every wall comes a slithering sound.", "The feather may point you to its kin, should you examine it closely.", "A table of glassy jade contains: ", gRedRoomItems, gRedRoomPath);
        gOrangeRoom = new Location("Bed of Basilisks", "Before you is an abundance of braziers, burning blue. You stand at the bottom left bound of the batch, looking at the twice bouncing shape made of eight braziers.", "Travel in the shape of a 'B' made of eight points, starting from the lower right corner.", "Below a brazier, you behold: ", gOrangeRoomItems, gOrangeRoomPath);
        tOrangeRoom = new Location("Bifurcated Bed of Basilisks", "Before you is an abundance of braziers, burning blue. You stand at the bottom left bound of the batch, looking at the twice bouncing shape made of eight braziers.", "Travel in the shape of a 'B' made of eight points, starting from the lower right corner. Then again, do you have to?", "Below a brazier, you behold: ", tOrangeRoomItems, tOrangeRoomPath);
        gYellowRoom = new Location("Gauntlet", "An imposing archway stands before you, and try as you might, you cannot see beyond it. Along the arch, the letters “S S S” are carved. A warning from the maze's serpentine occupants?", "Go south and battle your way to the next room.", "At the foot of the arch rests:", gYellowRoomItems, gYellowRoomPath);
        tYellowRoom = new Location("Mirrored Gauntlet", "An imposing archway stands before you, and try as you might, you cannot see beyond it. Along the arch, the letters “S S S” are carved. A warning from the maze's serpentine occupants?", "Go south and battle your way to the next room. Then again, do you have to?", "At the foot of the arch rests:", tYellowRoomItems, tYellowRoomPath);
        gGreenRoom = new Location("Clockwise Stars", "The enormous room seems empty, but for nine lights arranged like a constellation. Inscriptions glow and fade along the walls, and you catch two words: Serpens Caput. If this is a snake, you would be standing directly to the south of its tail.", " Travel clockwise in the shape of the Serpens Caput constellation, starting from its tail.", "Floating in the center of the room is: ", gGreenRoomItems, gGreenRoomPath);
        tGreenRoom = new Location("Reflected Clockwise Stars", "The enormous room seems empty, but for nine lights arranged like a constellation. Inscriptions glow and fade along the walls, and you catch two words: Serpens Caput. If this is a snake, you would be standing directly to the south of its tail. Then again, do you have to?", " Travel clockwise in the shape of the Serpens Caput constellation, starting from its tail. Then again, do you have to?", "Floating in the center of the room is: ", tGreenRoomItems, tGreenRoomPath);
        gBlueRoom = new Location("Twin Diamond Scales", "You feel that there are walls around, but wherever you look, there is only emptiness. The way the ground continues in the soft orange light is almost… infinite, looping back on itself. You look at your arrow, which points to the northeast.", "Go in the shape of the infinity symbol starting from the northeast.", "Suddenly, you see in your hand:", gBlueRoomItems, gBlueRoomPath);
        tBlueRoom = new Location("Infinite Scales", "You feel that there are walls around, but wherever you look, there is only emptiness. The way the ground continues in the soft orange light is almost… infinite, looping back on itself. You look at your arrow, which points to the northeast.", "Go in the shape of the infinity symbol starting from the northeast. Then again, do you have to?", "Suddenly, you see in your hand:", tBlueRoomItems, tBlueRoomPath);
        gIndigoRoom = new Location("Menagerie", "A large array of cages are haphazardly stacked around the room, all unlocked and empty. From the distance, you hear a simple tune, repeated over and over again.", "Wrap the music key around your compass.", "On a strangely out-of-place music stand, you see:", gIndigoRoomItems, gIndigoRoomPath);
        tIndigoRoom = new Location("Menagerie...?", "A large array of cages are haphazardly stacked around the room, all unlocked and empty. From the distance, you hear a simple tune, repeated over and over again.", "Wrap the music key around your compass. Then again, do you have to?", "On a strangely out-of-place music stand, you see:", tIndigoRoomItems, tIndigoRoomPath);
        gPinkRoom = new Location("Beginning of the End", "There are piles of tiny feathers of all colors of the rainbow strewn around giant nests. However, you feel a strange sense of icy death from them. Only one feather still feels warm and ready to fly.", "Follow the feather's guidance.", "Resting in a shattered incubator lies: ", gPinkRoomItems, gPinkRoomPath);
        tPinkRoom = new Location("End of the Beginning", "There are piles of tiny feathers of all colors of the rainbow strewn around giant nests. However, you feel a strange sense of icy death from them. Only one feather still feels warm and ready to fly.", "Follow the feather's guidance. Then again, do you have to?", "Resting in a shattered incubator lies: ", tPinkRoomItems, tPinkRoomPath);
        maze = new Location("Winding Maze", "You hear the walls shifting around you, but when you turn to look, there is only a cold wall. You are certain you cannot find your way back to the previous room, if it still exists.", "", "", new ArrayList<>(), new String[0]);
    }
    public static Location getMaze()
    {
        return maze;
    }
    public Item itemFromKey(String s)
    {
        for (Item i: allItems)
        {
            if (i.haveSameKeyword(s))
            {
                return i;
            }
        }
        return null;
    }
}