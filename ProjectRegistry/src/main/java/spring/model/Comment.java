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


/**
 * Model's a user's comment.
 * 
 * @author Shane Lockwood
 *
 */
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
	
	/**
	 * Construct a Comment.
	 * 
	 * @param commenter The {@link spring.model.User} creating the comment.
	 * @param dateTimePosted The GregorianCalendar of the time the comment is created.
	 * @param comment The String representing the comment.
	 */
	public Comment(User commenter, GregorianCalendar dateTimePosted, String comment) {
		setCommenter(commenter);
		setDateTimePosted(dateTimePosted);
		setComment(comment);
	}
	
	/**
	 * Returns the id. The id is auto-generated when this object is persisted.
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
	private Comment setId(int id) {
		this.id = id;
		return this;
	}
	
	/**
	 * Returns the commenter.
	 * 
	 * @return {@link spring.model.User}
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username", nullable=false)
	public User getCommenter() {
		return this.commenter;
	}
	
	private Comment setCommenter(User commenter) {
		this.commenter = commenter;
		return this;
	}
	
	/**
	 * Returns the datetime the comment was posted.
	 * 
	 * @return GregorianCalendar
	 */
	@Column(name="dateTimePosted", nullable=false)
	public GregorianCalendar getDateTimePosted() {
		return this.dateTimePosted;
	}
	
	private Comment setDateTimePosted(GregorianCalendar dateTimePosted) {
		this.dateTimePosted = dateTimePosted;
		return this;
	}
	
	/**
	 * Returns the comment.
	 * 
	 * @return String
	 */
	@Column(name="comment", nullable=false, length=512)
	public String getComment() {
		return this.comment;
	}
	
	/**
	 * Sets/modifies the comment.
	 * 
	 * @param comment The String representing the comment.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * Returns a Set of mapped votes;
	 * 
	 * @return Set&lt;{@link spring.model.CommentVote}&gt;
	 */
	@OneToMany(fetch=FetchType.LAZY, mappedBy="comment", cascade=CascadeType.ALL)
	public Set<CommentVote> getVotes() {
		return this.votes;
	}
	
	@SuppressWarnings("unused")
	private Comment setVotes(Set<CommentVote> votes) {
		this.votes = votes;
		return this;
	}
	
	/**
	 * Appends the vote to the List of previous votes. If a vote mapped to the same 
	 * {@link spring.model.User} exists, the existing vote is modified.
	 * 
	 * @param vote {@link spring.model.CommentVote}
	 */
	public void addVote(CommentVote vote) {
		for (CommentVote v : getVotes()) {
			if (v.getVoter().getUsername().equals(vote.getVoter().getUsername())
					&& v.getComment().getId() == vote.getComment().getId()) {
				v.setUpVote(vote.getUpVote());
				return;
			}
		}
		this.getVotes().add(vote);
	}
	
	/**
	 * Returns the sum of mapped votes with a true upVote subtracted by the sum of mapped votes
	 * with a false upVote.
	 * 
	 * @return int.
	 */
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
