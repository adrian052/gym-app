package gym.dao.inmemory;

import gym.entities.Trainee;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@Profile("memory")
public class TraineeInMemoryDao extends InMemoryDao<Trainee> {
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