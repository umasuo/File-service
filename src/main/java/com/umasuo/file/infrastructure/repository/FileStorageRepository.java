package com.umasuo.file.infrastructure.repository;

import com.umasuo.file.domain.model.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by umasuo on 17/2/10.
 */
@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, String>{

}
