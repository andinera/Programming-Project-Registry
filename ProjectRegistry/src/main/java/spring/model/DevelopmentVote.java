package spring.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * Models a vote specific to a {@link spring.model.Development}. Maintains a uniqueness constraint
 * ensuring duplicate entities with equivalent {@link spring.model.Development} and 
 * {@link spring.model.User} mapping aren't persisted.
 * 
 * @author Shane Lockwood
 *
 */
@Entity
@Table(name="developmentVotes", uniqueConstraints=@UniqueConstraint(columnNames= {"developmentId", "username"}))
public class DevelopmentVote extends Vote {

	private Development development;
	
	
	@SuppressWarnings("unused")
	private DevelopmentVote() {
	}
	
	/**
	 * Construct a DevelopmentVote.
	 * 
	 * @param voter The @{link spring.model.User} representing the voter.
	 * @param upVote The boolean representing true/false for upvote/downvote.
	 * @param development The {@link spring.model.Development} the vote is associated with.
	 */
	public DevelopmentVote(User voter, boolean upVote, Development development) {
		super(voter, upVote);
		setDevelopment(development);
	}
	
	/**
	 * Returns the associated {#link spring.model.Development}.
	 * 
	 * @return {#link spring.model.Development}
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="developmentId", referencedColumnName="id", nullable=false)
	public Development getDevelopment() {
		return this.development;
	}
	
	private void setDevelopment(Development development) {
		this.development = development;
	}
}
