package lapicito.backend.service;

import lapicito.backend.dto.MpTransactionDto;
import lapicito.backend.dto.ViewLapicitoDto;
import lapicito.backend.entity.Lapicito;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface ILapicitoService {

    void save(MpTransactionDto mpTransactionDto);
    List<ViewLapicitoDto> getViewLapicitosDto(int id);
}
