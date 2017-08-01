package fr.rtone.iotsigfox.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.rtone.iotsigfox.dto.SigfoxDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

/**
 * @Author: Hani
 */
@Component
public class SigfoxDataHandler extends TextWebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(SigfoxDataHandler.class);

    WebSocketSession session;

    @Autowired
    ObjectMapper objectMapper;


    public void pushSigfoxData(List<SigfoxDataDTO> dataDTO) {
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(dataDTO)));
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.error("Don't have open session to send:" + dataDTO.toString());
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
    }

}
