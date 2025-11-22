package util;

import model.BattleSystem;
import model.ErrorBird;
import model.NullParagon;
import model.Player;
import model.RenderGhost;

/**
 * 전역에서 공유하는 게임 상태를 관리한다.
 */
public final class GameData {

    private static final int MAX_STAGE = 3;

    private static Player player;
    private static BattleSystem battleSystem;
    private static ErrorBird errorBird;
    private static RenderGhost renderGhost;
    private static NullParagon nullParagon;

    private static String endingTitle;
    private static String endingMessage;

    static {
        reset();
    }

    private GameData() {
    }

    /**
     * 게임을 처음 상태로 되돌린다.
     */
    public static void reset() {
        player = new Player();
        battleSystem = new BattleSystem();
        errorBird = new ErrorBird();
        renderGhost = new RenderGhost();
        nullParagon = new NullParagon();
        endingTitle = "게임 종료";
        endingMessage = "플레이해 주셔서 감사합니다.";
    }

    public static Player getPlayer() {
        return player;
    }

    public static BattleSystem getBattleSystem() {
        return battleSystem;
    }

    public static ErrorBird getErrorBird() {
        return errorBird;
    }

    public static RenderGhost getRenderGhost() {
        return renderGhost;
    }

    public static NullParagon getNullParagon() {
        return nullParagon;
    }

    public static int getMaxStage() {
        return MAX_STAGE;
    }

    public static void setEnding(String title, String message) {
        endingTitle = title;
        endingMessage = message;
    }

    public static String getEndingTitle() {
        return endingTitle;
    }

    public static String getEndingMessage() {
        return endingMessage;
    }
}
