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


/**
 * Service for creating {@link spring.model.IdeaVote}s, {@link spring.model.DevelopmentVote}s, and
 * {@link spring.model.CommentVote}s.
 * 
 * @author Shane Lockwood
 *
 */
@Service("voteService")
public class VoteService {
	
	@Autowired
	@Qualifier("dAO")
	private DAO dao;
	
	/**
	 * Creates and returns an {@link spring.model.IdeaVote}.
	 * 
	 * @param voter The {@link spring.model.User} representing the voter.
	 * @param upVote The boolean representing the vote status.
	 * @param idea The {@link spring.model.Idea} representing the associated idea.
	 * @return {@link spring.model.IdeaVote}
	 */
	public IdeaVote create(User voter, boolean upVote, Idea idea) {
		return new IdeaVote(voter, upVote, idea);
	}
	
	/**
	 * Creates and returns a {@link spring.model.DevelopmentVote}.
	 * 
	 * @param voter The {@link spring.model.User} representing the voter.
	 * @param upVote The boolean representing the vote status.
	 * @param development The {@link spring.model.Development} representing the associated 
	 * development.
	 * @return {@link spring.model.DevelopmentVote}
	 */
	public DevelopmentVote create(User voter, boolean upVote, Development development) {
		return new DevelopmentVote(voter, upVote, development);
	}
	
	/**
	 * Creates and returns a {@link spring.model.CommentVote}.
	 * 
	 * @param voter The {@link spring.model.User} representing the voter.
	 * @param upVote The boolean representing the vote status.
	 * @param comment The {@link spring.model.Comment} representing the associated comment.
	 * @return {@link spring.model.CommentVote}
	 */
	public CommentVote create(User voter, boolean upVote, Comment comment) {
		return new CommentVote(voter, upVote, comment);
	}
}
