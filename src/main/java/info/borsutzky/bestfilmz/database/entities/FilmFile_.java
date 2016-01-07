package info.borsutzky.bestfilmz.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-14T14:42:44.607+0200")
@StaticMetamodel(FilmFile.class)
public class FilmFile_ {
	public static volatile SingularAttribute<FilmFile, Integer> fileId;
	public static volatile SingularAttribute<FilmFile, String> fileName;
	public static volatile SingularAttribute<FilmFile, String> mediaInfo;
	public static volatile SingularAttribute<FilmFile, Filmz> filmz;
	public static volatile SingularAttribute<FilmFile, Storage> storage;
}
