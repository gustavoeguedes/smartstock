package guedes.gustavo.smartstock.controller;

import guedes.gustavo.smartstock.controller.dto.StartDto;
import guedes.gustavo.smartstock.service.SmartStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class StartController {

    private final SmartStockService smartStockService;

    public StartController(SmartStockService smartStockService) {
        this.smartStockService = smartStockService;
    }

    @PostMapping(path = "/start")
    public ResponseEntity<Void> start(@RequestBody StartDto startDto) {

        CompletableFuture.runAsync(() -> {
            smartStockService.start(startDto.reportPath());
        });

        return ResponseEntity.accepted().build();
    }
}
