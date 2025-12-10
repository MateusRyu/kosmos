
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class KosmosServer {
    private final int port;

    public KosmosServer(int port) {
        this.port = port;
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
            System.out.print("Requisição Recebida " + requestLine);

            if (requestLine == null) return;

            if (requestLine.startsWith("POST /api/login")) {
                LoginRoute.handleLogin(in, out);
            } else {
                sendNotFound(out);
            }

            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotFound(OutputStream out) throws IOException {
        String response = "HTTP/1.1 404 Not Found\r\n"
                + "Conntent-Type: text/plain\r\n"
                + "Content-Length: 9\r\n\r\n"
                + "Not Found";
        out.write(response.getBytes());
    }
}
