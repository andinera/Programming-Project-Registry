package spring.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;


/**
 * Models the similarities between the entity specific votes.
 * 
 * @author Shane Lockwood
 *
 */
@MappedSuperclass
abstract public class Vote {
	
	private int id;
	private User voter;
	private boolean upVote;
	
	
	protected Vote() {
	}
	
	/**
	 * @param voter The @{link spring.model.User} representing the voter.
	 * @param upVote The boolean representing true/false for upvote/downvote.
	 */
	protected Vote(User voter, boolean upVote) {
		setVoter(voter);
		setUpVote(upVote);
	}

	/**
	 * Returns the id.
	 * 
	 * @return int
	 */
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	public int getId() {
		return this.id;
	}
	
	@SuppressWarnings("unused")
	private void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Returns the voter.
	 * 
	 * @return {@link spring.model.User}
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username", nullable=false)
	public User getVoter() {
		return this.voter;
	}
	
	private void setVoter(User voter) {
		this.voter = voter;
	}
	
	/**
	 * Returns true/false for upvote/downvote.
	 * 
	 * @return boolean
	 */
	@Column(name="upVote", nullable=false)
	public boolean getUpVote() {
		return this.upVote;
	}
	
	/**
	 * Sets/modifies the vote status such that true/false is upvote/downvote.
	 * 
	 * @param upVote The boolean representing the vote status.
	 */
	public void setUpVote(boolean upVote) {
		this.upVote = upVote;
	}
}
