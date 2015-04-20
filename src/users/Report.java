package users;

public class Report {

	private String title;
	private String content;
	private User reporter;
	private User reported;
	private java.sql.Date date;
	
	public Report(String title, String content, User reporter, User reported) {
		this.title = title;
		this.content = content;
		this.reporter = reporter;
		this.reporter = reported;
		date = new java.sql.Date(System.currentTimeMillis());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getReporter() {
		return reporter;
	}

	public void setReporter(User reporter) {
		this.reporter = reporter;
	}

	public User getReported() {
		return reported;
	}

	public void setReported(User reported) {
		this.reported = reported;
	}

	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		String message = "Title: " + title +
						 "\nTo: " + reported.getUserName() +
						 "\nContent: " + content;
		return message;
	}
}
