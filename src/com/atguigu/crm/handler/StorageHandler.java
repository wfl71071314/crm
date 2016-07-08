package com.atguigu.crm.handler;

import java.io.IOException;
import java.util.List;
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
import com.atguigu.crm.entity.Storage;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.ProductService;
import com.atguigu.crm.service.StorageService;


@RequestMapping("storage")
@Controller
public class StorageHandler extends BaseHandler{

	@Autowired
	private StorageService storageService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/list")
	public String query(Map<String,Object> map,
			@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,
			HttpServletRequest request){
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = encodeParamsToQueryString(params);
		System.out.println(queryString);
		map.put("queryString", queryString);
		
		Page<Storage> page=storageService.getPage(pageNo,params);
		
		map.put("page", page);
		return "storage/list";
	}
	
	@RequestMapping("/delete-ajax")
	public void delete(@RequestParam("id") Integer id,HttpServletResponse response) throws IOException{
		storageService.delete(id);
		response.getWriter().write("1");
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String saveOrUpdate(Storage storage,RedirectAttributes attributes
							,@RequestParam(value="stockCount",required=false) int stockCount,
							@RequestParam(value="incrementCount",required=false) int incrementCount){
		if(storage.getId()==null){
			storageService.save(storage);
			attributes.addFlashAttribute("message", "新建成功！");
		}else{
			int newStockCount=stockCount+incrementCount;
			storage.setStockCount(newStockCount);
			storageService.update(storage);
			attributes.addFlashAttribute("message", "更新成功！");
		}
		return "redirect:/storage/list2";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String toCreate(Map<String,Object> map,
							@RequestParam(value="id",required=false) Integer id){
		if(id==null){
			List<Product> products=productService.getAll();
			map.put("products", products);
			map.put("storage", new Storage());
		}else{
			Storage storage = storageService.getById(id);
			map.put("storage", storage);
		}
		
		return "storage/input";
	}
	
	@RequestMapping("/list2")
	public String list2(Map<String,Object> map,@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo){
		Page<Storage> page=storageService.getPage2(pageNo);
		map.put("page",page);
		return "storage/list";
	}
}
