import java.util.List;
import java.util.Random;

// The BaselineWolf class represents the movement capabilities of a wolf that will follow a prey as soon as it sees one.
public class BaselineWolf implements Wolf {

    @Override
    /**
     * Returns an array representing the next step of the wolf with 2 elements in {-1,0,1} where the first integer
     * indicates the ROW movement, and the second the COL movement. Within this method, diagonal moves are possible.
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

        // Else if no preys are present within the visibility of the wolf, the next move is defined randomly.
        } else {
            Random r = new Random();
            mymove[0] = r.nextInt(3)-1;
            mymove[1] = r.nextInt(3)-1;
        }

        return mymove;
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
