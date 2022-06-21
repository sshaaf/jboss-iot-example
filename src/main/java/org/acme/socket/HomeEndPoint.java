package org.acme.socket;

import org.acme.home.Home;
import org.acme.home.HomeFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value = "/devicesocket", encoders = { HomeEncoder.class }, decoders = { MessageDecoder.class })
public class HomeEndPoint {

    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());

    private Logger logger = Logger.getLogger(getClass().getName());

    private Runnable intervalNotifier;

    // It starts a Thread that notifies all sessions each second
    @PostConstruct
    public void startIntervalNotifier() {
        logger.info("Starting Home Device interval notifier");
        intervalNotifier = new Runnable() {

            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        notifyAllSessions(HomeFactory.getLatestData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(intervalNotifier).start();
    }


    // store the session once that it's opened
    @OnOpen
    public void onOpen(Session session) {
        logger.info("New websocket session opened: " + session.getId());
        clients.add(session);

    }

    // remove the session after it's closed
    @OnClose
    public void onClose(Session session) {
        logger.info("Websoket session closed: " + session.getId());
        clients.remove(session);
    }


    // Exception handling
    @OnError
    public void error(Session session, Throwable t) {
        t.printStackTrace();
    }

    // This method sends the same Home object to all opened sessions
    private void notifyAllSessions(Set<Home> homes) throws EncodeException, IOException {
        for (Session s : clients) {
            for(Home h: homes) {
                s.getBasicRemote().sendObject(h);
            }
        }

    }

}
