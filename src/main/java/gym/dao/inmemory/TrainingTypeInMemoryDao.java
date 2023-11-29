package gym.dao.inmemory;

import gym.dao.DataAccessObject;
import gym.entities.TrainingType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@Profile("memory")
public class TrainingTypeInMemoryDao extends InMemoryDao<TrainingType> {
    @Override
    protected Map<Long, TrainingType> getEntityMap() {
        return storage.getTrainingTypes();
    }

    @Override
    protected boolean hasReferentialIntegrity(TrainingType trainingType) {
        return true;
    }

    @Override
    protected boolean validateNotNull(TrainingType trainingType) {
        return trainingType.getTrainingTypeName()!=null;
    }

}