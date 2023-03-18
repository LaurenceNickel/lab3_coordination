import java.util.List;
import java.util.Random;

public class FollowCoordinationWolf implements Wolf{

    public int[] preyWithinSightOfWolves(List<int[]> wolvesSight, List<int[]> preysSight) {
        int visibilityRange = 5;
        int[] wolfClosestToPrey = null;
        double minimumDistance = Double.POSITIVE_INFINITY;

        for (int[] prey : preysSight) {
            for (int[] wolf : wolvesSight) {
                // Skip the current wolf.
                if (wolf[0] != 0 && wolf[1] != 0) {
                    double distance = Math.sqrt(Math.pow(prey[0]-wolf[0], 2) + Math.pow(prey[1]-wolf[1], 2));
                    //distance = sqrt((x1 - x2)^2 + (y1 - y2)^2)
                    if (distance <= visibilityRange && (wolfClosestToPrey == null || distance < minimumDistance)) {
                        wolfClosestToPrey = wolf;
                        minimumDistance = distance;
                    }
                }
            }
        }

        return wolfClosestToPrey;
    }


    @Override
    public int[] moveAll(List<int[]> wolvesSight, List<int[]> preysSight) {
        int[] mymove = new int[2];

        if(!preysSight.isEmpty()) {
            int[] wolfFollowingPrey = preyWithinSightOfWolves(wolvesSight, preysSight);

            if(wolfFollowingPrey == null) {
                // Find the closest pray.
                int minimumDistance = Integer.MAX_VALUE;
                int[] closestPrey = preysSight.get(0);

                for (int[] prey : preysSight) {

                    int distance = Math.abs(prey[0]) + Math.abs(prey[1]);
                    if (distance < minimumDistance) {
                        minimumDistance = distance;
                        closestPrey = prey;
                    }
                }
                // Move towards the closest prey
                if (closestPrey[0] < 0) {
                    mymove[0] = 1;
                } else if (closestPrey[0] > 0) {
                    mymove[0] = -1;
                }

                // Move up and down
                if (closestPrey[1] < 0) {
                    mymove[1] = 1;
                } else if (closestPrey[1] > 0) {
                    mymove[1] = -1;
                }
            }
            else {
                // Move towards the prey that is already being followed by another wolf.
                if (wolfFollowingPrey[0] < 0) {
                    mymove[0] = 1;
                } else if (wolfFollowingPrey[0] > 0) {
                    mymove[0] = -1;
                }

                // Move up and down
                if (wolfFollowingPrey[1] < 0) {
                    mymove[1] = 1;
                } else if (wolfFollowingPrey[1] > 0) {
                    mymove[1] = -1;
                }
            }
        } else {
            Random r = new Random();
            mymove[0] = r.nextInt(3)-1;
            mymove[1] = r.nextInt(3)-1;
        }

        return mymove;
    }

    public int moveLim(List<int[]> wolvesSight, List<int[]> preysSight) {
        return 0;
    }
}
