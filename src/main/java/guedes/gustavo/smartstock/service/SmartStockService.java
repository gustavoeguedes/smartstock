package guedes.gustavo.smartstock.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SmartStockService {

    private final ReportService reportService;

    public SmartStockService(ReportService reportService) {
        this.reportService = reportService;
    }

    public void start(String reportPath) {
        try {
            var items = reportService.readStockReport(reportPath);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
