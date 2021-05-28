package com.hutu.cloud.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.hutu.cloud.core.constant.CommonConstant;
import com.hutu.cloud.core.constant.ProfilesConstant;
import com.hutu.cloud.mybatis.properties.MybatisPlusAutoFillProperties;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.hutu.cloud.mybatis.properties.MybatisPlusAutoFillProperties.AUTO_FILL_PREFIX;

/**
 * mybatis统一配置
 *
 * @author hutu
 * @date 2020/5/25 3:13 下午
 */
@Configuration(proxyBeanMethods = false)
@MapperScan({ CommonConstant.MAPPER_PACKAGES, CommonConstant.MAPPER_PACKAGES_EXTRA })
@EnableConfigurationProperties(MybatisPlusAutoFillProperties.class)
public class MybatisAutoConfiguration {

	/**
	 * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false
	 * 避免缓存出现问题(该属性会在旧插件移除后一同移除) 注意:
	 * <p>
	 * 使用多个功能需要注意顺序关系,建议使用如下顺序
	 * <p>
	 * 多租户,动态表名 分页,乐观锁 sql性能规范,防止全表更新与删除 总结: 对sql进行单次改造的优先放入,不对sql进行改造的最后放入
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
			@Override
			public Expression getTenantId() {
				// 从当前系统上下文中取出当前请求的服务商ID，通过解析器注入到SQL中。
				return new LongValue(1);
			}

			@Override
			public String getTenantIdColumn() {
				return "tenant_id";
			}

			// 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
			@Override
			public boolean ignoreTable(String tableName) {
				return !"user".equalsIgnoreCase(tableName);
			}
		}));
		// 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
		// 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		// 攻击 SQL 阻断解析器,防止全表更新与删除
		interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
		return interceptor;
	}

	@Bean
	public ConfigurationCustomizer configurationCustomizer() {
		return configuration -> configuration.setUseDeprecatedExecutor(false);
	}

	/**
	 * 由于开发人员水平参差不齐，即使订了开发规范很多人也不遵守
	 * <p>
	 * SQL是影响系统性能最重要的因素，所以拦截掉垃圾SQL语句
	 * </p>
	 * 生产不使用，在开发阶段优化
	 */
	@Bean
	@Profile({ ProfilesConstant.DEV, ProfilesConstant.TEST })
	public IllegalSQLInnerInterceptor illegalSQLInnerInterceptor(MybatisPlusInterceptor mybatisPlusInterceptor) {
		IllegalSQLInnerInterceptor illegalSqlInnerInterceptor = new IllegalSQLInnerInterceptor();
		mybatisPlusInterceptor.addInnerInterceptor(illegalSqlInnerInterceptor);
		return illegalSqlInnerInterceptor;
	}

	/**
	 * 自动填充字段
	 * @return 填充处理类
	 */
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = AUTO_FILL_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
	public MetaObjectHandler metaObjectHandler(MybatisPlusAutoFillProperties autoFillProperties) {
		return new FillMetaObjectHandler(autoFillProperties);
	}

}
