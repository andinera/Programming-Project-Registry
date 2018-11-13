package spring.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name="developmentVotes", uniqueConstraints=@UniqueConstraint(columnNames= {"developmentId", "username"}))
public class DevelopmentVote extends Vote {

	private Development development;
	
	
	@SuppressWarnings("unused")
	private DevelopmentVote() {
	}
	
	public DevelopmentVote(User voter, boolean upVote, Development development) {
		super(voter, upVote);
		setDevelopment(development);
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="developmentId", referencedColumnName="id", nullable=false)
	public Development getDevelopment() {
		return this.development;
	}
	
	private void setDevelopment(Development development) {
		this.development = development;
	}
}
