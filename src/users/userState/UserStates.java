package users.userState;

public enum UserStates {

    SUPER_ADMIN,
    ADMIN,
    MODERATOR,
    MEMBER,
    GUEST;

    public static UserState newState(UserStates type) {
        switch (type) {
            case SUPER_ADMIN: return new SuperAdminState();
            case ADMIN: return new AdminState();
            case MODERATOR: return new ModeratorState();
            case MEMBER: return new MemberState();
            case GUEST: return new GuestState();
            default: return new MemberState();
        }
    }
}
