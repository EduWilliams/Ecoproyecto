package com.Torneo.TorneoDeLatas.Servicios;

import com.Torneo.TorneoDeLatas.Excepciones.Miexception;
import com.Torneo.TorneoDeLatas.Repositorio.UsuarioRepositorio;
import com.Torneo.TorneoDeLatas.entidades.Imagen;
import com.Torneo.TorneoDeLatas.entidades.Usuario;
import com.Torneo.TorneoDeLatas.enumeraciones.Rol;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class usuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuariorepositorio;
    @Autowired
    private ImagenServicio imagenservicio;

    @Transactional
    public void crearUsuario(MultipartFile archivo,String email, Integer dni, String nombre, String apellido, String password, String password2) throws Miexception {

        validar(email, dni, nombre, apellido, password, password2);

        Usuario usuario = new Usuario();

        usuario.setDni(dni);
        usuario.setApellido(apellido);
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setEcopuntos(100);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setAlta(new Date());
        usuario.setRol(Rol.USER);
        Imagen imagen=imagenservicio.guardar(archivo);
        usuario.setImagen(imagen);
        usuario.setId(id);
        usuariorepositorio.save(usuario);
    }

    public List<Usuario> listarUsuario() {
        List<Usuario> usuarios = new ArrayList();

        usuarios = usuariorepositorio.findAll();
        return usuarios;

    }

    @Transactional
    public void modificarUsuario(MultipartFile archivo, String id,String email, Integer dni, String nombre, String apellido, String password, String password2) throws Miexception {
        validar(email, dni, nombre, apellido, password, password2);
        Optional<Usuario> respuesta = usuariorepositorio.findById(email);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setPassword(password);
            usuario.setApellido(apellido);
            usuario.setNombre(nombre);
            usuario.setDni(dni);
            
            String idImagen=null;
            if (usuario.getImagen()!=null) {
                idImagen=usuario.getImagen().getId();
                Imagen imagen=imagenservicio.actualizar(archivo, idImagen);
                usuario.setImagen(imagen);
            }

            usuariorepositorio.save(usuario);
        }

    }

    @Transactional
    public void modificarUsuariopuntos(String email, Double ecop) {

        Optional<Usuario> respuesta = usuariorepositorio.findById(email);

        if (respuesta.isPresent()) {
            if (ecop != null) {

                Usuario usuario = respuesta.get();
                usuario.setEcopuntos((ecop * 400) + usuario.getEcopuntos());
                System.out.println("Se sumaron " + (ecop * 700));

                usuariorepositorio.save(usuario);
            }
        }
    }

    @Transactional
    public void modificarUsuariopuntosU(String email, Double ecop2, Double env) {

        Optional<Usuario> respuesta = usuariorepositorio.findById(email);

        if (respuesta.isPresent()) {

        }
        if (ecop2 != null) {
            Usuario usuario = respuesta.get();
            usuario.setEcopuntos((ecop2 * (env / 100)) + usuario.getEcopuntos());
            usuariorepositorio.save(usuario);
        }

    }

    private void validar(String email, Integer dni, String nombre, String apellido, String password, String password2) throws Miexception {
        if (email == null || email.isEmpty()) {
            throw new Miexception("Debe ingresar su correo electronico para contacto");
        }

        if (nombre == null || nombre.isEmpty()) {
            throw new Miexception("Debe ingresar su nombre");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new Miexception("Debe ingresar su apellido");
        }

        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new Miexception("La contraseña no debe estar vacia ni ser menor a 5 digitos");
        }

        if (!password.equals(password2)) {
            System.out.println("Las contraseñas deben ser iguales");
            throw new Miexception("Las contraseñas deben ser iguales");

        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuariorepositorio.buscarporemail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }

}
