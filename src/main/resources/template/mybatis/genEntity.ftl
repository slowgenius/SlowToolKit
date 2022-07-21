package ${packageInfo};

import lombok.Data;

import java.util.Date;
import java.math.BigDecimal;

/**
 * @author ${author}
 * @since ${since}
 *
 * ${classInfo.comment}
 */
@Data
public class ${classInfo.name} {

<#list fieldInfoList as value>
 /**
 * ${value.comment}
 */
 private ${value.type} ${value.name};
</#list>
}
