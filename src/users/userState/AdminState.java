package users.userState;

public class AdminState extends UserState {

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
