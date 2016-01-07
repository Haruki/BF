package info.borsutzky.bestfilmz.database.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the FILMZ database table.
 *
 */
@Entity
@Table(name = "FILMZ")
public class Filmz implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "FILMZ_MOVIEID_GENERATOR", sequenceName = "FILMZ_MOVIEID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILMZ_MOVIEID_GENERATOR")
	@Column(unique = true, nullable = false, precision = 10)
	private Integer movieid;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", nullable = false)
	private Date createTime;

	@Column(name = "IMDB_CODE", nullable = false, length = 150)
	private String imdbCode;

	@Column(name = "IMDB_RATING", nullable = false, precision = 126)
	private BigDecimal imdbRating;

	// @Lob
	@Column(name = "NAME_DEUTSCH", nullable = false)
	private String nameDeutsch;

	// @Lob
	@Column(name = "NAME_ORIGINAL", nullable = false)
	private String nameOriginal;

	@Temporal(TemporalType.DATE)
	@Column(name = "RELEASE_DATE", nullable = false)
	private Date releaseDate;

	@Column(length = 4000)
	private String seen;

	@Temporal(TemporalType.DATE)
	@Column(name = "SEEN_TIME", nullable = false)
	private Date seenTime;

	public Filmz() {
	}

	public Integer getMovieid() {
		return movieid;
	}

	public void setMovieid(final Integer movieid) {
		this.movieid = movieid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

	public String getImdbCode() {
		return imdbCode;
	}

	public void setImdbCode(final String imdbCode) {
		this.imdbCode = imdbCode;
	}

	public BigDecimal getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(final BigDecimal imdbRating) {
		this.imdbRating = imdbRating;
	}

	public String getNameDeutsch() {
		return nameDeutsch;
	}

	public void setNameDeutsch(final String nameDeutsch) {
		this.nameDeutsch = nameDeutsch;
	}

	public String getNameOriginal() {
		return nameOriginal;
	}

	public void setNameOriginal(final String nameOriginal) {
		this.nameOriginal = nameOriginal;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(final Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getSeen() {
		return seen;
	}

	public void setSeen(final String seen) {
		this.seen = seen;
	}

	public Date getSeenTime() {
		return seenTime;
	}

	public void setSeenTime(final Date seenTime) {
		this.seenTime = seenTime;
	}

}