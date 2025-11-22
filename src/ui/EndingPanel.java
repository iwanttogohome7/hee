package ui;

import util.GameData;

import javax.swing.*;
import java.awt.*;

public class EndingPanel extends JPanel {

    // GameFrame 참조
    private GameFrame gameFrame;

    // 엔딩 제목
    private JLabel titleLabel;

    // 엔딩 본문 메시지
    private JLabel messageLabel;

    // 버튼들
    private JButton restartButton;   // 처음부터 다시
    private JButton exitButton;      // 게임 종료

    // 생성자
    public EndingPanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        // BorderLayout 사용 → 위(NORTH), 중간(CENTER), 아래(SOUTH)로 나누기 쉽다
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // ============================================================
        // < 상단 제목 >

        // 기본 제목
        titleLabel = new JLabel("게임 종료", SwingConstants.CENTER); 
        // 폰트 설정
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 28)); 
        // 글자 색
        titleLabel.setForeground(Color.WHITE); 
        // 라벨 주변 여백
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10)); 
        // 화면 위쪽(NORTH)에 배치
        add(titleLabel, BorderLayout.NORTH); 

        // ============================================================        
        // < 중앙에 표시될 엔딩 메시지 >
        // 기본 메시지
        messageLabel = new JLabel("플레이해 주셔서 감사합니다.", SwingConstants.CENTER);

        // 폰트 설정
        messageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));

        // 글자 색
        messageLabel.setForeground(Color.LIGHT_GRAY);

        // 텍스트 주변 여백
        messageLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 중앙(CENTER)에 붙이기
        add(messageLabel, BorderLayout.CENTER);

        // ============================================================
        // < 하단 버튼 영역 >
        JPanel buttonPanel = new JPanel();

        // 버튼 배경색
        buttonPanel.setBackground(Color.DARK_GRAY);

        // 여백 설정
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        // “처음부터 다시” 버튼
        restartButton = new JButton("처음부터 다시");
        restartButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));

        // 버튼 누르면 → 게임 데이터 초기화 + 스토리 화면으로 이동
        restartButton.addActionListener(e -> {
            GameData.reset(); // → 플레이어/몬스터/스테이지 정보 초기화 (동료가 구현)
            gameFrame.showScreen(GameFrame.STORY_SCREEN);  // → 처음 화면(스토리)로 이동
        });


        // “게임 종료” 버튼
        exitButton = new JButton("게임 종료");
        exitButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));

        // 버튼 누르면 → 프로그램 완전 종료
        exitButton.addActionListener(e -> System.exit(0));


        // 버튼 패널에 버튼 추가
        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);

        // SOUTH(아래) 영역에 버튼 패널 부착
        add(buttonPanel, BorderLayout.SOUTH);

        refreshEnding();
    }

    public void refreshEnding() {
        titleLabel.setText(GameData.getEndingTitle());
        messageLabel.setText(GameData.getEndingMessage());
    }
}
/*

1. BattlePanel에서 전투가 끝난다. -> gameFrame.showScreen(GameFrame.ENDING_SCREEN)
2. GameFrame의 CardLayout이 ENDING_SCREEN에 등록된 EndingPanel을 보여줌
3. 처음부터 다시 -> GameData.reset -> gameFrame.showScreen(GameFrame.STORY_SCREEN)
    게임 종료 -> System.exit(0);
    
 */
