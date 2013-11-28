package MasterMindGame;

/**
 *
 * @author Dam Linh - s3372757
 */
public class Player {

    private int[] numberOfGuess;
    private int[] timeCount;
    private int totalNumberOfGuess;
    private int totalTimeCount;
    private int numberOfWin;
    private float avgGuess;
    private float avgTimeCount;
    private int id = 0;
    private int numberOfRound = 1;

    public Player(int numberOfRound, int id) {
        this.id = id;
        this.numberOfRound = numberOfRound;
        numberOfGuess = new int[numberOfRound];
        timeCount = new int[numberOfRound];
    }

    public Player() {
        numberOfGuess = new int[1];
        timeCount = new int[1];
    }

    public int[] getTimeCount() {
        return timeCount;
    }

    public int getTotalNumberOfGuess() {
        return totalNumberOfGuess;
    }

    public int getTotalTimeCount() {
        return totalTimeCount;
    }

    public double getAvgGuess() {
        return avgGuess;
    }

    public double getAvgTimeCount() {
        return avgTimeCount;
    }

    public int getNumberOfWin() {
        return numberOfWin;
    }

    public int[] getNumberOfGuess() {
        return numberOfGuess;
    }

    public int getId() {
        return id;
    }

    public void winOneRound() {
        numberOfWin++;
    }

    public int getNumberOfRound() {
        return numberOfRound;
    }

    /**
     * Increment 1 more guess in the specific round. Increment 1 more guess in
     * total guess. Update average guess.
     *
     * @param currentRound
     */
    public void guessIncrement(int currentRound) {
        numberOfGuess[currentRound - 1]++;
        totalNumberOfGuess++;
        avgGuess = (float) totalNumberOfGuess / currentRound;
    }

    /**
     * Increment 1 more second in the specific round. Increment 1 more second in
     * total time. Update average time count.
     *
     * @param currentRound
     */
    public void timeIncrement(int currentRound) {
        timeCount[currentRound - 1]++;
        totalTimeCount++;
        avgTimeCount = (float) totalTimeCount / currentRound;
    }
}
