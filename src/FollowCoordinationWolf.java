import java.util.List;
import java.util.Random;

public class FollowCoordinationWolf implements Wolf{

    @Override
    /**
     * Returns an array representing the next step of the wolf with 2 elements in {-1,0,1} where the first integer
     * indicates the ROW movement, and the second the COL movement. Within this method, diagonal moves are possible.
     * Here, the next move is decided by whether one of the preys that is within its vision is also within the vision of
     * another wolf. If this is the case, it will move to that prey. Otherwise, it will move to any of the preys within
     * its vision.
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
            // Checking whether one of the preys that is in the vision of the current wolf is also in the vision of any
            // of the other wolves.
            int[] wolfFollowingPrey = preyWithinSightOfWolves(wolvesSight, preysSight);

            // If there is no prey present in the current wolf its vision that is also present in the vision of any of
            // the other wolves, the wolf moves to the closest prey.
            if(wolfFollowingPrey == null) {
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

            // Else if there is a prey present in the current wolf its vision that is also present in the vision of any
            // other wolves, the wolf moves to this prey.
            } else {
                // Defining the next move with respect to the x-direction.
                if (wolfFollowingPrey[0] < 0) {
                    mymove[0] = 1;
                } else if (wolfFollowingPrey[0] > 0) {
                    mymove[0] = -1;
                }

                // Defining the next move with respect to the y-direction.
                if (wolfFollowingPrey[1] < 0) {
                    mymove[1] = 1;
                } else if (wolfFollowingPrey[1] > 0) {
                    mymove[1] = -1;
                }
            }

        // Else if no preys are present within the visibility of the wolf, the next move is defined randomly.
        } else {
            Random r = new Random();
            mymove[0] = r.nextInt(3)-1;
            mymove[1] = r.nextInt(3)-1;
        }

        return mymove;
    }

    /**
     * Returns the prey present in the 'preysSight' list that is also present in the vision of another wolf from the
     * 'wolvesSight' list.
     *
     * @param wolvesSight The list of distances in x and y direction from the current wolf to all the other wolves.
     * @param preysSight The list of distances in x and y direction from the current wolf to the preys that are present
     *                  within its visibility area.
     * @return The prey present in the 'preysSight' list that is also present in the vision of another wolf from the
     *         'wolvesSight' list.
     */
    public int[] preyWithinSightOfWolves(List<int[]> wolvesSight, List<int[]> preysSight) {
        int visibilityRange = 5;
        int[] wolfClosestToPrey = null;
        double minimumDistance = Double.POSITIVE_INFINITY;

        // Looping over ever prey and wolf combination.
        for (int[] prey : preysSight) {
            for (int[] wolf : wolvesSight) {
                // Since the current wolf is also present in the 'wolvesSight' list, this one is skipped.
                if (wolf[0] != 0 && wolf[1] != 0) {
                    // Calculating the distance and checking whether it is the smallest distance found so far.
                    double distance = Math.sqrt(Math.pow(prey[0]-wolf[0], 2) + Math.pow(prey[1]-wolf[1], 2));
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
