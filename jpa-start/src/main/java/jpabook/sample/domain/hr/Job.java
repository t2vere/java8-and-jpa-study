package jpabook.sample.domain.hr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JOBS")
public class Job {

	@Id
	@Column(name = "JOB_ID", length = 10)
	private String jobId;

	@Column(name = "JOB_TITLE")
	private String title;

	@Column(name = "MIN_SALARY", precision = 6)
	private Long minimumSalary;

	@Column(name = "MAX_SALARY", precision = 6)
	private Long maximumSalary;

	public Job() {
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getMinimumSalary() {
		return minimumSalary;
	}

	public void setMinimumSalary(Long minimumSalary) {
		this.minimumSalary = minimumSalary;
	}

	public Long getMaximumSalary() {
		return maximumSalary;
	}

	public void setMaximumSalary(Long maximumSalary) {
		this.maximumSalary = maximumSalary;
	}
}
