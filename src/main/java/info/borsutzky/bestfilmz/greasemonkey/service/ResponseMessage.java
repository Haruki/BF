package info.borsutzky.bestfilmz.greasemonkey.service;

import org.json.JSONObject;

/**
 * Diese Klasse dient dazu, einfache (Erfolgs-)Nachrichten an den Browser zu
 * senden.
 * 
 * @author songoku
 * @since 06.07.2013
 * 
 */
public class ResponseMessage implements JsonResponse {

	private final String message;

	public ResponseMessage(final String message) {
		this.message = message;
	}

	@Override
	public JSONObject asJson() {
		final JSONObject json = new JSONObject();
		json.put("message", this.message);
		return json;
	}

}
