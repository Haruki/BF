package info.borsutzky.bestfilmz.database.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-07-06T13:49:27.414+0200")
@StaticMetamodel(Filmz.class)
public class Filmz_ {
	public static volatile SingularAttribute<Filmz, Integer> movieid;
	public static volatile SingularAttribute<Filmz, Date> createTime;
	public static volatile SingularAttribute<Filmz, String> imdbCode;
	public static volatile SingularAttribute<Filmz, BigDecimal> imdbRating;
	public static volatile SingularAttribute<Filmz, String> nameDeutsch;
	public static volatile SingularAttribute<Filmz, String> nameOriginal;
	public static volatile SingularAttribute<Filmz, Date> releaseDate;
	public static volatile SingularAttribute<Filmz, String> seen;
	public static volatile SingularAttribute<Filmz, Date> seenTime;
}
