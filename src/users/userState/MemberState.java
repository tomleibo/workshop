package users.userState;

public class MemberState extends UserState {

    @Override
    public boolean isMember(){
        return true;
    }

    @Override
    public String toString() {
        return "Member";
    }
}
