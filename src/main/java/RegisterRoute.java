import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class RegisterRoute {
    public static void handleRegistration(BufferedReader in, OutputStream out) throws IOException {
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

        String[] fullname = {};
        String name = "";
        String lastName = "";
        String username = "";
        String password = "";
        String email = "";

        for (String dataString : rawData) {
            String[] data = dataString.split("=");

            String value = URLDecoder.decode(data[1], StandardCharsets.UTF_8.name());

            switch (data[0]) {
                case "name" -> fullname = value.split(" ");
                case "username" -> username = value;
                case "password" -> password = value;
                case "email" -> email = value;
            }
        }

        for (int i = 0; i < fullname.length; i++) {
            if (i == fullname.length - 1) {
                lastName = fullname[i];
            } else {
                name += " " + fullname[i];
            }
        }

        com.kosmos.dao.UserDAO userDAO = new com.kosmos.dao.UserDAO();
        boolean sucess = false;
        try {
            sucess = userDAO.registerUser(username, name, lastName, email, PasswordHasher.hashPassword(password), "default");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        String resposta = sucess ?
                "{\"status\":\"ok\"}" :
                "{\"status\":\"erro\"}";

        String responseHttp =
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: application/json\r\n" +
                        "Content-Length: " + resposta.length() + "\r\n" +
                        "\r\n" +
                        resposta;

        out.write(responseHttp.getBytes());
    }
}
