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
	private Set<CommentVote> votes = new HashSet<CommentVote>();

	
	@SuppressWarnings("unused")
	private Comment() {
	}
	
	public Comment(User commenter, GregorianCalendar dateTimePosted, String comment) {
		setCommenter(commenter);
		setDateTimePosted(dateTimePosted);
		setComment(comment);
	}
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	public int getId() {
		return this.id;
	}
	
	@SuppressWarnings("unused")
	private Comment setId(int id) {
		this.id = id;
		return this;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username", nullable=false)
	public User getCommenter() {
		return this.commenter;
	}
	
	private Comment setCommenter(User commenter) {
		this.commenter = commenter;
		return this;
	}
	
	@Column(name="dateTimePosted", nullable=false)
	public GregorianCalendar getDateTimePosted() {
		return this.dateTimePosted;
	}
	
	private Comment setDateTimePosted(GregorianCalendar dateTimePosted) {
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
	
	@SuppressWarnings("unused")
	private Comment setVotes(Set<CommentVote> votes) {
		this.votes = votes;
		return this;
	}
	
	public Comment addVote(CommentVote vote) {
		for (CommentVote v : getVotes()) {
			if (v.getVoter().getUsername().equals(vote.getVoter().getUsername())
					&& v.getComment().getId() == vote.getComment().getId()) {
				v.setUpVote(vote.getUpVote());
				return this;
			}
		}
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
