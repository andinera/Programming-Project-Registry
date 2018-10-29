package spring.service;

import org.springframework.stereotype.Component;

import spring.model.IdeaVote;
import spring.model.DevelopmentVote;
import spring.model.CommentVote;
import spring.model.User;
import spring.model.Idea;
import spring.model.Development;
import spring.model.Comment;


@Component("voteService")
public class VoteService {
	
	
	public IdeaVote create(User voter, boolean upVote, Idea idea) {
		return new IdeaVote(voter, upVote, idea);
	}
	
	public DevelopmentVote create(User voter, boolean upVote, Development development) {
		return new DevelopmentVote(voter, upVote, development);
	}
	
	public CommentVote create(User voter, boolean upVote, Comment comment) {
		return new CommentVote(voter, upVote, comment);
	}
}
