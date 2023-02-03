public class Human extends Humanoid {
    public int attack(Goblin goblin) {
        int humanAttack = (int) ((Math.random() * 100)/goblin.getArmor());
        goblin.setHealth(goblin.getHealth() - humanAttack);
        if (goblin.getHealth() < 0) goblin.setHealth(0);
        return humanAttack;
    }
}
