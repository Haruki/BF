package info.borsutzky.bestfilmz.database.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the files database table.
 * 
 */
@Entity
@Table(name = "files")
public class FilmFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "file_id")
	private int fileId;

	@Column(name = "file_name")
	private String fileName;

	@Lob
	@Column(name = "media_info")
	private String mediaInfo;

	// bi-directional many-to-one association to Filmz
	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Filmz filmz;

	// bi-directional many-to-one association to Storage
	@ManyToOne
	@JoinColumn(name = "storage_id")
	private Storage storage;

	public FilmFile() {
	}

	public int getFileId() {
		return this.fileId;
	}

	public void setFileId(int fileId) {
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