
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class KosmosServer {
    private final int port;
    private final Path resourcePath;

    public KosmosServer(int port) {
        this.port = port;
        this.resourcePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources");
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("O Kosmos está rodando em http://localhost:" + port);
            while (true) {
                Socket client = serverSocket.accept();
                handleClient(client);
            }

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void handleClient(Socket client) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            OutputStream out = client.getOutputStream();

            String requestLine = in.readLine();
            System.out.print("Requisição Recebida " + requestLine + "\n");

            if (requestLine == null) return;

            if (requestLine.startsWith("GET")) {
                send(out, requestLine.split(" ")[1]);
            } else if (requestLine.startsWith("POST /api/login")) {
                LoginRoute.handleLogin(in, out);
            } else {
                sendNotFound(out);
            }
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void send(OutputStream out, String request) throws IOException {
        if (Objects.equals(request, "/")) {
            request = "index.html";
        }

        Path filePath = Paths.get(String.valueOf(this.resourcePath), request);
        String mimeType = getContentType(request);

        if (Files.exists(filePath)) {
            byte[] htmlBytes = Files.readAllBytes(filePath);

            String headers = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: " + mimeType + "\r\n"
                    + "Content-Length: " + htmlBytes.length + "\r\n"
                    + "\r\n";

            out.write(headers.getBytes(StandardCharsets.UTF_8));
            out.write(htmlBytes);
        } else {
            System.err.println("Arquivo HTML não encontrado: " + filePath.toAbsolutePath());
            sendNotFound(out);
        }
    }

    private void sendNotFound(OutputStream out) throws IOException {
        String response = "HTTP/1.1 404 Not Found\r\n"
                + "Conntent-Type: text/plain\r\n"
                + "Content-Length: 9\r\n\r\n"
                + "Not Found";
        out.write(response.getBytes());
    }


    private static void sendRedirectResponse(OutputStream out, String newLocation) throws IOException {
        String responseHttp =
                "HTTP/1.1 302 Found\r\n"
                + "Location: " + newLocation + "\r\n"
                + "Content-Type: text/html\r\n"
                + "Content-Length: 0\r\n"
                + "\r\n";

        out.write(responseHttp.getBytes(StandardCharsets.UTF_8));
    }


    public String getContentType(String filePath) {
        String[] filePathArray = filePath.split("\\.");

        if (filePathArray.length == 1) {
            return "application/octet-stream";
        }

        String extension = filePathArray[1].toLowerCase();

        return switch (extension) {
            case "html" -> "text/html; charset=utf-8";
            case "css" -> "text/css";
            case "js" -> "application/javascript";
            case "jpg", ".jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "mp4" -> "video/mp4";
            default -> "application/octet-stream";
        };
    }
}
