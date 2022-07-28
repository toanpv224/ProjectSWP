/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.post;

import dal.AccountDAO;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import model.Account;

/**
 *
 * @author Duy Phuong
 */
@ServerEndpoint(value = "/chatRoomServer/{roomId}")
public class BlogCommentServerServlet {

    static Set<Session> users = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void handleOpen(Session session) {
        users.add(session);
    }

    @OnMessage
    public void handleMessage(String message, Session userSession) throws IOException, NumberFormatException {
        String username = (String) userSession.getUserProperties().get("username");
        if (username == null) {
            userSession.getUserProperties().put("username", message);
        } else {
            StringTokenizer st = new StringTokenizer(message, "|");
            AccountDAO accountDAO = new AccountDAO();
            Account sender = accountDAO.getAccountByID(Integer.parseInt(st.nextToken().trim()));
            String content = st.nextToken().trim();
            for (Session session : users) {
                session.getBasicRemote().sendText(sender.getImage_url() + "|" + sender.getFull_name() + "|" + content);
            }
        }
    }

    @OnClose
    public void handleClose(Session session) {
        users.remove(session);
    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }
}
