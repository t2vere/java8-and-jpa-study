package jpabook.second.chapter7.singletable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="Movie2")
@DiscriminatorValue(value="M") // 엔티티 저장시 구분 컬럼에 입력할 값 지정
public class Movie extends Item {

	private String director;
	private String actor;
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
}
