package com.saulo.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.saulo.workshopmongo.domain.Post;
import com.saulo.workshopmongo.domain.User;
import com.saulo.workshopmongo.dto.AuthorDTO;
import com.saulo.workshopmongo.dto.CommentDTO;
import com.saulo.workshopmongo.repository.PostRepository;
import com.saulo.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PostRepository postrepo;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		repo.deleteAll();
		postrepo.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		repo.saveAll(Arrays.asList(maria,alex,bob));
		
		Post post1 = new Post(null, "Partiu Viagem!","Vou viajar para São Paulo,abraços!", sdf.parse("21/03/2018"), new AuthorDTO(maria));
		Post post2 = new Post(null, "Partiu Viagem!","Bom dia!Acordei Feliz hoje!", sdf.parse("21/03/2018"), new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Boa viagem,mana!", sdf.parse("21/03/2018"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite!", sdf.parse("21/03/2018"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", sdf.parse("21/03/2018"), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1,c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		postrepo.saveAll(Arrays.asList(post1,post2));
		
		maria.getPosts().addAll(Arrays.asList(post1,post2));
		repo.save(maria);
	}

}
