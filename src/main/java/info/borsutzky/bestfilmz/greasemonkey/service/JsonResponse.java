package info.borsutzky.bestfilmz.greasemonkey.service;

import org.json.JSONObject;

/**
 * Alles was über die Greasemonkey-Schnittstelle an den Browser zurückgeschickt
 * wird, muss dieses Interface implementieren. (Movie Objekte und Sonstige
 * Nachrichten).
 * 
 * @author songoku
 * @since 06.07.2013
 * 
 */
public interface JsonResponse {

	public JSONObject asJson();

}
