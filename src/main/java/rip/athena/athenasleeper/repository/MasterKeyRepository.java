package rip.athena.athenasleeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rip.athena.athenasleeper.entity.MasterKeyEntity;
import rip.athena.athenasleeper.entity.UserEntity;

import java.util.UUID;

@Repository
public interface MasterKeyRepository extends JpaRepository<MasterKeyEntity, String> {
}
