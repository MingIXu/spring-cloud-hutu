package com.hutu.swagger;

import com.hutu.core.constant.CommonConstant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SwaggerProperties
 *
 * @author hutu
 */
@Data
@ConfigurationProperties(SwaggerProperties.PREFIX)
public class SwaggerProperties {
	/**
	 * Prefix of {@link SwaggerProperties}.
	 */
	public static final String PREFIX = "hutu.swagger";

	final static String[] BASE_PACKAGES = {CommonConstant.BASE_PACKAGES};
	/**
	 * swagger会解析的包路径
	 **/
	private List<String> basePackages = Arrays.asList(BASE_PACKAGES);

	/**
	 * swagger会解析的url规则
	 **/
	private List<String> basePath = new ArrayList<>();
	/**
	 * 在basePath基础上需要排除的url规则
	 **/
	private List<String> excludePath = new ArrayList<>();
	/**
	 * 标题
	 **/
	private String title = "接口文档系统";
	/**
	 * 描述
	 **/
	private String description = "接口文档系统";
	/**
	 * 版本
	 **/
	private String version = "2.6.2";
	/**
	 * 许可证
	 **/
	private String license = "";
	/**
	 * 许可证URL
	 **/
	private String licenseUrl = "";
	/**
	 * 服务条款URL
	 **/
	private String termsOfServiceUrl = "";

	/**
	 * host信息
	 **/
	private String host = "";
	/**
	 * 联系人信息
	 */
	private Contact contact = new Contact();
	/**
	 * 全局统一鉴权配置
	 **/
	private Authorization authorization = new Authorization();

	@Data
	@NoArgsConstructor
	public static class Contact {

		/**
		 * 联系人
		 **/
		private String name = "hutu";
		/**
		 * 联系人url
		 **/
		private String url = "";
		/**
		 * 联系人email
		 **/
		private String email = "806001926@qq.com";

	}

	@Data
	@NoArgsConstructor
	public static class Authorization {

		/**
		 * 鉴权策略ID，需要和SecurityReferences ID保持一致
		 */
		private String name = "";

		/**
		 * 需要开启鉴权URL的正则
		 */
		private String authRegex = "^.*$";

		/**
		 * 鉴权作用域列表
		 */
		private List<AuthorizationScope> authorizationScopeList = new ArrayList<>();

		private List<String> tokenUrlList = new ArrayList<>();
	}

	@Data
	@NoArgsConstructor
	public static class AuthorizationScope {

		/**
		 * 作用域名称
		 */
		private String scope = "";

		/**
		 * 作用域描述
		 */
		private String description = "";

	}
}
