package gym.dao;

import gym.entities.TrainingType;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class TrainingTypeDAO extends DataAccessObject<TrainingType> {
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