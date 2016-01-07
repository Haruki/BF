package info.borsutzky.bestfilmz.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-06-02T01:21:44.786+0200")
@StaticMetamodel(File.class)
public class File_ {
	public static volatile SingularAttribute<File, Integer> fileId;
	public static volatile SingularAttribute<File, String> fileName;
	public static volatile SingularAttribute<File, String> mediaInfo;
	public static volatile SingularAttribute<File, Filmz> filmz;
	public static volatile SingularAttribute<File, Storage> storage;
}
