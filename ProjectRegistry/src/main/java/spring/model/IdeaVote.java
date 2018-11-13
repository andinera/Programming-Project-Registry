package spring.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.FetchType;


@Entity
@Table(name="ideaVotes", uniqueConstraints=@UniqueConstraint(columnNames= {"ideaId", "username"}))
public class IdeaVote extends Vote {

	private Idea idea;
	
	
	@SuppressWarnings("unused")
	private IdeaVote() {
	}
	
	public IdeaVote(User voter, boolean upVote, Idea idea) {
		super(voter, upVote);
		setIdea(idea);
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ideaId", referencedColumnName="id", nullable=false)
	public Idea getIdea() {
		return this.idea;
	}
	
	private void setIdea(Idea idea) {
		this.idea = idea;
	}
}
