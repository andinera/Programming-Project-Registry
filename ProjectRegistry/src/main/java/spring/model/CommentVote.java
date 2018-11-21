package spring.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * Models a vote specific to a {@link spring.model.Comment}. Maintains a uniqueness constraint
 * ensuring duplicate entities with equivalent {@link spring.model.Comment} and 
 * {@link spring.model.User} mapping aren't persisted.
 * 
 * @author Shane Lockwood
 *
 */
@Entity
@Table(name="commentVotes", uniqueConstraints=@UniqueConstraint(columnNames= {"commentId", "username"}))
public class CommentVote extends Vote {

	private Comment comment;
	
	
	@SuppressWarnings("unused")
	private CommentVote() {
	}
	
	/**
	 * Construct a CommentVote.
	 * 
	 * @param voter The @{link spring.model.User} representing the voter.
	 * @param upVote The boolean representing true/false for upvote/downvote.
	 * @param comment The {@link spring.model.Comment} the vote is associated with.
	 */
	public CommentVote(User voter, boolean upVote, Comment comment) {
		super(voter, upVote);
		setComment(comment);
	}
	
	/**
	 * Returns the associated {@link spring.model.Comment}.
	 * 
	 * @return {@link spring.model.Comment}
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="commentId", referencedColumnName="id", nullable=false)
	public Comment getComment() {
		return this.comment;
	}
	
	private void setComment(Comment comment) {
		this.comment = comment;
	}
}
