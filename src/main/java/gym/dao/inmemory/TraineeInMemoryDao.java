package gym.dao.inmemory;

import gym.service.simple.ValidationUtil;
import gym.dao.TraineeDao;
import gym.entities.Trainee;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@Profile("memory")
public class TraineeInMemoryDao extends InMemoryDao<Trainee> implements TraineeDao {
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

    @Override
    public Trainee findByUsername(String username) {
        ValidationUtil.validateNotNull(Map.of("username",username));
        return getEntityMap().values().stream()
                .filter(trainee -> trainee.getUser() != null && trainee.getUser().getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}