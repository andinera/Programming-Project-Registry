package spring.builder;

import org.springframework.stereotype.Component;

import spring.model.Comment;
import spring.model.CommentVote;
import spring.model.User;


@Component("commentVoteBuilder")
public class CommentVoteBuilder {

private CommentVote commentVote = new CommentVote();
	
	
	public CommentVoteBuilder setVoter(User voter) {
		commentVote.setVoter(voter);
		return this;
	}
	
	public CommentVoteBuilder setUpVote(boolean upVote) {
		commentVote.setUpVote(upVote);
		return this;
	}
	
	public CommentVoteBuilder setComment(Comment comment) {
		commentVote.setComment(comment);
		return this;
	}
	
	public CommentVote build() {
		CommentVote vote = commentVote;
		commentVote = new CommentVote();
		return vote;
	}
}
