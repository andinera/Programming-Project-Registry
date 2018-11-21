package spring.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.FetchType;


/**
 * Models a vote specific to a {@link spring.model.Idea}. Maintains a uniqueness constraint
 * ensuring duplicate entities with equivalent {@link spring.model.Idea} and 
 * {@link spring.model.User} mapping aren't persisted.
 * 
 * @author Shane Lockwood
 *
 */
@Entity
@Table(name="ideaVotes", uniqueConstraints=@UniqueConstraint(columnNames= {"ideaId", "username"}))
public class IdeaVote extends Vote {

	private Idea idea;
	
	
	@SuppressWarnings("unused")
	private IdeaVote() {
	}
	
	/**
	 * Construct an IdeaVote.
	 * 
	 * @param voter The @{link spring.model.User} representing the voter.
	 * @param upVote The boolean representing true/false for upvote/downvote.
	 * @param idea The {@link spring.model.Idea} the vote is associated with.
	 */
	public IdeaVote(User voter, boolean upVote, Idea idea) {
		super(voter, upVote);
		setIdea(idea);
	}
	
	/**
	 * Returns the associated {#link spring.model.Idea}.
	 * 
	 * @return {#link spring.model.Idea}
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ideaId", referencedColumnName="id", nullable=false)
	public Idea getIdea() {
		return this.idea;
	}
	
	private void setIdea(Idea idea) {
		this.idea = idea;
	}
}
