package webTest.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "zeitraum")
public class Zeitraum extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private int dauerInMin;
	private Date startZeitPunkt;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Poll poll;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	public Einladung getEinladung() {
		return einladung;
	}

	public void setEinladung(Einladung einladung) {
		this.einladung = einladung;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	private Einladung einladung;

	public int getDauerInMin() {
		return dauerInMin;
	}

	public void setDauerInMin(int dauerInMin) {
		this.dauerInMin = dauerInMin;
	}

	public Date getStartZeitPunkt() {
		return startZeitPunkt;
	}

	public void setStartZeitPunkt(Date startZeitPunkt) {
		this.startZeitPunkt = startZeitPunkt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
