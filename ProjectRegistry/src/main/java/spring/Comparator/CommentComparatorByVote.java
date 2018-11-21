package spring.Comparator;

import java.util.Comparator;

import spring.model.Comment;


public class CommentComparatorByVote implements Comparator<Comment> {

	@Override
	public int compare(Comment left, Comment right) {
		int ret = Integer.compare(right.voteCount(), left.voteCount());
		if (ret == 0) {
			return Integer.compare(left.getId(), right.getId());
		} else {
			return ret;
		}
	}
}
