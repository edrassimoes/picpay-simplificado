package br.com.edras.picpaysimplificado.repository;

import br.com.edras.picpaysimplificado.domain.MerchantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantUserRepository extends JpaRepository<MerchantUser, Long> {

    boolean existsByCnpj(String cnpj);

}
