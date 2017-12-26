package com.fintech.modules.platform.sysmessage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.justobjects.pushlet.core.Command;
import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.Protocol;
import nl.justobjects.pushlet.core.Session;
import nl.justobjects.pushlet.core.SessionManager;
import nl.justobjects.pushlet.servlet.Pushlet;
import nl.justobjects.pushlet.util.Log;

public class MsgPushlet extends Pushlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 678321611390811613L;
	
	/**
	 * Generic request handler (GET+POST).
	 */
	protected void doRequest(Event anEvent, HttpServletRequest request, HttpServletResponse response) {
		// Must have valid event type.
		String eventType = anEvent.getEventType();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			Session session = null;
			if (eventType.startsWith(Protocol.E_JOIN)) {
				session = SessionManager.getInstance().createSession(anEvent);
				String userAgent = request.getHeader("User-Agent");
				if (userAgent != null) {
					userAgent = userAgent.toLowerCase();
				} else {
					userAgent = "unknown";
				}
				session.setUserAgent(userAgent);
			} else {
				String id = anEvent.getField(P_ID);
				if (id == null) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No id specified");
					Log.warn("Pushlet: bad request, no id specified event=" + eventType);
					return;
				}
				session = SessionManager.getInstance().getSession(id);
				if (session == null) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid or expired id: " + id);
					Log.warn("Pushlet:  bad request, no session found id=" + id + " event=" + eventType);
					return;
				}
			}
			Command command = Command.create(session, anEvent, request, response);
			session.getController().doCommand(command);
		} catch (Throwable t) {
			Log.warn("Pushlet:  Exception in doRequest() event=" + eventType, t);
			t.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}
}
