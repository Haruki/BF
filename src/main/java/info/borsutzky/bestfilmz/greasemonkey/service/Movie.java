package info.borsutzky.bestfilmz.greasemonkey.service;

import info.borsutzky.bestfilmz.database.entities.Filmz;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/**
 * Diese Movie-Klasse dient dem Datentransfer zum Greasemonkey script. Dazu wird
 * dieses Movie-Object in ein Json-Object gewandelt und umgekehrt. Damit
 * Film-Daten im Browser ankommen muss die Filmz Entity erst in ein Movie Objekt
 * für den Transfer umgewandelt werden und umgekehrt.
 * 
 * @author songoku
 * @since 09.02.2013
 * 
 */
public class Movie implements JsonResponse {

	private static Logger logger = LogManager.getLogger(Movie.class.getName());

	// using Object-Types for Long,Integer.. because they are needed to have
	// "null" as allowed value.
	private String imdbCode;
	private Integer movieid;
	private String nameDeutsch;
	private String nameOriginal;
	private BigDecimal imdbRating;
	private Long releaseDate;
	private boolean seen;
	private Long creationTime; // the time the movie was added to the database
	private Long seenTime; // the time i actually consumed the movie

	public Movie() {
	}

	/**
	 * Transformiert ein FilmzEntity in ein Movie-Objekt.
	 * 
	 * @param filmzEntity
	 * @return
	 */
	public static Movie createFromEntity(final Filmz filmzEntity) {
		Movie.logger.debug("filmzentity null?: " + filmzEntity == null);
		final Movie movie = new Movie();
		movie.setCreationTime(filmzEntity.getCreateTime().getTime());
		movie.setImdbRating(filmzEntity.getImdbRating());
		movie.setImdbCode(filmzEntity.getImdbCode());
		movie.setMovieid(filmzEntity.getMovieid());
		movie.setNameDeutsch(filmzEntity.getNameDeutsch());
		movie.setNameOriginal(filmzEntity.getNameOriginal());
		movie.setReleaseDate(filmzEntity.getReleaseDate().getTime());
		movie.setSeen(Boolean.parseBoolean(filmzEntity.getSeen()));
		movie.setSeenTime(filmzEntity.getSeenTime().getTime());
		return movie;
	}

	public String getImdbCode() {
		return this.imdbCode;
	}

	public void setImdbCode(final String imdbCode) {
		this.imdbCode = imdbCode;
	}

	public int getMovieid() {
		return this.movieid;
	}

	public void setMovieid(final int movieid) {
		this.movieid = movieid;
	}

	public String getNameDeutsch() {
		return this.nameDeutsch;
	}

	public void setNameDeutsch(final String nameDeutsch) {
		this.nameDeutsch = nameDeutsch;
	}

	public String getNameOriginal() {
		return this.nameOriginal;
	}

	public void setNameOriginal(final String nameOriginal) {
		this.nameOriginal = nameOriginal;
	}

	public BigDecimal getImdbRating() {
		return this.imdbRating;
	}

	public void setImdbRating(final BigDecimal imdbRating) {
		this.imdbRating = imdbRating;
	}

	public long getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(final long releaseDate) {
		this.releaseDate = releaseDate;
	}

	public boolean isSeen() {
		return this.seen;
	}

	public void setSeen(final boolean seen) {
		this.seen = seen;
	}

	public long getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(final long creationTime) {
		this.creationTime = creationTime;
	}

	public long getSeenTime() {
		return this.seenTime;
	}

	public void setSeenTime(final long seenTime) {
		this.seenTime = seenTime;
	}

	/**
	 * Transformiert dieses Movie-Objekt in ein Json-String zwecks übertragung
	 * über HTTP.
	 * 
	 * @return {@link String} jsonString Repräsentation des Movie-Objekts.
	 */
	@Override
	public JSONObject asJson() {
		final JSONObject json = new JSONObject();
		json.put("imdbcode", this.imdbCode);
		json.put("movieid", this.movieid);
		json.put("namedeutsch", this.nameDeutsch);
		json.put("nameoriginal", this.nameOriginal);
		json.put("imdbrating", this.imdbRating);
		json.put("releasedate", this.releaseDate);
		json.put("seen", this.seen);
		json.put("creationtime", this.creationTime);
		json.put("seentime", this.seenTime);
		return json;
	}

	/**
	 * Transformiert dieses Movie-Objekt in eine Filmz-Entity.
	 * 
	 * @return
	 */
	public Filmz toFilmz() {
		final Filmz filmz = new Filmz();
		// CreateTime and SeenTime are set by Database Trigger.
		// filmz.setCreateTime(new Timestamp(this.getCreationTime()));
		filmz.setImdbCode(this.getImdbCode());
		filmz.setImdbRating(this.getImdbRating());
		filmz.setNameDeutsch(this.getNameDeutsch());
		filmz.setNameOriginal(this.getNameOriginal());
		filmz.setReleaseDate(new Timestamp(this.getReleaseDate()));
		// filmz.setSeenTime(new Timestamp(this.getSeenTime()));
		return filmz;
	}

}
