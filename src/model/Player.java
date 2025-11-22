package model;

public class Player {

    private int maxHp = 70;
    private int attack = 15;
    private int defense = 10;           // 방어력 추가
    private double dodgeRate = 0.3;     // 회피 확률 (30%)
    private int currentHp = maxHp;
    private int stage = 1;              // 현재 스테이지

    public void printStatus() {
        System.out.println("===== 플레이어 정보 =====");
        System.out.println("체력: " + currentHp + "/" + maxHp);
        System.out.println("공격력: " + attack);
        System.out.println("방어력: " + defense);
        System.out.println("회피 확률: " + (int)(dodgeRate * 100) + "%");
        System.out.println("========================");
    }

    // --- Getter ---
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public double getDodgeRate() { return dodgeRate; }
    public int getCurrentHp() { return currentHp; }
    public int getStage() { return stage; }

    // --- Setter ---
    public void setStage(int stage) { this.stage = stage; }
}