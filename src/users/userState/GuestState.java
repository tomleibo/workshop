package users.userState;
import javax.persistence.*;

@Entity
@DiscriminatorValue("guest")
public class GuestState extends UserState {

    @Override
    public boolean isGuest(){
        return true;
    }

    @Override
    public String toString() {
        return "Guest";
    }
}
