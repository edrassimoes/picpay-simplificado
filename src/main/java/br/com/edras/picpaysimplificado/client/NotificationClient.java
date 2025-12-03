package br.com.edras.picpaysimplificado.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification", url = "https://util.devi.tools")
public interface NotificationClient {

    @PostMapping("/api/v1/notify")
    void notify(@RequestBody NotificationRequest request);
}
