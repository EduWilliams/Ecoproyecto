
package com.Torneo.TorneoDeLatas.Repositorio;

import com.Torneo.TorneoDeLatas.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Imagenrepositorio extends JpaRepository<Imagen,String>{
    
}
