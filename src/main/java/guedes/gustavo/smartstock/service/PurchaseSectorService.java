package guedes.gustavo.smartstock.service;

import guedes.gustavo.smartstock.client.PurchaseSectorClient;
import guedes.gustavo.smartstock.client.dto.PurchaseRequest;
import guedes.gustavo.smartstock.domain.CsvStockItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PurchaseSectorService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseSectorService.class);
    private final AuthService authService;
    private final PurchaseSectorClient purchaseSectorClient;

    public PurchaseSectorService(AuthService authService,
                                 PurchaseSectorClient purchaseSectorClient) {
        this.authService = authService;
        this.purchaseSectorClient = purchaseSectorClient;
    }
    public boolean sendPurchaseRequest(CsvStockItem item,
                                       Integer purchaseQuantity) {
        var token = authService.getToken();

        var request = new PurchaseRequest(
                item.getItemId(),
                item.getItemName(),
                item.getSupplierName(),
                item.getSupplierEmail(),
                purchaseQuantity
        );

        var response = purchaseSectorClient.sendPurchaseRequest(token, request);

        if (response.getStatusCode().value() != HttpStatus.ACCEPTED.value()) {
            logger.error("Error while sending purchase request, status: {}, response: {}", response.getStatusCode(), response);
            return false;

        }

        return true;

    }
}
