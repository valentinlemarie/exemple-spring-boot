import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Grab(group='mysql', module='mysql-connector-java', version='5.1.41')
class DataSourceConfig {
@Bean
public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/sirh-paie");
    dataSource.setUsername("root");
    dataSource.setPassword("");
    return dataSource;
}
}

class Cotisation {
def id
def code
def libelle
def tauxSalarial
def tauxPatronal
}

@RestController
class CotisationsController {

@Autowired JdbcTemplate jdbcTemplate;

RowMapper<Cotisation> rowMapper = {rs,num -> return new Cotisation(
    id:rs.getInt("ID"), 
    libelle:rs.getString("LIBELLE"),
    code:rs.getString("CODE"),
    tauxSalarial:rs.getString("TAUXSALARIAL"),
    tauxPatronal:rs.getString("TAUXPATRONAL")
)}

  @RequestMapping("/")
  List<Cotisation> lister() {
    return this.jdbcTemplate.query("select * from COTISATION", rowMapper)
  }

}