package ui;

import model.BattleSystem;
import model.ErrorBird;
import model.NullParagon;
import model.Player;
import model.RenderGhost;
import util.GameData;

import javax.swing.*;
import java.awt.*;

public class BattlePanel extends JPanel {

    private enum EnemyType {
        ERROR_BIRD("에러버드"),
        RENDER_GHOST("렌더고스트"),
        NULL_PARAGON("널파라곤");

        private final String displayName;

        EnemyType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private final GameFrame gameFrame;

    private Player player;
    private BattleSystem battleSystem;
    private EnemyType enemyType;

    private JLabel stageLabel;
    private JLabel playerHpLabel;
    private JLabel playerAtkLabel;
    private JLabel playerDefLabel;
    private JLabel playerDodgeLabel;

    private JLabel enemyNameLabel;
    private JLabel enemyHpLabel;
    private JLabel enemyAtkLabel;
    private JLabel enemyDefLabel;

    private JTextArea logArea;

    private JButton attackButton;
    private JButton defendButton;
    private JButton dodgeButton;

    public BattlePanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.BLACK);
        initComponents();
    }

    private void initComponents() {
        JLabel titleLabel = new JLabel("전투", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        stageLabel = new JLabel("", SwingConstants.CENTER);
        stageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        stageLabel.setForeground(Color.LIGHT_GRAY);

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setOpaque(false);
        topPanel.add(titleLabel);
        topPanel.add(stageLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel playerPanel = createPlayerPanel();
        JPanel enemyPanel = createEnemyPanel();

        JPanel statusPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        statusPanel.setOpaque(false);
        statusPanel.add(playerPanel);
        statusPanel.add(enemyPanel);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setBackground(Color.DARK_GRAY);
        logArea.setForeground(Color.WHITE);

        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("전투 로그"));

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);
        centerPanel.add(statusPanel, BorderLayout.NORTH);
        centerPanel.add(logScroll, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        attackButton = new JButton("공격");
        defendButton = new JButton("방어");
        dodgeButton = new JButton("회피");

        attackButton.addActionListener(e -> handleAttack());
        defendButton.addActionListener(e -> handleDefense());
        dodgeButton.addActionListener(e -> handleDodge());

        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(Color.DARK_GRAY);
        actionPanel.add(attackButton);
        actionPanel.add(defendButton);
        actionPanel.add(dodgeButton);

        add(actionPanel, BorderLayout.SOUTH);
    }

    private JPanel createPlayerPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.setBackground(new Color(20, 20, 20));
        panel.setBorder(BorderFactory.createTitledBorder("플레이어"));

        playerHpLabel = createStatusLabel();
        playerAtkLabel = createStatusLabel();
        playerDefLabel = createStatusLabel();
        playerDodgeLabel = createStatusLabel();

        panel.add(playerHpLabel);
        panel.add(playerAtkLabel);
        panel.add(playerDefLabel);
        panel.add(playerDodgeLabel);
        return panel;
    }

    private JPanel createEnemyPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.setBackground(new Color(20, 20, 20));
        panel.setBorder(BorderFactory.createTitledBorder("적"));

        enemyNameLabel = createStatusLabel();
        enemyHpLabel = createStatusLabel();
        enemyAtkLabel = createStatusLabel();
        enemyDefLabel = createStatusLabel();

        panel.add(enemyNameLabel);
        panel.add(enemyHpLabel);
        panel.add(enemyAtkLabel);
        panel.add(enemyDefLabel);
        return panel;
    }

    private JLabel createStatusLabel() {
        JLabel label = new JLabel();
        label.setForeground(Color.WHITE);
        label.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        return label;
    }

    public void startBattle() {
        player = GameData.getPlayer();
        battleSystem = GameData.getBattleSystem();
        battleSystem.resetDodgeBonus();
        loadEnemyForStage();
        updateStats();
        logArea.setText("");
        logStageIntro();
        setButtonsEnabled(true);
    }

    private void loadEnemyForStage() {
        int stage = Math.max(1, Math.min(GameData.getMaxStage(), player.getStage()));
        if (stage == 1) {
            enemyType = EnemyType.ERROR_BIRD;
        } else if (stage == 2) {
            enemyType = EnemyType.RENDER_GHOST;
        } else {
            enemyType = EnemyType.NULL_PARAGON;
        }
    }

    private void handleAttack() {
        int damage;
        switch (enemyType) {
            case ERROR_BIRD -> damage = battleSystem.attackErrorBird(player, GameData.getErrorBird());
            case RENDER_GHOST -> damage = battleSystem.attackRenderGhost(player, GameData.getRenderGhost());
            case NULL_PARAGON -> damage = battleSystem.attackNullParagon(player, GameData.getNullParagon());
            default -> damage = 0;
        }

        appendLog(String.format("플레이어의 공격! %s에게 %d 피해.", enemyType.getDisplayName(), damage));

        if (!isEnemyDefeated()) {
            int counterDamage = performCounterAttack();
            if (counterDamage > 0) {
                appendLog(String.format("%s의 반격! %d 피해를 받음.", enemyType.getDisplayName(), counterDamage));
            } else {
                appendLog(String.format("%s의 반격이 막혔다.", enemyType.getDisplayName()));
            }
        }

        endTurn();
    }

    private void handleDefense() {
        int result;
        switch (enemyType) {
            case ERROR_BIRD -> result = battleSystem.defenseErrorBird(player, GameData.getErrorBird());
            case RENDER_GHOST -> result = battleSystem.defenseRenderGhost(player, GameData.getRenderGhost());
            case NULL_PARAGON -> result = battleSystem.defenseNullParagon(player, GameData.getNullParagon());
            default -> result = 0;
        }

        if (result >= 0) {
            appendLog(String.format("방어 성공! 체력 %d 회복.", result));
        } else {
            appendLog(String.format("방어 실패! %d 피해를 받음.", -result));
        }

        endTurn();
    }

    private void handleDodge() {
        boolean success;
        switch (enemyType) {
            case ERROR_BIRD -> success = battleSystem.dodgeErrorBird(player, GameData.getErrorBird());
            case RENDER_GHOST -> success = battleSystem.dodgeRenderGhost(player, GameData.getRenderGhost());
            case NULL_PARAGON -> success = battleSystem.dodgeNullParagon(player, GameData.getNullParagon());
            default -> success = false;
        }

        if (success) {
            appendLog("회피 성공! 다음 행동이 강화됩니다.");
        } else {
            appendLog(String.format("회피 실패! %s의 공격(%d 피해).",
                    enemyType.getDisplayName(), getEnemyAttackPower()));
        }

        endTurn();
    }

    private int performCounterAttack() {
        return switch (enemyType) {
            case ERROR_BIRD -> battleSystem.errorBirdCounterAttack(player, GameData.getErrorBird());
            case RENDER_GHOST -> battleSystem.renderGhostCounterAttack(player, GameData.getRenderGhost());
            case NULL_PARAGON -> battleSystem.nullParagonCounterAttack(player, GameData.getNullParagon());
        };
    }

    private boolean isEnemyDefeated() {
        return switch (enemyType) {
            case ERROR_BIRD -> GameData.getErrorBird().getCurrentHp() <= 0;
            case RENDER_GHOST -> GameData.getRenderGhost().getCurrentHp() <= 0;
            case NULL_PARAGON -> GameData.getNullParagon().getCurrentHp() <= 0;
        };
    }

    private void endTurn() {
        updateStats();
        evaluateBattleResult();
    }

    private void evaluateBattleResult() {
        if (!isBattleEnded()) {
            return;
        }

        if (player.getCurrentHp() <= 0) {
            setButtonsEnabled(false);
            GameData.setEnding("패배", enemyType.getDisplayName() + "에게 패배했습니다.");
            gameFrame.showScreen(GameFrame.ENDING_SCREEN);
            return;
        }

        if (isPlayerWinner()) {
            if (player.getStage() >= GameData.getMaxStage()) {
                setButtonsEnabled(false);
                GameData.setEnding("승리", "모든 버그를 처치하고 현실로 돌아갑니다!");
                gameFrame.showScreen(GameFrame.ENDING_SCREEN);
            } else {
                player.setStage(player.getStage() + 1);
                battleSystem.resetDodgeBonus();
                appendLog("승리! 다음 스테이지로 이동합니다.");
                loadEnemyForStage();
                updateStats();
                logStageIntro();
            }
        }
    }

    private boolean isBattleEnded() {
        return switch (enemyType) {
            case ERROR_BIRD -> battleSystem.isBattleEndErrorBird(player, GameData.getErrorBird());
            case RENDER_GHOST -> battleSystem.isBattleEndRenderGhost(player, GameData.getRenderGhost());
            case NULL_PARAGON -> battleSystem.isBattleEndNullParagon(player, GameData.getNullParagon());
        };
    }

    private boolean isPlayerWinner() {
        return switch (enemyType) {
            case ERROR_BIRD -> battleSystem.isPlayerWinErrorBird(player, GameData.getErrorBird());
            case RENDER_GHOST -> battleSystem.isPlayerWinRenderGhost(player, GameData.getRenderGhost());
            case NULL_PARAGON -> battleSystem.isPlayerWinNullParagon(player, GameData.getNullParagon());
        };
    }

    private int getEnemyAttackPower() {
        return switch (enemyType) {
            case ERROR_BIRD -> GameData.getErrorBird().getAttack();
            case RENDER_GHOST -> GameData.getRenderGhost().getAttack();
            case NULL_PARAGON -> GameData.getNullParagon().getAttack();
        };
    }

    private void updateStats() {
        stageLabel.setText(String.format("스테이지 %d", player.getStage()));
        updatePlayerStats();
        updateEnemyStats();
    }

    private void updatePlayerStats() {
        playerHpLabel.setText(String.format("체력: %d / %d", player.getCurrentHp(), player.getMaxHp()));
        playerAtkLabel.setText(String.format("공격력: %d", player.getAttack()));
        playerDefLabel.setText(String.format("방어력: %d", player.getDefense()));
        playerDodgeLabel.setText(String.format("회피율: %d%%", (int) (player.getDodgeRate() * 100)));
    }

    private void updateEnemyStats() {
        switch (enemyType) {
            case ERROR_BIRD -> {
                ErrorBird enemy = GameData.getErrorBird();
                enemyNameLabel.setText("이름: 에러버드");
                enemyHpLabel.setText(String.format("체력: %d / %d", enemy.getCurrentHp(), enemy.getMaxHp()));
                enemyAtkLabel.setText(String.format("공격력: %d", enemy.getAttack()));
                enemyDefLabel.setText(String.format("방어력: %d", enemy.getDefense()));
            }
            case RENDER_GHOST -> {
                RenderGhost enemy = GameData.getRenderGhost();
                enemyNameLabel.setText("이름: 렌더고스트");
                enemyHpLabel.setText(String.format("체력: %d / %d", enemy.getCurrentHp(), enemy.getMaxHp()));
                enemyAtkLabel.setText(String.format("공격력: %d", enemy.getAttack()));
                enemyDefLabel.setText(String.format("방어력: %d", enemy.getDefense()));
            }
            case NULL_PARAGON -> {
                NullParagon enemy = GameData.getNullParagon();
                enemyNameLabel.setText("이름: 널파라곤");
                enemyHpLabel.setText(String.format("체력: %d / %d", enemy.getCurrentHp(), enemy.getMaxHp()));
                enemyAtkLabel.setText(String.format("공격력: %d", enemy.getAttack()));
                enemyDefLabel.setText(String.format("방어력: %d", enemy.getDefense()));
            }
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        attackButton.setEnabled(enabled);
        defendButton.setEnabled(enabled);
        dodgeButton.setEnabled(enabled);
    }

    private void appendLog(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private void logStageIntro() {
        appendLog(String.format("스테이지 %d 전투 시작! %s 등장!",
                player.getStage(), enemyType.getDisplayName()));
    }
}
