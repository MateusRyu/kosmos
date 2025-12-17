import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final Map<String, String> activeSessions = new HashMap<>();

    public static String createSession(String username) {
        String sessionId = UUID.randomUUID().toString();
        activeSessions.put(sessionId, username);
        return sessionId;
    }


    public static String getUsername(String sessionId) {
        return activeSessions.get(sessionId);
    }


    public static void invalidateSession(String sessionId) {
        activeSessions.remove(sessionId);
    }
}