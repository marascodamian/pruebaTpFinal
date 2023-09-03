package lapicito.backend.dto;

public class MpTransactionDto {

    private int id_donador;
    private int id_Receptor;
    private int cantidadLapicitos;
    private String comentario;

    public MpTransactionDto(){

    }

    public int getId_donador() {
        return id_donador;
    }

    public void setId_donador(int id_donador) {
        this.id_donador = id_donador;
    }

    public int getId_Receptor() {
        return id_Receptor;
    }

    public void setId_Receptor(int id_Receptor) {
        this.id_Receptor = id_Receptor;
    }

    public int getCantidadLapicitos() {
        return cantidadLapicitos;
    }

    public void setCantidadLapicitos(int cantidadLapicitos) {
        this.cantidadLapicitos = cantidadLapicitos;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

}
