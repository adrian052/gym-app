package gym.dao;

import gym.entities.Trainee;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class TraineeDAO extends DataAccessObject<Trainee> {
    @Override
    protected Map<Long, Trainee> getEntityMap() {
        return storage.getTrainees();
    }

    @Override
    protected boolean hasReferentialIntegrity(Trainee trainee) {
        return isEntityIdInMap(trainee.getUser(), storage.getUsers());
    }

    @Override
    protected boolean validateNotNull(Trainee trainee) {
        return trainee.getUser()!=null;
    }
}