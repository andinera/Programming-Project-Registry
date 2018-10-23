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
	
	
	public IdeaVote() {
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ideaId", referencedColumnName="id", nullable=false)
	public Idea getIdea() {
		return this.idea;
	}
	
	public void setIdea(Idea idea) {
		this.idea = idea;
	}
}