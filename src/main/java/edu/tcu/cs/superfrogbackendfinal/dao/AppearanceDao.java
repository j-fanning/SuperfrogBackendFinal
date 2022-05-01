package edu.tcu.cs.superfrogbackendfinal.dao;

import edu.tcu.cs.superfrogbackendfinal.domain.Appearance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppearanceDao extends JpaRepository<Appearance, Integer> {
    List<Appearance> findByPending(boolean pending);
}
