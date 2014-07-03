package webTest.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import webTest.enumClass.UmfrageType;

@Entity
@Table(name = "poll")
public class Poll extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String beschreibung;
	private Date deadline;
	
	@Column(nullable = false, columnDefinition="enum default null")
	@Enumerated(EnumType.STRING)
	private UmfrageType typ;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Wiederholung frequenz;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "poll")
	private List<Zeitraum> moeglicheZeitraeume;
//	private Zeitraum ermittelterZeitraum;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy="user_polls")
	private List<User> users;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy="group_polls")
	private List<Group> groups;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
}
