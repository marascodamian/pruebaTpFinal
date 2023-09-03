package lapicito.backend.service;

import com.mercadopago.exceptions.MPException;
import lapicito.backend.dto.MpTransactionDto;
import lapicito.backend.dto.MpUserDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public interface MercadoPagoService {

    void saveMercadoPago(MpUserDto mpUserDto);
    boolean existById(int id);
    void deleteById(int id);
    String createAndRedirect(MpTransactionDto mpTransactionDto) throws MPException;
}
