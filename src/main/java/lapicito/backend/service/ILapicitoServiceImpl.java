package lapicito.backend.service;

import lapicito.backend.dto.MpTransactionDto;
import lapicito.backend.dto.ViewLapicitoDto;
import lapicito.backend.entity.Lapicito;
import lapicito.backend.entity.Usuario;
import lapicito.backend.mapper.ILapicitoMapper;
import lapicito.backend.repository.ILapicitoRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.View;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class ILapicitoServiceImpl implements ILapicitoService{

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ILapicitoRepository lapicitoRepository;

    @Autowired
    ILapicitoMapper lapicitoMapper;


    @Override
    public void save(MpTransactionDto mpTransactionDto){

        Lapicito lapicito = new Lapicito(mpTransactionDto.getCantidadLapicitos(), usuarioService.getById(mpTransactionDto.getId_Receptor()).get()
                                            ,usuarioService.getById(mpTransactionDto.getId_donador()).get(), Calendar.getInstance(),mpTransactionDto.getComentario());

        lapicitoRepository.save(lapicito);

        Usuario usuario = usuarioService.getById(lapicito.getUsuarioReceptor().getId()).get();
        usuario.setCantidad_lapicitos(usuario.getCantidad_lapicitos()+ mpTransactionDto.getCantidadLapicitos());

    }


    @Override
    public List<ViewLapicitoDto> getViewLapicitosDto(int id){

        Usuario usuarioReceptor = usuarioService.getById(id).get();
        List<ViewLapicitoDto> listaLapicitos = lapicitoMapper.toDTOList(lapicitoRepository.getAllByUsuarioReceptor(usuarioReceptor));

        return listaLapicitos;
    }

}
