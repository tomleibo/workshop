package users.userState;

import javax.persistence.*;

@Entity
@DiscriminatorValue("member")
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
