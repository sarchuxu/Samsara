
public class Combat 
{
    private final Player player; 
    private final Enemy enemy;

    public Combat(Player player, Enemy enemy)
    {
        this.player = player; 
        this.enemy = enemy;
    }

    public void attack()
    {
        enemy.setCurrHp(Math.max(0, -player.getAtk()));
    }

    public void print()
    {
        System.out.println(enemy.getName());
        System.out.println("HP: " + enemy.getCurrHp() + "/" + enemy.getMaxHp());
        System.out.println(enemy.printImage());
        System.out.println("You"); 
        System.out.println("HP: " + player.getCurrHp() + "/" + player.getMaxHp());
        System.out.println("ATK: " + player.getAtk());
    }
    public Enemy getEnemy()
    {
        return enemy;
    }
}
