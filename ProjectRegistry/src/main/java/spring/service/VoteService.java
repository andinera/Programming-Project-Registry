package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import spring.builder.IdeaVoteBuilder;
import spring.builder.DevelopmentVoteBuilder;
import spring.builder.CommentVoteBuilder;
import spring.model.IdeaVote;
import spring.model.DevelopmentVote;
import spring.model.CommentVote;
import spring.model.User;
import spring.model.Idea;
import spring.model.Development;
import spring.model.Comment;


@Component("voteService")
@ComponentScan("spring.builder")
public class VoteService {

	@Autowired
	private IdeaVoteBuilder ideaVoteBuilder;
	
	@Autowired
	private DevelopmentVoteBuilder developmentVoteBuilder;
	
	@Autowired
	private CommentVoteBuilder commentVoteBuilder;
	
	
	public IdeaVote buildVote(User voter, boolean upVote, Idea idea) {
		return ideaVoteBuilder.setVoter(voter)
							  .setUpVote(upVote)
							  .setIdea(idea)
							  .build();
	}
	
	public DevelopmentVote buildVote(User voter, boolean upVote, Development development) {
		return developmentVoteBuilder.setVoter(voter)
									 .setUpVote(upVote)
									 .setDevelopment(development)
									 .build();
	}
	
	public CommentVote buildVote(User voter, boolean upVote, Comment comment) {
		return commentVoteBuilder.setVoter(voter)
									 .setUpVote(upVote)
									 .setComment(comment)
									 .build();
	}
}
