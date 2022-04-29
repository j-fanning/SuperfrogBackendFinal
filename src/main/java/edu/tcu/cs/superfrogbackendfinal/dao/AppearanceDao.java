package edu.tcu.cs.superfrogbackendfinal.dao;

import edu.tcu.cs.superfrogbackendfinal.domain.Appearance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppearanceDao extends JpaRepository<Appearance, Integer> {
}
