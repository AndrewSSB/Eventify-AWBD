package com.example.eventify.Kernel.Constants;

public class Constants {
    // Token
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final long EXPIRATION_TIME = 7_200_000; // 2 hours

    // Services
    // Auth
    public static final String InvalidUsernameOrPassword = "Invalid username or password";

    // Event
    public static final String InvalidEvent = "There is no event with the specified Id";

    // Venue
    public static final String InvalidVenue = "There is no venue with the specified Id";

    // Tag
    public static final String InvalidTag = "There is no tag with the specified Id";
    public static final String TagNameNull = "TagName cannot be null";

    // User
    public static final String InvalidUser = "Invalid user";

    // Registration
    public static final String RegistrationLimit = "The event has reached its registration limit";

    // Generic messages
    public static final String GenericMessage = "Something wen wrong, please try again later";
    public static final String ContactTeam = "Something wen wrong, if the error persists contact the development team";

    // Custom user details
    public static final String UserNotFound = "User not found with username or email: ";

    // Validators
    public static final String INVALID_EMAIL_FORMAT = "Invalid email format";
    public static final String INVALID_PASSWORD = "Password must contain at least 8 characters";
    public static final String PhoneNumberRegex = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";
    public static final String INVALID_RATING = "Rating should be from 0 to 5";
}
