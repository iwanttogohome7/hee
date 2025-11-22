package model;
import java.util.Random;

public class BattleSystem {
    private Random random;
    private boolean dodgeBonus;
    
    public BattleSystem() {
        this.random = new Random();
        this.dodgeBonus = false;
    }
    
    // 몬스터 공격력 난수 계산 (80% ~ 115%)
    private int calculateMonsterDamage(int baseAttack) {
        double multiplier = 0.80 + (random.nextDouble() * 0.35); // 0.80 ~ 1.15
        return (int)(baseAttack * multiplier);
    }
    
    // ========== ErrorBird 전투 메서드 ==========

    public int attackErrorBird(Player player, ErrorBird monster) {
        int attackPower = player.getAttack();

        if (dodgeBonus) {
            attackPower = (int)(attackPower * 1.5);
            dodgeBonus = false;
        }
        int damage = attackPower - monster.getDefenseChance(); // 몬스터 방어확률을 방어력으로 사용
        if (damage < 0) damage = 0;
        
        decreaseMonsterHp(monster, damage);
        return damage;
    }

    public int defenseErrorBird(Player player, ErrorBird monster) {
        int defensePower = player.getDefense();
        
        if (dodgeBonus) {
            defensePower = (int)(defensePower * 1.5);
            dodgeBonus = false;
        }
        
        int monsterDamage = calculateMonsterDamage(monster.getAttack()); // 난수 적용
        int result = defensePower - monsterDamage;
        
        if (result > 0) {
            increasePlayerHp(player, result);
            return result;
        } else {
            decreasePlayerHp(player, -result);
            return result;
        }
    }
    
    public boolean dodgeErrorBird(Player player, ErrorBird monster) {
        double dodgeChance = player.getDodgeRate();
        double randomValue = random.nextDouble();
        
        if (randomValue < dodgeChance) {
            dodgeBonus = true;
            return true;
        } else {
            int damage = calculateMonsterDamage(monster.getAttack()); // 난수 적용
            decreasePlayerHp(player, damage);
            return false;
        }
    }
    
    public int errorBirdCounterAttack(Player player, ErrorBird monster) {
        int monsterDamage = calculateMonsterDamage(monster.getAttack()); // 난수 적용
        int damage = monsterDamage - player.getDefense();
        if (damage < 0) damage = 0;
        decreasePlayerHp(player, damage);
        return damage;
    }
    
    // ========== RenderGhost 전투 메서드 ==========
    
    public int attackRenderGhost(Player player, RenderGhost monster) {
        int attackPower = player.getAttack();
        
        if (dodgeBonus) {
            attackPower = (int)(attackPower * 1.5);
            dodgeBonus = false;
        }
        
        int damage = attackPower - monster.getDefenseChance();
        if (damage < 0) damage = 0;
        
        decreaseMonsterHp(monster, damage);
        return damage;
    }

    public int defenseRenderGhost(Player player, RenderGhost monster) {
        int defensePower = player.getDefense();
        
        if (dodgeBonus) {
            defensePower = (int)(defensePower * 1.5);
            dodgeBonus = false;
        }
        
        int monsterDamage = calculateMonsterDamage(monster.getAttack());
        int result = defensePower - monsterDamage;
        
        if (result > 0) {
            increasePlayerHp(player, result);
            return result;
        } else {
            decreasePlayerHp(player, -result);
            return result;
        }
    }
    
    public boolean dodgeRenderGhost(Player player, RenderGhost monster) {
        double dodgeChance = player.getDodgeRate();
        double randomValue = random.nextDouble();
        
        if (randomValue < dodgeChance) {
            dodgeBonus = true;
            return true;
        } else {
            int damage = calculateMonsterDamage(monster.getAttack());
            decreasePlayerHp(player, damage);
            return false;
        }
    }

    public int renderGhostCounterAttack(Player player, RenderGhost monster) {
        int monsterDamage = calculateMonsterDamage(monster.getAttack());
        int damage = monsterDamage - player.getDefense();
        if (damage < 0) damage = 0;
        decreasePlayerHp(player, damage);
        return damage;
    }
    
    // ========== NullParagon 전투 메서드 ==========

