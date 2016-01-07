package info.borsutzky.bestfilmz.database.entities;

import javax.persistence.PostPersist;

public class FilmzEntityListener {

	@PostPersist
	void onPostPersist(final Filmz movie) {
	}
}
