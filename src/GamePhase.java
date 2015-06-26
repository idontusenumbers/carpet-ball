import java.util.*;

public enum GamePhase {
    WAITING,
    SETTING_UP,
    READY,
    MY_TURN,
    THEIR_TURN,
    END_OF_GAME;

    static Map<GamePhase, List<GamePhase>> transitionMap = new EnumMap<GamePhase, List<GamePhase>>(GamePhase.class);


    static {
        transitionMap.put(WAITING, createPhaseList(SETTING_UP));
        transitionMap.put(SETTING_UP, createPhaseList(READY));
        transitionMap.put(READY, createPhaseList(MY_TURN, THEIR_TURN));
        transitionMap.put(MY_TURN, createPhaseList(THEIR_TURN, END_OF_GAME));
        transitionMap.put(THEIR_TURN, createPhaseList(MY_TURN, END_OF_GAME));
        transitionMap.put(END_OF_GAME, createPhaseList(SETTING_UP));
    }

    private static List<GamePhase> createPhaseList(GamePhase... phases) {
        return Arrays.asList(phases);
    }

    public List<GamePhase> validTransitions() {
        return transitionMap.get(this);
    }

}
