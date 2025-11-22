package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StoryPanel extends JPanel {

    // GameFrame 참조
    private GameFrame gameFrame;

    // 스토리 텍스트 보여주는 영역
    private JLabel storyLabel;

    // 버튼, 눌렀을 때 다른 화면으로 넘어가는 트리거
    private JButton nextButton;

    private List<String> storyLines;

    // 현재 몇 번째 문장을 보여주고 있는지
    private int currentIndex = 0;

    public StoryPanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        // 레이아웃을 BorderLayout()으로 하겠다. (NORTH, CENTER, SOUTH, EAST, WEST 5개 영역에 컴포넌트 붙일 수 있음) 
        setLayout(new BorderLayout());
        
        // 이 패널의 기본 배경색 : 검정
        setBackground(Color.BLACK);

        // < 상단 제목 설정 >
        // 제목 컴포넌트 설정 (가운데 정렬 기능)
        JLabel titleLabel = new JLabel("게임 제목 뭐로 할까요", SwingConstants.CENTER);
        
        // 제목 폰트 설정
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        
        // 제목 글자 색 설정 : 흰 색
        titleLabel.setForeground(Color.WHITE);
        
        // 라벨 주변 여백 설정
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 패널의 위쪽(NORTH)에 붙임
        add(titleLabel, BorderLayout.NORTH); 


        // < 스토리 영역 >
        // 여러 줄의 텍스트 보여줄 컴포넌트 설정
        storyLabel = new JLabel("", SwingConstants.CENTER);

        // 글자색 설정
        storyLabel.setForeground(Color.WHITE);

        // 글자 폰트 설정
        storyLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        // 라벨 주변에 20px 여백 넣기
        storyLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 패널 중앙(CENTER)에 붙임
        add(storyLabel, BorderLayout.CENTER);

        // 스토리 문장 넣는 곳
        storyLines = new ArrayList<>();
        storyLines.add("게임학과·컴퓨터공학과 1학년인 주인공.");
        storyLines.add("하루 종일 자신이 개발하던 게임 속 버그들과 싸우다 지쳐 잠든다.");
        storyLines.add("눈을 뜬 순간, 익숙한 화면이 펼쳐진다.");
        storyLines.add("여기는… 내가 만들던 그 게임 속 세계?");
        storyLines.add("이곳의 몬스터들은 내가 해결하지 못한 버그들이 형체를 얻은 존재들이다.");
        storyLines.add("모든 스테이지를 클리어해야만 현실로 돌아갈 수 있다.");
        storyLines.add("지금, 버그 헌터의 모험이 시작된다…!");

        // 처음 문장 표시
        storyLabel.setText(storyLines.get(currentIndex));

        // < 다음 버튼 >
        JPanel buttonPanel = new JPanel();

        // 배경색
        buttonPanel.setBackground(Color.DARK_GRAY);

        // 텍스트
        nextButton = new JButton("다음");

        // 폰트
        nextButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));

        // 버튼을 눌르면 showNextLine() 메서드 실행
        nextButton.addActionListener(e -> showNextLine());

        // buttonPanel에 nextButton 붙이기
        buttonPanel.add(nextButton);

        // 이 StoryPanel 전체에서 하단(SOUTH)에 buttonPanel 붙임
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // ===== 문장 넘기기 메서드 =====
    private void showNextLine() {

        currentIndex++;

        if (currentIndex < storyLines.size()) {
            // 다음 문장 출력
            storyLabel.setText(storyLines.get(currentIndex));
        }
        else {
            // 스토리 끝 → 전투 화면 이동
            gameFrame.showScreen(GameFrame.BATTLE_SCREEN);
        }
    }
}           



/*
 
1. GameFrame에서 new StoryPanel(this)해서 생성 -> "story"키로 카드에 등록
2. 게임 시작 시 "story" 화면 먼저 보이게 함.
3. 유저가 스토리 읽고 "다음" 버튼 클릭, 마지막 다음 버튼 누르면
4. 버튼의 액션 리스너에서 gameFrame.showScreen(GameFrame.BATTLE_SCRREN)
5. CardLayout이 "battle" 화면(BattlePanel)을 보여줌
  
 */