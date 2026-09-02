package com.project.Demo_proj.Comtroller;

import java.util.List;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.Demo_proj.Model.product;
import com.project.Demo_proj.Service.ProductService;
@RestController

@CrossOrigin(origins = {"http://localhost:5173", "https://ecomerence-frontend.netlify.app"})
@RequestMapping("/api")
public class ProductController {
	@Autowired
	private ProductService service;
	
	@RequestMapping("/")
	public String greet() {
		return "Hello world";
	}
	@GetMapping("/product")
	public List<product> getAllProduct(){
		return service.getAllProducts();
		
	}
	@GetMapping("/product/{id}")
	public product getProduct(@PathVariable int id) {
		return service.getProductbyId(id);
	}
	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestPart product product,
			                              @RequestPart MultipartFile imageFile){
		
		try {
			product  product1= service.addProduct(product,imageFile);
			return new ResponseEntity<>(product1,HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/product/{productId}/image")
	public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
		product product = service.getProductbyId(productId);
		byte[] imageFile = product.getImageDate();
		return ResponseEntity.ok()
				.contentType(MediaType.valueOf(product.getImageType()))
				.body(imageFile);
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<String>updateProduct(@PathVariable int id,@RequestPart product product,
                                                          @RequestPart MultipartFile imageFile){
		product product1 = null;
		try {
			product1  = service.updateProduct(id,product,imageFile);
		}catch (Exception e) {
			return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
		}
		if(product1 !=null) {
			return new ResponseEntity<>("Updated",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Failed to Update",HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<String> deletProduct(@PathVariable int id){
		product product=service.getProductbyId(id);
		if(product !=null) {
			service.deletProductById(id);
			return new ResponseEntity<>("Deleted",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
			
		}
	}
	
	@GetMapping("product/search")
	public ResponseEntity<List<product>>searchProducts(@RequestParam String keyword){
		List<product> products = service.searchProducts(keyword);
		return new ResponseEntity<>(products,HttpStatus.OK);
		
	}
			                                                         

}
