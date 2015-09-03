package py.com.proyectobase.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private LoginSuccessHandler successHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userRoleService).passwordEncoder(
                new Md5PasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Map<String, String> permisosUrl = getPermisosUrl();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expression = http
                .authorizeRequests();

        for (String url : permisosUrl.keySet()) {
            String permiso = permisosUrl.get(url);

            expression = expression.antMatchers(url)
                    .access("hasRole('".concat(permiso).concat(
                            "') or hasRole('root')"));
        }
        
        /*Modificacion Se deshabilita csrf*/
        expression.and().formLogin().loginPage("/login").and().formLogin()
                .successHandler(successHandler).and().exceptionHandling()
                .accessDeniedPage("/403").and().csrf().disable();
        

    }

    private Map<String, String> getPermisosUrl() {
        Map<String, String> permisosUrl = new HashMap<String, String>();
        try {

            XMLConfiguration xmlConfiguration = new XMLConfiguration(getClass()
                    .getClassLoader().getResource("urls.xml"));

            List<HierarchicalConfiguration> urls = xmlConfiguration
                    .configurationsAt("url");

            for (HierarchicalConfiguration url : urls) {
                String nombre = url.getString("nombre");
                String permiso = url.getString("permiso");
                permisosUrl.put(nombre, permiso);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permisosUrl;
    }

}