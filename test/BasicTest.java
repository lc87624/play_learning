import java.util.List;

import models.Comment;
import models.Post;
import models.Tag;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class BasicTest extends UnitTest {

	@Before
	public void setUp(){
		Fixtures.deleteDatabase();
	}
    @Test
    public void aVeryImportantThingToTest() {
        assertEquals(2, 1 + 1);
    }
    
    @Test
    public void createAndRetrieveUser(){
    	
    	new User("lc87624@gmail.com", "secret", "Liu Chong").save();
    	
    	User user = User.find("byEmail", "lc87624@gmail.com").first();
    	
    	assertNotNull(user);
    	assertEquals("Liu Chong", user.fullName);
    }
    
    @Test
    public void createPost() {
        // Create a new user and save it
        User bob = new User("bob@gmail.com", "secret", "Bob").save();
        
        // Create a new post
        new Post(bob, "My first post", "Hello world").save();
        
        // Test that the post has been created
        assertEquals(1, Post.count());
        
        // Retrieve all posts created by Bob
        List<Post> bobPosts = Post.find("byAuthor", bob).fetch();
        
        // Tests
        assertEquals(1, bobPosts.size());
        Post firstPost = bobPosts.get(0);
        assertNotNull(firstPost);
        assertEquals(bob, firstPost.author);
        assertEquals("My first post", firstPost.title);
        assertEquals("Hello world", firstPost.content);
        assertNotNull(firstPost.postedAt);
    }
    
    @Test
    public void postComments(){
    	User user = new User("lc87624@gmail.com", "hello1234", "Liu Chong").save();
    	
    	Post post = new Post(user, "First blog", "Hello World").save();
    	
    	new Comment(post, "mayuyu", "I like it.").save();
    	
    	new Comment(post, "yukirin", "Me too").save();
    	
    	List<Comment> commentList = Comment.find("byPost", post).fetch();
    	
    	assertEquals(2, commentList.size());
    	
    	Comment comment1 = commentList.get(0);
    	Comment comment2 = commentList.get(1);
    	
    	assertEquals("mayuyu", comment1.author);
    	assertEquals("I like it.", comment1.content);
    	assertNotNull(comment1.postedAt);
    	assertNotNull(comment1.post);
    	
    	assertEquals("yukirin", comment2.author);
    	assertEquals("Me too", comment2.content);
    	assertNotNull(comment2.postedAt);
    	assertNotNull(comment2.post);
    }
    
    @Test
    public void addComments(){
    	User user = new User("lc87624@gmail.com", "hello1234", "Liu Chong").save();
    	
    	Post post = new Post(user, "First blog", "Hello World").save();
    	
    	post.addComment("mayuyu", "I like it.");
    	post.addComment("yukirin", "Me too.");
    	
    	assertEquals(1, User.count());
    	assertEquals(1, Post.count());
    	assertEquals(2, Comment.count());
    	
    	assertEquals("mayuyu", post.comments.get(0).author);
    	assertEquals("yukirin", post.comments.get(1).author);
    	
    	post.delete();
    	
    	assertEquals(1, User.count());
    	assertEquals(0, Post.count());
    	assertEquals(0, Comment.count());
    	
    }
    
    @Test
    public void fullTest(){
    	Fixtures.loadModels("data.yml");
    	
    	assertEquals(2, User.count());
    	assertEquals(3, Post.count());
    	assertEquals(3, Comment.count());
    	
    	assertNotNull(User.connect("bob@gmail.com", "secret"));
    	assertNotNull(User.connect("jeff@gmail.com", "secret"));
    	assertNull(User.connect("bob1@gmail.com", "secret"));
    	assertNull(User.connect("jeff@gmail.com", "badpassword"));
    	
    	List<Post> bobPosts = Post.find("author.email", "bob@gmail.com").fetch();
    	assertEquals(2, bobPosts.size());
    	
    	List<Comment> bobComment = Comment.find("post.author.email", "bob@gmail.com").fetch();
    	assertEquals(3, bobComment.size());
    	
    	Post firstPost = Post.find("order by postedAt desc").first();
    	assertEquals("About the model layer", firstPost.title);
    	
    	firstPost.addComment("mayuyu", "I come again");
    	assertEquals(3, firstPost.comments.size());
    	assertEquals(3, Comment.count("byPost", firstPost));
    	
    }
    
    @Test
    public void testTags(){
    	User bob = new User("lc87624@gmail.com", "secret", "Bob wayne").save();
    	Post post1 = new Post(bob, "My first blog", "Hello world").save();
    	Post post2 = new Post(bob, "My second blog", "Hello world").save();
    	
    	assertEquals(0, Post.findTaggedWith("Red").size());
    	
    	post1.tagItWith("Red").tagItWith("Blue").save();
    	post2.tagItWith("Red").tagItWith("Green").save();
    	
    	assertEquals(2, Post.findTaggedWith("Red").size());
    	assertEquals(1, Post.findTaggedWith("Blue").size());
    	assertEquals(1, Post.findTaggedWith("Green").size());
    }
    
}
