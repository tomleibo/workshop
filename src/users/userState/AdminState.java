package users.userState;

public class AdminState extends UserState {

    public boolean isAdmine(){
        return true;
    }

    public boolean isModerator(){
        return true;
    }

    public boolean isMember(){
        return true;
    }

    @Override
    public String toString() {
        return "Admin";
    }
}
