package by.zapolski.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.zapolski.model.DownloadResult;
import by.zapolski.service.CatService;

@RestController
@RequestMapping("/api")
public class CatApiController {
	
	private CatService catService = new CatService(); 
	
	@GetMapping("/cats")
	public List<DownloadResult> sayHello() throws IOException {
		return catService.doGet(5);
	}

}
