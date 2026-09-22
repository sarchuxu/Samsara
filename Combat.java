
public class Combat 
{
    private final Player player; 
    private final Enemy enemy;
    private final Location loc;

    public Combat(Player player, Enemy enemy, Location loc)
    {
        this.player = player; 
        this.enemy = enemy;
        this.loc = loc;
    }

    public void attack(Player p)
    {
        enemy.changeCurrHp(Math.min(0, -player.getAtk()));
        if (enemy.getCurrHp() <= 1)
        {
            p.setEnemy(null);
            loc.incEnemyDefeated();
        }
    }
    public void flee()
    {
        enemy.changeCurrHp(enemy.getMaxHp());
    }
    public void print()
    {
        System.out.println(enemy.getName());
        System.out.println("HP: " + enemy.getCurrHp() + "/" + enemy.getMaxHp());
        System.out.println(enemy.printImage());
        System.out.println();
        System.out.println("You"); 
        System.out.println("HP: " + player.getCurrHp() + "/" + player.getMaxHp());
        System.out.println("ATK: " + player.getAtk());
        System.out.println(player.printImage());
    }
    public Enemy getEnemy()
    {
        return enemy;
    }
}
