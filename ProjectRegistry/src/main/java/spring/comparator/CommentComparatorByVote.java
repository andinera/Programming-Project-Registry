package spring.comparator;

import java.util.Comparator;

import spring.model.Comment;


/**
 * A @{link java.util.Comparator} for sorting {@link spring.model.Comment} objects
 * 
 * @author Shane Lockwood
 *
 */
public class CommentComparatorByVote implements Comparator<Comment> {

	/**
	 * @param comment1 The first {@link spring.model.Comment} to be sorted
	 * @param comment2 The second {@link spring.model.Comment} to be sorted
	 * @return A negative or a positive integer if comment1 has more or less votes than comment2, 
	 * respectively. If comment1 and comment2 have the same amount of votes, then a negative or 
	 * a positive integer if comment1's id is less than or greater than comment2's id, respectively. 
	 */
	@Override
	public int compare(Comment comment1, Comment comment2) {
		int ret = Integer.compare(comment2.voteCount(), comment1.voteCount());
		if (ret == 0) {
			return Integer.compare(comment1.getId(), comment2.getId());
		} else {
			return ret;
		}
	}
}
