package lapicito.backend.repository;

import lapicito.backend.dto.MpTransactionDto;
import lapicito.backend.dto.ViewLapicitoDto;
import lapicito.backend.entity.Espacio;
import lapicito.backend.entity.Lapicito;
import lapicito.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILapicitoRepository extends JpaRepository<Lapicito, Integer> {
    void save(MpTransactionDto mpTransactionDto);
    List<Lapicito> getAllByUsuarioReceptor(Usuario usuario);
}
