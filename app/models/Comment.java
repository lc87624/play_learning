package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Comment extends Model {

	public String author;
	
	@Lob
	public String content;
	
	@ManyToOne
	public Post post;
	
	public Date postedAt;

	public Comment(Post post, String author, String content) {
		super();
		this.author = author;
		this.content = content;
		this.post = post;
		this.postedAt = new Date();
	}
	
}
