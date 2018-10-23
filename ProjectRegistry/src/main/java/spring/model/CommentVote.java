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
	
	
	public CommentVote() {
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="commentId", referencedColumnName="id", nullable=false)
	public Comment getComment() {
		return this.comment;
	}
	
	public void setComment(Comment comment) {
		this.comment = comment;
	}
}