    public int attackNullParagon(Player player, NullParagon monster) {
        int attackPower = player.getAttack();
        
        if (dodgeBonus) {
            attackPower = (int)(attackPower * 1.5);
            dodgeBonus = false;
        }
        
        int damage = attackPower - monster.getDefenseChance();
        if (damage < 0) damage = 0;
        
        decreaseMonsterHp(monster, damage);
        return damage;
    }

    public int defenseNullParagon(Player player, NullParagon monster) {
        int defensePower = player.getDefense();
        
        if (dodgeBonus) {
            defensePower = (int)(defensePower * 1.5);
            dodgeBonus = false;
        }
        
        int monsterDamage = calculateMonsterDamage(monster.getAttack());
        int result = defensePower - monsterDamage;
        
        if (result > 0) {
            increasePlayerHp(player, result);
            return result;
        } else {
            decreasePlayerHp(player, -result);
            return result;
        }
    }

    public boolean dodgeNullParagon(Player player, NullParagon monster) {
        double dodgeChance = player.getDodgeRate();
        double randomValue = random.nextDouble();
        
        if (randomValue < dodgeChance) {
            dodgeBonus = true;
            return true;
        } else {
            int damage = calculateMonsterDamage(monster.getAttack());
            decreasePlayerHp(player, damage);
            return false;
        }
    }

    public int nullParagonCounterAttack(Player player, NullParagon monster) {
        int monsterDamage = calculateMonsterDamage(monster.getAttack());
        int damage = monsterDamage - player.getDefense();
        if (damage < 0) damage = 0;
        decreasePlayerHp(player, damage);
        return damage;
    }
    
    // ========== 유틸리티 메서드 ==========

    private void decreasePlayerHp(Player player, int damage) {
        try {
            java.lang.reflect.Field currentHpField = Player.class.getDeclaredField("currentHp");
            currentHpField.setAccessible(true);
            int currentHp = (int) currentHpField.get(player);
            currentHp -= damage;
            if (currentHp < 0) currentHp = 0;
            currentHpField.set(player, currentHp);
        } catch (Exception e) {
            System.err.println("플레이어 HP 감소 실패: " + e.getMessage());
        }
    }
    
    private void increasePlayerHp(Player player, int amount) {
        try {
            java.lang.reflect.Field currentHpField = Player.class.getDeclaredField("currentHp");
            currentHpField.setAccessible(true);
            int currentHp = (int) currentHpField.get(player);
            currentHp += amount;
            int maxHp = player.getMaxHp();
            if (currentHp > maxHp) currentHp = maxHp;
            currentHpField.set(player, currentHp);
        } catch (Exception e) {
            System.err.println("플레이어 HP 증가 실패: " + e.getMessage());
        }
    }
    
    private void decreaseMonsterHp(Object monster, int damage) {
        try {
            java.lang.reflect.Field currentHpField = monster.getClass().getDeclaredField("currentHp");
            currentHpField.setAccessible(true);
            int currentHp = (int) currentHpField.get(monster);
            currentHp -= damage;
            if (currentHp < 0) currentHp = 0;
            currentHpField.set(monster, currentHp);
        } catch (Exception e) {
            System.err.println("몬스터 HP 감소 실패: " + e.getMessage());
        }
    }
    
    // 전투 종료 및 승리 확인 메서드들
    public boolean isBattleEndErrorBird(Player player, ErrorBird monster) { 
        return player.getCurrentHp() <= 0 || monster.getCurrentHp() <= 0;
    }
    
    public boolean isBattleEndRenderGhost(Player player, RenderGhost monster) {
        return player.getCurrentHp() <= 0 || monster.getCurrentHp() <= 0;
    }
    
    public boolean isBattleEndNullParagon(Player player, NullParagon monster) {
        return player.getCurrentHp() <= 0 || monster.getCurrentHp() <= 0;
    }
    
    public boolean isPlayerWinErrorBird(Player player, ErrorBird monster) {
        return monster.getCurrentHp() <= 0 && player.getCurrentHp() > 0;
    }
    
    public boolean isPlayerWinRenderGhost(Player player, RenderGhost monster) {
        return monster.getCurrentHp() <= 0 && player.getCurrentHp() > 0;
    }
    
    public boolean isPlayerWinNullParagon(Player player, NullParagon monster) {
        return monster.getCurrentHp() <= 0 && player.getCurrentHp() > 0;
    }
    
    public void resetDodgeBonus() { this.dodgeBonus = false; }
    public boolean hasDodgeBonus() { return this.dodgeBonus; }
}