package guedes.gustavo.smartstock.repository;

import guedes.gustavo.smartstock.entity.PurchaseRequestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseRequestRepository extends MongoRepository<PurchaseRequestEntity, String> {
}
