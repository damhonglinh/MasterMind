package MasterMindGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author Dam Linh - s3372757
 */
public class PlayerAI extends Player {

    private InGameModel model;
    private Token[] tokens;
    private int numberOfColor;
    private int numberOfCode;
    private int currentRow;
    private int[] result = new int[2];
    private Timer timer = new Timer(400, new TimeListener());
    private int[] previousGuess;
    private int[] secretCode;

    public PlayerAI(int numberOfRound, int id) {
        super(numberOfRound, id);
    }

    public void setModel(InGameModel model) {
        this.model = model;
        tokens = model.getTokens();
        numberOfColor = model.getNumberOfColor();
        numberOfCode = model.getNumberOfCode();
        secretCode = new int[numberOfCode];
    }

    public void setRowResult(int[] result) {
        this.result = result;
    }

    public void startGuessing() {
        //obtain secretCode from model
        for (int i = 0; i < secretCode.length; i++) {
            secretCode[i] = model.getSecretToken()[i].getColor();
        }

        currentRow = 9;
        timer.start();
    }

    private void guessOneRow() {
        int[] nextGuess = setNextGuess();
        for (int i = currentRow * numberOfCode, j = 0; i < (currentRow + 1) * numberOfCode; j++, i++) {
            tokens[i].setColor(nextGuess[j]);
        }
        model.ok(currentRow);
    }

    private int[] setNextGuess() {
        int[] guess = new int[numberOfCode];
        switch (currentRow) {
            case 9:
                setFirstGuess(guess);
                break;
            case 8:
                setSecondGuess(guess);
                break;
            default:
                setGuess(guess);
                break;
        }
        return guess;
    }

    private void setFirstGuess(int[] guess) {
        for (int i = 0; i < guess.length; i++) {
            if (i < guess.length / 2) {
                guess[i] = 1;
            } else {
                guess[i] = 2;
            }
        }
        if (numberOfCode == 6) {
            guess[2] = numberOfColor;
            guess[5] = numberOfColor;
        }
    }

    private void setSecondGuess(int[] guess) {
        for (int i = 0; i < guess.length; i++) {
            if (i < guess.length / 2) {
                guess[i] = 3;
            } else {
                guess[i] = 4;
            }
        }
        if (numberOfCode == 6) {
            guess[5] = numberOfColor - 1;
        }
        previousGuess = guess;
    }

    private void setGuess(int[] guess) {
        Random r = new Random();
        int black = result[1];
        int white = result[0];

        //set random color
        for (int i = 0; i < guess.length; i++) {
            int colorTemp = 1;
            do {
                colorTemp = r.nextInt(numberOfColor) + 1;
            } while (previousGuess[i] == colorTemp);
            guess[i] = colorTemp;
        }

        int index = r.nextInt(numberOfCode);
        boolean[] rightLocationGuess = new boolean[numberOfCode]; //to check if the location is already taken
        boolean loopAgain = false;
        do {
            for (int i = 0; i < rightLocationGuess.length; i++) {
                if (rightLocationGuess[i] && i == index) {
                    index = r.nextInt(numberOfCode);
                    loopAgain = true;
                } else {
                    rightLocationGuess[i] = true;
                    loopAgain = false;
                    break;
                }
            }
        } while (loopAgain);

        //make more rightColor guess if there is no white in previous guess
        if (white == black && numberOfCode - black > 1) {
            guess[index] = secretCode[r.nextInt(numberOfCode)];
        } else { //make 1 and only 1 more right location guess
            guess[index] = secretCode[index];
        }

        boolean baseAdditionCondition = true;
        if (numberOfCode == 6) {
            if (numberOfColor == 10) {
                baseAdditionCondition = r.nextDouble() < 0.25;
            } else {
                baseAdditionCondition = r.nextDouble() < 0.35;
            }
        }

        boolean additionCondition = baseAdditionCondition;
        for (int i = 0; i < numberOfCode; i++) {
            if (previousGuess[i] == secretCode[i] && (additionCondition || numberOfCode - black == 1 || numberOfCode - white == 1)) {
                guess[i] = secretCode[i];
            } else {
                guess[i] = secretCode[r.nextInt(numberOfCode)];
            }
            additionCondition = r.nextBoolean();
        }
        previousGuess = guess;
    }

