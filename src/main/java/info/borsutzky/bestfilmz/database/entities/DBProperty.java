package info.borsutzky.bestfilmz.database.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PROPERTIES database table.
 * 
 */
@Entity
@Table(name="PROPERTIES")
public class DBProperty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROPERTIES_ID_GENERATOR", sequenceName="FILMZ_MOVIEID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROPERTIES_ID_GENERATOR")
	@Column(unique=true, nullable=false, precision=10)
	private Integer id;

	@Column(length=400)
	private String description;

	@Column(name="PROP_NAME", nullable=false, length=100)
	private String propName;

	@Column(name="PROP_VALUE", nullable=false, length=100)
	private String propValue;

	public DBProperty() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPropName() {
		return this.propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getPropValue() {
		return this.propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}

}