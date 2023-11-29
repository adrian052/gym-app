package gym.dao.inmemory;

import gym.dao.DataAccessObject;
import gym.entities.Trainer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@Profile("memory")
public class TrainerInMemoryDao extends InMemoryDao<Trainer> {
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
