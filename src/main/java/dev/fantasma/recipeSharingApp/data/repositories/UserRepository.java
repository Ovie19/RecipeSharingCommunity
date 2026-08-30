package dev.fantasma.recipeSharingApp.data.repositories;

import dev.fantasma.recipeSharingApp.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
