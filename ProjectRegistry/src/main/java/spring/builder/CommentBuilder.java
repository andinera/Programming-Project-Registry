package spring.builder;

import java.util.GregorianCalendar;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

import spring.model.Comment;
import spring.model.Idea;
import spring.model.User;
import spring.model.CommentVote;

@Component("commentBuilder")
public class CommentBuilder {

	private Comment comment = new Comment();
	
	
	public CommentBuilder setId(int id) {
		comment.setId(id);
		return this;
	}
	
//	public CommentBuilder setIdea(Idea idea) {
//		comment.setIdea(idea);
//		return this;
//	}
	
	public CommentBuilder setCommenter(User commenter) {
		comment.setCommenter(commenter);
		return this;
	}
	
	public CommentBuilder setDateTimePosted(GregorianCalendar dateTimePosted) {
		comment.setDateTimePosted(dateTimePosted);
		return this;
	}
	
	public CommentBuilder setComment(String c) {
		comment.setComment(c);
		return this;
	}
	
	public CommentBuilder setVotes(Set<CommentVote> votes) {
		comment.setVotes(votes);
		return this;
	}
	
	public Comment build() {
		Comment newComment = comment;
		this.comment = new Comment();
		return newComment;
	}
}
