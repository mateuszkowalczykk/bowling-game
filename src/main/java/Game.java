import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private final int numberOfFrames = 10;
    private final Map<Integer, ArrayList<Integer>> framesMap = new HashMap<>();
    private int currentFrameNumber = 1;
    private boolean isEndGame = false;

    public Game() {
        framesMap.put(currentFrameNumber, new ArrayList<>());
    }

    public boolean roll(int knockedPins) {

        if(!isEndGame){
            if(isRollPossible(knockedPins)){
                framesMap.get(currentFrameNumber).add(knockedPins);
            }else{
                System.out.println("This roll is not possible. Please try again.");
            }
        }

        if (currentFrameNumber != numberOfFrames) {
            nextFrame();
        } else {
            if(endGameCheck()) return false;
        }
        return true;
    }

    public int score(){
        int score = 0;
        for(int i = 1;i<=numberOfFrames;i++){
            score += listSum(framesMap.get(i));
            if(i != numberOfFrames){
                score += isSpare(framesMap.get(i))? spareBonus(i): 0;
                score += isStrike(framesMap.get(i))? strikeBonus(i): 0;
            }
        }
        return score;
    }

    private void nextFrame() {
        ArrayList<Integer> currentFrame = framesMap.get(currentFrameNumber);

        if(isStrike(currentFrame) || currentFrame.size() == 2){
            currentFrameNumber++;
            framesMap.put(currentFrameNumber,new ArrayList<>());
        }
    }

    private boolean endGameCheck() {
        ArrayList<Integer> currentFrame = framesMap.get(currentFrameNumber);

        if((currentFrame.size() == 2 && !isSpare(currentFrame) && !isStrike(currentFrame)) || currentFrame.size() == 3) {
            isEndGame = true;
            System.out.println("Game over!");
            return true;
        }
        return false;
    }

    private boolean isRollPossible(int knockedPins)  {
        ArrayList<Integer> currentFrame = framesMap.get(currentFrameNumber);

        if(knockedPins>10 || knockedPins<0) return false;
        if(currentFrame.size() == 0) return true;
        if(currentFrameNumber != numberOfFrames || !isStrike(currentFrame)) {
            if (currentFrame.size() == 1 && currentFrame.get(0) + knockedPins > 10) return false;
        }else{
            if (currentFrame.size() == 2 && currentFrame.get(1) + knockedPins > 10 && currentFrame.get(1) != 10) return false;
        }
        return true;
    }

    private int spareBonus(int frameNumber) {
        return framesMap.get(frameNumber + 1).get(0);
    }

    private int strikeBonus(int frameNumber) {
        if(isStrike(framesMap.get(frameNumber + 1)) && frameNumber != numberOfFrames-1){
            return framesMap.get(frameNumber + 1).get(0) + framesMap.get(frameNumber + 2).get(0);
        }else{
            return framesMap.get(frameNumber + 1).get(0) + framesMap.get(frameNumber + 1).get(1);
        }
    }

    private boolean isStrike(ArrayList<Integer> frame) {
        return frame.size() != 0? frame.get(0) == 10 : false;
    }

    private boolean isSpare(ArrayList<Integer> frame) {
        return frame.size() != 0? listSum(frame) == 10 && frame.size() > 1 : false;
    }

    private int listSum(ArrayList<Integer> frame) {
        return frame.stream()
                .reduce(Integer::sum)
                .get();
    }
}