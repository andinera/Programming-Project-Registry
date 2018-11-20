package spring.Comparator;

import java.util.Comparator;

import spring.model.Development;
import spring.model.Idea;
import spring.model.User;


public class UserComparatorByVote implements Comparator<User> {

	@Override
	public int compare(spring.model.User left, spring.model.User right) {
		int l = 0;
		for (Idea idea : left.getIdeas()) {
			l += idea.voteCount();
		}
		for (Development development : left.getDevelopments()) {
			l += development.voteCount();
		}
		int r = 0;
		for (Idea idea : right.getIdeas()) {
			r += idea.voteCount();
		}
		for (Development development : right.getDevelopments()) {
			r += development.voteCount();
		}
		int ret = Integer.compare(r, l);
		if (ret == 0) {
			return left.getUsername().compareTo(right.getUsername());
		} else {
			return ret;
		}
	}
}
