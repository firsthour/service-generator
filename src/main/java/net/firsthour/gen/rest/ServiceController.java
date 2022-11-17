package net.firsthour.gen.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.firsthour.gen.generation.ServiceMethodGenerator;
import net.firsthour.gen.model.JavaMethod;
import net.firsthour.gen.model.Service;
import net.firsthour.gen.repo.ServiceRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/services")
public class ServiceController {
	
	private ServiceRepository serviceRepository;
	
	public ServiceController(ServiceRepository serviceRepository) {
		this.serviceRepository = serviceRepository;
	}
	
	@PostMapping("/generate")
	public JavaMethod generate(@Valid @RequestBody Service service) {
		final String code = new ServiceMethodGenerator(service).generate();
		var java = new JavaMethod();
		java.setCode(code);
		return java;
	}
	
	@PostMapping("/save")
	public void save(@Valid @RequestBody Service service) {
		serviceRepository.save(service);
	}
	
	@GetMapping
	public List<Service> getServices() {
		return (List<Service>) serviceRepository.findAll();
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(final MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
