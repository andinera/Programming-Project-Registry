package spring.comparator;

import java.util.Comparator;

import spring.model.Development;
import spring.model.Idea;
import spring.model.User;


/**
 * A @{link java.util.Comparator} for sorting @{link spring.model.User} objects
 * 
 * @author Shane Lockwood
 *
 */
public class UserComparatorByVote implements Comparator<User> {

	/**
	 * @param user1 The first @{link spring.model.User} to be sorted
	 * @param user2 The second @{link spring.model.User} to be sorted
	 * @return A negative or a positive integer if user1 has more or less cumulative idea and 
	 * development votes than user2, respectively. If user1 and user2 have the same amount of 
	 * votes, then a String compareTo is performed on their usernames and returned. 
	 * user1.getUsername().compareTo(user2.getUsername()
	 * @see java.lang.String#compareTo(java.lang.String)
	 */
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(spring.model.User user1, spring.model.User user2) {
		int u1 = 0;
		for (Idea idea : user1.getIdeas()) {
			u1 += idea.voteCount();
		}
		for (Development development : user1.getDevelopments()) {
			u1 += development.voteCount();
		}
		int u2 = 0;
		for (Idea idea : user2.getIdeas()) {
			u2 += idea.voteCount();
		}
		for (Development development : user2.getDevelopments()) {
			u2 += development.voteCount();
		}
		int ret = Integer.compare(u2, u1);
		if (ret == 0) {
			return user1.getUsername().compareTo(user2.getUsername());
		} else {
			return ret;
		}
	}
}
