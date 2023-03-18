import java.util.List;
import java.util.Random;

public class BaselineWolf implements Wolf{

    @Override
    public int[] moveAll(List<int[]> wolvesSight, List<int[]> preysSight) {
        int[] mymove = new int[2];
        if(!preysSight.isEmpty()) {
            // Find the closest pray
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
