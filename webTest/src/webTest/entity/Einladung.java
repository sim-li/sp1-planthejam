package webTest.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "einladung")
public class Einladung extends BaseEntity{

	private static final long serialVersionUID = 1L;
	private boolean ignoriert = false;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="einladung")
	private List<Zeitraum> zeitraums;
	
	public boolean isIgnoriert() {
		return ignoriert;
	}
	public void setIgnoriert(boolean ignoriert) {
		this.ignoriert = ignoriert;
	}
	public List<Zeitraum> getZeitraums() {
		return zeitraums;
	}
	public void setZeitraums(List<Zeitraum> zeitraums) {
		this.zeitraums = zeitraums;
	}
	
	
}
