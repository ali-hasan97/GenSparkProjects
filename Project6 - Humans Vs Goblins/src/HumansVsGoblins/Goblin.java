public class Goblin extends Humanoid {

    public int attack(Human human) {
        int goblinAttack = (int) ((Math.random() * 100) / human.getArmor());
        human.setHealth(human.getHealth() - goblinAttack);
        if (human.getHealth() < 0) human.setHealth(0);
        System.out.println(human);
        return goblinAttack;
    }
}
