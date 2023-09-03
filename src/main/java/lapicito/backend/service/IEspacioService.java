package lapicito.backend.service;

import lapicito.backend.dto.ArrayCategoriasDto;
import lapicito.backend.dto.EspaciosDto;
import lapicito.backend.dto.ViewEspacioDto;
import lapicito.backend.entity.Categoria;
import lapicito.backend.entity.Espacio;
import lapicito.backend.entity.Usuario;
import lapicito.backend.entity.Usuario_Espacio_Permiso;

import java.util.List;
import java.util.Optional;

public interface IEspacioService {

    public void saveByUsuario(Espacio espacio);
    public void save(Espacio espacio);
    public Optional<Espacio> getById(int id);
    public boolean existsById(int id);
    public void update(Espacio espacio);
    public void delete(int id);
    List<ViewEspacioDto> getListByArrayCategorias(ArrayCategoriasDto ac);
    ViewEspacioDto getDtoById(int id);

    List<ViewEspacioDto> search(String value, ArrayCategoriasDto ac);
}
