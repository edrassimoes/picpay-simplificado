package br.com.edras.picpaysimplificado.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "authorizer", url = "https://util.devi.tools")
public interface AuthorizerClient {

    @GetMapping("/api/v2/authorize")
    AuthorizerResponse authorize();
}
