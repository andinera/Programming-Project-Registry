package spring.builder;

import org.springframework.stereotype.Component;

import spring.model.DevelopmentVote;
import spring.model.Development;
import spring.model.User;


@Component("developmentVoteBuilder")
public class DevelopmentVoteBuilder {

	private DevelopmentVote developmentVote = new DevelopmentVote();
	
	
	public DevelopmentVoteBuilder setVoter(User voter) {
		developmentVote.setVoter(voter);
		return this;
	}
	
	public DevelopmentVoteBuilder setUpVote(boolean upVote) {
		developmentVote.setUpVote(upVote);
		return this;
	}
	
	public DevelopmentVoteBuilder setDevelopment(Development development) {
		developmentVote.setDevelopment(development);
		return this;
	}
	
	public DevelopmentVote build() {
		DevelopmentVote vote = developmentVote;
		developmentVote = new DevelopmentVote();
		return vote;
	}
}
