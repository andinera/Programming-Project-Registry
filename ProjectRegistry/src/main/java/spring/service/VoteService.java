package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import spring.model.IdeaVote;
import spring.model.DevelopmentVote;
import spring.model.CommentVote;
import spring.model.User;
import spring.model.Idea;
import spring.model.Development;
import spring.dao.DAO;
import spring.model.Comment;


@Service("voteService")
public class VoteService {
	
	@Autowired
	@Qualifier("dAO")
	private DAO dao;
	
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
