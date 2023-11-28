package gym.dao.inmemory;

import gym.service.simple.ValidationUtil;
import gym.dao.TrainerDao;
import gym.entities.Trainer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@Profile("memory")
public class TrainerInMemoryDao extends InMemoryDao<Trainer> implements TrainerDao {
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

    @Override
    public Trainer findByUsername(String username) {
        ValidationUtil.validateNotNull(Map.of("username",username));
        return getEntityMap().values().stream()
                .filter(trainer -> trainer.getUser() != null && trainer.getUser().getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
