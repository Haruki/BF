package info.borsutzky.bestfilmz.database.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the "STORAGE" database table.
 * 
 */
@Entity
@Table(name = "STORAGE")
public class Storage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "STORAGE_STORAGEID_GENERATOR", sequenceName = "FILMZ_MOVIEID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STORAGE_STORAGEID_GENERATOR")
	@Column(name = "STORAGE_ID", unique = true, nullable = false, precision = 10)
	private Integer storageId;

	@Column(length = 4000)
	private String description;

	@Column(name = "STORAGE_NAME", nullable = false, length = 100)
	private String storageName;

	// bi-directional many-to-one association to File
	@OneToMany(mappedBy = "storage")
	private List<File> files;

	public Storage() {
	}

	public Integer getStorageId() {
		return this.storageId;
	}

	public void setStorageId(final Integer storageId) {
		this.storageId = storageId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getStorageName() {
		return this.storageName;
	}

	public void setStorageName(final String storageName) {
		this.storageName = storageName;
	}

	public List<File> getFiles() {
		return this.files;
	}

	public void setFiles(final List<File> files) {
		this.files = files;
	}

}