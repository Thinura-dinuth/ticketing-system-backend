package com.example.Ticketing;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class LogWebSocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = Logger.getLogger(LogWebSocketHandler.class.getName());
    private WebSocketSession session;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private PipedOutputStream pipedOutputStream;
    private PipedInputStream pipedInputStream;
    private PrintStream originalOut;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;

        // Redirect System.out to a PipedOutputStream
        originalOut = System.out;
        pipedOutputStream = new PipedOutputStream();
        pipedInputStream = new PipedInputStream(pipedOutputStream);
        System.setOut(new PrintStream(pipedOutputStream));

        startLogCapture();
        LOGGER.info("WebSocket connection established for log streaming");
    }

    private void startLogCapture() {
        scheduler.execute(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(pipedInputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (session != null && session.isOpen()) {
                        session.sendMessage(new TextMessage(line));
                    }
                }
            } catch (IOException e) {
                LOGGER.severe("Error capturing logs: " + e.getMessage());
                closeSession();
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        closeSession();
        LOGGER.info("WebSocket connection closed for log streaming");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        LOGGER.severe("Transport error: " + exception.getMessage());
        closeSession();
    }

    private void closeSession() {
        try {
            // Restore original System.out
            System.setOut(originalOut);

            if (pipedOutputStream != null) {
                pipedOutputStream.close();
            }
            if (pipedInputStream != null) {
                pipedInputStream.close();
            }

            if (session != null && session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            LOGGER.severe("Error closing session: " + e.getMessage());
        } finally {
            scheduler.shutdown();
        }
    }
}