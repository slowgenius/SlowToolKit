package ${packageInfo};

import lombok.Data;

import java.util.Date;
import java.math.BigDecimal;

/**
 * ${classInfo.comment}
 *
 * @author ${author}
 * @since ${since}
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
