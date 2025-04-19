import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;

public class SimpleServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/save", new SaveHandler());
        server.setExecutor(null); // ברירת מחדל
        server.start();
        System.out.println("🚀 Server started on http://localhost:8080");
    }

    static class SaveHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            String[] params = query.split("&");

            String username = params[0].split("=")[1];
            String password = params[1].split("=")[1];

            saveUserToDB(username, password);

            String response = "✅ User saved";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }

        private void saveUserToDB(String username, String password) {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/eliau/Desktop/Interview/users.db");
            ) {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO users (username, password) VALUES (?, ?)");
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.executeUpdate();
                System.out.println("User saved: " + username);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
