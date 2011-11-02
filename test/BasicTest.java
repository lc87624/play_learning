import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

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

}
