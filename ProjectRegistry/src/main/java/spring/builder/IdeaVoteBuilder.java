package spring.builder;

import org.springframework.stereotype.Component;

import spring.model.IdeaVote;
import spring.model.User;
import spring.model.Idea;


@Component("ideaVoteBuilder")
public class IdeaVoteBuilder {

	private IdeaVote ideaVote = new IdeaVote();
	
	
	public IdeaVoteBuilder setVoter(User voter) {
		ideaVote.setVoter(voter);
		return this;
	}
	
	public IdeaVoteBuilder setUpVote(boolean upVote) {
		ideaVote.setUpVote(upVote);
		return this;
	}
	
	public IdeaVoteBuilder setIdea(Idea idea) {
		ideaVote.setIdea(idea);
		return this;
	}
	
	public IdeaVote build() {
		IdeaVote vote = ideaVote;
		ideaVote = new IdeaVote();
		return vote;
	}
}
