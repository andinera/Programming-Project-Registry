package spring.comparator;

import java.util.Comparator;

import spring.model.Idea;


/**
 * A @{link java.util.Comparator} for sorting {@link.spring.Idea} objects
 * 
 * @author Shane Lockwood
 *
 */
public class IdeaComparatorByVote implements Comparator<Idea> {
	
	/**
	 * @param idea1 The first {@link.spring.Idea} to be sorted
	 * @param idea2 The second {@link.spring.Idea} to be sorted
	 * @return A negative or a positive integer if idea1 has more or less votes than idea2, 
	 * respectively. If idea1 and idea2 have the same amount of votes, then a negative or a 
	 * positive integer if idea1's id is less than or greater than idea2's id, respectively. 
	 */
	@Override
	public int compare(Idea idea1, Idea idea2) {
		int ret = Integer.compare(idea2.voteCount(), idea1.voteCount());
		if (ret == 0) {
			return idea1.getDatePosted().compareTo(idea2.getDatePosted());
		} else {
			return ret;
		}
	}
}
