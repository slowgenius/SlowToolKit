package ${packageInfo};

import lombok.Data;

import java.util.Date;

/**
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
