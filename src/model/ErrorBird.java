package model;

public class ErrorBird {


   private int maxHp = 20;
   private int attack = 5;
   private int defenseChance = 25;

   private int currentHp = maxHp;

   public ErrorBird() {
    }
   // 외부에서 스텟 불러 올 때 사용
   public void printStatus() {
        System.out.println("===== 에러버드 정보 =====");
        System.out.println("체력: " + currentHp + "/" + maxHp);
        System.out.println("공격력: " + attack);
        System.out.println("방어 확률: " + defenseChance + "%");
        System.out.println("========================");
    }         
    // --- Getter ---객체의 값을 외부에서 읽을 수 있게 하는 함수
   public int getMaxHp() { return maxHp; }
   public int getAttack() { return attack; }
   public int getCurrentHp() { return currentHp; }
   public int getDefenseChance() { return defenseChance; }
}

