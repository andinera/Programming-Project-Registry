package spring.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.GregorianCalendar;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="comments")
public class Comment {
	
	private int id;
	private User commenter;
	private GregorianCalendar dateTimePosted;
	private String comment;
	private Set<CommentVote> votes = new HashSet<CommentVote>(0);

	
	public Comment() {
	}
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username", nullable=false)
	public User getCommenter() {
		return this.commenter;
	}
	
	public void setCommenter(User commenter) {
		this.commenter = commenter;
	}
	
	@Column(name="dateTimePosted", nullable=false)
	public GregorianCalendar getDateTimePosted() {
		return this.dateTimePosted;
	}
	
	public void setDateTimePosted(GregorianCalendar dateTimePosted) {
		this.dateTimePosted = dateTimePosted;
	}
	
	@Column(name="comment", nullable=false, length=512)
	public String getComment() {
		return this.comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="comment")
	public Set<CommentVote> getVotes() {
		return this.votes;
	}
	
	public void setVotes(Set<CommentVote> votes) {
		this.votes = votes;
	}
	
	public int numVotes() {
		return this.getVotes().size();
	}
}
