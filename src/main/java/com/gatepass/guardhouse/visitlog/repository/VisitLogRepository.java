package com.gatepass.guardhouse.visitlog.repository;

import com.gatepass.guardhouse.visitlog.model.VisitLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitLogRepository extends MongoRepository<VisitLog, String> {

    List<VisitLog> findByVisitorId(String visitorId);
}