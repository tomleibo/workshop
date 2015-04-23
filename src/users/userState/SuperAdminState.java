package users.userState;

public class SuperAdminState extends UserState {

    @Override
    public boolean isSuperAdmin(){
        return true;
    }

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
        return "Super Admin";
    }
}
