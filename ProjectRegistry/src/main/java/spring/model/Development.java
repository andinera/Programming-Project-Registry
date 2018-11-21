package spring.model;

import java.util.Set;
import java.util.HashSet;

import static javax.persistence.GenerationType.IDENTITY;

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
import javax.persistence.UniqueConstraint;


/**
 * Models a user's development. Maintains a uniqueness constraint ensuring duplicate entities 
 * with equivalent {@link spring.model.Idea} and {@link spring.model.User} mapping aren't 
 * persisted.
 * 
 * @author Shane Lockwood
 *
 */
@Entity
@Table(name="developments", uniqueConstraints=@UniqueConstraint(columnNames={"ideaId", "username"}))
public class Development {
	
	private int id;
	private Idea idea;
	private String link;
	private User developer;
	private Set<DevelopmentVote> votes = new HashSet<DevelopmentVote>();

	
	@SuppressWarnings("unused")
	private Development() {
	}
	
	/**
	 * Construct a Development.
	 * 
	 * @param developer The {@link spring.model.User} creating the development.
	 * @param idea The {@link spring.model.Idea} the Development is asociated with.
	 * @param link The String representing the link to the developer's website.
	 */
	public Development(User developer, Idea idea, String link) {
		setDeveloper(developer);
		setIdea(idea);
		setLink(link);
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
	 * Returns the associated {@link spring.model.Idea}.
	 * 
	 * @return {@link spring.model.Idea}
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ideaId", referencedColumnName="id", nullable=false)
	public Idea getIdea() {
		return this.idea;
	}
	
	private void setIdea(Idea idea) {
		this.idea = idea;
	}
	
	/**
	 * Returns the link to the developer's website.
	 * 
	 * @return String
	 */
	@Column(name="link", nullable=false, length=256)
	public String getLink() {
		return this.link;
	}
	
	/**
	 * Sets/modifies the link to the developer's website.
	 * 
	 * @param link The String representng the developer's website.
	 */
	public void setLink(String link) {
		this.link = link;
	}
	
	/**
	 * Returns the developer.
	 * 
	 * @return {@link spring.model.User}
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username", nullable=false)
	public User getDeveloper() {
		return this.developer;
	}
	
	private void setDeveloper(User developer) {
		this.developer = developer;
	}
	
	/**
	 * Returns the votes associated with this Development.
	 * 
	 * @return Set&lt;{@link spring.model.DevelopmentVote}&gt;
	 */
	@OneToMany(fetch=FetchType.LAZY, mappedBy="development", cascade=CascadeType.ALL)
	public Set<DevelopmentVote> getVotes() {
		return this.votes;
	}
	
	@SuppressWarnings("unused")
	private void setVotes(Set<DevelopmentVote> votes) {
		this.votes = votes;
	}
	
	/**
	 * Appends the vote to the List of previous votes. If a vote mapped to the same 
	 * {@link spring.model.User} exists, the existing vote is modified.
	 * 
	 * @param vote {@link spring.model.DevelopmentVote}
	 */
	public void addVote(DevelopmentVote vote) {
		for (DevelopmentVote v : getVotes()) {
			if (v.getVoter().getUsername().equals(vote.getVoter().getUsername())
					&& v.getDevelopment().getId() == vote.getDevelopment().getId()) {
				v.setUpVote(vote.getUpVote());
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
		for (DevelopmentVote vote : this.getVotes()) {
			if (vote.getUpVote()) {
				count++;
			} else {
				count --;
			}
		}
		return count;
	}
}
