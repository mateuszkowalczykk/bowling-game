import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private final int numberOfFrames = 10;
    private final Map<Integer, ArrayList<Integer>> framesMap = new HashMap<>();
    private final ArrayList<Integer> subScoresList = new ArrayList<>();
    private int currentFrameNumber;

    public Game() {
        currentFrameNumber = 1;
        framesMap.put(currentFrameNumber,new ArrayList<>());
    }

    public void roll(int knockedPins){
        if(currentFrameNumber != numberOfFrames){
            addRollToFramesMap(knockedPins);
        }else{
            addRollToFramesMapInLastFrame(knockedPins);
        }
    }

    public int score(){
        for(int i = 1 ;i<=framesMap.size();i++){
            if(framesMap.get(i).size() >0){
                int subScore = 0;
                subScore += listSum(framesMap.get(i));
                subScore += strikeBonus(i);
                subScore += spareBonus(i);
                subScoresList.add(subScore);
            }
        }
//        System.out.println(framesMap);
        System.out.println(subScoresList);
        return listSum(subScoresList);
    }

    private int spareBonus(int frameNumber){
        if(isSpare(framesMap.get(frameNumber)) && frameNumber != numberOfFrames && framesMap.get(frameNumber+1).size() > 0){
            return framesMap.get(frameNumber+1).get(0);
        }
        return 0;
    }

    private int strikeBonus(int frameNumber){
        if(isStrike(framesMap.get(frameNumber)) && frameNumber != numberOfFrames){
            if(framesMap.get(frameNumber+1).size()>1 && framesMap.get(frameNumber+1).size() > 1){//next roll isn't strike
                return framesMap.get(frameNumber+1).get(0) + framesMap.get(frameNumber+1).get(1);
            }else{                               //next roll is strike
                if(framesMap.get(frameNumber+2) != null){
                    return framesMap.get(frameNumber+1).get(0)+framesMap.get(frameNumber+2).get(0);
                }
            }
        }
        return 0;
    }

    private void addRollToFramesMap(int knockedPins){
        ArrayList<Integer> currentFrame = framesMap.get(currentFrameNumber);

        if(currentFrame.size() == 0){
            currentFrame.add(knockedPins);
            if(isStrike(currentFrame)){
                nextFrame();
            }
        }else{
            currentFrame.add(knockedPins);
            nextFrame();
        }
    }

    private void addRollToFramesMapInLastFrame(int knockedPins){
        ArrayList<Integer> currentFrame = framesMap.get(currentFrameNumber);

        if(currentFrame.size()<=1){
            currentFrame.add(knockedPins);
        }else if((isStrike(currentFrame)||isSpare(currentFrame))&&currentFrame.size()==2){
            currentFrame.add(knockedPins);
        }
    }

    private void nextFrame(){
        if(currentFrameNumber<numberOfFrames){
            currentFrameNumber++;
            framesMap.put(currentFrameNumber,new ArrayList<>());
        }
    }

    private boolean isStrike(ArrayList<Integer> frame){
        return frame.get(0) == 10;
    }

    private boolean isSpare(ArrayList<Integer> frame){
        return listSum(frame) == 10 && frame.size() > 1;
    }

    private int listSum(ArrayList<Integer> frame){
        return frame.stream()
                .reduce(Integer::sum)
                .get();
    }

}
