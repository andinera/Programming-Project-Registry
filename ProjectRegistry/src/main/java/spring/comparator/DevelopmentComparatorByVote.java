package spring.comparator;

import java.util.Comparator;

import spring.model.Development;


/**
 * A @{link java.util.Comparator} for sorting {@link spring.model.Development} objects
 * 
 * @author Shane Lockwood
 *
 */
public class DevelopmentComparatorByVote implements Comparator<Development> {

	/**
	 * @param development1 The first {@link spring.model.Development} to be sorted
	 * @param development2 The second {@link spring.model.Development} to be sorted
	 * @return A negative or a positive integer if development1 has more or less votes than 
	 * development2, respectively. If development1 and development2 have the same amount of votes, 
	 * then a negative or a positive integer if development1's id is less than or greater than 
	 * development2's id, respectively. 
	 */
	@Override
	public int compare(Development development1, Development development2) {
		int ret = Integer.compare(development2.voteCount(), development1.voteCount());
		if (ret == 0) {
			return Integer.compare(development1.getId(), development2.getId());
		} else {
			return ret;
		}
	}
}
