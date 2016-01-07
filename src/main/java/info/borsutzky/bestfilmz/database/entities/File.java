package info.borsutzky.bestfilmz.database.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FILES database table.
 * 
 */
@Entity
@Table(name="FILES")
public class File implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FILES_FILEID_GENERATOR", sequenceName="FILMZ_MOVIEID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FILES_FILEID_GENERATOR")
	@Column(name="FILE_ID", unique=true, nullable=false, precision=10)
	private Integer fileId;

	@Column(name="FILE_NAME", nullable=false, length=20)
	private String fileName;

	@Lob
	@Column(name="MEDIA_INFO")
	private String mediaInfo;

	//bi-directional many-to-one association to Filmz
	@ManyToOne
	@JoinColumn(name="MOVIE_ID")
	private Filmz filmz;

	//bi-directional many-to-one association to Storage
	@ManyToOne
	@JoinColumn(name="STORAGE_ID", nullable=false)
	private Storage storage;

	public File() {
	}

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMediaInfo() {
		return this.mediaInfo;
	}

	public void setMediaInfo(String mediaInfo) {
		this.mediaInfo = mediaInfo;
	}

	public Filmz getFilmz() {
		return this.filmz;
	}

	public void setFilmz(Filmz filmz) {
		this.filmz = filmz;
	}

	public Storage getStorage() {
		return this.storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

}