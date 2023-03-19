import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// The RandomWolf class represents the movement capabilities of a wolf that will perform a random move at each iteration.
public class RandomWolf implements Wolf {

	@Override
	/**
	 * Returns an array representing the next step of the wolf with 2 elements in {-1,0,1} where the first integer
	 * indicates the ROW movement, and the second the COL movement. Within this method, diagonal moves are possible.
	 * Here, the next move is decided by randomly generating numbers.
	 *
	 * @param wolvesSight The list of distances in x and y direction from the current wolf to all the other wolves.
	 * @param preysSight The list of distances in x and y direction from the current wolf to the preys that are present
	 *                  within its visibility area.
	 * @return The next move to be performed by the wolf.
	 */
	public int[] moveAll(List<int[]> wolvesSight, List<int[]> preysSight) {
		// Creates a random move to be performed by the wolf.
		Random r = new Random();
		int[] mymove = new int[2];
		mymove[0] = r.nextInt(3)-1;
		mymove[1] = r.nextInt(3)-1;
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
		Random r = new Random();
		return r.nextInt(4) + 1;
	}
}
