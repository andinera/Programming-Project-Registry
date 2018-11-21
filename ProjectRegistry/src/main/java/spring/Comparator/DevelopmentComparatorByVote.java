package spring.Comparator;

import java.util.Comparator;

import spring.model.Development;


public class DevelopmentComparatorByVote implements Comparator<Development> {

	@Override
	public int compare(Development left, Development right) {
		int ret = Integer.compare(right.voteCount(), left.voteCount());
		if (ret == 0) {
			return Integer.compare(left.getId(), right.getId());
		} else {
			return ret;
		}
	}
}
