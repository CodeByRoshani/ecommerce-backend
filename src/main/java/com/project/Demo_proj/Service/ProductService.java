package com.project.Demo_proj.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.Demo_proj.Model.product;
import com.project.Demo_proj.repo.ProductRepo;
@Service
public class ProductService {
	@Autowired
	private ProductRepo repo;

	public List<product> getAllProducts() {
		
		return repo.findAll();
	}

	public product getProductbyId(int id) {
		return repo.findById(id).get();
	}

	public product addProduct(product product, MultipartFile imageFile) throws IOException {
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageDate(imageFile.getBytes());
		return  repo.save(product);
		
	}

	public product updateProduct(int id, product product, MultipartFile imageFile) throws IOException {
		product.setImageDate(imageFile.getBytes());
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		return repo.save(product);
	}

	public void deletProductById(int id) {
		repo.deleteById(id);
		
	}

	public List<product> searchProducts(String keyword) {
		
		return repo.searchProducts(keyword);
	}

}
