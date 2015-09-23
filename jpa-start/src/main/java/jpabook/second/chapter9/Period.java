package jpabook.second.chapter9;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class Period {

	@Temporal(TemporalType.DATE)
	Date startDate;

	@Temporal(TemporalType.DATE)
	Date endDate;

	public boolean isWork(Date date) {
		// �� Ÿ���� ���� �޼ҵ� ����
		return startDate.after(date) && endDate.before(date);
	}
}
