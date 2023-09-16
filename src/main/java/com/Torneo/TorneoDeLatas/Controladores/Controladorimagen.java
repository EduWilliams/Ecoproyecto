
package com.Torneo.TorneoDeLatas.Controladores;

import com.Torneo.TorneoDeLatas.Servicios.usuarioServicio;
import com.Torneo.TorneoDeLatas.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/imagen")
public class Controladorimagen {
    @Autowired
    usuarioServicio usuarioservicio;
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenusuario(@PathVariable String id){
     
       Usuario usuario= usuarioservicio.getOne(id);
       
       byte [] imagen=usuario.getImagen().getContenido();
       HttpHeaders headers= new HttpHeaders();
       headers.setContentType(MediaType.IMAGE_JPEG);
       
        return new ResponseEntity<>(imagen,headers,HttpStatus.OK);
    }
}
