package spring.Comparator;

import java.util.Comparator;

import spring.model.Idea;


public class IdeaComparatorByVote implements Comparator<Idea> {
	
	@Override
	public int compare(Idea left, Idea right) {
		int ret = Integer.compare(right.voteCount(), left.voteCount());
		if (ret == 0) {
			return left.getDatePosted().compareTo(right.getDatePosted());
		} else {
			return ret;
		}
	}
}
