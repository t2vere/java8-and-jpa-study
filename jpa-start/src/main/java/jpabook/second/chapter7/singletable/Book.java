package jpabook.second.chapter7.singletable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity(name="Book2")
@DiscriminatorValue(value="B")
@PrimaryKeyJoinColumn(name="BOOK_ID") // ID 재정의 (기본값은 부모 테이블의 ID 컬럼을 그대로 사용)
public class Book extends Item {

	private String author;
	private String isbn;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
}
