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

import spring.Comparator.CommentComparatorByVote;
import spring.Comparator.DevelopmentComparatorByVote;


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
	
	public Idea(String title, String description, User poster, GregorianCalendar datePosted) {
		setTitle(title);
		setDescription(description);
		setPoster(poster);
		setDatePosted(datePosted);
		setDateModified(datePosted);
	}
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	public int getId() {
		return this.id;
	}
	
	@SuppressWarnings("unused")
	private Idea setId(int id) {
		this.id = id;
		return this;
	}
	
	@Column(name="title", nullable=false, length=255)
	public String getTitle() {
		return this.title;
	}
	
	public Idea setTitle(String title) {
		this.title = title;
		return this;
	}
	
	@Column(name="description", nullable=false, length=2048)
	public String getDescription() {
		return this.description;
	}
	
	public Idea setDescription(String description) {
		this.description = description;
		return this;
	}
	
	@Column(name="datePosted", nullable=false)
	public GregorianCalendar getDatePosted() {
		return this.datePosted;
	}
	
	private Idea setDatePosted(GregorianCalendar datePosted) {
		this.datePosted = datePosted;
		return this;
	}
	
	@Column(name="dateModified", nullable=false)
	public GregorianCalendar getDateModified() {
		return this.dateModified;
	}
	
	public Idea setDateModified(GregorianCalendar dateModified) {
		this.dateModified = dateModified;
		return this;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username", nullable=false)
	public User getPoster() {
		return this.poster;
	}
	
	private Idea setPoster(User poster) {
		this.poster = poster;
		return this;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="idea", cascade=CascadeType.ALL)
	public Set<IdeaVote> getVotes() {
		return this.votes;
	}
	
	@SuppressWarnings("unused")
	private Idea setVotes(Set<IdeaVote> votes) {
		this.votes = votes;
		return this;
	}
	
	public Idea addVote(IdeaVote vote) {
		for (IdeaVote v : getVotes()) {
			if (v.getVoter().getUsername().equals(vote.getVoter().getUsername())
					&& v.getIdea().getId() == vote.getIdea().getId()) {
				v.setUpVote(vote.getUpVote());
				return this;
			}
		}
		this.getVotes().add(vote);
		return this;
	}
	
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
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="idea", cascade=CascadeType.ALL)
	public Set<Development> getDevelopments() {
		return this.developments;
	}
	
	public Idea setDevelopments(Set<Development> developments) {
		this.developments = developments;
		return this;
	}
	
	public Idea addDevelopment(Development development) {
		this.developments.add(development);
		return this;
	}
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="comments", nullable=false)
	public Set<Comment> getComments() {
		return this.comments;
	}
	
	public Idea setComments(Set<Comment> comments) {
		this.comments = comments;
		return this;
	}
	
	public Idea addComment(Comment comment) {
		this.comments.add(comment);
		return this;
	}
}
