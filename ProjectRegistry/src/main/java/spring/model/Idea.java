package spring.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Set;

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

import spring.comparator.CommentComparatorByVote;
import spring.comparator.DevelopmentComparatorByVote;


/**
 * Models a user's idea.
 * 
 * @author Shane Lockwood
 *
 */
@Entity
@Table(name="ideas")
public class Idea {

	private int id;
	private String title;
	private String description;
	private GregorianCalendar datePosted;
	private GregorianCalendar dateModified;
	private User poster;
	private Set<IdeaVote> votes = new HashSet<IdeaVote>();
	private Set<Development> developments = new TreeSet<Development>(new DevelopmentComparatorByVote());
	private Set<Comment> comments = new TreeSet<Comment>(new CommentComparatorByVote());
	
	
	public Idea() {	
	}
	
	/**
	 * Construct an Idea.
	 * 
	 * @param title The String representing the title of the idea.
	 * @param description The String representing the description of the idea.
	 * @param poster The {@link spring.model.User} representing the poster of the idea.
	 * @param datePosted The GregorianCalendar representing the time the idea was created.
	 */
	public Idea(String title, String description, User poster, GregorianCalendar datePosted) {
		setTitle(title);
		setDescription(description);
		setPoster(poster);
		setDatePosted(datePosted);
		setDateModified(datePosted);
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
	private void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Returns the title.
	 * 
	 * @return String
	 */
	@Column(name="title", nullable=false, length=255)
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Returns the description.
	 * 
	 * @return String
	 */
	@Column(name="description", nullable=false, length=2048)
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Sets/modifies the description.
	 * 
	 * @param description The String representing the description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Returns the datetime the idea was posted.
	 * 
	 * @return GregorianCalendar
	 */
	@Column(name="datePosted", nullable=false)
	public GregorianCalendar getDatePosted() {
		return this.datePosted;
	}
	
	private void setDatePosted(GregorianCalendar datePosted) {
		this.datePosted = datePosted;
	}
	
	/**
	 * Returns the datetime the idea was modified.
	 * 
	 * @return GregorianCalendar
	 */
	@Column(name="dateModified", nullable=false)
	public GregorianCalendar getDateModified() {
		return this.dateModified;
	}
	
	/**
	 * Sets/modifies the datetime the idea was modified.
	 * 
	 * @param dateModified The GregorianCalendar representing the datetime the idea was modified.
	 */
	public void setDateModified(GregorianCalendar dateModified) {
		this.dateModified = dateModified;
	}
	
	/**
	 * Returns the poster.
	 * 
	 * @return {@link spring.model.User}
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username", nullable=false)
	public User getPoster() {
		return this.poster;
	}
	
	private void setPoster(User poster) {
		this.poster = poster;
	}
	
	/**
	 * Returns the votes associated with this Idea.
	 * 
	 * @return Set&lt;{@link spring.model.IdeaVote}&gt;
	 */
	@OneToMany(fetch=FetchType.LAZY, mappedBy="idea", cascade=CascadeType.ALL)
	public Set<IdeaVote> getVotes() {
		return this.votes;
	}
	
	@SuppressWarnings("unused")
	private void setVotes(Set<IdeaVote> votes) {
		this.votes = votes;
	}
	
	/**
	 * Appends the vote to the List of previous votes. If a vote mapped to the same 
	 * {@link spring.model.Idea} and {@link spring.model.User} exists, the existing vote is 
	 * modified.
	 * 
	 * @param vote {@link spring.model.IdeaVote}
	 */
	public void addVote(IdeaVote vote) {
		for (IdeaVote v : getVotes()) {
			if (v.getVoter().getUsername().equals(vote.getVoter().getUsername())
					&& v.getIdea().getId() == vote.getIdea().getId()) {
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
		for (IdeaVote vote : this.getVotes()) {
			if (vote.getUpVote()) {
				count++;
			} else {
				count --;
			}
		}
		return count;
	}
	
	/**
	 * Returns the mapped developments.
	 * 
	 * @return Set&lt;{@link spring.model.Development}&gt;
	 */
	@OneToMany(fetch=FetchType.LAZY, mappedBy="idea", cascade=CascadeType.ALL)
	public Set<Development> getDevelopments() {
		return this.developments;
	}
	
	@SuppressWarnings("unused")
	private void setDevelopments(Set<Development> developments) {
		this.developments = developments;
	}
	
	/**
	 * Appends the {@link spring.model.Development} to the List of previous developments. If a 
	 * {@link spring.model.Development} mapped to the same 
	 * {@link spring.model.User} exists, nothing happens.
	 * 
	 * @param development The {@link spring.model.Development} to append.
	 */
	public void addDevelopment(Development development) {
		for (Development d : getDevelopments()) {
			if (d.getDeveloper().getUsername().equals(development.getDeveloper().getUsername())) {
				return;
			}
		}
		this.developments.add(development);
	}
	
	/**
	 * Returns the mapped {@link spring.model.Comment}s.
	 * 
	 * @return Set&lt;{@link spring.model.Comment}&gt;
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="comments", nullable=false)
	public Set<Comment> getComments() {
		return this.comments;
	}
	
	@SuppressWarnings("unused")
	private void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	/**
	 * Appends the {@link spring.model.Comment} to the List of previous comments. 
	 * 
	 * @param comment The {@link spring.model.Comment} to append.
	 */
	public void addComment(Comment comment) {
		this.comments.add(comment);
	}
}
