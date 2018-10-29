package spring.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
abstract public class Vote {
	
	private int id;
	private User voter;
	private boolean upVote;
	
	
	protected Vote() {
	}
	
	public Vote(User voter, boolean upVote) {
		setVoter(voter);
		setUpVote(upVote);
	}

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	public int getId() {
		return this.id;
	}
	
	private void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username", nullable=false)
	public User getVoter() {
		return this.voter;
	}
	
	private void setVoter(User voter) {
		this.voter = voter;
	}
	
	@Column(name="upVote", nullable=false)
	public boolean getUpVote() {
		return this.upVote;
	}
	
	public void setUpVote(boolean upVote) {
		this.upVote = upVote;
	}
}
