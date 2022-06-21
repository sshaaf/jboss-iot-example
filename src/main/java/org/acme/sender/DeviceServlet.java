package org.acme.sender;

import org.acme.home.Home;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.Topic;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Definition of the two JMS destinations used by the quickstart
 * (one queue and one topic).
 */
@JMSDestinationDefinitions(
        value = {
                @JMSDestinationDefinition(
                        name = "java:/topic/DeviceTopic",
                        interfaceName = "javax.jms.Topic",
                        destinationName = "DeviceTopic"
                )
        }
)

@WebServlet("/DeviceServletClient")
public class DeviceServlet extends HttpServlet {

    @Inject
    private JMSContext context;

    private static final int MSG_COUNT = 5;

    @Resource(lookup = "java:/topic/DeviceTopic")
    private Topic destination;

    private Logger logger = Logger.getLogger(getClass().getName());

    private Runnable intervalNotifier;

    private HomeFactory homeFactory = new HomeFactory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.write("<h1>Quickstart: Example demonstrates the use of <strong>JMS 2.0</strong> and <strong>EJB 3.2 Message-Driven Bean</strong> in JBoss EAP.</h1>");
        try {
            boolean useTopic = req.getParameterMap().keySet().contains("topic");

            out.write("<p>Sending messages to <em>" + destination + "</em></p>");
            out.write("<h2>The following messages will be sent to the destination:</h2>");
            for (int i = 0; i < MSG_COUNT; i++) {
                String text = "This is message " + (i + 1);
                context.createProducer().send(destination, homeFactory.getNextRandomHome());
                out.write("Message (" + i + "): " + text + "</br>");
            }
            out.write("<p><i>Go to your JBoss EAP server console or server log to see the result of messages processing.</i></p>");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
