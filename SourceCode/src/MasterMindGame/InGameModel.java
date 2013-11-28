package MasterMindGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Dam Linh - s3372757
 */
public class InGameModel extends TokenModel {

    private CheckToken[] checkTokens;
    private OkButton[] okButtons;
    private CancelButton[] cancelButtons;
    private Token[] secretCode;
    private int numberOfPlayer;
    private int currentRound;
    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private Timer timer;
    private String[] result = new String[3];
    private int numberOfRound = 1;

    public InGameModel(Token[] tokens,
            CheckToken[] checkTokens,
            CancelButton[] cancelButtons,
            OkButton[] okButtons,
            int numberOfColor,
            Token[] secretCode) {

        super(numberOfColor, secretCode.length, tokens);
        timer = new Timer(1000, new TimeListener());
        this.checkTokens = checkTokens;
        this.okButtons = okButtons;
        this.cancelButtons = cancelButtons;
        this.secretCode = secretCode;

        currentRound = 1;
        initialize();
    }

    private void initialize() {
        //set changeable for the first numberOfCode tokens
        for (int i = tokens.length - numberOfCode; i < tokens.length; i++) {
            tokens[i].setChangeable(true);
        }
        //set invisible for all labels except the first 2
        for (int i = 0; i < 9; i++) {
            okButtons[i].setVisible(false);
            cancelButtons[i].setVisible(false);
        }
    }

    @Override
    public void ok(int currentRow) {
        if (!isAllFilled(currentRow)) {
            return;//if the tokens are not all filled then return
        }
        //set okLabel and cancelLabel invisible
        okButtons[currentRow].setVisible(false);
        cancelButtons[currentRow].setVisible(false);

        //increment 1 guess for currentPlayer
        incrementGuess();

        boolean isGameOver = false;
        if (checkIsWin(currentRow)) {
            win();
            isGameOver = true;
        } else {
            if (currentRow != 0) {//if it is NOT the last row then: 
                okButtons[currentRow - 1].setVisible(true);
                cancelButtons[currentRow - 1].setVisible(true);
            } else {
                lose();
                isGameOver = true;
            }
        }

        for (int i = currentRow * numberOfCode; i < (currentRow + 1) * numberOfCode; i++) {
            tokens[i].setChangeable(false);
            if ((currentRow != 0) && (!isGameOver)) {//if it is NOT the last row and game is NOT over:
                tokens[i - numberOfCode].setChangeable(true);
            }
        }

        setChanged();
        notifyObservers();
    }

    private int[] checkCurrentRow(int currentRow) {
        int[] checkingResult = new int[2];//index 0 is for right color
        // 2D array with 2 arrays. First array for color code
        // 2nd array for number of times that color appears in secret codes
        int[][] secretTemp = new int[numberOfCode][2];
        //check how many times a color appears in secretCode
        for (int i = 0; i < secretCode.length; i++) {
            boolean setted = false;
            for (int j = 0; j < secretTemp.length; j++) {
                if (secretCode[i].getColor() == secretTemp[j][0]) {
                    secretTemp[j][1]++;
                    setted = true;
                }
            }
            if (!setted) {
                secretTemp[i][0] = secretCode[i].getColor();
                secretTemp[i][1]++;
            }
        }
        //check right color
        for (int i = 0; i < secretTemp.length; i++) {
            int count = 0;
            for (int j = currentRow * numberOfCode; j < (currentRow + 1) * numberOfCode; j++) {
                if (tokens[j].getColor() == secretTemp[i][0]) {
                    count++;
                    if (count == secretTemp[i][1]) {
                        break;
                    }
                }
            }
            checkingResult[0] += count;//rightColor++
        }
        //check right location
        for (int i = 0; i < secretCode.length; i++) {
            if (secretCode[i].getColor() == tokens[currentRow * numberOfCode + i].getColor()) {
                checkingResult[1]++;//rightLocation++
            }
        }

        //tell AI the result of each row if currentPlayer is AI
        if (currentPlayer instanceof PlayerAI) {
            PlayerAI tempPlayer = (PlayerAI) currentPlayer;
            tempPlayer.setRowResult(checkingResult);
        }

        return checkingResult;
    }

    private boolean checkIsWin(int currentRow) {
        int[] checkResult = checkCurrentRow(currentRow);

        //change checkTokens based on results
        int index = currentRow * numberOfCode;
        for (int i = 0; i < checkResult[0]; i++, index++) {
            checkTokens[index].setColor(CheckToken.WHITE);
        }
        index = currentRow * numberOfCode;
        for (int i = 0; i < checkResult[1]; i++, index++) {
            checkTokens[index].setColor(CheckToken.BLACK);
        }

        if (checkResult[1] == numberOfCode) {
            return true;
        }
        return false;
    }

    private void revealSecretCode() {
        int[] codeInt = new int[secretCode.length];
        for (int i = 0; i < secretCode.length; i++) {
            codeInt[i] = secretCode[i].getColor();
        }
        setChanged();
        notifyObservers(codeInt);
    }