    private class TimeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            guessOneRow();
            currentRow--;
            if (currentRow < 8) {
                //set more thinking time
                int additionalDelay;
                if (numberOfCode == 4 && numberOfColor == 6) {
                    additionalDelay = new Random().nextInt(2) * 80;
                } else if (numberOfCode == 4 && numberOfColor == 8) {
                    additionalDelay = (new Random().nextInt(2) + 1) * 80;
                } else if (numberOfCode == 6 && numberOfColor == 8) {
                    additionalDelay = (new Random().nextInt(2) + 2) * 80;
                } else {
                    additionalDelay = (new Random().nextInt(3) + 2) * 80;
                }
                timer.setDelay(timer.getDelay() + additionalDelay);
            }
            if (currentRow < 0 || model.isTimerStop()) {
                timer.stop();
                timer.setDelay(400);
            }
        }
    }
    // Uncomplete
//    private void setCertainColor(int from, int to) {
//        for (int i = 0;
//                i < certainColor.length; i++) {
//            certainColor[i] = 0;
//        }
//        for (int i = from - 1;
//                i < to; i++) {
//            certainColor[i] = i;
//        }
//    }
//    private void set3Guess(int[] guess) {
//        int black1 = results[currentRow + 1][1];
//        int white1 = results[currentRow
//                + 2][0];
//        int black2 = results[currentRow + 2][1];
//        int white2 =
//                results[currentRow + 1][0];
//        switch (white1) {
//            case 0: //set certainColor
//                setCertainColor(3, numberOfColor);
//                switch (white2) {
//                    case 0:
//                        setCertainColor(5, numberOfColor);
//                        setGuess(guess, 5, 5, 6, 6);
//                        break;
//                    case 1:
//                        setGuess(guess, 3, 5, 6, 6);
//                        break;
//                    case 2:
//                        setGuess(guess, 3, 3, 6, 6);
//                        break;
//                    case 3:
//                        setGuess(guess, 3, 3, 5, 5);
//                        break;
//                    case 4:
//                        setGuess(guess, 4,
//                                4, 3, 3); // WIN break; } break;
//                }
//            case 1:
//                setCertainColor(1, numberOfColor);
//                switch (white2) {
//                    case 0:
//                        setCertainColor(1, 2);
//                        setCertainColor(5, numberOfColor);
//                        setGuess(guess, 1,
//                                5, 6, 6);
//                        break;
//                    case 1:
//                        setGuess(guess, 1, 3, 6, 6);
//                        break;
//                    case 2:
//                        setGuess(guess, 1, 3, 3, 6);
//                        break;
//                    case 3:
//                        setGuess(guess, 1, 3, 3, 4);
//                        break;
//                }
//                break;
//            case 2:
//                setCertainColor(1, numberOfColor);
//                switch (white2) {
//                    case 0:
//                        setCertainColor(1, 2);
//                        setCertainColor(5, numberOfColor);
//                        setGuess(guess, 1, 1, 6, 6);
//                        break;
//                    case 1:
//                        setGuess(guess, 1, 1, 3, 6);
//                        break;
//                    case 2:
//                        setGuess(guess, 1, 1, 5, 5);
//                        break;
//                }
//                break;
//            case 3:
//                setCertainColor(1, numberOfColor);
//                switch (white2) {
//                    case 0:
//                        setCertainColor(1, 2);
//                        setCertainColor(5, numberOfColor);
//                        setGuess(guess, 1,
//                                1, 2, 6);
//                        break;
//                    case 1:
//                        setGuess(guess, 1, 1, 2, 3);
//                        break;
//                }
//                break;
//            default:
//                setGuess(guess, 1, 1, 2, 2); // WIIN } return guess; } private void
//
//        }
//    }
//    private void setGuess(int[] guess, int a, int b, int c, int d) {
//        guess[0] = a;
//        guess[1] =
//                b;
//        guess[2] = c;
//        guess[3] = d;
//        if (guess.length == 6) {
//            guess[4] = b;
//            guess[5] = d;
//        }
//    }
}