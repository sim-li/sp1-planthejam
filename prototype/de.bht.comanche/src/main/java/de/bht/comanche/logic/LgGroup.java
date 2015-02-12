package de.bht.comanche.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import de.bht.comanche.persistence.DaObject;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This entity class represents a group and serve methods for working with
 * LgMember and LgGroup objects.
 *
 * @author Maxim Novichkov
 *
 */

@Entity
@Table(name = "group")
public class LgGroup extends DaObject{

    

	private static final long serialVersionUID = 1L;

    /**
     * Column for a group name. Must not be null.
     */
    @NotNull
    @Column
    private String name;

    /**
     * Column for a LgUser representation. Must not be null.
     */
    @NotNull
    @ManyToOne
    private LgUser user;

    /**
     * Representation of a foreign key in a LgMember entity. Provide a list of members.
     */
    @OneToMany(mappedBy="group", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER, orphanRemoval=true)
    private List<LgMember> members;

    /**
     * URL for gravatar random art.
     */
    private String iconurl;

    /**
     * Construct a new LgGroup with a list of members.
     */
    public LgGroup() {
        this.members = new ArrayList<LgMember>();
    }

    /**
     * Returns the LgMember object by oid.
     *
     * @param oid The LgMember oid.
     * @return Return serched LgMember.
     */
    public LgMember getMember(final long oid) {
        return search(this.members, oid);
    }

    /**
     * Sets specified LgGroup for LgMembers.
     * @param group The LgGroup to set.
     * @return Returns the LgGroup.
     */
    public LgGroup setForMember(LgGroup group){
            for (final LgMember member : group.getMembers()) {
                member.setGroup(group);
            }
            return this;
    }

    /**
     * Generates a random art URL for the gravatar service
     * using the group name.
     * @return URL to gravatar random art
     */
    public String getIconurl() {
        final LgGravatarUtils utils = new LgGravatarUtils();
        iconurl = utils.getGroupUrl(name);
        return iconurl;
    }

    /**
     * Sets the icon URL for this class. Setter for
     * JPA (creating new entitites). Possibly unnecessary.
     * @param iconurl URL to gravatar random art
     */
    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    /**
     * Returns list with LgUsers for specified group.
     * @return The list with LgUsers.
     */
    @JsonIgnore
    public List<LgUser> getUsers() {
        final List<LgUser> users = new LinkedList<LgUser>();
        for (final LgMember member : this.members) {
            users.add(member.getUser());
        }
        return users;
    }

    /**
     * Gets string name for this group.
     * @return The name of this group.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets a  name for this group.
     * @param name Name to set.
     * @return The name of this group.
     */
    public LgGroup setName(final String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets LgUser for this LgGroup.
     * @param user LgUser object to set.
     * @return LgGroup with specified LgUser object.
     */
    public LgGroup setUser(final LgUser user) {
        this.user = user;
        return this;
    }

    /**
     * Returns LgMember list for specified LgGroup.
     * @return The list with LgMembers.
     */
    public List<LgMember> getMembers() {
        return this.members;
    }
    
    public LgGroup setMembers(final List<LgMember> members) {
        this.members = members;
        return this;
    }

    @Override
    public String toString() {
        return String.format(
                "LgGroup [name=%s, user=%s, oid=%s]",
                name, user, oid);
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((iconurl == null) ? 0 : iconurl.hashCode());
		result = prime * result + ((members == null) ? 0 : members.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		LgGroup other = (LgGroup) obj;
		if (iconurl == null) {
			if (other.iconurl != null)
				return false;
		} else if (!iconurl.equals(other.iconurl))
			return false;
		if (members == null) {
			if (other.members != null)
				return false;
		} else if (!members.equals(other.members))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}