    private void incrementGuess() {
        currentPlayer.guessIncrement(currentRound);
        setChanged();
        notifyObservers(currentPlayer);
    }

    public void setPlayer1(Player player1) {
        if (player1 != null) {
            this.player1 = player1;
            currentPlayer = player1;
            numberOfRound = player1.getNumberOfRound();
            numberOfPlayer++;
        } else {
            currentPlayer = new Player();
        }
    }

    public void setPlayer2(Player player2) {
        if (player2 != null) {
            this.player2 = player2;
            numberOfPlayer++;
            if (player2 instanceof PlayerAI) {
                this.player2 = (PlayerAI) player2;
                PlayerAI tempPlayer = (PlayerAI) player2;
                tempPlayer.setModel(this);
            }
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void startTime() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public boolean isTimerStop() {
        return !(timer.isRunning());
    }

    private void processResult(String status) {
        result[0] = status;
        result[1] = "Time: " + currentPlayer.getTimeCount()[currentRound - 1];
        if (((currentPlayer.getId() == 2) || (numberOfPlayer == 1)) && (currentRound == numberOfRound)) {
            result[2] = "Result";
        } else if (numberOfPlayer == 0) {
            result[2] = "Quit";
        } else {
            result[2] = "Continue";
        }
        setChanged();
        notifyObservers(result);
    }

    private void win() {
        stopTimer();
        currentPlayer.winOneRound();
        processResult("WIN");
        revealSecretCode();
    }

    private void lose() {
        stopTimer();
        processResult("LOSE");
        revealSecretCode();
    }

    //invoked by listener
    public void quit() {
        Main.applet.start();
    }

    //invoked by listener
    public void cont() {
        resetMainPanel();
        //swap player
        if (numberOfPlayer == 2) {
            setChanged();
            notifyObservers(player2);
            if (currentPlayer == player1) {
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
                currentRound++;
            }
        }
        if (numberOfPlayer == 1) {
            currentRound++;
        }

        String[] newRound = new String[1];
        newRound[0] = currentRound + "";
        setChanged();
        notifyObservers(newRound);
        setChanged();
        notifyObservers(player1);
    }

    private void resetMainPanel() {
        for (int i = 0; i < tokens.length; i++) {
            tokens[i].setColor(Token.NO_COLOR);
            tokens[i].setChangeable(false);
            checkTokens[i].setColor(CheckToken.NO_COLOR);
        }

        for (int i = 0; i < 10; i++) {
            cancelButtons[i].setVisible(true);
            okButtons[i].setVisible(true);
        }
        // hide all secret codes
        int[] codeInt = new int[secretCode.length];
        for (int i = 0; i < secretCode.length; i++) {
            codeInt[i] = Token.HIDDEN;
        }
        setChanged();
        notifyObservers(codeInt);
        obtainSecretCode();
        initialize();
    }

    public void showResult() {
        byte winner;
        if (numberOfPlayer < 2) {
            //make StatisticPanel update player's data
            setChanged();
            notifyObservers(player1);

            winner = 1;
            //make StatisticPanel show final result
            setChanged();
            notifyObservers(winner);
            return;
        }

        //validate the result, winner is player's id
        if (player1.getNumberOfWin() > player2.getNumberOfWin()) {
            winner = 1;
        } else if (player1.getNumberOfWin() < player2.getNumberOfWin()) {
            winner = 2;
            //if numberOfWin are equal then:
        } else {
            if (player1.getTotalNumberOfGuess() < player2.getTotalNumberOfGuess()) {
                winner = 1;
            } else if (player1.getTotalNumberOfGuess() > player2.getTotalNumberOfGuess()) {
                winner = 2;
                //if totalNumberOfGuess are equal then
            } else {
                if (player1.getTotalTimeCount() < player2.getTotalTimeCount()) {
                    winner = 1;
                } else if (player1.getTotalTimeCount() > player2.getTotalTimeCount()) {
                    winner = 2;
                } else {
                    winner = 0;//DRAW
                }
            }
        }
        //make StatisticPanel update players' data
        setChanged();
        notifyObservers(player1);
        setChanged();
        notifyObservers(player2);
        //make StatisticPanel show final result
        setChanged();
        notifyObservers(winner);
    }

    private void obtainSecretCode() {
        if ((numberOfPlayer == 1) || (currentPlayer instanceof PlayerAI)) {
            Main.applet.setSecretCodeFromRandom();
            startTime();
        } else {
            Main.applet.obtainSecretCodeFromPlayer();
            stopTimer();
        }
    }

    public CheckToken[] getCheckTokens() {
        return checkTokens;
    }

    public int getNumberOfCode() {
        return numberOfCode;
    }

    public Token[] getTokens() {
        return tokens;
    }

    public Token[] getSecretToken() {
        return secretCode;
    }

    public Player getPlayer1() {
        return player1;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    private class TimeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            currentPlayer.timeIncrement(currentRound);
            InGameModel.this.setChanged();
            notifyObservers(currentPlayer);
        }
    }
}
