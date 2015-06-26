import java.util.ArrayList;

public enum GamePhase {
    WAITING(new ArrayList<GamePhase>() {{
        add(SETTING_UP);
    }}),

    SETTING_UP(new ArrayList<GamePhase>() {{
        add(READY);
    }}),

    READY(new ArrayList<GamePhase>() {{
        add(MY_TURN);
        add(THEIR_TURN);
    }}),

    MY_TURN(new ArrayList<GamePhase>() {{
        add(THEIR_TURN);
        add(END_OF_GAME);
    }}),

    THEIR_TURN(new ArrayList<GamePhase>() {{
        add(MY_TURN);
        add(END_OF_GAME);
    }}),

    END_OF_GAME(new ArrayList<GamePhase>() {{
        add(SETTING_UP);
    }});

    private ArrayList<GamePhase> transitionList;

    GamePhase(ArrayList<GamePhase> transitionList) {
        this.transitionList = transitionList;
    }

    public ArrayList<GamePhase> validTransitions() {
        return this.transitionList;
    }

}
