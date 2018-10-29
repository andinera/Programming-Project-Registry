package spring.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name="commentVotes", uniqueConstraints=@UniqueConstraint(columnNames= {"commentId", "username"}))
public class CommentVote extends Vote {

	private Comment comment;
	
	
	private CommentVote() {
	}
	
	public CommentVote(User voter, boolean upVote, Comment comment) {
		super(voter, upVote);
		setComment(comment);
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="commentId", referencedColumnName="id", nullable=false)
	public Comment getComment() {
		return this.comment;
	}
	
	private void setComment(Comment comment) {
		this.comment = comment;
	}
}
