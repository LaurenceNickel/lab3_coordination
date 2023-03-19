import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.lang.Math.*;

// The PackofWolves class represents the movement capabilities of a wolf where each wolf will follow a prey when there
// is one in its vision. Otherwise, the wolves will move together which after a while results in the wolves hunting in a
// pack.
public class PackofWolves implements Wolf {

    // The distance where wolves that have a smaller distance are defined as close.
    private static final int CLOSENESS_RANGE = 5;

    @Override
    /**
     * Returns an array representing the next step of the wolf with 2 elements in {-1,0,1} where the first integer
     * indicates the ROW movement, and the second the COL movement. Within this method, diagonal moves are possible.
     * Here, the next move is decided by whether a prey is present within its vision. If this is the case, the wolf will
     * follow the prey. If this is not the case, the wolf will move towards the other wolves resulting in the wolves
     * hunting together in a pack.
     *
     * @param wolvesSight The list of distances in x and y direction from the current wolf to all the other wolves.
     * @param preysSight The list of distances in x and y direction from the current wolf to the preys that are present
     *                  within its visibility area.
     * @return The next move to be performed by the wolf.
     */
	public int[] moveAll(List<int[]> wolvesSight, List<int[]> preysSight) {
        int[] mymove = new int[2];

        // If the 'preySight' list is not empty meaning there is at least one prey in the wolf its vision, it will
        // follow it.
        if(!preysSight.isEmpty()) {
            int minimumDistance = Integer.MAX_VALUE;
            int[] closestPrey = preysSight.get(0);

            // Looping over all the preys to find the prey that is closest to the wolf.
            for (int[] prey : preysSight) {
                int distance = Math.abs(prey[0]) + Math.abs(prey[1]);
                if (distance < minimumDistance) {
                    minimumDistance = distance;
                    closestPrey = prey;
                }
            }

            // When this closest prey is found, the next move of the wolf is defined such that it moves towards the
            // prey.
            // Defining the next move with respect to the x-direction.
            if (closestPrey[0] < 0) {
                mymove[0] = 1;
            } else if (closestPrey[0] > 0) {
                mymove[0] = -1;
            }

            // Defining the next move with respect to the y-direction.
            if (closestPrey[1] < 0) {
                mymove[1] = 1;
            } else if (closestPrey[1] > 0) {
                mymove[1] = -1;
            }

        // Else if there is no prey within the wolf its vision, it will move towards the other wolves.
        } else if (!alreadyCloseToWolf(wolvesSight)){
            int centerDirectionX = 0;
            int centerDirectionY = 0;

            // Looping over all the wolves and adding up their X and Y distances to discover to which direction the
            // current wolf should move to.
            for (int[] wolf : wolvesSight) {
                centerDirectionX += wolf[0];
                centerDirectionY += wolf[1];
            }

            // The next move of the wolf is defined such that it moves towards the other wolves.
            // Defining the next move with respect to the x-direction.
            if (centerDirectionX < 0) {
                mymove[0] = 1;
            } else if (centerDirectionX > 0) {
                mymove[0] = -1;
            }

            // Defining the next move with respect to the y-direction.
            if (centerDirectionY < 0) {
                mymove[1] = 1;
            } else if (centerDirectionY > 0) {
                mymove[1] = -1;
            }

        // Else if no preys are present within the visibility of the wolf and the wolf is already close enough to other
        // wolves, the next move is defined randomly.
        } else {
            Random r = new Random();
            mymove[0] = r.nextInt(3)-1;
            mymove[1] = r.nextInt(3)-1;
        }

        return mymove;
	}

    /**
     * Checking whether the current wolf is already close (within some predefined range) to any of the other wolves.
     *
     * @param wolvesSight The list of distances in x and y direction from the current wolf to all the other wolves.
     * @return Whether the current wolf is already close (within some predefined range) to any of the other wolves.
     */
    public boolean alreadyCloseToWolf(List<int[]> wolvesSight) {
        int minimumDistance = Integer.MAX_VALUE;

        // Looping over all the wolves to find the one that is closest to the wolf.
        for (int[] wolf : wolvesSight) {
            int distance = Math.abs(wolf[0]) + Math.abs(wolf[1]);
            if (distance < minimumDistance && distance != 0) {
                minimumDistance = distance;
            }
        }

        return minimumDistance <= CLOSENESS_RANGE;
    }

    @Override
    /**
     * Returns 0 for No Movement, 1 for North, 2 for East, 3 for South, 4 for West. Within this method, diagonal moves
     * are not possible.
     *
     * @param wolvesSight The list of distances in x and y direction from the current wolf to all the other wolves.
     * @param preySight The list of distances in x and y direction from the current wolf to the preys that are present
     *                  within its visibility area.
     * @return The next move to be performed by the wolf.
     */
	public int moveLim(List<int[]> wolvesSight, List<int[]> preysSight) {
		return 0;
	}


}