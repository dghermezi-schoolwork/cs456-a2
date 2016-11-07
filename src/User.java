import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.tree.DefaultMutableTreeNode;

// Part of the composite pattern, as well as observer pattern
public class User extends DefaultMutableTreeNode implements UserComponent, Subject, Observer {
	private String id;
	private UserGUI gui;

	private List<Observer> followers;
	private List<Subject> followings;
	private List<Tweet> messages;
	private List<Tweet> newsfeed;

	public User(String id) {
		this.id = id;

		followers = new ArrayList<Observer>();
		followings = new ArrayList<Subject>();
		messages = new ArrayList<Tweet>();
		newsfeed = new ArrayList<Tweet>();

	}

	public String getID() {
		return id;
	}

	public List<Tweet> getNewsfeed() {
		return newsfeed;
	}

	public List<Tweet> getMessages() {
		return messages;
	}

	public List<Observer> getFollowers() {
		return followers;
	}

	public void tweet(String message) {
		messages.add(new Tweet(message, this));
		notifyObserver();
	}

	public void follow(User user) {
		for (Subject subject : followings) {
			if (subject != user && !(((User) subject).getID().equals(user.getID()))) {
				followings.add(user);
				user.register(this);
			}
		}

	}

	public void register(Observer o) {
		followers.add(o);
	}

	public void notifyObserver() {
		Tweet tweet = new Tweet(messages.get(messages.size() - 1).getMessage(), this);
		for (Observer o : followers) {
			o.update(tweet);
		}

	}

	public void update(Tweet tweet) {
		newsfeed.add(tweet);
		ListModel lm = new DefaultListModel();
		for (int i = newsfeed.size() - 1; i >= 0; i++) {
			((DefaultListModel) lm).addElement(newsfeed.get(i));
		}

		this.getGUI().newsFeed = new JList(lm);
	}

	public String toString() {
		return id;
	}

	public void setGUI(UserGUI gui) {
		this.gui = gui;
	}

	public UserGUI getGUI() {
		return gui;
	}

}
