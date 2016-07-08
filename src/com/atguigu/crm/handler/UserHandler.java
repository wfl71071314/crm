/**
 * 
 */
package com.atguigu.crm.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Role;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.RoleService;
import com.atguigu.crm.service.UserService;

/**
 * @author wang fu liang 
 *
 */
@RequestMapping("/user")
@Controller
public class UserHandler extends BaseHandler{

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	@RequestMapping(value="/shiro-login", method=RequestMethod.POST)
	public String login2(@RequestParam(value="name",required=false) String name,
			@RequestParam(value="password", required=false) String password,
			HttpSession session,
			RedirectAttributes attributes,
			Locale locale){
		// 获取当前 User: 调用了 SecurityUtils.getSubject() 方法. 
		Subject currentUser = SecurityUtils.getSubject();

		// 检测用户是否已经被认证. 即用户是否登录. 调用 Subject 的 isAuthenticated()
		if (!currentUser.isAuthenticated()) {
			// 把用户名和密码封装为一个 UsernamePasswordToken 对象. 
		    UsernamePasswordToken token = new UsernamePasswordToken(name, password);
		    token.setRememberMe(true);
		    try {
		    	// 执行登录. 调用 Subject 的 login(UsernamePasswordToken) 方法.
		        currentUser.login(token);
		    } 
		    // 认证时的异常. 所有的认证时的异常的父类. 
		    catch (AuthenticationException ae) {
		    	String code = "error.user.login";
				String message = messageSource.getMessage(code, null, locale);
				attributes.addFlashAttribute("message", message);
				return "redirect:/index";
		    }
		}
		
		//可以通过调用 Subject 的 .getPrincipals().getPrimaryPrincipal() 获取到
		//在 realm 中创建 SimpleAuthenticationInfo 对象时的 principal 实例. 
		session.setAttribute("user", currentUser.getPrincipals().getPrimaryPrincipal());
		return "home/success";
	}
	
	@RequestMapping("/delete-ajax")
	public void delete(@RequestParam("id") Integer id,HttpServletResponse response) throws IOException{
		userService.delete(id);
		response.getWriter().write("1");
	}
	
	
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String toInput(Map<String,Object> map,
							@RequestParam(value="id",required=false) Integer id){
		Map<String,String> allStatus=new HashMap<>();
		List<Role> roles = roleService.getAll();
		allStatus.put("0", "无效");
		allStatus.put("1", "有效");
		if(id==null){
			map.put("user", new User());
		}else{
			map.put("user", userService.getById(id));
		}
		map.put("roles",roles);
		map.put("allStatus", allStatus);
		return "user/input";
	}
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String saveOrUpdate(User user,RedirectAttributes attributes,Locale locale){
		if(user.getId()==null){
			User checkUser=userService.getByName(user.getName());
			if(checkUser==null){
				userService.save(user);	
				attributes.addFlashAttribute("message","新建成功！");
			}else{
				String code="error.user.save";
				String message = messageSource.getMessage(code, null, locale);
				attributes.addFlashAttribute("message", message);
			}
		}else{
				userService.update(user);	
				attributes.addFlashAttribute("message","修改成功！");
		}
		return "redirect:/user/list2";
	}
	
	@RequestMapping("/list2")
	public String toList(Map<String,Object> map,
						@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,HttpServletRequest request){
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = encodeParamsToQueryString(params);
		map.put("queryString", queryString);
		Page<User> page=userService.getList(pageNo,params);
		map.put("page", page);
		return "user/list";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session,RedirectAttributes attributes){
		session.invalidate();
		attributes.addFlashAttribute("message","成功注销！");
		return "home/index";
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam(value="name",required=false) String name,
			@RequestParam(value="password",required=false) String password,
			HttpSession session,RedirectAttributes attributes,Locale locale){
		User user=userService.getByName(name,password);
		if(user==null){
			String code="error.user.login";
			String message = messageSource.getMessage(code, null, locale);
			attributes.addFlashAttribute("message", message);
			return "redirect:/index";
		}
		session.setAttribute("user", user);
		return "home/success";
	}
}
