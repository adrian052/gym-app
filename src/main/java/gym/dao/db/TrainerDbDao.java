package gym.dao.db;

import gym.entities.Trainer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("db")
public class TrainerDbDao extends DataBaseDao<Trainer> {
    @Override
    protected Class<Trainer> getIdentifieableClass() {
        return Trainer.class;
    }
}
