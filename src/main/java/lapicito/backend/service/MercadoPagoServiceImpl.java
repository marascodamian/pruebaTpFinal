package lapicito.backend.service;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import lapicito.backend.dto.MpTransactionDto;
import lapicito.backend.dto.MpUserDto;
import lapicito.backend.entity.MPUser;
import lapicito.backend.entity.Usuario;
import lapicito.backend.mapper.IMpUserMapper;
import lapicito.backend.repository.IMercadoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class MercadoPagoServiceImpl implements MercadoPagoService{

    @Autowired
    IMercadoPagoRepository mercadoPagoRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    IMpUserMapper mpUserMapper;

    @Autowired
    ILapicitoService lapicitoService;

    @Override
    public void saveMercadoPago(MpUserDto mercadoPago) {
        MPUser mpUser = mercadoPagoRepository.save(mpUserMapper.toEntity(mercadoPago));
        Usuario usuario = usuarioService.getById(mercadoPago.getIdUsuario()).get();
        usuario.setMpUser(mpUser);
    }

    @Override
    public boolean existById(int id) {
        return mercadoPagoRepository.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        MPUser mpUser = mercadoPagoRepository.findById(id).get();
        Usuario usuario = usuarioService.getByMpUserId(mpUser);
        usuario.setMpUser(null);
        mercadoPagoRepository.deleteById(id);
    }

    @Override
    public String createAndRedirect(MpTransactionDto mpTransactionDto) throws MPException {

        //TEST-3338168057872422-061218-e8f9cfe2942a778488a15c480adbbcd7-366015016
        //MercadoPago.SDK.setAccessToken("TEST-2974784228505657-061822-576b1c4f90dac3d25a41a4d456451f0c-49668766");

        Usuario usuarioReceptor = usuarioService.getById(mpTransactionDto.getId_Receptor()).get();
        MercadoPago.SDK.setAccessToken(usuarioReceptor.getMpUser().getAccess_token());

        Preference preferences = new Preference();

        preferences.setBackUrls(new BackUrls().setFailure("http://localhost:8080/swagger-ui.html#/espacio-controller/saveByUsuarioUsingPOST_1")
                .setPending("http://lapicito.frontend.s3-website.us-east-2.amazonaws.com/feed")
                .setSuccess("http://lapicito.frontend.s3-website.us-east-2.amazonaws.com/lapicito/success?quantity="
                        + mpTransactionDto.getCantidadLapicitos() + "&comentario="
                        + mpTransactionDto.getComentario() + "&id_receptor="
                + mpTransactionDto.getId_Receptor() + "&id_donador="
                + mpTransactionDto.getId_donador()));

        //-----------------------------------------------------
        /// AGREGAR ÇUANDO ESTE EN EL DTO EL COMENTARIO A LA URL DEL SUCCESS
        //-----------------------------------------------------

        Item item = new Item();
        item.setDescription("Lapicito");
        item.setTitle("Donacion Lapicitos")
                .setQuantity(mpTransactionDto.getCantidadLapicitos())
                .setUnitPrice((float) 100.0);

        preferences.appendItem(item);
        Preference result = preferences.save();
        System.out.println(result.getInitPoint());

        //------------ REGISTRO DE TRANSACCION DE LAPICITOS -----------------
        //if(result.getInitPoint() != null){

            //lapicitoService.save(mpTransactionDto);
            //usuarioReceptor.setCantidad_lapicitos(usuarioReceptor.getCantidad_lapicitos() + mpTransactionDto.getCantidadLapicitos());
        //}

        return result.getInitPoint();
    }

}
