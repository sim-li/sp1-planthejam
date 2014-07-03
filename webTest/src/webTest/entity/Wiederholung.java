package webTest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import webTest.enumClass.ZeitEinheit;

@Entity
@Table(name = "wiederholung")
public class Wiederholung extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private int abstand;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ZeitEinheit zeiteinheit;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Poll poll;
	
	public int getAbstand() {
		return abstand;
	}

	public void setAbstand(int abstand) {
		this.abstand = abstand;
	}

	public ZeitEinheit getZeiteinheit() {
		return zeiteinheit;
	}

	public void setZeiteinheit(ZeitEinheit zeiteinheit) {
		this.zeiteinheit = zeiteinheit;
	}

	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}
}
