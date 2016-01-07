package info.borsutzky.bestfilmz.database.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-06-02T01:21:44.797+0200")
@StaticMetamodel(Storage.class)
public class Storage_ {
	public static volatile SingularAttribute<Storage, Integer> storageId;
	public static volatile SingularAttribute<Storage, String> description;
	public static volatile SingularAttribute<Storage, String> storageName;
	public static volatile ListAttribute<Storage, File> files;
}
