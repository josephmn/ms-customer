//package com.nttdata.customer.security;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
///**
// * CustomUserDetailsService.
// *
// * @author Joseph Magallanes
// * @since 2025-05-23
// */
//public class CustomUserDetailsService implements UserDetailsService {
//
//    // Aquí podrías inyectar un repositorio para obtener el usuario real de tu base de datos
//
//    /**
//     * Carga el usuario por su nombre de usuario.
//     *
//     * @param username el nombre de usuario
//     * @return UserDetails el objeto UserDetails con la información del usuario
//     * @throws UsernameNotFoundException si no se encuentra el usuario
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Ejemplo estático
//        if ("admin".equals(username)) {
//            return User.withUsername("admin")
//                .password(new BCryptPasswordEncoder().encode("admin123"))
//                .roles("ADMIN")
//                .build();
//        }
//        else {
//            throw new UsernameNotFoundException("User not found");
//        }
//    }
//}
