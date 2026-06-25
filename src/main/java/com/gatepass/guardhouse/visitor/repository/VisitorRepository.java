package com.gatepass.guardhouse.visitor.repository;

import com.gatepass.guardhouse.visitor.model.Visitor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorRepository extends MongoRepository<Visitor, String> {

    List<Visitor> findAllByHostResidentId(String hostResidentId);

    List<Visitor> findAllByRegisteredBy(String registeredBy);

    Optional<Visitor> findByQrToken(String qrToken);
}
