package model;

public class Player {

    private int maxHp = 100;
    private int attack = 10;
    private int defense = 10;
    private double dodgeRate = 0.25; // 25%

    private int currentHp = maxHp;

    public Player() {
        
    }

    // --- Getter --- 객체의 값을 외부에서 읽을 수 있게 하는 함수들
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public double getDodgeRate() { return dodgeRate; }
    public int getCurrentHp() { return currentHp; }
}
