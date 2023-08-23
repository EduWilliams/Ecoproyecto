package com.Torneo.TorneoDeLatas.Servicios;

import com.Torneo.TorneoDeLatas.Excepciones.Miexception;
import com.Torneo.TorneoDeLatas.Repositorio.Imagenrepositorio;
import com.Torneo.TorneoDeLatas.entidades.Imagen;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {

    @Autowired
    private Imagenrepositorio imagenrepositorio;

    public Imagen guardar(MultipartFile archivo) throws Miexception {
        if (archivo != null) {

            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenrepositorio.save(imagen);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

        }
        return null;

    }

    public Imagen actualizar(MultipartFile archivo, String idImagen) throws Miexception {
        if (archivo != null) {
            try {
                    Imagen imagen = new Imagen();
            
            if (idImagen != null) {
                
                Optional<Imagen> Respuesta=imagenrepositorio.findById(idImagen);
                
                if (Respuesta.isPresent()) {
                    imagen=Respuesta.get();
                }
            }
                
                    imagen.setMime(archivo.getContentType());
                    imagen.setNombre(archivo.getName());
                    imagen.setContenido(archivo.getBytes());

                    return imagenrepositorio.save(imagen);
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }

            }
            return null;

        }

    }
