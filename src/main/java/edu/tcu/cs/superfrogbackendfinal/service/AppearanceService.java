package edu.tcu.cs.superfrogbackendfinal.service;

import edu.tcu.cs.superfrogbackendfinal.dao.AppearanceDao;
import edu.tcu.cs.superfrogbackendfinal.domain.Appearance;
import edu.tcu.cs.superfrogbackendfinal.domain.Result;
import edu.tcu.cs.superfrogbackendfinal.domain.StatusCode;
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

    public List<Appearance> findPending(){
        return appearanceDao.findByPending(true);
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

    public Result approve(Integer appearanceId) {
        Appearance appearance = appearanceDao.findById(appearanceId).get();
        Integer range = Integer.valueOf(appearance.getMilageRange());
        if (range <= 100) {
            appearance.setPending(false);
            appearanceDao.save(appearance);
            return new Result(true, StatusCode.SUCCESS, "Appearance Approved");
        }
        else{
            return new Result(false, StatusCode.FAILURE, "Must be under 100 Miles");
        }

    }
}
