package jpabook.second.chapter7.singletable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="Album2")
@DiscriminatorValue(value="A") // 엔티티 저장시 구분 컬럼에 입력할 값 지정
public class Album extends Item {

	private String artist;

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}
}
