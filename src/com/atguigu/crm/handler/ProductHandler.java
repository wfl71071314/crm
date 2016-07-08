package com.atguigu.crm.handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Product;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.ProductService;

@RequestMapping("/product")
@Controller
public class ProductHandler extends BaseHandler {

	@Autowired
	private ProductService productService; 
	
	@RequestMapping("/list")
	public String query(Map<String,Object> map,
						@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,
						HttpServletRequest request){
		Map<String,Object> params=WebUtils.getParametersStartingWith(request,"search_");
		String queryString=encodeParamsToQueryString(params);
		map.put("queryString", queryString);
		
		Page<Product> page=productService.getPage2(pageNo,params);
		
		map.put("page", page);
		return "product/list";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String create(Product product,RedirectAttributes attributes){
		if(product.getId()==null){
			productService.save(product);
			attributes.addFlashAttribute("message","新建成功！");
		}else{
			productService.update(product);
			attributes.addFlashAttribute("message","修改成功！");
		}
		return "redirect:/product/list2";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String toCreate(Map<String,Object> map,@RequestParam(value="id",required=false) Integer id){
		if(id!=null){
			map.put("product", productService.getById(id));
		}else{
			map.put("product",new Product());
		}
		return "product/input";
	}
	
	@RequestMapping("/delete-ajax")
	public void delete(@RequestParam("id") Integer id,HttpServletResponse response) throws IOException{
		productService.delete(id);
		response.getWriter().write("1");
	}
	
	@RequestMapping("/list2")
	public String list(Map<String,Object> map,@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo){
		Page<Product> page=productService.getPage2(pageNo);
		map.put("page", page);
		return "product/list";
	}
	
}
