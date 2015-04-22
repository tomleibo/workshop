package users.userState;

public class SuperAdminState extends UserState {

    public Boolean isSuperAdmin(){
        return true;
    }

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
        return "Super Admin";
    }
}
