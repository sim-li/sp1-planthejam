package de.bht.comanche.logic;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.bht.comanche.persistence.DaObject;

@Entity
@Table(name = "message")
public class LgMessage extends DaObject{
	@ManyToOne
	private LgUser user;
	
	public String messageText;
	
	public String getMessage() {
		return this.messageText;
	}
	
	public void setMessage(String message) {
		this.messageText = message;
	}
	
	public LgMessage updateWith(LgMessage other){
		this.messageText = other.messageText;
		return this;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((messageText == null) ? 0 : messageText.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LgMessage other = (LgMessage) obj;
		if (messageText == null) {
			if (other.messageText != null)
				return false;
		} else if (!messageText.equals(other.messageText))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
} 
