package listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.Date;
import java.util.logging.Logger;

@WebListener
public class UserActivityListener implements HttpSessionAttributeListener {
    private static final Logger logger = Logger.getLogger(UserActivityListener.class.getName());

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if ("username".equals(event.getName())) {
            String username = (String) event.getValue();
            HttpSession session = event.getSession();
            String sessionId = session.getId();

            String logMessage = String.format(
                    "ПОЛЬЗОВАТЕЛЬ ВОШЕЛ | Время: %s | Имя: %s | ID сессии: %s",
                    new Date(), username, sessionId
            );
            logger.info(logMessage);
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if ("username".equals(event.getName())) {
            HttpSession session = event.getSession();
            String sessionId = session.getId();

            String logMessage = String.format(
                    "ПОЛЬЗОВАТЕЛЬ ВЫШЕЛ | Время: %s | ID сессии: %s",
                    new Date(), sessionId
            );
            logger.info(logMessage);
        }
    }
}
