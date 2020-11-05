# Swagger2使用说明
### 常用注解说明
- @Api() 用于类；表示标识这个类是swagger的资源 
    * tags–表示说明 
    * value–也是说明，可以使用tags替代 

- @ApiOperation() 用于方法；表示一个http请求的操作 
    * value用于方法描述 
    * notes用于提示内容 

- @ApiParam() 用于方法，参数，字段说明；表示对参数的添加元数据（说明或是否必填等） 
    * name–参数名 
    * value–参数说明 
    * required–是否必填

- @ApiModel()用于类 ；表示对类进行说明，用于参数用实体类接收 
    * value–表示对象名 

- @ApiModelProperty()用于方法，字段； 表示对model属性的说明或者数据操作更改 
    * value–字段说明 
    * name–重写属性名字 
    * dataType–重写属性类型 
    * required–是否必填 
    * example–举例说明 
    * hidden–隐藏

- @ApiImplicitParam() 用于方法,表示单独的请求参数

- @ApiImplicitParams() 用于方法，包含多个 @ApiImplicitParam 
    * name–参数ming 
    * value–参数说明 
    * dataType–数据类型 
    * paramType–参数类型 
    * example–举例说明

- @ApiIgnore 作用于方法上，使用这个注解swagger将忽略这个接口