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
	
	public Development(User developer, Idea idea, String link) {
		setDeveloper(developer);
		setIdea(idea);
		setLink(link);
	}
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	public int getId() {
		return this.id;
	}
	
	@SuppressWarnings("unused")
	private Development setId(int id) {
		this.id = id;
		return this;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ideaId", referencedColumnName="id", nullable=false)
	public Idea getIdea() {
		return this.idea;
	}
	
	private Development setIdea(Idea idea) {
		this.idea = idea;
		return this;
	}
	
	@Column(name="link", nullable=false, length=256)
	public String getLink() {
		return this.link;
	}
	
	public Development setLink(String link) {
		this.link = link;
		return this;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username", nullable=false)
	public User getDeveloper() {
		return this.developer;
	}
	
	private Development setDeveloper(User developer) {
		this.developer = developer;
		return this;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="development", cascade=CascadeType.ALL)
	public Set<DevelopmentVote> getVotes() {
		return this.votes;
	}
	
	@SuppressWarnings("unused")
	private Development setVotes(Set<DevelopmentVote> votes) {
		this.votes = votes;
		return this;
	}
	
	public Development addVote(DevelopmentVote vote) {
		for (DevelopmentVote v : getVotes()) {
			if (v.getVoter().getUsername().equals(vote.getVoter().getUsername())
					&& v.getDevelopment().getId() == vote.getDevelopment().getId()) {
				v.setUpVote(vote.getUpVote());
				return this;
			}
		}
		this.getVotes().add(vote);
		return this;
	}
	
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
