package gym.service;

import gym.dao.DataAccessObject;
import gym.entities.Trainee;
import gym.entities.TrainingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeService implements GymService<TrainingType>{

    @Autowired
    private DataAccessObject<TrainingType> trainingTypeDAO;

    @Override
    public TrainingType select(Long id) {
        return trainingTypeDAO.findById(id);
    }

    @Override
    public Long create(TrainingType entity) {
        //TODO
        return null;
    }

    @Override
    public void update(TrainingType entity) {
        //TODO
    }

    @Override
    public void delete(Long id) {
        //TODO
    }

    @Override
    public List<TrainingType> selectAll() {
        //TODO
        return null;
    }
}
