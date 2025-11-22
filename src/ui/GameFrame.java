package ui;

import javax.swing.*;
import java.awt.*;

// 앞으로 만들 GameFrame은 Jframe
public class GameFrame extends JFrame {

    // 여러 화면(패널)을 하나씩 보여주는 레이아웃, 화면 전환을 담당
    private CardLayout cardLayout;
    // 여기에 StoryPanel, BattlePanel, EndingPanel 등이 들어감
    private JPanel cardPanel;
    private StoryPanel storyPanel;
    private BattlePanel battlePanel;
    private EndingPanel endingPanel;

    // 각각 화면을 구분하는 이름 태그, 상수로 정해 놓고 어디서나 갖다 쓸 거(하드코딩 안함으로 오타방지)
    public static final String STORY_SCREEN = "story";
    public static final String BATTLE_SCREEN = "battle";
    public static final String ENDING_SCREEN = "ending";

    // 실제 창 만들고 레이아웃 깔고 화면 등록하는 핵심
    public GameFrame() {

        // 창 상단에 뜨는 게임 이름
        setTitle("게임이름 뭐로 할까요");

        // 창 닫기(x) 누르면 완전히 종료되도록 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 창 크기
        setSize(500, 400);

        // 카드 레이아웃, 페널 객체 생성
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 화면 등록
        initScreens();

        // JFrame(GameFrame)안에 cardPanel 붙이기, 실제 내용
        add(cardPanel);

        // 이걸 호출애햐 실제 화면에 보임. swing에선 이거 항상 마지막에 해줘야 함
        setVisible(true);
    }

    private void initScreens() {
        // 스토리 화면
        storyPanel = new StoryPanel(this);
        cardPanel.add(storyPanel, STORY_SCREEN);

        // 전투 화면
        battlePanel = new BattlePanel(this);
        cardPanel.add(battlePanel, BATTLE_SCREEN);

        // 엔딩 화면
        endingPanel = new EndingPanel(this);
        cardPanel.add(endingPanel, ENDING_SCREEN);
    }

    // 화면 전환 메서드
    public void showScreen(String screenName) {
        cardLayout.show(cardPanel, screenName);
        if (BATTLE_SCREEN.equals(screenName)) {
            battlePanel.startBattle();
        } else if (ENDING_SCREEN.equals(screenName)) {
            endingPanel.refreshEnding();
        }
    }
}


/*  

1. Main.java 에서 new GameFrame(); 실행
2. GameFrame() 생성자 안에서 창 크기 설정, 
    cardLayout, cardPanel 만들기,  
    initScreen()로 StoryPannel, BattlePanel, EndingPanel 등록, 
    add(cardPanel), setVisible(true)로 화면 띄움
3. 유저가 StoryPanle에서 "전투 시작"같은 버튼을 클릭
    -> gameFrame.showScreen(GameFrame.BATTLE_SCREEN); 호출
4. BattlePanel로 화면 전환
5. 전투 끝 -> gameFrame.showScreen(GameFrame.ENDING_SCREEN) 호출
6. 엔딩 화면으로 전환

*/
