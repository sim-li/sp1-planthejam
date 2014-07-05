package de.bht.comanche.persistence;

import java.util.Collection;

import de.bht.comanche.logic.LgUser;
import javassist.NotFoundException;

public interface DaUser {
    Collection<LgUser> findByName(String name);
}
