package spring.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.GregorianCalendar;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
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
	
	public Comment setId(int id) {
		this.id = id;
		return this;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username", nullable=false)
	public User getCommenter() {
		return this.commenter;
	}
	
	public Comment setCommenter(User commenter) {
		this.commenter = commenter;
		return this;
	}
	
	@Column(name="dateTimePosted", nullable=false)
	public GregorianCalendar getDateTimePosted() {
		return this.dateTimePosted;
	}
	
	public Comment setDateTimePosted(GregorianCalendar dateTimePosted) {
		this.dateTimePosted = dateTimePosted;
		return this;
	}
	
	@Column(name="comment", nullable=false, length=512)
	public String getComment() {
		return this.comment;
	}
	
	public Comment setComment(String comment) {
		this.comment = comment;
		return this;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="comment", cascade=CascadeType.ALL)
	public Set<CommentVote> getVotes() {
		return this.votes;
	}
	
	public Comment setVotes(Set<CommentVote> votes) {
		this.votes = votes;
		return this;
	}
	
	public Comment addVote(CommentVote vote) {
		this.getVotes().add(vote);
		return this;
	}
	
	public int voteCount() {
		int count = 0;
		for (CommentVote vote : this.getVotes()) {
			if (vote.getUpVote()) {
				count++;
			} else {
				count --;
			}
		}
		return count;
	}
}
