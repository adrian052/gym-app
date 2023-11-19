package gym.dao;

import gym.entities.Trainer;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class TrainerDAO extends DataAccessObject<Trainer> {
    @Override
    protected Map<Long, Trainer> getEntityMap() {
        return storage.getTrainers();
    }

    @Override
    protected boolean hasReferentialIntegrity(Trainer trainer) {
        return isEntityIdInMap(trainer.getUser(), storage.getUsers());
    }

    @Override
    protected boolean validateNotNull(Trainer trainer) {
        return trainer.getSpecialization()!=null && trainer.getUser()!=null;
    }
}
