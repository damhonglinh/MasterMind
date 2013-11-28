package MasterMindGame;

import java.util.Observable;

/**
 *
 * @author Dam Linh - s3372757
 */
public class PreGameModel extends Observable {

    private boolean isQuickGameSelected = false;

    public void selectQuickGame() {
        Main.applet.setQuickPlayMode();
        isQuickGameSelected = true;
        setChanged();
        notifyObservers(true);
    }

    public void select1PlayerMode() {
        Main.applet.set1PlayerMode();
        setChanged();
        notifyObservers(true);
    }

    public void select2PlayerMode() {
        Main.applet.set2PlayerMode();
        setChanged();
        notifyObservers(true);
    }

    public void selectAIMode() {
        Main.applet.setAIMode();
        setChanged();
        notifyObservers(true);
    }

    //notifyObservers(false) = make PreGameFrame invisible
    public void selecteEasy() {
        Main.applet.setDifficulty(4, 6);
        setChanged();
        notifyObservers(true);
        if (isQuickGameSelected) {
            startGame();
        }
    }

    public void selectNormal() {
        Main.applet.setDifficulty(4, 8);
        setChanged();
        notifyObservers(true);
        if (isQuickGameSelected) {
            startGame();
        }
    }

    public void selectHard() {
        Main.applet.setDifficulty(6, 8);
        setChanged();
        notifyObservers(true);
        if (isQuickGameSelected) {
            startGame();
        }
    }

    public void selectCrazy() {
        Main.applet.setDifficulty(6, 10);
        setChanged();
        notifyObservers(true);
        if (isQuickGameSelected) {
            startGame();
        }
    }

    public void selectNumberOfRound(int numberOfRound) {
        Main.applet.setNumberOfRound(numberOfRound);
        startGame();
    }

    private void startGame() {
        Main.applet.startGame();
    }
}