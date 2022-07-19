package ${packageInfo};

/**
* ${classInfo.comment}
*/
public class ${classInfo.name} {

<#list fieldInfoList as value>
 /**
 * ${value.comment}
 */
 private ${value.type} ${value.name};
</#list>
}
