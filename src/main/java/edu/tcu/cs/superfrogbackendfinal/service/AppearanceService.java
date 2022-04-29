package edu.tcu.cs.superfrogbackendfinal.service;

import edu.tcu.cs.superfrogbackendfinal.dao.AppearanceDao;
import edu.tcu.cs.superfrogbackendfinal.domain.Appearance;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AppearanceService {
    private AppearanceDao appearanceDao;

    public AppearanceService(AppearanceDao appearanceDao) {
        this.appearanceDao = appearanceDao;
    }

    public List<Appearance> findAll(){
        return appearanceDao.findAll();
    }

    public Appearance findById(Integer id) {
        return appearanceDao.findById(id).get();
    }

    public void save(Appearance appearance){
        appearanceDao.save(appearance);
    }

    public void update(Integer id, Appearance appearance) {
        appearance.setId(id);
        appearanceDao.save(appearance);
    }

    public void deleteById(Integer id) {
        appearanceDao.deleteById(id);
    }

}
