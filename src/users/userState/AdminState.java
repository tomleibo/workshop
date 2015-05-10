package users.userState;

import javax.persistence.*;

@Entity
@DiscriminatorValue("admin")
public class AdminState extends UserState {
    public AdminState(){}
    @Override
    public boolean isAdmin(){
        return true;
    }

    @Override
    public boolean isModerator(){
        return true;
    }

    @Override
    public boolean isMember(){
        return true;
    }

    @Override
    public String toString() {
        return "Admin";
    }
}
