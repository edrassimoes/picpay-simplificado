package br.com.edras.picpaysimplificado.repository;

import br.com.edras.picpaysimplificado.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // boolean existsByEmail(String email);
    // boolean existsByCpf(String cpf);
    // boolean existsByCnpj(String cnpj);

}
