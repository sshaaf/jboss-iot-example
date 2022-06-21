package org.acme.socket;

import com.google.gson.Gson;
import org.acme.home.Home;
import org.acme.sender.HomeFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@MessageDriven(name = "HomeEndPointMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "topic/DeviceTopic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
@ServerEndpoint(value = "/devicesocket", encoders = { HomeEncoder.class }, decoders = { MessageDecoder.class })
public class HomeEndPoint implements MessageListener {

    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());

    private Logger logger = Logger.getLogger(getClass().getName());

    private Runnable intervalNotifier;

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
    private void notifyAllSessions(String h) throws EncodeException, IOException {
        for (Session s : clients) {
                s.getBasicRemote().sendObject(h);
        }

    }

    @Override
    public void onMessage(Message rcvMessage) {
        TextMessage msg = null;
        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                logger.info("Received Message from topic at HomeEndPoint: " + msg.getText());
                notifyAllSessions(msg.getText());
                logger.info("Sent Message from topic at HomeEndPoint to socket: " + msg.getText());
            } else {
                logger.warning("Message of wrong type: " + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        } catch (EncodeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
