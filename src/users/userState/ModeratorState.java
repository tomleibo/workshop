package users.userState;

import content.SubForum;

import java.util.ArrayList;
import java.util.List;

public class ModeratorState extends UserState {

    private List<SubForum> subForums;

    public ModeratorState() {
        this.subForums = new ArrayList<>();
    }

    public boolean addSubForum(SubForum subForum) {
        return subForums.add(subForum);
    }

    public boolean removeSubForum(SubForum subForum) {
        return subForums.remove(subForum);
    }

    @Override
    public String toString() {
        return "Moderator";
    }
}
