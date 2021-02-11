package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.Jedis;

@SpringBootApplication
@RestController
public class Application {

	@RequestMapping("/")
	public String home() {
		return "Docker coneection Sava data to Redis cache!!";
	}

	@GetMapping("/getEmployee")
	
	
	public String getRedisData(@RequestParam String empId) {
		try (Jedis jedis = new Jedis("172.17.0.3", 6379, 5000)) {
			System.out.println("Connection successful");
			System.out.println("Getting response from the server: " + jedis.get(empId));
			String pingReply = jedis.get(empId);
			return "Getting response from the server: " + pingReply;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
	}
	
	@PutMapping("/addEmployee")
	public String putRedisData(@RequestParam String empId,@RequestParam String empName) {
		try (Jedis jedis = new Jedis("172.17.0.3", 6379, 5000)) {
			System.out.println("Connection successful");
			jedis.set(empId,empName);
			String pingReply = jedis.ping();
			return "Getting response from the server: " + pingReply;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
