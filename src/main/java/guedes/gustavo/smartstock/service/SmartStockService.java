package guedes.gustavo.smartstock.service;

import guedes.gustavo.smartstock.domain.CsvStockItem;
import guedes.gustavo.smartstock.entity.PurchaseRequestEntity;
import guedes.gustavo.smartstock.repository.PurchaseRequestRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class SmartStockService {

    private final ReportService reportService;
    private final PurchaseSectorService purchaseSectorService;
    private final PurchaseRequestRepository  purchaseRequestRepository;

    public SmartStockService(ReportService reportService,
                             PurchaseSectorService purchaseSectorService,
                             PurchaseRequestRepository purchaseRequestRepository) {
        this.reportService = reportService;
        this.purchaseSectorService = purchaseSectorService;
        this.purchaseRequestRepository = purchaseRequestRepository;
    }

    public void start(String reportPath) {
        try {
            var items = reportService.readStockReport(reportPath);

            items.forEach(item -> {
                if (item.getQuantity() <= item.getReorderTheshold()) {
                    var reorderQuantity = calculateReorderQuantity(item);

                    var purchasedWithSuccess = purchaseSectorService.sendPurchaseRequest(item, reorderQuantity);

                    persist(item, reorderQuantity, purchasedWithSuccess);

                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void persist(CsvStockItem item,
                         Integer reorderQuantity,
                         boolean purchasedWithSuccess) {
        var entity = new PurchaseRequestEntity();
        entity.setItemId(item.getItemId());
        entity.setItemName(item.getItemName());
        entity.setQuantityOnStock(item.getQuantity());
        entity.setReorderThreshold(item.getReorderTheshold());
        entity.setSupplierName(item.getSupplierName());
        entity.setSupplierEmail(item.getSupplierEmail());
        entity.setLastStockUpdateTime(LocalDateTime.parse(item.getLastStockUpdateTime()));

        entity.setPurchaseQuantity(reorderQuantity);
        entity.setPurchasedWithSuccess(purchasedWithSuccess);
        entity.setPurchaseDateTime(LocalDateTime.now());
        purchaseRequestRepository.save(entity);
    }

    private Integer calculateReorderQuantity(CsvStockItem item) {
        return item.getReorderTheshold() + ((int) Math.ceil(item.getQuantity() * 0.2));
    }
}


