package users;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name="report")
public class Report {
	@Id
    @GeneratedValue
	@Column(name="report_id")
	public int id;
	@Column(name="title")
	private String title;
	@Column(name="content")
	private String content;
	@ManyToOne
	@JoinColumn(name="reporter")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private User reporter;
	@OneToOne
	@JoinColumn(name="reported")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private User reported;
	@Column(name="date")
	@Temporal(TemporalType.DATE)
	private java.util.Date date;

	public Report(){}
	public Report(String title, String content, User reporter, User reported) {
		this.title = title;
		this.content = content;
		this.reporter = reporter;
		this.reported = reported;
		this.date = new java.sql.Date(System.currentTimeMillis());
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public User getReporter() {
		return reporter;
	}

	public User getReported() {
		return reported;
	}

	public java.util.Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		String message = "Title: " + title +
						 "\nTo: " + reported.getUsername() +
						 "\nContent: " + content;
		return message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Report report = (Report) o;

		if (!title.equals(report.title)) return false;
		if (!content.equals(report.content)) return false;
		if (!reporter.equals(report.reporter)) return false;
		if (!reported.equals(report.reported)) return false;
		return date.equals(report.date);
	}

	@Override
	public int hashCode() {
		int result = title.hashCode();
		result = 31 * result + content.hashCode();
		result = 31 * result + reporter.hashCode();
		result = 31 * result + reported.hashCode();
		result = 31 * result + date.hashCode();
		return result;
	}
}
