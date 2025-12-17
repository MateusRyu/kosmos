import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class LoginRoute {

    public static void handleLogin(BufferedReader in, OutputStream out) throws Exception {
        String line;
        int contentLength = 0;

        while (!(line = in.readLine()).isEmpty()) {
            if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.split(":")[1].trim());
            }
        }

        char[] body = new char[contentLength];
        in.read(body);

        String[] rawData = new String(body).split("&");

        String email = "";
        String password = "";

        for (String dataString : rawData) {
            String[] data = dataString.split("=");

            String value = URLDecoder.decode(data[1], StandardCharsets.UTF_8.name());

            switch (data[0]) {
                case "email" -> email = value;
                case "password" -> password = value;
            }
        }

        com.kosmos.dao.UserDAO userDAO = new com.kosmos.dao.UserDAO();
        String storedPassword = "";
        storedPassword = userDAO.buscarSenhaPorEmail(email);

        if (PasswordHasher.verifyPassword(password, storedPassword)) {
            System.out.println("Login successful: " + email);
            String[] sessionCookie = {"sessionId", SessionManager.createSession(email)};
            KosmosServer.sendRedirectResponse(out, "/main/main.html", sessionCookie);
        } else {
            System.out.println("Login failed: " + email);
            KosmosServer.sendRedirectResponse(out, "/");
        }
    }
}
