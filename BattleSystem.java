package model;
import java.util.Random; // 난수 생성을 위한 클래스

public class BattleSystem { // 전투 시스템 클래스
    private Random random; // 난수 생성기
    private boolean dodgeBonus; // 회피 성공 시 다음 공격/방어 50% 증가 확률
    
    public BattleSystem() { // 생성자
        this.random = new Random(); // 난수 생성기 초기화
        this.dodgeBonus = false; // 회피 보너스 초기화
    }
    
    // ========== ErrorBird 전투 메서드 ==========

    public int attackErrorBird(Player player, ErrorBird monster) { // 플레이어가 에러버드를 공격
        int attackPower = player.getAttack(); // 공격력 = 플레이어의 공격력

        if (dodgeBonus) { // 회피 성공 시
        attackPower = (int)(attackPower * 1.5);  // 공격력 50% 증가
        dodgeBonus = false;  // 보너스 초기화
    }
        int damage = attackPower - monster.getDefense(); // 입힐 데미지 계산
        if (damage < 0) damage = 0; // 데미지가 음수일 경우 0으로 설정
        
        decreaseMonsterHp(monster, damage); // 몬스터 체력 감소
        
        return damage; // 입힌 데미지 반환
    }

    public int defenseErrorBird(Player player, ErrorBird monster) { // 플레이어가 에러버드 공격 방어
        int defensePower = player.getDefense(); // 방어력 = 플레이어의 방어력
        
        if (dodgeBonus) { // 회피 성공 시
            defensePower = (int)(defensePower * 1.5); // 방어력 50% 증가
            dodgeBonus = false; // 보너스 초기화
        }
        
        int result = defensePower - monster.getAttack(); // 방어 결과 계산
        
        if (result > 0) { // 방어 성공
            increasePlayerHp(player, result); // 체력 회복
            return result; // 회복한 체력 반환
        } else { // 방어 실패
            decreasePlayerHp(player, -result); // 데미지 입음
            return result; // 받은 데미지 반환
        }
    }
    
    public boolean dodgeErrorBird(Player player, ErrorBird monster) { // 플레이어 에러버드 공격 회피
        double dodgeChance = player.getDodgeRate(); // 플레이어의 회피 확률
        double randomValue = random.nextDouble(); // 0.0 ~ 1.0 사이의 난수 생성
        
        if (randomValue < dodgeChance) { // 회피 성공 시
            dodgeBonus = true; // 다음 공격/방어 보너스 설정
            return true; // 회피 성공 반환
        } else { // 회피 실패
            int damage = monster.getAttack();// 데미지 = 몬스터의 공격력
            decreasePlayerHp(player, damage);// 플레이어 체력 감소
            return false;// 회피 실패 반환
        }
    }
    
    public int errorBirdCounterAttack(Player player, ErrorBird monster) { // 에러버드 반격
        int damage = monster.getAttack() - player.getDefense(); // 데미지 계산
        if (damage < 0) damage = 0; // 데미지가 음수일 경우 0으로 설정
        decreasePlayerHp(player, damage); // 플레이어 체력 감소
        return damage; // 입힌 데미지 반환
    }
    
    // ========== RenderGhost 전투 메서드 ==========
    
    public int attackRenderGhost(Player player, RenderGhost monster) { // 플레이어가 렌더고스트 공격
        int attackPower = player.getAttack(); // 공격력 = 플레이어의 공격력
        
        if (dodgeBonus) { // 회피 성공 시
            attackPower = (int)(attackPower * 1.5); // 공격력 50% 증가
            dodgeBonus = false; // 보너스 초기화
        }
        
        int damage = attackPower - monster.getDefense(); // 입힐 데미지 계산
        if (damage < 0) damage = 0; // 데미지가 음수일 경우 0으로 설정
        
        decreaseMonsterHp(monster, damage); // 몬스터 체력 감소
        
        return damage; // 입힌 데미지 반환
    }
    
    /**
     * 렌더고스트 방어
     */
    public int defenseRenderGhost(Player player, RenderGhost monster) { // 플레이어가 렌더고스트 공격 방어
        int defensePower = player.getDefense(); // 방어력 = 플레이어의 방어력
        
        if (dodgeBonus) { // 회피 성공 시
            defensePower = (int)(defensePower * 1.5); // 방어력 50% 증가
            dodgeBonus = false; // 보너스 초기화
        }
         
        int result = defensePower - monster.getAttack(); // 방어 결과 계산
        
        if (result > 0) { // 방어 성공
            increasePlayerHp(player, result); // 체력 회복
            return result; // 회복한 체력 반환
        } else { // 방어 실패
            decreasePlayerHp(player, -result); // 데미지 입음
            return result; // 받은 데미지 반환
        }
    }
    
