package com.daniel.portscanner.mongo.repository;

import com.daniel.portscanner.mongo.document.ScanResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScanResultRepository extends MongoRepository<ScanResult, String> {
    List<ScanResult> findByHostIdOrderByStartedAtDesc(String hostId); // devuelve el histórico de un host ordenado por
                                                                      // fecha

    List<ScanResult> findByHostIdOrderByStartedAtDesc(String hostId, Pageable pageable); // con pegeable se puede poner
                                                                                         // para que solo te de 2
                                                                                         // resultados por ejemplo

}
