package users.userState;

import content.SubForum;
import users.User;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "userState")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="user_state")
public class UserState {
    @Id
    @GeneratedValue
    @Column(name = "state_id")
    private int id;
    @Column(name = "status")
	private String status;

	public UserState() {
		this.status = "";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isSuperAdmin(){
		return false;
	}

	public boolean isAdmin(){
		return false;
	}

	public boolean isModerator(){
		return false;
	}

	public boolean isMember(){
		return false;
	}

	public boolean isGuest(){
		return false;
	}

	public boolean addManagedSubForum(SubForum subForum) {
		return false;
	}

	public boolean removeManagedSubForum(SubForum subForum) {
		return false;
	}

	public List<SubForum> getManagedSubForums() {
		return null;
	}

	public int getNumberOfManagedSubForums() {
		return -1;
	}

}
