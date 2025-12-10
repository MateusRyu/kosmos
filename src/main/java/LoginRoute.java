import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public class LoginRoute {

    public static void handleLogin(BufferedReader in, OutputStream out) throws Exception {
        String line;
        int contentLength = 0;

        // Lê headers
        while (!(line = in.readLine()).isEmpty()) {
            if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.split(":")[1].trim());
            }
        }

        // Lê corpo
        char[] body = new char[contentLength];
        in.read(body);

        JSONObject json = new JSONObject(new String(body));

        String email = json.getString("email");
        String senha = json.getString("senha");

        boolean ok = email.equals("teste@teste.com") && senha.equals("123456");

        String resposta = ok ?
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
