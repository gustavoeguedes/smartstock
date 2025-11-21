package guedes.gustavo.smartstock.service;

import guedes.gustavo.smartstock.domain.CsvStockItem;
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

            items.forEach(item -> {
                if (item.getQuantity() < item.getReorderTheshold()) {
                    var reorderQuantity = calculateReorderQuantity(item);

                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer calculateReorderQuantity(CsvStockItem item) {
        return item.getReorderTheshold() + ((int) Math.ceil(item.getQuantity() * 0.2));
    }
}


