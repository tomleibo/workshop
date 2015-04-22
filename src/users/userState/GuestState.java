package users.userState;

public class GuestState extends UserState {

    public boolean isGuest(){
        return true;
    }

    @Override
    public String toString() {
        return "Guest";
    }
}
