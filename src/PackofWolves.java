import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.lang.Math.*;

public class PackofWolves implements Wolf {

	@Override
	public int[] moveAll(List<int[]> wolvesSight, List<int[]> preysSight) {
        int[] mymove = new int[2];

        // Gather all wolves in a group first before hunting
        int[] mainWolf = wolvesSight.get(0);
        if (mainWolf[0] != 0 || mainWolf[1] != 0) {
            mymove[0] = -Integer.signum(mainWolf[0]);
            mymove[1] = -Integer.signum(mainWolf[1]);
        } else {
            if (!preysSight.isEmpty()) {
                // Find the closest prey
                int minDistance = Integer.MAX_VALUE;
                int[] closestPrey = preysSight.get(0);

                for (int[] prey : preysSight) {
                    int distance = Math.abs(prey[0]) + Math.abs(prey[1]);
                    if (distance < minDistance) {
                        minDistance = distance;
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
        }

        return mymove;
	}
	
    @Override
	public int moveLim(List<int[]> wolvesSight, List<int[]> preysSight) {
		return 0;
	}


}