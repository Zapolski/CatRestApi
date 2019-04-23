package by.zapolski.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.zapolski.model.DownloadResult;
import by.zapolski.service.CatService;

@RestController
@RequestMapping("/api")
public class CatApiController {
	
	@Autowired
	private CatService catService; 
	
	@GetMapping("/cats/")
	public List<DownloadResult> getCat() throws IOException {
		return catService.doGet(1);
	}
	
	@GetMapping("/cats/{count}")
	public List<DownloadResult> getCats(@PathVariable int count) throws IOException {
		
		if ((count<=0)||(count>CatService.LIMIT_IMAGES)) {
			throw new CatNotFoundException("Quantity is limited from 1 to "+CatService.LIMIT_IMAGES);
		}
		
		return catService.doGet(count);
	}
	
}