    public boolean dodgeRenderGhost(Player player, RenderGhost monster) { // 플레이어 렌더고스트 공격 회피
        double dodgeChance = player.getDodgeRate(); // 플레이어의 회피 확률
        double randomValue = random.nextDouble(); // 0.0 ~ 1.0 사이의 난수 생성
        
        if (randomValue < dodgeChance) { // 회피 성공 시
            dodgeBonus = true; // 다음 공격/방어 보너스 설정
            return true; // 회피 성공 반환
        } else { // 회피 실패
            int damage = monster.getAttack(); // 데미지 = 몬스터의 공격력
            decreasePlayerHp(player, damage); // 플레이어 체력 감소
            return false; // 회피 실패 반환
        }
    }

    public int renderGhostCounterAttack(Player player, RenderGhost monster) { // 렌더고스트 반격
        int damage = monster.getAttack() - player.getDefense(); // 데미지 계산
        if (damage < 0) damage = 0; // 데미지가 음수일 경우 0으로 설정
        decreasePlayerHp(player, damage); // 플레이어 체력 감소
        return damage; // 입힌 데미지 반환
    }
    
    // ========== NullParagon 전투 메서드 ==========

    public int attackNullParagon(Player player, NullParagon monster) { // 플레이어가 널파라곤 공격
        int attackPower = player.getAttack(); // 공격력 = 플레이어의 공격력
        
        if (dodgeBonus) { // 회피 성공 시
            attackPower = (int)(attackPower * 1.5); // 공격력 50% 증가
            dodgeBonus = false; // 보너스 초기화
        }
        
        int damage = attackPower - monster.getDefense(); // 입힐 데미지 계산
        if (damage < 0) damage = 0; // 데미지가 음수일 경우 0으로 설정
        
        decreaseMonsterHp(monster, damage); // 몬스터 체력 감소
        
        return damage; // 입힌 데미지 반환
    }

    public int defenseNullParagon(Player player, NullParagon monster) { // 플레이어가 널파라곤 공격 방어
        int defensePower = player.getDefense(); // 방어력 = 플레이어의 방어력
        
        if (dodgeBonus) { // 회피 성공 시
            defensePower = (int)(defensePower * 1.5); // 방어력 50% 증가
            dodgeBonus = false; // 보너스 초기화
        }
        
        int result = defensePower - monster.getAttack(); // 방어 결과 계산
        
        if (result > 0) { // 방어 성공
            increasePlayerHp(player, result); // 체력 회복
            return result; // 회복한 체력 반환
        } else { // 방어 실패
            decreasePlayerHp(player, -result); // 데미지 입음
            return result; // 받은 데미지 반환
        }
    }

    public boolean dodgeNullParagon(Player player, NullParagon monster) { // 플레이어 널파라곤 공격 회피
        double dodgeChance = player.getDodgeRate(); // 플레이어의 회피 확률
        double randomValue = random.nextDouble(); // 0.0 ~ 1.0 사이의 난수 생성
        
        if (randomValue < dodgeChance) { // 회피 성공 시
            dodgeBonus = true;  // 다음 공격/방어 보너스 설정
            return true; // 회피 성공 반환
        } else { // 회피 실패
            int damage = monster.getAttack(); // 데미지 = 몬스터의 공격력
            decreasePlayerHp(player, damage); // 플레이어 체력 감소
            return false; // 회피 실패 반환
        }
    }

    public int nullParagonCounterAttack(Player player, NullParagon monster) { // 널파라곤 반격
        int damage = monster.getAttack() - player.getDefense(); // 데미지 계산
        if (damage < 0) damage = 0; // 데미지가 음수일 경우 0으로 설정
        decreasePlayerHp(player, damage); // 플레이어 체력 감소
        return damage; // 입힌 데미지 반환
    }
    
    // ========== 유틸리티 메서드 ==========

