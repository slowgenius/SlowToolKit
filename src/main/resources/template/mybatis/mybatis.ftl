public class Test {

<#list fieldList as value>
   private ${value.type} ${value.name};
</#list>
}
