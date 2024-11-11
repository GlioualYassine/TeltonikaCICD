package com.geolocation.geolocation_api;

import com.geolocation.geolocation_api.entities.Company;
import com.geolocation.geolocation_api.entities.User;
import com.geolocation.geolocation_api.entities.enums.ERole;
import com.geolocation.geolocation_api.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import com.geolocation.geolocation_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class GeolocationApiApplication  {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(GeolocationApiApplication.class, args);
	}
//@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, CompanyRepository companyRepository) {
		return args -> {
			// Création des utilisateurs (50 utilisateurs)
			List<User> users = Arrays.asList(
					new User("John", "Doe", "john.doe1@example.com", passwordEncoder.encode("Password@1")),
					new User("Jane", "Smith", "jane.smith2@example.com", "Password@2"),
					new User("Alice", "Johnson", "alice.johnson3@example.com", "Password@3"),
					new User("Bob", "Brown", "bob.brown4@example.com", "Password@4"),
					new User("Charlie", "Davis", "charlie.davis5@example.com", "Password@5"),
					new User("David", "Miller", "david.miller6@example.com", "Password@6"),
					new User("Eve", "Wilson", "eve.wilson7@example.com", "Password@7"),
					new User("Frank", "Moore", "frank.moore8@example.com", "Password@8"),
					new User("Grace", "Taylor", "grace.taylor9@example.com", "Password@9"),
					new User("Hank", "Anderson", "hank.anderson10@example.com", "Password@10"),
					new User("Ivy", "Thomas", "ivy.thomas11@example.com", "Password@11"),
					new User("Jack", "Jackson", "jack.jackson12@example.com", "Password@12"),
					new User("Kate", "White", "kate.white13@example.com", "Password@13"),
					new User("Leo", "Harris", "leo.harris14@example.com", "Password@14"),
					new User("Mia", "Martin", "mia.martin15@example.com", "Password@15"),
					new User("Noah", "Thompson", "noah.thompson16@example.com", "Password@16"),
					new User("Olivia", "Garcia", "olivia.garcia17@example.com", "Password@17"),
					new User("Paul", "Martinez", "paul.martinez18@example.com", "Password@18"),
					new User("Quinn", "Robinson", "quinn.robinson19@example.com", "Password@19"),
					new User("Rachel", "Clark", "rachel.clark20@example.com", "Password@20"),
					new User("Steve", "Rodriguez", "steve.rodriguez21@example.com", "Password@21"),
					new User("Tina", "Lewis", "tina.lewis22@example.com", "Password@22"),
					new User("Uma", "Walker", "uma.walker23@example.com", "Password@23"),
					new User("Vince", "Hall", "vince.hall24@example.com", "Password@24"),
					new User("Wendy", "Allen", "wendy.allen25@example.com", "Password@25"),
					new User("Xander", "Young", "xander.young26@example.com", "Password@26"),
					new User("Yara", "King", "yara.king27@example.com", "Password@27"),
					new User("Zane", "Scott", "zane.scott28@example.com", "Password@28"),
					new User("Aiden", "Adams", "aiden.adams29@example.com", "Password@29"),
					new User("Bella", "Baker", "bella.baker30@example.com", "Password@30"),
					new User("Caleb", "Gonzalez", "caleb.gonzalez31@example.com", "Password@31"),
					new User("Diana", "Nelson", "diana.nelson32@example.com", "Password@32"),
					new User("Eli", "Carter", "eli.carter33@example.com", "Password@33"),
					new User("Fiona", "Mitchell", "fiona.mitchell34@example.com", "Password@34"),
					new User("George", "Perez", "george.perez35@example.com", "Password@35"),
					new User("Holly", "Roberts", "holly.roberts36@example.com", "Password@36"),
					new User("Ian", "Turner", "ian.turner37@example.com", "Password@37"),
					new User("Jade", "Phillips", "jade.phillips38@example.com", "Password@38"),
					new User("Kyle", "Campbell", "kyle.campbell39@example.com", "Password@39"),
					new User("Lily", "Parker", "lily.parker40@example.com", "Password@40"),
					new User("Mason", "Evans", "mason.evans41@example.com", "Password@41"),
					new User("Nina", "Edwards", "nina.edwards42@example.com", "Password@42"),
					new User("Owen", "Collins", "owen.collins43@example.com", "Password@43"),
					new User("Paula", "Stewart", "paula.stewart44@example.com", "Password@44"),
					new User("Quincy", "Sanchez", "quincy.sanchez45@example.com", "Password@45"),
					new User("Ruth", "Morris", "ruth.morris46@example.com", "Password@46"),
					new User("Sam", "Rogers", "sam.rogers47@example.com", "Password@47"),
					new User("Tara", "Reed", "tara.reed48@example.com", "Password@48"),
					new User("Ulysses", "Cook", "ulysses.cook49@example.com", "Password@49"),
					new User("Victoria", "Morgan", "victoria.morgan50@example.com", "Password@50")
			);
			userRepository.saveAll(users);

			// Création des entreprises marocaines (25 entreprises)
			List<Company> companies = Arrays.asList(
					new Company("Atlas Technologies", "123 Avenue des Nations Unies, Casablanca", "+212-555-1111"),
					new Company("Sahara Innovations", "456 Rue de Marrakech, Rabat", "+212-555-2222"),
					new Company("Casablanca Digital", "789 Boulevard Zerktouni, Casablanca", "+212-555-3333"),
					new Company("Tangier IT Solutions", "321 Rue de Tanger, Tanger", "+212-555-4444"),
					new Company("Rabat Softworks", "654 Avenue Hassan II, Rabat", "+212-555-5555"),
					new Company("Fes Advanced Systems", "987 Avenue Moulay Ismail, Fes", "+212-555-6666"),
					new Company("Marrakech Web Services", "123 Route de l'Ourika, Marrakech", "+212-555-7777"),
					new Company("Agadir DevHub", "456 Rue de la Corniche, Agadir", "+212-555-8888"),
					new Company("Oujda AI Labs", "789 Boulevard des Facultés, Oujda", "+212-555-9999"),
					new Company("Kenitra Data Center", "321 Boulevard Mohamed V, Kenitra", "+212-555-1010"),
					new Company("Tetouan Tech Experts", "654 Rue d'Espagne, Tetouan", "+212-555-1111"),
					new Company("Meknes Software Solutions", "987 Avenue des F.A.R., Meknes", "+212-555-1212"),
					new Company("Essaouira Cloud Services", "123 Rue de la Liberté, Essaouira", "+212-555-1313"),
					new Company("Laayoune Network Solutions", "456 Avenue Moulay Rachid, Laayoune", "+212-555-1414"),
					new Company("Nador CyberTech", "789 Boulevard Hassan II, Nador", "+212-555-1515"),
					new Company("Taza Digital Solutions", "321 Rue de Fes, Taza", "+212-555-1616"),
					new Company("Mohammedia IT Hub", "654 Avenue Hassan I, Mohammedia", "+212-555-1717"),
					new Company("El Jadida Smart Systems", "987 Rue de Marrakech, El Jadida", "+212-555-1818"),
					new Company("Beni Mellal Web Technologies", "123 Avenue de l'Industrie, Beni Mellal", "+212-555-1919"),
					new Company("Khouribga Data Solutions", "456 Rue des Phosphates, Khouribga", "+212-555-2020"),
					new Company("Khemisset IT Innovations", "789 Boulevard Mohamed V, Khemisset", "+212-555-2121"),
					new Company("Settat Cloud Systems", "321 Avenue des F.A.R., Settat", "+212-555-2222"),
					new Company("Ouarzazate Tech Center", "654 Route de Marrakech, Ouarzazate", "+212-555-2323"),
					new Company("Safi Web Dynamics", "987 Rue de la Mer, Safi", "+212-555-2424"),
					new Company("Al Hoceima Digital Ventures", "123 Boulevard Mohamed V, Al Hoceima", "+212-555-2525")
			);
			companyRepository.saveAll(companies);

			// Assigner des administrateurs et utilisateurs à chaque entreprise
			for (int i = 0; i < companies.size(); i++) {
				Company company = companies.get(i);
				User admin = users.get(i);  // Administrateur (1 par entreprise)
				company.setAdmin(admin);

				User user1 = users.get((i + 25) % users.size());  // Utilisateur 1
				User user2 = users.get((i + 30) % users.size());  // Utilisateur 2
				User user3 = users.get((i + 35) % users.size());  // Utilisateur 3

				List<User> usersList = Arrays.asList(
						user1,
						user2,
						user3
				);
				usersList.forEach(user->user.setCompany(company));

				// Ajouter des utilisateurs supplémentaires à l'entreprise
				company.setUsers(usersList);
				userRepository.saveAll(usersList);
				companyRepository.save(company);
			}
		};


}


}