    private void decreasePlayerHp(Player player, int damage) { // 플레이어 체력 감소 (리플렉션 사용)
        try {  // 리플렉션을 사용하여 private 필드에 접근
            java.lang.reflect.Field currentHpField = Player.class.getDeclaredField("currentHp"); // currentHp 필드 가져오기
            currentHpField.setAccessible(true); // 접근 허용
            int currentHp = (int) currentHpField.get(player); // 현재 체력 가져오기
            currentHp -= damage; // 데미지만큼 체력 감소
            if (currentHp < 0) currentHp = 0; // 체력이 0 미만이면 0으로 설정
            currentHpField.set(player, currentHp); // 변경된 체력 설정
        } catch (Exception e) {  // 예외 처리
            System.err.println("플레이어 HP 감소 실패: " + e.getMessage()); // 오류 메시지 출력
        }
    }
    
    private void increasePlayerHp(Player player, int amount) { // 플레이어 체력 증가 (리플렉션 사용)
        try { // 리플렉션을 사용하여 private 필드에 접근
            java.lang.reflect.Field currentHpField = Player.class.getDeclaredField("currentHp"); // currentHp 필드 가져오기
            currentHpField.setAccessible(true); // 접근 허용
            int currentHp = (int) currentHpField.get(player); // 현재 체력 가져오기
            currentHp += amount; // 체력 증가
            int maxHp = player.getMaxHp();   // 최대 체력 가져오기
            if (currentHp > maxHp) currentHp = maxHp; // 체력이 최대 체력 초과하지 않도록 설정
            currentHpField.set(player, currentHp); // 변경된 체력 설정
        } catch (Exception e) { // 예외 처리
            System.err.println("플레이어 HP 증가 실패: " + e.getMessage()); // 오류 메시지 출력
        }
    }
    
    private void decreaseMonsterHp(Object monster, int damage) { // 몬스터 체력 감소 (리플렉션 사용)
        try { // 리플렉션을 사용하여 private 필드에 접근
            java.lang.reflect.Field currentHpField = monster.getClass().getDeclaredField("currentHp"); // currentHp 필드 가져오기
            currentHpField.setAccessible(true); // 접근 허용
            int currentHp = (int) currentHpField.get(monster); // 현재 체력 가져오기
            currentHp -= damage; // 데미지만큼 체력 감소
            if (currentHp < 0) currentHp = 0; // 체력이 0 미만이면 0으로 설정
            currentHpField.set(monster, currentHp); // 변경된 체력 설정
        } catch (Exception e) { // 예외 처리
            System.err.println("몬스터 HP 감소 실패: " + e.getMessage()); // 오류 메시지 출력
        }
    }
    
    /**
     * 전투 종료 확인 (ErrorBird)
     */
    public boolean isBattleEndErrorBird(Player player, ErrorBird monster) { 
        return player.getCurrentHp() <= 0 || monster.getCurrentHp() <= 0;// 전투 종료 조건 확인
    }
    
    /**
     * 전투 종료 확인 (RenderGhost)
     */
    public boolean isBattleEndRenderGhost(Player player, RenderGhost monster) {
        return player.getCurrentHp() <= 0 || monster.getCurrentHp() <= 0; // 전투 종료 조건 확인
    }
    
    /**
     * 전투 종료 확인 (NullParagon)
     */
    public boolean isBattleEndNullParagon(Player player, NullParagon monster) {
        return player.getCurrentHp() <= 0 || monster.getCurrentHp() <= 0; // 전투 종료 조건 확인
    }
    
    /**
     * 승리 여부 확인 (ErrorBird)
     */
    public boolean isPlayerWinErrorBird(Player player, ErrorBird monster) {
        return monster.getCurrentHp() <= 0 && player.getCurrentHp() > 0; // 승리 조건 확인
    }
    
    /**
     * 승리 여부 확인 (RenderGhost)
     */
    public boolean isPlayerWinRenderGhost(Player player, RenderGhost monster) {
        return monster.getCurrentHp() <= 0 && player.getCurrentHp() > 0; // 승리 조건 확인
    }
    
    /**
     * 승리 여부 확인 (NullParagon)
     */
    public boolean isPlayerWinNullParagon(Player player, NullParagon monster) {
        return monster.getCurrentHp() <= 0 && player.getCurrentHp() > 0; // 승리 조건 확인
    }
    
    /**
     * 회피 보너스 초기화
     */
    public void resetDodgeBonus() {
        this.dodgeBonus = false; // 회피 보너스 초기화
    }
    
    /**
     * 회피 보너스 상태 확인
     */
    public boolean hasDodgeBonus() {
        return this.dodgeBonus; // 회피 보너스 상태 반환
    }
}